package my.project.repository;

import my.project.data.entities.FoodItem;
import my.project.data.entities.testobjectgenerator.FoodItemTestObjectGenerator;
import my.project.data.repository.FoodItemsRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 2/24/17.
 */
@Transactional
public class FoodItemsRepositoryTest extends BasicIntegrationTest {
    @Autowired
    private FoodItemsRepository foodItemsRepository;

    @Test
    public void findByNameTest() {
        FoodItem nutrientBefore = FoodItemTestObjectGenerator.Generate(1).get(0);
        foodItemsRepository.save(nutrientBefore);
        FoodItem nutrientAfter = foodItemsRepository.findByName(nutrientBefore.name);
        assertEquals(nutrientBefore, nutrientAfter);
    }
    @Test
    public void readWriteTest() {
        List<FoodItem> foodItems = FoodItemTestObjectGenerator.Generate(3);

        int foodItemsBefore = foodItemsRepository.findAll().size();
        foodItemsRepository.saveAll(foodItems);
        assertEquals(foodItems.get(0), foodItemsRepository.findByName(foodItems.get(0).name));
        assertEquals(foodItems.get(1), foodItemsRepository.findByName(foodItems.get(1).name));
        assertEquals(foodItems.get(2), foodItemsRepository.findByName(foodItems.get(2).name));
    }

}
