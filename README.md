## Smart Campus Sensor and Room Management API
5COSC022W - Client Server Architectures
University of Westminster
w20066301
Ashley Annor

---

### Overview
This project is a RESTful Smart Campus API built using JAX-RS. This was developed to manage rooms, sensors and its readings within a university campus.
The API allows clients to view rooms, register sensors inside rooms, filter sensors by type and store historical readings for each sensor. This also includes custom exception handling and logging to improve reliability and make the service easier to manage as well as more room for further expansion should they wish to do so.
This system uses data structures like 'HashMap' and 'ArrayList' instead of a database to be in line with the coursework requirements.


### Project Structure

```
smart-campus-api/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── mycompany/
        │           └── w2006630_ashleyannor_smartroomcampus/
        │               ├── JAXRSConfiguration.java
        │               ├── exceptions/
        │               │   ├── LinkedResourceNotFoundException.java
        │               │   ├── RoomNotEmptyException.java
        │               │   └── SensorUnavailableException.java
        │               ├── filters/
        │               │   └── LoggingFilter.java
        │               ├── mappers/
        │               │   ├── LinkedResourceNotFoundExceptionMapper.java
        │               │   ├── RoomNotEmptyExceptionMapper.java
        │               │   ├── SensorUnavailableExceptionMapper.java
        │               │   └── ThrowableExceptionMapper.java
        │               ├── models/
        │               │   ├── ErrorResponse.java
        │               │   ├── Room.java
        │               │   ├── Sensor.java
        │               │   └── SensorReading.java
        │               ├── resources/
        │               │   ├── DiscoveryResource.java
        │               │   ├── RoomResource.java
        │               │   ├── SensorReadingResource.java
        │               │   └── SensorResource.java
        │               └── store/
        │                   └── DataStore.java
        ├── resources/
        │   └── META-INF/
        └── webapp/
            ├── META-INF/
            │   └── context.xml
            ├── WEB-INF/
            │   ├── beans.xml
            │   └── web.xml
            └── index.html

```


## How to Build and Run the Project

### Checklist of Software

- Java JDK 17 from adoptum.net
- Apache Maven - from maven.apache.org
- Apache Tomcat 10 or higher from tomcat.apache.org
- Apache Netbeans 18 or higher (The software used to build the API)

### Step 1: Clone The Repo

```bash
git clone https://github.com/ashmarine/smart-campus-api.git
```

### Step 2: Build The Project

After cloning the repository, open the project folder using NetBeans and then right click the project, click "Clean and Build" followed by the "Run" button.

If everything is successful, Maven should be downloading the dependencies and building the project.

### Step 3: Run The Project

Right click the project and select "Run"
Wait for the server to deploy the application
Open Postman or a browser window
Use the base URL: 

```
http://localhost:8080/w2006630_AshleyAnnor_SmartRoomCampus/api/v1
```

If you see this:
```
{"name":"Smart Campus Sensor and Room Management API","version":"v1","status":"online","adminContact":{"team":"Smart Campus Support","email":"smartcampussupport@westminster.ac.uk"},"resources":{"self":"/api/v1","rooms":"/api/v1/rooms","sensors":"/api/v1/sensors"}}
```
then everything should be working.


## Sample Curl Commands
Here are some requests you could try on the API. These have been tested through out development of this project.

### Example 1: Getting the discovery endpoint
```bash
curl http://localhost:8080/w2006630_AshleyAnnor_SmartRoomCampus/api/v1
```

### Example 2: Get all rooms
```bash
curl http://localhost:8080/w2006630_AshleyAnnor_SmartRoomCampus/api/v1/rooms
```

### Example 3: Create a room
```bash
curl -X POST http://localhost:8080/w2006630_AshleyAnnor_SmartRoomCampus/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"LIB-301","name":"Library Quiet Study","capacity":40}'
```

### Example 4: Get all sensors
```bash
curl http://localhost:8080/w2006630_AshleyAnnor_SmartRoomCampus/api/v1/sensors
```

### Example 5: Create a sensor:
```bash
curl -X POST http://localhost:8080/w2006630_AshleyAnnor_SmartRoomCampus/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"CO2-001","type":"CO2","status":"ACTIVE","currentValue":415.5,"roomId":"LIB-301"}'
```

### Example 6: Filter sensors by type
```bash
curl "http://localhost:8080/w2006630_AshleyAnnor_SmartRoomCampus/api/v1/sensors?type=CO2"
```

