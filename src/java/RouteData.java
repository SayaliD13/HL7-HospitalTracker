import java.util.HashMap;
import java.util.Map;

// this file stores the walking directions for every ward
// route depends on the WARD, not on the patient, so any new patient
// added to an existing ward automatically gets the correct route
public class RouteData {

    private static Map<String, String[]> routes = new HashMap<>();

    static {
        // each ward has { english route, hindi route }
        routes.put("Emergency ICU", new String[]{
            "Ground floor. From the reception go left and follow the Emergency sign.",
            "ग्राउंड फ्लोर। रिसेप्शन से बाएं जाइए और इमरजेंसी साइन का पालन करें।"
        });

        routes.put("General Ward", new String[]{
            "Ground floor. From the reception go right, near Lift A.",
            "ग्राउंड फ्लोर। रिसेप्शन से दायें जाइए, लिफ्ट A के पास।"
        });

        routes.put("Orthopedic Ward", new String[]{
            "Take Lift A to the 1st floor. The ward is on the right of the nursing desk.",
            "लिफ्ट A से पहली मंजिल जाइए। वार्ड नर्सिंग डेस्क के दायें है।"
        });

        routes.put("Maternity Ward", new String[]{
            "Take Lift A to the 2nd floor and turn left after the lift.",
            "लिफ्ट A से दूसरी मंजिल जाइए और लिफ्ट के बाद बायें मुड़िए।"
        });

        routes.put("Cardiology Ward", new String[]{
            "Take Lift B to the 2nd floor and turn right after the lift.",
            "लिफ्ट B से दूसरी मंजिल जाइए और लिफ्ट के बाद दायें मुड़िए।"
        });
    }

    public static String getEnglish(String ward) {
        String[] r = routes.get(ward);
        return (r != null) ? r[0] : "Route not set for this ward.";
    }

    public static String getHindi(String ward) {
        String[] r = routes.get(ward);
        return (r != null) ? r[1] : "इस वार्ड के लिए रूट उपलब्ध नहीं है।";
    }
}
