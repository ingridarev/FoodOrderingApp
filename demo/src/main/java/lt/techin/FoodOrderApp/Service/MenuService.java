package lt.techin.FoodOrderApp.Service;

import lt.techin.FoodOrderApp.Model.Meal;
import lt.techin.FoodOrderApp.Model.Menu;
import lt.techin.FoodOrderApp.dao.MealRepository;
import lt.techin.FoodOrderApp.dao.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MealRepository mealRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, MealRepository mealRepository) {
        this.menuRepository = menuRepository;
        this.mealRepository = mealRepository;
    }

    public List<Menu> getAll() {
        return menuRepository.findAll();
    }

    public Menu create(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu update(Long id, Menu menu) {
        menu.setId(id);
        return menuRepository.save(menu);
    }

    public void deleteMenu(Long menuId) {
        menuRepository.deleteById(menuId);
    }

    public Optional<Menu> getById(Long id) {
        return menuRepository.findById(id);
    }


    public boolean deleteById(Long id) {
        try {
            if (menuRepository.existsById(id)) {
                menuRepository.deleteById(id);
                return true;
            }
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
        return false;
    }

    public List<Meal> getMealByMenuId(Long menuId) {
        return menuRepository.findMealById(menuId);
    }

    public boolean deleteMenuAndMeal(Long menuId) {
        Optional<Menu> menuOptional = menuRepository.findById(menuId);
        if (menuOptional.isPresent()) {
            Menu menu = menuOptional.get();
            List<Meal> meal = mealRepository.findByMenu(menu);

            for (Meal meals : meal) {
                meals.setMenu(null);
                mealRepository.save(meals);
            }
            menuRepository.deleteById(menuId);
            return true;
        } else {
            return false;
        }
    }
}
