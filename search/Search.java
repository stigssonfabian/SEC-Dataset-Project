/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Fabian
 */
public class Search {

    public static int levenstheinThreshold = 5;

    public static List<CikPair> levenstheinSearchCik(String needle, List<CikPair>haystack){
        
        List<CikPair> result = new LinkedList<>();
        
        for(CikPair cp : haystack){
            int levenstheinDistance = levenstheinDistance(cp.getCompanyName(), needle);
            
            if(levenstheinDistance < levenstheinThreshold){
                result.add(cp);
            }
        }
       
        return result;
                
    }

    public static int levenstheinDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
       
        return costs[b.length()];
    }

}
