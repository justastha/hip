package com.cdac.healthinfoprovider.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.healthinfoprovider.model.Employe;
import com.cdac.healthinfoprovider.repository.EmployeRepo;

@Service
public class EmployeImpl {
	@Autowired
	EmployeRepo employeRepo;

	
	@Transactional
	public Employe save(Employe emp) {
		try {
			employeRepo.save(emp);
		} catch (Exception e) {
		}
		return emp;
	}

	public List<Employe> getAllList() {
		return employeRepo.findAll();
	}

}
