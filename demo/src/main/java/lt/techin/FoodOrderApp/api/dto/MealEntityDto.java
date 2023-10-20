package lt.techin.FoodOrderApp.api.dto;

import lt.techin.FoodOrderApp.Model.Meal;
import lt.techin.FoodOrderApp.Model.Menu;

import java.util.List;
import java.util.Objects;

public class MealEntityDto extends MealDto {
    private Long id;

    public MealEntityDto(){}

    public MealEntityDto(Long id) {
        this.id = id;
    }

    public MealEntityDto(String title, String description, Menu menu, Long id) {
        super(title, description, menu);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MealEntityDto that = (MealEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "MealEntityDto{" +
                "id=" + id +
                '}';
    }
}
