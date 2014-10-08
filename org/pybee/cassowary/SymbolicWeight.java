package org.pybee.cassowary;


public class SymbolicWeight
{

    public SymbolicWeight(int cLevels)
    {
        _values = new double[cLevels];
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
        for (int i = 0; i < cLevels; i++)
        {
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
        for (int i = 0; i < _values.length; i++)
        {
            clsw._values[i] *= n;
        }
        return clsw;
    }

    public SymbolicWeight divideBy(double n)
    {
        // assert(n != 0);
        SymbolicWeight clsw = (SymbolicWeight) clone();
        for (int i = 0; i < _values.length; i++)
        {
            clsw._values[i] /= n;
        }
        return clsw;
    }

    public SymbolicWeight add(SymbolicWeight cl)
    {
        // assert(cl.cLevels() == cLevels());

        SymbolicWeight clsw = (SymbolicWeight) clone();
        for (int i = 0; i < _values.length; i++)
        {
            clsw._values[i] += cl._values[i];
        }
        return clsw;
    }

    public SymbolicWeight subtract(SymbolicWeight cl)
    {
        // assert(cl.cLevels() == cLevels());

        SymbolicWeight clsw = (SymbolicWeight) clone();
        for (int i = 0; i < _values.length; i++)
        {
            clsw._values[i] -= cl._values[i];
        }
        return clsw;
    }

    public boolean lessThan(SymbolicWeight cl)
    {
        // assert cl.cLevels() == cLevels()
        for (int i = 0; i < _values.length; i++)
        {
            if (_values[i] < cl._values[i])
            {
                return true;
            }
            else if (_values[i] > cl._values[i])
            {
                return false;
            }
        }
        return false; // they are equal
    }

    public boolean lessThanOrEqual(SymbolicWeight cl)
    {
        // assert cl.cLevels() == cLevels()
        for (int i = 0; i < _values.length; i++)
        {
            if (_values[i] < cl._values[i])
            {
                return true;
            }
            else if (_values[i] > cl._values[i])
            {
                return false;
            }
        }
        return true; // they are equal
    }

    public boolean equal(SymbolicWeight cl)
    {
        for (int i = 0; i < _values.length; i++)
        {
            if (_values[i] != cl._values[i])
            {
                return false;
            }
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
    {
        return _values.length;
    }

    private double[] _values;
}
