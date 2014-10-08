package org.pybee.cassowary;


public class EditConstraint extends EditOrStayConstraint
{

  public EditConstraint(Variable clv, Strength strength, double weight)
  {
      super(clv, strength, weight);
  }

  public EditConstraint(Variable clv, Strength strength)
  {
      super(clv, strength);
  }

  public EditConstraint(Variable clv)
  {
      super(clv);
  }

  public boolean isEditConstraint()
  {
      return true;
  }

  public String toString()
  {
      return "edit" + super.toString();
  }

}
