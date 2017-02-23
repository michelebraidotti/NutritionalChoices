package my.project.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import my.project.data.Nutrient;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michele on 2/17/17.
 */
public class NutrientsDAO {
    private static final String MONGO_COLLECTION_NAME = "nutrients";
    private final MongoCollection<Document> nutrientsCollection;
    private final ObjectMapper mapper;

    public NutrientsDAO() {
        nutrientsCollection = MongoDBStore.getCollection(MONGO_COLLECTION_NAME);
        mapper = new ObjectMapper();
    }

    public void saveOne(Nutrient nutrient) throws JsonProcessingException {
        String jsonInString = mapper.writeValueAsString(nutrient);
        Document document = Document.parse(jsonInString);
        nutrientsCollection.insertOne(document);
    }

    public void saveMany(List<Nutrient> nutrients) throws JsonProcessingException {
        List<Document> documents = new ArrayList<Document>();
        for (Nutrient nutrient:nutrients) {
            String jsonInString = mapper.writeValueAsString(nutrient);
            documents.add(Document.parse(jsonInString));
        }
        nutrientsCollection.insertMany(documents);
    }

    public List<Nutrient> findAll() throws IOException {
        List<Nutrient> result = new ArrayList<Nutrient>();
        for (Document document: nutrientsCollection.find()) {
            Nutrient nutrient = mapper.readValue(document.toJson(), Nutrient.class);
            result.add(nutrient);
        }
        return result;
    }
}
