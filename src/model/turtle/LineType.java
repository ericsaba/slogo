package model.turtle;

import main.Constants;


public enum LineType {
    SOLID(Constants.SOLID_LINE, Constants.ZERO),
    DASHED(Constants.DASHED_LINE, Constants.ONE),
    DOTTED(Constants.DOTTED_LINE, Constants.TWO);

    private String path;
    private int index;

    private LineType(String aPath, int ind) {
        path = aPath;
        index = ind;
    }

    @Override
    public String toString() {
        return path;
    }

    public static LineType getType(String test) {
        return valueOf(test.toUpperCase());
    }

    public static LineType getType(int test) {
        for (LineType l : LineType.values()) {
            if (l.index == test)
                return l;
        }
        throw new IllegalArgumentException();
    }
    public int getIndex() {
    	return index;
    }
}
