package de.hsma.igt.sascha.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
@Table(name = "Airport")
public class Airport implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8773875876147220514L;

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy ="uuid2")
    private String A_ID;

    @Column
    private String A_NAME;

    @Column
    private String A_COUNTRY;

    public Airport(){}

    // Bi-directional relationship with Flightsegment
    @OneToMany(mappedBy="FS_START_AIRPORT")
    private List<Flightsegment> flightsegmentsStart = new ArrayList<>();

    @OneToMany(mappedBy="FS_GOAL_AIRPORT")
    private List<Flightsegment> flightsegmentsGoal = new ArrayList<>();;

    public Airport(String A_NAME, String A_COUNTRY) {
        this.A_NAME = A_NAME;
        this.A_COUNTRY = A_COUNTRY;
        //this.A_ID = (long)(Math.random() * 2000000);
    }

    public String getA_ID() {
        return A_ID;
    }
    public void setA_ID(String a_ID) {
        A_ID = a_ID;
    }
    public String getA_NAME() {
        return A_NAME;
    }
    public void setA_NAME(String a_NAME) {
        A_NAME = a_NAME;
    }
    public String getA_COUNTRY() {
        return A_COUNTRY;
    }

    public void setA_COUNTRY(String a_COUNTRY) {
        A_COUNTRY = a_COUNTRY;
    }

    public List<Flightsegment> getFlightsegmentsStart() {
        return flightsegmentsStart;
    }

    public void setFlightsegmentsStart(List<Flightsegment> flightsegmentsStart) {
        this.flightsegmentsStart = flightsegmentsStart;
    }

    public List<Flightsegment> getFlightsegmentsGoal() {
        return flightsegmentsGoal;
    }

    public void setFlightsegmentsGoal(List<Flightsegment> flightsegmentsGoal) {
        this.flightsegmentsGoal = flightsegmentsGoal;
    }
}
