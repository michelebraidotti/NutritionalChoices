package my.project.storage;

import my.project.data.CsvNutrientParserException;
import my.project.data.Nutrient;
import my.project.data.Parser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 2/24/17.
 */
@Transactional
public class NutrientsRepositoryTest extends BasicIntegrationTest {
    @Autowired
    private NutrientsRepository nutrientsRepository;

    @Test
    public void readWriteTest() throws IOException, CsvNutrientParserException {
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

        int nutrientsBefore = nutrientsRepository.findAll().size();
        nutrientsRepository.save(nutrients);
        assertEquals(almonds, nutrientsRepository.findByName(almonds.getName()));
        assertEquals(oranges, nutrientsRepository.findByName(oranges.getName()));
        assertEquals(zucchini, nutrientsRepository.findByName(zucchini.getName()));
        nutrientsRepository.delete(nutrients);
        int nutrientsAfter = nutrientsRepository.findAll().size();
        assertEquals(nutrientsBefore, nutrientsAfter);
    }
}
