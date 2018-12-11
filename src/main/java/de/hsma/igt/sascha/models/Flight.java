package de.hsma.igt.sascha.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Indexed;


@Entity@Indexed
@Table(name = "Flight")
public class Flight implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5400230129648771583L;
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy ="uuid2")
    private String F_ID;
    @Column
    private String F_AIRPLANE_TYPE;
    @Column
    private Date F_DEPARTURE;
    @Column
    private Date F_ARRIVAL;
    @Column
    private Integer F_EC_SEATS;
    @Column
    private Double F_EC_PRICE;
    @Column
    private Integer F_FC_SEATS;
    @Column
    private Double F_FC_PRICE;


    @ManyToMany(mappedBy = "bookedFlights", fetch = FetchType.EAGER)
    private Set<MyCustomer> customers = new HashSet<>();
    
    @OneToMany(mappedBy="flight", fetch = FetchType.EAGER)
    private List<UsedFlightsegment> usedFlightsegment = new ArrayList<UsedFlightsegment>();

    public Flight() {}
    public Flight(String F_AIRPLANE_TYPE, Date F_DEPARTURE, Date F_ARRIVAL, Integer F_EC_SEATS, Double F_EC_PRICE, Integer F_FC_SEATS, Double F_FC_PRICE) {
        this.F_AIRPLANE_TYPE = F_AIRPLANE_TYPE;
        this.F_DEPARTURE = F_DEPARTURE;
        this.F_ARRIVAL = F_ARRIVAL;
        this.F_EC_SEATS = F_EC_SEATS;
        this.F_EC_PRICE = F_EC_PRICE;
        this.F_FC_SEATS = F_FC_SEATS;
        this.F_FC_PRICE = F_FC_PRICE;
        // TODO change this to avoid collisions (For testing purpose)
        //this.F_ID = (long)(Math.random() * 2000000);
    }

    public String getF_ID() {
        return F_ID;
    }

    public void setF_ID(String f_ID) {
        F_ID = f_ID;
    }

    public String getF_AIRPLANE_TYPE() {
        return F_AIRPLANE_TYPE;
    }

    public void setF_AIRPLANE_TYPE(String f_AIRPLANE_TYPE) {
        F_AIRPLANE_TYPE = f_AIRPLANE_TYPE;
    }

    public Date getF_DEPARTURE() {
        return F_DEPARTURE;
    }

    public void setF_DEPARTURE(Date f_DEPARTURE) {
        F_DEPARTURE = f_DEPARTURE;
    }

    public Date getF_ARRIVAL() {
        return F_ARRIVAL;
    }

    public void setF_ARRIVAL(Date f_ARRIVAL) {
        F_ARRIVAL = f_ARRIVAL;
    }

    public Integer getF_EC_SEATS() {
        return F_EC_SEATS;
    }

    public void setF_EC_SEATS(Integer f_EC_SEATS) {
        F_EC_SEATS = f_EC_SEATS;
    }

    public Double getF_EC_PRICE() {
        return F_EC_PRICE;
    }

    public void setF_EC_PRICE(Double f_EC_PRICE) {
        F_EC_PRICE = f_EC_PRICE;
    }

    public Integer getF_FC_SEATS() {
        return F_FC_SEATS;
    }

    public void setF_FC_SEATS(Integer f_FC_SEATS) {
        F_FC_SEATS = f_FC_SEATS;
    }

    public Double getF_FC_PRICE() {
        return F_FC_PRICE;
    }

    public void setF_FC_PRICE(Double f_FC_PRICE) {
        F_FC_PRICE = f_FC_PRICE;
    }

    public List<UsedFlightsegment> getUsedFlightsegment() {
		return usedFlightsegment;
	}
    
	public void setUsedFlightsegment(List<UsedFlightsegment> usedFlightsegment) {
		this.usedFlightsegment = usedFlightsegment;
	}
	public void setUsedFlightsegment(UsedFlightsegment usedFlightsegment) {
		this.usedFlightsegment.add(usedFlightsegment);
	}

	public Set<MyCustomer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<MyCustomer> customers) {
        this.customers = customers;
    }
	@Override
	public String toString() {
		return "Flight [F_ID=" + F_ID + ", F_AIRPLANE_TYPE=" + F_AIRPLANE_TYPE + ", F_DEPARTURE=" + F_DEPARTURE
				+ ", F_ARRIVAL=" + F_ARRIVAL + ", F_EC_SEATS=" + F_EC_SEATS + ", F_EC_PRICE=" + F_EC_PRICE
				+ ", F_FC_SEATS=" + F_FC_SEATS + ", F_FC_PRICE=" + F_FC_PRICE + ", customers=" + customers
				+ ", usedFlightsegment=" + usedFlightsegment + "]";
	}
}
