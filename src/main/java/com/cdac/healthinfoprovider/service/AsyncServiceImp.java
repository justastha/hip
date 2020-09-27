package com.cdac.healthinfoprovider.service;

/**
 * @author Prashant Mishra
 * 
 */

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cdac.healthinfoprovider.domain.patientdiscoveryresponse.Patient;
import com.cdac.healthinfoprovider.domain.patientdiscoveryresponse.PatientDiscoveryResponseVo;
import com.cdac.healthinfoprovider.model.patientdiscoveryrequest.PatientDiscoveryRequestFb;
import com.cdac.healthinfoprovider.repository.PatientDiscoveryRepoImp;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class AsyncServiceImp {
	
	private String Urlprefix1="https://webhook.site/b0da1609-1f30-43cb-a176-cacb30d935b7";
	private String Urlprefix2="https://dev.ndhm.gov.in";
	private String postOnDiscoverUrl =Urlprefix1+"/gateway/v0.5/care-contexts/on-discover";
	private String postOnInitUrl = Urlprefix1+"/gateway/v0.5/links/link/on-init";
	private String postSessionUrl = Urlprefix1+"/gateway/v0.5/sessions";
	private URI postOnConfirmUrl, postOnNotifyUrl, postOnRequestUrl, postFetchModesUrl, postConfirmUrl, postInitUrl,
			postAddContextsUrl, postNotifyUrl, getOpenidConfigurationUrl, getCertsUrl;

	private static Logger log = LoggerFactory.getLogger(AsyncServiceImp.class);

	@Autowired
	private RestTemplate restTemplate;
	private PatientDiscoveryRepoImp patientDiscoveryRepoImp;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Async("asyncExecutor")
	public void trigerAsyncPatientDiscovery(PatientDiscoveryRequestFb patientDiscoveryRequestFb)
			throws InterruptedException {
		log.info("trigerAsyncPatientDiscovery starts");

		PatientDiscoveryResponseVo patientDiscoveryResponseVo = new PatientDiscoveryResponseVo();
		patientDiscoveryResponseVo.setRequestId(patientDiscoveryRequestFb.getRequestId());
		patientDiscoveryResponseVo.setTimestamp(patientDiscoveryRequestFb.getTimestamp());
		patientDiscoveryResponseVo.setTransactionId(patientDiscoveryRequestFb.getTransactionId());
		//patientDiscoveryResponseVo.resp.requestId=patientDiscoveryRequestFb.getTransactionId();

		Patient patient = new Patient();
		PatientDiscoveryRepoImp patientDiscoveryRepoImp1 = new PatientDiscoveryRepoImp();
		patient = patientDiscoveryRepoImp1.getPatientCareContexts(patientDiscoveryRequestFb);
		patientDiscoveryResponseVo.setPatient(patient);
		
		Gson gson = new Gson();
		String patientDiscoveryResponseVoJsonStr = gson.toJson(patientDiscoveryResponseVo);
		patientOnDiscoverCallback(patientDiscoveryResponseVoJsonStr);

		log.info("trigerAsyncPatientDiscovery completed");
	}

	@Async("asyncExecutor")
	public void trigerAsyncOnInit(PatientDiscoveryRequestFb patientDiscoveryRequestFb) throws InterruptedException {
		log.info("trigerAsyncOnInit started");

		PatientDiscoveryResponseVo patientDiscoveryResponseVo = new PatientDiscoveryResponseVo();
		patientDiscoveryResponseVo.setRequestId(patientDiscoveryRequestFb.getRequestId());
		patientDiscoveryResponseVo.setTimestamp(patientDiscoveryRequestFb.getTimestamp());
		patientDiscoveryResponseVo.setTransactionId(patientDiscoveryRequestFb.getTransactionId());

		Gson gson = new Gson();
		String patientDiscoveryResponseVoJsonStr = gson.toJson(patientDiscoveryResponseVo);
		patientOnInitCallback(patientDiscoveryResponseVoJsonStr);

		log.info("trigerAsyncOnInit completed");
	}

	private JsonObject getInitGatewaySession() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		JsonObject personJsonObject = new JsonObject();
		personJsonObject.addProperty("clientId", "cdac_9135661");
		personJsonObject.addProperty("clientSecret", "9b36621d-6eb8-4d0c-90a4-b1ac7a8f66ea");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
		String gatewaySessionJsonStr = restTemplate.exchange(postSessionUrl, HttpMethod.POST, request, String.class)
				.getBody();

		JsonObject gatewaySessionJson = new Gson().fromJson(gatewaySessionJsonStr, JsonObject.class);

		return gatewaySessionJson;
	}

	private String getGatewayAccessToken() {

		JsonObject gatewaySessionJson = new JsonObject();
		gatewaySessionJson = getInitGatewaySession();
		String gatewayAccessToken = gatewaySessionJson.get("accessToken").getAsString();

		/*
		 * JSONObject gatewaySessionJson2; String accessToken = null; try {
		 * gatewaySessionJson2 = new JSONObject(gatewayAccessToken); accessToken =
		 * gatewaySessionJson2.getString("accessToken"); } catch (JSONException e)
		 * {e.printStackTrace();}
		 */

		return gatewayAccessToken;
	}

	public HttpStatus patientOnDiscoverCallback(String patientDiscoveryResponseVoJsonStr) throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		String gatewayAccessToken ="";// getGatewayAccessToken();

		headers.set("Content-Type", "application/json");
		headers.setBearerAuth(gatewayAccessToken);
		headers.set("X-CM-ID", "sbx");

		HttpEntity<String> request = new HttpEntity<String>(patientDiscoveryResponseVoJsonStr, headers);
		HttpStatus objStatus = restTemplate.exchange(postOnDiscoverUrl, HttpMethod.POST, request, String.class)
				.getStatusCode();

		return objStatus;
	}

	public HttpStatus patientOnInitCallback(String patientDiscoveryResponseVoJsonStr) throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		String gatewayAccessToken = getGatewayAccessToken();

		headers.set("Content-Type", "application/json");
		headers.setBearerAuth(gatewayAccessToken);
		headers.set("X-CM-ID", "sbx");

		HttpEntity<String> request = new HttpEntity<String>(patientDiscoveryResponseVoJsonStr, headers);
		HttpStatus objStatus = restTemplate.exchange(postOnInitUrl, HttpMethod.POST, request, String.class)
				.getStatusCode();

		return objStatus;
	}

	public HttpStatus patientOnConfirmCallback(String patientDiscoveryResponseVoJsonStr) throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		String gatewayAccessToken = getGatewayAccessToken();

		headers.set("Content-Type", "application/json");
		headers.setBearerAuth(gatewayAccessToken);
		headers.set("X-CM-ID", "sbx");

		HttpEntity<String> request = new HttpEntity<String>(patientDiscoveryResponseVoJsonStr, headers);
		HttpStatus objStatus = restTemplate.exchange(postOnConfirmUrl, HttpMethod.POST, request, String.class)
				.getStatusCode();

		return objStatus;
	}

	public HttpStatus onNotifyCallback(String patientDiscoveryResponseVoJsonStr) throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		String gatewayAccessToken = getGatewayAccessToken();

		headers.set("Content-Type", "application/json");
		headers.setBearerAuth(gatewayAccessToken);
		headers.set("X-CM-ID", "sbx");

		HttpEntity<String> request = new HttpEntity<String>(patientDiscoveryResponseVoJsonStr, headers);
		HttpStatus objStatus = restTemplate.exchange(postOnNotifyUrl, HttpMethod.POST, request, String.class)
				.getStatusCode();

		return objStatus;
	}

	public HttpStatus onRequestCallback(String patientDiscoveryResponseVoJsonStr) throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		String gatewayAccessToken = getGatewayAccessToken();

		headers.set("Content-Type", "application/json");
		headers.setBearerAuth(gatewayAccessToken);
		headers.set("X-CM-ID", "sbx");

		HttpEntity<String> request = new HttpEntity<String>(patientDiscoveryResponseVoJsonStr, headers);
		HttpStatus objStatus = restTemplate.exchange(postOnRequestUrl, HttpMethod.POST, request, String.class)
				.getStatusCode();

		return objStatus;
	}

	public HttpStatus fetchModesCallback(String patientDiscoveryResponseVoJsonStr) throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		String gatewayAccessToken = getGatewayAccessToken();

		headers.set("Content-Type", "application/json");
		headers.setBearerAuth(gatewayAccessToken);
		headers.set("X-CM-ID", "sbx");

		HttpEntity<String> request = new HttpEntity<String>(patientDiscoveryResponseVoJsonStr, headers);
		HttpStatus objStatus = restTemplate.exchange(postFetchModesUrl, HttpMethod.POST, request, String.class)
				.getStatusCode();

		return objStatus;
	}

	public HttpStatus InitCallback(String patientDiscoveryResponseVoJsonStr) throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		String gatewayAccessToken = getGatewayAccessToken();

		headers.set("Content-Type", "application/json");
		headers.setBearerAuth(gatewayAccessToken);
		headers.set("X-CM-ID", "sbx");

		HttpEntity<String> request = new HttpEntity<String>(patientDiscoveryResponseVoJsonStr, headers);
		HttpStatus objStatus = restTemplate.exchange(postInitUrl, HttpMethod.POST, request, String.class)
				.getStatusCode();

		return objStatus;
	}

	public HttpStatus confirmCallback(String patientDiscoveryResponseVoJsonStr) throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		String gatewayAccessToken = getGatewayAccessToken();

		headers.set("Content-Type", "application/json");
		headers.setBearerAuth(gatewayAccessToken);
		headers.set("X-CM-ID", "sbx");

		HttpEntity<String> request = new HttpEntity<String>(patientDiscoveryResponseVoJsonStr, headers);
		HttpStatus objStatus = restTemplate.exchange(postConfirmUrl, HttpMethod.POST, request, String.class)
				.getStatusCode();

		return objStatus;
	}

	public HttpStatus addContextsCallback(String patientDiscoveryResponseVoJsonStr) throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		String gatewayAccessToken = getGatewayAccessToken();

		headers.set("Content-Type", "application/json");
		headers.setBearerAuth(gatewayAccessToken);
		headers.set("X-CM-ID", "sbx");

		HttpEntity<String> request = new HttpEntity<String>(patientDiscoveryResponseVoJsonStr, headers);
		HttpStatus objStatus = restTemplate.exchange(postAddContextsUrl, HttpMethod.POST, request, String.class)
				.getStatusCode();

		return objStatus;
	}

	public HttpStatus notifyCallback(String patientDiscoveryResponseVoJsonStr) throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		String gatewayAccessToken = getGatewayAccessToken();

		headers.set("Content-Type", "application/json");
		headers.setBearerAuth(gatewayAccessToken);
		headers.set("X-CM-ID", "sbx");

		HttpEntity<String> request = new HttpEntity<String>(patientDiscoveryResponseVoJsonStr, headers);
		HttpStatus objStatus = restTemplate.exchange(postNotifyUrl, HttpMethod.POST, request, String.class)
				.getStatusCode();

		return objStatus;
	}

	public HttpStatus openidConfigurationCallback(String patientDiscoveryResponseVoJsonStr)
			throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		String gatewayAccessToken = getGatewayAccessToken();

		headers.set("Content-Type", "application/json");
		headers.setBearerAuth(gatewayAccessToken);
		headers.set("X-CM-ID", "sbx");

		HttpEntity<String> request = new HttpEntity<String>(patientDiscoveryResponseVoJsonStr, headers);
		HttpStatus objStatus = restTemplate.exchange(getOpenidConfigurationUrl, HttpMethod.POST, request, String.class)
				.getStatusCode();

		return objStatus;
	}

	public HttpStatus certsCallback(String patientDiscoveryResponseVoJsonStr) throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		String gatewayAccessToken = getGatewayAccessToken();

		headers.set("Content-Type", "application/json");
		headers.setBearerAuth(gatewayAccessToken);
		headers.set("X-CM-ID", "sbx");

		HttpEntity<String> request = new HttpEntity<String>(patientDiscoveryResponseVoJsonStr, headers);
		HttpStatus objStatus = restTemplate.exchange(getCertsUrl, HttpMethod.POST, request, String.class)
				.getStatusCode();

		return objStatus;
	}
}
