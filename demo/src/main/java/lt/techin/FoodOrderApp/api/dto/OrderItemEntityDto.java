package lt.techin.FoodOrderApp.api.dto;

import lt.techin.FoodOrderApp.Model.FoodOrder;
import lt.techin.FoodOrderApp.Model.Meal;

import java.util.Objects;

public class OrderItemEntityDto extends OrderItemDto{

    private Long id;

    public OrderItemEntityDto(Meal meal, int quantity, FoodOrder foodOrder, Long id) {
        super(meal, quantity, foodOrder);
        this.id = id;
    }

    public OrderItemEntityDto(Long id) {
        this.id = id;
    }

    public OrderItemEntityDto() {
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
        OrderItemEntityDto that = (OrderItemEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderItemEntityDto{" +
                "id=" + id +
                '}';
    }
}
