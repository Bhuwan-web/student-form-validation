# Use Tomcat 9 with JDK 11 as the base image
FROM tomcat:9-jdk11-openjdk-slim

# Remove default Tomcat applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the compiled WAR file into the Tomcat webapps directory
COPY target/student-form-validation.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]

