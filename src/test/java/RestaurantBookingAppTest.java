
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.Assert.*;

public class RestaurantBookingAppTest {

    @Test
    public void testBookingCreation() {
        RestaurantBookingApp.Booking booking = new RestaurantBookingApp.Booking(
            "John Doe", "123-456-789", 4, LocalDate.now(), LocalTime.of(19, 0)
        );
        assertEquals("John Doe", booking.getCustomerName());
        assertEquals("123-456-789", booking.getPhone());
        assertEquals(4, booking.getTableSize());
        assertNotNull(booking.getDate());
        assertNotNull(booking.getTime());
    }

    @Test
    public void testInvalidPhonePattern() {
        String invalidPhone = "123456789";
        assertFalse("Phone should not match", invalidPhone.matches("\\d{3}-\\d{3}-\\d{3}"));
    }

    @Test
    public void testValidPhonePattern() {
        String validPhone = "123-456-789";
        assertTrue("Phone should match", validPhone.matches("\\d{3}-\\d{3}-\\d{3}"));
    }

    @Test
    public void testEmptyNameCheck() {
        RestaurantBookingApp.Booking booking = new RestaurantBookingApp.Booking(
            "", "123-456-789", 4, LocalDate.now(), LocalTime.of(18, 0)
        );
        assertTrue("Customer name should be empty", booking.getCustomerName().trim().isEmpty());
    }
}
