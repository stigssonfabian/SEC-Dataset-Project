/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import data.DataLine;
import files.PreFiles;
import files.TagFiles;
import java.util.Arrays;

/**
 *
 * @author Fabian
 */
public class NumData {

    public static final int DATA_COUNT = 15;

    // num data fields
    public static final int ADSH = 0;
    public static final int TAG = 1;
    public static final int VERSION = 2;
    public static final int COREG = 3;
    public static final int DDATE = 4;
    public static final int QTRS = 5;
    public static final int UOM = 6;
    public static final int VALUE = 7;
    public static final int FOOTNOTE = 8;

    // pre data fields
    public static final int REPORT = 9;
    public static final int LINE = 10;
    public static final int STMT = 11;
    public static final int INPTH = 12;
    public static final int RFILE = 13;
    public static final int PLABEL = 14;

    
    /*
    // tag data fields
    public static final int CUSTOM = 15;
    public static final int ABSTRACT = 16;
    public static final int DATATYPE = 16;
    public static final int IORD = 18;
    public static final int CRDR = 19;
    public static final int TLABEL = 20;
    public static final int DOC = 21;

*/
    private DataLine dataLine;
    
    public NumData(String [] dataFromFiling){
        dataLine = new DataLine(dataFromFiling);
    }

    public NumData(DataLine numLine, DataLine preLine) {
        String[] data = new String[DATA_COUNT];
                
        for (int i = 0; i < numLine.dataCount(); i++) {
            data[i] = numLine.get(i);
        }
      
        int c = numLine.dataCount();

        for (int i = 0; i < PreFiles.DATA_COUNT_EXCLUDING_NEW_TAGS; i++) {
            switch (i) {
                case PreFiles.ADSH:
                    break;
                case PreFiles.TAG:
                    break;
                case PreFiles.VERSION:
                    break;
                default:
                    data[c] = preLine.get(i);
                    c++;
                    break;
            }
        }
        
        
//        for (int i = 0; i < TagFiles.DATA_COUNT; i++) {
//            switch (i) {
//                case TagFiles.TAG:
//                    break;
//                case PreFiles.VERSION:
//                    break;
//                default:
//                    data[c] = tagLine.get(i);
//                    c++;
//                    break;
//            }
//        }
        dataLine = new DataLine(data);

    }

    public String get(int index) {
        return dataLine.get(index);
    }

    public DataLine getData() {
        return dataLine;
    }

}
