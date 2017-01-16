/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package companies;

import data.IndustryClassification;
import dataset.DataSet;
import filings.FilingSummary;
import files.SubFiles;
import filings.Filing;
import data.NumData;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import paths.Names;
import paths.Paths;
import search.CikPair;
import search.Search;
import time.Quarter;
import x.FilingReader;

/**
 *
 * @author Fabian
 */
public class Companies {

    public static String VALUE_DELIMITER = DataSet.DATA_SET_DELIMITER;

    public static final String CIK_LOOKUP_DELIMITER = ":";

    public static final int CIK_COMPANY_NAME_INDEX = 0;
    public static final int CIK_COMPANY_CIK_INDEX = 1;

    public static List<IndustryClassification> getIndustryClassifications() throws FileNotFoundException, IOException {

        File file = new File(Paths.FILE_SIC_LOOKUP);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = null;

        List<IndustryClassification> classifications = new LinkedList<>();

        while ((line = reader.readLine()) != null) {
            int indexOf = line.indexOf(" ");
            String sic = line.substring(0, indexOf);
            String industry = line.substring(indexOf + 1);
            classifications.add(new IndustryClassification(sic, industry));
        }
        return classifications;
    }

    public static List<CikPair> searchForCiks(String companyNameQuery) throws FileNotFoundException, IOException {
        File file = new File(Paths.FILE_CIK_LOOKUP);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = null;

        List<CikPair> cikPairs = new LinkedList<>();

        while ((line = reader.readLine()) != null) {
            try {
                String companyName = line.split(CIK_LOOKUP_DELIMITER)[CIK_COMPANY_NAME_INDEX];
                String cik = Integer.parseInt(line.split(CIK_LOOKUP_DELIMITER)[CIK_COMPANY_CIK_INDEX]) + "";

                if (companyName.toLowerCase().contains(companyNameQuery.toLowerCase())) {
                    cikPairs.add(new CikPair(cik, companyName));
                }
            } catch (Exception ex) {

            }
        }
        reader.close();
        return Search.levenstheinSearchCik(companyNameQuery, cikPairs);
    }

    public static int companyCount() {
        return new File(Paths.FOLDER_COMPANIES).list().length;
    }

    public static File[] getQuarterlyFilings(String cik) {
        return new File(Paths.FOLDER_COMPANIES + File.separatorChar + cik + File.separatorChar + Paths.FOLDER_QUARTERLY_FILINGS).listFiles((File pathname) -> {
            return !pathname.getName().startsWith(".");
        });
    }

    public static File[] getAnnualFilings(String cik) {
        return new File(Paths.FOLDER_COMPANIES + File.separatorChar + cik + File.separatorChar + Paths.FOLDER_ANNUAL_FILINGS).listFiles((File pathname) -> {

            return !pathname.getName().startsWith(".");
        });
    }

