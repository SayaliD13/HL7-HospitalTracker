// this file reads a mock HL7 PV1 string and pulls out the useful parts
// example input: PV1||I|General Ward^Room 04^Bed 12
public class HL7Parser {

    public static String[] parse(String hl7String) {
        // first split by | to get the last section (location part)
        String[] pipeParts = hl7String.split("\\|");
        String locationPart = pipeParts[pipeParts.length - 1];

        // then split that part by ^ to get ward, room, bed separately
        return locationPart.split("\\^");
    }
}
