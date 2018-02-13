package my.project.data.parser;

import my.project.data.entities.Measurement;
import my.project.data.entities.Nutrient;

/**
 * Created by michele on 2/5/17.
 */
public class UsdaParser {
    private static final String CSV_SEPARATOR = ",";
    private static final String CSV_NEWLINE = "\n";

    public static Nutrient csvToNutrientParser(String csvData) throws CsvNutrientParserException {
        Nutrient nutrient = new Nutrient();
        String[] lines = csvData.split(CSV_NEWLINE);
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith("\"Nutrient data for:")) {
                String name = lines[i].replaceAll("\"", "");
                name = name.replace("Nutrient data for: ", "");
                nutrient.name = name;
            } else {
                String[] line = lines[i].split(CSV_SEPARATOR);
                // Assuming measurement lines have some sequence fo numbes in column 3
                if (line.length > 2) {
                    if (line[2].matches("[0-9.]*")) {
                        String elementName = line[0].replaceAll("\"", "");
                        String unit = line[1];
                        String value = line[2];
                        nutrient.getMeasurements().add(new Measurement(elementName, unit, value));
                    } else {
                        // TODO: log line with unexpected data in position 3.
                    }
                } else {
                    // TODO: log too short line.
                }
            }

        }
        return nutrient;
    }

}