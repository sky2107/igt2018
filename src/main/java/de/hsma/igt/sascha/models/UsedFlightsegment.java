package de.hsma.igt.sascha.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "UsedFlightsegment")
public class UsedFlightsegment implements Serializable{
	public UsedFlightsegment() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6493549068437391722L;
	
	public UsedFlightsegment(Flight flight, Flightsegment flightsegment, Airport startingAirport, Airport destinationAirport) {
		super();
		this.startingAirport = startingAirport.getA_NAME();
		this.destinationAirport = destinationAirport.getA_NAME();
		this.flight = flight;
		this.flightsegment = flightsegment;
	}

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy ="uuid2")
    private String US_ID;
	
	@Column
	private String startingAirport;
	@Column
	private String destinationAirport;
	
	
    @ManyToOne
    @JoinColumn(name = "F_ID")
    private Flight flight;
    
	
    @ManyToOne
    @JoinColumn(name = "FS_ID")
    private Flightsegment flightsegment;

	public String getUS_ID() {
		return US_ID;
	}

	public void setUS_ID(String uS_ID) {
		US_ID = uS_ID;
	}

	public String getStartingAirport() {
		return startingAirport;
	}

	public void setStartingAirport(String startingAirport) {
		this.startingAirport = startingAirport;
	}

	public String getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Flightsegment getFlightsegment() {
		return flightsegment;
	}

	public void setFlightsegment(Flightsegment flightsegment) {
		this.flightsegment = flightsegment;
	}

}
