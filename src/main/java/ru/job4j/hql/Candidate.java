package ru.job4j.hql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "candidates")
public class Candidate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String experience;
	private Double salary;
	
	public static Candidate of(String name, String experience, Double salary) {
		Candidate candidate = new Candidate();
		candidate.name = name;
		candidate.experience = experience;
		candidate.salary = salary;
		return candidate;
	}
}
