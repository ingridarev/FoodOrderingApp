package lt.techin.FoodOrderApp.api;

import lt.techin.FoodOrderApp.Model.Meal;
import lt.techin.FoodOrderApp.Service.MealService;
import lt.techin.FoodOrderApp.api.dto.Mapper.MealMapper;
import lt.techin.FoodOrderApp.api.dto.Mapper.MenuMapper;
import lt.techin.FoodOrderApp.api.dto.MealDto;
import lt.techin.FoodOrderApp.api.dto.MealEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static lt.techin.FoodOrderApp.api.dto.Mapper.MealMapper.*;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/meals")
@Validated
public class MealController {

    private final Logger logger = LoggerFactory.getLogger(MealController.class);
    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }


    @GetMapping(value = "/all/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<MealEntityDto> getMealsByMenuId(@PathVariable Long menuId) {
        List<Meal> meals = mealService.getAll(menuId);
        return meals.stream()
                .map(MealMapper::toMealEntityDto)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/{menuId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MealDto> createMeal(@RequestBody MealDto mealDto, @PathVariable Long menuId) {
        var createdMeal = mealService.create(toMeal(mealDto), menuId);

        return ok(toMealDto(createdMeal));
    }

    @PatchMapping(path = "/{mealId}")
    public ResponseEntity<MealDto> updateMeal(@PathVariable Long mealId, @RequestBody MealDto mealDto) {
        Meal updatedMeal = mealService.update(mealId, toMeal(mealDto));
        return ResponseEntity.ok(toMealDto(updatedMeal));
    }

    @GetMapping(value = "/{mealId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MealEntityDto> getMealById(@PathVariable Long mealId) {
        Meal meal = mealService.getById(mealId);

        if (meal != null) {
            MealEntityDto mealEntityDto = MealMapper.toMealEntityDto(meal);
            return ResponseEntity.ok(mealEntityDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long mealId) {
        boolean deleted = mealService.deleteById(mealId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

