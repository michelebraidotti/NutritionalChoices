package my.project.repository;

import my.project.data.entities.testobjectgenerator.NutrientTestObjectGenerator;
import my.project.data.repository.NutrientsRepository;
import my.project.data.parser.CsvNutrientParserException;
import my.project.data.entities.Nutrient;
import my.project.data.parser.UsdaParser;
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
        Nutrient almonds = UsdaParser.csvToNutrientParser(almondsNutritionalValuesCsv);
        String orangesNutritionalValuesCsv = new String(Files.readAllBytes(Paths.get("src/test/resources/sample_data/oranges_nutritional_values.csv")));
        Nutrient oranges = UsdaParser.csvToNutrientParser(orangesNutritionalValuesCsv);
        String zucchiniNutritionalValuesCsv = new String(Files.readAllBytes(Paths.get("src/test/resources/sample_data/zucchini_nutritional_values.csv")));
        Nutrient zucchini = UsdaParser.csvToNutrientParser(zucchiniNutritionalValuesCsv);

        List<Nutrient> nutrients = new ArrayList<Nutrient>();
        nutrients.add(almonds);
        nutrients.add(oranges);
        nutrients.add(zucchini);

        int nutrientsBefore = nutrientsRepository.findAll().size();
        nutrientsRepository.saveAll(nutrients);
        assertEquals(almonds, nutrientsRepository.findByName(almonds.name));
        assertEquals(oranges, nutrientsRepository.findByName(oranges.name));
        assertEquals(zucchini, nutrientsRepository.findByName(zucchini.name));
        nutrientsRepository.deleteAll(nutrients);
        int nutrientsAfter = nutrientsRepository.findAll().size();
        assertEquals(nutrientsBefore, nutrientsAfter);
    }

    @Test
    public void findByNameTest() throws IOException, CsvNutrientParserException {
        Nutrient nutrientBefore = NutrientTestObjectGenerator.Generate(1).get(0);
        nutrientsRepository.save(nutrientBefore);
        Nutrient nutrientAfter = nutrientsRepository.findByName(nutrientBefore.name);
        assertEquals(nutrientBefore, nutrientAfter);

    }
}