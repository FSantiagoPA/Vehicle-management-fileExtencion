package com.santiparra.cars.entities;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "CAR_FP07")

/* CAR(LICENSE_ID, MODEL, YEAR) */
public class Car {

	@Id
	@Column(name = "LICENSE_ID")
	private String licenseId;

	@Column(name = "MODEL", nullable = true)
	private String model;

	@Column(name = "YEAR", nullable = true)
	private Integer year;

	@ManyToOne
	@JoinColumn(name = "DRIVER_ID")
	private Person owner;

	@OneToOne(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
	private Policy policy;

	@ManyToMany
	@JoinTable(name = "PARTICIPATED_FP07", joinColumns = { @JoinColumn(name = "LICENSE_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "REPORT_NUMBER") })
	private Set<Accident> accidents = new LinkedHashSet<Accident>();

	public Car() {
	}

	public Car(String licenseId, String model, Integer year) {
		this.licenseId = licenseId;
		this.model = model;
		this.year = year;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public Set<Accident> getAccidents() {
		return accidents;
	}

	public void setAccidents(Set<Accident> accidents) {
		this.accidents = accidents;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(licenseId); // Usa solo el ID único
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Car car = (Car) o;
	    return Objects.equals(licenseId, car.licenseId); // Usa solo el ID único
	}

	@Override
	public String toString() {
		return "Car [licenseId=" + licenseId + ", model=" + model + ", year=" + year + ", owner=" + owner + ", accident =" + accidents.size() + "]";
	}

}
