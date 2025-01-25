# Student Form Validation System

This is an enhanced student form validation system built using Java servlets, Bootstrap, and Docker. It demonstrates form handling, input validation, exception management, and responsive design in a web application.

## Features

- User-friendly HTML form with Bootstrap styling for student information input
- Server-side validation for name, age, and email with additional checks:
  - Name length (2-50 characters)
  - Age range (18-120 years)
  - Email domain whitelist (.edu, .np, .com.np)
  - Prevention of common placeholder names
- Custom exception handling for validation errors
- Responsive design using Bootstrap
- Dockerized application for easy deployment

## Prerequisites

- Docker
- Maven (for building the project)
- JDK 11 or later

# RUN Project in docker

`docker build -t studentformvalidation2:latest .`

`docker run --rm -it -p 8080:8080/tcp studentformvalidation2:latest`

