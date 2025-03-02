package com.santiparra.cars.entities;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "POLICY_FP07")

/* POLICY (POLICY_ID) */
public class Policy {
	
	@Id
	@Column(name = "POLICY_ID")
	private String policyId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LICENSE_ID")
	private Car car;
	
	public Policy() {}

	public Policy(String policyId) {
		super();
		this.policyId = policyId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public int hashCode() {
		return Objects.hash(car, policyId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Policy other = (Policy) obj;
		return Objects.equals(car, other.car) && Objects.equals(policyId, other.policyId);
	}

	@Override
	public String toString() {
		return "Policy [policyId=" + policyId + ", car=" + car + "]";
	}

}
