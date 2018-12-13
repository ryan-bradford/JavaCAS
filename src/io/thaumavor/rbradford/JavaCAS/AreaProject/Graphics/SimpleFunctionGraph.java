package io.thaumavor.rbradford.JavaCAS.AreaProject.Graphics;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ryan_bradford on 4/26/17.
 */
public class SimpleFunctionGraph extends Drawing {

    public SimpleFunctionGraph(Function toGraph) {
        functions = new ArrayList<Function>();
        functions.add(toGraph);
    }

    public SimpleFunctionGraph(Function f, Function g) {
        functions = new ArrayList<Function>();
        functions.add(f);
        functions.add(g);
        shadedParts.add(new Area(f, g, Color.black, -3.14, 3.14));
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }
}
