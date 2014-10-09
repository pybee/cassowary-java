package org.pybee.cassowary;


public enum Strength
{
    REQUIRED (1001001000),
    STRONG (1000000),
    MEDIUM (1000),
    WEAK (1);

    public int value;

    private Strength(int value) {
        this.value = value;
    }

    public String toString(Strength strength) {
        switch (strength) {
            case REQUIRED:
                return "Required";
            case STRONG:
                return "Strong";
            case MEDIUM:
                return "Medium";
            case WEAK:
                return "Weak";
            default:
                return "Unknown (" + value + ")";
        }
    }
}
