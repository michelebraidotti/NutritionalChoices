package my.project.data.entities;

import my.project.data.entities.testobjectgenerator.FoodItemTestObjectGenerator;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by michele on 2/10/18.
 */

public class FoodItemsTest {

    @Test
    public void testGenerator() {
        // Just checking that the generator is still ok
        List<FoodItem> foodItemList = FoodItemTestObjectGenerator.Generate(5);
        assertEquals(5, foodItemList.size());
    }

    @Test
    public void testEquals() {
        List<FoodItem> foodItems = FoodItemTestObjectGenerator.Generate(2);
        assertNotEquals(foodItems.get(0), foodItems.get(1));
        assertTrue(foodItems.get(0).hashCode() != foodItems.get(1).hashCode());
        FoodItem newFoodItem = new FoodItem();
        newFoodItem.name = foodItems.get(0).name;
        for (Nutrient n:foodItems.get(0).getNutrients()) {
            Nutrient newNutrient = new Nutrient(n.getElement(), n.getUnit(), n.getValue());
            newFoodItem.getNutrients().add(newNutrient);
        }
        assertEquals(foodItems.get(0), newFoodItem);
        assertTrue(foodItems.get(0).hashCode() == newFoodItem.hashCode());
    }
}
