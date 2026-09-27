import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// this acts like a mock ADT system - it fires a new HL7 message
// that moves the patient to the ICU, and logs the change
public class ShiftRoom extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        if (session.getAttribute("staff") == null) {
            out.print("{\"success\":false,\"message\":\"Staff login required\"}");
            return;
        }

        String idText = request.getParameter("id");

        try (Connection con = DBConnect.getConnection()) {

            int id = Integer.parseInt(idText);

            String newWard = "Emergency ICU";
            String newHl7 = "PV1||I|" + newWard + "^Block-A^Bed 09";

            // step 1: update the patient's current location
            String updateSql = "UPDATE patients SET ward = ?, hl7_location = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(updateSql);
            ps.setString(1, newWard);
            ps.setString(2, newHl7);
            ps.setInt(3, id);
            ps.executeUpdate();

            // step 2: save this change in the audit log, so history is not lost
            String logSql = "INSERT INTO hl7_audit_log (patient_id, hl7_message) VALUES (?,?)";
            PreparedStatement logPs = con.prepareStatement(logSql);
            logPs.setInt(1, id);
            logPs.setString(2, newHl7);
            logPs.executeUpdate();

            String routeEn = RouteData.getEnglish(newWard);
            String routeHi = RouteData.getHindi(newWard);

            out.print("{\"success\":true,\"ward\":\"" + newWard + "\",\"room\":\"Block-A\",\"bed\":\"Bed 09\","
                    + "\"routeEn\":\"" + routeEn + "\",\"routeHi\":\"" + routeHi + "\"}");

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\":false}");
        }
    }
}
