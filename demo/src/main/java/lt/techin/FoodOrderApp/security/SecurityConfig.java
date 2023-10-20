package lt.techin.FoodOrderApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private SecurityEntryPoint securityEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        //var authenticationManager = authenticationManagerBuilder.build();

        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers("/", "/swagger-ui/","/api/loggedUser","/api/**")
                .permitAll()
                .antMatchers( "/h2", "/h2/**").authenticated()
                .and().formLogin()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {

                        Authentication user = SecurityContextHolder.getContext().getAuthentication();

                        String username = user.getName();
                        Object[] roles = user.getAuthorities().toArray();
                        String role = roles[0].toString().substring(5);

                        response.setHeader("Access-Control-Allow-Credentials", "true");
                        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
                        response.setHeader("Content-Type", "application/json;charset=UTF-8");
                        response.getWriter().print("{\"username\": \"" + username + "\", \"role\":\"" + role + "\"}");

                    }

                }).failureHandler(new AuthenticationFailureHandler() {

                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                        AuthenticationException exception) throws IOException, ServletException {
                        String username = request.getParameter("username");

                        response.sendError(401, "Neteisingas prisijungimo vardas ir/arba slaptažodis");
                    }
                }).loginPage("/login").permitAll()

                .and().logout().logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true)
                .deleteCookies("JSESSIONID").logoutSuccessUrl("/").permitAll()

                .and()

                .exceptionHandling().authenticationEntryPoint(securityEntryPoint).and().headers().frameOptions()
                .sameOrigin() // arba deny
                .and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false)
                .expiredUrl("/api/loggedUser").sessionRegistry(sessionRegistry());

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*").allowedOriginPatterns("*").allowCredentials(true);
            }
        };
    }

}