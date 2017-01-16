/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataset;

import data.LatestNumData;
import files.NumFiles;
import files.PreFiles;
import files.SubFiles;
import files.TagFiles;
import data.DataLine;
import filings.Filing;
import data.NumData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import time.Quarter;

/**
 *
 * @author Fabian
 */
public class DatasetReader {

    private List<String> numLines, preLines /*,tagLines*/;

    private int numIndex = 0;
    private int preIndex = 0;

    private Quarter q;

    public DatasetReader(File numFile, File preFile, File tagFile, Quarter q) throws IOException {

        this.q = q;
        try {
            numLines = readLines(numFile);

            numLines.sort((String o1, String o2) -> {
                return o1.compareTo(o2);
            });

            preLines = readLines(preFile);

            preLines.sort((String o1, String o2) -> {
                return o1.compareTo(o2);
            });

//            tagLines = readLines(tagFile);
//            long t  = System.currentTimeMillis();
//            tagLines.sort((String o1, String o2) -> {
//                return (o1.compareTo(o2));
//            });
//            System.out.println("Sorting time = " + (System.currentTimeMillis() - t));
        } catch (IOException ex) {
            Logger.getLogger(DatasetReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Filing generateFiling(DataLine submissionLine) throws IOException {
        String adsh = submissionLine.get(SubFiles.ADSH);
        boolean annualFiling = submissionLine.get(SubFiles.FORM).equals(SubFiles.FORM_ANNUAL_AMENDED)
                || submissionLine.get(SubFiles.FORM).equals(SubFiles.FORM_ANNUAL);

//        long t0 = System.currentTimeMillis();
        List<DataLine> numData = readNumData(adsh, annualFiling);
//        System.out.println("Time for numDAta = " + (System.currentTimeMillis() - t0));

        if (numData.isEmpty()) {
            System.out.println("********NUMDATA EMPTY*****'");
            return null;
        }

        /*
        long t1 = System.currentTimeMillis();
        List<DataLine> tagData = readTagdataWithBinarySearch(numData);
        System.out.println("Time for tagData = " + (System.currentTimeMillis() - t1));
         */
//        long t2 = System.currentTimeMillis();
        List<DataLine> preData = readPreData(adsh);
//        System.out.println("Time for preData = " + (System.currentTimeMillis() - t2));

        if (preData.isEmpty()) {
            System.out.println("********PREDATA EMPTY*****'");
        }

        return createFiling(numData, preData, submissionLine, annualFiling, q);

    }

    /**
     * Creates the filing from the numData, pre data and tag data
     *
     * @param numData
     * @param preData
     * @param tagData
     * @param submissionLine
     * @param isAnnual
     * @param quarter
     * @return
     */
    private static Filing createFiling(List<DataLine> numData, List<DataLine> preData, DataLine submissionLine, boolean isAnnual, Quarter quarter) {

        List<NumData> numbers = new LinkedList<>();
        for (DataLine numDataLine : numData) {

            NumData compileNumData = compileNumData(numDataLine, preData);
            if (compileNumData != null) {
                numbers.add(compileNumData);
            }
        }
        String date = isAnnual ? ("" + quarter.getYear()) : quarter.toString();
        return new Filing(date, isAnnual, submissionLine, numbers);
    }

    private static NumData compileNumData(DataLine numDataLine, List<DataLine> preData) {

        DataLine preDataLine = null;

        for (DataLine dataLine : preData) {
            boolean versionok = dataLine.get(PreFiles.VERSION).equals(numDataLine.get(NumFiles.VERSION))
                    || dataLine.get(PreFiles.VERSION).equals(numDataLine.get(NumFiles.ADSH));

            if (dataLine.get(PreFiles.TAG).equals(numDataLine.get(NumFiles.TAG)) && versionok) {
                preDataLine = dataLine;
            }
        }

        try {
            return new NumData(numDataLine, preDataLine);
        } catch (Exception ex) {
            return null;
        }

    }

    private List<String> readLines(File file) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        List<String> lines = new LinkedList<>();

        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;

    }

    public List<DataLine> readPreData(String adsh) throws IOException {
        List<DataLine> preData = new LinkedList<>();
        boolean found = false;

        preLines.subList(0, preIndex).clear();
        preIndex = 0;

        int iterations = 0;

        for (String line : preLines) {

            boolean sameAdsh = line.split(DataSet.DATA_SET_DELIMITER)[PreFiles.ADSH].equals(adsh);

            if (!found && sameAdsh) {
                found = true;
            }
            if (found && sameAdsh) {
                preData.add(new DataLine(DataSet.formatLine(line).split(DataSet.DATA_SET_DELIMITER)));
            }

            if (found && !sameAdsh) {
                preIndex = iterations;
                return preData;
            }
            iterations++;
        }

        return preData;
    }

    /**
     * Reads the num data in the num.txt file associated with the submission id
     * adsh if there is no filing associated with the adsh the method returns
     * null
     *
     * @param adsh
     * @param isAnnualFiling
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<DataLine> readNumData(String adsh, boolean isAnnualFiling) throws FileNotFoundException, IOException {

        List<DataLine> numDataLines = new LinkedList<>();

        boolean found = false;

        numLines.subList(0, numIndex).clear();

        numIndex = 0;

        int iterations = 0;

        for (String line : numLines) {

            boolean sameAdsh = line.split(DataSet.DATA_SET_DELIMITER)[NumFiles.ADSH].equals(adsh);

            if (sameAdsh && !found) {
                found = true;
            }
            if (found && sameAdsh) {
                //   System.out.println(sameAdsh);
                DataLine dataLine = new DataLine(DataSet.formatLine(line).split(DataSet.DATA_SET_DELIMITER));
                if ((dataLine.get(NumFiles.QTRS).equals("1") || dataLine.get(NumFiles.QTRS).equals("0")) && !isAnnualFiling) {
                    numDataLines.add(dataLine);
                } else if (isAnnualFiling && (dataLine.get(NumFiles.QTRS).equals("4") || dataLine.get(NumFiles.QTRS).equals("0"))) {
                    numDataLines.add(dataLine);
                }

            }
            if (found && !sameAdsh) {
                if (numDataLines.isEmpty()) {
                    return numDataLines;
                }
                numIndex = iterations;

                return getLatestNumData(numDataLines, isAnnualFiling);
            }
            iterations++;
        }

        return numDataLines;

    }

    private List<DataLine> getLatestNumData(List<DataLine> numLines, boolean isAnnualFiling) {

        List<DataLine> numData = new LinkedList<>();

        Iterator<DataLine> it = numLines.iterator();

        DataLine numLine = it.next();

        while (it.hasNext()) {

            String tag = numLine.get(NumFiles.TAG);

            LatestNumData latest = new LatestNumData(isAnnualFiling);
            while (tag.equals(numLine.get(NumFiles.TAG))) {

                latest.addLatest(numLine);
                if (!it.hasNext()) {
                    break;
                }
                numLine = it.next();
            }
            numData.add(latest.getLatestDataLine());
        }

        return numData;

    }

//    public List<DataLine> readTagdataWithBinarySearch(List<DataLine> numData) throws IOException {
//
//        List<DataLine> tagData = new LinkedList<>();
//
//        for (DataLine numLine : numData) {
//            int index = binarySearch(tagLines, numLine.get(NumFiles.TAG), numLine.get(NumFiles.VERSION), numLine.get(NumFiles.ADSH));
//            if (index >= 0) {
//                DataLine tagLine = new DataLine(DataSet.formatLine(tagLines.get(index)).split(DataSet.DATA_SET_DELIMITER));
//                tagData.add(tagLine);
//            }else{
//                System.out.println("***********Broken binary search********  " + numLine.toDelimiteredString(", "));
//            }
//
//        }
//
//        return tagData;
//
//    }
    /**
     *
     * @param tagLines
     * @param tag
     * @param version
     * @param adsh
     * @return
     */
    public int binarySearch(List<String> tagLines, String tag, String version, String adsh) {

        int low = 0;
        int high = tagLines.size() - 1;
//       System.out.println("Tag =   " + tag + "  Version =   " + version);

        while (high >= low) {
            int middle = (low + high) / 2;
            String t = tagLines.get(middle).split(DataSet.DATA_SET_DELIMITER)[TagFiles.TAG];

            boolean custom = tagLines.get(middle).split(DataSet.DATA_SET_DELIMITER)[TagFiles.CUSTOM].equals("1");

//            System.out.println(t + "   " + tag);
            if (t.equals(tag)) {
                if (!custom) {
                    return findTagIndex(middle, tagLines, tag, version);
                } else {
                    return findTagIndex(middle, tagLines, tag, adsh);
                }
            }

            if (t.compareTo(tag) < 0) {
                low = middle + 1;
//                System.out.println("Low = "+ low + "  Hight = " + high);
            }

            if (t.compareTo(tag) > 0) {
                high = middle - 1;
//                System.out.println("Low = "+ low + "  Hight = " + high);
            }

        }
        return -1;

    }

    private int findTagIndex(int currentIndex, List<String> tagLines, String tagToMatch, String versionToMatch) {
        while (tagLines.get(currentIndex).split(DataSet.DATA_SET_DELIMITER)[TagFiles.TAG].equals(tagToMatch)) {

//            System.out.println("TAG ====   " + tagLines.get(currentIndex).split(DataSet.DATA_SET_DELIMITER)[TagFiles.TAG]
//                    + "     VERSTION ====   " + tagLines.get(currentIndex).split(DataSet.DATA_SET_DELIMITER)[TagFiles.VERSION]
//            );
            if (versionToMatch.equals(tagLines.get(currentIndex).split(DataSet.DATA_SET_DELIMITER)[TagFiles.VERSION])) {
//                System.out.println("RETURRRNINGS");

                return currentIndex;
            }
            currentIndex++;
        }

        currentIndex--;
        while (tagLines.get(currentIndex).split(DataSet.DATA_SET_DELIMITER)[TagFiles.TAG].equals(tagToMatch)) {
//            System.out.println("TAG ====   " + tagLines.get(currentIndex).split(DataSet.DATA_SET_DELIMITER)[TagFiles.TAG]
//                    + "     VERSTION ====   " + tagLines.get(currentIndex).split(DataSet.DATA_SET_DELIMITER)[TagFiles.VERSION]
//            );

            if (versionToMatch.equals(tagLines.get(currentIndex).split(DataSet.DATA_SET_DELIMITER)[TagFiles.VERSION])) {

//                System.out.println("RETURRRNINGS");
                return currentIndex;
            }
            currentIndex--;
        }

        return -1;
    }

}
