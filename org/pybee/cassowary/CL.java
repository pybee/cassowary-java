package org.pybee.cassowary;


public class CL {
    public static boolean approx(double a, double b)
    {
        double epsilon = 1.0e-8;
        if (a == 0.0)
        {
	          return (Math.abs(b) < epsilon);
        }
        else if (b == 0.0)
        {
	          return (Math.abs(a) < epsilon);
        }
        else
        {
	          return (Math.abs(a-b) < Math.abs(a) * epsilon);
        }
    }

    public static boolean approx(Variable clv, double b)
    {
        return approx(clv.value(),b);
    }

    static boolean approx(double a, Variable clv)
    {
        return approx(a,clv.value());
    }
}
