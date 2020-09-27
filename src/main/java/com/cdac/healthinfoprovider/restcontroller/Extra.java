package com.cdac.healthinfoprovider.restcontroller;
/**
 * @author Prashant Mishra
 *
 */
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cdac.healthinfoprovider.model.Users;
import com.cdac.healthinfoprovider.model.patientdiscoveryrequest.PatientDiscoveryRequestFb;
import com.cdac.healthinfoprovider.service.AsyncServiceImp;
import com.google.gson.Gson;

@SuppressWarnings("unused")
@RestController
@RequestMapping( value = "/v0.6") //RequestMethod.PUT, RequestMethod.POST,
public class Extra {
	String getUrl = "http://uniprrjleapi.digiscapetech.com/journal/list";
	String postUrl = "https://reqres.in/api/login";
	String postUlr1="http://dummy.restapiexample.com/api/v1/create";

	@Autowired
	private AsyncServiceImp asyncServiceImp;
	
	
	@PostMapping("/care-contexts/discovery")
	public ResponseEntity<?> postDiscovery(@RequestBody String patientDiscoveryFb,@RequestHeader Map<String, String> headers) {
		System.out.println("------------------------------------" + patientDiscoveryFb);
		
		String stAccessToken = headers.get("authorization");
		String[] arr = {"1",stAccessToken, patientDiscoveryFb};
		//str=patientDiscoveryFb;
		//jmsTemplate.convertAndSend(queue, arr);
		//PatientDataFb obj = new PatientDataFb();
		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	

	@PostMapping("/care-contexts/discoveryy")
	public ResponseEntity<?> postDiscoveryy(@RequestBody String patientDiscoveryFb,@RequestHeader Map<String, String> headers) {
		System.out.println("------------------------------------" + patientDiscoveryFb);
		
		String stAccessToken = headers.get("authorization");
		String[] arr = {"1",stAccessToken, patientDiscoveryFb};
		//str=patientDiscoveryFb;
		//jmsTemplate.convertAndSend(queue, arr);
		//PatientDataFb obj = new PatientDataFb();
		Gson gson = new Gson();
		PatientDiscoveryRequestFb bb = new PatientDiscoveryRequestFb();
		String bbstr = gson.toJson(bb); 
		return new ResponseEntity<>(bb, HttpStatus.OK);
		//return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	@RequestMapping(value = "/getExternalCall")
	public ResponseEntity<?> getProductList() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		RestTemplate restTemplate = new RestTemplate();
		String data = restTemplate.exchange(getUrl, HttpMethod.GET, entity, String.class).getBody();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
//	@PostMapping("postExternalCall")
//	public ResponseEntity<?> postProductList1(@RequestBody Employe employe) {
//		System.out.println("uri-->" + postUrl);
//		RestTemplate restTemplate = new RestTemplate();
//		String  user = null;
//		HttpEntity<Employe> request = new HttpEntity<>(employe);
//		try {
//			user = restTemplate.postForObject(postUlr1, request, String.class);
//		
//			System.out.println("result we got from postExternalCall User Created---->" + user);
//			return new ResponseEntity<>(user, HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<>(user, HttpStatus.OK);
//	}

	@PostMapping("postData")
	public ResponseEntity<?> postProductList(@RequestBody Users users) {
		System.out.println("Users info- 1->" + users);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		RestTemplate restTemplate = new RestTemplate();
		String post = restTemplate.exchange(postUrl, HttpMethod.POST, entity, String.class).getBody();

		System.out.println("uri- 2->" + post);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@RequestMapping( value = "*", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<?> allFallback(HttpServletRequest request) {
		 String requestUri = request.getRequestURI();
		return new ResponseEntity<>("Fallback for All Requests: "+requestUri+" Not Found", HttpStatus.NOT_FOUND);
	    
	}
	
/*
	//{"patient":{"id":"astha@sbx","name":"Astha Rai","gender":"F","yearOfBirth":1984,"verifiedIdentifiers":[{"type":"MOBILE","value":"+91-9599566448"},{"type":"NDHM_HEALTH_NUMBER","value":"22-3786-8700-7462"},{"type":"HEALTH_ID","value":"astha@sbx"}],"unverifiedIdentifiers":[]},"requestId":"7646dcbe-13fa-4e07-86bb-9f9f67fecf51","timestamp":"2020-09-20T17:45:54.857249","transactionId":"6d46b9e9-1639-4b03-ae6b-947464ece439"}
	
	@PostMapping("/v0.5/care-contexts/discover")
	public ResponseEntity<?> postDiscovery(@RequestBody String patientDiscoveryFb,@RequestHeader Map<String, String> headers) {
		System.out.println("------------------------------------" + patientDiscoveryFb);
		
		String stAccessToken = headers.get("authorization");
		String[] arr = {"1",stAccessToken, patientDiscoveryFb};
		str=patientDiscoveryFb;
		jmsTemplate.convertAndSend(queue, arr);
		//PatientDataFb obj = new PatientDataFb();
		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/v0.5/links/link/init")
	public ResponseEntity<?> onInit(@RequestBody String patientDiscoveryFb,@RequestHeader Map<String, String> headers) {
		System.out.println("------------------------------------" + patientDiscoveryFb);
		
		String stAccessToken = headers.get("authorization");
		String[] arr = {"2",stAccessToken, patientDiscoveryFb};
		System.out.println("Linking Request");
	//	str=patientDiscoveryFb;
		jmsTemplate.convertAndSend(queue, arr);
		//PatientDataFb obj = new PatientDataFb();
		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/v0.5/links/link//v0.5/links/link/confirm")
	public ResponseEntity<?> onConfirm(@RequestBody String patientDiscoveryFb,@RequestHeader Map<String, String> headers) {
		System.out.println("------------------------------------" + patientDiscoveryFb);
		
		String stAccessToken = headers.get("authorization");
		String[] arr = {"3",stAccessToken, patientDiscoveryFb};
		System.out.println("Linking Request");
	//	str=patientDiscoveryFb;
		jmsTemplate.convertAndSend(queue, arr);
		//PatientDataFb obj = new PatientDataFb();
		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	*/
}
