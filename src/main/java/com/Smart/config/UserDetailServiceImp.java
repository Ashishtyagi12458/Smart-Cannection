package com.Smart.config;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.Smart.dao.UserRepository;
import com.Smart.entites.User;

public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user from DB
        User user = userRepository.getUserDetails(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
       
        String dbRole = user.getRole();
        if (dbRole.equalsIgnoreCase("Role_user")) {
            dbRole = "USER";
        } else if (dbRole.equalsIgnoreCase("Role_admin")) {
            dbRole = "ADMIN";
        }

        String role = "ROLE_" + dbRole.toUpperCase();
        // convert role -> ROLE_XXX
        Collection<? extends GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(role)
        );
        System.out.println("Loaded user: " + user.getEmail());
        System.out.println("Role from DB: " + user.getRole());
        System.out.println("Authorities given: " + authorities);
        // return Spring Security user
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),         // username
                user.getPassword(),      // encoded password
                authorities              // roles/authorities
        );
    }
}
