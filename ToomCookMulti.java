
public class ToomCookMulti {
    /*
    while normally implemented in base 2^32,
    I will implement this in base 10^9.
    While less efficient, this makes the code more readable
    and is appropriate for the scope of the project
    */

    private static int DEFAULT_THRESHOLD = 2;

    private int THRESHOLD;


    public ToomCookMulti(){
        this.THRESHOLD = DEFAULT_THRESHOLD;
    }

    public ToomCookMulti(int customThreshold){
        this.THRESHOLD = customThreshold;
    }

    public String multiply(String aStr, String bStr){

        MassiveInteger a = new MassiveInteger(aStr);
        MassiveInteger b = new MassiveInteger(bStr);

        boolean resultSign = (a.isPositive()==b.isPositive());
        if (!a.isPositive()){
            a.flipSign();
        }
        if (!b.isPositive()){
            b.flipSign();
        }

        MassiveInteger result = MassiveInteger.createShallowCopy(toom3(a, b));
        if (resultSign!=result.isPositive()){
            result.flipSign();
        }
        return result.toString();
    }
    /*
    this is the main recursive alg, with a base case calling regulat multiplication
    if either number is less than about 10^18 (about 10^(9*threshold))
    */
    private MassiveInteger toom3(MassiveInteger aIn, MassiveInteger bIn){
        if (aIn.length() <= THRESHOLD || bIn.length() <= THRESHOLD){
            return aIn.schoolbookMultiply(bIn);
        }

        boolean resultSign = (aIn.isPositive()==bIn.isPositive());
        MassiveInteger a, b;
        if (!aIn.isPositive()){
            a=MassiveInteger.createShallowCopy(aIn);
            a.flipSign();
        }
        else a=aIn;
        if (!bIn.isPositive()){
            b=MassiveInteger.createShallowCopy(bIn);
            b.flipSign();
        }
        else b=bIn;



        int k = (Math.max(a.length(), b.length()) + 2) / 3;
        MassiveInteger aLow  = a.getLimbs(0, k);
        MassiveInteger aMid  = a.getLimbs(k, 2*k);
        MassiveInteger aHigh = a.getLimbs(2*k, a.length());
        MassiveInteger bLow  = b.getLimbs(0, k);
        MassiveInteger bMid  = b.getLimbs(k, 2*k);
        MassiveInteger bHigh = b.getLimbs(2*k, b.length());

        // Evaluate at {0, 1, -1, -2, inf}
        MassiveInteger aAtZero = aLow;
        MassiveInteger aAtOne  = aLow.add(aMid).add(aHigh);
        MassiveInteger aAtNeg1 = aLow.subtract(aMid).add(aHigh);
        MassiveInteger aAtNeg2 = aLow.subtract(aMid.scalarMultiply(2)).add(aHigh.scalarMultiply(4));
        MassiveInteger aAtInf  = aHigh;

        MassiveInteger bAtZero = bLow;
        MassiveInteger bAtOne  = bLow.add(bMid).add(bHigh);
        MassiveInteger bAtNeg1 = bLow.subtract(bMid).add(bHigh);
        MassiveInteger bAtNeg2 = bLow.subtract(bMid.scalarMultiply(2)).add(bHigh.scalarMultiply(4));
        MassiveInteger bAtInf  = bHigh;

        // Pointwise recursive multiplication
        MassiveInteger productAtZero = toom3(aAtZero, bAtZero);
        MassiveInteger productAtOne  = toom3(aAtOne,  bAtOne);
        MassiveInteger productAtNeg1 = toom3(aAtNeg1, bAtNeg1);
        MassiveInteger productAtNeg2 = toom3(aAtNeg2, bAtNeg2);
        MassiveInteger productAtInf  = toom3(aAtInf,  bAtInf);

        // Interpolation (Bodrato sequence)
        MassiveInteger w0 = productAtZero;
        MassiveInteger w4 = productAtInf;
        MassiveInteger w3 = productAtNeg2.subtract(productAtOne).scalarDivide(3);
        MassiveInteger w1 = productAtOne.subtract(productAtNeg1).scalarDivide(2);
        MassiveInteger w2 = productAtNeg1.subtract(productAtZero);
        w3 = w2.subtract(w3).scalarDivide(2).add(w4.scalarMultiply(2));
        w2 = w2.add(w1).subtract(w4);
        w1 = w1.subtract(w3);

        // Recomposition
        MassiveInteger res = w0
            .add(w1.leftShift(k))
            .add(w2.leftShift(2*k))
            .add(w3.leftShift(3*k))
            .add(w4.leftShift(4*k));

        if (res.isPositive()!=resultSign){
            res.flipSign();
        }

        return res;
    }

}