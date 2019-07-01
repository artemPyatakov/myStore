package com.pyatakov.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig
        extends WebSecurityConfigurerAdapter {

    private Logger logger =
            LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private DataSource dataSource;


    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) {
        try {

            auth.jdbcAuthentication().dataSource(dataSource)
                    .usersByUsernameQuery("select username,password, enabled from users where username=?")
                    .authoritiesByUsernameQuery("select username, authority from authorities where username=?")
                    .passwordEncoder(passwordEncoder());

        } catch (Exception e){
            logger.error("Could not configure authentication!", e);
        }
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
                                                         
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.sessionManagement()
               .and()
               .authorizeRequests()
               .antMatchers("/public/**").permitAll()
               .antMatchers("/category/public/**").permitAll()
               .antMatchers("/product/public/**").permitAll()
               .antMatchers("/basket/public/**").hasAnyRole("USER","ADMIN")
               .antMatchers("/order/public/create").hasAnyRole("USER","ADMIN")
               .antMatchers("/*/private/**").hasRole("ADMIN").anyRequest().authenticated()
               .and()
               .formLogin()
               .and()
               .logout()
               .and()
               .httpBasic()
               .and()
               .csrf().disable();
    }

}
