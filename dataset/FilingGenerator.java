/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataset;

import companies.Companies;
import files.SubFiles;
import data.DataLine;
import filings.Filing;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import time.Quarter;

/**
 * Reads the content of the datasets files and stores pertinent data in objects
 *
 * @author Fabian
 */
public class FilingGenerator {

    

    public static void createFilingsForAllDescending() throws IOException {
        List<Quarter> availibleQuarters = DataSet.getAvailibleQuarters();
        
        Collections.reverse(availibleQuarters);
        
        for (Quarter q : availibleQuarters) {
            createFilingsFor(q);
            
        }
    }
    
    public static void createFilingsForAllAscending() throws IOException {
        List<Quarter> availibleQuarters = DataSet.getAvailibleQuarters();        

        for (Quarter q : availibleQuarters) {
            System.out.println("********" + q.toString() + " ********");
            createFilingsFor(q);
        }
    }

    public static void continueGeneratingFiling() throws IOException {
        Quarter latest = Companies.getLatestAnalysedQuarter();

        List<Quarter> availibleQuarters = DataSet.getAvailibleQuarters();

        for (Quarter q : availibleQuarters) {
            if (q.isBefore(latest)) {
                continue;
            }
            System.out.println("********    " + q.toString() + " *********");
            createFilingsFor(q);

        }

    }

    public static void createFilingsFor(Quarter q, String cik) throws IOException {
        Filing filing = getFilingFor(cik, q);
        if (filing != null && !filing.getNumData().isEmpty()) {
            Companies.writeFiling(filing);
        }
    }

    /**
     * Creates all the filings from the datasets availible (slow approach)
     *
     * @param cik
     */
    public static void createFilingsFor(String cik) throws IOException {

        List<Quarter> availibleQuarters = DataSet.getAvailibleQuarters();

        for (Quarter q : availibleQuarters) {
            System.out.println("********    " + q.toString() + " *********");
            Filing filing = getFilingFor(cik, q);

            if (filing != null && !filing.getNumData().isEmpty()) {;
                Companies.writeFiling(filing);
            }
        }

    }

    
    public static List<String> readSubFile(Quarter q) throws IOException{
        
        BufferedReader reader = new BufferedReader(new FileReader(q.toDataSetFile(DataSet.FILE_SUB)));
        
        String line = reader.readLine();
        
        List<String> subLines = new LinkedList<>();
        
        while((line = reader.readLine()) != null){
            subLines.add(line);
        }
        
        return subLines;
        
    }
    
    public static void createFilingsFor(Quarter q) throws IOException {        
        
        List<String> subLines = readSubFile(q);
        subLines.sort((string, anotherString) -> string.compareTo(anotherString));
       

        DatasetReader filingReader = createFilingReader(q);

        for (String line : subLines) {

            DataLine submissionLine = new DataLine(DataSet.formatLine(line).split(DataSet.DATA_SET_DELIMITER));

            boolean annualFiling = submissionLine.get(SubFiles.FORM).equals(SubFiles.FORM_ANNUAL_AMENDED)
                    || submissionLine.get(SubFiles.FORM).equals(SubFiles.FORM_ANNUAL);

            boolean quarterlyFiling = submissionLine.get(SubFiles.FORM).equals(SubFiles.FORM_QUARTERLY);
            if ((annualFiling || quarterlyFiling)) {
               // System.out.println("SubmissionLine = " + submissionLine.toDelimiteredString(", "));
                
                Filing filing = filingReader.generateFiling(submissionLine);

                if (filing != null && !filing.getNumData().isEmpty()) {
                    Companies.writeFiling(filing);
                }else{
                    System.out.println("Filing null... ADSH = " + submissionLine.get(SubFiles.ADSH) + "  Quarter " + q.toString());
                }
                

            }
        }
    }

    public static Filing getFilingFor(String cik, Quarter q) throws FileNotFoundException, IOException {
        String dataSetPath = q.toDataSetPath();

        File subFile = new File(dataSetPath + File.separatorChar + DataSet.FILE_SUB);

        BufferedReader reader = new BufferedReader(new FileReader(subFile));

        String line = reader.readLine();

        while ((line = reader.readLine()) != null) {

            DataLine submissionLine = new DataLine(DataSet.formatLine(line).split(DataSet.DATA_SET_DELIMITER));

            boolean annualFiling = submissionLine.get(SubFiles.FORM).equals(SubFiles.FORM_ANNUAL_AMENDED)
                    || submissionLine.get(SubFiles.FORM).equals(SubFiles.FORM_ANNUAL);

            boolean quarterlyFiling = submissionLine.get(SubFiles.FORM).equals(SubFiles.FORM_QUARTERLY);

            if (submissionLine.get(SubFiles.CIK).equals(cik) && (annualFiling || quarterlyFiling)) {
                DatasetReader filingReader = createFilingReader(q);
                return filingReader.generateFiling(submissionLine);
            }

        }

        reader.close();
        return null;

    }

    private static DatasetReader createFilingReader(Quarter q) throws IOException {
        return new DatasetReader(q.toDataSetFile(DataSet.FILE_NUM), q.toDataSetFile(DataSet.FILE_PRE), q.toDataSetFile(DataSet.FILE_TAG), q);
    }

}
