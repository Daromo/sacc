package com.cfm.sacc.operaciones.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cfm.sacc.config.OperacionesProperties;
import com.cfm.sacc.operaciones.model.BancoEmisor;
import com.cfm.sacc.operaciones.model.ConceptoPago;
import com.cfm.sacc.operaciones.model.FormaPago;
import com.cfm.sacc.operaciones.model.MetodoPago;
import com.cfm.sacc.operaciones.model.Pago;
import com.cfm.sacc.operaciones.model.Periodo;
import com.cfm.sacc.operaciones.model.ReciboHonorario;
import com.cfm.sacc.operaciones.model.TipoHonorario;
import com.cfm.sacc.util.GUIDGenerator;
import com.cfm.sacc.util.LogHandler;
import com.cfm.sacc.ws.client.IClientWsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OperacionesServices implements IOperacionesServices {
	
	private static final String APPLICATION_JSON = "application/json";
	
	@Autowired 
	IClientWsService clientWsService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	OperacionesProperties operacionesProperties;
	
	/**
	 * Consumir API para obtener el catalogo de conceptos de pago
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ConceptoPago> getConceptosPago() {
		List<ConceptoPago> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					operacionesProperties.getUrlConceptosPago(), null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<ConceptoPago>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}
	
	/**
	 * Consumir API para obtener el catalogo de tipos de honorarios
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoHonorario> getTiposHonorarios() {
		List<TipoHonorario> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					operacionesProperties.getUrlTiposHonorarios(), null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<TipoHonorario>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}
	
	/**
	 * Consumir API para obtener el catalogo de formas de pago
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FormaPago> getFormasPago() {
		List<FormaPago> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					operacionesProperties.getUrlFormasPago(), null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<FormaPago>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}
	
	/**
	 * Consumir API para obtener el catalogo de bancos
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BancoEmisor> getBancosEmisor() {
		List<BancoEmisor> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					operacionesProperties.getUrlBancosEmisor(), null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<BancoEmisor>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}
	
	/**
	 * Consumir API para obtener el catalogo de metodos de pago
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MetodoPago> getMetodosPago() {
		List<MetodoPago> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					operacionesProperties.getUrlMetodosPago(), null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<MetodoPago>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}
	
	/**
	 * Consumir API para obenter la lista de peridos pendientes por pagar de cada cliente
	 * @param cliente RFC
	 * @return lista de periodos
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Periodo> getPeridosByCliente(String clienteRFC) {
		String url = operacionesProperties.getUrlPeriodos().concat(clienteRFC);
		List<Periodo> flagList;
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(url, null, HttpMethod.GET, APPLICATION_JSON);
			String json = objectMapper.writeValueAsString(response.getBody());
			return objectMapper.readValue(json, new TypeReference<List<Periodo>>(){});
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), uid, e);
			flagList = new ArrayList<>();
		}
		return flagList;
	}
	
	/**
	 * Consumir API para para guardar el registro del pago
	 * @param pago
	 * @return
	 */
	@Override
	public ResponseEntity<String> addPago(Pago pago) throws JsonProcessingException{
		try {
			clientWsService.consumeService(operacionesProperties.getUrlAddPago(), pago, HttpMethod.POST, APPLICATION_JSON);
		}catch (Exception e) {
			String messageException = e.getMessage();
			int index = messageException.indexOf(':');
			messageException = messageException.substring(index+1, messageException.length());
			JsonNode json = objectMapper.readTree(messageException);
			String detalleException = json.findPath("detalle").asText();
			return new ResponseEntity<>(detalleException, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	/**
	 * Consumir API para guardar el detalle del recibo honorario sencillo
	 * @return id del nuevo recibo almacenado en base de datos
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Integer> addReciboHonorario(ReciboHonorario recibo) throws JsonProcessingException{
		int idReciboHonorario = 0;
		HttpHeaders headers = new HttpHeaders();
		try {
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(
					operacionesProperties.getUrlAddReciboHonorario(), recibo, HttpMethod.POST, APPLICATION_JSON);
			idReciboHonorario = Integer.parseInt(response.getBody().get("id").asText());
		}catch (Exception e) {
			String detailException = getServerExceptionDetail(e.getMessage());
			headers.set("serverResponse", detailException);
			return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(idReciboHonorario, HttpStatus.OK);
	}
	
	/**
	 * Consumir API para convertir el numero a letra
	 * @param Numero
	 * @return Numero en letras
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String convertNumberToLetters(Float number) {
		try {
			String url = operacionesProperties.getUrlAPINumberToLetters().concat(number.toString());
			ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(url, null, HttpMethod.GET, APPLICATION_JSON);
			return response.getBody().path("letras").asText();
		} catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), "convertNumberToLetters", e);
		}
		return "null";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pago> getPagosListByFormaPago(String formaPagoId, String startDate, String endDate) throws JsonProcessingException {
		String url = operacionesProperties.getUrlReportePagosFormaPago()+
				"formaPagoId="+formaPagoId+"&startDate="+startDate+"&endDate="+endDate;
		ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(url, null, HttpMethod.GET, APPLICATION_JSON);
		String json = objectMapper.writeValueAsString(response.getBody());
		return objectMapper.readValue(json, new TypeReference<List<Pago>>(){});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pago> getPagosListByTipoHonorario(Integer tipoHonorarioId, String startDate, String endDate) throws JsonProcessingException {
		String url = operacionesProperties.getUrlReportePagosTipoHonorario()+
				"tipoHonorarioId="+tipoHonorarioId+"&startDate="+startDate+"&endDate="+endDate;
		ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(url, null, HttpMethod.GET, APPLICATION_JSON);
		String json = objectMapper.writeValueAsString(response.getBody());
		return objectMapper.readValue(json, new TypeReference<List<Pago>>(){});
	}
	
	private String getServerExceptionDetail(String messageException) {
		try {
			int index = messageException.indexOf(':');
			messageException = messageException.substring(index+1, messageException.length());
			JsonNode json;
			json = objectMapper.readTree(messageException);
			return json.findPath("detalle").asText();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "Not found detail";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pago> getPagosCliente(String clienteRFC) throws JsonProcessingException {
		String url = operacionesProperties.getUrlHistorialPagos().concat(clienteRFC);
		ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) clientWsService.consumeService(url, null, HttpMethod.GET, APPLICATION_JSON);
		String json = objectMapper.writeValueAsString(response.getBody());
		return objectMapper.readValue(json, new TypeReference<List<Pago>>(){});
	}
}
