/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filings;

import data.NumData;
import data.DataLine;
import dataset.DataSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Fabian
 */
public class Filing {

    public static final String VALUE_DELIMITER = DataSet.DATA_SET_DELIMITER;

    private String date;

    private boolean annual;

    private DataLine submissionLine;

    private List<NumData> numData;

    public Filing(String date, boolean annual, DataLine submissionLine, List<NumData> numData) {
        this.date = date;
        this.annual = annual;
        this.submissionLine = submissionLine;
        this.numData = numData;
    }

    public NumData getNumberByTag(String tag) {
        for (NumData num : numData) {
            if (num.get(NumData.TAG).equals(tag)) {
                return num;
            }
        }
        return null;
    }
    
    public NumData getNumberByPLabel(String plabel){
        for (NumData num : numData) {
            if (num.get(NumData.TAG).equals(plabel)) {
                return num;
            }
        }
        return null;
    }
    
    public NumData search(String query){
        for (NumData num : numData) {
            if (num.get(NumData.TAG).toLowerCase().contains(query.toLowerCase())) {
                return num;
            }
        }
        return null;
    }
    public List<NumData> searchMultiple(String query){
        List<NumData> result = new LinkedList<>();
        
        for (NumData num : numData) {
            if (num.get(NumData.TAG).toLowerCase().contains(query.toLowerCase())) {
                result.add(num);
            }
        }
        return result;
    }

    public Filing(DataLine submissionLine, List<NumData> numData) {
        this.submissionLine = submissionLine;
        this.numData = numData;
    }

    public DataLine getSubmissionLine() {
        return submissionLine;
    }

    public void setSubmissionLine(DataLine submissionLine) {
        this.submissionLine = submissionLine;
    }

    public List<NumData> getNumData() {
        return numData;
    }

    public void setNumData(List<NumData> numData) {
        this.numData = numData;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAnnual() {
        return annual;
    }

    public void setAnnual(boolean annual) {
        this.annual = annual;
    }

}
