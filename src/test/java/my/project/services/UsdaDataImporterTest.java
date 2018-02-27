package my.project.services;

import my.project.data.repository.FoodItemsRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 2/15/18.
 */
@Transactional
public class UsdaDataImporterTest extends  BasicTest {
    @Autowired
    UsdaDataImporter usdaDataImporter;
    @Autowired
    FoodItemsRepository foodItemsRepository;

    @Test
    public void test() throws IOException {
        long beforeFoodItemCount = foodItemsRepository.count();
        usdaDataImporter.setDataPath("src/test/resources/sample_data");
        long testFoodItemsCount = usdaDataImporter.getItemsToImportCount();
        usdaDataImporter.parseFiles();
        assertEquals(testFoodItemsCount, usdaDataImporter.getFoodItems().size());
        usdaDataImporter.saveAll();
        assertEquals(beforeFoodItemCount + testFoodItemsCount, foodItemsRepository.count());
        assertEquals(0, usdaDataImporter.getFoodItems().size());
    }
}
