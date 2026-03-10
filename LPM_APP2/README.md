# LPM_APP2 - Library Place Management System

A Spring Boot REST API application for managing library places (rooms), students, and reservations. This application demonstrates a layered architecture with Controllers, DTOs, Services, Repositories, and Exception Handling.

## Overview

**LPM_APP2** is a place reservation system that allows:
- **Students** to make reservations at library **Places** (study rooms)
- **Admin** to manage places, students, and reservations via REST API
- **Automatic error handling** with custom exception messages
- **Data persistence** using PostgreSQL database

## Architecture Layers

### 1. **Controller Layer** (`/controller`)
Handles HTTP requests and routes them to services.

- **PlaceController**: Manages place endpoints (`/place`)
- **StudentController**: Manages student endpoints (`/student`)
- **ReservationController**: Manages reservation endpoints (`/reservation`)
- **HomeController**: Serves web pages (`/`, `/about`)

All REST controllers use `@RestController` for JSON responses.

### 2. **DTO Layer** (`/dtos`)
Data Transfer Objects used for API request/response serialization.

- **PlaceDTO**: Contains `id`, `building`, `floor`, `room`, `seat`
- **StudentDTO**: Contains `id`, `name`, `department`
- **ReservationDTO**: Contains `id`, `date`, `duration`, `isReserved`, `studentDTO`, `placeDTO`

DTOs prevent exposing internal entity relationships directly to API clients.

### 3. **Model Layer** (`/model`)
JPA entities mapped to database tables with relationships.

- **Place**: Represents a study room (One-to-Many with Reservation)
- **Student**: Represents a student (One-to-Many with Reservation)
- **Reservation**: Joins Student and Place (Many-to-One with both)

Models use Lombok `@Data` for automatic getters/setters.

### 4. **Repository Layer** (`/repository`)
Spring Data JPA interfaces for database operations.

- **PlaceRepository**: CRUD operations for Place entity
- **StudentRepository**: CRUD operations for Student entity
- **ReservationRepository**: CRUD operations for Reservation entity

All repositories extend `JpaRepository<T, Long>` for built-in query methods.

### 5. **Service Layer** (`/service`)
Business logic and validation layer between controller and repository.

Each service has an **Interface** and **Implementation**:

- **PlaceService/PlaceServiceImpl**: 
  - `getAllPlaces()` - Returns all places as DTOs
  - `getPlaceById(Long id)` - Returns place by ID
  - `createPlace(Place place)` - Creates new place
  - `updatePlace(Long id, Place place)` - Updates existing place
  - `deletePlace(Long id)` - Deletes place

- **StudentService/StudentServiceImpl**: Similar CRUD operations for students

- **ReservationService/ReservationServiceImpl**: Similar CRUD operations for reservations

Services include **error handling** and **validation**.

### 6. **Exception Layer** (`/exception`)
Centralized error handling across the application.

- **GlobalExceptionHandler**: `@ControllerAdvice` that catches custom exceptions
  - Handles `ResourceNotFoundException` → HTTP 404
  - Handles `ResourceAlreadyExistsException` → HTTP 409
  
- **ResourceNotFoundException**: Thrown when resource not found
- **ResourceAlreadyExistsException**: Thrown when duplicate resource
- **ErrorResponse**: Standard error response object with status code and message

## Database Setup

### Prerequisites
- PostgreSQL 12+ running on `localhost:5432`
- Database: `lpm_db`
- Username: `postgres`
- Password: `Jeep1234`

### Auto-initialization
The application automatically:
1. Creates database tables (via Hibernate)
2. Loads sample data from `data.sql` on startup

No manual SQL needed!

## API Endpoints

### Base URL
```
http://localhost:8080
```

### Place Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/place/all` | Get all places |
| GET | `/place/get/{id}` | Get place by ID |
| POST | `/place/add` | Create new place |
| PUT | `/place/update/{id}` | Update place |
| DELETE | `/place/delete/{id}` | Delete place |

**Example - POST /place/add:**
```json
{
  "building": "A Block",
  "floor": "1",
  "room": "101",
  "seat": 30
}
```

### Student Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/student/all` | Get all students |
| GET | `/student/get/{id}` | Get student by ID |
| POST | `/student/add` | Create new student |
| PUT | `/student/update/{id}` | Update student |
| DELETE | `/student/delete/{id}` | Delete student |

**Example - POST /student/add:**
```json
{
  "name": "John Doe",
  "department": "Computer Engineering"
}
```

### Reservation Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/reservation/all` | Get all reservations |
| GET | `/reservation/get/{id}` | Get reservation by ID |
| POST | `/reservation/add` | Create new reservation |
| PUT | `/reservation/update/{id}` | Update reservation |
| DELETE | `/reservation/delete/{id}` | Delete reservation |

**Example - POST /reservation/add:**
```json
{
  "date": "2026-03-12T10:00:00",
  "duration": "2026-03-12T11:00:00",
  "isReserved": true,
  "student": {"id": 1},
  "place": {"id": 1}
}
```

## Testing with Postman

1. **Import Collection**: Create a new collection in Postman
2. **Add Requests**: Copy endpoints from API Endpoints table above
3. **Set Headers**: Add `Content-Type: application/json` for POST/PUT requests
4. **Test Workflow**:
   - POST a new place/student (get new ID)
   - POST a reservation linking student and place
   - GET the reservation to verify
   - PUT to update reservation
   - DELETE to remove reservation

## Running the Application

### Requirements
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### Start Application
```bash
./mvnw spring-boot:run
```

Application runs on: `http://localhost:8080`

### Web Pages
- Home: `http://localhost:8080/`
- About: `http://localhost:8080/about`

## Technologies Used

- **Framework**: Spring Boot 4.0.3
- **ORM**: Hibernate JPA
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Language**: Java 17
- **Dependencies**:
  - spring-boot-starter-data-jpa
  - spring-boot-starter-webmvc
  - spring-boot-starter-thymeleaf
  - postgresql
  - lombok
  - springdoc-openapi (Swagger UI)

## Error Handling Examples

### 404 Not Found
```json
{
  "statusCode": 404,
  "message": "Place Not Found: 1"
}
```

### 409 Conflict (Duplicate)
```json
{
  "statusCode": 409,
  "message": "Place already exists: 1"
}
```

## Data Flow Example

```
Client Request (Postman)
    ↓
Controller (receives @RequestBody)
    ↓
Service Layer (validates business logic)
    ↓
Repository (queries database)
    ↓
Database (PostgreSQL)
    ↓
Repository (returns entity)
    ↓
Service (converts to DTO)
    ↓
Controller (returns JSON)
    ↓
Client Response
```

## Project Structure

```
src/
├── main/
│   ├── java/com/sau/lpm/lpm_app2/
│   │   ├── controller/          # HTTP endpoints
│   │   ├── service/             # Business logic
│   │   ├── repository/          # Database access
│   │   ├── model/               # JPA entities
│   │   ├── dtos/                # Data transfer objects
│   │   ├── exception/           # Error handling
│   │   └── LpmApp2Application   # Main class
│   └── resources/
│       ├── application.properties
│       ├── data.sql             # Sample data
│       └── templates/           # HTML pages
└── test/                        # Unit tests
```

## Future Improvements

- Add authentication/authorization (Spring Security)
- Implement search and filtering
- Add pagination for large datasets
- Add transaction management for complex operations
- Unit and integration tests
- Swagger documentation generation

---

**Version**: 0.0.1-SNAPSHOT  
**Author**: SAU Development Team  
**License**: MIT
