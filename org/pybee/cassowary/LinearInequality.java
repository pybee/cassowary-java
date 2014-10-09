package org.pybee.cassowary;


public class LinearInequality extends LinearConstraint
{

    public LinearInequality(LinearExpression cle, Strength strength, double weight)
    {
        super(cle, strength, weight);
    }

    public LinearInequality(LinearExpression cle, Strength strength)
    {
        super(cle, strength);
    }

    public LinearInequality(LinearExpression cle)
    {
        super(cle);
    }

    public LinearInequality(Variable clv1, byte op_enum, Variable clv2, Strength strength, double weight)
            throws InternalError
    {
        super(new LinearExpression(clv2),strength,weight);

        if (op_enum == CL.GEQ)
        {
            _expression.multiplyMe(-1.0);
            _expression.addVariable(clv1);
        }
        else if (op_enum == CL.LEQ)
        {
            _expression.addVariable(clv1,-1.0);
        }
        else
        {
            // the operator was invalid
            throw new InternalError("Invalid operator in LinearInequality constructor");
        }
    }

    public LinearInequality(Variable clv1, byte op_enum, Variable clv2, Strength strength)
            throws InternalError
    {
        this(clv1, op_enum, clv2, strength, 1.0);
    }

    public LinearInequality(Variable clv1, byte op_enum, Variable clv2)
            throws InternalError
    {
        this(clv1, op_enum, clv2, Strength.REQUIRED, 1.0);
    }


    public LinearInequality(Variable clv, byte op_enum, double val, Strength strength, double weight)
            throws InternalError
    {
        super(new LinearExpression(val),strength,weight);
        if (op_enum == CL.GEQ)
        {
            _expression.multiplyMe(-1.0);
            _expression.addVariable(clv);
        }
        else if (op_enum == CL.LEQ)
        {
            _expression.addVariable(clv,-1.0);
        }
        else
        {
            // the operator was invalid
            throw new InternalError("Invalid operator in LinearInequality constructor");
        }
    }

    public LinearInequality(Variable clv, byte op_enum, double val, Strength strength)
            throws InternalError
    {
        this(clv, op_enum, val, strength, 1.0);
    }

    public LinearInequality(Variable clv, byte op_enum, double val)
            throws InternalError
    {
        this(clv, op_enum, val, Strength.REQUIRED, 1.0);
    }

    public LinearInequality(LinearExpression cle1, byte op_enum, LinearExpression cle2, Strength strength, double weight)
            throws InternalError
    {
        super(((LinearExpression) cle2.clone()), strength, weight);
        if (op_enum == CL.GEQ)
        {
            _expression.multiplyMe(-1.0);
            _expression.addExpression(cle1);
        }
        else if (op_enum == CL.LEQ)
        {
            _expression.addExpression(cle1,-1.0);
        }
        else
        {
            // the operator was invalid
            throw new InternalError("Invalid operator in LinearInequality constructor");
        }
    }

    public LinearInequality(LinearExpression cle1, byte op_enum, LinearExpression cle2, Strength strength)
            throws InternalError
    {
        this(cle1, op_enum, cle2, strength, 1.0);
    }

    public LinearInequality(LinearExpression cle1, byte op_enum, LinearExpression cle2)
            throws InternalError
    {
        this(cle1, op_enum, cle2, Strength.REQUIRED, 1.0);
    }

    public LinearInequality(AbstractVariable clv, byte op_enum, LinearExpression cle, Strength strength, double weight)
        throws InternalError
    {
        super(((LinearExpression) cle.clone()),strength,weight);
        if (op_enum == CL.GEQ)
        {
            _expression.multiplyMe(-1.0);
            _expression.addVariable(clv);
        }
        else if (op_enum == CL.LEQ)
        {
            _expression.addVariable(clv,-1.0);
        }
        else
        {
            // the operator was invalid
            throw new InternalError("Invalid operator in LinearInequality constructor");
        }
    }

    public LinearInequality(AbstractVariable clv, byte op_enum, LinearExpression cle, Strength strength)
            throws InternalError
    {
        this(clv, op_enum, cle, strength, 1.0);
    }

    public LinearInequality(AbstractVariable clv, byte op_enum, LinearExpression cle)
        throws InternalError
    {
        this(clv, op_enum, cle, Strength.REQUIRED, 1.0);
    }

    public LinearInequality(LinearExpression cle, byte op_enum, AbstractVariable clv, Strength strength, double weight)
            throws InternalError
    {
        super(((LinearExpression) cle.clone()),strength,weight);
        if (op_enum == CL.LEQ)
        {
            _expression.multiplyMe(-1.0);
            _expression.addVariable(clv);
        }
        else if (op_enum == CL.GEQ)
        {
            _expression.addVariable(clv,-1.0);
        }
        else
        {
            // the operator was invalid
            throw new InternalError("Invalid operator in LinearInequality constructor");
        }
    }

    public LinearInequality(LinearExpression cle, byte op_enum, AbstractVariable clv, Strength strength)
        throws InternalError
    {
        this(cle, op_enum, clv, strength, 1.0);
    }

    public LinearInequality(LinearExpression cle, byte op_enum, AbstractVariable clv)
            throws InternalError
    {
        this(cle, op_enum, clv, Strength.REQUIRED, 1.0);
    }

    public final boolean isInequality()
    {
        return true;
    }

    public final String toString()
    {
        return super.toString() + " >= 0 )";
    }
}
