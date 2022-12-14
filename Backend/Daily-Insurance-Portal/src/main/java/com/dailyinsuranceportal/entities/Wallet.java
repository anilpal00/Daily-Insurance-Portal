package com.dailyinsuranceportal.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(	name = "wallet")
@Data
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotBlank
//	@Size(max = 20)
	private String mode;

//	@NotBlank
//	@Size(max = 20)
	private Integer amount;
	
//	@NotBlank
//	@Size(max = 20)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user_id;
}
