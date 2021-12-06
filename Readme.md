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
    - what id did you get at auth0?
    - where is the implementation of the database access methods?
    - why are there two different application files?
    - are the logs meaningful?
- compile and run application from the command line (hint: activate the spring local profile )
- get application to run in IntelliJ IDEA (or some other IDE) with the local profile
- create a docker container out of jar file and run it locally (using again the local profile)

Known errors and issues (there certainly are more, we'd be happy if you share your findings with us):

- the application is logging too much data - reduce the amount of logs
- the update operation is currently broken and results in a 500 error even when provided with valid parameters and
  should be fixed
- when a customer id is not found in the DB the application should return a 404 error instead of 500
- when performing an update and changing the customer's email address, the new address is never persisted, but it should
  be
- when performing the getuser operation private customer data is logged in plaintext to the log, it should be anonymised

Possible extensions (only describe how you would implement them, no actual implementation required):

- introduce a method for creating new customers
- introduce a method for updating certain attributes
- introduce access restrictions - allow access only for certain authenticated principals, allow certain operations only
  for certain authenticated principals
- testing & documentation - what are good candidates for unit tests, integration tests, what needs to be documented?
- introduce additional logging and error handling where necessary
