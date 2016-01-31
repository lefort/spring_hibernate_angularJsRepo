package com.esprit.springproject.web.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.esprit.springproject.persistence.entity.Annonce;
import com.esprit.springproject.service.IAnnonceService;
import com.esprit.springproject.service.IImageService;

@Controller
@Scope("session")
public class AnnonceController {
	Logger logger = Logger.getLogger(AnnonceController.class.getName());

	@Autowired(required = true)
	private IAnnonceService annonceService;

	@Autowired(required = true)
	private IImageService imageService;

	@RequestMapping(value = "/annonces", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Annonce>> listAllAnnonces() {
		List<Annonce> annonces = annonceService.findAll();
		if (annonces == null || annonces.isEmpty()) {
			logger.log(Level.INFO, "No anonce returned");
			return new ResponseEntity<List<Annonce>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Annonce>>(annonces, HttpStatus.OK);
	}

	@RequestMapping(value = "/annonce/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Annonce> getAnnonce(@PathVariable("id") long id) {
		Annonce annonce = annonceService.findOne(id);
		if (annonce == null) {
			logger.log(Level.INFO, "No anonce found");
			return new ResponseEntity<Annonce>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Annonce>(annonce, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/annonce/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Annonce> updateAnnonces(@PathVariable("id") long id, @RequestBody Annonce annonce) {
		Annonce currentAnnonce = annonceService.findOne(id);
		if (currentAnnonce == null) {
			return new ResponseEntity<Annonce>(HttpStatus.NOT_FOUND);
		} else {
			annonceService.update(annonce);
			return new ResponseEntity<Annonce>(annonce, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/annonces", method = RequestMethod.POST)
	public ResponseEntity<Void> createAnnonce(@RequestBody Annonce annonce, UriComponentsBuilder ucBuilder) {
		if (annonce != null) {
			annonceService.save(annonce);
		} else {
			logger.log(Level.SEVERE, "null entity passed to save");
		}
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/annonce/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Annonce> deleteAnnonce(@PathVariable("id") long id) {

		Annonce annonce = annonceService.findOne(id);
		if (annonce == null) {
			return new ResponseEntity<Annonce>(HttpStatus.NOT_FOUND);
		} else {
			annonceService.delete(id);
		}

		return new ResponseEntity<Annonce>(HttpStatus.MOVED_PERMANENTLY);
	}
}
