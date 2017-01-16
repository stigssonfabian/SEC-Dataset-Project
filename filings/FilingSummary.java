/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filings;

import dataset.DataSet;
import data.Numbers;
import java.util.List;
import numbers.NumberId;
import numbers.NumberParts;
import time.FilingPeriod;

/**
 * STRUCTURE = TAG\tPLABEL\tUOM~VALUE\tVALUE...
 *
 * @author Fabian
 */
public class FilingSummary {

    public static final String VALUE_DELIMITER = DataSet.DATA_SET_DELIMITER;
    public static final String SEGMENT_DELIMITER = "~";

    public static final int SEGMENT_TAG_DATA = 0;
    public static final int SEGMENT_VALUE_DATA = 1;

    public static final int PERIOD = 0;
    public static final int DDATE = 1;

    public static final int TAG = 0;
    public static final int PLABEL = 1;
    public static final int UOM = 2;

    private FilingPeriod[] periods;

    private List<Numbers> numbers;

    public FilingSummary(FilingPeriod[] periods, List<Numbers> numbers) {
        this.periods = periods;
        this.numbers = numbers;
    }

    public FilingPeriod getPeriod(int index) {
        return periods[index];
    }

    public List<Numbers> getNumbers() {
        return numbers;
    }

    public String getValueOf(String tag, int index) {
        for (Numbers nums : numbers) {
            if (nums.getInfo().getTag().equals(tag)) {
                return nums.getValue(index);
            }
        }
        return null;
    }

    public Numbers getNumbers(String tag) {
        for (Numbers nums : numbers) {
            if (nums.getInfo().getTag().equals(tag)) {
                return nums;
            }
        }
        return null;
    }
    
    public FilingPeriod[] getPeriods(){
        return this.periods;
    }

    public Numbers getNumbers(NumberId id) {
        List<String> synonymsInPrefferedOrder = id.getSynonymsInPrefferedOrder();
        for (String syn : synonymsInPrefferedOrder) {
            for (Numbers nums : numbers) {
                if (nums.getInfo().getTag().equals(syn)) {
                    return nums;
                }
            }
        }
        
        List<NumberParts> parts = id.getParts();
        
        Numbers part1 = null;
        
        // handles the case where there is two numbers building up to the
        // desired number id
        for (NumberParts part : parts) {
            for (Numbers nums : numbers) {
                if(part.getParts().contains(nums.getInfo().getTag())){
                    if(part1 == null){
                        part1 = new Numbers(nums);
                    }else{
                        return nums.conbine(part1, part1.getInfo().getTag() + "+" + nums.getInfo().getTag(), id.getName());
                    }
                    
                    
                }
            }
        }
        
        return null;
    }

}
