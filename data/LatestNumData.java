/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import files.NumFiles;

/**
 *
 * @author Fabian
 */
public class LatestNumData {

    private DataLine latest;

    private static final String QUARTERLY_DATA_LENGTH = "1";
    private static final String ANNUAL_DATA_LENGTH = "4";
    private static final String BALANCE_SHEET_DATA_LENGTH = "0";

    private boolean isAnnualFiling;

    public LatestNumData(boolean isAnnualFiling) {
        this.isAnnualFiling= isAnnualFiling;
    }

    public void addLatest(DataLine numLine) {
        if (!isAnnualFiling) {
            updateQuarterlyLatest(numLine);
        } else {
            updateAnnualLatest(numLine);
        }

    }

    private void updateAnnualLatest(DataLine numLine) {
        boolean done = true;
        if (numLine.get(NumFiles.QTRS).equals(ANNUAL_DATA_LENGTH) || numLine.get(NumFiles.QTRS).equals(BALANCE_SHEET_DATA_LENGTH)) {
            if (latest == null) {
                latest = numLine;
                done = false;
            } else {

                int ddatePassed = Integer.valueOf(numLine.get(NumFiles.DDATE));
                int ddateLatest = Integer.valueOf(latest.get(NumFiles.DDATE));
                if (ddatePassed > ddateLatest) {
                    this.latest = numLine;
                    
                }
                done = false;

            }
        }
        
        if(done){
            System.err.println("err a");
        }
    }

    private void updateQuarterlyLatest(DataLine numLine) {
        boolean done = true;
        if (numLine.get(NumFiles.QTRS).equals(QUARTERLY_DATA_LENGTH) || numLine.get(NumFiles.QTRS).equals(BALANCE_SHEET_DATA_LENGTH)) {
            if (latest == null) {
                latest = numLine;
                done = false;
            } else {
                int ddatePassed = Integer.valueOf(numLine.get(NumFiles.DDATE));
                int ddateLatest = Integer.valueOf(latest.get(NumFiles.DDATE));
                if (ddatePassed > ddateLatest) {
                    this.latest = numLine;
                    
                }
                done = false;

            }
        }
        if(done){
            System.err.println("err q");
        }
    }

    public DataLine getLatestDataLine() {
        return latest;
    }

}
