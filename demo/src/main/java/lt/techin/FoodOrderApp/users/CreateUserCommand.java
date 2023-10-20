package lt.techin.FoodOrderApp.users;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class CreateUserCommand {

    @NotNull
    @Length(min = 1, max = 100)
    private String username;


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }



}