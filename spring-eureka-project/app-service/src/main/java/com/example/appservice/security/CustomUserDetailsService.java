package com.example.appservice.security;

import com.example.appservice.model.Admin;
import com.example.appservice.model.AppOwner;
import com.example.appservice.model.User;
import com.example.appservice.repository.AdminRepository;
import com.example.appservice.repository.AppOwnerRepository;
import com.example.appservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AppOwnerRepository appOwnerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1) Try Admin
        Optional<Admin> adminOpt = adminRepository.findByUserName(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            GrantedAuthority authority =
                    new SimpleGrantedAuthority("ROLE_ADMIN");
            return new org.springframework.security.core.userdetails.User(
                    admin.getUserName(),
                    admin.getPassword(),
                    Collections.singleton(authority)
            );
        }

        // 2) Try AppOwner  (AppOwnerRepository returns AppOwner, not Optional)
        AppOwner owner = appOwnerRepository.findByUserName(username);
        if (owner != null) {
            GrantedAuthority authority =
                    new SimpleGrantedAuthority("ROLE_OWNER");
            return new org.springframework.security.core.userdetails.User(
                    owner.getUserName(),
                    owner.getPassword(),
                    Collections.singleton(authority)
            );
        }

        // 3) Try normal User
        Optional<User> userOpt = userRepository.findByUserName(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            GrantedAuthority authority =
                    new SimpleGrantedAuthority("ROLE_USER");
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    Collections.singleton(authority)
            );
        }

        // 4) Not found
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
