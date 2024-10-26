package com.gamingroom.gameauth.auth;

import org.checkerframework.checker.nullness.qual.Nullable;

import io.dropwizard.auth.Authorizer;
import jakarta.ws.rs.container.ContainerRequestContext;

public class GameAuthorizer implements Authorizer<GameUser> 
{
    
	@Override
	public boolean authorize(GameUser user, String role, 
			@Nullable ContainerRequestContext containerRequiestContext) {
		
		// Authorize method.
    	return user.getRoles() != null && user.getRoles().contains(role);
	}
}

