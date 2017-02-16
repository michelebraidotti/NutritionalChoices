package my.project.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 2/16/17.
 */
public class ObjectToJSONAndJSONToObjectTest {

    @Test
    public void test() throws IOException, CsvNutrientParserException {
        ObjectMapper mapper = new ObjectMapper();
        ///home/michele/Development/Code/NutritionalChoices/src/test/resources/sample_data/almonds_nutritional_values.csv
        String almondsNutritionalValuesCsv = new String(Files.readAllBytes(Paths.get("src/test/resources/sample_data/almonds_nutritional_values.csv")));
        Nutrient almonds = Parser.csvToNutrientParser(almondsNutritionalValuesCsv);
        String jsonInString = mapper.writeValueAsString(almonds);
        System.out.println(jsonInString);
        Nutrient almondsReparsed = mapper.readValue(jsonInString, Nutrient.class);
        assertEquals(almonds, almondsReparsed);
    }
}
