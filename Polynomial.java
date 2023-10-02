import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.lang.Math;
import java.nio.Buffer;
import java.util.Arrays;

public class Polynomial {
	double[] coefficients;
    int[] exponents;

    public Polynomial() {
        coefficients = new double[1];
        exponents = new int[1];
    }

    public Polynomial(double[] coefficients, int[] exponents) { 
        int len = coefficients.length;
        this.coefficients = new double[len];
        this.exponents = new int[len];
        for (int i = 0; i < len; i++) {
            this.coefficients[i] = coefficients[i];
            this.exponents[i] = exponents[i];
        }
    }

    public Polynomial(File filename){ 
        try {
            BufferedReader b = new BufferedReader(new FileReader(filename));
            String line = b.readLine();
            b.close();        

            if (line != null) {
                line = line.replace("-", "-+" );
                String[] terms = line.split("\\+");
                int[] expTemp = new int[terms.length];
                double[] coeffTemp = new double[terms.length];
                for (int i = 0; i < terms.length; i++) {
                    double coeff = 1.0;
                    if (terms[i].contains("x")) {
                        String[] part = terms[i].split("x");
                        int exp = 0;
                        if (part.length == 1) {
                            exp = Integer.parseInt(part[0].trim());
                        }
                        else if (part.length == 2) {
                            coeff = Double.parseDouble(part[0].trim());
                            exp = Integer.parseInt(part[1].trim());
                        }
                        coeffTemp[i] = coeff;
                        expTemp[i] = exp;
                    }
                    else {
                        coeff = Double.parseDouble(terms[i]);
                        expTemp[i] = 0;
                        coeffTemp[i] = coeff;
                    }
                }
                exponents = Arrays.copyOf(expTemp, expTemp.length);
                coefficients = Arrays.copyOf(coeffTemp, coeffTemp.length);
            }
            else {
                coefficients = new double[0];
                exponents = new int[0];
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public Polynomial add(Polynomial x) { 
        
        //combine exponents arrays
        int[] tempExp = new int[x.exponents.length + exponents.length];
        System.arraycopy(x.exponents, 0, tempExp, 0, x.exponents.length);
        System.arraycopy(exponents, 0, tempExp, x.exponents.length, exponents.length);
        Arrays.sort(tempExp);

        int unique = 1;
        for (int i = 1; i < tempExp.length; i++) {
            if (tempExp[i] != tempExp[i - 1]) {
                tempExp[unique] = tempExp[i];
                unique++;
            }
        }
        int[] exp = Arrays.copyOf(tempExp, unique);
        double[] coeff = new double[exp.length];

        int i = 0, j = 0, k =0;
        while (i < exp.length) {
            int temp = exp[i];
            if (j < x.exponents.length && temp == x.exponents[j]) {
                coeff[i] += x.coefficients[j];
                j++;
            }
            if (k < exponents.length && temp == exponents[k]) {
                coeff[i] += coefficients[k];
                k++;
            }
            i++;
        }

        Polynomial result = new Polynomial(coeff, exp);
        return result;
    }

    public double evaluate(double x) {  
        //evaluates the polynomial at x
        double sum = 0;
        for (int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return sum;
    }

    public boolean hasRoot(double x) { 
        // determines if this value is a root of the polynomial or not.
        return(evaluate(x) == 0);
    }

    public Polynomial multiply(Polynomial x) { 
        // returns polynomial from multiplying x and calling object
        Polynomial result = new Polynomial();

        int[] newExp = new int[x.exponents.length];
        double[] newCoeff = new double[x.exponents.length];

        for (int i = 0; i < exponents.length; i++) {
            Polynomial newPoly = new Polynomial();
            for (int j = 0; j<x.exponents.length; j++) {
                newExp[j] = exponents[i] + x.exponents[j];
                newCoeff[j] = coefficients[i] * x.coefficients[j];

                newPoly = new Polynomial(newCoeff, newExp);
                
            }
            result = result.add(newPoly);
        }
        return result; 
    }

    public void saveToFile(String filename) {
        // saves polynomials in text form in the file filename.
        try (BufferedWriter b = new BufferedWriter(new FileWriter(filename))) {
            StringBuilder string = new StringBuilder();
            for (int i = 0; i < coefficients.length; i++) {
                if (i > 0) {
                    if (coefficients[i] >= 0) {
                        string.append("+");
                    }
                }
                if (exponents[i] == 0) {
                    string.append(coefficients[i]);
                }
                else {
                    if (coefficients[i] != 1) {
                        string.append(coefficients[i]);
                    }
                    string.append("x");
                    if (exponents[i] != 1) {
                        string.append(exponents[i]);
                    }
                }
            }
            b.write(string.toString());
            b.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void print(){
        StringBuilder polynomialString = new StringBuilder();

        for (int i = 0; i < coefficients.length; i++) {
            double coeff = coefficients[i];
            int exp = exponents[i];
    
            if (i > 0) {
                if (coeff >= 0) {
                    polynomialString.append(" + ");
                } else {
                    polynomialString.append(" - ");
                    coeff = Math.abs(coeff); 
                }
            }
    
            if (exp == 0) {
                polynomialString.append(coeff);
            } else if (exp == 1) {
                polynomialString.append(coeff).append("x");
            } else {
                polynomialString.append(coeff).append("x^").append(exp);
            }
        }
    
        System.out.println(polynomialString.toString());
    }
}