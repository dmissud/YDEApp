package org.yde.ydeapp.exposition.security.jwt;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;

	private final String jwttoken;

	private final List<String> grants;
	public JwtResponse(final String jwttoken, Collection<? extends GrantedAuthority> authorities) {
		this.jwttoken = jwttoken;
		this.grants = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}

	public String getToken() {
		return jwttoken;
	}
	public List<String> getGrants() {
		return grants;
	}


}
