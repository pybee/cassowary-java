package org.pybee.cassowary;

import java.lang.*;


public abstract class AbstractVariable
{
    private static int iVariableNumber;

    private String _name;

    public AbstractVariable(String name)
    {
        _name = name;
        iVariableNumber++;
    }

    public AbstractVariable()
    {
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

}
