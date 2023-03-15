package de.sanj0.alicelang;

import java.util.Arrays;

// locates variables and functions
public class AlicePath {
    public static String SEPARATOR = "::";
    private final String[] segments;

    private String bufToString = null;
    public AlicePath(final String[] segments) {
        this.segments = segments;
    }

    public AlicePath(final String s) {
        segments = s.split(SEPARATOR);
        bufToString = s;
    }

    // default, empty path
    public AlicePath() {
        segments = new String[0];
    }

    @Override
    public String toString() {
        return bufToString == null ? bufToString = String.join(SEPARATOR, segments) : bufToString;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AlicePath)) return false;
        AlicePath alicePath = (AlicePath) o;
        return Arrays.equals(segments, alicePath.segments);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(segments);
    }
}
