/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author hcadavid
 */
@Component("bpServices")
public class BlueprintsServices {

    @Autowired
    private BlueprintsPersistence bpp = null;

    @Autowired
//    @Qualifier("subFilter")
    @Qualifier("redFilter")
    private BlueprintFilter bpf = null;

    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    public void updateBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.updateBlueprint(bp);
    }

    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        return bpf.filter(bpp.getAllBlueprints());
    }

    /**
     * @param author blueprint's author
     * @param name   blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return bpf.filter(bpp.getBlueprint(author, name));
    }

    /**
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return bpf.filter(bpp.getBlueprintsByAuthor(author));
    }

    public String getBlueprintsJson(Set<Blueprint> blueprints) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(blueprints);
    }

    public String getBlueprintJson(Blueprint blueprint) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(blueprint);
    }

    public void setBpp(BlueprintsPersistence bpp) {
        this.bpp = bpp;
    }

    public void setBpf(BlueprintFilter bpf) {
        this.bpf = bpf;
    }
}
