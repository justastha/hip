package com.cdac.healthinfoprovider.restcontroller;

import java.util.List;

/**
 * @author Prashant Mishra
 *
 */
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.healthinfoprovider.model.Employe;
import com.cdac.healthinfoprovider.service.EmployeImpl;

@RestController
public class HomeCnt {
	@Autowired
	EmployeImpl employeService;

//	@RequestMapping( value = "*", method = { RequestMethod.GET,RequestMethod.POST })
//	@ResponseBody
	public ResponseEntity<?> allFallback(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		return new ResponseEntity<>("Fallback for All Requests: " + requestUri + " Not Found", HttpStatus.NOT_FOUND);

	}

	@GetMapping("/saveEmp")
	public ResponseEntity<Employe> saveEmpData(@RequestBody Employe employe) {
		System.out.println(employe.toString());
		employeService.save(employe);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<?> getEmpData() {
		List<Employe> empList = employeService.getAllList();
		return new ResponseEntity<>(empList, HttpStatus.OK);
	}
}
