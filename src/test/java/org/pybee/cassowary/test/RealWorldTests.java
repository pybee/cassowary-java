package org.pybee.cassowary.test;

import org.junit.Test;
import org.pybee.cassowary.Constraint;
import org.pybee.cassowary.ConstraintNotFound;
import org.pybee.cassowary.Expression;
import org.pybee.cassowary.SimplexSolver;
import org.pybee.cassowary.Strength;
import org.pybee.cassowary.Variable;
import org.pybee.cassowary.test.parser.ConstraintParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by alex on 20/11/2014.
 */
public class RealWorldTests {

    private static double EPSILON = 1.0e-2;

    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String CENTERX = "centerX";
    public static final String CENTERY = "centerY";

    private static final String[] CONSTRAINTS = {

            "container.columnWidth == container.width * 0.4",
            "container.thumbHeight == container.columnWidth / 2",
            "container.padding == container.width * (0.2 / 3)",
            "container.leftPadding == container.padding",
            "container.rightPadding == container.width - container.padding",
            "container.paddingUnderThumb == 5",
            "container.rowPadding == 15",
            "container.buttonPadding == 20",

            "thumb0.left == container.leftPadding",
            "thumb0.top == container.padding",
            "thumb0.height == container.thumbHeight",
            "thumb0.width == container.columnWidth",

            "title0.left == container.leftPadding",
            "title0.top == thumb0.bottom + container.paddingUnderThumb",
            "title0.height == title0.intrinsicHeight",
            "title0.width == container.columnWidth",

            "thumb1.right == container.rightPadding",
            "thumb1.top == container.padding",
            "thumb1.height == container.thumbHeight",
            "thumb1.width == container.columnWidth",

            "title1.right == container.rightPadding",
            "title1.top == thumb0.bottom + container.paddingUnderThumb",
            "title1.height == title1.intrinsicHeight",
            "title1.width == container.columnWidth",

            "thumb2.left == container.leftPadding",
            "thumb2.top >= title0.bottom + container.rowPadding",
            "thumb2.top == title0.bottom + container.rowPadding !strong",
            "thumb2.top >= title1.bottom + container.rowPadding",
            "thumb2.top == title1.bottom + container.rowPadding !strong",
            "thumb2.height == container.thumbHeight",
            "thumb2.width == container.columnWidth",

            "title2.left == container.leftPadding",
            "title2.top == thumb2.bottom + container.paddingUnderThumb",
            "title2.height == title2.intrinsicHeight",
            "title2.width == container.columnWidth",

            "thumb3.right == container.rightPadding",
            "thumb3.top == thumb2.top",

            "thumb3.height == container.thumbHeight",
            "thumb3.width == container.columnWidth",

            "title3.right == container.rightPadding",
            "title3.top == thumb3.bottom + container.paddingUnderThumb",
            "title3.height == title3.intrinsicHeight",
            "title3.width == container.columnWidth",

            "thumb4.left == container.leftPadding",
            "thumb4.top >= title2.bottom + container.rowPadding",
            "thumb4.top >= title3.bottom + container.rowPadding",
            "thumb4.top == title2.bottom + container.rowPadding !strong",
            "thumb4.top == title3.bottom + container.rowPadding !strong",
            "thumb4.height == container.thumbHeight",
            "thumb4.width == container.columnWidth",

            "title4.left == container.leftPadding",
            "title4.top == thumb4.bottom + container.paddingUnderThumb",
            "title4.height == title4.intrinsicHeight",
            "title4.width == container.columnWidth",

            "thumb5.right == container.rightPadding",
            "thumb5.top == thumb4.top",
            "thumb5.height == container.thumbHeight",
            "thumb5.width == container.columnWidth",

            "title5.right == container.rightPadding",
            "title5.top == thumb5.bottom + container.paddingUnderThumb",
            "title5.height == title5.intrinsicHeight",
            "title5.width == container.columnWidth",

            "line.height == 1",
            "line.width == container.width",
            "line.top >= title4.bottom + container.rowPadding",
            "line.top >= title5.bottom + container.rowPadding",

            "more.top == line.bottom + container.buttonPadding",
            "more.height == more.intrinsicHeight",
            "more.left == container.leftPadding",
            "more.right == container.rightPadding",

            "container.height == more.bottom + container.buttonPadding"};

