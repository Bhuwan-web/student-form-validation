package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Arrays;
import java.util.List;

@WebServlet("/studentForm")
public class StudentFormServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(StudentFormServlet.class.getName());
    private static final List<String> PLACEHOLDER_NAMES = Arrays.asList("john doe", "jane doe");
    private static final List<String> ALLOWED_EMAIL_DOMAINS = Arrays.asList("edu", "np", "com.np");

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String ageStr = request.getParameter("age");
        String email = request.getParameter("email");

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Student Form Result</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body class='bg-light'>");
        out.println("<div class='container mt-5'>");

        try {
            validateForm(name, ageStr, email);
            displayStudentInfo(out, name, ageStr, email);
        } catch (ValidationException e) {
            displayErrors(out, e.getErrors());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error occurred", e);
            out.println("<div class='alert alert-danger' role='alert'>");
            out.println("<h4 class='alert-heading'>An unexpected error occurred. Please try again later.</h4>");
            out.println("</div>");
        }

        out.println("</div>");
        out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js'></script>");
        out.println("</body>");
        out.println("</html>");
    }

    private void validateForm(String name, String ageStr, String email) throws ValidationException {
        StringBuilder errors = new StringBuilder();

        if (name == null || name.trim().isEmpty()) {
            errors.append("Name is required.<br>");
        } else if (name.trim().length() < 2) {
            errors.append("Name must be at least 2 characters long.<br>");
        } else if (name.trim().length() > 50) {
            errors.append("Name must not exceed 50 characters.<br>");
        } else if (PLACEHOLDER_NAMES.contains(name.trim().toLowerCase())) {
            errors.append("Please provide your real name.<br>");
        }

        try {
            int age = Integer.parseInt(ageStr);
            if (age < 18) {
                errors.append("You must be at least 18 years old.<br>");
            } else if (age > 120) {
                errors.append("Please enter a valid age (maximum 120 years).<br>");
            }
        } catch (NumberFormatException e) {
            errors.append("Invalid age format.<br>");
        }

        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.append("Invalid email format.<br>");
        } else {
            String domain = email.substring(email.lastIndexOf(".") + 1).toLowerCase();
            if (!ALLOWED_EMAIL_DOMAINS.contains(domain)) {
                errors.append("Only .edu, .np, com.np email domains are allowed.<br>");
            }
        }

        if (errors.length() > 0) {
            throw new ValidationException(errors.toString());
        }
    }

    private void displayStudentInfo(PrintWriter out, String name, String ageStr, String email) {
        out.println("<div class='card'>");
        out.println("<div class='card-body'>");
        out.println("<h2 class='card-title'>Student Information</h2>");
        out.println("<p class='card-text'><strong>Name:</strong> " + name + "</p>");
        out.println("<p class='card-text'><strong>Age:</strong> " + ageStr + "</p>");
        out.println("<p class='card-text'><strong>Email:</strong> " + email + "</p>");
        out.println("</div>");
        out.println("</div>");
    }

    private void displayErrors(PrintWriter out, String errors) {
        out.println("<div class='alert alert-danger' role='alert'>");
        out.println("<h4 class='alert-heading'>Validation Errors</h4>");
        out.println("<p>" + errors + "</p>");
        out.println("</div>");
        out.println("<a href='index.html' class='btn btn-primary'>Go back to form</a>");
    }

    private static class ValidationException extends Exception {
        private final String errors;

        public ValidationException(String errors) {
            this.errors = errors;
        }

        public String getErrors() {
            return errors;
        }
    }
}

