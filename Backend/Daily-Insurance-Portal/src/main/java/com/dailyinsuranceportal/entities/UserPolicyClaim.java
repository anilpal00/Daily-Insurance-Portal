package com.dailyinsuranceportal.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPolicyClaim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
//	@Column(name = "user-id")
	private User user_id;
		
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policy_id", nullable = false)
	private Policy policy_id;
	
    @Column(name = "purchase_dttm")
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date dateTimeOfPurchase;
    
    @Column(name = "claim_amount")
    private Integer claimAmount;
    
    @Column(name = "claim_status")
    private Integer claimStatus;
    
    @Column(name = "claim_dttm")
    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dateTimeOfClaim;
    
//    @JsonProperty("policy_id")
//    private void unpackNested(Long policy_id) {
//        this.policy_id = new Policy();
//        policy_id
//        
//        flight.setFlight_id(flight_id);
//    }
    
}
