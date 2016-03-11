package combinedModel;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by kumars on 3/11/2016.
 */
public class CreateJSONLikeClass {
    Map<String, FinalModel> allEmpDetails = Discrepancy.EmpCombinedMap;
    Map<String, JSONModelForWeb> newEmpDetails = new TreeMap<>();

    public void generate() {
        JSONModelForWeb jsonModelForWeb;
        for (FinalModel f : allEmpDetails.values()) {
            {
                jsonModelForWeb = new JSONModelForWeb(f);
                newEmpDetails.put(f.getEmpId(), jsonModelForWeb);
            }
        }
    }

    public void displayJSONFiles() {
        newEmpDetails.values().forEach(JSONModelForWeb::display);
    }

    public void toJsonFile() {
        ObjectMapper mapper = new ObjectMapper();
        // For testing
        Map<String, JSONModelForWeb> user = newEmpDetails;

        try {
            File jfile = new File(".\\JSON files\\WebDetails.json");
            // Convert object to JSON string and save into file directly
            mapper.writeValue(jfile, user);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
