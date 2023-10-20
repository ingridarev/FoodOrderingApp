package lt.techin.FoodOrderApp.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<Users, Long> {

    Users findByUserName(String username);
    List<Users> findByRole(Role role);
    void deleteByUserName(String username);



}