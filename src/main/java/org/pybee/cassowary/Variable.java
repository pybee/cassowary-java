package org.pybee.cassowary;

import java.util.Hashtable;


public class Variable extends AbstractVariable
{
    private static Hashtable _ourVarMap;

    private double _value;

    private Object _attachedObject;

    private VariableObserver _observer;

    public Variable(String name, double value)
    {
        super(name);
        _value = value;
        if (_ourVarMap != null)
        {
            _ourVarMap.put(name,this);
        }
    }

    public Variable(String name)
    {
        super(name);
        _value = 0.0;
        if (_ourVarMap != null)
        {
            _ourVarMap.put(name,this);
        }
    }

    public Variable(double value)
    {
        _value = value;
    }

    public Variable()
    {
        _value = 0.0;
    }


    public Variable(long number, String prefix, double value)
    {
        super(number,prefix);
        _value = value;
    }

    public Variable(long number, String prefix)
    {
        super(number,prefix);
        _value = 0.0;
    }

    public boolean isDummy()
    {
        return false;
    }

    public boolean isExternal()
    {
        return true;
    }

    public boolean isPivotable()
    {
        return false;
    }

    public boolean isRestricted()
    {
        return false;
    }

    public String toString()
    {
        return "[" + getName() + ":" + _value + "]";
    }

    // change the value held -- should *not* use this if the variable is
    // in a solver -- instead use addEditVar() and suggestValue() interface
    public final double getValue()
    {
        return _value;
    }

    public final void setValue(double value)
    {
        _value = value;
    }

    // permit overriding in subclasses in case something needs to be
    // done when the value is changed by the solver
    // may be called when the value hasn't actually changed -- just
    // means the solver is setting the external variable
    public void changeValue(double value)
    {
        _value = value;
        if (_observer != null) {
            _observer.onVariableChanged(this);
        }
    }

    public void setAttachedObject(Object o)
    {
        _attachedObject = o;
    }

    public Object getAttachedObject()
    {
        return _attachedObject;
    }

    public static void setVarMap(Hashtable map)
    {
        _ourVarMap = map;
    }

    public static Hashtable getVarMap()
    {
        return _ourVarMap;
    }

    public Expression times(double val)
    {
        return new Expression(this, val);
    }

    public Expression times(Expression var)
            throws NonlinearExpression
    {
        return var.times(new Expression(this));
    }

    public Expression plus(double val)
    {
        return new Expression(this).plus(new Expression(val));
    }

    public VariableObserver getObserver() {
        return _observer;
    }

    public void setObserver(VariableObserver variableObserver) {
        this._observer = variableObserver;
    }
}
