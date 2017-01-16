/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package time;

import java.io.File;
import paths.Paths;

/**
 * Represents a quarter of the year. With one quarter variable that can span
 * between 1 - 4 inclusivly.
 *
 * @author Fabian
 */
public class Quarter{

    protected int q;
    protected int year;

    /**
     * All quarters should textually be represented in lowercase
     *
     * @param textQuarter
     */
    public Quarter(String textQuarter) {
        textQuarter = textQuarter.toLowerCase();
        String[] spl = textQuarter.split("q");
        q = Integer.valueOf(spl[1]);
        year = Integer.valueOf(spl[0]);
    }

    public Quarter(int q, int year) {
        this.q = q;
        this.year = year;
    }

    public Quarter() {

    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isBefore(Quarter quarter) {
        if (year < quarter.getYear()) {
            return true;
        } else if (year == quarter.getYear()) {
            if (q < quarter.getQ()) {
                return true;
            } else if (q == quarter.getYear()) {
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return year + "q" + q;
    }
    /**
     * 
     * @return Paths.FOLDER_DATA_SETS + File.separatorChar + toString();
     */
    public String toDataSetPath(){
        return Paths.FOLDER_DATA_SETS + File.separatorChar + toString();
    }
    /**
     * 
     * @param cik
     * @return Paths.FOLDER_COMPANIES + File.separatorChar + cik + Paths.FOLDER_QUARTERLY_FILINGS + File.separatorChar + toString();
     */
    public String toCompanyQFilingPath(String cik){
        return Paths.FOLDER_COMPANIES + File.separatorChar + cik + Paths.FOLDER_QUARTERLY_FILINGS + File.separatorChar + toString();
    }
    
    public File toDataSetFile(String dataSetFileName){
        return new File(toDataSetPath() + File.separatorChar + dataSetFileName);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.q;
        hash = 71 * hash + this.year;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Quarter other = (Quarter) obj;
        if (this.q != other.q) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        return true;
    }

    
    
}
