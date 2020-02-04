# create a new build stage from the Java image
# which has java installed already
FROM java:8

# change the working directory to where the application
# is going to be installed
WORKDIR /opt/bookmanager

# copy the JAR file that was created in the previous
# build stage to the application folder in this build stage
COPY /build/target/*.jar app.jar

# create an entrypoint to run the application
ENTRYPOINT ["/usr/bin/java", "-jar", "app.jar"]
