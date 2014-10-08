package org.pybee.cassowary;


class ObjectiveVariable extends AbstractVariable
{
    public ObjectiveVariable(String name)
    {
        super(name);
    }

    public ObjectiveVariable(long number, String prefix)
    {
        super(number,prefix);
    }

    public String toString()
    {
        return "[" + name() + ":obj]";
    }

    public boolean isExternal()
    {
        return false;
    }

    public boolean isPivotable()
    {
        return false;
    }

    public boolean isRestricted()
    {
        return false;
    }
}
