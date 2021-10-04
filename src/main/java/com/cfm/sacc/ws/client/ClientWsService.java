package com.cfm.sacc.ws.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

public class ClientWsService implements IClientWsService {

	@Override
	public Object consumeService(String url, Object object, HttpMethod typeMethod, String contentType,
			String authorization, Object typeResponse) {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2CborHttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		
		MultiValueMap<String, String> headers= new LinkedMultiValueMap<>();
		headers.add("Content-Type", contentType);
		headers.add("User-Agent", "CFM SACC 1.0.0");
		HttpEntity<Object> requestService = new HttpEntity<>(object,headers);
			
		Object responseObject = null;
		ResponseEntity<JsonNode> responseService = restTemplate.exchange(url, typeMethod, requestService, JsonNode.class);
		responseObject = responseService.getBody();
		
		return responseObject;
	}


}
