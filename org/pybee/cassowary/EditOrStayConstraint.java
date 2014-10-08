package org.pybee.cassowary;


abstract class EditOrStayConstraint extends Constraint
{

  public EditOrStayConstraint(Variable var, Strength strength, double weight)
  {
      super(strength, weight);
      _variable = var;
      _expression = new LinearExpression(_variable, -1.0, _variable.value());
  }

  public EditOrStayConstraint(Variable var, Strength strength)
  {
      this(var,strength,1.0);
  }

  public EditOrStayConstraint(Variable var)
  {
      this(var,Strength.required,1.0);
      _variable = var;
  }

  public Variable variable()
  {
      return _variable;
  }

  public LinearExpression expression()
  {
      return _expression;
  }

  private void setVariable(Variable v)
  {
      _variable = v;
  }

  protected Variable  _variable;

  // cache the expresion
  private LinearExpression _expression;

}
