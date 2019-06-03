package com.example.tutorial.clientproxy;

import com.example.tutorial.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public interface UserResourceV1 {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<User> fetchUsers(@QueryParam("gender") String gender);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userId}")
    Response fetchUser(@PathParam("userId") UUID userId);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response insertNewUser (User user);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateUser (User user);

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userId}")
    Response deleteUser(@PathParam("userId") UUID userId);
}

