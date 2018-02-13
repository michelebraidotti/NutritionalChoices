package my.project.data.parser;

import my.project.data.entities.Measurement;
import my.project.data.entities.Nutrient;
import my.project.data.parser.CsvNutrientParserException;
import my.project.data.parser.UsdaParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 2/5/17.
 */
public class UsdaParserTest {
    @Test
    public void testCsvToNutrientParser() throws CsvNutrientParserException {
        // Sample data
        String expectedName = "2061, Nuts, almonds";
        String expectedWaterNutrientName = "Water";
        Measurement expectedWaterNutrientMeasurement = new Measurement(expectedWaterNutrientName, "g", "4.41");
        String jsonRawData = "Source: USDA National Nutrient Database for Standard Reference 28 slightly revised May 2016 Software v.3.7 2017-02-01\n" +
                 "Basic Report\n" +
                 "Report Run at: February 05, 2017 09:51 EST\n" +
                 "\"Nutrient data for: " + expectedName + "\"\n" +
                 "Nutrient,Unit,1Value per 100 g,\"1 cup, whole = 143.0g\",\"1 cup, sliced = 92.0g\",\"1 cup, slivered = 108.0g\",\"1 cup, ground = 95.0g\",\"1 oz (23 whole kernels) = 28.35g\",\"1 almond = 1.2g\",\n" +
                 "Proximates\n" +
                 "\"" + expectedWaterNutrientName + "\"," + expectedWaterNutrientMeasurement.getUnit()+ "," + expectedWaterNutrientMeasurement.getValue() + ",6.31,4.06,4.76,4.19,1.25,0.05\n" +
                 "\"Energy\",kcal,579,828,533,625,550,164,7\n" +
                 "\"Protein\",g,21.15,30.24,19.46,22.84,20.09,6.00,0.25\n" +
                 "\"Total lipid (fat)\",g,49.93,71.40,45.94,53.92,47.43,14.16,0.60";
        Nutrient almonds = UsdaParser.csvToNutrientParser(jsonRawData);
        assertEquals(expectedName, almonds.name);
        assertEquals(expectedWaterNutrientMeasurement.getUnit(), almonds.getMeasurementByName(expectedWaterNutrientName).getUnit());
        assertEquals(expectedWaterNutrientMeasurement.getValue(), almonds.getMeasurementByName(expectedWaterNutrientName).getValue());
        assertEquals(4, almonds.getMeasurements().size());
    }
}
