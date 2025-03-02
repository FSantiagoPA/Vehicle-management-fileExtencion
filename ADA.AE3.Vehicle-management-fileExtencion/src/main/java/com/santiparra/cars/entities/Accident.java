package com.santiparra.cars.entities;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "ACCIDENT_FP07")

/* ACCIDENT(REPORT_NUMBER, LOCATION, DATE) */
public class Accident {

	@Id
	@Column(name = "REPORT_NUMBER")
	private String reportNumber;

	@Column(name = "LOCATION", nullable = true)
	private String location;

	@ManyToMany(mappedBy = "accidents", cascade = CascadeType.ALL)
	private Set<Car> carsInvolved = new LinkedHashSet<Car>();

	public Accident() {
	}

	public Accident(String reportNumber, String location) {
		this.reportNumber = reportNumber;
		this.location = location;
	}

	public String getReportNumber() {
		return reportNumber;
	}

	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Set<Car> getCarsInvoled() {
		return carsInvolved;
	}

	public void setCarsInvoled(Set<Car> carsInvoled) {
		this.carsInvolved = carsInvoled;
	}

	@Override
	public int hashCode() {
		return Objects.hash(carsInvolved, location, reportNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Accident other = (Accident) obj;
		return Objects.equals(carsInvolved, other.carsInvolved) && Objects.equals(location, other.location)
				&& Objects.equals(reportNumber, other.reportNumber);
	}

	@Override
	public String toString() {
		return "Accident [reportNumber=" + reportNumber + ", location=" + location + ", carsInvoled=" + carsInvolved
				+ "]";
	}

}
