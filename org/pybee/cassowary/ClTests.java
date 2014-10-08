package org.pybee.cassowary;

import java.util.Random;


public class ClTests extends CL {
    public ClTests()
    {
        RND = new Random(123456789);
    }

    public final static boolean simple1()
            throws InternalError, RequiredFailure
    {
        boolean fOkResult = true;
        Variable x = new Variable(167);
        Variable y = new Variable(2);
        SimplexSolver solver = new SimplexSolver();

        LinearEquation eq = new LinearEquation(x,new LinearExpression(y));
        solver.addConstraint(eq);
        fOkResult = (x.value() == y.value());

        System.out.println("x == " + x.value());
        System.out.println("y == " + y.value());
        return(fOkResult);
    }

    public final static boolean justStay1()
           throws InternalError, RequiredFailure
    {
        boolean fOkResult = true;
        Variable x = new Variable(5);
        Variable y = new Variable(10);
        SimplexSolver solver = new SimplexSolver();

        solver.addStay(x);
        solver.addStay(y);
        fOkResult = fOkResult && CL.approx(x,5);
        fOkResult = fOkResult && CL.approx(y,10);
        System.out.println("x == " + x.value());
        System.out.println("y == " + y.value());
        return(fOkResult);
    }

    public final static boolean addDelete1()
            throws InternalError, RequiredFailure, ConstraintNotFound
    {
        boolean fOkResult = true;
        Variable x = new Variable("x");
        SimplexSolver solver = new SimplexSolver();

        solver.addConstraint( new LinearEquation( x, 100, Strength.weak ) );

        LinearInequality c10 = new LinearInequality(x,CL.LEQ,10.0);
        LinearInequality c20 = new LinearInequality(x,CL.LEQ,20.0);

        solver.addConstraint(c10);
        solver.addConstraint(c20);

        fOkResult = fOkResult && CL.approx(x,10.0);
        System.out.println("x == " + x.value());

        solver.removeConstraint(c10);
        fOkResult = fOkResult && CL.approx(x,20.0);
        System.out.println("x == " + x.value());

        solver.removeConstraint(c20);
        fOkResult = fOkResult && CL.approx(x,100.0);
        System.out.println("x == " + x.value());

        LinearInequality c10again = new LinearInequality(x,CL.LEQ,10.0);

        solver.addConstraint(c10);
        solver.addConstraint(c10again);

        fOkResult = fOkResult && CL.approx(x,10.0);
        System.out.println("x == " + x.value());

        solver.removeConstraint(c10);
        fOkResult = fOkResult && CL.approx(x,10.0);
        System.out.println("x == " + x.value());

        solver.removeConstraint(c10again);
        fOkResult = fOkResult && CL.approx(x,100.0);
        System.out.println("x == " + x.value());

        return(fOkResult);
    }

    public final static boolean addDelete2()
            throws InternalError, RequiredFailure, ConstraintNotFound, NonlinearExpression
    {
        boolean fOkResult = true;
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        SimplexSolver solver = new SimplexSolver();

        solver.addConstraint(new LinearEquation(x, 100.0, Strength.weak));
        solver.addConstraint(new LinearEquation(y, 120.0, Strength.strong));

        LinearInequality c10 = new LinearInequality(x,CL.LEQ,10.0);
        LinearInequality c20 = new LinearInequality(x,CL.LEQ,20.0);

        solver.addConstraint(c10);
        solver.addConstraint(c20);

        fOkResult = fOkResult && CL.approx(x,10.0) && CL.approx(y,120.0);
        System.out.println("x == " + x.value() + ", y == " + y.value());

        solver.removeConstraint(c10);
        fOkResult = fOkResult && CL.approx(x,20.0) && CL.approx(y,120.0);
        System.out.println("x == " + x.value() + ", y == " + y.value());

        LinearEquation cxy = new LinearEquation(x.times(2.0), y);
        solver.addConstraint(cxy);
        fOkResult = fOkResult && CL.approx(x,20.0) && CL.approx(y,40.0);
        System.out.println("x == " + x.value() + ", y == " + y.value());

        solver.removeConstraint(c20);
        fOkResult = fOkResult && CL.approx(x,60.0) && CL.approx(y,120.0);
        System.out.println("x == " + x.value() + ", y == " + y.value());

        solver.removeConstraint(cxy);
        fOkResult = fOkResult && CL.approx(x,100.0) && CL.approx(y,120.0);
        System.out.println("x == " + x.value() + ", y == " + y.value());

        return(fOkResult);
    }

    public final static boolean casso1()
        throws InternalError, RequiredFailure
    {
        boolean fOkResult = true;
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        SimplexSolver solver = new SimplexSolver();

        solver.addConstraint(new LinearInequality(x,CL.LEQ,y));
        solver.addConstraint(new LinearEquation(y, x.plus(3.0)));
        solver.addConstraint(new LinearEquation(x,10.0,Strength.weak));
        solver.addConstraint(new LinearEquation(y,10.0,Strength.weak));

        fOkResult = fOkResult && ( CL.approx(x,10.0) && CL.approx(y,13.0) || CL.approx(x,7.0) && CL.approx(y,10.0) );

        System.out.println("x == " + x.value() + ", y == " + y.value());
        return(fOkResult);
    }

