package com.cdac.healthinfoprovider.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.healthinfoprovider.model.Employe;
import com.cdac.healthinfoprovider.repository.EmployeRepo;

@Service
public class EmployeImpl implements EmployeService {
	@Autowired
	EmployeRepo employeRepo;

	@Override
	@Transactional
	public Employe save(Employe emp) {
		try {
			employeRepo.save(emp);
		} catch (Exception e) {
		}
		return emp;
	}

	@Override
	public List<Employe> getAllList() {
		return employeRepo.findAll();
	}

}
