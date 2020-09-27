package com.cdac.healthinfoprovider.model;

//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class PatientDataFb {

	
		private String name="Prashant Mishra";
		private String age="24";
		private String gender="M";
		private String status="Healthy";
		
		private String requestId;
		private String timestamp;
		private String transactionId;
		private String patient;
		private String error;
		private String resp;
		
		
		
}

