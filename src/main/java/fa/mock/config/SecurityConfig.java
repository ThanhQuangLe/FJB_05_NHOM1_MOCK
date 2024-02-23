package fa.mock.config;

import fa.mock.entities.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/img/**", "/css/**", "/js/**").permitAll()
                .requestMatchers("/home/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.name(), RoleEnum.ROLE_USER.name(), RoleEnum.ROLE_EMPLOYEE.name())
                .requestMatchers("/vaccine-type-create", "/vaccine-type-update", "/vaccine-type-updatestatus",
                        "/vaccine-create", "/vaccine-update", "/customer-update", "/customer-create", "/vaccine-updatestatus",
                        "/customer-delete", "/customer-get").hasAuthority(RoleEnum.ROLE_ADMIN.name())
                .requestMatchers("/vaccineResult-create","/report-customer",
                        "/reportcustomer", "/report-vaccine", "/reportVaccine"
                        ,"/VaccineResult-Report","/VaccineResult-ReportYear","/vaccineResult-update"
                        ,"/vaccineResult-delete", "/customer-list", "/vaccine-list", "/vaccine-type-list").hasAnyAuthority(RoleEnum.ROLE_ADMIN.name(), RoleEnum.ROLE_EMPLOYEE.name())
                .anyRequest().authenticated()
        ).formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login-check")
                .permitAll()
        ).logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        ).csrf(csrf -> csrf.disable());
        // ...
        return http.build();
    }

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public PasswordEncoder paswordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
