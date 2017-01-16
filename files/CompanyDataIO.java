/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import dataset.DataSet;
import internet.CikScraper;
import internet.CompanyData;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import paths.Paths;

/**
 * NEEDS IMPLEMENTATION
 *
 * @author Fabian
 */
public class CompanyDataIO {

    public static final String VALUE_DELIMITER = DataSet.DATA_SET_DELIMITER;

    private static final int INDEX_TICKER = 0;
    private static final int INDEX_NAME = 1;
    private static final int INDEX_INDUSTRY = 2;
    private static final int INDEX_SUB_INDUSTRY = 3;
    private static final int INDEX_CIK = 4;

    /**
     * Creates a company data io object which can be used to read
     * the company data objects derived from the S&P 500 page at wikipedia.
     * If the data is not downloaded locally on the machine it is downloaded,
     * when the object is created.
     */
    public CompanyDataIO() {
        if(!new File(Paths.FILE_S_AND_P_500).exists()){
            try {
                writeCompanyData(CikScraper.scrapeCiks());
            } catch (IOException ex) {
                Logger.getLogger(CompanyDataIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Scrapes the wikipedia page and downloads the data
     */
    public static void update() throws IOException{
        writeCompanyData(CikScraper.scrapeCiks());
    }
    
    /**
     * Reads the company data objects stored in the s&p files deriving from wikipedia
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public List<CompanyData> readCompanies() throws FileNotFoundException, IOException {
        List<CompanyData> data;
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(Paths.FILE_S_AND_P_500)))) {
            String line = reader.readLine();// headers,...
            data = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                String[] vals = line.split(VALUE_DELIMITER);
                data.add(new CompanyData(vals[INDEX_TICKER],
                        vals[INDEX_NAME],
                        vals[INDEX_INDUSTRY],
                        vals[INDEX_SUB_INDUSTRY],
                        vals[INDEX_CIK]));
            }
        }
        return data;

    }

    private static void writeCompanyData(List<CompanyData> data) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.FILE_S_AND_P_500));

        writer.append("Ticker");
        writer.append(VALUE_DELIMITER);
        writer.append("Name");
        writer.append(VALUE_DELIMITER);
        writer.append("Industry");
        writer.append(VALUE_DELIMITER);
        writer.append("Sub Industry");
        writer.append(VALUE_DELIMITER);
        writer.append("Cik");
        writer.newLine();
        
        

        for (int i = 0; i < data.size() - 1; i++) {
            CompanyData cd = data.get(i);
            writer.append(cd.getTicker() + VALUE_DELIMITER);
            writer.append(cd.getCompanyName() + VALUE_DELIMITER);
            writer.append(cd.getIndustry() + VALUE_DELIMITER);
            writer.append(cd.getSubIndustry() +VALUE_DELIMITER);
            writer.append(cd.getCik());
            writer.newLine();
        }
        CompanyData cd = data.get(data.size() - 1);
        writer.write(cd.getTicker() + VALUE_DELIMITER);
        writer.write(cd.getCompanyName() + VALUE_DELIMITER);
        writer.write(cd.getIndustry() + VALUE_DELIMITER);
        writer.write(cd.getSubIndustry() + VALUE_DELIMITER);
        writer.write(cd.getCik());

        writer.close();

    }

}
