package my.project.data.entities;

import my.project.data.entities.testobjectgenerator.NutrientTestObjectGenerator;
import org.junit.Test;
import java.util.List;

/**
 * Created by michele on 2/10/18.
 */

public class NutrientsTest {

    @Test
    public void test() {
        List<Nutrient> nutrientList = NutrientTestObjectGenerator.Generate(5);
        // nothing to test yet
        System.out.print("Testing the test!\n");
    }
}
