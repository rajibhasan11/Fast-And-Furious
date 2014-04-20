package com.hasan.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SportsCar {

	// private static final long serialVersionUID = 2794297733350035774L;
	
	private long id;
	// private String uniqueid;
	private String model;
	private String manufacturer;
	private String style;
	private String origin;
	// private String years;
	
	public SportsCar() {

	}
	
	public SportsCar(long id, String model, String manufacturer, String style,
			String countryOfOrigin) {
		this.id = id;
		this.model = model;
		this.manufacturer = manufacturer;
		this.style = style;
		this.origin = countryOfOrigin;
	}

	public SportsCar(String model, String manufacturer, String style,
			String countryOfOrigin) {
		this.model = model;
		this.manufacturer = manufacturer;
		this.style = style;
		this.origin = countryOfOrigin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	
}
