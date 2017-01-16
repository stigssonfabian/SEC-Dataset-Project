/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package time;

import java.util.Objects;

/**
 *
 * @author Fabian
 */
public class FilingPeriod {
    
    private int ddate;
    private String period;

    public FilingPeriod() {
    }

    public FilingPeriod(int ddate, String period) {
        this.ddate = ddate;
        this.period = period;
    }

    public int getDdate() {
        return ddate;
    }

    public void setDdate(int ddate) {
        this.ddate = ddate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.ddate;
        hash = 17 * hash + Objects.hashCode(this.period);
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
        final FilingPeriod other = (FilingPeriod) obj;
        if (this.ddate != other.ddate) {
            return false;
        }
        if (!Objects.equals(this.period, other.period)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FilingPeriod{" + "ddate=" + ddate + ", period=" + period + '}';
    }
    
    
    
    
    

    
}
