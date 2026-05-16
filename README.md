# Courier Management System

Courier Management System gives all information regarding the every Courier in the systems. The system has two user role one is customer, who can track package using randomly generated number and another role is  manager who can add packages and customer details. All of them there is a primary role is admin who can add branches and location in the system

## Technology Stack

- **Backend**: Java 21, Spring Boot 3.x
- **Database**: MySQL 8.x with Flyway migrations
- **Frontend**: Thymeleaf templates with Bootstrap 5
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito
- **CI/CD**: GitHub Actions

## Development Setup

### Prerequisites
- Java 21
- Maven 3.6+
- MySQL 8.0+
- Git

### Local Development

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd courier-management-system
   ```

2. **Set up Git hooks** (optional but recommended)
   ```bash
   git config core.hooksPath .githooks
   chmod +x .githooks/pre-commit
   ```

3. **Create MySQL database**
   ```sql
   CREATE DATABASE courier_db;
   ```

4. **Configure database connection**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

6. **Access the application**
   - Home: http://localhost:8080
   - Admin login: username `admin`, password `admin123`

## Git Workflow

This project follows Git Flow branching strategy:

- `main`: Production-ready code
- `develop`: Integration branch
- `feature/*`: Feature branches
- `bugfix/*`: Bug fix branches

### Commit Convention
Follow conventional commits:
- `feat: add user authentication`
- `fix: resolve null pointer exception`
- `docs: update API documentation`
- `refactor: simplify service logic`

### Pull Request Process
1. Create feature branch from `develop`
2. Implement changes with tests
3. Create pull request to `develop`
4. Code review and CI checks
5. Squash merge after approval

## CI/CD Pipeline

The project includes automated CI/CD with GitHub Actions:

- **Build & Test**: Maven compile and test execution
- **Code Quality**: Checkstyle and SpotBugs analysis
- **Security**: Vulnerability scanning with Trivy
- **Deployment**: Automated deployment to staging/production

## Project Structure

```
src/
├── main/
│   ├── java/com/courier/management/
│   │   ├── controller/     # REST controllers
│   │   ├── entity/         # JPA entities
│   │   ├── repository/     # Data access layer
│   │   ├── service/        # Business logic
│   │   └── config/         # Configuration classes
│   └── resources/
│       ├── templates/      # Thymeleaf templates
│       ├── static/         # CSS, JS, images
│       ├── db/migration/   # Flyway migrations
│       └── application.properties
└── test/                   # Unit and integration tests
```

## API Documentation

### Authentication
- Admin login: `/login` (POST)
- Session-based authentication

### Courier Management
- `GET /couriers` - List all couriers
- `POST /couriers/add` - Add new courier
- `GET /couriers/{id}` - View courier details
- `PUT /couriers/{id}/edit` - Update courier
- `DELETE /couriers/{id}/delete` - Delete courier

### Office Management
- `GET /offices` - List all offices
- `POST /offices/add` - Add new office
- `GET /offices/{id}` - View office details

### Tracking
- `GET /couriers/track?consignmentNo=XXX` - Track courier
- `POST /tracking/update` - Update courier status

## Database Schema

The application uses Flyway for database migrations. Schema includes:

- `tbl_courier`: Courier information
- `tbl_courier_officers`: Staff details
- `tbl_offices`: Office locations
- `tbl_courier_track`: Tracking history

## Testing

Run tests with:
```bash
mvn test
```

Run integration tests with:
```bash
mvn verify
```

## Deployment

### Docker
```bash
# Build image
docker build -t courier-management .

# Run container
docker run -p 8080:8080 courier-management
```

### Production Deployment
The CI/CD pipeline handles automated deployment to staging and production environments.

## Contributing

1. Fork the repository
2. Create feature branch
3. Make changes with tests
4. Run pre-commit checks
5. Create pull request
6. Wait for review and CI checks

## License

This project is licensed under the MIT License.
