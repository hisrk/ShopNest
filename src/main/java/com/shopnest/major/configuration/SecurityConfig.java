package com.shopnest.major.configuration;

import com.shopnest.major.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


//Control +O gives the method present
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //here we are deleing with
    //1---->NORMAL LOGIN
    //2---->CUSTOM LOGIN
    //3---->GOOLE LOGIN LOGIN-->succeshandle create karna padega isme


    //AFTER COMPLETING GOLLE AUTH SUCESS HANDLER

    @Autowired
    private GoogleOAuthSuccessHandler googleOAuthSuccessHandler;

    //after Configuring CustomUserDetailService


    @Autowired(required = true)
    private CustomUserDetailService customUserDetailService;





    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/","/shop/**","/register").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/")   //ifSuccesful then go to this
                .usernameParameter("email")
                .passwordParameter("password")//because we domt handle the login login thats why we are telling sspring securit to do it for us
                .and()
                .oauth2Login()
                .loginPage("/login")
                .successHandler(googleOAuthSuccessHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")//THIS IS THE COOKE NAME THAT IS CREATED IN BROWSER
                .and()
                .exceptionHandling()
                .and()
                .csrf()
                .disable();


    }


    @Bean//BHUL GAYA THA YE WALA
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();

    }

    //whahtever the informations we are providing retrieve the info from databases
    //and build a custom user object and pass it.

    //how to do it


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //pass on the customUserDetailsService object is to be passed whiich will create
        //Custom user service model ka object create karege aur service layer me method ki hel se object return karaege
        auth.userDetailsService(customUserDetailService);
    }

    //static content security check


    @Override
    public void configure(WebSecurity web) throws Exception {
        //BASICALLY TELLING SECURITY TO IGNORE THERE ROUTES
        web.ignoring().antMatchers("/resources/**","/static/**","/images/**","/productImages/**","/css/**","/js/**");
    }
}