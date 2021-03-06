package com.cfm.sacc.socios.service;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.cfm.sacc.socios.model.Porcentaje;
import com.cfm.sacc.socios.model.PorcentajeSocioRep;
import com.cfm.sacc.socios.model.Socio;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ISocioService {
	List<Socio> getAllSocios();
	List<Socio> getSociosActivos();
	List<PorcentajeSocioRep> getAllPorcentajes();
	HttpStatus addPorcentaje(List<Porcentaje> listaPorcentajes) throws JsonProcessingException;
}
