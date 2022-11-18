package com.dailyinsuranceportal.services;

import java.util.List;

import com.dailyinsuranceportal.dto.UserPolicyClaimDto;
import com.dailyinsuranceportal.entities.Policy;
import com.dailyinsuranceportal.entities.User;
import com.dailyinsuranceportal.entities.UserPolicyClaim;
import com.dailyinsuranceportal.entities.Wallet;

public interface WalletService {

	Wallet addWalletBalance(Wallet walletBalance, User uid);
	List<Policy> getAllPolicy();
	Policy getPolicyById(Long policyId);
	UserPolicyClaim purchaseInsurancePolicy(Policy purchasePolicyId, User uid);
	UserPolicyClaimDto claimRequest(UserPolicyClaimDto requestClaim, User uid, String fromDate);
	List<UserPolicyClaim> getClaimRequestdata(Long policyId , String purchase_dttm, Long uid);
	Wallet getWalletData(Long user_id);
	Wallet updateWalletAmount(Integer claimAmount, Wallet userWalletData);
	
}
