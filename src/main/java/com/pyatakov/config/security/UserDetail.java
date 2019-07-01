package com.pyatakov.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/// return AuthorityUtils.createAuthorityList("ROLE_USER");                //, "EDIT_POST", "CREATE_POST"

public class UserDetail implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
    
}
