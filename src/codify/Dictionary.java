/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Razorbreak
 */
public class Dictionary {
    
    private ArrayList<DictionaryEntry> dictionary;
    private int len = 0;
    boolean debug = false;
    
    public Dictionary(){
         dictionary = new ArrayList<>();
    }
    
    public void sortDictionary(){
        Collections.sort(dictionary, new Comparator<DictionaryEntry>() {
            @Override
            public int compare(DictionaryEntry one, DictionaryEntry other) {
                return one.compareTo(other);
                }
            });
    }
    
    public void insert(String character){
        dictionary.add(new DictionaryEntry(character));
        len++;
    }
    
    public void insert(DictionaryEntry entry){
        dictionary.add(entry);
        len++;
    }
    
    public void update(int index){
        dictionary.get(index).increaseCounter();
    }
    
    public void delete(int index){
        if(index<dictionary.size()){
            dictionary.remove(index);
            len--;
        }else{
            System.out.println("Error: Index out of bounds");
        }
    }
    
    public void updateProbabilities(int totalChars){
        for(int i=0;i<dictionary.size();i++){
            DictionaryEntry entry = dictionary.get(i);
            entry.updateProbability(totalChars);
        }
    }
    
    public DictionaryEntry get(int index){
        return this.dictionary.get(index);
    }
    
    public DictionaryEntry get(String character){
        for(int i=0;i<this.len;i++){
            DictionaryEntry entry = this.dictionary.get(i);
            if(entry.character.equals(character)){
                return entry;
            }
        }
        return null;
    }
    
    public String getCode(String character){
        for(int i=0;i<this.len;i++){
            DictionaryEntry entry = this.dictionary.get(i);
            if(entry.character.equals(character)){
                return entry.huffCode;
            }
        }
        return null;
    }
    
    public int len(){
        return this.len;
    }
    
    @Override
    public String toString(){
        String s = "";
        s += " ------------------ DICTIONARY ------------------\n";
        for(int i=0;i<this.len;i++){
            DictionaryEntry entry = this.dictionary.get(i);
            if(this.dictionary.get(i).counter==0){
                if(debug){
                    s += " > '"+entry.character+"' ~> "+entry.huffCode+" - N:"+entry.counter+", P:"+String.format("%.4f",entry.probability);
                    s += " ---> "+this.dictionary.get(i)+"\n";
                }
            }else{
                s += " > '"+entry.character+"' ~> "+entry.huffCode+" - N:"+entry.counter+", P:"+String.format("%.4f",entry.probability);
                s += "\n";
            }
        }
        s += " ------------------ Total: "+len+" entries ------------------\n";
        return s;
    }
}
