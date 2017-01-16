/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

/**
 *
 * @author Fabian
 */
public class CikPair {
    
    private String cik, companyName;

    public CikPair() {
    }

    
    
    public CikPair(String cik, String companyName) {
        this.cik = cik;
        this.companyName = companyName;
    }

    public String getCik() {
        return cik;
    }

    public void setCik(String cik) {
        this.cik = cik;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "CikPair{" + "cik=" + cik + ", companyName=" + companyName + '}';
    }
    
    
    
    
}
