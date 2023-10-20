package lt.techin.FoodOrderApp.api.dto;


import lt.techin.FoodOrderApp.Model.OrderItem;
import lt.techin.FoodOrderApp.users.Users;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FoodOrderEntityDto extends FoodOrderDto {

    private Long id;

    public FoodOrderEntityDto() {
    }

    public FoodOrderEntityDto(Long id) {
        this.id = id;
    }

    public FoodOrderEntityDto(LocalDateTime createdDate, Double totalAmount, List<OrderItem> orderItems, Boolean isConfirmed, Users user, Long id) {
        super(createdDate, totalAmount, orderItems, isConfirmed, user);
        this.id = id;
    }

    public FoodOrderEntityDto(LocalDateTime createdDate,Boolean isConfirmed, Long id) {
        super(createdDate, isConfirmed);
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
        FoodOrderEntityDto that = (FoodOrderEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "FoodOrderEntityDto{" +
                "id=" + id +
                '}';
    }
}


