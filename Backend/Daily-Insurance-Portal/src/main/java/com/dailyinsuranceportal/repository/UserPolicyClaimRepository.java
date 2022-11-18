package com.dailyinsuranceportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dailyinsuranceportal.entities.UserPolicyClaim;

public interface UserPolicyClaimRepository extends JpaRepository<UserPolicyClaim, Long> {

	@Query(value = "SELECT * FROM dailyinsuranceportal.user_policy_claim where policy_id=:policyId and user_id =:uid or purchase_dttm =:purchase_dttm", nativeQuery = true)
	List<UserPolicyClaim> getAllClaimData(@Param("policyId") Long policyId, @Param("purchase_dttm") String purchase_dttm, @Param("uid") Long uid);
}
