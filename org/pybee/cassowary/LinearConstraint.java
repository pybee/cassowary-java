package org.pybee.cassowary;


class LinearConstraint extends Constraint
{
    protected Expression _expression;

    public LinearConstraint(Expression cle, Strength strength, double weight)
    {
        super(strength, weight);
        _expression = cle;
    }

    public LinearConstraint(Expression cle, Strength strength)
    {
        super(strength, 1.0);
        _expression = cle;
    }

    public LinearConstraint(Expression cle)
    {
        super(Strength.REQUIRED, 1.0);
        _expression = cle;
    }

    public Expression expression()
    {
        return _expression;
    }

    protected void setExpression(Expression expr)
    {
        _expression = expr;
    }
}
