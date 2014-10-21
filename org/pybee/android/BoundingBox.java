package org.pybee.android;

import org.pybee.cassowary.Variable;
import org.pybee.cassowary.Expression;

public class BoundingBox {
    public enum Attribute {
        LEFT (1),
        RIGHT (2),
        TOP (3),
        BOTTOM (4),
        LEADING (5),
        TRAILING (6),
        WIDTH (7),
        HEIGHT (8),
        CENTER_X (9),
        CENTER_Y (10);
        // BASELINE (11);

        private int _value;

        private Attribute(int value) {
            this._value = value;
        }

        public int value() {
            return _value;
        }

        public static Attribute fromValue(int value)
        {
            switch (value)
            {
                case 1: return LEFT;
                case 2: return RIGHT;
                case 3: return TOP;
                case 4: return BOTTOM;
                case 5: return LEADING;
                case 6: return TRAILING;
                case 7: return WIDTH;
                case 8: return HEIGHT;
                case 9: return CENTER_X;
                case 10: return CENTER_Y;
                // case 11: return BASELINE;
                default: throw new RuntimeException("Unknown enumeration value: " + value);
            }
        }
    }

    public Variable x;
    public Variable y;
    public Variable width;
    public Variable height;

    public BoundingBox() {
        x = new Variable("x", 0.0);
        y = new Variable("y", 0.0);
        width = new Variable("width", 0.0);
        height = new Variable("height", 0.0);
    }

    public String toString() {
        return this.width.value() + "x" + this.height.value() + " @ " + this.x.value() + "," + this.y.value();
    }

    public Expression expression(Attribute attr)
    {
        switch (attr) {
            case LEFT: return new Expression(x);
            case RIGHT: return new Expression(x).plus(new Expression(width));
            case TOP: return new Expression(y);
            case BOTTOM: return new Expression(y).plus(new Expression(height));
            case LEADING: return new Expression(x);
            case TRAILING: return new Expression(x).plus(new Expression(width));
            case WIDTH: return new Expression(width);
            case HEIGHT: return new Expression(height);
            case CENTER_X: return new Expression(x).plus(new Expression(width)).divide(2.0);
            case CENTER_Y: return new Expression(y).plus(new Expression(height)).divide(2.0);
            default: return null;
        }
    }
}