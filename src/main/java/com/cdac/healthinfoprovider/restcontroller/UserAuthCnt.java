package com.cdac.healthinfoprovider.restcontroller;
/**
 * @author Prashant Mishra
 *
 */
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.healthinfoprovider.model.patientdiscoveryrequest.PatientDiscoveryRequestFb;
import com.cdac.healthinfoprovider.service.AsyncServiceImp;
import com.google.gson.JsonObject;


@RestController
@RequestMapping( value = "/v0.5/users/auth") //RequestMethod.PUT, RequestMethod.POST,
public class UserAuthCnt {

	@Autowired
	private AsyncServiceImp asyncServiceImp;
	
	@RequestMapping( value = {"/",}, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<?> allFallback(HttpServletRequest request) {
		 String requestUri = request.getRequestURI();
		return new ResponseEntity<>("Welcome Prashant This is Fallback for All /v0.5 RequestedURL: "+requestUri, HttpStatus.NOT_FOUND);
	    
	}
	
	@PostMapping("/on-fetch-modes")
	public ResponseEntity<?> postOnFetchModes(@RequestBody PatientDiscoveryRequestFb patientDiscoveryRequestFb) {
		System.out.println("postOnFetchModes:patientDiscoveryRequestFb::------------------------------------" + patientDiscoveryRequestFb);
		try {
			asyncServiceImp.trigerAsyncPatientDiscovery(patientDiscoveryRequestFb);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/init")
	public ResponseEntity<?> postAuthInit(@RequestBody JsonObject patAuthJson) {
		System.out.println("postOnInit:patientDiscoveryRequestFb::------------------------------------" + patAuthJson);
		try {
			asyncServiceImp.trigerAsyncAuthInit(patAuthJson);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/on-init")
	public ResponseEntity<?> postOnInit(@RequestBody PatientDiscoveryRequestFb patientDiscoveryRequestFb) {
		System.out.println("postOnInit:patientDiscoveryRequestFb::------------------------------------" + patientDiscoveryRequestFb);
		try {
			asyncServiceImp.trigerAsyncPatientDiscovery(patientDiscoveryRequestFb);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/on-confirm")
	public ResponseEntity<?> postOnConfirm(@RequestBody PatientDiscoveryRequestFb patientDiscoveryRequestFb) {
		System.out.println("postOnConfirm:patientDiscoveryRequestFb::------------------------------------" + patientDiscoveryRequestFb);
		try {
			asyncServiceImp.trigerAsyncPatientDiscovery(patientDiscoveryRequestFb);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
}
