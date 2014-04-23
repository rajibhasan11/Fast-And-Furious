package com.hasan.rest.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.hasan.rest.crud.UserCRUD;
import com.hasan.rest.model.User;

@Path("/user")
public class UserResource {

	// final static String ID = "idUser";
	final static String FIRST_NAME = "firstName";
	final static String LAST_NAME = "lastName";
	final static String EMAIL = "email";
	final static String PASSWORD = "password";

	private User user = new User(null, null, null, null);
	private UserCRUD usercrud = new UserCRUD();

	// The @Context annotation allows us to have certain contextual objects
	// injected into this class.
	// UriInfo object allows us to get URI information (no kidding).
	@Context
	UriInfo uriInfo;

	// Another "injected" object. This allows us to use the information that's
	// part of any incoming request.
	// We could, for example, get header information, or the requestor's
	// address.
	@Context
	Request request;

	// Basic "is the service running" test
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String respondAsReady() {
		return "Demo user service is ready!";
	}

	@GET
	@Path("sample/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getSampleUserByUserId(@PathParam("id") int id) {
		
		// TODO
		user = usercrud.selectById(id);

		System.out.println("Returning sample user: " + user.getFirstName()
				+ " " + user.getLastName());

		return user;
	}
	
	@GET
	@Path("sample")
	@Produces(MediaType.APPLICATION_JSON)
	public User getSampleUser() {
		
		// TODO
		user = usercrud.selectById(11);

		System.out.println("Returning sample user: " + user.getFirstName()
				+ " " + user.getLastName());

		return user;
	}
	
	@GET
	@Path("selectAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUser() {
		// TODO
		List<User> list = usercrud.selectAll();
		
		return list;
	}

	// Use data from the client source to create a new User object, returned
	// in JSON format.
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User postUser(MultivaluedMap<String, String> userParams) {

		String email = userParams.getFirst(EMAIL);
		String password = userParams.getFirst(PASSWORD);
		
		System.out.println("Posted data: " + email + "  " + password);
		
		// TODO
		User gotUser = usercrud.selectByEmailPassword(email, password);
		
		if(gotUser != null) {
			return gotUser;
		}

		return null;

	}
}