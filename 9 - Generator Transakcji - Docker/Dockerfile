FROM openjdk:8-jdk-alpine
ADD transaction-generator.jar /
run mkdir /storage
WORKDIR /
CMD ["java", "-jar", "transaction-generator.jar", "/storage/generator.properties"]
