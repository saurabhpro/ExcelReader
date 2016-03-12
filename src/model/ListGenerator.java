package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import combinedModel.Discrepancy;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Saurabh on 3/12/2016.
 */
public abstract class ListGenerator {
    protected final Map<String, FinalModel> allEmpDetails = Discrepancy.EmpCombinedMap;
    protected final Map<String, JSONModelForWeb> filteredEmpDetails = new TreeMap<>();

    public abstract void generate();

    public void displayOnConsole() {
        filteredEmpDetails.values().forEach(JSONModelForWeb::displayAllDates);
    }

    public void createJSONList(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        // For testing
        Map<String, JSONModelForWeb> user = filteredEmpDetails;

        try {
            File jfile = new File(".\\JSON files\\" + fileName + ".json");
            // Convert object to JSON string and save into file directly
            mapper.writeValue(jfile, user);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
