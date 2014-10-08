package org.pybee.cassowary;


public class Strength
{
    public Strength(String name, SymbolicWeight symbolicWeight)
    {
        _name = name;
        _symbolicWeight = symbolicWeight;
    }

    public Strength(String name, double w1, double w2, double w3)
    {
        _name = name;
        _symbolicWeight = new SymbolicWeight(w1, w2, w3);
    }

    public boolean isRequired()
    {
        return (this == required);
    }

    public String toString()
    {
        return name () + (!isRequired()? (":" + symbolicWeight()) : "");
    }

    public SymbolicWeight symbolicWeight()
    {
        return _symbolicWeight;
    }

    public String name()
    {
        return _name;
    }

    public void set_name(String name)
    {
        _name = name;
    }

    public void set_symbolicWeight(SymbolicWeight symbolicWeight)
    {
        _symbolicWeight = symbolicWeight;
    }

    public static final Strength required = new Strength("<Required>", 1000, 1000, 1000);

    public static final Strength strong = new Strength("strong", 1.0, 0.0, 0.0);

    public static final Strength medium = new Strength("medium", 0.0, 1.0, 0.0);

    public static final Strength weak = new Strength("weak", 0.0, 0.0, 1.0);

    private String _name;

    private SymbolicWeight _symbolicWeight;

}
