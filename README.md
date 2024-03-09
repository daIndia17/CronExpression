
# Cron Expression Parser

This Parser helps to parse cron expression resulting in number of time the cron has run in minutes, hours , day-of-month, months, day-of-week


To run this project, Please follow the steps below :

* Clone this repository in any IDE (eg : Intellij, Eclipse) : `git clone https://github.com/daIndia17/CronExpression.git`

* Run this command : `mvn clean install -DskipTests`

* Run the project using either supported IDE run button or one can also run manually by using this command `mvn spring-boot:run`

* Use this curl for sending the post request.

```
curl --location --request POST 'http://localhost:8090/cron/expressionParser' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cronExpression": "0 0 10/5 * ?"
}' 
```

JAVA VERSION : 17



