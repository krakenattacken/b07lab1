import java.io.File;
import java.io.IOError;
import java.io.IOException;

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,5};
        int [] e1 = {0,3};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {-2,-9};
        int [] e2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
        System.out.println("1 is a root of s");
        else
        System.out.println("1 is not a root of s");



        double[] c3 = {1, 1};
        int [] e3 = {0, 1};
        double [] c4 = {2, 1};
        Polynomial p3 = new Polynomial(c3, e3);
        Polynomial p4 = new Polynomial(c4, e3);
        Polynomial m = p3.multiply(p4);
        System.out.println("******");
        p3.print();
        p4.print();
        m.print();
        m.saveToFile("x");
        File file = new File("x");
        Polynomial k = new Polynomial(file);
        k.print();
    }
}