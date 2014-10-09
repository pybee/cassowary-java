package org.pybee.cassowary;


abstract class EditOrStayConstraint extends Constraint
{
  protected Variable  _variable;

  // cache the expresion
  private Expression _expression;

  public EditOrStayConstraint(Variable var, Strength strength, double weight)
  {
      super(strength, weight);
      _variable = var;
      _expression = new Expression(_variable, -1.0, _variable.value());
  }

  public EditOrStayConstraint(Variable var, Strength strength)
  {
      this(var, strength, 1.0);
  }

  public EditOrStayConstraint(Variable var)
  {
      this(var, Strength.REQUIRED, 1.0);
      _variable = var;
  }

  public Variable variable()
  {
      return _variable;
  }

  public Expression expression()
  {
      return _expression;
  }

  private void setVariable(Variable v)
  {
      _variable = v;
  }
}
