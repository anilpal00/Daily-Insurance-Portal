package com.dailyinsuranceportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dailyinsuranceportal.entities.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

	@Query(value = "SELECT * FROM dailyinsuranceportal.wallet where user_id=:userId and mode=:walletMode" , nativeQuery = true)
	Wallet findUserExist(String walletMode, Long userId);

	@Query(value="UPDATE wallet w set w.amount =:totalAmount WHERE w.user_id=:userId and w.mode=:walletMode", nativeQuery = true)
	Wallet updateWalletAmount(String walletMode, Long userId, Integer totalAmount);
}
