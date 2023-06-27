import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class clsStore extends clsFirm {


    public ArrayList<clsArticle> lstArticle = new ArrayList<clsArticle>();//Lista artykułow zapisywana w pliku binarnym Arcticles.bin

    public ArrayList<clsServices> lstServices = new ArrayList<clsServices>();//Lista serwisów
    public ArrayList<clsCustomer> lstCustomer = new ArrayList<clsCustomer>();//Lista Klientów zapisywana w pliku binarnym Customers.bin

    clsCustomer oCustomer_Sale;    // Klient hurtowy aktualnej sprzedaży, ta zmienna potrzeba w funkcji sprzedaży
    public ArrayList<ifOferta> lstSprzeazy = new ArrayList<ifOferta>();  // Lista towarow sprzedazy detalicznej lub hurtowej. Mogą być w niej artykuły(klasa clsArticles) i serwisy (clsServices) bo jest typu interface.


    public clsStore(String Store_Name, String Store_NIP, String Store_Street,String Store_Postal, String Store_City , String Store_Country, String Store_Email,  String Store_WWW) {
        super.setFirm_name(Store_Name);//Dane sklepu
        super.setNIP(Store_NIP);
        super.setCity(Store_City);
        super.setCountry(Store_Country);
        super.setEmail(Store_Email);
        super.setWWW(Store_WWW);

        clsServices oNew = new clsServices("Dostawa","0000000000000",50.0,8.0);
        lstServices.add(oNew);
        oNew = new clsServices("Pakowanie","0000000000001",20.0,8.0);
        lstServices.add(oNew);
    }

    clsInput oInput = new clsInput();

    //      ___  ______ _______   ___   ___   _ _   __   __
    //     / _ \ | ___ \_   _\ \ / / | / / | | | |  \ \ / /
    //    / /_\ \| |_/ / | |  \ V /| |/ /| | | | |   \ V /
    //    |  _  ||    /  | |   \ / |    \| | | | |    \ /
    //    | | | || |\ \  | |   | | | |\  \ |_| | |____| |
    //    \_| |_/\_| \_| \_/   \_/ \_| \_/\___/\_____/\_/
    //

    public   void ArtikelAdd( ){
        clsArticle oArticleNew = new clsArticle();
        String sTemp = "";
        System.out.print("Podaj nazwę artkułu: ");

        oArticleNew.setArticle_Name( oInput.GetString("Podaj nazwę artykułu: ",5));
        oArticleNew.setEAN( oInput.GetString("13 znakowy kod EAN (European Article Number): ",13));
        oArticleNew.setPrice_Netto( oInput.GetDouble("Cena netto : "));
        oArticleNew.setVAT( oInput.GetDouble("stawka VAT: "));
        oArticleNew.setState( oInput.GetDouble("stan magazynu : "));
        oArticleNew.setMinimumState( oInput.GetDouble("stan alarmowy (wezwanie do zamówienia) : "));

        lstArticle.add(oArticleNew);
        Save_Artikles();
    }

    public   void ArticleEdit( ){


        String sEAN = oInput.GetString("Podaj EAN artykułu: ",13);
        clsArticle oArticleTemp;

        try{

            //List<clsArticle>  oList =  lstArticle.stream().filter(n -> n.getEAN().equals(sEAN) ).collect(Collectors.toList());

            Iterator<clsArticle> oIterator = lstArticle.iterator();
            while (oIterator.hasNext()) {

                oArticleTemp = oIterator.next();
                String tEAN_Temp =oArticleTemp.getEAN();
                if (tEAN_Temp.equals( sEAN))
                {
                    lstArticle.remove(oArticleTemp);

                    System.out.print("Podaj nazwę artkułu: ");
                    clsArticle oArticleNew = new clsArticle();

                    oArticleNew.setEAN(sEAN); // Nie zmienione !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                    oArticleNew.setArticle_Name( oInput.GetString("Podaj nazwę artykułu: ",5));
                    oArticleNew.setPrice_Netto( oInput.GetDouble("Cena netto : "));
                    oArticleNew.setVAT( oInput.GetDouble("stawka VAT: "));
                    oArticleNew.setState( oInput.GetDouble("stan magazynu : "));
                    oArticleNew.setMinimumState( oInput.GetDouble("stan alarmowy (wezwanie do zamówienia) : "));

                    lstArticle.add(oArticleNew);
                    Save_Artikles();


                }
            }


        }
        catch (Exception ex){
            System.out.println("Błąd 004: " + ex.getMessage());
        }

    }

    public   void ArticleStateChange( ){


        String sEAN = oInput.GetString("Podaj EAN artykułu do aktualizacji stanu: ",13);
        clsArticle oArticleTemp;

        try{

            //List<clsArticle>  oList =  lstArticle.stream().filter(n -> n.getEAN().equals(sEAN) ).collect(Collectors.toList());

            Iterator<clsArticle> oIterator = lstArticle.iterator();
            while (oIterator.hasNext()) {

                oArticleTemp = oIterator.next();
                String tEAN_Temp =oArticleTemp.getEAN();
                if (tEAN_Temp.equals( sEAN))
                {

                    oArticleTemp.setState( oArticleTemp.getState() +  oInput.GetDouble("Podaj ilość dostawy : "));
                    Save_Artikles();
                    return;

                }
            }


        }
        catch (Exception ex){
            System.out.println("Błąd 004: " + ex.getMessage());
        }

    }

    public   void ArticleDelete( ){


        String sEAN = oInput.GetString("Podaj EAN artykułu do skasowania: ",13);
        clsArticle oArticleTemp;

        try{

            List<clsArticle>  oList =  lstArticle.stream().filter(n -> n.getEAN() == sEAN) .collect(Collectors.toList());//

            Iterator<clsArticle> oIterator = lstArticle.iterator();
            while (oIterator.hasNext()) {

                oArticleTemp = oIterator.next();
                String tEAN_Temp =oArticleTemp.getEAN();
                if (tEAN_Temp.equals( sEAN))
                {
                    lstArticle.remove(oArticleTemp);
                    Save_Artikles();
                }
            }


        }
        catch (Exception ex){
            System.out.println("Błąd 004: " + ex.getMessage());
        }

    }



    public void  ArtiklesList(boolean bOnly_ToBeOrder){
        // Jeżeli parametr bOnly_toBeOrder jest true to wyypisze tylko te, których stan magazynowy jest mniejszy niż minimalny
        try {

            clsArticle oArtikelTemp;
            byte[] aBytes;
            String sTemp;

            Iterator<clsArticle> oIterator = lstArticle.iterator();
            System.out.println("");
          //System.out.println("123456789012345123456789.1234567890123451234.123456789.123456789.123456789.123456789.123456789.");
            System.out.println("Artykuł                  EAN                      netto       VAT    brutto      Stan   Minimum ");
            System.out.println("-----------------------------------------------------------------------------------------------");
            while (oIterator.hasNext()) {
                oArtikelTemp = oIterator.next();
                if (!bOnly_ToBeOrder || (oArtikelTemp.getMinimumState() > oArtikelTemp.getState())){
                    // Jeżeli parametr bOnly_toBeOrder jest true to wyypisze tylko te, których stan magazynowy jest mniejszy niż minimalny
                    oArtikelTemp.PrintData();
                }

            }
            System.out.println("-----------------------------------------------------------------------------------------------");
            System.out.println("");


        }
        catch (Exception ex){
            System.out.println("Błąd 003: " + ex.getMessage());
        }
    }


    public   void Save_Artikles( ){

    try {
        String sFileName = "C:\\JAVA\\SKLEP\\ARTIKLES.bin";
        ObjectOutputStream  oObjectOutputStream  = new ObjectOutputStream (new FileOutputStream(sFileName));
        clsArticle oArtikelTemp;
        byte[] aBytes;
        String sTemp;

        Iterator<clsArticle> oIterator = lstArticle.iterator();

            while (oIterator.hasNext()) {
                oArtikelTemp = oIterator.next();

                sTemp = oArtikelTemp.getArticle_Name();
                aBytes = sTemp.getBytes();
                oObjectOutputStream.writeInt(sTemp.length());
                oObjectOutputStream.write(aBytes);


                sTemp = oArtikelTemp.getEAN();
                aBytes = sTemp.getBytes();
                oObjectOutputStream.writeInt(sTemp.length());
                oObjectOutputStream.write(aBytes);


                oObjectOutputStream.writeDouble(oArtikelTemp.getPrice_Netto());
                oObjectOutputStream.writeDouble(oArtikelTemp.getVAT());
                oObjectOutputStream.writeDouble(oArtikelTemp.getState());
                oObjectOutputStream.writeDouble(oArtikelTemp.getMinimumState());

            }
            oObjectOutputStream.close();
            System.out.println("ARTIKLES.bin zapisane.");
        }
        catch (Exception ex){
            System.out.println("Błąd 003: " + ex.getMessage());
        }
    }




    public   void Read_Artikles( ){
        Integer iCounter = 0;
        try {
            Integer iLen = 0;

            String sFileName = "C:\\JAVA\\SKLEP\\ARTIKLES.bin";
            ObjectInputStream   oObjectInputStream   = new ObjectInputStream  (new FileInputStream(sFileName));

            lstArticle.clear();
            byte[]  aByte = new byte[0];

            while (true) {
                clsArticle oArticleNew = new clsArticle();

                // Article-Name
                iLen = oObjectInputStream.readInt();
                aByte = new byte[iLen];
                iLen = oObjectInputStream.read(aByte,0,iLen);
                sFileName =   new String(aByte, "UTF-8");
                oArticleNew.setArticle_Name(sFileName );

                // EAN
                iLen = oObjectInputStream.readInt();
                aByte = new byte[iLen];
                iLen = oObjectInputStream.read(aByte,0,iLen);
                sFileName =   new String(aByte, "UTF-8");
                oArticleNew.setEAN(sFileName );

                oArticleNew.setPrice_Netto(oObjectInputStream.readDouble() );
                oArticleNew.setVAT(oObjectInputStream.readDouble() );
                oArticleNew.setState(oObjectInputStream.readDouble() );
                oArticleNew.setMinimumState(oObjectInputStream.readDouble() );

                lstArticle.add( oArticleNew);
                iCounter ++;
            }

        }
        catch (Exception ex){
            System.out.println("Ilość wczytanych artykułów: " + iCounter );
        }

    }


    public   void Save_CSV( ){
        clsImportExport oIE = new clsImportExport(("C:\\JAVA\\SKLEP\\"));
        oIE.CSV_Export_ARTICLES(lstArticle);
        oIE.CSV_Export_CUSTOMERS(lstCustomer);
    }

    public   void Read_CSV( ){
        clsImportExport oIE = new clsImportExport(("C:\\JAVA\\SKLEP\\"));
        oIE.CSV_Import_ARTICLES(lstArticle);
        oIE.CSV_Import_CUSTOMERS(lstCustomer);
    }


    //      _   __ _     _____ _____ _   _ _____ _____
    //     | | / /| |   |_   _|  ___| \ | /  __ \_   _|
    //     | |/ / | |     | | | |__ |  \| | /  \/ | |
    //     |    \ | |     | | |  __|| . ` | |     | |
    //     | |\  \| |_____| |_| |___| |\  | \__/\_| |_
    //     \_| \_/\_____/\___/\____/\_| \_/\____/\___/
    //

    public   void CustomerAdd( ){
        clsCustomer oCustomer = new clsCustomer();
        String sTemp = "";
        System.out.print("Podaj dane klienta hurtowego: ");

        oCustomer.setFirm_name( oInput.GetString("Nazwa firmy: ",4));
        oCustomer.setNIP( oInput.GetString("NIP: ",6));
        oCustomer.setStreet( oInput.GetString("Ulica: ",5));
        oCustomer.setPostal_code( oInput.GetString("Kod pocztowy: ",6));
        oCustomer.setCity( oInput.GetString("Miejscowosc: ",2));
        oCustomer.setCountry( oInput.GetString("Kod państwa: ",1));
        oCustomer.setPhone_number( oInput.GetString("Telefon stacjonarny: ",7));
        oCustomer.setCell_phone_number( oInput.GetString("Telefon komórkowy: ",7));
        oCustomer.setEmail( oInput.GetString("Email: ",3));
        oCustomer.setWWW( oInput.GetString("WWW: ",3));
        oCustomer.setPocket( oInput.GetDouble("Zawartosc portfela: "));

        lstCustomer.add(oCustomer);
        Save_Customers();
    }

    public   void Save_Customers( ){

        try {
            String sFileName = "C:\\JAVA\\SKLEP\\CUSTOMERS.bin";
            ObjectOutputStream  oObjectOutputStream  = new ObjectOutputStream (new FileOutputStream(sFileName));
            clsCustomer oCustomerTemp;
            byte[] aBytes;
            String sTemp;

            Iterator<clsCustomer> oIterator = lstCustomer.iterator();

            while (oIterator.hasNext()) {
                oCustomerTemp = oIterator.next();

                sTemp = oCustomerTemp.getFirm_name();
                aBytes = sTemp.getBytes();
                oObjectOutputStream.writeInt(sTemp.length());
                oObjectOutputStream.write(aBytes);

                sTemp = oCustomerTemp.getNIP();
                aBytes = sTemp.getBytes();
                oObjectOutputStream.writeInt(sTemp.length());
                oObjectOutputStream.write(aBytes);

                sTemp = oCustomerTemp.getStreet();
                aBytes = sTemp.getBytes();
                oObjectOutputStream.writeInt(sTemp.length());
                oObjectOutputStream.write(aBytes);

                sTemp = oCustomerTemp.getPostal_code();
                aBytes = sTemp.getBytes();
                oObjectOutputStream.writeInt(sTemp.length());
                oObjectOutputStream.write(aBytes);

                sTemp = oCustomerTemp.getCity();
                aBytes = sTemp.getBytes();
                oObjectOutputStream.writeInt(sTemp.length());
                oObjectOutputStream.write(aBytes);

                sTemp = oCustomerTemp.getCountry();
                aBytes = sTemp.getBytes();
                oObjectOutputStream.writeInt(sTemp.length());
                oObjectOutputStream.write(aBytes);

                sTemp = oCustomerTemp.getPhone_number();
                aBytes = sTemp.getBytes();
                oObjectOutputStream.writeInt(sTemp.length());
                oObjectOutputStream.write(aBytes);

                sTemp = oCustomerTemp.getCell_phone_number();
                aBytes = sTemp.getBytes();
                oObjectOutputStream.writeInt(sTemp.length());
                oObjectOutputStream.write(aBytes);

                sTemp = oCustomerTemp.getEmail();
                aBytes = sTemp.getBytes();
                oObjectOutputStream.writeInt(sTemp.length());
                oObjectOutputStream.write(aBytes);

                sTemp = oCustomerTemp.getWWW();
                aBytes = sTemp.getBytes();
                oObjectOutputStream.writeInt(sTemp.length());
                oObjectOutputStream.write(aBytes);

                oObjectOutputStream.writeDouble(oCustomerTemp.getPocket());
            }
            oObjectOutputStream.close();
            System.out.println("CUSTOMERS.bin zapisane");
        }
        catch (Exception ex){
            System.out.println("Błąd 006: " + ex.getMessage());
        }
    }


    public   void Read_Customers( ){
        Integer iCounter = 0;
        try {
            Integer iLen = 0;

            String sFileName = "C:\\JAVA\\SKLEP\\CUSTOMERS.bin";
            ObjectInputStream   oObjectInputStream   = new ObjectInputStream  (new FileInputStream(sFileName));

            lstCustomer.clear();
            byte[]  aByte = new byte[0];

            while (true) {
                clsCustomer oCustomerNew = new clsCustomer();

                // Customer-Name
                iLen = oObjectInputStream.readInt();
                if (iLen < 50) {   // Ende der Datei
                    aByte = new byte[iLen];
                    iLen = oObjectInputStream.read(aByte, 0, iLen);
                    sFileName = new String(aByte, "UTF-8");
                    oCustomerNew.setFirm_name(sFileName);

                    // Customer-Street
                    iLen = oObjectInputStream.readInt();
                    aByte = new byte[iLen];
                    iLen = oObjectInputStream.read(aByte, 0, iLen);
                    sFileName = new String(aByte, "UTF-8");
                    oCustomerNew.setNIP(sFileName);

                    // Customer-Street
                    iLen = oObjectInputStream.readInt();
                    aByte = new byte[iLen];
                    iLen = oObjectInputStream.read(aByte, 0, iLen);
                    sFileName = new String(aByte, "UTF-8");
                    oCustomerNew.setStreet(sFileName);

                    // Customer-kod pocztowy
                    iLen = oObjectInputStream.readInt();
                    aByte = new byte[iLen];
                    iLen = oObjectInputStream.read(aByte, 0, iLen);
                    sFileName = new String(aByte, "UTF-8");
                    oCustomerNew.setPostal_code(sFileName);

                    // Customer-miejscowosc
                    iLen = oObjectInputStream.readInt();
                    aByte = new byte[iLen];
                    iLen = oObjectInputStream.read(aByte, 0, iLen);
                    sFileName = new String(aByte, "UTF-8");
                    oCustomerNew.setCity(sFileName);

                    // Customer-Name
                    iLen = oObjectInputStream.readInt();
                    aByte = new byte[iLen];
                    iLen = oObjectInputStream.read(aByte, 0, iLen);
                    sFileName = new String(aByte, "UTF-8");
                    oCustomerNew.setCountry(sFileName);

                    // Customer-Phone_number
                    iLen = oObjectInputStream.readInt();
                    aByte = new byte[iLen];
                    iLen = oObjectInputStream.read(aByte, 0, iLen);
                    sFileName = new String(aByte, "UTF-8");
                    oCustomerNew.setPhone_number(sFileName);

                    // Customer-Cell_phone_number
                    iLen = oObjectInputStream.readInt();
                    aByte = new byte[iLen];
                    iLen = oObjectInputStream.read(aByte, 0, iLen);
                    sFileName = new String(aByte, "UTF-8");
                    oCustomerNew.setCell_phone_number(sFileName);

                    // Customer-Email
                    iLen = oObjectInputStream.readInt();
                    aByte = new byte[iLen];
                    iLen = oObjectInputStream.read(aByte, 0, iLen);
                    sFileName = new String(aByte, "UTF-8");
                    oCustomerNew.setEmail(sFileName);

                    // Customer-WWW
                    iLen = oObjectInputStream.readInt();
                    aByte = new byte[iLen];
                    iLen = oObjectInputStream.read(aByte, 0, iLen);
                    sFileName = new String(aByte, "UTF-8");
                    oCustomerNew.setWWW(sFileName);


                    oCustomerNew.setPocket(oObjectInputStream.readDouble());


                    lstCustomer.add(oCustomerNew);
                    iCounter++;
                }
            }
            //System.out.println("Zapisano.");
        }
        catch (Exception ex){
            System.out.println("Ilość wczytanych klientów: " + iCounter );
        }

    }

    public   void CustomerEdit( ){


        String sNIP = oInput.GetString("Podaj NIP klienta: ",9);
        clsCustomer oCustomerTemp;

        try{

            //List<clsCustomer>  oList =  lstArticle.stream().filter(n -> n.getEAN().equals(sNIP) .collect(Collectors.toList());

            Iterator<clsCustomer> oIterator = lstCustomer.iterator();
            while (oIterator.hasNext()) {

                oCustomerTemp = oIterator.next();
                String tEAN_Temp =oCustomerTemp.getNIP();
                if (tEAN_Temp.equals( sNIP))
                {
                    lstCustomer.remove(oCustomerTemp);

                    System.out.print("Podaj nazwę artkułu: ");
                    clsCustomer oCustomerNew = new clsCustomer();

                    oCustomerNew.setNIP(sNIP); // pozostaje Nie zmienione !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                    oCustomerNew.setFirm_name( oInput.GetString("Nazwa firmy: ",4));
                    oCustomerNew.setStreet( oInput.GetString("Ulica: ",5));
                    oCustomerNew.setPostal_code( oInput.GetString("Kod pocztowy: ",6));
                    oCustomerNew.setCity( oInput.GetString("Miejscowosc: ",2));
                    oCustomerNew.setCountry( oInput.GetString("Kod państwa: ",1));
                    oCustomerNew.setPhone_number( oInput.GetString("Telefon stacjonarny: ",7));
                    oCustomerNew.setCell_phone_number( oInput.GetString("Telefon komórkowy: ",7));
                    oCustomerNew.setEmail( oInput.GetString("Email: ",3));
                    oCustomerNew.setWWW( oInput.GetString("WWW: ",3));
                    oCustomerNew.setPocket( oInput.GetDouble("Zawartosc portfela: "));

                    lstCustomer.add(oCustomerNew);
                    Save_Customers();


                }
            }


        }
        catch (Exception ex){
            System.out.println("Błąd 0014: " + ex.getMessage());
        }

    }

    public   void CustomerDelete( ){


        String sNIP = oInput.GetString("Podaj NIP klienta do skasowania: ",9);
        clsCustomer oCustomerTemp;

        try{

          //  List<clsCustomer>  oList =  lstCustomer.stream().filter(n -> n.getNIP().equals(sNIP)).collect(Collectors.toList());


            Iterator<clsCustomer> oIterator = lstCustomer.iterator();
            while (oIterator.hasNext()) {

                oCustomerTemp = oIterator.next();
                String tEAN_Temp =oCustomerTemp.getNIP();
                if (tEAN_Temp.equals( sNIP))
                {
                    lstCustomer.remove(oCustomerTemp);
                    Save_Customers();
                }
            }


        }
        catch (Exception ex){
            System.out.println("Błąd 0015: " + ex.getMessage());
        }

    }


    public void  CustomersList( boolean bOnly_In_debt){
        try {

            clsCustomer oCustomerTemp;
            byte[] aBytes;
            String sTemp;

            Iterator<clsCustomer> oIterator = lstCustomer.iterator();
            System.out.println("");
          //System.out.println("1234567890123456789.12345678901234.123456789.----12345678901234.124567.123456789012345.1234.12345678901234.12345678901234.12345678901234.12345678901234.");
            System.out.println("Klient              NIP               Portfel    ulica          KOD     Mijscowość     P 9   Tel.           Komórka        Email          WWW");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
            while (oIterator.hasNext()) {
                oCustomerTemp = oIterator.next();
                if (!bOnly_In_debt ||  oCustomerTemp.getPocket() < 0 ){
                    oCustomerTemp.PrintData();
                }

            }
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("");

        }
        catch (Exception ex){
            System.out.println("Błąd 003: " + ex.getMessage());
        }
    }


//      _________________ ______ ___________  ___   ______
//     /  ___| ___ \ ___ \___  /|  ___|  _  \/ _ \ |___  /
//     \ `--.| |_/ / |_/ /  / / | |__ | | | / /_\ \   / /
//      `--. \  __/|    /  / /  |  __|| | | |  _  |  / /
//     /\__/ / |   | |\ \./ /___| |___| |/ /| | | |./ /___
//     \____/\_|   \_| \_\_____/\____/|___/ \_| |_/\_____/
//

    public void  Sale( boolean bWhole){
        if (bWhole) {
            String sNIP = oInput.GetString("Podaj NIP klienta hurtowego: ",9);
            List<clsCustomer>  oList =  lstCustomer.stream().filter(n -> n.getNIP().equals(sNIP)).collect(Collectors.toList());// Tu sprawdzam czy NIP jest dostępny. Filtruje liste lstcustomers i z wynikow tworze liste olist
            if (oList.isEmpty())
            {
                System.out.println("Klient z tym  numerem nieznajduje sie w danych.");
                return;
            }
            oCustomer_Sale = oList.get(0);//biore pierwszy element, bo powinien tylko istniec 1 NIP
        }
        else {
            oCustomer_Sale = null;// Kiedy sprzedaż detaliczna
        }

        boolean bEndOfArtcleList = false;//Zmienna bedzie true jezeli z klawiatury wpisze się T przy pytaniu: Następny artykuł? T/N ?
         while  (!bEndOfArtcleList)
        {
            String sEAN = oInput.GetString("Podaj EAN towaru: ",9);
            if (sEAN.length() > 12 && sEAN.substring(0,12).equals( "000000000000"))
            {
                List<clsServices> oServicetemp = lstServices.stream().filter(n -> n.getEAN().equals(sEAN)).collect(Collectors.toList());//Sprawdzam czy podany numer jest w liscie serwisow
                if (oServicetemp.isEmpty()) {
                    System.out.println("Serwis nie odnaleziony.");
                } else {
                    Double dQuantity = oInput.GetDouble("Podaj ilość: ");
                    oServicetemp.get(0).setSale_State(dQuantity);
                    lstSprzeazy.add((oServicetemp.get(0)));
                    SaleList_View();
                }
            }
            else {
                List<clsArticle> oArticletemp = lstArticle.stream().filter(n -> n.getEAN().equals(sEAN)).collect(Collectors.toList());//Sprawdzam czy podany numer jest w liscie artykułow Jeżeli tak, to oArticeltemp bedzie jeden element z tym EAN'em
                if (oArticletemp.isEmpty()) {
                    System.out.println("Artykuł nie odnaleziony.");
                } else {
                    Double dQuantity = oInput.GetDouble("Podaj ilość: ");
                    if (oArticletemp.get(0).getState() - dQuantity < 0) {
                        System.out.println("Na magazynie  jest tylko " + oArticletemp.get(0).getState().toString());
                    } else {
                        oArticletemp.get(0).setSale_State(dQuantity);// Tu wpsiuje ilość artykułow do sprzedania
                        lstSprzeazy.add((oArticletemp.get(0)));//Tu dodaje artykuł do listy sprzedaży
                        SaleList_View();
                    }

                }
            }
            String sEND = oInput.GetString("Następny artykuł? T/N ? ",1);
            bEndOfArtcleList = (sEND.toLowerCase().equals("n"));

            SaleList_Update(bWhole);//Zakonczyło się wproawdzanie danych sprzedazy, tu aktualizuje baze danych artykułow i klientow

        }


    }


    private void SaleList_View(){



        try {
            if (lstSprzeazy == null) return;

            Iterator<ifOferta> oIterator = lstSprzeazy.iterator();
            Double dSum = (double) 0;
            ifOferta oItem;
            System.out.println("Artykuł                  EAN/Service Nr		      netto      VAT%     Ilość   Wartość");
            System.out.println("-------------------------------------------------------------------------------------");
            while (oIterator.hasNext()) {

                oItem = oIterator.next();
                oItem.PrintData_Sale();
                dSum += oItem.getPrice_Netto() * (oItem.getVAT() * 0.01 +1) * oItem.getSale_State();
            }
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("Wartość zakupu: " + FormatDouble(dSum ));
            System.out.println("-------------------------------------------------------------------------------------");
        }
        catch (Exception ex){
            System.out.println("Błąd 003: " + ex.getMessage());
        }
    }


    private void SaleList_Update(boolean bWhole){
        //Zakonczyło się wproawdzanie danych sprzedazy, tu aktualizuje baze danych artykułow i klientow

        try {
            if (lstSprzeazy == null) return;

            Iterator<ifOferta> oIterator = lstSprzeazy.iterator();
            Double dSum = (double) 0;//Suma wartości wszytskich artykułow
            ifOferta oItem;
            while (oIterator.hasNext()) {
                oItem = oIterator.next();
                dSum += oItem.getPrice_Netto() * (oItem.getVAT() * 0.01 +1) * oItem.getSale_State();
                Article_Update(bWhole,oItem.getEAN(),oItem.getSale_State());//Tutaj zdejmuje ilość sprzedaną ze stanu artykułow w lstArticel
            }

            Save_Artikles();  //Zapisuje artykuły w w pliku binarnym
            if (bWhole)
            {
                oCustomer_Sale.setPocket(oCustomer_Sale.getPocket() -dSum);// Jeżeli to sprzedaż to zdejmije wartość brutto towarów z portfela klienta
                Save_Customers();// Zapisuje dane klientów w pliku binarnym
            }

        }
        catch (Exception ex){
            System.out.println("Błąd 003: " + ex.getMessage());
        }
    }

    private void Article_Update(boolean bWhole, String sEAN, Double dSale_State){
        //Tutaj zdejmuje ilość sprzedaną ze stanu artykułow w lstArticel
        List<clsArticle>  oList =  lstArticle.stream().filter(n -> n.getEAN().equals(sEAN) ) .collect(Collectors.toList());
        if (oList.isEmpty()) return;//Jezeli jest to usługa(service) to nie ma stanu magazynowego
        oList.get(0).setState( oList.get(0).getState()-dSale_State);

    }

    private String FormatDouble(Double dValue){
        String tFormated =   String.format("%.2f", dValue);
        return String.format("%10s",tFormated);
    }




}
