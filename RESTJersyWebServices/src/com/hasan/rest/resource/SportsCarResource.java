package com.hasan.rest.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.hasan.rest.crud.SportsCarCRUD;
import com.hasan.rest.model.SportsCar;

@Path("/sportscar")
public class SportsCarResource {

	/*private final static String MODEL = "model";
	private final static String MANUFACTURER = "manufacturer";
	private final static String STYLE = "style";
	private final static String COUNTRY_OF_ORIGIN = "countryOfOrigin";*/

	private SportsCar sportsCar = new SportsCar(null, null, null, null);
	private SportsCarCRUD sportsCarDao = new SportsCarCRUD();
	
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
		return "Demo service is okay!";
	}

	@GET
	@Path("sample/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SportsCar getSamplePersonByPersonId(@PathParam("id") int id) {
		
		// TODO
		sportsCar = sportsCarDao.selectById(id);

		System.out.println("Returning sample car: " + sportsCar.getModel()
				+ " " + sportsCar.getOrigin());

		return sportsCar;
	}
	
	@GET
	@Path("sample")
	@Produces(MediaType.APPLICATION_JSON)
	public SportsCar getSamplePerson() {
		
		// TODO
		sportsCar = sportsCarDao.selectById(1);

		System.out.println("Returning sample car: " + sportsCar.getModel()
				+ " " + sportsCar.getOrigin());

		return sportsCar;
	}
	
	@GET
	@Path("selectAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SportsCar> getAllPerson() {
		// TODO
		List<SportsCar> list = sportsCarDao.selectAll();
		
		return list;
	}

}