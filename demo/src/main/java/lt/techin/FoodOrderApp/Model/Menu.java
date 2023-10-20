package lt.techin.FoodOrderApp.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "menu")
    private List<Meal> meals = new ArrayList<>();

    public Menu() {
    }

    public Menu(Long id) {
        this.id = id;
    }

    public Menu(Long id, String title, List<Meal> meals) {
        this.id = id;
        this.title = title;
        this.meals = meals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id) && Objects.equals(title, menu.title) && Objects.equals(meals, menu.meals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, meals);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", meals=" + meals +
                '}';
    }
}