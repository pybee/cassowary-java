// $Id: SymbolicWeight.java,v 1.11 1999/04/20 00:26:41 gjb Exp $
//
// Cassowary Incremental Constraint Solver
// Original Smalltalk Implementation by Alan Borning
// This Java Implementation by Greg J. Badros, <gjb@cs.washington.edu>
// http://www.cs.washington.edu/homes/gjb
// (C) 1998, 1999 Greg J. Badros and Alan Borning
// See ../LICENSE for legal details regarding this software
//
// SymbolicWeight

package org.pybee.cassowary;

import java.util.*;

public class SymbolicWeight
{

  public SymbolicWeight(int cLevels)
    {
      _values = new double[cLevels];
      // FIXGJB: ok to assume these get initialized to 0?
//       for (int i = 0; i < cLevels; i++) {
// 	_values[i] = 0;
//       }
    }

  public SymbolicWeight(double w1, double w2, double w3)
    {
      _values = new double[3];
      _values[0] = w1;
      _values[1] = w2;
      _values[2] = w3;
    }

  public SymbolicWeight(double[] weights)
    {
      final int cLevels = weights.length;
      _values = new double[cLevels];
      for (int i = 0; i < cLevels; i++) {
	_values[i] = weights[i];
      }
    }

  public static final SymbolicWeight clsZero = new SymbolicWeight(0.0, 0.0, 0.0);

  public Object clone()
    {
      return new SymbolicWeight(_values);
    }

  public SymbolicWeight times(double n)
    {
      SymbolicWeight clsw = (SymbolicWeight) clone();
      for (int i = 0; i < _values.length; i++) {
        clsw._values[i] *= n;
      }
      return clsw;
    }

  public SymbolicWeight divideBy(double n)
    {
      // assert(n != 0);
      SymbolicWeight clsw = (SymbolicWeight) clone();
      for (int i = 0; i < _values.length; i++) {
        clsw._values[i] /= n;
      }
      return clsw;
    }

  public SymbolicWeight add(SymbolicWeight cl)
    {
      // assert(cl.cLevels() == cLevels());

      SymbolicWeight clsw = (SymbolicWeight) clone();
      for (int i = 0; i < _values.length; i++) {
        clsw._values[i] += cl._values[i];
      }
      return clsw;
    }

  public SymbolicWeight subtract(SymbolicWeight cl)
    {
      // assert(cl.cLevels() == cLevels());

      SymbolicWeight clsw = (SymbolicWeight) clone();
      for (int i = 0; i < _values.length; i++) {
        clsw._values[i] -= cl._values[i];
      }
      return clsw;
    }

  public boolean lessThan(SymbolicWeight cl)
    {
      // assert cl.cLevels() == cLevels()
      for (int i = 0; i < _values.length; i++) {
	if (_values[i] < cl._values[i])
	  return true;
	else if (_values[i] > cl._values[i])
	  return false;
      }
      return false; // they are equal
    }

  public boolean lessThanOrEqual(SymbolicWeight cl)
    {
      // assert cl.cLevels() == cLevels()
      for (int i = 0; i < _values.length; i++) {
	if (_values[i] < cl._values[i])
	  return true;
	else if (_values[i] > cl._values[i])
	  return false;
      }
      return true; // they are equal
    }

  public
    boolean equal(SymbolicWeight cl)
    {
      for (int i = 0; i < _values.length; i++) {
	if (_values[i] != cl._values[i])
	  return false;
      }
      return true; // they are equal
    }

  public boolean greaterThan(SymbolicWeight cl)
    {
      return !this.lessThanOrEqual(cl);
    }

  public boolean greaterThanOrEqual(SymbolicWeight cl)
    {
      return !this.lessThan(cl);
    }

  public boolean isNegative()
    {
      return this.lessThan(clsZero);
    }

  public double asDouble()
    {
      SymbolicWeight clsw = (SymbolicWeight) clone();
      double sum  =  0;
      double factor = 1;
      double multiplier = 1000;
      for (int i = _values.length - 1; i >= 0; i--)
        {
	sum += _values[i] * factor;
	factor *= multiplier;
	}
      return sum;
    }

  public String toString()
    {
      StringBuffer bstr = new StringBuffer("[");
      for (int i = 0; i < _values.length-1; i++) {
	bstr.append(_values[i]);
	bstr.append(",");
      }
      bstr.append(_values[_values.length-1]);
      bstr.append("]");
      return bstr.toString();
    }

  public int cLevels()
    { return _values.length; }

  private double[] _values;

}
