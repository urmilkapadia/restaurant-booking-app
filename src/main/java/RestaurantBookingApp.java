// Required Libraries:
// - io.muserver: for lightweight HTTP server
// - com.fasterxml.jackson.core:jackson-databind for JSON (also jsr310 for Java 8 Date/Time)
// - junit:junit for unit testing

import io.muserver.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class RestaurantBookingApp {

    // In-memory storage of bookings (by date)
    private static final Map<LocalDate, List<Booking>> bookings = new ConcurrentHashMap<>();

    // ObjectMapper with support for Java 8 Date/Time
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // Phone number format: ddd-ddd-ddd
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{3}-\\d{3}-\\d{3}");

    public static void main(String[] args) {
        MuServer server = MuServerBuilder.httpServer()
                .withHttpPort(8080)

                // GET /bookings?date=yyyy-MM-dd
                .addHandler(Method.GET, "/bookings", (request, response, pathParams) -> {
                    String dateParam = request.query().get("date");
                    if (dateParam == null) {
                        response.status(400);
                        response.write("Missing 'date' query parameter.");
                        return;
                    }
                    LocalDate date = LocalDate.parse(dateParam);
                    List<Booking> bookingsForDate = bookings.getOrDefault(date, new ArrayList<>());
                    response.contentType("application/json");response.write(mapper.writeValueAsString(bookingsForDate));
                })

                // POST /bookings with JSON body
                .addHandler(Method.POST, "/bookings", (request, response, pathParams) -> {
                    try {
                        Booking booking = mapper.readValue(request.readBodyAsString(), Booking.class);

                        // Input validations
                        if (booking.getCustomerName() == null || booking.getCustomerName().trim().isEmpty()) {
                            response.status(400);
                            response.write("Customer name is required.");
                            return;
                        }

                        if (booking.getPhone() == null || !PHONE_PATTERN.matcher(booking.getPhone()).matches()) {
                            response.status(400);
                            response.write("Phone must match ddd-ddd-ddd.");
                            return;
                        }

                        if (booking.getDate() == null || booking.getTime() == null) {
                            response.status(400);
                            response.write("Date and time must be provided.");
                            return;
                        }

                        // Avoid booking the same time slot for same table size
                        List<Booking> bookingsForDate = bookings.computeIfAbsent(booking.getDate(), k -> new ArrayList<>());
                        boolean timeConflict = bookingsForDate.stream()
                                .anyMatch(b -> b.getTime().equals(booking.getTime()) && b.getTableSize() == booking.getTableSize());

                        if (timeConflict) {
                            response.status(409);
                            response.write("Table is full at this time. Please choose another slot.");
                            return;
                        }

                        bookingsForDate.add(booking);
                        response.status(201);
                        response.write("Booking created successfully.");

                    } catch (Exception e) {
                        response.status(500);
                        response.write("Error processing request: " + e.getMessage());
                    }
                })

                .start();

        System.out.println("Server started at " + server.uri());
    }


}
