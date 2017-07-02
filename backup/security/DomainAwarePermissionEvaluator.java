package com.test.infra.user.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;

@Component
@Slf4j
class DomainAwarePermissionEvaluator implements PermissionEvaluator {

	@Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

		log.info("check permission '{}' for user '{}' for target '{}'", permission, authentication.getName(),
				targetDomainObject);



		return true;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
								 Object permission) {
		return hasPermission(authentication, new DomainObjectReference(targetId, targetType), permission);
	}

	private boolean hasRole(String role, Authentication auth) {

		if (auth == null || auth.getPrincipal() == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		if (CollectionUtils.isEmpty(authorities)) {
			return false;
		}

		return authorities.stream().filter(ga -> role.equals(ga.getAuthority())).findAny().isPresent();
	}

	@Data
	@RequiredArgsConstructor
	static class DomainObjectReference {
		private final Serializable targetId;
		private final String targetType;
	}
}