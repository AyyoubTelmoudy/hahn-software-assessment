# IT Support System

## Project Overview

The **IT Support System** is an internal application designed to help employees submiting issues related to it material and software. 
It gives them the possibility to create tickets and the it-support members process it.

### Key Features
1. **Tickets management**
- Manages the following attributes:
    - Ticket ID
    - Category
    - Status
    - Title
    - Description
    - Priority

2. **Role-Based Permissions**
- **Employees**:
    - Create and view their own tickets.
- **It-support members**:
    - Update ticket status and add comments to tickets.

3. **Audit Trail**
- Logs all changes of ticket status and comments.

4. **Search and Filtering**
- Allows searching by:
    - Ticket ID
    - Ticket status

5. **Swagger Documentation**
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

![front1.PNG](assets%2Ffront1.PNG)
![front2.PNG](assets%2Ffront2.PNG)
![front3.PNG](assets%2Ffront3.PNG)
![front4.PNG](assets%2Ffront4.PNG)

3. **Docker Integration**:
- Backedn dockerized for consistent deployment.
- H2 database included within the Docker setup for seamless testing.

4. **Testing**:
- Comprehensive unit and integration tests written using **JUnit** and **Mockito**.
- Created a **Postman Collection** to validate API endpoints.

---
demo_video%2Fdemo.mkv
## Demo Video

Check out the demo video of the project here: [Video Demo](https://github.com/AyyoubTelmoudy/hahn-software-assessment/tree/main/demo_video)



## Instructions for Running the Application

### Prerequisites
- **Java 17**
- **Docker** installed on your machine.
- **Postman** (optional, for API testing).

### Swagger
- **Swagger** http://localhost:9090/swagger-ui.html


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

3. Check swagger on http://localhost:9090/swagger-ui.html:


4. click on the executable jar : swing-ui.jar to run the ui
