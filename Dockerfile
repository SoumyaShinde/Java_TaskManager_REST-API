FROM openjdk:22-jdk
#Coping the data from src to dest
ADD target/taskmanager.jar taskmanager.jar
#creating image
ENTRYPOINT ["java", "-jar" ,"/taskmanager.jar"]