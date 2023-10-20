package lt.techin.FoodOrderApp.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lt.techin.FoodOrderApp.Model.FoodOrder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Users {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    @NotBlank
    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<FoodOrder> foodOrders = new ArrayList<>();

    public Users() {

    }

    public Users(Long userId, String userName, String password, Role role, List<FoodOrder> foodOrders) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.foodOrders = foodOrders;
    }

    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String username) {
        this.userName = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }

    public Role getRole() {
        return this.role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    public void setFoodOrders(List<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }
}