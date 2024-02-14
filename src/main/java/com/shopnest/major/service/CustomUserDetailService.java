package com.shopnest.major.service;

import com.shopnest.major.model.User;
import com.shopnest.major.model.CustomUserDetail;
import com.shopnest.major.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailService implements UserDetailsService {

    //databases me present hai ki nahi

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user=userRepository.findUserByEmail(email);

        user.orElseThrow(()->new UsernameNotFoundException("user not found with this email"));




                  return user.map(CustomUserDetail::new).get() ;

    }
}
