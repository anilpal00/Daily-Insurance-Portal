package com.dailyinsuranceportal.services.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailyinsuranceportal.dto.UserPolicyClaimDto;
import com.dailyinsuranceportal.entities.Policy;
import com.dailyinsuranceportal.entities.User;
import com.dailyinsuranceportal.entities.UserPolicyClaim;
import com.dailyinsuranceportal.entities.Wallet;
import com.dailyinsuranceportal.exceptions.ResourceNotFoundException;
import com.dailyinsuranceportal.repository.PolicyRepository;
import com.dailyinsuranceportal.repository.UserPolicyClaimRepository;
import com.dailyinsuranceportal.repository.UserRepository;
import com.dailyinsuranceportal.repository.WalletRepository;
import com.dailyinsuranceportal.services.WalletService;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PolicyRepository policyRepository;

	@Autowired
	private UserPolicyClaimRepository userPolicyClaimRepository;

	@Autowired
	private ModelMapper modelMapper;

	UserPolicyClaimDto userPolicyClaimDto = new UserPolicyClaimDto();

	private Wallet wallet;

	@Override
	public Wallet addWalletBalance(Wallet walletBalance, User uid) {
		Long userId = uid.getId();
		String walletMode = walletBalance.getMode();
		Integer walletAmt = walletBalance.getAmount();

		walletBalance.setMode(walletBalance.getMode());
		walletBalance.setAmount(walletBalance.getAmount());
		walletBalance.setUser_id(uid);

		Wallet walletData = walletRepository.findUserExist(walletMode, userId);
		if (walletData!=null && walletData.getUser_id().getId().equals(userId) && walletData.getMode().equals(walletMode)) {
			Integer totalAmount = walletAmt + walletData.getAmount();
			walletBalance.setId(walletData.getId());
			walletBalance.setAmount(totalAmount);
			wallet = this.walletRepository.save(walletBalance);
		}else if(walletData==null) {
			walletBalance.setId(walletBalance.getId());
			wallet = this.walletRepository.save(walletBalance);
		}

		return wallet;
	}

	@Override
	public List<Policy> getAllPolicy() {
		List<Policy> policy = this.policyRepository.findAll();
		return policy;
	}

	@Override
	public UserPolicyClaim purchaseInsurancePolicy(Policy purchasePolicyId, User uid) {
		UserPolicyClaim userPolicyClaim = new UserPolicyClaim();
//		Integer premiumAmount = purchasePolicyId.getPolicyPremium();
//		Optional<User> user1 = userRepository.findById(uid.getId());
//		User user2 = user1.get();
		userPolicyClaim.setUser_id(uid);
		UserPolicyClaim userPolicyClaimSave = this.userPolicyClaimRepository.save(userPolicyClaim);
		return userPolicyClaimSave;
	}

	@Override
	public Policy getPolicyById(Long policyId) {
		Policy policy = this.policyRepository.findById(policyId).get();
		return policy;
	}

	@Override
	public UserPolicyClaimDto claimRequest(UserPolicyClaimDto requestClaim, User uid, String fromDate) {

		UserPolicyClaim userPolicyClaim = this.dtoToUser(requestClaim);
		Long policyId = userPolicyClaim.getPolicy_id().getId();
		Long user_id = uid.getId();
		List<UserPolicyClaim> claimdata = getClaimRequestdata(policyId, fromDate, user_id);

		claimdata.stream().forEach(data -> {
			Date input = data.getDateTimeOfPurchase();
			String output = input.toString();
			String date = output.substring(0, 10);

			if (date.compareTo(fromDate) == 0) {
				Integer claimAmount = userPolicyClaim.getClaimAmount();
				Wallet userWalletData = getWalletData(user_id);
				updateWalletAmount(claimAmount, userWalletData);

				userPolicyClaim.setClaimAmount(userPolicyClaim.getClaimAmount());
				userPolicyClaim.setId(data.getId());
				userPolicyClaim.setUser_id(uid);
				userPolicyClaim.setClaimStatus(1);
				UserPolicyClaim amountUpdateInUserPolicy = this.userPolicyClaimRepository.save(userPolicyClaim);
				UserPolicyClaimDto userPolicyClaimDto = userToDto(amountUpdateInUserPolicy);
			}

			if (date.compareTo(fromDate) == -1) {
				System.out.println("Purcahse date Not match - " + date);
			}
		});

		return userPolicyClaimDto;
	}

	@Override
	public List<UserPolicyClaim> getClaimRequestdata(Long policyId, String purchase_dttm, Long user_id) {
		List<UserPolicyClaim> claimRequestdata = this.userPolicyClaimRepository.getAllClaimData(policyId, purchase_dttm,
				user_id);
		return claimRequestdata;
	}

	@Override
	public Wallet getWalletData(Long user_id) {
		Wallet walletdata = this.walletRepository.findById(user_id)
				.orElseThrow(() -> new ResourceNotFoundException("Wallet", " Id ", user_id));
		return walletdata;
	}

	@Override
	public Wallet updateWalletAmount(Integer claimAmount, Wallet userWalletData) {
		userWalletData.setAmount(userWalletData.getAmount() + claimAmount);
		Wallet amountaddInWallet = this.walletRepository.save(userWalletData);
		return amountaddInWallet;
	}

	public UserPolicyClaim dtoToUser(UserPolicyClaimDto userPolicyClaimDto) {
		UserPolicyClaim userPolicyClaim = this.modelMapper.map(userPolicyClaimDto, UserPolicyClaim.class);
		userPolicyClaim.setPolicy_id(userPolicyClaimDto.getPolicy());
		userPolicyClaim.setDateTimeOfPurchase(userPolicyClaimDto.getDateTimeOfPurchase());
		userPolicyClaim.setClaimAmount(userPolicyClaimDto.getClaimAmount());
		return userPolicyClaim;
	}

	public UserPolicyClaimDto userToDto(UserPolicyClaim userPolicyClaim) {
		UserPolicyClaimDto userPolicyClaimDto = this.modelMapper.map(userPolicyClaim, UserPolicyClaimDto.class);
		userPolicyClaimDto.setClaimAmount(userPolicyClaim.getClaimAmount());
		userPolicyClaimDto.setDateTimeOfPurchase(userPolicyClaim.getDateTimeOfPurchase());
		userPolicyClaimDto.setPolicy(userPolicyClaim.getPolicy_id());
		return userPolicyClaimDto;
	}

}
