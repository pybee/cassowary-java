package org.pybee.cassowary;


class LinearConstraint extends Constraint
{

    public LinearConstraint(LinearExpression cle, Strength strength, double weight)
    {
        super(strength, weight);
        _expression = cle;
    }

    public LinearConstraint(LinearExpression cle, Strength strength)
    {
        super(strength, 1.0);
        _expression = cle;
    }

    public LinearConstraint(LinearExpression cle)
    {
        super(Strength.required, 1.0);
        _expression = cle;
    }

    public LinearExpression expression()
    {
        return _expression;
    }

    protected void setExpression(LinearExpression expr)
    {
        _expression = expr;
    }

    protected LinearExpression _expression;
}
