import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// only staff can delete a patient record
public class DeletePatient extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        if (session.getAttribute("staff") == null) {
            out.print("{\"success\":false,\"message\":\"Staff login required\"}");
            return;
        }

        String idText = request.getParameter("id");

        try (Connection con = DBConnect.getConnection()) {

            String sql = "DELETE FROM patients WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idText));
            ps.executeUpdate();

            out.print("{\"success\":true}");

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\":false}");
        }
    }
}
