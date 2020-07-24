package posmy.interview.boot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Rashidi Zin
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/book/**").permitAll()
                .anyRequest().authenticated();
        

        // the following 2 rows are added to view h2 console on browser
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}
