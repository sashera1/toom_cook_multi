public class MassiveInteger{

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
    /* 
    public MassiveInteger add(MassiveInteger b){

    }

    public MassiveInteger subtract(MassiveInteger b){

    }

    public MassiveInteger schoolbookMultiply(MassiveInteger b){
        
        //When Massive integer becomes small enought (the "base case"),
        //schoolbook multiplication should be called
        

    }

    public MassiveInteger scalarMultiply(int scalar){

    }

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