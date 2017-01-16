/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

/**
 * The indexes of the tab delimited data in a line in the SubFiles (sub.txt)
 * @author Fabian
 */
public class SubFiles {
    
    
    public static final String FORM_ANNUAL = "10-K";
    public static final String FORM_ANNUAL_AMENDED = "10-K/A";
    public static final String FORM_QUARTERLY = "10-Q";
    
    

    public static final int ADSH = 0;
    /**
     * Central Index Key (CIK). Ten digit number assigned by the SEC to each registrant that submits filings.
     */
    public static final int CIK = 1;
    public static final int NAME = 2;
    public static final int SIC = 3;
    public static final int COUNTRYBA = 4;
    public static final int STPRBA = 5;
    public static final int CITYBA = 6;
    public static final int ZIPBA = 7;
    public static final int BAS1 = 8;
    public static final int BAS2 = 9;
    public static final int BAPH = 10;
    public static final int COUNTRYMA = 11;
    public static final int STPRMA = 12;
    public static final int CITYMA = 13;
    public static final int ZIPMA = 14;
    public static final int MAS1 = 15;
    public static final int MAS2 = 16;
    public static final int COUNTRYINC = 17;
    public static final int STPRINC = 18;
    public static final int EIN = 19;
    public static final int FORMER = 20;
    public static final int CHANGED = 21;
    public static final int AFS = 22;
    public static final int WKSI = 23;
    public static final int FYE = 24;
    public static final int FORM = 25;
    /**
     * Balance Sheet Date.
     */
    public static final int PERIOD = 26;
    public static final int FY = 27;
    /**
     * Fiscal Period Focus (as defined in EFM Ch. 6) within Fiscal Year. The 10-Q for the 1st, 2nd and 3rd quarters would have a fiscal period focus of Q1, Q2 (or H1), and Q3 (or M9) respectively, and a 10-K would have a fiscal period focus of FY.
     */
    public static final int FP = 28;
    public static final int FILED = 29;
    public static final int ACCEPTED = 30;
    public static final int PREVRPT = 31;
    public static final int DETAIL = 32;
    public static final int INSTANCE = 33;
    public static final int NCIKS = 34;
    public static final int ACIKS = 35;
    
    public static final int FIELD_COUNT = 36;

}