    public final static boolean inconsistent1()
            throws InternalError, RequiredFailure
    {
        try
        {
            Variable x = new Variable("x");
            SimplexSolver solver = new SimplexSolver();

            solver.addConstraint(new LinearEquation(x,10.0));
            solver.addConstraint(new LinearEquation(x, 5.0));

            // no exception, we failed!
            return(false);
        }
        catch (RequiredFailure err)
        {
            // we want this exception to get thrown
            System.out.println("Success -- got the exception");
            return(true);
        }
    }

    public final static boolean inconsistent2()
            throws InternalError, RequiredFailure
    {
        try
        {
            Variable x = new Variable("x");
            SimplexSolver solver = new SimplexSolver();

            solver.addConstraint(new LinearInequality(x,CL.GEQ,10.0));
            solver.addConstraint(new LinearInequality(x,CL.LEQ, 5.0));

            // no exception, we failed!
            return(false);
        }
        catch (RequiredFailure err)
        {
            // we want this exception to get thrown
            System.out.println("Success -- got the exception");
            return(true);
        }
    }

    public final static boolean multiedit()
            throws InternalError, RequiredFailure, CassowaryError
    {
        try
        {
            boolean fOkResult = true;

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

            solver.suggestValue(x,10);
            solver.suggestValue(y,20);
            solver.resolve();

            System.out.println("x = " + x.value() + "; y = " + y.value());
            System.out.println("w = " + w.value() + "; h = " + h.value());

            fOkResult = fOkResult && CL.approx(x,10) && CL.approx(y,20) && CL.approx(w,0) && CL.approx(h,0);

            solver.addEditVar(w);
            solver.addEditVar(h);
            solver.beginEdit();

            solver.suggestValue(w,30);
            solver.suggestValue(h,40);
            solver.endEdit();

            System.out.println("x = " + x.value() + "; y = " + y.value());
            System.out.println("w = " + w.value() + "; h = " + h.value());

            fOkResult = fOkResult && CL.approx(x,10) && CL.approx(y,20) && CL.approx(w,30) && CL.approx(h,40);

            solver.suggestValue(x,50).suggestValue(y,60).endEdit();

            System.out.println("x = " + x.value() + "; y = " + y.value());
            System.out.println("w = " + w.value() + "; h = " + h.value());

            fOkResult = fOkResult && CL.approx(x,50) && CL.approx(y,60) && CL.approx(w,30) && CL.approx(h,40);

            return(fOkResult);
        }
        catch (RequiredFailure err)
        {
            // we want this exception to get thrown
            System.out.println("Success -- got the exception");
            return(true);
        }
    }


    public final static boolean inconsistent3()
            throws InternalError, RequiredFailure
    {
        try
        {
            Variable w = new Variable("w");
            Variable x = new Variable("x");
            Variable y = new Variable("y");
            Variable z = new Variable("z");
            SimplexSolver solver = new SimplexSolver();

            solver.addConstraint(new LinearInequality(w,CL.GEQ,10.0));
            solver.addConstraint(new LinearInequality(x,CL.GEQ,w));
            solver.addConstraint(new LinearInequality(y,CL.GEQ,x));
            solver.addConstraint(new LinearInequality(z,CL.GEQ,y));
            solver.addConstraint(new LinearInequality(z,CL.GEQ,8.0));
            solver.addConstraint(new LinearInequality(z,CL.LEQ, 4.0));

            // no exception, we failed!
            return(false);
        }
        catch (RequiredFailure err)
        {
            // we want this exception to get thrown
            System.out.println("Success -- got the exception");
            return(true);
        }
    }


