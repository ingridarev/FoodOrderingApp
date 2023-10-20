package lt.techin.FoodOrderApp.api.dto;


import lt.techin.FoodOrderApp.Model.Meal;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MenuEntityDto extends MenuDto{

    private Long id;

    public MenuEntityDto(){}

    public MenuEntityDto(Long id) {
        this.id = id;
    }

    public MenuEntityDto(String title, Long id) {
        super(title);
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
        MenuEntityDto that = (MenuEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "MenuEntityDto{" +
                "id=" + id +
                '}';
    }
}
