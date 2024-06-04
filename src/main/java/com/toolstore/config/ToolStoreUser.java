package com.toolstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ToolStoreUser implements UserDetailsService {
	@Autowired
	@Qualifier("IoPasswordEncoder")
	private PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		if (username.equals("user")) {
			return User.builder().passwordEncoder(encoder::encode) //
					.username("frodo").password("ring").roles("USER").build();
		}

		if (username.equals("admin")) {
			return User.builder().passwordEncoder(encoder::encode) //
					.username("gandalf").password("mellon").roles("ADMIN", "USER").build();
		}

		return null;
	}

}