package Medium.QuadraticEquation;

public class Main {
    public static void main(String[] args) {
        solution(3, 4, 1);
//        System.out.println(3.4641016151377544);
    }

    public static void solution(int a, int b, int c) {
        double discriminant = Math.pow(b, 2) - (4 * a * c);

        double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        if(discriminant > 0) {
            double root2 = (-b - Math.sqrt(discriminant)) / (2*a);
            System.out.println("The equation has two distinct real roots:  x=" + root1 + " x=" + root2);
        } else if (discriminant == 0) {
            System.out.println("The equation has one real root: x=" + root1);
        } else if(discriminant < 0) {
            System.out.println("The equation has no real solution");
        }
    }
}
