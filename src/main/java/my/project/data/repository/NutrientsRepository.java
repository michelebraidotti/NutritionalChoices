package my.project.data.repository;

import my.project.data.entities.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by michele on 2/24/17.
 */
@Transactional
public interface NutrientsRepository extends JpaRepository<Nutrient, Integer> {
    public Nutrient findByName(String name);
}
