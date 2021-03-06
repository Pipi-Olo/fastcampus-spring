package com.pipiolo.security.config;

import com.pipiolo.security.filter.StopwatchFilter;
import com.pipiolo.security.filter.TesterAuthenticationFilter;
import com.pipiolo.security.jwt.JwtAuthenticationFilter;
import com.pipiolo.security.jwt.JwtAuthorizationFilter;
import com.pipiolo.security.user.User;
import com.pipiolo.security.user.UserRepository;
import com.pipiolo.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // add custom filters
        http.addFilterBefore(
                new StopwatchFilter(),
                WebAsyncManagerIntegrationFilter.class
        );

        http.addFilterBefore(
                new TesterAuthenticationFilter(this.authenticationManager()),
                UsernamePasswordAuthenticationFilter.class
        );

        // basic authentication
        http.httpBasic().disable(); // basic authentication filter ????????????
        // csrf
        http.csrf().disable();
        // remember-me
        http.rememberMe().disable();
        // stateless
        http.sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // jwt filter
        http.addFilterBefore(
                new JwtAuthenticationFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class
        ).addFilterBefore(
                new JwtAuthorizationFilter(userRepository),
                BasicAuthenticationFilter.class
        );

        // authorization
        http.authorizeRequests()
                // /??? /home??? ???????????? ??????
                .antMatchers("/", "/home", "/signup").permitAll()
                // hello ???????????? USER ?????? ?????? ??????????????? ??????
                .antMatchers("/note").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/notice").authenticated()
                .antMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/notice").hasRole("ADMIN")
                .anyRequest().authenticated(); // ?????????
        // login
        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll(); // ?????? ??????
        // logout
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/");
    }

    @Override
    public void configure(WebSecurity web) {
//        ?????? ????????? spring security ???????????? ??????
//        web.ignoring().antMatchers("/images/**", "/css/**");
//        ?????? ????????? ?????? ???????????????.

//        permitAll??? ?????? ????????? ????????????.
//        ?????????, ?????? ?????? permitAll??? ?????? ????????? ???????????? ?????????
//        ignoring??? ?????? ????????? ????????? ??? (?????? ??????)
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException(username);
            }
            return user;
        };
    }
}
