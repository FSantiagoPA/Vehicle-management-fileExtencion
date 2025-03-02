package com.santiparra.cars.entities;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "PERSON_FP07")
public class Person {

	@Id
	@Column(name = "DRIVER_ID")
	private String driverId;

	@Column(name = "ADDRESS", nullable = true)
	private String address;

	@Column(name = "NAME", nullable = true)
	private String name;

	@Column(name = "AGE", nullable = false)
	private int age;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private Set<Car> cars = new LinkedHashSet<>();

	public Person() {
	}

	public Person(String driverId, String address, String name, int age) {
		this.driverId = driverId;
		this.address = address;
		this.name = name;
		this.age = age;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getYearOfBirth() {
		return java.time.Year.now().getValue() - this.age;
	}

	public Set<Car> getCars() {
		return cars;
	}

	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}

	@Override
	public int hashCode() {
		return Objects.hash(driverId); // Usa solo el ID único
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Person person = (Person) o;
		return Objects.equals(driverId, person.driverId); // Usa solo el ID único
	}

	@Override
	public String toString() {
		return "Person [driverId=" + driverId + ", address=" + address + ", name=" + name + ", age=" + age + "]";
	}
}
