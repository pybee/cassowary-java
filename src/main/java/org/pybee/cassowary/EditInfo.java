package org.pybee.cassowary;


// EditInfo is a privately-used class
// that just wraps a constraint, its positive and negative
// error variables, and its prior edit constant.
// It is used as values in _editVarMap, and replaces
// the parallel vectors of error variables and previous edit
// constants from the smalltalk version of the code.
class EditInfo {
    private AbstractConstraint _cn;
    private AbstractVariable _clvEditPlus;
    private AbstractVariable _clvEditMinus;
    private double _prevEditConstant;
    private int _i;

    public EditInfo(AbstractConstraint cn, AbstractVariable eplus, AbstractVariable eminus, double prevEditConstant, int i)
    {
        _cn = cn;
        _clvEditPlus = eplus;
        _clvEditMinus = eminus;
        _prevEditConstant = prevEditConstant;
        _i = i;
    }

    public int getIndex()
    {
        return _i;
    }

    public AbstractConstraint getConstraint()
    {
        return _cn;
    }

    public AbstractVariable getClvEditPlus()
    {
        return _clvEditPlus;
    }

    public AbstractVariable getClvEditMinus()
    {
        return _clvEditMinus;
    }

    public double getPrevEditConstant()
    {
        return _prevEditConstant;
    }

    public void setPrevEditConstant(double prevEditConstant)
    {
        _prevEditConstant = prevEditConstant;
    }
}
