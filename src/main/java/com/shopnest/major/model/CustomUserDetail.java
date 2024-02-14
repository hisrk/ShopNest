package com.shopnest.major.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//USER DETAILS COMING FROM SPRING SECURITY SO DEPENDENCY MUST BE ADDED
public class CustomUserDetail extends User implements UserDetails {



    //FOR INJEECTING USERNAME WE NEED CONSTRUCTOR OR SETTER METHDO

    public CustomUserDetail(User user){
        super(user);

    }

//YE SSARI METHOD JO NICHE HAI YE SPRING PROVIDE KARATAI HAI BUT JO USER KI DETAILS HAI VO HAME USER CLASS HI DEGI
    //JO HUMNE BNAI HAI TO VO SAARI DETAILS VAHI SE AEGI TO CONTRUCTOR KI REQUIREMENT HAI AUR VO SUPER SE INSTANTIATE HO JAGI

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //kis user ko kon kon si authorities hai aur roles provided hai is case me list hai iska matlap
        //user - user bhi ho sakta hai aur admin bhi

        List<GrantedAuthority> grantedAuthorityList=new ArrayList<>();

        super.getRoles().forEach(role->

                grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()))

                );

        return  grantedAuthorityList;
    }

    @Override
    public String getUsername() {
        return super.getEmail();//beacuase hum use hi username man rahe hai
    }
    @Override
    public String getPassword() {
        return super.getPassword();
    }//ye bhi humne die hai

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