    public final static boolean addDel(int nCns, int nVars, int nResolves)
            throws InternalError, RequiredFailure, NonlinearExpression, ConstraintNotFound
    {
        Timer timer = new Timer();
        // FIXGJB: from where did .12 come?
        final double ineqProb = 0.12;
        final int maxVars = 3;

        System.out.println("starting timing test. nCns = " + nCns + ", nVars = " + nVars + ", nResolves = " + nResolves);

        timer.Start();
        SimplexSolver solver = new SimplexSolver();

        Variable[] rgpclv = new Variable[nVars];
        for (int i = 0; i < nVars; i++)
        {
            rgpclv[i] = new Variable(i,"x");
            solver.addStay(rgpclv[i]);
        }

        Constraint[] rgpcns = new Constraint[nCns];
        int nvs = 0;
        int k;
        int j;
        double coeff;
        for (j = 0; j < nCns; j++)
        {
            // number of variables in this constraint
            nvs = RandomInRange(1,maxVars);
            LinearExpression expr = new LinearExpression(UniformRandomDiscretized() * 20.0 - 10.0);
            for (k = 0; k < nvs; k++) {
                coeff = UniformRandomDiscretized()*10 - 5;
                int iclv = (int) (UniformRandomDiscretized()*nVars);
                expr.addExpression(rgpclv[iclv].times(coeff));
            }
            if (UniformRandomDiscretized() < ineqProb) {
                rgpcns[j] = new LinearInequality(expr);
            } else {
                rgpcns[j] = new LinearEquation(expr);
            }
        }

        System.out.println("done building data structures");
        System.out.println("time = " + timer.ElapsedTime());
        timer.Start();
        int cExceptions = 0;
        for (j = 0; j < nCns; j++) {
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

        int e1Index = (int) (UniformRandomDiscretized()*nVars);
        int e2Index = (int) (UniformRandomDiscretized()*nVars);

        System.out.println("indices " + e1Index + ", " + e2Index);

        EditConstraint edit1 = new EditConstraint(rgpclv[e1Index],Strength.strong);
        EditConstraint edit2 = new EditConstraint(rgpclv[e2Index],Strength.strong);

        solver.addConstraint(edit1);
        solver.addConstraint(edit2);

        System.out.println("done creating edit constraints -- about to start resolves");
        System.out.println("time = " + timer.ElapsedTime() + "\n");
        timer.Start();

        // FIXGJB start = Timer.now();
        for (int m = 0; m < nResolves; m++)
        {
            solver.resolve(rgpclv[e1Index].value() * 1.001, rgpclv[e2Index].value() * 1.001);
        }

        System.out.println("done resolves -- now removing constraints");
        System.out.println("time = " + timer.ElapsedTime() + "\n");

        solver.removeConstraint(edit1);
        solver.removeConstraint(edit2);

        timer.Start();

        for (j = 0; j < nCns; j++)
        {
            if (rgpcns[j] != null)
            {
                solver.removeConstraint(rgpcns[j]);
            }
        }

        System.out.println("done removing constraints and addDel timing test");
        System.out.println("time = " + timer.ElapsedTime() + "\n");

        timer.Start();

        return true;
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

    public final static void main(String[] args)
       throws InternalError, NonlinearExpression, RequiredFailure, ConstraintNotFound, CassowaryError
    {
        ClTests clt = new ClTests();

        boolean fAllOkResult = true;
        boolean fResult;

        System.out.println("simple1:");
        fResult = simple1(); fAllOkResult &= fResult;
        if (!fResult) System.out.println("Failed!");
        System.out.println("Num vars = " + AbstractVariable.numCreated());

        System.out.println("justStay1:");
        fResult = justStay1(); fAllOkResult &= fResult;
        if (!fResult) System.out.println("Failed!");
        System.out.println("Num vars = " + AbstractVariable.numCreated());

        System.out.println("addDelete1:");
        fResult = addDelete1(); fAllOkResult &= fResult;
        if (!fResult) System.out.println("Failed!");
        System.out.println("Num vars = " + AbstractVariable.numCreated());

        System.out.println("addDelete2:");
        fResult = addDelete2(); fAllOkResult &= fResult;
        if (!fResult) System.out.println("Failed!");
        System.out.println("Num vars = " + AbstractVariable.numCreated());

        System.out.println("casso1:");
        fResult = casso1(); fAllOkResult &= fResult;
        if (!fResult) System.out.println("Failed!");
        System.out.println("Num vars = " + AbstractVariable.numCreated());

        System.out.println("inconsistent1:");
        fResult = inconsistent1(); fAllOkResult &= fResult;
        if (!fResult) System.out.println("Failed!");
        System.out.println("Num vars = " + AbstractVariable.numCreated());

        System.out.println("inconsistent2:");
        fResult = inconsistent2(); fAllOkResult &= fResult;
        if (!fResult) System.out.println("Failed!");
        System.out.println("Num vars = " + AbstractVariable.numCreated());

        System.out.println("inconsistent3:");
        fResult = inconsistent3(); fAllOkResult &= fResult;
        if (!fResult) System.out.println("Failed!");
        System.out.println("Num vars = " + AbstractVariable.numCreated());

        System.out.println("multiedit:");
        fResult = multiedit(); fAllOkResult &= fResult;
        if (!fResult) System.out.println("Failed!");
        System.out.println("Num vars = " + AbstractVariable.numCreated());

        System.out.println("addDel:");

        int cns = 900, vars = 900, resolves = 10000;

        if (args.length > 0)
        {
            cns = Integer.parseInt(args[0]);
        }

        if (args.length > 1)
        {
            vars = Integer.parseInt(args[1]);
        }

        if (args.length > 2)
        {
            resolves = Integer.parseInt(args[2]);
        }

        fResult = addDel(cns,vars,resolves);
        // fResult = addDel(300,300,1000);
        // fResult = addDel(30,30,100);
        // fResult = addDel(10,10,30);
        // fResult = addDel(5,5,10);
        fAllOkResult &= fResult;
        if (!fResult) System.out.println("Failed!");
        System.out.println("Num vars = " + AbstractVariable.numCreated());

    }

    static private Random RND;
}
