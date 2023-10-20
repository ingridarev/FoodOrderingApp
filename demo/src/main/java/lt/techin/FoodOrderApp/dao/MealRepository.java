package lt.techin.FoodOrderApp.dao;

import lt.techin.FoodOrderApp.Model.Meal;
import lt.techin.FoodOrderApp.Model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
//    List<Meal> findMealById(Long id);
    List<Meal> getAllByMenu_Id(Long id);

    List<Meal> findByMenu(Menu menu);



}
