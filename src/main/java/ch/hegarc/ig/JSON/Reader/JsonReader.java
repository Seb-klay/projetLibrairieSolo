package ch.hegarc.ig.JSON.Reader;

import ch.hegarc.ig.business.Competition;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    private static List<Competition> competitions = new ArrayList<>();

    public static List<Competition> readSourceJackson(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            competitions = objectMapper.readValue(new File(filename), new TypeReference<List<Competition>>(){});
            return competitions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
