package de.hsma.igt.sascha.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Flightsegment")
public class Flightsegment implements Serializable {
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy ="uuid2")
    private String FS_ID;
    
    public String getFS_NAME() {
		return FS_NAME;
	}

	public void setFS_NAME(String fS_NAME) {
		FS_NAME = fS_NAME;
	}


    @Column
    private String FS_NAME;
    
    @Column
    private Double FS_DISTANCE;
    
//    @ManyToMany(mappedBy = "usedFlightsegments", fetch = FetchType.EAGER)
//    private Set<Flight> flights = new HashSet<>();

//    @ManyToOne(fetch = FetchType.LAZY) // LAZY
//    @JoinColumn(name = "flight_id")
//    private Flight flight;
    
    @OneToMany(mappedBy="flightsegment", fetch = FetchType.EAGER)
    private List<UsedFlightsegment> usedFlightsegment = new ArrayList<>();

    @ManyToOne(optional=false)
    @JoinColumn(name = "FS_START_AIRPORT_ID", referencedColumnName="A_ID")
    private Airport FS_START_AIRPORT;

    @ManyToOne(optional=false)
    @JoinColumn(name = "FS_GOAL_AIRPORT_ID", referencedColumnName="A_ID")
    private Airport FS_GOAL_AIRPORT;

    public Flightsegment() {}

    public Flightsegment(Airport FS_START_AIRPORT, Airport FS_GOAL_AIRPORT, double FS_DISTANCE) {
        this.FS_START_AIRPORT = FS_START_AIRPORT;
        this.FS_GOAL_AIRPORT = FS_GOAL_AIRPORT;
        this.FS_DISTANCE = FS_DISTANCE;
        this.FS_NAME = "flightsegment"+Math.random();
        //this.FS_ID = (long)(Math.random() * 2000000);
    }

    public String getFS_ID() {
		return FS_ID;
	}

	public void setFS_ID(String fS_ID) {
		FS_ID = fS_ID;
	}

	public Double getFS_DISTANCE() {
        return FS_DISTANCE;
    }

    public void setFS_DISTANCE(Double FS_DISTANCE) {
        this.FS_DISTANCE = FS_DISTANCE;
    }

    public Airport getFS_START_AIRPORT() {
        return FS_START_AIRPORT;
    }

    public void setFS_START_AIRPORT(Airport FS_START_AIRPORT) {
        this.FS_START_AIRPORT = FS_START_AIRPORT;
    }

    public Airport getFS_GOAL_AIRPORT() {
        return FS_GOAL_AIRPORT;
    }

    public void setFS_GOAL_AIRPORT(Airport FS_GOAL_AIRPORT) {
        this.FS_GOAL_AIRPORT = FS_GOAL_AIRPORT;
    }

    public List<UsedFlightsegment> getUsedFlightsegment() {
		return usedFlightsegment;
	}

	public void setUsedFlightsegment(UsedFlightsegment usedFlightsegment) {
		this.usedFlightsegment.add(usedFlightsegment);
	}

//	public Flight getFlight() {
//        return flight;
//    }
//
//    public void setFlight(Flight flight) {
//        this.flight = flight;
//    }
//
//	public Set<Flight> getFlights() {
//		return flights;
//	}
//
//	public void setFlights(Set<Flight> flights) {
//		this.flights = flights;
//	}
}

