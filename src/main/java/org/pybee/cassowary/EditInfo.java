package org.pybee.cassowary;


// EditInfo is a privately-used class
// that just wraps a constraint, its positive and negative
// error variables, and its prior edit constant.
// It is used as values in _editVarMap, and replaces
// the parallel vectors of error variables and previous edit
// constants from the smalltalk version of the code.
class EditInfo {
    private AbstractConstraint _cn;
    private SlackVariable _clvEditPlus;
    private SlackVariable _clvEditMinus;
    private double _prevEditConstant;
    private int _i;

    public EditInfo(AbstractConstraint cn, SlackVariable eplus, SlackVariable eminus, double prevEditConstant, int i)
    {
        _cn = cn;
        _clvEditPlus = eplus;
        _clvEditMinus = eminus;
        _prevEditConstant = prevEditConstant;
        _i = i;
    }

    public int index()
    {
        return _i;
    }

    public AbstractConstraint constraint()
    {
        return _cn;
    }

    public SlackVariable clvEditPlus()
    {
        return _clvEditPlus;
    }

    public SlackVariable clvEditMinus()
    {
        return _clvEditMinus;
    }

    public double prevEditConstant()
    {
        return _prevEditConstant;
    }

    public void setPrevEditConstant(double prevEditConstant)
    {
        _prevEditConstant = prevEditConstant;
    }
}
