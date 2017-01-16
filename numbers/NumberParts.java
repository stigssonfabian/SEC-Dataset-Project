/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numbers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents number parts that adds up to a final number
 * @author Fabian
 */
public class NumberParts {
    
    private List<String> parts = new LinkedList<>();

    public NumberParts(String ... parts) {
        for (String part : parts) {
            this.parts.add(part);
        }
    }
    
    public List<String> getParts(){
        return parts;
    }
    
  


    
    
    
    
}
