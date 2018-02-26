package my.project.data.repository;

import my.project.data.entities.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by michele on 2/24/17.
 */
@Transactional
public interface FoodItemsRepository extends JpaRepository<FoodItem, Integer> {
    public FoodItem findByName(String name);
}
