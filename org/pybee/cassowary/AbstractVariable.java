package org.pybee.cassowary;

import java.lang.*;


public abstract class AbstractVariable
{
    public AbstractVariable(String name)
    {
        //hash_code = iVariableNumber;
        _name = name;
        iVariableNumber++;
    }

    public AbstractVariable()
    {
        //hash_code = iVariableNumber;
        _name = "v" + iVariableNumber;
        iVariableNumber++;
    }

    public AbstractVariable(long varnumber, String prefix)
    {
        //hash_code = iVariableNumber;
        _name = prefix + varnumber;
        iVariableNumber++;
    }

    public String name()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public boolean isDummy()
    {
        return false;
    }

    public abstract boolean isExternal();

    public abstract boolean isPivotable();

    public abstract boolean isRestricted();

    public abstract String toString();

    public static int numCreated()
    {
        return iVariableNumber;
    }

    private String _name;

    private static int iVariableNumber;
}
