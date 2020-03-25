FROM java:8
COPY ./target/OrderTaker-0.0.1-SNAPSHOT.jar OrderTaker-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","OrderTaker-0.0.1-SNAPSHOT.jar"]