# Employee Records Management System

## Project Overview

The **Employee Records Management System** is an internal application designed to centralize employee data management across departments. It provides secure access controls, detailed audit trails, advanced search and filtering capabilities, and reporting features to streamline HR and administrative workflows.

### Key Features
1. **Employee Data Management**
- Manages the following attributes:
    - Full Name
    - Employee ID
    - Job Title
    - Department
    - Hire Date
    - Employment Status
    - Contact Information
    - Address

2. **Role-Based Permissions**
- **HR Personnel**:
    - Full CRUD (Create, Read, Update, Delete) operations on all employee data.
- **Managers**:
    - Limited editing rights for employees within their department.
- **Administrators**:
    - Full system access, including configuration settings and user permission management.

3. **Audit Trail**
- Logs all changes to employee records with detailed information about the user who made the modification and a timestamp.

4. **Search and Filtering**
- Allows searching by:
    - Name
    - Employee ID
    - Department
    - Job Title
- Provides filtering based on:
    - Employment Status
    - Department
    - Hire Date

5. **Validation**
- Ensures:
    - Valid email formats.
    - Unique Employee IDs to prevent duplication.

6. **Reporting**
- Generates basic reports for insights into employee data.

7. **Swagger Documentation**
- Comprehensive documentation of the API endpoints using **Swagger OpenAPI**, enabling developers to test and understand the backend functionality easily.

---

## Progress Overview

### Completed Tasks
1. **Backend Development**:
- Built using **Spring Boot**, ensuring scalability and modularity.
- CRUD operations implemented with robust validation and detailed exception handling to ensure
  consistent and informative responses for all API endpoints.
- Integrated an in-memory **H2 database** for rapid development and testing, with detailed logging
  and error messages.
- Added detailed **Swagger documentation** for all API endpoints, including parameter descriptions,
  response types and examples.
- Exception handling and validation for all API endpoints.


![swagger.png](assets%2Fswagger.png)

2. **Frontend Development**:
- Developed a responsive desktop UI using **Swing**.
- Designed with **GridBagLayout** for an intuitive user experience.

![front1.png](assets%2Ffront1.png)
![front2.png](assets%2Ffront2.png)

3. **Docker Integration**:
- Application fully dockerized for consistent deployment.
- H2 database included within the Docker setup for seamless testing.

![docker.png](assets%2Fdocker.png)

4. **Testing**:
- Comprehensive unit and integration tests written using **JUnit** and **Mockito**.
- Created a **Postman Collection** to validate API endpoints.

![test.PNG](assets%2Ftest.PNG)
---

## Demo Video

Check out the demo video of the project here: [Video Demo](https://drive.google.com/file/d/1VTG2NWFF-MW0oMj0__-MeGuRZyKPkbcV/view?usp=sharing)


## Next Steps
- **Advanced Search & Filtering**:
    - Implement multi-criteria search capabilities.
- **Enhanced Audit Trail**:
    - Add detailed logs for API access and user actions.
- **Reports Module**:
    - Build report generation templates for exporting employee data.
- **Database Migration**:
    - Transition to **Oracle SQL** for production environments.
- **UI Enhancements**:
    - Improve the Swing-based interface with better visual elements and user-friendly features.

---

## Instructions for Running the Application

### Prerequisites
- **Java 17**
- **Docker** installed on your machine.
- **Postman** (optional, for API testing).

### Swagger
- **Swagger** http://localhost:8080/swagger-ui.html


### Important Note for GUI (Swing) Application Users

If you plan to run the Swing-based GUI application on Windows using WSL2 or on Linux, you need to install and configure an X server such as **XLaunch** to enable graphical display.

#### Steps to Install and Configure XLaunch (Windows/WSL2):
1. Download and install **XLaunch** from [SourceForge](https://vcxsrv.com/).
2. Run the XLaunch setup with the following settings:
    - **Display Settings**: Select "Multiple windows."
    - **Display Number**: Leave as `0`.
    - **Additional Settings**: Enable "Disable access control" to allow connections from any client.
3. Start XLaunch.

### Steps to Run Locally
1. Clone the repository:
   ```bash
   git clone <repository_url>
   cd employee-records-management
2. Run The Project:
   ```bash
    docker-compose up --build
