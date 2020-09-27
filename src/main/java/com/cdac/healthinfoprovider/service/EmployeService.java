package com.cdac.healthinfoprovider.service;

import java.util.List;

import com.cdac.healthinfoprovider.model.Employe;

public interface EmployeService {

	Employe save(Employe e);

	List<Employe> getAllList();
}
