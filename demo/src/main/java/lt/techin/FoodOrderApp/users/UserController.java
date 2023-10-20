package lt.techin.FoodOrderApp.users;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "user-service")
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//@PostMapping(path="/user/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//public ResponseEntity<CarWorkshopDto> createUser(String userName, String password) {
////    @RequestMapping(path="/user/create",method =  RequestMethod.POST)
////    public @ResponseBody ResponseEntity<Users> createUser(String userName, String password) {
//
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        if(!userService.usersUserNameIsUnique(userName))
//            return new ResponseEntity<Users>("Naudotojas egzistuoja", HttpStatus.BAD_REQUEST);
//
//        userService.createUser(userName, password);
//
//        return new ResponseEntity<Users>("Sukurtas naudotojas",HttpStatus.CREATED);
//    }
    @RequestMapping(path="/user/create",method =  RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> createUser(String userName, String password) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!userService.usersUserNameIsUnique(userName))
            return new ResponseEntity<String>("Naudotojas egzistuoja", HttpStatus.BAD_REQUEST);

        userService.createUser(userName, password);

        return new ResponseEntity<String>("Sukurtas naudotojas",HttpStatus.CREATED);

    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Users> getUsers() {
        return userService.getAll();
    }

    @GetMapping(value = "/{userName}",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public UserDto getUserByUserName(@PathVariable String userName) {
        UserDto userDto = new UserDto();
        Users user = userService.findByUserName(userName);
        userDto.setUserName(user.getUserName());
        userDto.setRole(user.getRole());
        return userDto;
    }

}