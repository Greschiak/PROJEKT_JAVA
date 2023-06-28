import java.util.ArrayList;
import java.util.Iterator;
import java.io.PrintWriter;
import java.io.*;
import java.io.FileReader;
import java.util.Spliterator;


public class clsImportExport {
    private String sPath;

    public clsImportExport(String sPath) {
        this.sPath = sPath;
    }


    public void CSV_Export_ARTICLES(ArrayList<clsArticle> lst){
        try {
            String sCSV="";
            String tSeparator = ";";
            clsArticle oTemp;
            Iterator<clsArticle> oIterator = lst.iterator();
            while (oIterator.hasNext()) {

                oTemp = oIterator.next();
                sCSV += oTemp.getArticle_Name() + tSeparator;
                sCSV += oTemp.getEAN() + tSeparator;
                sCSV += oTemp.getPrice_Netto() + tSeparator;
                sCSV += oTemp.getVAT()+ tSeparator;
                sCSV += oTemp.getState() + tSeparator;
                sCSV += oTemp.getMinimumState() + tSeparator;
                sCSV +="\r\n";
            }

            PrintWriter out = new PrintWriter(sPath + "ARTICLES.csv");
            out.print(sCSV);
            out.flush();
            out.close();
            System.out.println("Artykuły wyeksportowane:" +  sPath + "ARTICLES.csv");
        }


        catch (Exception ex        ){
            System.out.println("Błąd 007: " + ex.getMessage());
        }

    }

    public void CSV_Export_CUSTOMERS(ArrayList<clsCustomer> lst) {
        try {
            String sCSV = "";
            String tSeparator = ";";
            clsCustomer oTemp;
            Iterator<clsCustomer> oIterator = lst.iterator();
            while (oIterator.hasNext()) {

                oTemp = oIterator.next();
                sCSV += oTemp.getFirm_name() + tSeparator;
                sCSV += oTemp.getNIP() + tSeparator;
                sCSV += oTemp.getCity() + tSeparator;
                sCSV += oTemp.getPostal_code() + tSeparator;
                sCSV += oTemp.getCountry() + tSeparator;
                sCSV += oTemp.getEmail() + tSeparator;
                sCSV += oTemp.getPhone_number() + tSeparator;
                sCSV += oTemp.getCell_phone_number() + tSeparator;
                sCSV += oTemp.getStreet() + tSeparator;
                sCSV += oTemp.getWWW() + tSeparator;
                sCSV += oTemp.getPocket() + tSeparator;

                sCSV += "\r\n";
            }
            PrintWriter out = new PrintWriter(sPath + "CUSTOMERS.csv");
            out.print(sCSV);
            out.flush();
            out.close();
            System.out.println("Klienci wyeksportowani:" + sPath + "CUSTOMERS.csv");
        } catch (Exception ex) {
            System.out.println("Błąd 008: " + ex.getMessage());
        }

    }

    public void CSV_Import_ARTICLES(ArrayList<clsArticle> lst){
        try {


            File oFile = new File(sPath + "ARTICLES.csv");

            BufferedReader obj = new BufferedReader(new FileReader(oFile));

            String oLine;
            lst.clear();
            while ((oLine = obj.readLine()) != null) {
                System.out.println(oLine);
                String[] aTemp = oLine.split(";", 7);
                if (aTemp.length > 5)
                {
                    clsArticle oTemp = new clsArticle();
                    oTemp.setArticle_Name( aTemp[0]);
                    oTemp.setEAN( aTemp[1]);
                    oTemp.setPrice_Netto( To_Double( aTemp[2]));
                    oTemp.setVAT( To_Double(aTemp[3]));
                    oTemp.setState(To_Double( aTemp[4]));
                    oTemp.setMinimumState( To_Double(aTemp[5]));
                    lst.add(oTemp);
                }
            }


            System.out.println("Artykuły zaimportowane :" +  sPath + "ARTICLES.csv\r\n\r\n");
        }


        catch (Exception ex        ){
            System.out.println("Błąd 007: " + ex.getMessage());
        }
    }


    public void CSV_Import_CUSTOMERS(ArrayList<clsCustomer> lst){
        try {


            File oFile = new File(sPath + "CUSTOMERS.csv");

            BufferedReader obj = new BufferedReader(new FileReader(oFile));

            String oLine;
            lst.clear();
            while ((oLine = obj.readLine()) != null) {
                System.out.println(oLine);
                String[] aTemp = oLine.split(";", 12);
                if (aTemp.length > 10)
                {
                    clsCustomer oTemp = new clsCustomer();

                    oTemp.setFirm_name(aTemp[0]);
                    oTemp.setNIP(aTemp[1]);
                    oTemp.setCity(aTemp[2]);
                    oTemp.setPostal_code(aTemp[3]);
                    oTemp.setCountry(aTemp[4]);
                    oTemp.setEmail(aTemp[5]);
                    oTemp.setPhone_number(aTemp[6]);
                    oTemp.setCell_phone_number(aTemp[7]);
                    oTemp.setStreet(aTemp[8]);
                    oTemp.setWWW(aTemp[9]);
                    oTemp.setPocket( To_Double(aTemp[10]));

                    lst.add(oTemp);
                }
            }


            System.out.println("Klienci zaimportowani :" +  sPath + "CUSTOMERS.csv\r\n\r\n");
        }


        catch (Exception ex        ){
            System.out.println("Błąd 007: " + ex.getMessage());
        }
    }

    Double To_Double(String tValue){
        try {
            return Double.parseDouble(tValue);
        }
        catch (Exception ex){
            return 0.0;
        }
    }



}
