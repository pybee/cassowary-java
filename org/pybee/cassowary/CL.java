// $Id: CL.java,v 1.14 1999/04/20 00:26:24 gjb Exp $
//
// Cassowary Incremental Constraint Solver
// Original Smalltalk Implementation by Alan Borning
// This Java Implementation by Greg J. Badros, <gjb@cs.washington.edu>
// http://www.cs.washington.edu/homes/gjb
// (C) 1998, 1999 Greg J. Badros and Alan Borning
// See ../LICENSE for legal details regarding this software
//
// CL.java
// The enumerations from LinearInequality,
// and `global' functions that we want easy to access

package org.pybee.cassowary;

public class CL {
  protected final static boolean fDebugOn = false;
  protected final static boolean fTraceOn = false;
  protected final static boolean fGC = false;

  protected static void debugprint(String s)
  { System.err.println(s); }

  protected static void traceprint(String s)
  { System.err.println(s); }

  protected static void fnenterprint(String s)
  { System.err.println("* " + s); }

  protected static void fnexitprint(String s)
  { System.err.println("- " + s); }



  public static final byte GEQ = 1;
  public static final byte LEQ = 2;

  public static LinearExpression Plus(LinearExpression e1, LinearExpression e2)
    { return e1.plus(e2); }

  public static LinearExpression Plus(LinearExpression e1, double e2)
    { return e1.plus(new LinearExpression(e2)); }

  public static LinearExpression Plus(double e1, LinearExpression e2)
    { return (new LinearExpression(e1)).plus(e2); }

  public static LinearExpression Plus(Variable e1, LinearExpression e2)
    { return (new LinearExpression(e1)).plus(e2); }

  public static LinearExpression Plus(LinearExpression e1, Variable e2)
    { return e1.plus(new LinearExpression(e2)); }

  public static LinearExpression Plus(Variable e1, double e2)
    { return (new LinearExpression(e1)).plus(new LinearExpression(e2)); }

  public static LinearExpression Plus(double e1, Variable e2)
    { return (new LinearExpression(e1)).plus(new LinearExpression(e2)); }


  public static LinearExpression Minus(LinearExpression e1, LinearExpression e2)
    { return e1.minus(e2); }

  public static LinearExpression Minus(double e1, LinearExpression e2)
    { return (new LinearExpression(e1)).minus(e2); }

  public static LinearExpression Minus(LinearExpression e1, double e2)
    { return e1.minus(new LinearExpression(e2)); }

  public static LinearExpression Times(LinearExpression e1, LinearExpression e2)
    throws NonlinearExpression
    { return e1.times(e2); }

  public static LinearExpression Times(LinearExpression e1, Variable e2)
    throws NonlinearExpression
    { return e1.times(new LinearExpression(e2)); }

  public static LinearExpression Times(Variable e1, LinearExpression e2)
    throws NonlinearExpression
    { return (new LinearExpression(e1)).times(e2); }

  public static LinearExpression Times(LinearExpression e1, double e2)
    throws NonlinearExpression
    { return e1.times(new LinearExpression(e2)); }

  public static LinearExpression Times(double e1, LinearExpression e2)
    throws NonlinearExpression
    { return (new LinearExpression(e1)).times(e2); }

  public static LinearExpression Times(double n, Variable clv)
    throws NonlinearExpression
    { return (new LinearExpression(clv,n)); }

  public static LinearExpression Times( Variable clv, double n)
    throws NonlinearExpression
    { return (new LinearExpression(clv,n)); }

  public static LinearExpression Divide(LinearExpression e1, LinearExpression e2)
    throws NonlinearExpression
    { return e1.divide(e2); }

  public static boolean approx(double a, double b)
    {
      double epsilon = 1.0e-8;
      if (a == 0.0) {
	return (Math.abs(b) < epsilon);
      } else if (b == 0.0) {
	return (Math.abs(a) < epsilon);
      } else {
	return (Math.abs(a-b) < Math.abs(a) * epsilon);
      }
    }

  public static boolean approx(Variable clv, double b)
    {
      return approx(clv.value(),b);
    }

  static boolean approx(double a, Variable clv)
    {
      return approx(a,clv.value());
    }
}
