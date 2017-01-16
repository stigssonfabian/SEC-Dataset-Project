/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secdatasetanalyzer;
import companies.CompanyProfile;
import data.Numbers;
import java.io.IOException;
import java.util.List;
import search.CompanySearch;
import time.FilingPeriod;


/**
 *
 * @author Fabian
 */
public class SecDatasetAnalyzer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        List<CompanyProfile> searchCompanies = CompanySearch.searchCompanies("APPLE");
        showCompany(searchCompanies.get(0));
        
    }
    
    
    private static void showCompany(CompanyProfile profile){
        System.out.println("*********" + profile.getQuarterlyFilings().get(0).getSubmissionLine().toDelimiteredString("\t") + " ************");
        String dateo = "";
        String del = "\t";
        for(FilingPeriod period : profile.getQuarterlySummary().getPeriods()){
            dateo += period.getPeriod() + del;
        }
        System.out.println(dateo.trim());
        
        for(Numbers num : profile.getQuarterlySummary().getNumbers()){
            String out = num.getInfo().getPlabel() + " (" + num.getInfo().getUom() + ")";
            for(int i = 0; i < num.length(); i++){
                out += del+ num.getValue(i);
            }
            System.out.println(out.trim());
        }
        
        
    }
    
    
    

}
