package lt.techin.FoodOrderApp;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@ImportResource({"classpath*:application-context.xml"})
@Configuration
public class AppConfig {




    @Bean
    PasswordEncoder encoder() {
        return   PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}