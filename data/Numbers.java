/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.LinkedList;
import java.util.List;
import paths.Names;

/**
 *
 * @author Fabian
 */
public class Numbers {

    private NumberInfo info;

    private List<String> values;
    
    public Numbers(Numbers other){
        this.info = new NumberInfo(other.info.getUom(), other.info.getTag(), other.info.getPlabel());
        values = new LinkedList<>();
        for (String value : other.values) {
            values.add(value);
        }
    }

    public Numbers(NumberInfo info, List<String> values) {
        this.info = info;
        this.values = values;
    }

    public String getValue(int index) {
        return values.get(index);
    }

    public boolean hasData(int index) {
        return values.get(index).equals(Names.NO_DATA);
    }

    public NumberInfo getInfo() {
        return info;
    }

    @Override
    public String toString() {
        String s = info.toString() + "\n";
        return s + values.toString();
    }
    
    public int length(){
        return this.values.size();
    }

    /**
     * Combines a number with another that builds to another.
     *
     * For instance. (1)ServiceRevenue and GoodsRevenue build Total Revenue.
     * (2)Current liabillities and Uncurrent liabillities build Total Debt
     *
     *
     * @param other
     * @param newTag
     * @param plabel
     * @return the combines number for two sub numbers
     */
    public Numbers conbine(Numbers other, String newTag, String plabel) {
        NumberInfo ninfo = new NumberInfo(info.getUom(), newTag, plabel);
        
        List<String> totalValues = new LinkedList<>();
        
        for (int i = 0; i < other.values.size(); i++) {
            totalValues.add(String.valueOf(Double.valueOf(this.values.get(i)) + Double.valueOf(other.values.get(i))));
        }
        return new Numbers(ninfo, totalValues);
    }

}
