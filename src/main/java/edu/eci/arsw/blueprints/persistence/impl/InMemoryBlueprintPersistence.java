/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hcadavid
 */
@Component("inMemorybp")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        ArrayList<Point> pts = new ArrayList<>(Arrays.asList(new Point(140, 140),
                new Point(115, 115),
                new Point(100, 80),
                new Point(100, 80),
                new Point(160, 400)));
        Blueprint bp1 = new Blueprint("pau", "la desgracia de sgbd", pts);
        Blueprint bp2 = new Blueprint("pau", "el plano de la casa", pts);
        Blueprint bp3 = new Blueprint("lau", "totoro", pts);
        Blueprint bp4 = new Blueprint("dani", "area 51", pts);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
        blueprints.put(new Tuple<>(bp4.getAuthor(), bp4.getName()), bp4);
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    @Override
    public void updateBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            Blueprint actualBlueprint = blueprints.get(new Tuple<>(bp.getAuthor(), bp.getName()));
            actualBlueprint.setAuthor(bp.getAuthor());
            actualBlueprint.setName(bp.getName());
            actualBlueprint.setPoints(bp.getPoints());
        } else {
            throw new BlueprintPersistenceException("The given blueprint does not exists: " + bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        if (blueprints.containsKey(new Tuple<>(author, bprintname))) {
            return blueprints.get(new Tuple<>(author, bprintname));
        } else {
            throw new BlueprintNotFoundException("No existe el plano buscado");
        }
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> authorBlueprints = new HashSet<>();
        for (Blueprint blueprint : blueprints.values()) {
            if (blueprint.getAuthor().equals(author)) {
                authorBlueprints.add(blueprint);
            }
        }
        if (authorBlueprints.isEmpty()) {
            throw new BlueprintNotFoundException("No existe ningún plano del autor");
        }
        return authorBlueprints;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> authorBlueprints = new HashSet<>();
        for (Blueprint bp : blueprints.values()) {
            authorBlueprints.add(bp);
        }
        if (authorBlueprints.isEmpty()) {
            throw new BlueprintNotFoundException("No existe ningún plano del autor");
        }
        return authorBlueprints;
    }
}
