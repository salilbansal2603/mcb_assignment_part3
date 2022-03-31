package com.nagarro.userLogin.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nagarro.userLogin.entity.User;

import dto.UsersDTO;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private UsersDTO user;

	private String userName;

	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Integer id, UsersDTO user, String userName, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.user = user;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserDetailsImpl(user.getUserId(), user.toDTO(), user.getUserName(), user.getUserName(), user.getPassword(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public UsersDTO getUser() {
		return this.user;
	}

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	public String getEmail() {
		return email;
	}
}