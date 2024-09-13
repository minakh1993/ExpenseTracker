FROM openjdk:17-alpine

ADD ./target/ExpenseTracker-*.jar /app/

RUN mv /app/*.jar /app/app.jar && chmod -R 777 /app

WORKDIR /app/

CMD ["java", "-jar", "app.jar"]
