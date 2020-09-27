package com.cdac.healthinfoprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.healthinfoprovider.model.Employe;

@Repository
public abstract class EmployeRepo implements JpaRepository<Employe, Integer> {

}
