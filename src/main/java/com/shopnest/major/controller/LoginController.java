package com.shopnest.major.controller;




import com.shopnest.major.global.GlobalData;
import com.shopnest.major.model.Role;
import com.shopnest.major.model.User;
import com.shopnest.major.repository.RoleRepository;
import com.shopnest.major.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
public class LoginController {

  @Autowired(required = true)
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @GetMapping("/login")
  public String login() {
//why because whenever we are going to login page after logout we have to get this clear
    GlobalData.cart.clear();;
    return "login";
  }

  @GetMapping("/register")
  public String getRegistration() {

    return "register";


  }

  @PostMapping("/register")
  //WE  ARE USING THIS HTTPREQUEST BECAUSE WE WANT IF A USER LOGINS THEN IT WILL GET REDIRECTED TO THE inside login not again to login page
  //EXCEPTION THROW KARTA HAI
  public String newRegistration(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {

      String password= user.getPassword();

      user.setPassword(bCryptPasswordEncoder.encode(password));//this will encode password to load it in database

List<Role> role=  new ArrayList<>();

role.add(roleRepository.findById(2).get());
user.setRoles(role);

      //ab user ke pass sabkuch hai hi to directlt save kara denge

    userRepository.save(user);
    //request kar rahe hai ki login kara do
    //jiske basis ppe login hora hai
    request.login(user.getEmail(),password);//ENCRYPTED PASSWORD DENA HAI


  return "redirect:/";


  }




}
