import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// this checks staff name and ID
// values are hardcoded here since there is no real staff system yet,
// this is only to demo the staff-only controls
public class StaffLogin extends HttpServlet {

    private static final String STAFF_NAME = "Admin";
    private static final String STAFF_ID = "STAFF001";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        String name = request.getParameter("name");
        String id = request.getParameter("id");

        PrintWriter out = response.getWriter();

        if (STAFF_NAME.equals(name) && STAFF_ID.equals(id)) {
            // session remembers that this browser is logged in as staff
            HttpSession session = request.getSession();
            session.setAttribute("staff", true);
            out.print("{\"success\":true}");
        } else {
            out.print("{\"success\":false}");
        }
    }
}
