package jxcel.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jxcel.BiometricFileWorker;
import jxcel.model.EmpDetails;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//h

/**
 * Created by Saurabh on 2/14/2016.
 */
public class JsonMapper {
    ObjectMapper mapper = new ObjectMapper();
    //For testing
    List<EmpDetails> user;

    public JsonMapper toJsonFile(String fileName) {


        //For testing
        user = BiometricFileWorker.empList;

        try {
            File jfile = new File(".\\JSON files\\unformattedJson.json");
            //Convert object to JSON string and save into file directly
            mapper.writeValue(jfile, user);

            //Convert object to JSON string
            String jsonInString = mapper.writeValueAsString(user);
            //System.out.println(jsonInString);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void fromJsonToFormattedJson(String jsonInString) {
        //Convert object to JSON string and pretty print
        try {
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        FileWriter file = null;
        try {
            file = new FileWriter(new File(".\\JSON files\\formattedJson.json"));
            file.write(jsonInString);
            //System.out.println(jsonInString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void fromJsonToObject(Object objectName) {
        mapper = new ObjectMapper();

        // read JSON from a file
        try {
            Map<String, Object> map = mapper.readValue(
                    new File("c:\\user.json"),
                    new TypeReference<Map<String, Object>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
