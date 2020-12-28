package com.revature.pirate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pirate")
public class Pirate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private int id;
	@Column
	@Size(min = 4)
	private String name;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	@Column
//	@Min(value = (long)1.00)
//	private double price;
	@Min(value = 10)
	private int price;
}
