# Task Manager_MeetUsAr
# Task Manager (Spring Boot)
## Requirements implemented
- POST /auth/register
- POST /auth/login -> returns JWT token { accessToken }
- POST /auth/logout -> invalidates token in in-memory blacklist
- POST /tasks (auth required)
- GET /tasks (auth required)
- PUT /tasks/{id} (auth required) - update status
- DELETE /tasks/{id} (auth required)

## Run
Build: `mvn clean package`

H2 console: http://localhost:8080/h2-console (jdbc url: `jdbc:h2:mem:taskdb`)

## Example curl

**Register:**

curl --location 'http://localhost:8080/auth/register' \
--header 'Content-Type: application/json' \
--data '{"email":"userexample.com","password":"mypassword","name":"John Doe"}'

**Login:**

curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{"email":"user@example.com","password":"mypassword"}'

**Create task:**

curl --location 'http://localhost:8080/tasks' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer ' \
--data-raw '{"title":"My Task","description":"desc","status":"open"}'


**List tasks:**

curl --location 'http://localhost:8080/tasks' \
--header 'Authorization: Bearer ' \

**Update status:**

curl --location --request PUT 'http://localhost:8080/tasks/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer ' \
--data '{"status":"done"}'


**Delete Task:**

curl --location --request DELETE 'http://localhost:8080/tasks/1' \
--header 'Authorization: Bearer ' \

**Logout:**

curl --location --request POST 'http://localhost:8080/auth/logout' \
--header 'Authorization: Bearer ' \


**All tests are located under**:
src/test/java/com/example/taskmanager/

**Main test class:**
AuthControllerTest.java
