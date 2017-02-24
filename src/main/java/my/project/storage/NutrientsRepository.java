package my.project.storage;

import my.project.data.Nutrient;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by michele on 2/24/17.
 */
public interface NutrientsRepository extends MongoRepository<Nutrient, String> {
    public Nutrient findByName(String name);
}
