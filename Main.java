public class Main{

    public static void main(String[] args){

        //make some test cases
        //inputs as strings
        //ToomCookMulti.multiply("3158351","51386597");

        //for testing rn
        /*
        MassiveInteger testy = new MassiveInteger("4321");
        MassiveInteger testy2 = new MassiveInteger("3298");
        MassiveInteger res = testy.add(testy2);
        System.out.println(res.toString());
        System.out.println(4321+3298);

        System.out.println(testy.schoolbookMultiply(testy2).toString());
        System.out.println(4321*3298);
        System.out.println(res.scalarMultiply(23));
        System.out.println((4321+3298)*23);
        
        MassiveInteger trimTest = new MassiveInteger("000000000000000000000004321");
        trimTest.trim();
        System.out.println(trimTest.toString());
        */
        MassiveInteger testy = new MassiveInteger("-4320432329843214320");
        MassiveInteger testy2 = new MassiveInteger("043232983214321");
        System.out.println(testy.scalarDivide(-1));
        

    }

}