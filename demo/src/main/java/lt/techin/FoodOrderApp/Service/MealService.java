package lt.techin.FoodOrderApp.Service;

import lt.techin.FoodOrderApp.Model.FoodOrder;
import lt.techin.FoodOrderApp.Model.Meal;
import lt.techin.FoodOrderApp.dao.MealRepository;
import lt.techin.FoodOrderApp.dao.MenuRepository;
import lt.techin.FoodOrderApp.exception.FoodOrderAppValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {
    private final MealRepository mealRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public MealService(MealRepository mealRepository, MenuRepository menuRepository) {
        this.mealRepository = mealRepository;
        this.menuRepository = menuRepository;
    }

    public List<Meal> getAll(Long menuId) {
        return mealRepository.getAllByMenu_Id(menuId);
    }

    public Meal create(Meal meal) {
        return mealRepository.save(meal);
    }

    public Meal create(Meal meal, Long menuId) {
        var existingMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new FoodOrderAppValidationException("Menu does not exist",
                        "id", "Menu not found", menuId.toString()));
        meal.setMenu(existingMenu);

        return mealRepository.save(meal);
    }

    public Meal update(Long id, Meal meal) {
        meal.setId(id);
        return mealRepository.save(meal);
    }


    public Meal getById(Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meal not found with id: " + id));
    }

    public boolean deleteById(Long id) {
        try {
            if (mealRepository.existsById(id)) {
                mealRepository.deleteById(id);
                return true;
            }
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
        return false;
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }

}
