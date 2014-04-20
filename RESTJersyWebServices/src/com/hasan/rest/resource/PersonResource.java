package com.hasan.rest.resource;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.hasan.rest.model.Person;
import com.hasan.rest.crud.PersonCRUD;

@Path("/person")
public class PersonResource {

	// private final static String ID = "idPerson";
	private final static String FIRST_NAME = "firstName";
	private final static String LAST_NAME = "lastName";
	private final static String EMAIL = "email";

	private Person person = new Person(null, null, null);
	private PersonCRUD persondao = new PersonCRUD();

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
		return "Demo service is ready!";
	}

	@GET
	@Path("sample/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getSamplePersonByPersonId(@PathParam("id") int id) {
		
		// TODO
		person = persondao.selectById(id);

		System.out.println("Returning sample person: " + person.getFirstName()
				+ " " + person.getLastName());

		return person;
	}
	
	@GET
	@Path("sample")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getSamplePerson() {
		
		// TODO
		person = persondao.selectById(1);

		System.out.println("Returning sample person: " + person.getFirstName()
				+ " " + person.getLastName());

		return person;
	}
	
	@GET
	@Path("selectAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> getAllPerson() {
		// TODO
		List<Person> list = persondao.selectAll();
		
		return list;
	}

	// Use data from the client source to create a new Person object, returned
	// in JSON format.
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Person postPerson(MultivaluedMap<String, String> personParams) {

		String firstName = personParams.getFirst(FIRST_NAME);
		String lastName = personParams.getFirst(LAST_NAME);
		String email = personParams.getFirst(EMAIL);
		
		System.out.println("Storing posted " + firstName + " " + lastName
				+ "  " + email);

		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setEmail(email);
	
		// TODO
		persondao.insert(person);

		System.out.println("person info: " + person.getFirstName() + " "
				+ person.getLastName() + " " + person.getEmail());

		return person;

	}
}