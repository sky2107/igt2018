package de.hsma.igt.sascha.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Indexed;

@Entity@Indexed
@Table(name = "MyCustomer")
public class MyCustomer implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8957907817189823085L;
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy ="uuid2")
    private String C_ID;
    @Column
    private String C_ADDR;
    @Column
    private String C_STATUS = Status.NONE.name();
    @Column
    private String C_NAME;
    @Column
    private Double C_FLOWN_MILES;
    @Column
    private Double C_CURRENT_FLOWN_MILES;
    
    @Column
    private String C_PHONE;

    @ManyToMany(cascade = { CascadeType.ALL },fetch = FetchType.EAGER)
    @JoinTable(
            name = "booked_flights",
            joinColumns = { @JoinColumn(name = "C_ID") },
            inverseJoinColumns = { @JoinColumn(name = "F_ID") }
    )
    private Set<Flight> bookedFlights = new HashSet<>();
    
    public MyCustomer() {}

    public MyCustomer(String C_ADDR, String C_NAME, String[] C_PHONE) {
        this.C_ADDR = C_ADDR;
        this.C_NAME = C_NAME;
        String concatenatedPhones ="";
        for(String phone : C_PHONE) {
        	if(concatenatedPhones.equals("")) {
        		concatenatedPhones = phone;
        	} else {
            	concatenatedPhones = concatenatedPhones+","+phone;
        	}
        }
        this.C_PHONE = concatenatedPhones;
        this.C_FLOWN_MILES = 0D;
        this.C_CURRENT_FLOWN_MILES = 0D;
        //this.C_ID = (long)(Math.random() * 2000000);
    }

    public String getC_ID() {
        return C_ID;
    }

    public void setC_ID(String c_ID) {
        C_ID = c_ID;
    }

    public String getC_STATUS() {
        return C_STATUS;
    }

    public void setC_STATUS(String c_STATUS) {
        C_STATUS = c_STATUS;
    }

    public Double getC_FLOWN_MILES() {
        return C_FLOWN_MILES;
    }

    public void setC_FLOWN_MILES(Double c_FLOWN_MILES) {
        C_FLOWN_MILES = c_FLOWN_MILES;
    }

    public Double getC_CURRENT_FLOWN_MILES() {
        return C_CURRENT_FLOWN_MILES;
    }

    public void setC_CURRENT_FLOWN_MILES(Double c_CURRENT_FLOWN_MILES) {
        C_CURRENT_FLOWN_MILES = c_CURRENT_FLOWN_MILES;
    }

    public String getC_PHONE() {
        return C_PHONE;
    }

    public void setC_PHONE(String c_PHONE) {
        this.C_PHONE = c_PHONE;
    }

    public String getC_ADDR() {
        return C_ADDR;
    }

    public void setC_ADDR(String c_ADDR) {
        C_ADDR = c_ADDR;
    }

    public String getC_NAME() {
        return C_NAME;
    }

    public void setC_NAME(String c_NAME) {
        C_NAME = c_NAME;
    }

	public Set<Flight> getBookedFlights() {
        return bookedFlights;
    }

    public void setBookedFlights(Set<Flight> bookedFlights) {
        this.bookedFlights = bookedFlights;
    }

}
