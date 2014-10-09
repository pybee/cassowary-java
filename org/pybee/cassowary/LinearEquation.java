package org.pybee.cassowary;


public class LinearEquation extends LinearConstraint
{
    public LinearEquation(LinearExpression cle, Strength strength, double weight)
    {
        super(cle, strength, weight);
    }

    public LinearEquation(LinearExpression cle, Strength strength)
    {
        super(cle, strength);
    }

    public LinearEquation(LinearExpression cle)
    {
        super(cle);
    }

    public LinearEquation(AbstractVariable clv, LinearExpression cle, Strength strength, double weight)
    {
        super(cle, strength, weight);
        _expression.addVariable(clv,-1.0);
    }

    public LinearEquation(AbstractVariable clv, LinearExpression cle, Strength strength)
    {
        this(clv, cle, strength, 1.0);
    }

    public LinearEquation(AbstractVariable clv, LinearExpression cle)
    {
        this(clv, cle, Strength.REQUIRED, 1.0);
    }

    public LinearEquation(AbstractVariable clv, double val, Strength strength, double weight)
    {
        super(new LinearExpression(val), strength, weight);
        _expression.addVariable(clv, -1.0);
    }

    public LinearEquation(AbstractVariable clv, double val, Strength strength)
    {
        this(clv, val, strength, 1.0);
    }

    public LinearEquation(AbstractVariable clv, double val)
    {
        this(clv, val, Strength.REQUIRED, 1.0);
    }

    public LinearEquation(LinearExpression cle, AbstractVariable clv, Strength strength, double weight)
    {
        super(((LinearExpression) cle.clone()), strength, weight);
        _expression.addVariable(clv, -1.0);
    }

    public LinearEquation(LinearExpression cle, AbstractVariable clv, Strength strength)
    {
        this(cle, clv, strength, 1.0);
    }


    public LinearEquation(LinearExpression cle, AbstractVariable clv)
    {
        this(cle, clv, Strength.REQUIRED, 1.0);
    }

    public LinearEquation(LinearExpression cle1, LinearExpression cle2, Strength strength, double weight)
    {
        super(((LinearExpression) cle1.clone()), strength, weight);
        _expression.addExpression(cle2, -1.0);
    }

    public LinearEquation(LinearExpression cle1, LinearExpression cle2, Strength strength)
    {
        this(cle1, cle2, strength, 1.0);
    }

    public LinearEquation(LinearExpression cle1, LinearExpression cle2)
    {
        this(cle1, cle2, Strength.REQUIRED, 1.0);
    }

    public String toString()
    {
        return super.toString() + " = 0)";
    }
}
