package devmaster.edu.vn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Employee")
public class Employee extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=Employee";
    private static final String JDBC_USER = "DESTOP-8CERLSL\\SQLEXPRESS";
    private static final String JDBC_PASSWORD = "Vuongtuan102";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Employee Search</title></head><body>");
        out.println("<h2>Employee Search</h2>");
        out.println("<form action=\"Employee\" method=\"post\">");
        out.println("Employee Name: <input type=\"text\" name=\"emp_name\"><br>");
        out.println("Employee Number: <input type=\"text\" name=\"emp_no\"><br>");
        out.println("Salary: <input type=\"text\" name=\"salary\"><br>");
        out.println("<input type=\"submit\" value=\"Search\">");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String empName = request.getParameter("emp_name");
        String empNo = request.getParameter("emp_no");
        String salary = request.getParameter("salary");

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM employee WHERE EMP_NAME LIKE ? AND EMP_NO LIKE ? AND SALARY >= ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + empName + "%");
                preparedStatement.setString(2, "%" + empNo + "%");
                preparedStatement.setFloat(3, Float.parseFloat(salary));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    out.println("<html><head><title>Search Results</title></head><body>");
                    out.println("<h2>Search Results</h2>");
                    out.println("<table border=\"1\"><tr><th>Employee Name</th><th>Employee Number</th><th>Salary</th></tr>");

                    while (resultSet.next()) {
                        out.println("<tr><td>" + resultSet.getString("EMP_NAME") + "</td><td>"
                                + resultSet.getString("EMP_NO") + "</td><td>" + resultSet.getFloat("SALARY") + "</td></tr>");
                    }

                    out.println("</table>");
                    out.println("<br><a href=\"Employee\">Back to Search</a>");
                    out.println("</body></html>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error connecting to the database.");
        }
    }
}
