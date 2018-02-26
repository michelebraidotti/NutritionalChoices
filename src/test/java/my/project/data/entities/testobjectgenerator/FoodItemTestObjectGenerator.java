package my.project.data.entities.testobjectgenerator;

import my.project.data.entities.FoodItem;
import my.project.data.entities.Nutrient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by michele on 2/10/18.
 */
public class FoodItemTestObjectGenerator {
    private static int MAX_MEASUREMENTS = 7;

    public static List<FoodItem> Generate(int amount) {
        Random generator = new Random();
        List<FoodItem> res = new ArrayList<>();
        for (int i = 0; i < amount ; i++) {
            FoodItem n = new FoodItem();
            n.name = "TestName" + i;
            int nutrientsCount = generator.nextInt(MAX_MEASUREMENTS);
            for (int j = 0; j < nutrientsCount; j++) {
                Nutrient m = new Nutrient("element" + j, "unit" + j, String.valueOf(100 * j + j));
                n.getNutrients().add(m);
            }
            res.add(n);
        }
        return res;
    }
}
