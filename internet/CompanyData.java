/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internet;

import java.util.Objects;

/**
 *
 * @author Fabian
 */
public class CompanyData {
    
    private String ticker;
    private String companyName;
    private String cik;
    private String industry;
    private String subIndustry;

    public CompanyData() {
    }

    public CompanyData(String ticker, String companyName, String industry, String subIndustry, String cik) {
        this.ticker = ticker;
        this.companyName = companyName;
        this.cik = cik;
        this.industry = industry;
        this.subIndustry = subIndustry;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCik() {
        return cik;
    }

    public void setCik(String cik) {
        this.cik = cik;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSubIndustry() {
        return subIndustry;
    }

    public void setSubIndustry(String subIndustry) {
        this.subIndustry = subIndustry;
    }

    @Override
    public String toString() {
        return "CompanyData{" + "ticker=" + ticker + ", companyName=" + companyName + ", cik=" + cik + ", industry=" + industry + ", subIndustry=" + subIndustry + '}';
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.ticker);
        hash = 11 * hash + Objects.hashCode(this.companyName);
        hash = 11 * hash + Objects.hashCode(this.cik);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompanyData other = (CompanyData) obj;
        if (!Objects.equals(this.ticker, other.ticker)) {
            return false;
        }
        if (!Objects.equals(this.companyName, other.companyName)) {
            return false;
        }
        if (!Objects.equals(this.cik, other.cik)) {
            return false;
        }
        return true;
    }
    
    

   
    
    
    
    
}
