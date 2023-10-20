package lt.techin.FoodOrderApp.api.dto;


import lt.techin.FoodOrderApp.Model.Meal;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MenuDto {

    private String title;

    private List<Meal> meals;

    public MenuDto(){}

    public MenuDto(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDto menuDto = (MenuDto) o;
        return Objects.equals(title, menuDto.title) && Objects.equals(meals, menuDto.meals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, meals);
    }

    @Override
    public String toString() {
        return "MenuDto{" +
                "title='" + title + '\'' +
                ", meals=" + meals +
                '}';
    }
}
