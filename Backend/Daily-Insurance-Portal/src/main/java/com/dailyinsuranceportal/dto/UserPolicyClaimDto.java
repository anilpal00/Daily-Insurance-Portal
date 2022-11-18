package com.dailyinsuranceportal.dto;

import java.util.Date;

import com.dailyinsuranceportal.entities.Policy;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserPolicyClaimDto {
		
	private Policy policy;
	
	private Date dateTimeOfPurchase;
    
    private Integer claimAmount;
    
    @JsonProperty("policy_id")
    private void unpackNested(Long policy_id) {
        this.policy = new Policy();
        policy.setId(policy_id);
    }
    
}
