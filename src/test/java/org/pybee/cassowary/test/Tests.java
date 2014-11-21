package org.pybee.cassowary.test;

import java.util.Random;

import org.pybee.cassowary.*;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Tests {

    private static double EPSILON = 1.0e-8;

    @Test
    public void simple1() {
        Variable x = new Variable(167);
        Variable y = new Variable(2);
        SimplexSolver solver = new SimplexSolver();

        Constraint eq = new Constraint(x, Constraint.Operator.EQ, new Expression(y));
        //ClLinearEquation eq = new ClLinearEquation(x, new ClLinearExpression(y));
        solver.addConstraint(eq);
        assertEquals(x.value(), y.value(), EPSILON);
    }

    @Test
    public void justStay1() {
        Variable x = new Variable(5);
        Variable y = new Variable(10);
        SimplexSolver solver = new SimplexSolver();

        solver.addStay(x);
        solver.addStay(y);
        assertEquals(5, x.value(), EPSILON);
        assertEquals(10, y.value(), EPSILON);
    }


    @Test
    public void addDelete1() throws ConstraintNotFound {
        Variable x = new Variable("x");
        SimplexSolver solver = new SimplexSolver();

        solver.addConstraint(new Constraint(x, Constraint.Operator.EQ, 100, Strength.WEAK));

        Constraint c10 = new Constraint(x, Constraint.Operator.LEQ, 10.0);
        Constraint c20 = new Constraint(x, Constraint.Operator.LEQ, 20.0);

        solver.addConstraint(c10);
        solver.addConstraint(c20);

        assertEquals(10, x.value(), EPSILON);

        solver.removeConstraint(c10);
        assertEquals(20, x.value(), EPSILON);

        solver.removeConstraint(c20);
        assertEquals(100, x.value(), EPSILON);

        Constraint c10again = new Constraint(x, Constraint.Operator.LEQ, 10.0);

        solver.addConstraint(c10);
        solver.addConstraint(c10again);

        assertEquals(10, x.value(), EPSILON);

        solver.removeConstraint(c10);
        assertEquals(10, x.value(), EPSILON);

        solver.removeConstraint(c10again);
        assertEquals(100, x.value(), EPSILON);
    }


    @Test
    public void addDelete2() throws ConstraintNotFound {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        SimplexSolver solver = new SimplexSolver();

        solver.addConstraint(new Constraint(x, Constraint.Operator.EQ, 100.0, Strength.WEAK));
        solver.addConstraint(new Constraint(y, Constraint.Operator.EQ, 120.0, Strength.STRONG));


        Constraint c10 = new Constraint(x, Constraint.Operator.LEQ, 10.0);
        Constraint c20 = new Constraint(x, Constraint.Operator.LEQ, 20.0);

        solver.addConstraint(c10);
        solver.addConstraint(c20);

        assertEquals(10, x.value(), EPSILON);
        assertEquals(120, y.value(), EPSILON);

        solver.removeConstraint(c10);
        assertEquals(20, x.value(), EPSILON);
        assertEquals(120, y.value(), EPSILON);

        Constraint cxy = new Constraint(x.times(2.0), Constraint.Operator.EQ, y);
        solver.addConstraint(cxy);
        assertEquals(20, x.value(), EPSILON);
        assertEquals(40, y.value(), EPSILON);

        solver.removeConstraint(c20);
        assertEquals(60, x.value(), EPSILON);
        assertEquals(120, y.value(), EPSILON);

        solver.removeConstraint(cxy);
        assertEquals(100, x.value(), EPSILON);
        assertEquals(120, y.value(), EPSILON);
    }

    @Test
    public void casso1() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        SimplexSolver solver = new SimplexSolver();

        solver.addConstraint(new Constraint(x, Constraint.Operator.LEQ, y));
        solver.addConstraint(new Constraint(y, Constraint.Operator.EQ, x.plus(3.0)));
        solver.addConstraint(new Constraint(x, Constraint.Operator.EQ, 10.0, Strength.WEAK));
        solver.addConstraint(new Constraint(y, Constraint.Operator.EQ, 10.0, Strength.WEAK));

        if (Math.abs(x.value() - 10.0) < EPSILON) {
            assertEquals(10, x.value(), EPSILON);
            assertEquals(13, y.value(), EPSILON);
        } else {
            assertEquals(7, x.value(), EPSILON);
            assertEquals(10, y.value(), EPSILON);
        }
    }

    @Test(expected = RequiredFailure.class)
    public void inconsistent1() throws InternalError {
        Variable x = new Variable("x");
        SimplexSolver solver = new SimplexSolver();

        solver.addConstraint(new Constraint(x, Constraint.Operator.EQ, 10.0));
        solver.addConstraint(new Constraint(x, Constraint.Operator.EQ, 5.0));
    }


    @Test(expected = RequiredFailure.class)
    public void inconsistent2() {
        Variable x = new Variable("x");
        SimplexSolver solver = new SimplexSolver();

        solver.addConstraint(new Constraint(x, Constraint.Operator.GEQ, 10.0));
        solver.addConstraint(new Constraint(x, Constraint.Operator.LEQ, 5.0));
    }

    @Test
    public void multiedit() throws CassowaryError {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable w = new Variable("w");
        Variable h = new Variable("h");
        SimplexSolver solver = new SimplexSolver();

        solver.addStay(x);
        solver.addStay(y);
        solver.addStay(w);
        solver.addStay(h);

        solver.addEditVar(x);
        solver.addEditVar(y);
        solver.beginEdit();

        solver.suggestValue(x, 10);
        solver.suggestValue(y, 20);
        solver.resolve();

        assertEquals(10, x.value(), EPSILON);
        assertEquals(20, y.value(), EPSILON);
        assertEquals(0, w.value(), EPSILON);
        assertEquals(0, h.value(), EPSILON);


        solver.addEditVar(w);
        solver.addEditVar(h);
        solver.beginEdit();

        solver.suggestValue(w, 30);
        solver.suggestValue(h, 40);
        solver.endEdit();

        assertEquals(10, x.value(), EPSILON);
        assertEquals(20, y.value(), EPSILON);
        assertEquals(30, w.value(), EPSILON);
        assertEquals(40, h.value(), EPSILON);

        solver.suggestValue(x, 50).suggestValue(y, 60).endEdit();


        assertEquals(50, x.value(), EPSILON);
        assertEquals(60, y.value(), EPSILON);
        assertEquals(30, w.value(), EPSILON);
        assertEquals(40, h.value(), EPSILON);
    }


    @Test
    public void multieditAutoSolveOff() throws CassowaryError {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable w = new Variable("w");
        Variable h = new Variable("h");
        SimplexSolver solver = new SimplexSolver();
        solver.setAutosolve(false);

        solver.addStay(x);
        solver.addStay(y);
        solver.addStay(w);
        solver.addStay(h);

        solver.addEditVar(x);
        solver.addEditVar(y);
        solver.beginEdit();

        solver.suggestValue(x, 10);
        solver.suggestValue(y, 20);
        solver.resolve();
        solver.solve();

        assertEquals(10, x.value(), EPSILON);
        assertEquals(20, y.value(), EPSILON);
        assertEquals(0, w.value(), EPSILON);
        assertEquals(0, h.value(), EPSILON);


        solver.addEditVar(w);
        solver.addEditVar(h);
        solver.beginEdit();

        solver.suggestValue(w, 30);
        solver.suggestValue(h, 40);
        solver.endEdit();

        solver.solve();

        assertEquals(10, x.value(), EPSILON);
        assertEquals(20, y.value(), EPSILON);
        assertEquals(30, w.value(), EPSILON);
        assertEquals(40, h.value(), EPSILON);

        solver.suggestValue(x, 50).suggestValue(y, 60).endEdit();
        solver.solve();

        assertEquals(50, x.value(), EPSILON);
        assertEquals(60, y.value(), EPSILON);
        assertEquals(30, w.value(), EPSILON);
        assertEquals(40, h.value(), EPSILON);
    }

    @Test
    public void multieditRequiredEdits() throws CassowaryError {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable w = new Variable("w");
        Variable h = new Variable("h");
        SimplexSolver solver = new SimplexSolver();

        solver.addStay(x);
        solver.addStay(y);
        solver.addStay(w);
        solver.addStay(h);

        solver.addEditVar(x, Strength.REQUIRED);
        solver.addEditVar(y, Strength.REQUIRED);
        solver.beginEdit();

        solver.suggestValue(x, 10);
        solver.suggestValue(y, 20);
        solver.resolve();
        solver.solve();

        assertEquals(10, x.value(), EPSILON);
        assertEquals(20, y.value(), EPSILON);
        assertEquals(0, w.value(), EPSILON);
        assertEquals(0, h.value(), EPSILON);


        solver.addEditVar(w, Strength.REQUIRED);
        solver.addEditVar(h, Strength.REQUIRED);
        solver.beginEdit();

        solver.suggestValue(w, 30);
        solver.suggestValue(h, 40);
        solver.endEdit();

        solver.solve();

        assertEquals(10, x.value(), EPSILON);
        assertEquals(20, y.value(), EPSILON);
        assertEquals(30, w.value(), EPSILON);
        assertEquals(40, h.value(), EPSILON);

        solver.suggestValue(x, 50).suggestValue(y, 60).endEdit();
        solver.solve();

        assertEquals(50, x.value(), EPSILON);
        assertEquals(60, y.value(), EPSILON);
        assertEquals(30, w.value(), EPSILON);
        assertEquals(40, h.value(), EPSILON);
    }

    @Test(expected = RequiredFailure.class)
    public void inconsistent3() {

        Variable w = new Variable("w");
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable z = new Variable("z");
        SimplexSolver solver = new SimplexSolver();

        solver.addConstraint(new Constraint(w, Constraint.Operator.GEQ, 10.0));
        solver.addConstraint(new Constraint(x, Constraint.Operator.GEQ, w));
        solver.addConstraint(new Constraint(y, Constraint.Operator.GEQ, x));
        solver.addConstraint(new Constraint(z, Constraint.Operator.GEQ, y));
        solver.addConstraint(new Constraint(z, Constraint.Operator.GEQ, 8.0));
        solver.addConstraint(new Constraint(z, Constraint.Operator.LEQ, 4.0));
    }

}
