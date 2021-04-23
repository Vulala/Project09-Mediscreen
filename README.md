# Mediscreen

**REST API** used to communicate with the differents micro-services via **HTTP** request. <br>
In such, the application provides differents endpoints related to **CRUD** operations wich build-up **HTTP** request targeting the right micro-service.

For more information about the available endpoints, feel free to checkout the swagger's documentation.


## Stack

- **Java 15** *(should run smoothly on older versions)*
- **Spring Boot 2.4.5**
- **Spring Cloud OpenFeign**
- **Spring Boot AOP 2.4.5**
- **Spring Boot Test**
- **JaCoCo 0.8.6**
- **Swagger 3.0.0**
- **Gradle 6.8.3**



## Installation

The application is quite easy to install; it only needs to be imported in your preferred IDE as a gradle project. <br>

**Listen on port: 8080**


## Test

You can do end-to-end tests by doing HTTP request with the differents endpoints provided, f.e with Postman. <br>
If you wish to add or tweak some tests, you can find them under the traditional **src/test/java** package. <br>

Note that the integration and end-to-end tests requires the micro-service(s) to be up and running, as it is the main purpose of this API.


## Logs

The application is logged by making use of the **Aspect Oriented Programming**. <br>
*LoggingAspect* is the class using **AOP** which define the logging. <br>


## Documentation

The application uses Swagger2 to build up the documentation. <br>
To access the documentation, run the application and reach those links: <br>
[UI](http://localhost:8080/swagger-ui/) | [JSON](http://localhost:8080/v2/api-docs)



