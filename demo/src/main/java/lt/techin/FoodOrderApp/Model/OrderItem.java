package lt.techin.FoodOrderApp.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    private int quantity;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "foodOrder_id")
    private FoodOrder foodOrder;

    public OrderItem(Long id) {
        this.id = id;
    }

    public OrderItem(Long id, Meal meal, int quantity, FoodOrder foodOrder) {
        this.id = id;
        this.meal = meal;
        this.quantity = quantity;
        this.foodOrder = foodOrder;
    }

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public FoodOrder getFoodOrder() {
        return foodOrder;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return quantity == orderItem.quantity && Objects.equals(id, orderItem.id) && Objects.equals(meal, orderItem.meal) && Objects.equals(foodOrder, orderItem.foodOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meal, quantity, foodOrder);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", meal=" + meal +
                ", quantity=" + quantity +
                ", foodOrder=" + foodOrder +
                '}';
    }
}
