package com.shopnest.major.configuration;


import com.shopnest.major.model.Role;
import com.shopnest.major.model.User;
import com.shopnest.major.repository.RoleRepository;
import com.shopnest.major.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//forhandling gamil succes handler
@Service
public class GoogleOAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    //INTERNALLY REDIRECTION KE LIE USE KAR SAKTE HAI
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //OAUTH authentication token ko leker aege agr authgentication succesfull ho jata hai to
        //that mean google validated the id and password in database then google [provide
        //us with a token and now we have to receeive this token
        //and who is providing token in above case
        //Authentiocation authentication-->receives the token and we are oprtaing on that token
//Object casting from comimg token to OAuth2Authentication token is a class that is present in
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
//token ke opass email hai ab usko niklna hai qki data base me woh check karega ke user hai to tredirect karega
        //nahi to nahi
        //principal user ko hi bolte hai
        //get me key de rahe hai
        String email = token.getPrincipal().getAttributes().get("email").toString();

        //NOW FINDING USER ON BASIS OF EMAIL

        if (userRepository.findUserByEmail(email).isPresent()) {

            //providing nothing

        } else {
            //new user banao usko

            User user = new User();

            user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
            user.setEmail(email);
            List<Role> roles = new ArrayList<>();

            roles.add(roleRepository.findById(2).get());

            user.setRoles(roles);

            userRepository.save(user);


        }

        redirectStrategy.sendRedirect(request,response,"/");//this is same as rd.forward that we used in servlet but this is another method


        //AFTER THIS OUR GOOGLEAUTHSUCESSHANDLER IS COMPLETED

    }
}