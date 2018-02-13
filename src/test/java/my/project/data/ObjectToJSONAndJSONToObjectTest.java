package my.project.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.project.data.entities.Nutrient;
import my.project.data.parser.CsvNutrientParserException;
import my.project.data.parser.UsdaParser;
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
        String almondsNutritionalValuesCsv = new String(Files.readAllBytes(Paths.get("src/test/resources/sample_data/almonds_nutritional_values.csv")));
        Nutrient almonds = UsdaParser.csvToNutrientParser(almondsNutritionalValuesCsv);
        String jsonInString = mapper.writeValueAsString(almonds);
        //System.out.println(jsonInString);
        Nutrient almondsReparsed = mapper.readValue(jsonInString, Nutrient.class);
        assertEquals(almonds, almondsReparsed);
    }
}
