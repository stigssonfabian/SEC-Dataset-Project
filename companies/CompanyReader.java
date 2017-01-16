/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package companies;

import filings.FilingSummary;
import internet.CompanyData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import x.FilingReader;

/**
 *
 * @author Fabian
 */
public class CompanyReader {

    public static List<SummaryFilings> readSummaryFilings(List<CompanyData> list) throws IOException {
        List<SummaryFilings> filings = new ArrayList<>();

        for (CompanyData companyData : list) {
            FilingSummary annual = FilingReader.readAnnualFilingSummary(companyData.getCik());
            FilingSummary quarterly = FilingReader.readQuarterlyFilingSummary(companyData.getCik());

            if (annual != null && quarterly != null) {
                filings.add(new SummaryFilings(companyData, annual, quarterly));
            }
        }
        return filings;

    }

    public static List<SummaryFilings> readSummaryFilings(int companies) throws IOException {

        List<String> allCompanyCiks = Companies.getAllCompanyCiks();

        List<SummaryFilings> list = new ArrayList<>();
        int i = 0;
        for (String cik : allCompanyCiks) {

            if (i > companies) {
                return list;
            }

            FilingSummary annual = FilingReader.readAnnualFilingSummary(cik);
            FilingSummary quarterly = FilingReader.readQuarterlyFilingSummary(cik);

            if (annual != null && quarterly != null) {
                list.add(new SummaryFilings(new CompanyData("", "", "", "", cik), annual, quarterly));
            }
            i++;

        }

        return list;

    }

}
