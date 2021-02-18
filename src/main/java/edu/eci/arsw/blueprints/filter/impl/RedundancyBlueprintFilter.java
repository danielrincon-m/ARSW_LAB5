package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component("redFilter")
public class RedundancyBlueprintFilter implements BlueprintFilter {
    /**
     * @param blueprint The blueprint to filter
     * @return A filtered blueprint
     */
    @Override
    public Blueprint filter(Blueprint blueprint) {
        ArrayList<Point> blueprintPoints = blueprint.getPoints();
        ArrayList<Point> pointsToRemove = new ArrayList<>();

        Point lastPoint = blueprintPoints.get(0);
        for (int i = 1; i < blueprintPoints.size(); i++) {
            Point p = blueprintPoints.get(i);
            if (lastPoint.equals(p)) {
                pointsToRemove.add(p);
            } else {
                lastPoint = p;
            }
        }
        for (Point p : pointsToRemove) {
            blueprintPoints.remove(p);
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
