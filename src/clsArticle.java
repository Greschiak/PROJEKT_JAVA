
public class clsArticle implements ifOferta {
    private String Article_Name;
    private Double MinimumState;
    private Double State;

    private Double Sale_State;
    private Double SaLe_State;    private Double Price_Netto;
    private Double VAT;  // Podatek VAT na artykół (23%, 9%, 0%)
    private String EAN;

    public String getArticle_Name() {
        return Article_Name;
    }

    public void setArticle_Name(String article_Name) {
        Article_Name = article_Name;
    }

    public Double getMinimumState() {
        return MinimumState;
    }

    public void setMinimumState(Double minimumState) {
        MinimumState = minimumState;
    }

    public Double getState() {
        return State;
    }

    public void setState(Double state) {
        State = state;
    }

    public Double getSale_State() {
        return Sale_State;
    }

    public void setSale_State(Double sale_State) {
        Sale_State = sale_State;
    }
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

    public String getEAN() {
        return EAN;
    }
    public void setEAN(String EAN) {
        this.EAN = EAN;
    }
    // Kod EAN, czyli 13 znakowy  Europejski Kod Towarowy (European Article Number)

    public void PrintData()
    {
     try {

         System.out.print(String.format("%-25s", Article_Name));   // -->  "          test");
         System.out.print(String.format("%-20s", EAN));   // -->  "          test");

         System.out.print(FormatDouble(Price_Netto));
         System.out.print(FormatDouble( VAT));
         System.out.print(FormatDouble( Price_Netto  *(1 + VAT * 0.01)));
         System.out.print(FormatDouble( State ));
         System.out.print(FormatDouble( MinimumState ));
         System.out.println("");


     }
     catch (Exception ex) {
         System.out.println("Błąd 002: " + ex.getMessage());
     }

    }


    public void PrintData_Sale()
    {
        try {

            System.out.print(String.format("%-25s", Article_Name));   // -->  "          test");
            System.out.print(String.format("%-20s", EAN));   // -->  "          test");
            System.out.print(FormatDouble(Price_Netto));
            System.out.print(FormatDouble( VAT));
            System.out.print(FormatDouble( Sale_State ));
            System.out.print(FormatDouble( Sale_State * Price_Netto *(1 + VAT * 0.01)));//Wartość brutto sprzedaży

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
