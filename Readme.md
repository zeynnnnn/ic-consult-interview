Welcome to the iC Consult coding assignment!

Disclaimer: This application lacks tests and documentation on purpose (see bellow). We actually value meaningful tests
and good documentation. Normally for us security and data protection are of the utmost importance. Next come
maintainability and performance. For the code presented here these values have been broken on purpose in order to create
room for discussion during the interview process.

This project represents a very minimalistic user management application with an in memory database and a somewhat
RESTful API. The API is described in swagger ui and protected by OAuth 2.0. There are several faults in the application
which you might want to fix and if you find the time you might wish to implement some additional functionality. Even if
you don't get far, please get familiar with teh codebase as it will be discussed during the code interview.

For the OAuth 2.0 integration with auth0 in the swagger ui, please use the values provided in the local profile. If you
change any of the swagger/oauth config the swagger ui might break.

Tasks and assignments (in no particular order):

- get familiar with the code base and understand what happens where
    - how many test accounts are provided in the initial state of the database? 
      - 3
          (1, '16978a6c-27a6-48f2-a865-147def8d092e', 'Testo', 'Testerson', 'tt@testing.com'),
          (2, '7c2297b1-0f24-4a4d-8d9e-709e0c46254e', 'Testa', 'Tester', 'atset@test.me'),
          (3, 'fb44755d-7c91-49c7-b742-0ecfb475e26e', 'Testi', 'MacTest', 'test@test.test');
    - what id did you get at auth0?
      - JSON Web Token
      - Bearer Token
      'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImhPMF84V05OcFBFczBTdlhVZVI1dCJ9.eyJpc3MiOiJodHRwczovL2ljYy1kZW1vLmV1LmF1dGgwLmNvbS8iLCJzdWIiOiJnb29nbGUtb2F1dGgyfDExMjQwMjU4Mzg5NDA2MjI4NTAzNyIsImF1ZCI6ImN1c3RvbWVyIiwiaWF0IjoxNjc1ODg4MTE5LCJleHAiOjE2NzU5NzQ1MTksImF6cCI6IjZwRFdNTHR3S0owdk5YRlpzZHhiQnZ6VEI3OWdGWnBHIn0.uL3ypGIFsAe1hO0sAnjMcCPjYQNe2SC5Rgc-aXWtuMIFJ3tXsuZFUcOesHip0yBbbPHOlGzltjPSE2pQ8SHUAzTffPjErQBI2wdmpd_P3LesR0gd3XPsdwN5UfLUcoNDb_CqkliyBm9k2N5PuFDiLgN1aKNNYvdtQagpF5ur3kSm_Vy8xuTGcy8qnPZBtqhImONtdPAH65QB9jbhOatIaKvugytinqYUdyleRgoM7zVSmtoa5SSvG0BZ_zh29WHQBZTWJEzf6KjKmjHtkC1bfSoktV6fmWJzckDT98Qv3nDrSVhqRq78KXdfOUjtBNPevDNXkR3WQDZ0z4zJWP8dNg' \
    - where is the implementation of the database access methods?
      - implemented in : public class DefaultCustomerManagementService implements CustomerManagementService 
      - usage: com.icconsult.interview.usermanagement.api.endpoint getCustomer and putCustomer
    - why are there two different application files?
      - application and application-local :
      - jdbc:h2:mem:customerdb and  url: jdbc:h2:mem:userdb
      - Possible cross-origin (CORS) issue
      - are the logs meaningful?
        - Trace logs too many but all makes sense
- compile and run application from the command line (hint: activate the spring local profile )
  - mvn clean  spring-boot:run  -D"spring-boot.run.profiles"=local
  - mvn clean  spring-boot:run  -D"spring-boot.run.profiles"=default

- get application to run in IntelliJ IDEA (or some other IDE) with the local profile
  - added to pom.xml : <configuration>
    <profiles>
    <profile>
    <id>local</id>
    <activation>
    <activeByDefault>true</activeByDefault>
    </activation>
    <properties>
    <spring.profiles.active>local</spring.profiles.active>
    </properties>
    </profile>
    </profiles>
    </configuration> 
  - on run time via a JVM system parameter:  spring.profiles.active=local
- create a docker container out of jar file and run it locally (using again the local profile)
  - Created Dockerfile
  - docker build . -t usermanagement
  -  docker run -p 8888:8888 -t usermanagement:latest

Known errors and issues (there certainly are more, we'd be happy if you share your findings with us):

- the application is logging too much data - reduce the amount of logs
  - changed the pom, logging level root to ERROR
- the update operation is currently broken and results in a 500 error even when provided with valid parameters and
  should be fixed
  - out of bound error is fixed (-1 added)
- when a customer id is not found in the DB the application should return a 404 error instead of 500
  - changed the globalexceptionhandler and updateCustomer
- when performing an update and changing the customer's email address, the new address is never persisted, but it should
  be
  - added setEmail to updateCustomer
- when performing the getuser operation private customer data is logged in plaintext to the log, it should be anonymised
  - added anonymizeString to getuser method

Possible extensions (only describe how you would implement them, no actual implementation required):

- introduce a method for creating new customers
  - public CustomerResponse addCustomer(String email,String name, String lastName, String admin)  //check defaultcustomermanagementservice
- introduce a method for updating certain attributes
  - same thing as updateCustomer but particular for each attributes
- introduce access restrictions - allow access only for certain authenticated principals, allow certain operations only
  for certain authenticated principals
  - token differences
- testing & documentation - what are good candidates for unit tests, integration tests, what needs to be documented?
  - id looks random
- introduce additional logging and error handling where necessary
