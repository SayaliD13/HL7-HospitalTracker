import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// only staff can add a new patient record
public class AddPatient extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        if (session.getAttribute("staff") == null) {
            out.print("{\"success\":false,\"message\":\"Staff login required\"}");
            return;
        }

        String name = request.getParameter("name");
        String ageText = request.getParameter("age");
        String city = request.getParameter("city");
        String ward = request.getParameter("ward");

        try (Connection con = DBConnect.getConnection()) {

            int age = Integer.parseInt(ageText);

            // starting room is always Room 01 / Bed 01, staff can shift it later
            String hl7 = "PV1||I|" + ward + "^Room 01^Bed 01";

            String sql = "INSERT INTO patients (name, age, city, ward, hl7_location) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, city);
            ps.setString(4, ward);
            ps.setString(5, hl7);
            ps.executeUpdate();

            out.print("{\"success\":true}");

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\":false,\"message\":\"Could not add patient\"}");
        }
    }
}