### Example 7: Add a reading
```bash
curl -X POST http://localhost:8080/w2006630_AshleyAnnor_SmartRoomCampus/api/v1/sensors/CO2-001/readings \
-H "Content-Type: application/json" \
-d '{"id":"READ-001","timestamp":1713888000000,"value":430.2}'
```

### Example 8: Get sensor readings
```bash
curl http://localhost:8080/w2006630_AshleyAnnor_SmartRoomCampus/api/v1/sensors/CO2-001/readings
```

## Report

## Project & Application Configuration

Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and
synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions

When working with JAX-RS, its resourse classes are usually created again for each new request. This means that the server should normally use a new object when a client sends a request.

Because of how this works, data deemed as important should not be stored as a normal instance variable inside the resource class. For this coursework specifically, the data needs to be constantly available between requests. Because of this requirement, I have deemed it necessary to store the data in data structures like a HashMap.

Because the data is required to be constantly available, it should also be handled carefully as creating multiple requests could attempt to change it at the same time. If this potential issue is not handled properly, errors or incinsistent results could appear.

## The "Discovery" Endpoint

Question: Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?

The use of Hypermedia typically  means that the API should give links or routes to other resources inside its own responses which allows the client to better understand what in that route could be accessed next.

This is very useful because this means that the client should not need to rely on anything external and it could use the links that are returned by the API. This is beneficial when any further expansion is made in the future, it could quickly be reflected and maintained.

## Room Resource Implementation

Question: When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.

When you are returning the room IDs, you sre using less bandwidth because the response provided is smaller. However returning full room objects gives the client more information at a single time like the room name and capacity which removes the need to make a larger number of requests. A result of this could make the response larger.

I made a decision to return full room objects because it makes the endpont easier to work with and the client gets all the information in one go.

## Room Deletion and Safety Logic

Question: Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.

DELETE is idempotent in my API. If a client sends a DELETE request for a specific room, the room should be deleted successfully. Sending another DELETE request should not delete anything more as its already removed and provide a not found response instead. This should satisfy the idempotent requirement because nothing further happens after the first deletion.

## Sensor Resourse and Integrity

Question: We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?

The POST method implemented uses @Consumes(MediaType.APPLICATION_JSON). When this is implemented, the API should be expecting a JSON input. In a scenario where a client sends data that is in a different format than the API is expecting, JAX-RS should reject the request and the server will not have the functionality to convert therequest into a Java object. When using @Consumes, the expected perception of the API should be that it is clear and self explanatory to use and only uses the format it was designed for.

## Filtered Retrieval and Search

Question: You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/vl/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?

When using a query parameter like /sensors?type=CO2, it is generally considered better in the scenario of filtering because it shows that the client is still accessing the same sensors collection but with a filter applied. If the type was part of the URL path, it could be percieved as a different path instead of a filtered verdion of the same collection. This is what makes query parameters feel clarer and more flexible to use, especially in the future where more filters could be added.

## The Sub-Resource Locator Pattern

Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?

The Sub-Resource Locator patterns intended use is to assist in keeping the API organised by moving nested logic into a separate class. For example, instead of putting all sensor and, reading endpoints into one large class, the reading related logic will be handled by its own SensorReadingResource class which will allow for easier understanding, maintenance and potential expansion. In the case of larger APIS, this is usefu; because clutter wont be a reoccuring issue and all related functionality is grouped together.

## Dependency Validation

Question: Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?

HTTP code 422 is more suitable to use than code 404 because the request made is valid, but a value in the JSON body is not. For this scenario, the Sensor is linked to a roomId that does not exist. Code 404 typically means that the URL itself or the endpoind could not be found whereas in this scenario, the endpoint does exist making code 404 misleading.

## The Global Safety Net

Question: From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?

In the scenario of a Java stack trace being exposed, there is a very high risk and likelihood of internal details about the API being leaked with enough determination or if it was a planned attack. An attacker could study how the API works and look for vulnerabilities and abuse them for many black hat activites like ransom. In this case it is more favourable to retern a general error than for the possibility of internal systems being leaked.

## API Request and Response Logging Filters

Question: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?

Using JAX-RS filters for logging is more advantageous because it can apply to many endpoints. This allows for request and response logging to all be handled in one place making the code cleaner and easier to identify.