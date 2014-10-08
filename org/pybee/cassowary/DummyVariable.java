package org.pybee.cassowary;


class DummyVariable extends AbstractVariable
{
    public DummyVariable(String name)
    {
        super(name);
    }

    public DummyVariable() {}

    public DummyVariable(long number, String prefix)
    {
        super(number, prefix);
    }

    public String toString()
    {
        return "[" + name() + ":dummy]";
    }

    public boolean isDummy()
    {
        return true;
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
        return true;
    }

}
