package lt.techin.FoodOrderApp.api.dto;


import lt.techin.FoodOrderApp.Model.OrderItem;
import lt.techin.FoodOrderApp.users.Users;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FoodOrderDto {

    private LocalDateTime createdDate;
    private Double totalAmount;

    private List<OrderItem> orderItems;

    private Boolean isConfirmed;

    private Users user;

    public FoodOrderDto() {
    }

    public FoodOrderDto(LocalDateTime createdDate, Double totalAmount, List<OrderItem> orderItems, Boolean isConfirmed, Users user) {
        this.createdDate = createdDate;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
        this.isConfirmed = isConfirmed;
        this.user = user;
    }

    public FoodOrderDto(LocalDateTime createdDate, Boolean isConfirmed) {
        this.createdDate = createdDate;
        this.isConfirmed = isConfirmed;
    }

    public FoodOrderDto(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodOrderDto that = (FoodOrderDto) o;
        return Objects.equals(createdDate, that.createdDate) && Objects.equals(totalAmount, that.totalAmount) && Objects.equals(orderItems, that.orderItems) && Objects.equals(isConfirmed, that.isConfirmed) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdDate, totalAmount, orderItems, isConfirmed, user);
    }

    @Override
    public String toString() {
        return "FoodOrderDto{" +
                "createdDate=" + createdDate +
                ", totalAmount=" + totalAmount +
                ", orderItems=" + orderItems +
                ", isConfirmed=" + isConfirmed +
                ", user=" + user +
                '}';
    }
}