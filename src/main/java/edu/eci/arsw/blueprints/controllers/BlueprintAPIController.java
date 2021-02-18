/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hcadavid
 */
@RestController
public class BlueprintAPIController {

    @Autowired
    @Qualifier("bpServices")
    private BlueprintsServices bps = null;

    @RequestMapping(value = "/blueprints", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> manejadorBlueprints() {
        try {
            Set<Blueprint> blueprints = bps.getAllBlueprints();
            return new ResponseEntity<>(bps.getBlueprintsJson(blueprints), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No hay planos :(", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/blueprints/{author}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> manejadorBlueprintsAuthor(@PathVariable String author) {
        try {
            Set<Blueprint> blueprints = bps.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(bps.getBlueprintsJson(blueprints), HttpStatus.OK);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No existe ningún plano del autor :(", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/blueprints/{author}/{bpname}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> manejadorBlueprintsAuthorBpName(@PathVariable String author, @PathVariable String bpname) {
        try {
            Blueprint blueprint = bps.getBlueprint(author, bpname);
            return new ResponseEntity<>(bps.getBlueprintJson(blueprint), HttpStatus.OK);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No existe el plano :(", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/blueprints", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> manejadorPostBlueprint(@RequestBody Blueprint blueprint){
        try {
            bps.addNewBlueprint(blueprint);
            return new ResponseEntity<>("Se ha registrado el plano exitosamente.", HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No se ha podido registrar el plano :(",HttpStatus.FORBIDDEN);
        }
    }

    public void setBps(BlueprintsServices bps) {
        this.bps = bps;
    }
}

