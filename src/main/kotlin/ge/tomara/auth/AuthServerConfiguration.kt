package ge.tomara.auth

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class AuthServerConfiguration: WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http!!
            .authorizeRequests()
                .antMatchers("/admin/**", "/api/admin/**").authenticated()
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
    }

}
