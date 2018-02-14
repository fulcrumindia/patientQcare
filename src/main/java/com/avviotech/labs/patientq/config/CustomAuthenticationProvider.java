package com.avviotech.labs.patientq.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.avviotech.labs.patientq.repository.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserRepository userRepository;
	
	public CustomAuthenticationProvider() {
		super();
	}

	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		String username = String.valueOf(auth.getName());
		String password = String.valueOf(auth.getCredentials().toString());

		com.avviotech.labs.patientq.dto.User u = userRepository.findByUser(username, password);
		if(u != null)
		{
			final List<GrantedAuthority> grantedAuths = new ArrayList<>();
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			final UserDetails principal = new User(username, password, grantedAuths);
			final Authentication authentication = new UsernamePasswordAuthenticationToken(
					principal, password, grantedAuths);

			return authentication;
		}
		return null;
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication));
	}

}