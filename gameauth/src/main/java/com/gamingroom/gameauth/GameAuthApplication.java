package com.gamingroom.gameauth;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gamingroom.gameauth.auth.GameAuthenticator;
import com.gamingroom.gameauth.auth.GameAuthorizer;
import com.gamingroom.gameauth.auth.GameUser;
import com.gamingroom.gameauth.controller.GameUserRESTController;
import com.gamingroom.gameauth.controller.RESTClientController;
import com.gamingroom.gameauth.healthcheck.AppHealthCheck;
import com.gamingroom.gameauth.healthcheck.HealthCheckController;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.client.Client;



public class GameAuthApplication extends Application<GameAuthConfiguration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameAuthApplication.class);

	@Override
	public void initialize(Bootstrap<GameAuthConfiguration> b) {
	}

	@Override
	public void run(GameAuthConfiguration c, Environment e) throws Exception 
	{
		
		LOGGER.info("Registering REST resources");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); // Create Validator.
		Validator validator = factory.getValidator(); // get validator
 
		// Register GameUserRESTController  and pass validator as parameter.
		e.jersey().register(new GameUserRESTController(validator)); 
		
		// Create create JerseyClient (DemoRESTClient). 
		final Client DemoRESTClient = new JerseyClientBuilder(e)
				.using(c.getJerseyClientConfiguration())
				.build(getName());
		
		// Inject JerseyClient into API Controller.
		e.jersey().register(new RESTClientController(DemoRESTClient));
	
		
		e.healthChecks().register("APIHealthCheck", new AppHealthCheck(DemoRESTClient));

		// Run multiple health checks
		e.jersey().register(new HealthCheckController(e.healthChecks()));
		
		//Setup Basic Security
		e.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<GameUser>()
                .setAuthenticator(new GameAuthenticator())
                .setAuthorizer(new GameAuthorizer())
                .setRealm("App Security")
                .buildAuthFilter()));
        e.jersey().register(new AuthValueFactoryProvider.Binder<>(GameUser.class));
        e.jersey().register(RolesAllowedDynamicFeature.class);
	}

	public static void main(String[] args) throws Exception {
		new GameAuthApplication().run(args);
	}
}