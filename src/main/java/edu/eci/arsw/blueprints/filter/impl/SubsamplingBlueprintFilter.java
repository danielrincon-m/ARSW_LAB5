package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component("subFilter")
public class SubsamplingBlueprintFilter implements BlueprintFilter {
    /**
     * @param blueprint The blueprint to filter
     * @return A filtered blueprint
     */
    @Override
    public Blueprint filter(Blueprint blueprint) {
        ArrayList<Point> blueprintPoints = blueprint.getPoints();
        for (int i = blueprintPoints.size() - 1; i >= 0; i--) {
            if (i % 2 == 0) {
                blueprintPoints.remove(i);
            }
        }
        return new Blueprint(blueprint.getAuthor(), blueprint.getName(), blueprintPoints);
    }

    /**
     * @param blueprints The set of blueprints to filter
     * @return A filtered set of blueprints
     */
    @Override
    public Set<Blueprint> filter(Set<Blueprint> blueprints) {
        HashSet<Blueprint> filteredBlueprints = new HashSet<>();
        for (Blueprint bp : blueprints) {
            filteredBlueprints.add(filter(bp));
        }
        return filteredBlueprints;
    }
}
