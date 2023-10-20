package lt.techin.FoodOrderApp.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lt.techin.FoodOrderApp.users.Users;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    private Double totalAmount;

    @OneToMany(mappedBy = "foodOrder", cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems = new ArrayList<>();

    private Boolean isConfirmed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;


    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

    public FoodOrder() {
    }

    public FoodOrder(Long id) {
        this.id = id;
    }

    public FoodOrder(Long id, LocalDateTime createdDate, Double totalAmount, List<OrderItem> orderItems, Boolean isConfirmed, Users user) {
        this.id = id;
        this.createdDate = createdDate;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
        this.isConfirmed = isConfirmed;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        FoodOrder foodOrder = (FoodOrder) o;
        return Objects.equals(id, foodOrder.id) && Objects.equals(createdDate, foodOrder.createdDate) && Objects.equals(totalAmount, foodOrder.totalAmount) && Objects.equals(orderItems, foodOrder.orderItems) && Objects.equals(isConfirmed, foodOrder.isConfirmed) && Objects.equals(user, foodOrder.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, totalAmount, orderItems, isConfirmed, user);
    }

    @Override
    public String toString() {
        return "FoodOrder{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", totalAmount=" + totalAmount +
                ", orderItems=" + orderItems +
                ", isConfirmed=" + isConfirmed +
                ", user=" + user +
                '}';
    }
}