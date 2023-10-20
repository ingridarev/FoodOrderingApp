package lt.techin.FoodOrderApp.api.dto.Mapper;

import lt.techin.FoodOrderApp.Model.Meal;
import lt.techin.FoodOrderApp.api.dto.MealDto;
import lt.techin.FoodOrderApp.api.dto.MealEntityDto;

public class MealMapper {

    public static MealDto toMealDto(Meal meal) {
        var mealDto = new MealDto();

        mealDto.setTitle(meal.getTitle());
        mealDto.setDescription(meal.getDescription());
        mealDto.setMenu(meal.getMenu());

        return mealDto;
    }

    public static MealEntityDto toMealEntityDto(Meal meal) {
        var mealDto = new MealEntityDto();

        mealDto.setId(meal.getId());
        mealDto.setTitle(meal.getTitle());
        mealDto.setDescription(meal.getDescription());
        mealDto.setMenu(meal.getMenu());
        return mealDto;
    }

    public static Meal toMeal(MealDto mealDto) {
        var meal = new Meal();

        meal.setTitle(mealDto.getTitle());
        meal.setDescription(mealDto.getDescription());
        meal.setMenu(mealDto.getMenu());
        return meal;
    }

    public static Meal toMealEntityDto(MealEntityDto mealDto) {
        var meal = new Meal();

        mealDto.setId(meal.getId());
        meal.setTitle(mealDto.getTitle());
        meal.setDescription(mealDto.getDescription());
        meal.setMenu(mealDto.getMenu());
        return meal;
    }

}
