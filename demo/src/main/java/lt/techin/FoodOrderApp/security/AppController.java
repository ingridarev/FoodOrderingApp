package lt.techin.FoodOrderApp.security;
import lt.techin.FoodOrderApp.users.Users;
import lt.techin.FoodOrderApp.users.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AppController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/api/v1/loggedUser")
    public ResponseEntity<String> getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = userRepo.findByUserName(username);
//            UserDto userDto = new UserDto();
//            userDto.setUserName(user.getUserName());
//            userDto.setRole(user.getRole());
            return new ResponseEntity<>(username, HttpStatus.OK);

        }

        return new ResponseEntity<String>("unauthorized", HttpStatus.UNAUTHORIZED);



    }
}