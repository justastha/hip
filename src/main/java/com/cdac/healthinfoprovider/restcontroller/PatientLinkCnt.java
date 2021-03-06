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


@RestController
@RequestMapping( value = "/v0.5​/links​/link") //RequestMethod.PUT, RequestMethod.POST,
public class PatientLinkCnt {

	@Autowired
	private AsyncServiceImp asyncServiceImp;
	
	@RequestMapping( value = {"/"}, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<?> allFallback(HttpServletRequest request) {
		 String requestUri = request.getRequestURI();
		return new ResponseEntity<>("Welcome Prashant This is Fallback for All /v0.5 RequestedURL: "+requestUri, HttpStatus.NOT_FOUND);
	    
	}

	@PostMapping("/init")
	public ResponseEntity<?> postInit(@RequestBody PatientDiscoveryRequestFb patientDiscoveryRequestFb) {
		System.out.println("postInit:patientDiscoveryRequestFb::------------------------------------" + patientDiscoveryRequestFb);
		try {
			asyncServiceImp.trigerAsyncOnInit(patientDiscoveryRequestFb);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/confirm")
	public ResponseEntity<?> postConfirm(@RequestBody PatientDiscoveryRequestFb patientDiscoveryRequestFb) {
		System.out.println("postConfirm:patientDiscoveryRequestFb::------------------------------------" + patientDiscoveryRequestFb);
		try {
			asyncServiceImp.trigerAsyncPatientDiscovery(patientDiscoveryRequestFb);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/on-add-contexts")
	public ResponseEntity<?> postOnAddContexts(@RequestBody PatientDiscoveryRequestFb patientDiscoveryRequestFb) {
		System.out.println("postOnAddContexts:patientDiscoveryRequestFb::------------------------------------" + patientDiscoveryRequestFb);
		try {
			asyncServiceImp.trigerAsyncPatientDiscovery(patientDiscoveryRequestFb);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	
}
