
public class ToomCookMulti {
    /*
    while normally implemented in base 2^32,
    I will implement this in base 10^9.
    While less efficient, this makes the code more readable
    and is appropriate for the scope of the project
    */

    private static final int THRESHOLD = 2;

    public static String multiply(String aStr, String bStr){
        MassiveInteger a = new MassiveInteger(aStr);
        MassiveInteger b = new MassiveInteger(bStr);
        return toom3(a, b).toString();
    }
    /*
    what we need: main recursive alg, and helper methods 
    where appropriate to encapsulate concepts and/or reuse code */
    private static MassiveInteger toom3(MassiveInteger a, MassiveInteger b){
        if (a.length() <= THRESHOLD || b.length() <= THRESHOLD){
            return a.schoolbookMultiply(b);
        }
        int k = (Math.max(a.length(), b.length()) + 2) / 3;
        MassiveInteger a0 = a.getLimbs(0, k);
        MassiveInteger a1 = a.getLimbs(k, 2*k);
        MassiveInteger a2 = a.getLimbs(2*k, a.length());
        MassiveInteger b0 = b.getLimbs(0, k);
        MassiveInteger b1 = b.getLimbs(k, 2*k);
        MassiveInteger b2 = b.getLimbs(2*k, b.length());

        // Evaluate at {0, 1, -1, 2, inf}
        MassiveInteger p0   = a0;
        MassiveInteger p1   = a0.add(a1).add(a2);
        MassiveInteger pm1  = a0.subtract(a1).add(a2);
        MassiveInteger p2   = a0.add(a1.scalarMultiply(2)).add(a2.scalarMultiply(4));
        MassiveInteger pinf = a2;

        MassiveInteger q0   = b0;
        MassiveInteger q1   = b0.add(b1).add(b2);
        MassiveInteger qm1  = b0.subtract(b1).add(b2);
        MassiveInteger q2   = b0.add(b1.scalarMultiply(2)).add(b2.scalarMultiply(4));
        MassiveInteger qinf = b2;

        return null; // placeholder
    }

}