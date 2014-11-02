package org.pybee.cassowary;


class SlackVariable extends AbstractVariable
{
    public SlackVariable(String name)
    {
        super(name);
    }

    public SlackVariable() {}

    public SlackVariable(long number, String prefix)
    {
        super(number, prefix);
    }

    public String toString()
    {
        return "[" + name() + ":slack]";
    }

    public boolean isExternal()
    {
        return false;
    }

    public boolean isPivotable()
    {
        return true;
    }

    public boolean isRestricted()
    {
        return true;
    }
}
