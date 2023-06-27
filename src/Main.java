//    _____ _   __ _      ___________
//   /  ___| | / /| |    |  ___| ___ \
//   \ `--.| |/ / | |    | |__ | |_/ /
//    `--. \    \ | |    |  __||  __/
//   /\__/ / |\  \| |____| |___| |
//   \____/\_| \_/\_____/\____/\_|
//    MICHAŁ SZUKAŁA


import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // moje prefiksy nazw zmiennych
    // i     = Integer
    // s     = String
    // o     = Object Instancja ...
    // cls   = Klasa
    // lst   = List
    // a     = Array





    public static void main(String[] args)
    {

        clsStore oStore = new clsStore( "Michal Szukala-STORE", "872-140-55-66","Kaweczynska 57a","39-200","Debica","PL","michal.szukala@interia.pl","www.michal-STORE.pl");
        oStore.Read_Artikles();
        oStore.Read_Customers();


        Scanner scanner = new Scanner(System.in);
        int wybor = 0;
        do{
            MENU();
            try{
                wybor = Integer.parseInt(scanner.nextLine());
                switch (wybor) {
                    case 1:
                        oStore.ArtikelAdd();
                        break;
                    case 2:
                        oStore.ArticleEdit();
                        break;
                    case 3:
                        oStore.ArticleDelete();
                        break;
                    case 4:
                        oStore.ArtiklesList(false);
                        break;
                    case 5:
                        oStore.ArtiklesList(true);
                        break;
                    case 6:
                        oStore.CustomerAdd();
                        break;
                    case 7:
                        oStore.CustomerEdit();
                        break;
                    case 8:
                        oStore.CustomerDelete();
                        break;
                    case 9:
                        oStore.CustomersList(false);
                        break;
                    case 10:
                        oStore.CustomersList(true);
                        break;
                    case 11:
                        oStore.Sale(true);
                        break;
                    case 12:
                        oStore.Sale(false);
                        break;
                    case 13:
                        oStore.Save_CSV();
                        break;
                    case 14:
                        oStore.Read_CSV();
                        break;
                    case 0:
                        System.out.println("Wyjdź z programu");
                        break;
                    default:
                        System.out.println("Podaj wybór jeszcze raz");
                }
            } catch (Exception e){
                System.out.println("Podaj wybór jeszcze raz");
            }

    } while(wybor != 0);



    }

    public static void MENU(){
        System.out.println("Wybierz operację:");
        System.out.println("------------------------------");
        System.out.println(" 1. Dodanie artykułu");
        System.out.println(" 2. Edycja artykułu");
        System.out.println(" 3. Kasowanie artkułu");
        System.out.println(" 4. Lista artykułów");
        System.out.println(" 5. Lista artykułów do zamówienia");
        System.out.println(" 6. Dodanie Klienta");
        System.out.println(" 7. Edycja Klienta");
        System.out.println(" 8. Kasowanie Klienta");
        System.out.println(" 9. Lista klientów");
        System.out.println("10. Lista klientów zadłuzonych");
        System.out.println("11. Sprzedaż hurtowa");
        System.out.println("12. Sprzedaż detaliczna");
        System.out.println("13. csv-Export");
        System.out.println("14. csv-Import");
        System.out.println("------------------------------");
        System.out.println(" 0. Wyjście z programu");

    }




}