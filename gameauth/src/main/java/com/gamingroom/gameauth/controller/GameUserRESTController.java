package com.gamingroom.gameauth.controller;
 
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

import com.gamingroom.gameauth.auth.GameUser;
import com.gamingroom.gameauth.dao.GameUserDB;
import com.gamingroom.gameauth.representations.GameUserInfo;

import io.dropwizard.auth.Auth;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
// Add path annotation for gameusers.
@Path("/gameusers")
@Produces(MediaType.APPLICATION_JSON)
public class GameUserRESTController {
 
    private final Validator validator;
 
    public GameUserRESTController(Validator validator) {
        this.validator = validator;
    }
 
    @PermitAll
    @GET
    public Response getGameUsers(@Auth GameUser user) {
        return Response.ok(GameUserDB.getGameUsers()).build();
    }
 
    // Add RolesAllowed annotation for USER based on BasicAuth Security Example.

    @GET
    @Path("/{id}")
    @RolesAllowed({"USER"})
    public Response getGameUserById(@PathParam("id") Integer id,@Auth GameUser user) {
    	//You can validate here if user is watching his record
    	/*if(id != user.getId()){
    			//Not allowed
    	}*/
        GameUserInfo gameUserInfo = GameUserDB.getGameUser(id);
        if (gameUserInfo != null)
            return Response.ok(gameUserInfo).build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }


    
    // RolesAllowed annotation for ADMIN.
    @POST
    @RolesAllowed({"ADMIN"})
    public Response createGameUser(GameUserInfo gameUserInfo, @Auth GameUser user) throws URISyntaxException {
        // validation
        Set<ConstraintViolation<GameUserInfo>> violations = validator.validate(gameUserInfo);
        GameUserInfo e = GameUserDB.getGameUser(gameUserInfo.getId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<GameUserInfo> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            GameUserDB.updateGameUser(gameUserInfo.getId(), gameUserInfo);
            return Response.created(new URI("/gameusers/" + gameUserInfo.getId()))
                    .build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }
 
    @PUT
    @Path("/{id}")
    public Response updateGameUserById(@PathParam("id") Integer id, GameUserInfo gameUserInfo) {
        // validation
        Set<ConstraintViolation<GameUserInfo>> violations = validator.validate(gameUserInfo);
        GameUserInfo e = GameUserDB.getGameUser(gameUserInfo.getId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<GameUserInfo> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            gameUserInfo.setId(id);
            GameUserDB.updateGameUser(id, gameUserInfo);
            return Response.ok(gameUserInfo).build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }
 
    @DELETE
    @Path("/{id}")
    public Response removeGameUserById(@PathParam("id") Integer id) {
        GameUserInfo gameUserInfo = GameUserDB.getGameUser(id);
        if (gameUserInfo != null) {
            GameUserDB.removeGameUser(id);
            return Response.ok().build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }
}