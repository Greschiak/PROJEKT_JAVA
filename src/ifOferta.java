public interface ifOferta {

     String getArticle_Name();
     void setArticle_Name(String article_Name) ;
     Double getPrice_Netto() ;
     void setPrice_Netto(Double price_Netto);
     Double getVAT() ;
     void setVAT(Double VAT);

      String getEAN();
      Double getSale_State();
      void setSale_State(Double sale_State);
     void PrintData();
    void PrintData_Sale();
}
