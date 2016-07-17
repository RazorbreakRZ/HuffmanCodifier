/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codify;

/**
 *
 * @author Razorbreak
 */
public class Conversor {
    
    public Conversor(){
    }
    
    public String BintoHex(String original){
        String hex = "0x";
        boolean isBin = original.matches("[0|1]+");
        if(isBin){
            String hexadecimal = "";
            for(int i=0;i<original.length();i++){
                hexadecimal += String.valueOf(original.charAt(i));
                if(i%4==3){
                    hex += Integer.toHexString(Integer.parseInt(hexadecimal,2)).toUpperCase();
                    //System.out.println(hexadecimal+" - "+hex);
                    hexadecimal = "";
                }
            }
            if(!hexadecimal.equals("")){
                int pad = 0;
                while(hexadecimal.length()<4){
                    hexadecimal += "0";
                    pad++;
                }
                hex += Integer.toHexString(Integer.parseInt(hexadecimal,2)).toUpperCase();
                //System.out.println(hexadecimal+" - "+hex);
                hex += "~"+pad;
            }
            //System.out.println("Original: "+original+"\n"+"Is bin? "+isBin+"\n"+"Hex: "+hex);
        }else{
            System.out.println("Error: The input number is not Binary");
        }
        return hex;
    }
    
    public String HextoBin(String original){
        String bin = "";
        boolean isHex = original.startsWith("0x") || original.startsWith("0X");
        int padPos = original.indexOf("~");
        boolean hasPad = padPos>-1;
        int pad = 0;
        int limit = original.length();
        if(hasPad){
            pad = Integer.parseInt(original.substring(padPos+1));
            limit = padPos;
        }
        isHex = isHex && original.substring(2, limit).matches("[0-9A-Fa-f]+");
        
        if(isHex){
            //System.out.println(original.substring(2, limit));
            for(int i=2;i<limit;i++){
                int cha = Integer.parseInt(String.valueOf(original.charAt(i)), 16);
                String binary = "";
                binary = Integer.toBinaryString(cha);
                int zeros = 4-binary.length()%5;
                for(int j=0;j<zeros;j++){
                    binary = "0"+binary;
                }
                bin += binary;
                //System.out.println(cha+" - "+binary);
            }
            bin = bin.substring(0, bin.length()-pad);
            //System.out.println("Original: "+original+"\n"+"Is hex? "+isHex+"\n"+"PadPos:"+padPos+" - Pad: "+pad+"\n"+"Bin: "+bin);
        }else{
            System.out.println("Error: The input number has not valid Hex format");
        }
        
        return bin;
    }
}
