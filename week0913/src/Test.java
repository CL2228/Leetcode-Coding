public class Test {

    public static void getSec(double N, double u) {
        double F = 20000;
        double dmin = 2;
        double us = 30;

        double csTime = Math.max(N * F / us, F / dmin);

        double tmp = Math.max(F / us, F / dmin);
        double p2pTime = Math.max(tmp, (N*F)/(us + N*u));

        System.out.println("cs:" + csTime);
        System.out.println("p2p:" + p2pTime);

    }

    public static void main(String[] args) {
        getSec(10, 2);
    }
}
