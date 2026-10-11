package org.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/display")
public class display extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {

            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL database
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/employee",
                    "root",
                    "system"
            );

            // Create Statement
            Statement st = con.createStatement();

            // Execute SQL query
            ResultSet rs = st.executeQuery("SELECT * FROM employee");

            // HTML Output
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Employee Details</title>");
            out.println("</head>");

            out.println("<body>");

            out.println("<h2>Employee Details</h2>");

            out.println("<table border='1'>");

            out.println("<tr>");
            out.println("<th>Employee ID</th>");
            out.println("<th>Branch</th>");
            out.println("<th>Salary</th>");
            out.println("</tr>");

            // Display records
            while (rs.next()) {

                out.println("<tr>");

                out.println("<td>"
                        + rs.getInt("employee_id")
                        + "</td>");

                out.println("<td>"
                        + rs.getString("branch")
                        + "</td>");

                out.println("<td>"
                        + rs.getDouble("salary")
                        + "</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("</body>");
            out.println("</html>");

            // Close connection
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {

            out.println("<h3>Error occurred:</h3>");
            out.println("<p>" + e.getMessage() + "</p>");

            e.printStackTrace();
        }
    }
}
