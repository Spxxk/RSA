import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA {
    private static BigInteger one     = new BigInteger("1");
    private static BigInteger zero    = new BigInteger("0");
    private static BigInteger p;
    private static BigInteger q;
    private static BigInteger n;
    private static BigInteger phi;
    private static BigInteger e;
    private static BigInteger d;
    private static BigInteger m;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rnd = new Random();

        System.out.print("Enter a prime number for p: ");
        p = scanner.nextBigInteger();
        System.out.print("Enter a prime number for q: ");
        q = scanner.nextBigInteger();
        System.out.print("Enter a number for m (plaintext): ");
        m = scanner.nextBigInteger();

        n = p.multiply(q);
        phi = p.subtract(one).multiply(q.subtract(one));

        System.out.print("Do you want to pick e? (y/n): ");
        String response = scanner.next().toLowerCase();

        if (response.equals("y")) {
            System.out.print("Enter a number for e: ");
            e = scanner.nextBigInteger();
        } else {
            do {
                e = new BigInteger(phi.bitLength(), rnd);
            } while (e.compareTo(one) <= 0 || e.compareTo(phi) >= 0 || !euclideanAlgorithm(e, phi).equals(one));
        }

        d = e.modInverse(phi);

        System.out.println("Public Key (pk): " + e + " " + n);
        System.out.println("Private Key (sk): " + d + " " + n);


        BigInteger c = m.modPow(e, n);
        System.out.println("Encrypted message: " + c);

        BigInteger decryptedM = c.modPow(d, n);
        System.out.println("Decrypted message: " + decryptedM);
    }

    public static BigInteger euclideanAlgorithm(BigInteger a, BigInteger b) {
        if (b.equals(zero)) {
            return a;
        } else {
            return euclideanAlgorithm(b, a.mod(b));
        }
    }
}
