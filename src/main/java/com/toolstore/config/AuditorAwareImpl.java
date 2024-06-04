package com.toolstore.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuditorAwareImpl implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		Optional<String> defaultReturn = Optional.ofNullable("Tools");

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return defaultReturn;
		}

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails != null && userDetails.getUsername() != null) {
			return Optional.ofNullable(userDetails.getUsername());
		}

		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
	}

}