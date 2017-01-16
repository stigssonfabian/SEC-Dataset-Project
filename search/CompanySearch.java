/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import companies.Companies;
import companies.CompanyProfile;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import filings.FilingReader;

/**
 *
 * @author Fabian
 */
public class CompanySearch {

    public static List<CompanyProfile> searchCompanies(String queryCompanyName) throws IOException {
        List<CikPair> pairs = Companies.searchForCiks(queryCompanyName);

        List<CompanyProfile> profiles = new LinkedList<>();

        
        for (CikPair cikNamePair : pairs) {
            
            // the cik does not exist
            try{
            CompanyProfile companyProfile = new CompanyProfile(FilingReader.readQuarterlyFilings(cikNamePair.getCik()),
                    FilingReader.readAnnualFilings(cikNamePair.getCik()),
                    FilingReader.readAnnualFilingSummary(cikNamePair.getCik()),
                    FilingReader.readQuarterlyFilingSummary(cikNamePair.getCik())
            );
            profiles.add(companyProfile);
            }catch(Exception ex){
                
            }
        }
        return profiles;
    }

}
