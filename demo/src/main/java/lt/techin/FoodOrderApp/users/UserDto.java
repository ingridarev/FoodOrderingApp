package lt.techin.FoodOrderApp.users;

import lt.techin.FoodOrderApp.Model.FoodOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDto extends  Users{
    String username;

    Role role;

    private List<FoodOrder> foodOrders = new ArrayList<>();

    public UserDto(){

    }

    public UserDto(Long userId, String username, String password, Role role, List<FoodOrder> foodOrders) {
        super(userId, username, password, role,foodOrders);
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public List<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    @Override
    public void setFoodOrders(List<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(username, userDto.username) && role == userDto.role && Objects.equals(foodOrders, userDto.foodOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role, foodOrders);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", role=" + role +
                ", foodOrders=" + foodOrders +
                '}';
    }
}