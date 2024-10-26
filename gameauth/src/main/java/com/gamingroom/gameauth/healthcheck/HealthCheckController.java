package com.gamingroom.gameauth.healthcheck;

import java.util.Map.Entry;
import java.util.Set;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Path("/status")
public class HealthCheckController 
{
	private HealthCheckRegistry registry;

	public HealthCheckController(HealthCheckRegistry registry) {
		this.registry = registry;
	}
	
	@GET
	public Set<Entry<String, Result>> getStatus(){
		return registry.runHealthChecks().entrySet();
	}
}