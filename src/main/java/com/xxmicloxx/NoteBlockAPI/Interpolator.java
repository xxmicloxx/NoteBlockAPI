package com.xxmicloxx.NoteBlockAPI;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ml
 * Date: 07.12.13
 * Time: 11:51
 */
public class Interpolator {
    public static double[] interpolateLinear(double[] x, double[] y, double[] xi) throws IllegalArgumentException {
        if (x.length != y.length) {
            throw new IllegalArgumentException("X and Y must be the same length");
        }
        if (x.length == 1) {
            throw new IllegalArgumentException("X must contain more than one value");
        }
        double[] dx = new double[x.length - 1];
        double[] dy = new double[x.length - 1];
        double[] slope = new double[x.length - 1];
        double[] intercept = new double[x.length - 1];

        // Calculate the line equation (i.e. slope and intercept) between each point
        for (int i = 0; i < x.length - 1; i++) {
            dx[i] = x[i + 1] - x[i];
            if (dx[i] == 0) {
                throw new IllegalArgumentException("X must be montotonic. A duplicate " + "x-value was found");
            }
            if (dx[i] < 0) {
                throw new IllegalArgumentException("X must be sorted");
            }
            dy[i] = y[i + 1] - y[i];
            slope[i] = dy[i] / dx[i];
            intercept[i] = y[i] - x[i] * slope[i];
        }

        // Perform the interpolation here
        double[] yi = new double[xi.length];
        for (int i = 0; i < xi.length; i++) {
            if ((xi[i] > x[x.length - 1]) || (xi[i] < x[0])) {
                yi[i] = Double.NaN;
            }
            else {
                int loc = Arrays.binarySearch(x, xi[i]);
                if (loc < -1) {
                    loc = -loc - 2;
                    yi[i] = slope[loc] * xi[i] + intercept[loc];
                }
                else {
                    yi[i] = y[loc];
                }
            }
        }

        return yi;
    }

    public static double interpolateLinear(double[] xy, double xx) {
        if (xy.length % 2 != 0) {
            throw new IllegalArgumentException("XY must be divisible by two.");
        }
        double[] x = new double[xy.length/2];
        double[] y = new double[x.length];
        for (int i = 0; i < xy.length; i++) {
            if (i % 2 == 0) {
                x[i/2] = xy[i];
            } else {
                y[i/2] = xy[i];
            }
        }
        return interpolateLinear(x, y, new double[] {xx})[0];
    }
}
