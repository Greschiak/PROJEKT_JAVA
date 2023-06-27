public class clsServices implements ifOferta {
    private String Service_Name;
    private String Service_EAN;
    private Double Price_Netto;
    private Double VAT;  // Podatek VAT na artykół (23%, 9%, 0%)

    private Double Sale_State;

    public clsServices(String service_Name, String service_EAN, Double price_Netto, Double VAT) {
        Service_Name = service_Name;
        Service_EAN = service_EAN;
        Price_Netto = price_Netto;
        this.VAT = VAT;
    }

    public String getArticle_Name() {
        return Service_Name;
    }

    public void setArticle_Name(String article_Name) {
        Service_Name = article_Name;
    }

     public String getEAN() {
        return Service_EAN;
    }

    public void setEAN(String EAN) {
        this.Service_EAN = EAN;
    }
    // Kod EAN, czyli 13 znakowy  Europejski Kod Towarowy (European Article Number)


    public Double getPrice_Netto() {
        return Price_Netto;
    }

    public void setPrice_Netto(Double price_Netto) {
        Price_Netto = price_Netto;
    }

    public Double getVAT() {
        return VAT;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }


    public Double getSale_State() {
        return Sale_State;
    }

    public void setSale_State(Double sale_State) {
        Sale_State = sale_State;
    }

    public void PrintData()
    {
        try {

            System.out.print(String.format("%-25s", Service_Name));   // -->  "          test");
            System.out.print("--------------------");   // -->  "          test");

            System.out.print(FormatDouble(Price_Netto));
            System.out.print(FormatDouble( VAT));
            System.out.print(FormatDouble( Price_Netto  *(1 + VAT * 0.01)));
            System.out.print("       ---");
            System.out.print("       ---");
            System.out.println("");


        }
        catch (Exception ex) {
            System.out.println("Błąd 002: " + ex.getMessage());
        }

    }

    public void PrintData_Sale()
    {
        try {

            System.out.print(String.format("%-25s", Service_Name));   // -->  "          test");
            System.out.print("--------------------");   // -->  "          test");
            System.out.print(FormatDouble(Price_Netto));
            System.out.print(FormatDouble( VAT));
            System.out.print(FormatDouble( Sale_State ));
            System.out.print(FormatDouble( Sale_State * Price_Netto *(1 + VAT * 0.01)));

            System.out.println("");


        }
        catch (Exception ex) {
            System.out.println("Błąd 002: " + ex.getMessage());
        }

    }


    private String FormatDouble(Double dValue){
        String tFormated =   String.format("%.2f", dValue);
        return String.format("%10s",tFormated);
    }


}
