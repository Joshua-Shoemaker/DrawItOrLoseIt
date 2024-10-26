package com.gamingroom.gameauth.healthcheck;

import java.util.ArrayList;

import com.codahale.metrics.health.HealthCheck;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class AppHealthCheck extends HealthCheck {
	private final Client client;

	public AppHealthCheck(Client client) {
		super();
		this.client = client;
	}

	@Override
	protected Result check() throws Exception {
		WebTarget webTarget = client.target("http://localhost:8080/gameusers");
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		@SuppressWarnings("rawtypes")
		ArrayList gameusers = response.readEntity(ArrayList.class);
		if(gameusers !=null && gameusers.size() > 0){
			return Result.healthy();
		}
		return Result.unhealthy("API Failed");
	}
}