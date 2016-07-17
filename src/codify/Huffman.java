/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codify;

/**
 *
 * @author Razorbreak
 */
public class Huffman {
    
    private String original;
    private Dictionary dictionary;
    private String codifiedString;
    int errorlevel = 0;
    
    public Huffman(){
    }
        
    private void createDictionary(){
        String newChars = "";
        dictionary = new Dictionary();
        for(int i=0;i<original.length();i++){
            String cha = String.valueOf(original.charAt(i));
            if(newChars.contains(cha)){
                dictionary.update(newChars.indexOf(cha));
            }else{
                dictionary.insert(cha);
                newChars += cha;
            }
        }
        dictionary.updateProbabilities(original.length());
        dictionary.sortDictionary();
    }
        
    private void createBinTree(){
        DictionaryEntry lentry,rentry,newEntry;
        boolean exit = false;
        int index = 0;
        while(!exit){
            lentry = dictionary.get(index);
            rentry = dictionary.get(index+1);
            newEntry = new DictionaryEntry(lentry.character+rentry.character
                                            ,0
                                            ,lentry.probability+rentry.probability
                                            ,lentry
                                            ,rentry);
            dictionary.insert(newEntry);
            dictionary.sortDictionary();
            if(newEntry.probability>=1.0){
                exit = true;
            }
            index += 2;
        }
    }
    
    public void PrintDictionary(boolean debugMode){
        dictionary.debug = debugMode;
        System.out.println(dictionary);
    }
    
    public void PrintBinaryTree(){
        System.out.println(" ----- BINARY TREE -----\n "
                +this.dictionary.get(dictionary.len()-1).toString()
                +"\n -----------------------\n");
    }
    
    public void PrintCodifiedString(){
        System.out.println(this.codifiedString);
    }
    
    public void PrintDecodifiedString(){
        System.out.println(this.original);
    }
    
    public void codify(String s){
        this.original = s;
        this.codifiedString = "";
        this.createDictionary();
        this.createBinTree();
        this.dictionary.get(this.dictionary.len()-1).updateCode("");
        for(int i=0;i<this.original.length();i++){
            this.codifiedString += this.dictionary.getCode(String.valueOf(this.original.charAt(i)));
        }
        Conversor con = new Conversor();
        this.codifiedString = con.BintoHex(this.codifiedString);
    }
    
    public void decodify(String codeString,String key){
        String code;
        this.codifiedString = codeString;
        this.original = "";
        this.dictionary = new Dictionary();
        Conversor con = new Conversor();
        code = con.HextoBin(codeString);
        if(!code.equals("")){
            if(isValidKey(key) && key.length()>=5){
                this.dictionary.get(this.dictionary.len()-1).updateCode("");
                //this.PrintDictionary(true);
                //this.PrintBinaryTree();
                DictionaryEntry entry = this.dictionary.get(this.dictionary.len()-1);
                int pointer = 0;
                boolean error = true;
                while(pointer<code.length()){
                    //System.out.println("Nodo: "+entry.character);
                    if(entry.ltree!=null && entry.rtree!=null){
                        String current = String.valueOf(code.charAt(pointer));
                        pointer++;
                        //System.out.println("Current char: "+current);
                        if(current.equals("0")){
                            entry = entry.ltree;
                        }else{
                            entry = entry.rtree;
                        }
                    }else{
                        original += entry.character;
                        //System.out.println(original);
                        entry = this.dictionary.get(this.dictionary.len()-1);
                    }
                }
                if(entry.ltree==null && entry.rtree==null){
                    original += entry.character;
                    error = false;
                }
                if(error){
                    System.out.println("Error: The key can't decode the whole input code");
                    errorlevel = 1;
                }
            }else{
                System.out.println("Error: The key has not valid format");
                errorlevel = 1;
            }
        }
    }
    
    private boolean isValidKey(String key){
        boolean isValid = false;
        int open=0,close=0;
        if(key.length()!=1){
            for(int i=0;i<key.length();i++){
                String current = String.valueOf(key.charAt(i));
                if(current.equals("[")){
                    open++;
                }else if(current.equals("]")){
                    close++;
                }else if(current.equals(",")){
                    if(open-close==1){
                        String left = key.substring(1,i);
                        String right = key.substring(i+1,key.length()-1);
                        //System.out.println("Current: "+key+" ----> l:"+left+"   r:"+right);
                        isValid = isValidKey(left) && isValidKey(right);
                        if(isValid){
                            DictionaryEntry lentry = this.dictionary.get(left);
                            DictionaryEntry rentry = this.dictionary.get(right);
                            DictionaryEntry newEntry = new DictionaryEntry("["+lentry.character+","+rentry.character+"]"
                                            ,0
                                            ,0
                                            ,lentry
                                            ,rentry);
                            this.dictionary.insert(newEntry);
                        }
                        break;
                    }
                }
            }
        }else{
            isValid = true;
            this.dictionary.insert(key);
        }
        return isValid;
    }
}
