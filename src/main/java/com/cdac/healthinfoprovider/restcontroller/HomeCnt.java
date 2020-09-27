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

@RestController
public class HomeCnt {
	

//	@RequestMapping( value = "*", method = { RequestMethod.GET,RequestMethod.POST })
//	@ResponseBody
	public ResponseEntity<?> allFallback(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		return new ResponseEntity<>("Fallback for All Requests: " + requestUri + " Not Found", HttpStatus.NOT_FOUND);

	}

	
}
