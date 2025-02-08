package zura.premium.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import zura.premium.model.ListarQuerysRequest;
import zura.premium.model.ListarQuerysResponse;
import zura.premium.service.ListarQuerysService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/listarquerys")
public class PremiunRest {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ListarQuerysService listarquerysService;

	
	@PostMapping(path = "/getListarQuerys")
	public ResponseEntity<List<ListarQuerysResponse>> getListarQuerys(
			@RequestBody ListarQuerysRequest listarquerysRequest) {

		List<ListarQuerysResponse> listListarQuerys = new ArrayList<>();

		try {
			listListarQuerys = listarquerysService.getListarQuerys(listarquerysRequest);
		} catch (Exception e) {									
			logger.info("Error al traer la informacion: " + e.getMessage());
		}
		return new ResponseEntity<List<ListarQuerysResponse>>(listListarQuerys, HttpStatus.OK);

	}
	
	@PostMapping(path = "/getLibrosAuxiliares")
	public ResponseEntity<List<ListarQuerysResponse>> getLibrosAuxiliares(
			@RequestBody ListarQuerysRequest listarquerysRequest) {

		List<ListarQuerysResponse> listListarQuerys = new ArrayList<>();

		try {
			listListarQuerys = listarquerysService.getListarQuerys(listarquerysRequest);
		} catch (Exception e) {									
			logger.info("Error al traer la informacion: " + e.getMessage());
		}
		return new ResponseEntity<List<ListarQuerysResponse>>(listListarQuerys, HttpStatus.OK);

	}
	@PostMapping(path = "/getIListarQuerys")
	public ResponseEntity<List<ListarQuerysResponse>> getIListarQuerys(
			@RequestBody ListarQuerysRequest listarquerysRequest) {

		List<ListarQuerysResponse> listListarQuerys = new ArrayList<>();

		try {
			listListarQuerys = listarquerysService.getIListarQuerys(listarquerysRequest);
		} catch (Exception e) {									
			logger.info("Error al traer la informacion: " + e.getMessage());
		}
		return new ResponseEntity<List<ListarQuerysResponse>>(listListarQuerys, HttpStatus.OK);

	}
}