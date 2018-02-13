package my.project.data.entities;

import my.project.data.entities.testobjectgenerator.NutrientTestObjectGenerator;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 2/10/18.
 */

public class NutrientsTest {

    @Test
    public void testGenerator() {
        // Just checking that the generator is still ok
        List<Nutrient> nutrientList = NutrientTestObjectGenerator.Generate(5);
        assertEquals(5, nutrientList.size());
    }
}
