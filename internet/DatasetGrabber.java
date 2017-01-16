/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internet;

import time.CountabaleQuarter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import paths.Paths;

/**
 * Downloads and writes datasets to the specific paths in the Paths class
 * @author Fabian
 */
public class DatasetGrabber {

    // root link for download url
    private static final String rootLink = "https://www.sec.gov/data/financial-statements/";

    private static final String writePath = Paths.FOLDER_DATA_SETS;

    /**
     * Downloads and writes the specific dataset from the SEC webpage
     *
     * @param q
     * @throws IOException
     */
    public static void downloadAndWriteDataset(CountabaleQuarter q) throws IOException {

        File file = new File(writePath + File.separatorChar + q.getQuarter() + ".zip");

        Paths.ensurePath(file);

        FileUtils.copyURLToFile(new URL(rootLink + q.getQuarter() + ".zip"), file);


        try {
            ZipFile zip = new ZipFile(file);
            zip.extractAll(writePath + File.separatorChar + q.getQuarter());
            file.delete();
        } catch (ZipException ex) {
            System.err.println("Could not unzip file: " + file.getAbsolutePath());
        }

    }

    /**
     * Downloads all the availible datasets on the SEC webpage and writes them
     * to the passed output location
     */
    public static void downloadAndWriteAll() {

        CountabaleQuarter q = new CountabaleQuarter();

        try {

            while (true) {
                System.err.println(q.getQuarter());
                downloadAndWriteDataset(q);
                q.increment();
            }

        } catch (IOException ex) {
            System.out.println(q.getQuarter() + "  last q");
        }

    }
    
    /**
     * Downloads the dataset of the quarter passed and writes it to the specific
     * path in Paths
     * @param q 
     */
    public static void downloadAndWriteQuarter(CountabaleQuarter q) {
        try {
            downloadAndWriteDataset(q);
        } catch (IOException ex) {
            System.err.println("Could not download specific quarter: " + q.getQuarter());
        }
    }

}
