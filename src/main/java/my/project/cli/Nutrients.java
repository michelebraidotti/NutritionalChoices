package my.project.cli;

import my.project.services.UsdaDataImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by michele on 2/27/18.
 */
public class Nutrients {



    public static void main(String[] args ) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-context.xml");
        UsdaDataImporter usdaDataImporter = (UsdaDataImporter) applicationContext.getBean("UsdaDataImporter");

        try {
            usdaDataImporter.setDataPath(args[0]);
            usdaDataImporter.parseFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(usdaDataImporter.getFoodItems().size() + "\n");
        usdaDataImporter.saveAll();
    }
}