    public static List<String> getAllCompanyCiks() {
        String[] list = new File(Paths.FOLDER_COMPANIES).list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        });
        return Arrays.asList(list);
    }

    public static File getAnnualFiling(String cik, int year) {
        return new File(Paths.FOLDER_COMPANIES + File.separatorChar + cik + File.separatorChar + Paths.FOLDER_ANNUAL_FILINGS + File.separatorChar + year + ".txt");

    }

    public static File getAnnualSummary(String cik) {
        return new File(Paths.FOLDER_COMPANIES + File.separatorChar + cik + File.separatorChar + Paths.FILE_ANNUAL_FILING_SUMMARY);
    }

    public static File getQuarterlySummary(String cik) {
        return new File(Paths.FOLDER_COMPANIES + File.separatorChar + cik + File.separatorChar + Paths.FILE_QUARTERLY_FILING_SUMMARY);
    }

    public static void writeFiling(Filing filing) throws IOException {

        String formFolder = filing.isAnnual() ? Paths.FOLDER_ANNUAL_FILINGS : Paths.FOLDER_QUARTERLY_FILINGS;

        File output = new File(Paths.FOLDER_COMPANIES + File.separatorChar + filing.getSubmissionLine().get(SubFiles.CIK) + File.separatorChar + formFolder + File.separatorChar + filing.getDate() + ".txt");

        Paths.ensurePath(output);
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));

        writer.append(filing.getSubmissionLine().toDelimiteredString(VALUE_DELIMITER));
        writer.newLine();

        List<NumData> numData = filing.getNumData();

        for (int i = 0; i < numData.size() - 1; i++) {
            writer.append(numData.get(i).getData().toDelimiteredString(VALUE_DELIMITER));
            writer.newLine();
        }
        writer.append(numData.get(numData.size() - 1).getData().toDelimiteredString(VALUE_DELIMITER));
        writer.close();
        writeSummaryFiling(filing);

    }

    private static void writeSummaryFiling(Filing filing) throws FileNotFoundException, IOException {
        File summary = new File(Paths.FOLDER_COMPANIES + File.separatorChar + filing.getSubmissionLine().get(SubFiles.CIK) + File.separatorChar + (filing.isAnnual() ? Paths.FILE_ANNUAL_FILING_SUMMARY : Paths.FILE_QUARTERLY_FILING_SUMMARY));

        if (summary.exists()) {
            writeExistingSummary(filing, summary);
        } else {
            writeNewSummary(filing, summary);
        }

    }

    private static void writeNewSummary(Filing filing, File summary) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(summary));

        writer.append(filing.getDate() + FilingSummary.VALUE_DELIMITER + filing.getSubmissionLine().get(SubFiles.FILED));
        writer.newLine();

        for (int i = 0; i < filing.getNumData().size() - 1; i++) {
            String numDataRow = createNewNumDataRow(filing.getNumData().get(i), 0);

            writer.append(numDataRow);
            writer.newLine();
        }
        writer.append(createNewNumDataRow(filing.getNumData().get(filing.getNumData().size() - 1), 0));
        writer.close();
    }

    private static void writeExistingSummary(Filing filing, File summary) throws FileNotFoundException, IOException {

        BufferedReader reader = new BufferedReader(new FileReader(summary));

        String dates = reader.readLine();
        int recordedDates = dates.split(FilingSummary.SEGMENT_DELIMITER).length;
        // appending new date to summary filing
        String newSummaryFiling = filing.getDate() + FilingSummary.VALUE_DELIMITER + filing.getSubmissionLine().get(SubFiles.FILED) + FilingSummary.SEGMENT_DELIMITER + dates + "\n";

        String line = null;

        if (dates.contains(filing.getDate())) {
            reader.close();
            return;
        }

        List<String> existingTags = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String tagSegment = line.split(FilingSummary.SEGMENT_DELIMITER)[FilingSummary.SEGMENT_TAG_DATA];
            String valueSegment = line.split(FilingSummary.SEGMENT_DELIMITER)[FilingSummary.SEGMENT_VALUE_DATA];
            String tag = tagSegment.split(FilingSummary.VALUE_DELIMITER)[FilingSummary.TAG];
            existingTags.add(tag);
            String value = findExistingSummaryData(tag, filing.getNumData());
            newSummaryFiling += tagSegment + FilingSummary.SEGMENT_DELIMITER + value + FilingSummary.VALUE_DELIMITER + valueSegment + "\n";
        }
        reader.close();
        newSummaryFiling = newSummaryFiling.trim();
        for (int i = 0; i < filing.getNumData().size(); i++) {
            if (!containsNewTag(existingTags, filing.getNumData().get(i).getData().get(NumData.TAG))) {
                String newNumDataRow = createNewNumDataRow(filing.getNumData().get(i), recordedDates);
                newSummaryFiling += newNumDataRow + "\n";
            }
        }
        newSummaryFiling = newSummaryFiling.trim();

        BufferedWriter writer = new BufferedWriter(new FileWriter(summary));
        writer.write(newSummaryFiling);
        writer.close();
    }

    private static String createNewNumDataRow(NumData numData, int periods) {

        String dataRow = numData.get(NumData.TAG) + FilingSummary.VALUE_DELIMITER
                + numData.get(NumData.PLABEL)
                + FilingSummary.VALUE_DELIMITER
                + numData.get(NumData.UOM)
                + FilingSummary.SEGMENT_DELIMITER
                + numData.get(NumData.VALUE);

        int loopVar = periods - 1;

        if (periods > 0) {
            dataRow += Filing.VALUE_DELIMITER;
        }

        for (int i = 0; i < loopVar; i++) {
            dataRow += Names.NO_DATA + Filing.VALUE_DELIMITER;
        }

        if (loopVar >= 0) {
            dataRow += Names.NO_DATA;
        }

        return dataRow;

    }

    private static boolean containsNewTag(List<String> tags, String newTag) {
        for (String tag : tags) {
            if (tag.equals(newTag)) {
                return true;

            }
        }
        return false;
    }

    private static String findExistingSummaryData(String tag, List<NumData> numData) {
        for (NumData nd : numData) {
            if (nd.get(NumData.TAG).equals(tag)) {
                return nd.getData().get(NumData.VALUE);
            }
        }
        return Names.NO_DATA;
    }

    /**
     *
     * @return the latest quarter that has been extracted from to create filings
     */
    public static Quarter getLatestAnalysedQuarter() {
        String[] list = new File(Paths.FOLDER_COMPANIES).list((File dir, String name) -> !name.startsWith("."));

        int filingsToCheck = 10;

        int iter = (list.length < filingsToCheck) ? list.length : filingsToCheck;

        List<Quarter> latestQuarters = new LinkedList<>();

        for (int i = 0; i < iter; i++) {

            String[] qFilings = new File(Paths.FOLDER_COMPANIES + File.separatorChar + list[i] + File.separatorChar + Paths.FOLDER_QUARTERLY_FILINGS).list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return !name.startsWith(".");
                }
            });

            // if the folder does not contain any Quarterly filings the counter
            // variable is incremented so more can be checked
            if (qFilings == null) {
                if (list.length > iter) {
                    i++;
                }
                continue;
            }
            if (qFilings.length == 0) {
                if (list.length > iter) {
                    i++;
                }
                continue;
            }

            latestQuarters.add(latest(qFilings));
        }

        if (!latestQuarters.isEmpty()) {
            return latest(latestQuarters);
        } else {
            return null;
        }

    }

    private static Quarter latest(List<Quarter> quarters) {

        Quarter latestQ = quarters.get(0);
        for (int i = 1; i < quarters.size(); i++) {
            Quarter q = quarters.get(i);

            if (latestQ.isBefore(q)) {
                latestQ = q;
            }
        }
        return latestQ;
    }

    private static Quarter latest(String[] quarters) {
        Quarter latestQ = new Quarter(quarters[0].replaceAll("\\.[a-zA-Z]+", ""));
        for (int i = 1; i < quarters.length; i++) {
            Quarter q = new Quarter(quarters[i].replaceAll("\\.[a-zA-Z]+", ""));

            if (latestQ.isBefore(q)) {
                latestQ = q;
            }
        }
        return latestQ;
    }

}
