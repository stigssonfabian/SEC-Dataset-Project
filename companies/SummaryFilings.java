/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package companies;

import filings.FilingSummary;
import internet.CompanyData;

/**
 *
 * @author Fabian
 */
public class SummaryFilings {
    
    private CompanyData data;
    
    private FilingSummary annual, quarterly;

    public SummaryFilings(CompanyData cd) {
        this.data = cd;
    }

    public SummaryFilings(CompanyData cik, FilingSummary annual, FilingSummary quarterly) {
        this.data = cik;
        this.annual = annual;
        this.quarterly = quarterly;
    }
    

    public CompanyData getCompanyData() {
        return data;
    }

    public void setCompanyData(CompanyData companyData) {
        this.data = companyData;
    }

    public FilingSummary getAnnual() {
        return annual;
    }

    public void setAnnual(FilingSummary annual) {
        this.annual = annual;
    }

    public FilingSummary getQuarterly() {
        return quarterly;
    }

    public void setQuarterly(FilingSummary quarterly) {
        this.quarterly = quarterly;
    }
    
    
    
}
