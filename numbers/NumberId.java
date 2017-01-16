/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numbers;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Fabian
 */
public class NumberId {

    private String name;

    private List<String> synonyms = new LinkedList<>();
    private List<NumberParts> parts = new LinkedList<>();
    private List<String> plabels = new LinkedList<>();
    
    
    public NumberId(String name, String[] aliases, NumberParts[] pairs, String [] plabels) {
        for (String alias : aliases) {
            this.synonyms.add(alias);
        }
        
        for (NumberParts pair : pairs) {
            this.parts.add(pair);
        }
        
        for (String plabel : plabels) {
            this.plabels.add(plabel);
        }
    
        
        this.name = name;
    }



    public List<String> getSynonymsInPrefferedOrder() {
        return synonyms;
    }
    public List<NumberParts>getParts(){
        return parts;
    }
    /**
     * all lower case, should be used with the contains() method for string
     * @return 
     */
    public List<String> getPlabels(){
        return plabels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    

}
