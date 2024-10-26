package com.gamingroom.gameauth;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.core.Configuration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class GameAuthConfiguration extends Configuration {
	
	@Valid
	@NotNull
	private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();
	
	@JsonProperty("jerseyClient")
	public JerseyClientConfiguration getJerseyClientConfiguration() {
		return jerseyClient;
	}
	
	@JsonProperty("jerseyClient")
	public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient) {
		this.jerseyClient = jerseyClient;
	}
}