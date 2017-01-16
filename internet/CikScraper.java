/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internet;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Fabian
 */
public class CikScraper {

    public static final String url = "https://en.wikipedia.org/wiki/List_of_S%26P_500_companies";

    private static final int TICKER = 0;
    private static final int COMPANY_NAME = 1;
    private static final int INDUSTRY = 3;
    private static final int SUB_INDUSTRY = 4;
    private static final int CIK = 7;

    public static List<CompanyData> scrapeCiks() throws IOException {

        WebClient client = new WebClient();
        Page page = client.getPage(url);

        HtmlPage htmlpage = null;

        if (page.isHtmlPage()) {
            htmlpage = (HtmlPage) page;
        }

        DomNodeList<DomElement> elementsByTagName = htmlpage.getElementsByTagName("table");

        HtmlTable table = (HtmlTable) elementsByTagName.get(0); // the first table is of interest

        List<CompanyData> data = new LinkedList<>();

        for (int i = 0; i < table.getRowCount(); i++) {
            HtmlTableRow row = table.getRow(i);

            List<HtmlTableCell> cells = row.getCells();

            
            String cik = cells.get(CIK).asText();
            
            while(cik.startsWith("0")){
                cik = cik.substring(1);
            }
            
            
            data.add(new CompanyData(cells.get(TICKER).asText(),
                    cells.get(COMPANY_NAME).asText(),
                    cells.get(INDUSTRY).asText(),
                    cells.get(SUB_INDUSTRY).asText(),
                    cik));
            
            
        }

        client.close();

        return data;

    }

}
