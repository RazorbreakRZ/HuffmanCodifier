/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codify;

/**
 *
 * @author Razorbreak
 */
public class DictionaryEntry implements Comparable<DictionaryEntry> {
    
    String character;
    int counter;
    float probability;
    String huffCode = null;
    DictionaryEntry ltree = null;
    DictionaryEntry rtree = null;
    
    public DictionaryEntry(String cha){
        character = cha;
        counter = 1;
        probability = 0;
    }
    
    public DictionaryEntry(String cha,int count,float prob){
        character = cha;
        counter = count;
        probability = prob;
    }
    
    public DictionaryEntry(String cha,int count,float prob,DictionaryEntry lt,DictionaryEntry rt){
        character = cha;
        counter = count;
        probability = prob;
        ltree = lt;
        rtree = rt;
    }
    
    public void updateCode(String currentCode){
        if(this.ltree==null && this.rtree==null){
            this.huffCode = currentCode;
        }else{
            ltree.updateCode(currentCode+"0");
            rtree.updateCode(currentCode+"1");
        }
    }
    
    public void increaseCounter(){
        counter++;
    }
    
    public void updateProbability(int totalChars){
        probability = (float)counter / totalChars;
    }
    
    public void updateHuffmanCode(String binCode){
        huffCode = binCode;
    }

    @Override
    public int compareTo(DictionaryEntry o) {
        int value;
        value = Float.compare(this.probability, o.probability);
        if(value==0){
            int val1 = this.counter;
            int val2 = o.counter;
            if(val1>val2){
                value = 1;
            }else if(val1<val2){
                value = -1;
            }else{
                if(this.character.length()<o.character.length()){
                    value = 1;
                }else if(this.character.length()>o.character.length()){
                    value = -1;
                }else{
                    val1 = Integer.valueOf(this.character.charAt(0));
                    val2 = Integer.valueOf(o.character.charAt(0));
                    if(val1>val2){
                        value = 1;
                    }else if(val1<val2){
                        value = -1;
                    }
                }
            }
        }
        return value;
    }
        
    @Override
    public String toString(){
        String s = "";
        if(this.ltree==null && this.rtree==null){
            s += character;
        }else{
            s += "["+this.ltree+","+this.rtree+"]";
        }
        return s;
    }
}
