package org.pybee.cassowary;


// EditInfo is a privately-used class
// that just wraps a constraint, its positive and negative
// error variables, and its prior edit constant.
// It is used as values in _editVarMap, and replaces
// the parallel vectors of error variables and previous edit
// constants from the smalltalk version of the code.
class EditInfo {
    private AbstractConstraint cn;
    private SlackVariable clvEditPlus;
    private SlackVariable clvEditMinus;
    private double prevEditConstant;
    private int i;

    public EditInfo(AbstractConstraint cn_, SlackVariable eplus_, SlackVariable eminus_, double prevEditConstant_, int i_)
    {
        cn = cn_;
        clvEditPlus = eplus_;
        clvEditMinus = eminus_;
        prevEditConstant = prevEditConstant_;
        i=i_;
    }

    public int Index()
    {
        return i;
    }

    public AbstractConstraint Constraint()
    {
        return cn;
    }

    public SlackVariable ClvEditPlus()
    {
        return clvEditPlus;
    }

    public SlackVariable ClvEditMinus()
    {
        return clvEditMinus;
    }

    public double PrevEditConstant()
    {
        return prevEditConstant;
    }

    public void SetPrevEditConstant(double prevEditConstant_)
    {
        prevEditConstant = prevEditConstant_;
    }
}
