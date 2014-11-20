package org.pybee.cassowary.test;

import org.junit.Test;
import org.pybee.cassowary.AbstractConstraint;
import org.pybee.cassowary.Constraint;
import org.pybee.cassowary.ConstraintNotFound;
import org.pybee.cassowary.EditConstraint;
import org.pybee.cassowary.Expression;
import org.pybee.cassowary.NonlinearExpression;
import org.pybee.cassowary.RequiredFailure;
import org.pybee.cassowary.SimplexSolver;
import org.pybee.cassowary.Strength;
import org.pybee.cassowary.Variable;

import java.util.Random;

/**
 * Created by alex on 20/11/2014.
 */
public class TimingTests {

    private static final Random RND = new Random(123456789);
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