    @Test
    public void testGrid() throws ConstraintNotFound {

        final SimplexSolver solver = new SimplexSolver();
        solver.setAutosolve(false);

        final HashMap<String, HashMap<String, Variable>> nodeHashMap = new HashMap<String, HashMap<String, Variable>>();


        ConstraintParser.CassowaryVariableResolver variableResolver = new ConstraintParser.CassowaryVariableResolver() {

            private Variable getVariableFromNode(HashMap<String, Variable> node, String variableName) {
                if (node.containsKey(variableName)) {
                    return node.get(variableName);
                } else {
                    Variable variable = new Variable();
                    node.put(variableName, variable);
                    if (RIGHT.equals(variableName)) {
                        solver.addConstraint(new Constraint(variable, Constraint.Operator.EQ, new Expression(getVariableFromNode(node, LEFT)).plus(getVariableFromNode(node, WIDTH)), Strength.REQUIRED));
                    } else if (BOTTOM.equals(variableName)) {
                        solver.addConstraint(new Constraint(variable, Constraint.Operator.EQ, new Expression(getVariableFromNode(node, TOP)).plus(getVariableFromNode(node, HEIGHT)), Strength.REQUIRED));
                    } else if (CENTERX.equals(variableName)) {
                        solver.addConstraint(new Constraint(variable, Constraint.Operator.EQ, new Expression(getVariableFromNode(node, WIDTH)).divide(2).plus(getVariableFromNode(node, LEFT)), Strength.REQUIRED));
                    } else if (CENTERY.equals(variableName)) {
                        solver.addConstraint(new Constraint(variable, Constraint.Operator.EQ, new Expression(getVariableFromNode(node, HEIGHT)).divide(2).plus(getVariableFromNode(node, TOP)), Strength.REQUIRED));
                    }
                    return variable;
                }
            }

            private HashMap<String, Variable> getNode(String nodeName) {
                HashMap<String, Variable> node;
                if (nodeHashMap.containsKey(nodeName)) {
                    node = nodeHashMap.get(nodeName);
                } else {
                    node = new HashMap<String, Variable>();
                    nodeHashMap.put(nodeName, node);
                }
                return node;
            }

            @Override
            public Variable resolveVariable(String variableName) {

                String[] stringArray = variableName.split("\\.");
                if (stringArray.length == 2) {
                    String nodeName = stringArray[0];
                    String propertyName = stringArray[1];

                    HashMap<String, Variable> node = getNode(nodeName);

                    return getVariableFromNode(node, propertyName);

                } else {
                    throw new RuntimeException("can't resolve variable");
                }
            }

            @Override
            public Expression resolveConstant(String name) {
                try {
                    return new Expression(Double.parseDouble(name));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        };

        for (String constraint : CONSTRAINTS) {
            solver.addConstraint(ConstraintParser.parseConstraint(constraint, variableResolver));
        }

        System.out.println("node count " + nodeHashMap.size());

        solver.addConstraint(ConstraintParser.parseConstraint("container.width == 300", variableResolver));
        solver.addConstraint(ConstraintParser.parseConstraint("title0.intrinsicHeight == 100", variableResolver));
        solver.addConstraint(ConstraintParser.parseConstraint("title1.intrinsicHeight == 110", variableResolver));
        solver.addConstraint(ConstraintParser.parseConstraint("title2.intrinsicHeight == 120", variableResolver));
        solver.addConstraint(ConstraintParser.parseConstraint("title3.intrinsicHeight == 130", variableResolver));
        solver.addConstraint(ConstraintParser.parseConstraint("title4.intrinsicHeight == 140", variableResolver));
        solver.addConstraint(ConstraintParser.parseConstraint("title5.intrinsicHeight == 150", variableResolver));
        solver.addConstraint(ConstraintParser.parseConstraint("more.intrinsicHeight == 160", variableResolver));

        solver.solve();

        System.out.println("variable count " + nodeHashMap.size());
        printNodes(nodeHashMap);


        assertEquals(20, nodeHashMap.get("thumb0").get("top").value(), EPSILON);
        assertEquals(20, nodeHashMap.get("thumb1").get("top").value(), EPSILON);

        assertEquals(85, nodeHashMap.get("title0").get("top").value(), EPSILON);
        assertEquals(85, nodeHashMap.get("title1").get("top").value(), EPSILON);


        assertEquals(210, nodeHashMap.get("thumb2").get("top").value(), EPSILON);
        assertEquals(210, nodeHashMap.get("thumb3").get("top").value(), EPSILON);

        assertEquals(275, nodeHashMap.get("title2").get("top").value(), EPSILON);
        assertEquals(275, nodeHashMap.get("title3").get("top").value(), EPSILON);

        assertEquals(420, nodeHashMap.get("thumb4").get("top").value(), EPSILON);
        assertEquals(420, nodeHashMap.get("thumb5").get("top").value(), EPSILON);

        assertEquals(485, nodeHashMap.get("title4").get("top").value(), EPSILON);
        assertEquals(485, nodeHashMap.get("title5").get("top").value(), EPSILON);

    }

    private static void printNodes(HashMap<String, HashMap<String, Variable>> variableHashMap) {
        Iterator<Map.Entry<String, HashMap<String, Variable>>> it = variableHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, HashMap<String, Variable>> pairs = it.next();
            System.out.println("node " + pairs.getKey());
            printVariables(pairs.getValue());
        }
    }

    private static void printVariables(HashMap<String, Variable> variableHashMap) {
        Iterator<Map.Entry<String, Variable>> it = variableHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Variable> pairs = it.next();
            System.out.println(" " + pairs.getKey() + " = " + pairs.getValue().value());
        }
    }
}
