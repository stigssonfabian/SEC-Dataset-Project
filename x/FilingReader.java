/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x;

import companies.Companies;
import companies.CompanyProfile;
import data.DataLine;
import filings.Filing;
import data.NumData;
import data.NumberInfo;
import data.Numbers;
import filings.FilingSummary;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import paths.Paths;
import time.FilingPeriod;
import time.Quarter;

/**
 *
 * @author Fabian
 */
public class FilingReader {

    private FilingReader() {
    }

    public static CompanyProfile readCompanyProfile(String cik) throws IOException {
        return new CompanyProfile(FilingReader.readQuarterlyFilings(cik),
                FilingReader.readAnnualFilings(cik),
                FilingReader.readAnnualFilingSummary(cik),
                FilingReader.readQuarterlyFilingSummary(cik));
    }

    public static FilingSummary readQuarterlyFilingSummary(String cik) throws IOException {
        return readFilingSummary(Companies.getQuarterlySummary(cik));
    }

    public static FilingSummary readAnnualFilingSummary(String cik) throws IOException {
        return readFilingSummary(Companies.getAnnualSummary(cik));
    }

    private static FilingSummary readFilingSummary(File filing) throws FileNotFoundException, IOException {

        if (!filing.exists()) {
            return null;
        }

        BufferedReader reader = new BufferedReader(new FileReader(filing));

        String[] periods = reader.readLine().split(FilingSummary.SEGMENT_DELIMITER);

        FilingPeriod[] filingPeriods = new FilingPeriod[periods.length];

        for (int i = 0; i < periods.length; i++) {
            int ddate = Integer.valueOf(periods[i].split(FilingSummary.VALUE_DELIMITER)[FilingSummary.DDATE]);
            String period = periods[i].split(FilingSummary.VALUE_DELIMITER)[FilingSummary.PERIOD];
            filingPeriods[i] = new FilingPeriod(ddate, period);
        }

        String line = null;

        List<Numbers> numbers = new LinkedList<>();

        while ((line = reader.readLine()) != null) {
            try {
                String[] segments = line.split(FilingSummary.SEGMENT_DELIMITER);

                String[] tagSegment = segments[FilingSummary.SEGMENT_TAG_DATA].split(FilingSummary.VALUE_DELIMITER);

                NumberInfo info = new NumberInfo(tagSegment[FilingSummary.UOM],
                        tagSegment[FilingSummary.TAG],
                        tagSegment[FilingSummary.PLABEL]);

                String[] valueSegment = segments[FilingSummary.SEGMENT_VALUE_DATA].split(FilingSummary.VALUE_DELIMITER);
                List<String> list = new LinkedList<>();
                for (String value : valueSegment) {
                    list.add(value);
                }

                numbers.add(new Numbers(info, list));
            } catch (Exception ex) {

            }
        }

        return new FilingSummary(filingPeriods, numbers);

    }

    public static Filing readFiling(String cik, Quarter q) throws IOException {
        return readFiling(new File(q.toCompanyQFilingPath(cik)));
    }

    public static Filing readFiling(String cik, int year) throws IOException {
        return readFiling(Companies.getAnnualFiling(cik, year));
    }

    public static List<Filing> readAnnualFilings(String cik) throws IOException {
        return readFilings(Companies.getAnnualFilings(cik));
    }

    public static List<Filing> readQuarterlyFilings(String cik) throws IOException {
        return readFilings(Companies.getQuarterlyFilings(cik));
    }

    private static List<Filing> readFilings(File[] filingFiles) throws IOException {

        List<Filing> filings = new LinkedList<>();
        if (filingFiles == null) {
            return filings;
        }
        if (filingFiles.length == 0) {
            return filings;
        }

        for (File f : filingFiles) {
            filings.add(readFiling(f));
        }
        return filings;
    }

    private static Filing readFiling(File file) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();// submissionline
        DataLine submissionLine = new DataLine(line.split(Filing.VALUE_DELIMITER));

        List<NumData> numData = new LinkedList<>();

        while ((line = reader.readLine()) != null) {
            numData.add(new NumData(line.split(Filing.VALUE_DELIMITER)));
        }
        return new Filing(submissionLine, numData);
    }

}
