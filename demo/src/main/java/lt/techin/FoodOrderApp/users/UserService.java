package lt.techin.FoodOrderApp.users;

import lt.techin.FoodOrderApp.Model.FoodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    @Lazy
    private SessionRegistry sessionRegistry;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepo.findByUserName(username);
        if (user == null)
            throw new UsernameNotFoundException(username + " not found");

        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(
                        "ROLE_"+user.getRole().name()));

    }

    @Transactional
    public void createUser(String username, String password,Role role) {

        if (userRepo.findAll().isEmpty()) {
            Users newUser = new Users();
            newUser.setRole(role);
            newUser.setUserName(username);

            newUser.setPassword(encoder.encode(password));
            userRepo.saveAndFlush(newUser);
        }

    }

    @Transactional
    public void createUser(String username, String password) {


        Users newUser = new Users();
        newUser.setRole(Role.USER);
        newUser.setUserName(username);

        newUser.setPassword(encoder.encode(password));
        userRepo.saveAndFlush(newUser);

    }

    @Transactional(readOnly = true)
    public Users findByUserName(String username) {
        return userRepo.findByUserName(username);
    }


    @Transactional
    public void deleteUser(String username) {
        Users user = userRepo.findByUserName(username);

        expireSession(user);
        userRepo.deleteByUserName(username);
    }

    public boolean usersUserNameIsUnique(String userName) {
        return userRepo.findAll()
                .stream()
                .noneMatch(user -> user.getUserName().equals(userName));
    }

    public List<Users> getAll() {
        return userRepo.findAll();
    }


    private void expireSession(Users user) {

        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object principal : principals) {
            UserDetails pUser = (UserDetails) principal;
            if (pUser.getUsername().equals(user.getUserName())) {
                for (SessionInformation activeSession : sessionRegistry.getAllSessions(principal, false)) {
                    activeSession.expireNow();
                }
            }
        }
    }

    public List<FoodOrder> getFoodOrdersByUserId(Long userId) {
        Users user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            List<FoodOrder> foodOrders = user.getFoodOrders();
            return foodOrders;
        } else {
            return Collections.emptyList();
        }
    }

}