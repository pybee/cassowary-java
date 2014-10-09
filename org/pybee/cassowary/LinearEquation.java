package org.pybee.cassowary;


public class LinearEquation extends LinearConstraint
{
    public LinearEquation(Expression cle, Strength strength, double weight)
    {
        super(cle, strength, weight);
    }

    public LinearEquation(Expression cle, Strength strength)
    {
        super(cle, strength);
    }

    public LinearEquation(Expression cle)
    {
        super(cle);
    }

    public LinearEquation(AbstractVariable clv, Expression cle, Strength strength, double weight)
    {
        super(cle, strength, weight);
        _expression.addVariable(clv,-1.0);
    }

    public LinearEquation(AbstractVariable clv, Expression cle, Strength strength)
    {
        this(clv, cle, strength, 1.0);
    }

    public LinearEquation(AbstractVariable clv, Expression cle)
    {
        this(clv, cle, Strength.REQUIRED, 1.0);
    }

    public LinearEquation(AbstractVariable clv, double val, Strength strength, double weight)
    {
        super(new Expression(val), strength, weight);
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

    public LinearEquation(Expression cle, AbstractVariable clv, Strength strength, double weight)
    {
        super(((Expression) cle.clone()), strength, weight);
        _expression.addVariable(clv, -1.0);
    }

    public LinearEquation(Expression cle, AbstractVariable clv, Strength strength)
    {
        this(cle, clv, strength, 1.0);
    }


    public LinearEquation(Expression cle, AbstractVariable clv)
    {
        this(cle, clv, Strength.REQUIRED, 1.0);
    }

    public LinearEquation(Expression cle1, Expression cle2, Strength strength, double weight)
    {
        super(((Expression) cle1.clone()), strength, weight);
        _expression.addExpression(cle2, -1.0);
    }

    public LinearEquation(Expression cle1, Expression cle2, Strength strength)
    {
        this(cle1, cle2, strength, 1.0);
    }

    public LinearEquation(Expression cle1, Expression cle2)
    {
        this(cle1, cle2, Strength.REQUIRED, 1.0);
    }

    public String toString()
    {
        return super.toString() + " = 0)";
    }
}
