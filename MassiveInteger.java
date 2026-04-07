public class MassiveInteger{
    private static final long BASE = 1000000000L;
    /*
    Custom class to store integers that are much to big to be longs
    The array of ints itself (not the individual ints within the array)
    is stored in little endian form
     
    array of ints will always store magnitude:
    sign as boolean flag 
    */

    private int[] contents;
    private boolean positive;

    public MassiveInteger(String inputStr){
        /*
        instantiate and convert from string
        little endian
        */
        int digitLen = inputStr.length();

        if (inputStr.startsWith("-")){
            this.positive = false;
            inputStr = inputStr.substring(1,digitLen);
            digitLen = inputStr.length();
        }
        else{
            this.positive = true;
        }

        
        int contentsLen = (digitLen +8 ) / 9;
        this.contents = new int[contentsLen];

        int contentsIdx = 0;
        int stringIdx = digitLen-1; 

        while(stringIdx>=8){
            contents[contentsIdx] = Integer.parseInt(inputStr.substring(stringIdx-8,stringIdx+1));
            contentsIdx++;
            stringIdx-=9;
        }

        if (stringIdx>=0){
            contents[contentsIdx]=Integer.parseInt(inputStr.substring(0,stringIdx+1));
        }

    }

    public MassiveInteger(int[] contents, boolean positive){
        //should add something that truncates unneeded empty 0 ints in array
        this.contents = contents;
        this.positive = positive;
    }

    public boolean isPositive(){
        return this.positive;
    }

    public void flipSign(){
        this.positive = !this.positive;
    }
     
    public MassiveInteger add(MassiveInteger b){
        /*
        this method could add a bunch of extra elements of all 0's in contents
        we should trim in instantiation

        HAVE NOT YET ACCOUNTED FOR NEGATIVE AND POSITIVITY
         */


        int maxResultSize = (Math.max(this.contents.length, b.contents.length) + 1);
        int[] resultContents = new int[maxResultSize];

        long carry = 0;

        for (int i = 0; i < maxResultSize; i++){
            long thisVal = (i<this.contents.length) ? this.contents[i] : 0;
            long bVal = (i<b.contents.length) ? b.contents[i] : 0;

            long intermediateRes = thisVal+bVal+carry;

            resultContents[i]=(int) (intermediateRes%BASE);
            carry = intermediateRes / BASE;
        }

        /* 
        since the max result size is at least one place val greater than 
        either input, we only have to place, not add, the carry (if carry != 0 at all)
        */
        if (carry!=0){
            resultContents[maxResultSize]=(int)carry;
        }

        return new MassiveInteger(resultContents,this.positive);
    }

    /*
    public MassiveInteger subtract(MassiveInteger b){

    }
    */

    public MassiveInteger schoolbookMultiply(MassiveInteger b){

        boolean positiveRes = (this.positive==b.isPositive());
        
        //When Massive integer becomes small enought (the "base case"),
        //schoolbook multiplication should be called

        int thisLen = this.contents.length;
        int bLen = b.contents.length;

        int[] resultContents = new int[thisLen+bLen];

        for (int i = 0; i<thisLen; i++){
            long carry = 0;
            for (int j = 0; j < thisLen; j++){

                long product = ((long)this.contents[i])*((long)b.contents[j]);
                long res = product + resultContents[i+j] + carry;

                resultContents[i+j] = (int)(res%BASE);
                carry = res / BASE;

            }
            if (carry!=0){
                int carryIdx = i+bLen;
                while (carry>0 && carryIdx < resultContents.length){
                    long sum = resultContents[carryIdx] + carry;
                    resultContents[carryIdx] = (int)(sum%BASE);
                    carry = sum / BASE;
                    carryIdx++;
                }

            }

        }
        MassiveInteger result = new MassiveInteger(resultContents,positiveRes);
        return result;

    }

    
    public MassiveInteger scalarMultiply(int scalar){
        boolean positiveRes = (this.positive==(scalar>=0));
        int thisLen = this.contents.length;
        int[] resultContents = new int[thisLen+1]; //dont think we're using scalars big enough for this to ever be an issue
        long carry = 0;

        for (int i = 0; i<thisLen; i++){
            long res = (long)this.contents[i] * scalar;
            resultContents[i]=(int)(res%BASE);
            carry = res/BASE;
        }
        if (carry!=0){
            resultContents[thisLen] = (int)carry;
        }
        MassiveInteger result = new MassiveInteger(resultContents,positiveRes);
        return result;
    }

    /* 
    public MassiveInteger scalarDivide(int scalar){

    }

    public MassiveInteger leftShift(int shiftFactor){

    }
    */
    public String toString(){
        String result = "";
        if (this.positive == false){
            result += "-";
        }
        
        for (int i = this.contents.length - 1; i>=0 ; i--){
            result += String.format("%09d",this.contents[i]);
        }
        return result;

    }











}