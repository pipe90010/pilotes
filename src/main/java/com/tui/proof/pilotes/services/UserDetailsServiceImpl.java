package com.tui.proof.pilotes.services;

import java.util.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tui.proof.pilotes.dto.Role;
import com.tui.proof.pilotes.dto.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		var user = new User();
		user.setUserename("username");
		user.setPassword("password");
		Set<Role> roles = new HashSet<>();
		var role = new Role();
		role.setId("1");
		role.setName("ADMIN");
		roles.add(role);
		user.setRoles(roles);

		return UserDetailsImpl.build(user);
	}

}