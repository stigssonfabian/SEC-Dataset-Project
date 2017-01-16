/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Objects;

/**
 *
 * @author Fabian
 */
public class IndustryClassification {
    
    private String sicCode;
    private String industry;

    public IndustryClassification() {
    }

    public IndustryClassification(String sicCode, String industry) {
        this.sicCode = sicCode;
        this.industry = industry;
    }

    public String getSicCode() {
        return sicCode;
    }

    public void setSicCode(String sicCode) {
        this.sicCode = sicCode;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.sicCode);
        hash = 83 * hash + Objects.hashCode(this.industry);
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
        final IndustryClassification other = (IndustryClassification) obj;
        if (!Objects.equals(this.sicCode, other.sicCode)) {
            return false;
        }
        if (!Objects.equals(this.industry, other.industry)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IndustryClassification{" + "sicCode=" + sicCode + ", industry=" + industry + '}';
    }
    
    
    
    
    
}
