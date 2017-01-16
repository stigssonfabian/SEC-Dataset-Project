/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Fabian
 */
public class DataLine {

    private String[] data;

    public DataLine(String[] data) {
        this.data = data;
    }

    public String get(int index) {
        return data[index];
    }

    public void set(int index, String value) {
        data[index] = value;
    }

    public String[] getData() {
        return data;
    }
    
    public int dataCount(){
        return data.length;
    }
        
    /**
     * Splits every item in the data line and returns a string that is delimitered by
     * the passed delimiter
     * @param delimiter
     * @return 
     */
    public String toDelimiteredString(String delimiter){
        String string = "";
        for(int i = 0; i< data.length - 1; i++){
          string +=  data[i] + delimiter;
        }
        string += data[data.length - 1];
        return string;
    }

}
