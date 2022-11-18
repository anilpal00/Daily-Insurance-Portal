package com.dailyinsuranceportal.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailyinsuranceportal.dto.UserPolicyClaimDto;
import com.dailyinsuranceportal.entities.Policy;
import com.dailyinsuranceportal.entities.User;
import com.dailyinsuranceportal.entities.UserPolicyClaim;
import com.dailyinsuranceportal.entities.Wallet;
import com.dailyinsuranceportal.services.WalletService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class WalletController {

	@Autowired
	private WalletService walletService;
	
	@PostMapping("/wallet/{uid}")
	public ResponseEntity<Wallet> addWalletBalance(@RequestBody Wallet walletBalance, @PathVariable User uid){
		Wallet wallet = this.walletService.addWalletBalance(walletBalance, uid);
		return new ResponseEntity<Wallet>(wallet, HttpStatus.CREATED);
	}
	
	@GetMapping("/getPolicy")
	public ResponseEntity<List<Policy>> getAllPolicy(){
		List<Policy> policy = this.walletService.getAllPolicy();
		return ResponseEntity.ok(policy);
	}
	
	@GetMapping("/getPolicy/{policyId}")
	public ResponseEntity<Policy> getPolicyById( @PathVariable Long policyId){
		Policy policy = this.walletService.getPolicyById(policyId);
		return ResponseEntity.ok(policy);
	}
	
	@PostMapping("/purchasepolicy/{uid}")
	public ResponseEntity<UserPolicyClaim> purchaseInsurancePolicy(@RequestBody Policy purchasePolicyId, @PathVariable User uid){
		UserPolicyClaim userPolicyClaim = this.walletService.purchaseInsurancePolicy(purchasePolicyId, uid);
		return new ResponseEntity<UserPolicyClaim>(userPolicyClaim, HttpStatus.CREATED);
	}
	
	@PatchMapping("/claimRequest/{uid}")
	public ResponseEntity<UserPolicyClaimDto> claimRequest(@RequestBody UserPolicyClaimDto requestClaim, @RequestParam("date") String fromDate, @PathVariable User uid) throws ParseException{
		UserPolicyClaimDto userPolicyClaim = this.walletService.claimRequest(requestClaim, uid, fromDate);
		return new ResponseEntity<UserPolicyClaimDto>(userPolicyClaim, HttpStatus.CREATED);
	}
	
}
