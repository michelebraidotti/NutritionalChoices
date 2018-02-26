package my.project.services;

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

    @Test
    public void test() throws IOException {
        int totalFooItems = 8789;
        usdaDataImporter.setDataPath("/home/michele/Development/Code/NutritionalChoices/usda_data/sr28asc");
        usdaDataImporter.parseFiles();
        assertEquals(totalFooItems, usdaDataImporter.getFoodItems().size());
        usdaDataImporter.saveAll();
        assertEquals(0, usdaDataImporter.getFoodItems().size());
    }
}
