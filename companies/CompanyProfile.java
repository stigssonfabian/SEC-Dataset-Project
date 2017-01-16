/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package companies;

import filings.Filing;
import filings.FilingSummary;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Fabian
 */
public class CompanyProfile {
    
    private List<Filing> quarterlyFilings;
    private List<Filing> annualFilings;
    
    private FilingSummary annualSummary;
    private FilingSummary quarterlySummary;

    public CompanyProfile(List<Filing> quarterlyFilings, List<Filing> annualFilings, FilingSummary annualSummary, FilingSummary quarterlySummary) {
        this.quarterlyFilings = quarterlyFilings;
        this.annualFilings = annualFilings;
        this.annualSummary = annualSummary;
        this.quarterlySummary = quarterlySummary;
    }

    public List<Filing> getQuarterlyFilings() {
        return quarterlyFilings;
    }

    public List<Filing> getAnnualFilings() {
        return annualFilings;
    }

    public FilingSummary getAnnualSummary() {
        return annualSummary;
    }

    public FilingSummary getQuarterlySummary() {
        return quarterlySummary;
    }
    
    
    
    
}
