package org.pybee.cassowary.test;

import java.util.Random;

import org.pybee.cassowary.*;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Tests {
    static private Random RND;
    private static double EPSILON = 1.0e-8;

    public Tests()
    {
        RND = new Random(123456789);
    }

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

    private static final int CONSTRAINT_COUNT = 900;
    private static final int VARIABLE_COUNT = 900;
    private static final int RESOLVE_COUNT = 100000;

    @Test
    public void addDel()
            throws InternalError, RequiredFailure, NonlinearExpression, ConstraintNotFound {
        Timer timer = new Timer();
        // FIXGJB: from where did .12 come?
        final double ineqProb = 0.12;
        final int maxVars = 3;

        System.out.println("starting timing test. nCns = " + CONSTRAINT_COUNT + ", nVars = " + VARIABLE_COUNT + ", nResolves = " + RESOLVE_COUNT);

        timer.Start();
        SimplexSolver solver = new SimplexSolver();

        Variable[] rgpclv = new Variable[VARIABLE_COUNT];
        for (int i = 0; i < VARIABLE_COUNT; i++)
        {
            rgpclv[i] = new Variable(i,"x");
            solver.addStay(rgpclv[i]);
        }

        AbstractConstraint[] rgpcns = new AbstractConstraint[CONSTRAINT_COUNT];
        int nvs = 0;
        int k;
        int j;
        double coeff;
        for (j = 0; j < CONSTRAINT_COUNT; j++)
        {
            // number of variables in this constraint
            nvs = RandomInRange(1,maxVars);
            Expression expr = new Expression(UniformRandomDiscretized() * 20.0 - 10.0);
            for (k = 0; k < nvs; k++) {
                coeff = UniformRandomDiscretized()*10 - 5;
                int iclv = (int) (UniformRandomDiscretized()*VARIABLE_COUNT);
                expr.addExpression(rgpclv[iclv].times(coeff));
            }
            rgpcns[j] = new Constraint(expr);
        }

        System.out.println("done building data structures");
        System.out.println("time = " + timer.ElapsedTime());
        timer.Start();
        int cExceptions = 0;
        for (j = 0; j < CONSTRAINT_COUNT; j++) {
            // add the constraint -- if it's incompatible, just ignore it
            // FIXGJB: exceptions are extra expensive in C++, so this might not
            // be particularly fair
            try
            {
                solver.addConstraint(rgpcns[j]);
            }
            catch (RequiredFailure err)
            {
                cExceptions++;
                rgpcns[j] = null;
            }
        }
        // FIXGJB end = Timer.now();
        System.out.println("done adding constraints [" + cExceptions + " exceptions]");
        System.out.println("time = " + timer.ElapsedTime() + "\n");
        timer.Start();

        int e1Index = (int) (UniformRandomDiscretized()*VARIABLE_COUNT);
        int e2Index = (int) (UniformRandomDiscretized()*VARIABLE_COUNT);

        System.out.println("indices " + e1Index + ", " + e2Index);

        EditConstraint edit1 = new EditConstraint(rgpclv[e1Index], Strength.STRONG);
        EditConstraint edit2 = new EditConstraint(rgpclv[e2Index], Strength.STRONG);

        solver.addConstraint(edit1);
        solver.addConstraint(edit2);

        System.out.println("done creating edit constraints -- about to start resolves");
        System.out.println("time = " + timer.ElapsedTime() + "\n");
        timer.Start();

        // FIXGJB start = Timer.now();
        for (int m = 0; m < RESOLVE_COUNT; m++) {
            solver.resolve(rgpclv[e1Index].value() * 1.001, rgpclv[e2Index].value() * 1.001);
        }

        System.out.println("done resolves -- now removing constraints");
        System.out.println("time = " + timer.ElapsedTime() + "\n");

        solver.removeConstraint(edit1);
        solver.removeConstraint(edit2);

        timer.Start();

        for (j = 0; j < CONSTRAINT_COUNT; j++)
        {
            if (rgpcns[j] != null)
            {
                solver.removeConstraint(rgpcns[j]);
            }
        }

        System.out.println("done removing constraints and addDel timing test");
        System.out.println("time = " + timer.ElapsedTime() + "\n");
    }

    public final static double UniformRandomDiscretized()
    {
        double n = Math.abs(RND.nextInt());
        return (n/Integer.MAX_VALUE);
    }

    public final static int RandomInRange(int low, int high)
    {
        return (int) UniformRandomDiscretized()*(high-low)+low;
    }

}
