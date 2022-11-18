package com.dailyinsuranceportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Policy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "policy_id")
	private Long id;
	
//	@NotBlank
//	@Size(max = 20)	
	@Column(name = "policy_name")
	private String policyName;
	
//	@Column(name = "policy_premium")
//	private String policyPremium;
	
	@Column(name = "policy_premium")
	private Integer policyPremium;
	
	@Column(name = "policy_coverage")
	private String policyMaximumCoverage;

	public Policy(Long id, String policyName, Integer policyPremium, String policyMaximumCoverage) {
		super();
		this.id = id;
		this.policyName = policyName;
		this.policyPremium = policyPremium;
		this.policyMaximumCoverage = policyMaximumCoverage;
	}
	
	
}
