package org.pybee.android;

import java.util.HashMap;
import java.util.HashSet;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import org.pybee.cassowary.*;


public class CassowaryLayout extends ViewGroup {

    public BoundingBox bounding_box;
    HashMap<View, BoundingBox> children;
    HashSet<Constraint> constraints;

    public CassowaryLayout(Context context) {
        super(context);

        bounding_box = new BoundingBox();
        children = new HashMap<View, BoundingBox>();
        constraints = new HashSet<Constraint>();

    }

    /**
     * Any layout manager that doesn't scroll will want this.
     */
    // @Override
    // public boolean shouldDelayChildPressedState() {
    //     return false;
    // }

    /**
     * Ask all children to measure themselves and compute the measurement of this
     * layout based on the children.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        // System.out.println("onMeasure: " + count + " children, width = " + widthMeasureSpec + ", height = " + heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // System.out.println("  modes: " + MeasureSpec.EXACTLY + " " + MeasureSpec.AT_MOST);
        // System.out.println("  width: " + widthSize + " mode " + widthMode);
        // System.out.println("  height: " + heightSize + " mode " + heightMode);

        // System.out.println("Set layout's bounding box");
        bounding_box.x.set_value(0);
        bounding_box.y.set_value(0);
        bounding_box.width.set_value(widthSize);
        bounding_box.height.set_value(heightSize);

        // System.out.println("Create solver");
        SimplexSolver solver = new SimplexSolver();

        // System.out.println("Add stays for origin");
        solver.addStay(bounding_box.x, Strength.REQUIRED);
        solver.addStay(bounding_box.y, Strength.REQUIRED);

        if (widthMode == MeasureSpec.EXACTLY)
        {
            // System.out.println("Add stay for overall width");
            solver.addStay(bounding_box.width, Strength.REQUIRED);
        }
        else
        {
            // System.out.println("Add overall width constraint");
            solver.addConstraint(
                new Constraint(
                    new Expression(bounding_box.width),
                    Constraint.Operator.LEQ,
                    new Expression((double) widthSize)
                )
            );
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            // System.out.println("Add stay for overall height");
            solver.addStay(bounding_box.height, Strength.REQUIRED);
        }
        else
        {
            // System.out.println("Add overall width constraint");
            solver.addConstraint(
                new Constraint(
                    new Expression(bounding_box.height),
                    Constraint.Operator.LEQ,
                    new Expression((double) heightSize)
                )
            );
        }

        // System.out.println("Add layout constraints");
        for (Constraint c: constraints) {
            // System.out.println("   Add " + c);
            solver.addConstraint(c);
        }

        int total_height = 50;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                // System.out.println("  Check child " + i + " - " + child);

                measureChild(child, MeasureSpec.makeMeasureSpec(widthSize - 100, MeasureSpec.AT_MOST), heightMeasureSpec);

                // System.out.println("    measured: " + child.getMeasuredWidth() + " x " + child.getMeasuredHeight());
                total_height = total_height + child.getMeasuredHeight() + 50;

                // System.out.println("    add width/height constraints");
                solver.addConstraint(
                    new Constraint(
                        new Expression(children.get(child).width),
                        Constraint.Operator.GEQ,
                        new Expression((double) child.getMeasuredWidth())
                    )
                );
                solver.addConstraint(
                    new Constraint(
                        new Expression(children.get(child).height),
                        Constraint.Operator.GEQ,
                        new Expression((double) child.getMeasuredHeight())
                    )
                );
            }
        }

        // System.out.println("Overall layout:");
        // for (View v: children.keySet())
        // {
        //     System.out.println("  Child: " + v + " " + children.get(v));
        // }
        // System.out.println("  Layout: " + bounding_box);
        // Report our final dimensions.
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Math.max(heightSize, total_height), MeasureSpec.EXACTLY));
    }

    /**
     * Position all children within this layout.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();

        // System.out.println("onLayout: " + count + " children, L" + left + ", T" + top + ", R" + right + ", B" + bottom);

        // System.out.println("  padding: L" + getPaddingLeft() + ", T" + getPaddingTop() + ", R" + getPaddingRight() + ", B" + getPaddingBottom());
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                // System.out.println("  layout child " + i + " - " + child);

                BoundingBox box = children.get(child);
                child.layout(
                    (int) box.x.value(),
                    (int) box.y.value(),
                    (int) (box.x.value() + box.width.value()),
                    (int) (box.y.value() + box.height.value()));
            }
        }
    }

    public void addView(View child) {
        super.addView(child);
        // System.out.println("ADD CHILD " +  child);
        children.put(child, new BoundingBox());
    }

    public Constraint makeConstraint(View child1, int attr1, int op, View child2, int attr2, double multiplier, double constant) {
        return makeConstraint(
            child1, BoundingBox.Attribute.fromValue(attr1),
            Constraint.Operator.fromValue(op),
            child2, BoundingBox.Attribute.fromValue(attr2),
            multiplier, constant
        );
    }

    public Constraint makeConstraint(View child1, BoundingBox.Attribute attr1, Constraint.Operator op, View child2, BoundingBox.Attribute attr2, double multiplier, double constant) {
        System.out.println("Add Constraint between " + child1 + " " + child2);
        System.out.println("Available children: " + children);
        BoundingBox box1;

        if (child1 == this)
        {
            box1 = bounding_box;
        }
        else
        {
            box1 = children.get(child1);
        }
        Expression expr1 = box1.expression(attr1);
        System.out.println("Expression 1: " + expr1);

        BoundingBox box2;
        if (child2 == this)
        {
            box2 = bounding_box;
        }
        else
        {
            box2 = children.get(child2);
        }
        Expression expr2 = box2.expression(attr2);

        System.out.println("Expression 2a: " + expr1);
        expr2 = expr2.times(multiplier).plus(new Expression(constant));
        System.out.println("Expression 2b: " + expr1);
        expr2 = expr2.plus(new Expression(constant));
        System.out.println("Expression 2: " + expr1);
        return new Constraint(expr1, op, expr2);
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }
}
