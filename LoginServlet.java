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
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=USER_ACCOUNT";
    private static final String JDBC_USER = "DESTOP-8CERLSL\\SQLEXPRESS";
    private static final String JDBC_PASSWORD = "Vuongtuan102";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM USER_ACCOUNT WHERE CusUser = ? AND CusPass = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        session.setAttribute("name", resultSet.getString("CusName"));
                        session.setAttribute("phone", resultSet.getString("CusPhone"));
                        session.setAttribute("address", resultSet.getString("CusAdd"));
                        session.setAttribute("email", resultSet.getString("CusEmail"));
                        session.setAttribute("facebook", resultSet.getString("CusFacebook"));
                        session.setAttribute("skype", resultSet.getString("CusSkyper"));

                        response.sendRedirect("UserInforServlet");
                    } else {
                        out.println("<html><head><title>Login Error</title></head><body>");
                        out.println("<h2>Login Error</h2>");
                        out.println("<p>Invalid username or password. Please try again.</p>");
                        out.println("<a href=\"login.html\">Back to Login</a>");
                        out.println("</body></html>");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error connecting to the database.");
        }
    }
}
