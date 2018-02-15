package my.project.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by michele on 2/15/18.
 */
public class UsdaDataImporterTest extends  BasicTest {
    @Autowired
    UsdaDataImporter usdaDataImporter;

    @Test
    public void test() throws IOException {
        usdaDataImporter.setDataPath("/home/michele/Development/Code/NutritionalChoices/usda_data/sr28asc");
        usdaDataImporter.parseFiles();
    }
}
