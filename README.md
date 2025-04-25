# 🍽 Restaurant Booking App

A lightweight Java web server to handle restaurant bookings with validations. Built using MuServer and Jackson.

---
## 🪑 Assumptions

- No database is used.
- The `tableSize` acts as a category. The same `tableSize` and `time` cannot be booked twice on the same date.
- All data is lost when the server restarts.

---

## 🧾 Features

- Accepts table booking requests via REST API
- Validates input:
  - Customer name must not be empty
  - Phone number format must match `ddd-ddd-ddd` (e.g., `123-456-789`)
  - Date and Time are mandatory
  - Prevents double-booking for the same table size and time
- Stores data in-memory (not using DB)

---

## 📁 Project Structure

```
project-root/
│
├── build.gradle
├── settings.gradle
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── RestaurantBookingApp.java
│   └── test/
│       └── java/
│           └── RestaurantBookingAppTest.java
```

---

## 📦 Dependencies

Your `build.gradle` should include:

```groovy
plugins {
    id 'java'
    id 'application'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.muserver:mu-server:1.0.0'
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.17.0'
    implementation 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.0'
    testImplementation 'junit:junit:4.13.2'
}

application {
    mainClass = 'RestaurantBookingApp'
}
```

---

## 🚀 How to Run the App

1. Open terminal and navigate to your project root
2. Start the application:

```bash
./gradlew run
```

3. App runs on:  
   👉 `http://localhost:8080`

---

## ✅ Input Validation

| Field         | Validation                                      |
|---------------|--------------------------------------------------|
| `customerName`| Must not be empty                                |
| `phone`       | Must match format `ddd-ddd-ddd`                  |
| `date`        | Must be a valid date in format `yyyy-MM-dd`      |
| `time`        | Must be a valid time in format `HH:mm`           |
| `tableSize`   | Integer value required                           |

📌 **Conflict Rule**:  
A booking cannot be created if another one exists with the same `date`, `time`, and `tableSize`.

---

## 📡 API Usage

### 🔍 Get Bookings

**GET /bookings?date=yyyy-MM-dd**

Example:

```bash
curl "http://localhost:8080/bookings?date=2025-04-30"
```

---

### 📝 Create Booking

**POST /bookings**

**Request JSON:**

```json
{
  "customerName": "Alice",
  "phone": "123-456-789",
  "tableSize": 2,
  "date": "2025-04-30",
  "time": "18:00"
}
```

**Example cURL:**

```bash
curl -X POST http://localhost:8080/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Alice",
    "phone": "123-456-789",
    "tableSize": 2,
    "date": "2025-04-30",
    "time": "18:00"
}'
```

---

## 🧪 Run Unit Tests

```bash
./gradlew test
```

---


---

## 📍 Output Example

- **201 Created** – if booking is successful
- **400 Bad Request** – if validation fails
- **409 Conflict** – if slot is already booked
- **500 Internal Server Error** – on unexpected issues

---

Happy Coding 👨‍💻
