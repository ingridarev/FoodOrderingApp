package lt.techin.FoodOrderApp.api.dto;

import lt.techin.FoodOrderApp.Model.Menu;

import java.util.Objects;

public class MealDto {

    private String title;

    private String description;

    private Menu menu;

    public MealDto(String title, String description, Menu menu) {
        this.title = title;
        this.description = description;
        this.menu = menu;
    }

    public MealDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealDto mealDto = (MealDto) o;
        return Objects.equals(title, mealDto.title) && Objects.equals(description, mealDto.description) && Objects.equals(menu, mealDto.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, menu);
    }

    @Override
    public String toString() {
        return "MealDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", menu=" + menu +
                '}';
    }
}
