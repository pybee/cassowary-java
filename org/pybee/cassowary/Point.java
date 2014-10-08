// $Id: Point.java,v 1.9 1999/04/20 00:26:35 gjb Exp $
//
// Cassowary Incremental Constraint Solver
// Original Smalltalk Implementation by Alan Borning
// This Java Implementation by Greg J. Badros, <gjb@cs.washington.edu>
// http://www.cs.washington.edu/homes/gjb
// (C) 1998, 1999 Greg J. Badros and Alan Borning
// See ../LICENSE for legal details regarding this software
//
// Point
//

package org.pybee.cassowary;

public class Point
{
  public Point(double x, double y)
    {
      _clv_x = new Variable(x);
      _clv_y = new Variable(y);
    }

  public Point(double x, double y, int a)
    {
      _clv_x = new Variable("x"+a,x);
      _clv_y = new Variable("y"+a,y);
    }

  public Point(Variable clv_x, Variable clv_y)
    { _clv_x = clv_x; _clv_y = clv_y; }

  public Variable X()
    { return _clv_x; }

  public Variable Y()
    { return _clv_y; }

  // use only before adding into the solver
  public void SetXY(double x, double y)
    { _clv_x.set_value(x); _clv_y.set_value(y); }

  public void SetXY(Variable clv_x, Variable clv_y)
    { _clv_x = clv_x; _clv_y = clv_y; }

  public double Xvalue()
    { return X().value(); }

  public double Yvalue()
    { return Y().value(); }

  public String toString()
    {
      return "(" + _clv_x.toString() + ", " + _clv_y.toString() + ")";
    }

  private Variable _clv_x;

  private Variable _clv_y;

}
