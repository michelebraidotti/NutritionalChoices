package my.project.data.entities.testobjectgenerator;

import my.project.data.entities.Measurement;
import my.project.data.entities.Nutrient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by michele on 2/10/18.
 */
public class NutrientTestObjectGenerator {
    private static int MAX_MEASUREMENTS = 7;

    public static List<Nutrient> Generate(int amount) {
        Random generator = new Random();
        List<Nutrient> res = new ArrayList<>();
        for (int i = 0; i < amount ; i++) {
            Nutrient n = new Nutrient();
            n.name = "TestName" + i;
            int measurements_count = generator.nextInt(MAX_MEASUREMENTS);
            for (int j = 0; j < measurements_count; j++) {
                Measurement m = new Measurement("element" + j, "unit" + j, String.valueOf(100 * j + j));
                n.getMeasurements().add(m);
            }
            res.add(n);
        }
        return res;
    }
}
