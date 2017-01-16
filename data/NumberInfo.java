/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Fabian
 */
public class NumberInfo {
    private String uom, tag, plabel;

    public NumberInfo() {
    }

    public NumberInfo(String uom, String tag, String plabel) {
        this.uom = uom;
        this.tag = tag;
        this.plabel = plabel;
    }

    
    
    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPlabel() {
        return plabel;
    }

    public void setPlabel(String plabel) {
        this.plabel = plabel;
    }

    @Override
    public String toString() {
        return tag + "\t" + plabel + "\t" + plabel;
    }
    
    
    
    
}
