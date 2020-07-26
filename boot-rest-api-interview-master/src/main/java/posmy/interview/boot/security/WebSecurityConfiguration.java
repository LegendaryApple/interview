package posmy.interview.boot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import posmy.interview.boot.entities.enumeration.RoleEnum;

/**
 * @author Rashidi Zin
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/book").hasAnyRole(RoleEnum.LIBRARIAN.name())
                .antMatchers(HttpMethod.PUT, "/book").hasAnyRole(RoleEnum.LIBRARIAN.name())
                .antMatchers(HttpMethod.DELETE, "/book").hasAnyRole(RoleEnum.LIBRARIAN.name())
                .antMatchers(HttpMethod.GET, "/book").hasAnyRole(RoleEnum.LIBRARIAN.name(), RoleEnum.MEMBER.name())
                .antMatchers(HttpMethod.POST, "/users").hasAnyRole(RoleEnum.LIBRARIAN.name())
                .antMatchers(HttpMethod.PUT, "/users").hasAnyRole(RoleEnum.LIBRARIAN.name())
                .antMatchers(HttpMethod.DELETE, "/users").hasAnyRole(RoleEnum.LIBRARIAN.name(), RoleEnum.MEMBER.name())
                .antMatchers(HttpMethod.GET, "/users").hasAnyRole(RoleEnum.LIBRARIAN.name(), RoleEnum.MEMBER.name())
                .antMatchers(HttpMethod.POST, "/self-borrow").hasAnyRole(RoleEnum.LIBRARIAN.name(), RoleEnum.MEMBER.name())
                .antMatchers(HttpMethod.POST, "/through-librarian").hasAnyRole(RoleEnum.LIBRARIAN.name())
                .antMatchers(HttpMethod.POST, "/return").hasAnyRole(RoleEnum.LIBRARIAN.name(), RoleEnum.MEMBER.name())
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();
        

        // the following 2 rows are added to view h2 console on browser
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}
