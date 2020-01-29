package authorization.config;

import authorization.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void globalUserDetails(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter(), SessionManagementFilter.class)
                .authorizeRequests()
                //.antMatchers("/login").permitAll()
                .antMatchers("/oauth/token/revokeById/**").permitAll()
                .antMatchers("/tokens/**").permitAll()
                .and().csrf().disable();
    }

    @Bean
    CorsFilter corsFilter() {
        return new CorsFilter();
    }
}