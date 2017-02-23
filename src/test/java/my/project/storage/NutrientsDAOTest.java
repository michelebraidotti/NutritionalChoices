package my.project.storage;

import my.project.data.CsvNutrientParserException;
import my.project.data.Nutrient;
import my.project.data.Parser;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 2/17/17.
 */
public class NutrientsDAOTest {
    @Test
    public void testSaveMany() throws IOException, CsvNutrientParserException {

        String almondsNutritionalValuesCsv = new String(Files.readAllBytes(Paths.get("src/test/resources/sample_data/almonds_nutritional_values.csv")));
        Nutrient almonds = Parser.csvToNutrientParser(almondsNutritionalValuesCsv);
        String orangesNutritionalValuesCsv = new String(Files.readAllBytes(Paths.get("src/test/resources/sample_data/oranges_nutritional_values.csv")));
        Nutrient oranges = Parser.csvToNutrientParser(orangesNutritionalValuesCsv);
        String zucchiniNutritionalValuesCsv = new String(Files.readAllBytes(Paths.get("src/test/resources/sample_data/zucchini_nutritional_values.csv")));
        Nutrient zucchini = Parser.csvToNutrientParser(zucchiniNutritionalValuesCsv);

        List<Nutrient> nutrients = new ArrayList<Nutrient>();
        nutrients.add(almonds);
        nutrients.add(oranges);
        nutrients.add(zucchini);

        NutrientsDAO nutrientsDAO = new NutrientsDAO();
        nutrientsDAO.saveMany(nutrients);

        List<Nutrient> nutrientsFromDb = nutrientsDAO.findAll();
        assertEquals(nutrients, nutrientsFromDb);
    }
}
