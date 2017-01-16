/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numbers;

/**
 *
 * @author Fabian
 */
public class CommonNumberIds {

    public static final int REVENUE = 0;

    public static NumberId[] commonNumbers = new NumberId[]{
        new NumberId("Revenue", new String[]{
            "Revenues",
            "Revenue",
            "SalesRevenueNet",
            "SalesRevenueGoodsNet",
            "SalesRevenueServicesNet",
            "FoodAndBeverageRevenue",
            "HealthCareOrganizationRevenue",
            "OilAndGasRevenue",
            "RealEstateRevenueNet",
            "BrokerageCommissionsRevenue",
            "RefiningAndMarketingRevenue",
            "RegulatedAndUnregulatedOperatingRevenue",
            "InterestAndDividendIncomeOperating",
            "RevenueMineralSales",
            "ElectricUtilityRevenue",
            "AssetManagementFees1",
            "ExplorationAndProductionRevenue",
            "SalesAndOtherOperatingRevenueIncludingSalesBasedTaxes"
            
        }, new NumberParts[]{
            new NumberParts(
            "NoninterestIncome",
            "InterestAndDividendIncomeOperating"),
            new NumberParts(
            "InterestIncomeOperating",
            "NoninterestIncome"        
            )

        },
          new String[]{"total revenues"}
        
        ), /*
        new NumberId("Stocks Outstanding", new String[]{
            
        }),
        new NumberId("Net Income", new String[]{
            
        }),
        new NumberId("Operating Income", new String[]{
               
        }),
        new NumberId("Cash", new String[]{
               
        }),
        new NumberId("Long Term Debt", new String[]{
        }),
        new NumberId("Short Term Debt", new String[]{
        }),
        new NumberId("Total Liabillities", new String[]{
               
        }),
        new NumberId("Stockholder Equity", new String[]{
               
        }),
        
        new NumberId("Current Assets", new String[]{
        }),
        new NumberId("Total Assets", new String[]{
        }),
        new NumberId("Dividend", new String[]{
               
        }),
        new NumberId("Cost of Sales", new String[]{
               
        }),
        
        new NumberId("Earnings per Share", new String[]{
               
        }),
        
     */};

}
