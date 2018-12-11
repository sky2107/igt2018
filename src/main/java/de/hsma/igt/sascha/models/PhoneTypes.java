package de.hsma.igt.sascha.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PhoneTypes")
public class PhoneTypes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8366064282624077023L;
	
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy ="uuid2")
    private String P_ID;
    
    @Column(name="PhoneType")
    private String phoneType;

	public PhoneTypes(String p_ID, String phoneType) {
		super();
		P_ID = p_ID;
		this.phoneType = phoneType;
	}

	public String getP_ID() {
		return P_ID;
	}

	public void setP_ID(String p_ID) {
		P_ID = p_ID;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
