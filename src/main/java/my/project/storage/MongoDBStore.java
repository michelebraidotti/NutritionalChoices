package my.project.storage;

import com.mongodb.MongoClient;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import my.project.data.Nutrient;
import org.bson.Document;

import java.util.List;

/**
 * Created by michele on 2/17/17.
 */
public class MongoDBStore {
    private static final String MONGO_HOST = "localhost";
    private static final int MONGO_PORT = 27017;
    private static final String MONGO_DB_NAME = "nutritional_choices";

    public static MongoCollection<Document> getCollection(String collecionName) {
        MongoClient mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT);
        MongoDatabase database = mongoClient.getDatabase(MONGO_DB_NAME);
        MongoCollection<Document> collection = database.getCollection(collecionName);
        // MongoCollection<Document> collection = database.getCollection(MONGO_COLLECTION_NAME);
        return collection;
    }
}
