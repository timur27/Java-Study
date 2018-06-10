FROM openjdk:8-jdk-alpine
ADD build/libs/transaction-generator.jar /
run mkdir /storage
ADD generator.properties /storage
ADD items.csv /storage
WORKDIR /
CMD ["java", "-jar", "transaction-generator.jar", "storage/generator.properties"] 
