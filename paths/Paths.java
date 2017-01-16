/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paths;

import java.io.File;

/**
 *
 * @author Fabian
 */
public class Paths {

    public static final String FOLDER_DATA_SETS = "DataSets";

    public static final String FOLDER_COMPANIES = "Companies";

    public static final String FOLDER_RESOURCES = "Resources";

    public static final String FOLDER_ANNUAL_FILINGS = "AnnualFilings";
    public static final String FOLDER_QUARTERLY_FILINGS = "QuarterlyFilings";

    public static final String FILE_ANNUAL_FILING_SUMMARY = "AFilingSummary.txt";
    public static final String FILE_QUARTERLY_FILING_SUMMARY = "QFilingSummary.txt";

    public static final String FILE_CIK_LOOKUP = FOLDER_RESOURCES + File.separatorChar + "cik-lookup.txt";
    public static final String FILE_SIC_LOOKUP = FOLDER_RESOURCES + File.separatorChar + "sic-lookup.txt";

    public static final String FILE_S_AND_P_500 = FOLDER_RESOURCES + File.separatorChar + "SP500.txt";

    public static final String FILE_PRICES = FOLDER_RESOURCES + File.separatorChar + "PRICES.CSV";

    public static void ensurePath(String dirPathToFile) {
        new File(dirPathToFile).getParentFile().mkdirs();
    }

    public static void ensurePath(File file) {
        file.getParentFile().mkdirs();
    }

}
