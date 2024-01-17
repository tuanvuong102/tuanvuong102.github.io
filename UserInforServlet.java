package devmaster.edu.vn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UserInforServlet")
public class UserInforServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            String name = (String) session.getAttribute("name");
            String phone = (String) session.getAttribute("phone");
            String address = (String) session.getAttribute("address");
            String email = (String) session.getAttribute("email");
            String facebook = (String) session.getAttribute("facebook");
            String skype = (String) session.getAttribute("skype");

            out.println("<html><head><title>User Information</title></head><body>");
            out.println("<h2>User Information</h2>");
            out.println("<p>Username: " + username + "</p>");
            out.println("<p>Name: " + name + "</p>");
            out.println("<p>Phone: " + phone + "</p>");
            out.println("<p>Address: " + address + "</p>");
            out.println("<p>Email: " + email + "</p>");
            out.println("<p>Facebook: " + facebook + "</p>");
            out.println("<p>Skype: " + skype + "</p>");
            out.println("<br><a href=\"login.html\">Logout</a>");
            out.println("</body></html>");
        } else {
            response.sendRedirect("login.html");
        }
    }
}
