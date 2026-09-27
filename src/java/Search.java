import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// this servlet handles patient search
// if the name matches more than one patient, all matches are sent back
// and the frontend shows them as a list (this is the duplicate name filter)
public class Search extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");

        String name = request.getParameter("name");
        if (name == null) {
            name = ""; // empty name returns everyone, used by the staff panel
        }

        StringBuilder json = new StringBuilder("[");
        boolean first = true;

        try (Connection con = DBConnect.getConnection()) {

            String sql = "SELECT * FROM patients WHERE name LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (!first) {
                    json.append(",");
                }
                first = false;

                int id = rs.getInt("id");
                String pname = rs.getString("name");
                int age = rs.getInt("age");
                String city = rs.getString("city");
                String ward = rs.getString("ward");
                String hl7 = rs.getString("hl7_location");

                // break the HL7 string into ward / room / bed
                String[] location = HL7Parser.parse(hl7);
                String room = location.length > 1 ? location[1] : "";
                String bed = location.length > 2 ? location[2] : "";

                String routeEn = RouteData.getEnglish(ward);
                String routeHi = RouteData.getHindi(ward);

                json.append("{")
                    .append("\"id\":").append(id).append(",")
                    .append("\"name\":\"").append(pname).append("\",")
                    .append("\"age\":").append(age).append(",")
                    .append("\"city\":\"").append(city).append("\",")
                    .append("\"ward\":\"").append(ward).append("\",")
                    .append("\"room\":\"").append(room).append("\",")
                    .append("\"bed\":\"").append(bed).append("\",")
                    .append("\"routeEn\":\"").append(routeEn).append("\",")
                    .append("\"routeHi\":\"").append(routeHi).append("\"")
                    .append("}");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        json.append("]");

        PrintWriter out = response.getWriter();
        out.print(json.toString());
    }
}
