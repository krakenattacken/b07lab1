import java.lang.Math;

public class Polynomial {
	double[] coefficients;

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0;
    }

    public Polynomial(double[] x) {
        coefficients = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            coefficients[i] = x[i];
        }
    }

    public Polynomial add(Polynomial x) {
        int len1 = x.coefficients.length;
        int len2 = coefficients.length;
        int maxLength = Math.max(len1, len2);
        double[] sum = new double[maxLength];
        for (int i = 0; i < maxLength; i++) {
            if (i < len1) {
                sum[i] += x.coefficients[i];
            }
            if (i < len2) {
                sum[i] += coefficients[i];
            }
        }
        Polynomial result = new Polynomial(sum);
        return result;
    }

    public double evaluate(double x) {
        //evaluates the polynomial at x
        double sum = 0;
        for (int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i] * Math.pow(x, i);
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        // determines if this value is a root of the polynomial or not.
        return(evaluate(x) == 0);
    }

}