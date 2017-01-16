/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataset;

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import paths.Paths;
import time.Quarter;

/**
 *
 * @author Fabian
 */
public class DataSet {

    public static final String FILE_NUM = "num.txt";
    public static final String FILE_PRE = "pre.txt";
    public static final String FILE_SUB = "sub.txt";
    public static final String FILE_TAG = "tag.txt";

    public static final String NO_DATA_INDICATOR = "ND";
    
    public static final String DATA_SET_DELIMITER = "\t";

    public static String formatLine(String dataSetLine) {

        if (dataSetLine.endsWith(DATA_SET_DELIMITER)) {
            dataSetLine = dataSetLine + NO_DATA_INDICATOR;
        }
        if (dataSetLine.startsWith(DATA_SET_DELIMITER)) {
            dataSetLine = NO_DATA_INDICATOR + dataSetLine;
        }

        while (Pattern.compile(DATA_SET_DELIMITER +"{2}").matcher(dataSetLine).find()) {
            dataSetLine = dataSetLine.replaceAll(DATA_SET_DELIMITER + "{2}", DATA_SET_DELIMITER + NO_DATA_INDICATOR + DATA_SET_DELIMITER);
        }

        return dataSetLine;
    }
    
    public static File[] getDataSetFolders(){
        File[] listFiles = new File(Paths.FOLDER_DATA_SETS).listFiles((File dir, String name) -> {
            return !name.startsWith(".");
        });
        return listFiles;
    }
    
    public static List<Quarter> getAvailibleQuarters(){
        
        File[] folders = new File(Paths.FOLDER_DATA_SETS).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        });
        List<Quarter> list = new LinkedList<>();
        
        for(File folder : folders){
            list.add(new Quarter(folder.getName()));
        }
        return list;
        
    }
    
     /**
     *
     * @return the latest dataset quarter that has been downloaded and stored in
     * the Paths.FOLDER_DATA_SETS folder
     */
    public static Quarter getLastQuarter() {
        File[] dataSetFolders = new File(Paths.FOLDER_DATA_SETS).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        });

        Quarter latestQuarter = new Quarter(dataSetFolders[0].getName());

        for (int i = 1; i < dataSetFolders.length; i++) {
            String dataSetFolderName = dataSetFolders[i].getName();

            Quarter quarter = new Quarter(dataSetFolderName);

            if (!quarter.isBefore(latestQuarter)) {
                latestQuarter = quarter;
            }

        }
        return latestQuarter;
    }

}
