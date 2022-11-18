package com.dailyinsuranceportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailyinsuranceportal.entities.Policy;

public interface PolicyRepository  extends JpaRepository<Policy, Long> {

}
