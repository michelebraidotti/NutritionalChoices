package my.project.nutrients;

import my.project.data.entities.Nutrient;
import my.project.data.repository.NutrientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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
    private static String FOOD_DESC = "FOOD_DES.txt";
    private static String FOOD_GROUP = "FD_GROUP.txt";
    private static String NUTRIENT_DATA = "NUT_DATA.txt";
    private static String NUTRIENT_DEFINITION = "NUTR_DEF.txt";
    private static String CSV_SEPARATOR = "^";
    private static String CSV_EXTRA_SEPARATOR = "~";

    private String dataPath = "";
    private List<Nutrient> genes = new ArrayList<Nutrient>();
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

    public void parseFiles() throws IOException {
        // - Assumes dataPath is filled and the path contains the USDA data files.
        // - Will report parsing errors in errors array.
        // - Will report parsing warning in warnings array.
        // These are NOT a replacement for throwing exceptions or logging, they are
        // only intended to make the import process interactive and provide a
        // way to alert the users about parsing problems that may be ignored by them.

        // 1. Fill dictionary from FOOD_GROUP information
        Map<String, String> foodGroup = new HashMap<>();
        List<String> food_group_lines = Files.readAllLines(Paths.get(getDataPath() + File.separator + FOOD_GROUP));
        for ( String food_grp_line:food_group_lines ) {
            String[] elems = food_grp_line.split(CSV_SEPARATOR);
            for (int i = 0; i < elems.length; i++) {
                elems[i] = elems[i].replaceAll(CSV_EXTRA_SEPARATOR, "");
            }
            foodGroup.put(elems[0], elems[1]);
        }

        // 2. Fill dictionary from NUTRIENT_DEFINITION information
        Map<String, NutrientDef> nutrientDefMap = new HashMap<>();
        List<String> nutrient_def_lines = Files.readAllLines(Paths.get(getDataPath() + File.separator + NUTRIENT_DEFINITION));
        for ( String nutrient_def_line:nutrient_def_lines ) {
            String[] elems = nutrient_def_line.split(CSV_SEPARATOR);
            for (int i = 0; i < elems.length; i++) {
                elems[i] = elems[i].replaceAll(CSV_EXTRA_SEPARATOR, "");
            }
            NutrientDef nutrientDef = new NutrientDef();
            int i = 0;
            nutrientDef.NutrNo = elems[i++];
            nutrientDef.Units = elems[i++];
            nutrientDef.Tagname = elems[i++];
            nutrientDef.NutrDesc = elems[i++];
            nutrientDef.NumDec = elems[i++];
            nutrientDef.SROrder = elems[i++];
            nutrientDefMap.put(nutrientDef.NutrNo, nutrientDef);
        }

        // 3. Parse FOOD_DESCR
        List<FoodDescr> foodDescrs = new ArrayList<>();
        List<String> food_descr_lines = Files.readAllLines(Paths.get(getDataPath() + File.separator + FOOD_DESC));
        for ( String food_descr_line:food_descr_lines ) {
            String[] elems = food_descr_line.split(CSV_SEPARATOR);
            for (int i = 0; i < elems.length; i++) {
                elems[i] = elems[i].replaceAll(CSV_EXTRA_SEPARATOR, "");
            }
            FoodDescr foodDescr = new FoodDescr();
            int i = 0;
            foodDescr.NDBNo = elems[i++];
            foodDescr.FdGrpCd = elems[i++];
            foodDescr.LongDesc = elems[i++];
            foodDescr.ShrtDesc = elems[i++];
            foodDescr.ComName = elems[i++];
            foodDescr.ManufacName = elems[i++];
            foodDescr.Survey = elems[i++];
            foodDescr.RefDesc = elems[i++];
            foodDescr.Refuse = elems[i++];
            foodDescr.SciName = elems[i++];
            foodDescr.NFactor = elems[i++];
            foodDescr.ProFactor = elems[i++];
            foodDescr.FatFactor = elems[i++];
            foodDescr.CHOFactor = elems[i++];
            foodDescrs.add(foodDescr);
        }

        // 4. Parse NUTRIENT_DATA
        Map<String,List<NutrientData>> nutrientDataMap = new HashMap<>();
        List<String> nutrient_data_lines = Files.readAllLines(Paths.get(getDataPath() + File.separator + NUTRIENT_DATA));
        for ( String nutrient_data_line:nutrient_data_lines ) {
            String[] elems = nutrient_data_line.split(CSV_SEPARATOR);
            for (int i = 0; i < elems.length; i++) {
                elems[i] = elems[i].replaceAll(CSV_EXTRA_SEPARATOR, "");
            }
            int i = 0;
            NutrientData nutrientData = new NutrientData();
            nutrientData.NDBNo = elems[i++];
            nutrientData.NutrNo = elems[i++];
            nutrientData.NutrVal = elems[i++];
            nutrientData.NumData_Pts = elems[i++];
            nutrientData.StdError = elems[i++];
            nutrientData.SrcCd = elems[i++];
            nutrientData.DerivCd = elems[i++];
            nutrientData.RefNDB_No = elems[i++];
            nutrientData.AddNutrMark = elems[i++];
            nutrientData.NumStudies = elems[i++];
            nutrientData.Min = elems[i++];
            nutrientData.Max = elems[i++];
            nutrientData.DF = elems[i++];
            nutrientData.LowEB = elems[i++];
            nutrientData.UpEB= elems[i++];
            nutrientData.Statcmt = elems[i++];
            nutrientData.AddModDate = elems[i++];
            nutrientData.CC = elems[i++];
            if (! nutrientDataMap.containsKey(nutrientData.NDBNo)) {
                nutrientDataMap.put(nutrientData.NDBNo, new ArrayList<>());
            }
            nutrientDataMap.get(nutrientData.NDBNo).add(nutrientData);
        }

        // 5. "Join" FoodDescr with dictionaries and NutrientData
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
        public String CC = "";
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
