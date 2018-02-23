package my.project.services;

import my.project.data.entities.Measurement;
import my.project.data.entities.Nutrient;
import my.project.data.repository.NutrientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by michele on 2/14/18.
 */
@Service("UsdaDataImporter")
public class UsdaDataImporter {
    private static String FOOD_DESC = "FOOD_DES_NEW.txt";
    private static String FOOD_GROUP = "FD_GROUP.txt";
    private static String NUTRIENT_DATA = "NUT_DATA.txt";
    private static String NUTRIENT_DEFINITION = "NUTR_DEF_NEW.txt";

    private String dataPath = "";
    private List<Nutrient> nutrients = new ArrayList<Nutrient>();
    private List<String> errors = new ArrayList<String>();
    private List<String> warnings = new ArrayList<String>();
    @Autowired
    private NutrientsRepository nutrientsRepository;

    public UsdaDataImporter() {
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    private List<String[]> parseCsvFile(String filePath, int maxCols) throws IOException {
        String csvSeparator = "\\^";
        String csvDelimiter = "~";
        List<String[]> rows = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for ( String line:lines ) {
            String[] row = new String[maxCols];
            String[] elems = line.split(csvSeparator);
            // TODO if (elems.length > maxCols) then some elems will be silently clipped
            int i;
            for (i = 0; i < elems.length; i++) {
                row[i] = elems[i].replaceAll(csvDelimiter, "");
            }
            while ( i < maxCols) {
                row[i++] = "";
            }
            rows.add(row);
        }
        return  rows;
    }

    public void parseFiles() throws IOException {
        // - Assumes dataPath is filled and the path contains the USDA data files.
        // - Some of the files need to be converted to UTF-8 encoding before running this.
        // - Will report parsing errors in "errors" array.
        // - Will report parsing warning in "warnings" array.
        // These are NOT a replacement for throwing exceptions or logging, they are
        // only intended to make the import process interactive and provide a
        // way to alert the users about parsing problems that may be ignored by them.

        // 1. Fill dictionary from FOOD_GROUP information
        Map<String, String> foodGroup = new HashMap<>();
        List<String[]> foodGroupLines = parseCsvFile(getDataPath() + File.separator + FOOD_GROUP, 2);
        for ( String[] foodGrpLine:foodGroupLines ) {
            foodGroup.put(foodGrpLine[0], foodGrpLine[1]);
        }

        // 2. Fill dictionary from NUTRIENT_DEFINITION information
        Map<String, NutrientDef> nutrientDefMap = new HashMap<>();
        List<String[]> nutrient_def_lines = parseCsvFile(getDataPath() + File.separator + NUTRIENT_DEFINITION, 6);
        for ( String[] nutrient_def_line:nutrient_def_lines ) {
            NutrientDef nutrientDef = new NutrientDef();
            int i = 0;
            nutrientDef.NutrNo = nutrient_def_line[i++];
            nutrientDef.Units = nutrient_def_line[i++];
            nutrientDef.Tagname = nutrient_def_line[i++];
            nutrientDef.NutrDesc = nutrient_def_line[i++];
            nutrientDef.NumDec = nutrient_def_line[i++];
            nutrientDef.SROrder = nutrient_def_line[i++];
            nutrientDefMap.put(nutrientDef.NutrNo, nutrientDef);
        }

        // 3. Parse FOOD_DESCR and put them in a list
        List<FoodDescr> foodDescrs = new ArrayList<>();
        List<String[]> foodDescrLines = parseCsvFile(getDataPath() + File.separator + FOOD_DESC, 14);
        for ( String[] foodDescrLine:foodDescrLines ) {
            FoodDescr foodDescr = new FoodDescr();
            int i = 0;
            foodDescr.NDBNo = foodDescrLine[i++];
            foodDescr.FdGrpCd = foodDescrLine[i++];
            foodDescr.LongDesc = foodDescrLine[i++];
            foodDescr.ShrtDesc = foodDescrLine[i++];
            foodDescr.ComName = foodDescrLine[i++];
            foodDescr.ManufacName = foodDescrLine[i++];
            foodDescr.Survey = foodDescrLine[i++];
            foodDescr.RefDesc = foodDescrLine[i++];
            foodDescr.Refuse = foodDescrLine[i++];
            foodDescr.SciName = foodDescrLine[i++];
            foodDescr.NFactor = foodDescrLine[i++];
            foodDescr.ProFactor = foodDescrLine[i++];
            foodDescr.FatFactor = foodDescrLine[i++];
            foodDescr.CHOFactor = foodDescrLine[i++];
            foodDescrs.add(foodDescr);
        }

        // 4. Parse NUTRIENT_DATA and put them in a map indexed by NDBNo so
        // it will be easy to retrieve from the previous foodDescrs list
        Map<String,List<NutrientData>> nutrientDataMap = new HashMap<>();
        List<String[]> nutrientDataLines = parseCsvFile(getDataPath() + File.separator + NUTRIENT_DATA, 17);
        for ( String[] nutrientDataLine:nutrientDataLines ) {
            int i = 0;
            NutrientData nutrientData = new NutrientData();
            nutrientData.NDBNo = nutrientDataLine[i++];
            nutrientData.NutrNo = nutrientDataLine[i++];
            nutrientData.NutrVal = nutrientDataLine[i++];
            nutrientData.NumData_Pts = nutrientDataLine[i++];
            nutrientData.StdError = nutrientDataLine[i++];
            nutrientData.SrcCd = nutrientDataLine[i++];
            nutrientData.DerivCd = nutrientDataLine[i++];
            nutrientData.RefNDB_No = nutrientDataLine[i++];
            nutrientData.AddNutrMark = nutrientDataLine[i++];
            nutrientData.NumStudies = nutrientDataLine[i++];
            nutrientData.Min = nutrientDataLine[i++];
            nutrientData.Max = nutrientDataLine[i++];
            nutrientData.DF = nutrientDataLine[i++];
            nutrientData.LowEB = nutrientDataLine[i++];
            nutrientData.UpEB= nutrientDataLine[i++];
            nutrientData.Statcmt = nutrientDataLine[i++];
            nutrientData.AddModDate = nutrientDataLine[i++];
            if (! nutrientDataMap.containsKey(nutrientData.NDBNo)) {
                nutrientDataMap.put(nutrientData.NDBNo, new ArrayList<>());
            }
            nutrientDataMap.get(nutrientData.NDBNo).add(nutrientData);
        }

        // 5. "Join" FoodDescr with dictionaries and NutrientData
        for (FoodDescr foodDescr:foodDescrs) {
            Nutrient nutrient = new Nutrient();
            nutrient.name = foodDescr.LongDesc;
            List<Measurement> measurements = new ArrayList<>();
            List<NutrientData> nutrientDataList = nutrientDataMap.get(foodDescr.NDBNo);
            for (NutrientData nutrientData:nutrientDataList) {
                // TODO: transform nutrientdata in measurement and add it to measurements
                measurements.add(new Measurement("", "", ""));
            }
            nutrient.setMeasurements(measurements);
            nutrients.add(nutrient);
        }
    }

    private class FoodDescr {
        public String NDBNo = "";
        public String FdGrpCd = "";
        public String LongDesc = "";
        public String ShrtDesc = "";
        public String ComName = "";
        public String ManufacName = "";
        public String Survey = "";
        public String RefDesc = "";
        public String Refuse = "";
        public String SciName = "";
        public String NFactor = "";
        public String ProFactor = "";
        public String FatFactor = "";
        public String CHOFactor = "";
    }

    private class NutrientData {
        public String NDBNo = "";
        public String NutrNo = "";
        public String NutrVal = "";
        public String NumData_Pts = "";
        public String StdError = "";
        public String SrcCd = "";
        public String DerivCd = "";
        public String RefNDB_No = "";
        public String AddNutrMark = "";
        public String NumStudies = "";
        public String Min = "";
        public String Max = "";
        public String DF = "";
        public String LowEB = "";
        public String UpEB = "";
        public String Statcmt = "";
        public String AddModDate = "";
    }

    private class NutrientDef {
        public String NutrNo = "";
        public String Units = "";
        public String Tagname = "";
        public String NutrDesc = "";
        public String NumDec = "";
        public String SROrder = "";
    }
}
