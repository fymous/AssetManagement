package com.springboot.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Shop entity class.It contains hibernate annotaions which maps to 
 * table and columns in db.
 * 
 */
@Entity
public class Shop {
   	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column
	private String shopName;
	@Column
	private String shopAddressNumber;
	@Column
	private int shopAddressPostCode;
	@Column
	private double lattitude;
	@Column
	private double longitude;
	
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopAddressNumber() {
		return shopAddressNumber;
	}
	public void setShopAddressNumber(String shopAddressNumber) {
		this.shopAddressNumber = shopAddressNumber;
	}
	public int getShopAddressPostCode() {
		return shopAddressPostCode;
	}
	public void setShopAddressPostCode(int shopAddressPostCode) {
		this.shopAddressPostCode = shopAddressPostCode;
	}
	
	}
