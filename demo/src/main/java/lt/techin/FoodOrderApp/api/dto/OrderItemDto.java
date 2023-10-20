package lt.techin.FoodOrderApp.api.dto;

import lt.techin.FoodOrderApp.Model.FoodOrder;
import lt.techin.FoodOrderApp.Model.Meal;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

public class OrderItemDto {


    private Meal meal;

    private int quantity;

    private FoodOrder foodOrder;

    public OrderItemDto(Meal meal, int quantity, FoodOrder foodOrder) {
        this.meal = meal;
        this.quantity = quantity;
        this.foodOrder = foodOrder;
    }

    public OrderItemDto() {

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
        OrderItemDto that = (OrderItemDto) o;
        return quantity == that.quantity && Objects.equals(meal, that.meal) && Objects.equals(foodOrder, that.foodOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meal, quantity, foodOrder);
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "meal=" + meal +
                ", quantity=" + quantity +
                ", foodOrder=" + foodOrder +
                '}';
    }
}
