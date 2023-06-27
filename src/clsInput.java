import java.util.Scanner;

public class clsInput {
    Scanner oInput = new Scanner(System.in);

    Integer GetInteger(String sPrompt)
    {
        System.out.print(sPrompt );

        Integer iInputValue = Integer.MIN_VALUE;
        do {
            try {
                iInputValue = Integer.parseInt(oInput.nextLine());
            }
            catch (Exception ex) {
                System.out.println("Podaj poprawną wartość całkowitą:" );
                iInputValue = Integer.MIN_VALUE;
            }
        }  while(iInputValue == Integer.MIN_VALUE);
        System.out.println("" );
        return iInputValue;
}

    Double GetDouble(String sPrompt)
    {
        System.out.print(sPrompt );

        Double dInputValue = Double.MIN_VALUE;
        do {
            try {
                dInputValue = Double.parseDouble(oInput.nextLine());
            }
            catch (Exception ex) {
                System.out.println("Podaj poprawną wartość liczbową: " );
                dInputValue = Double.MIN_VALUE;
            }
        }  while(dInputValue == Double.MIN_VALUE);
        System.out.println("" );
        return dInputValue;
    }

    String GetString(String sPrompt, Integer iMinLength )
    {
        System.out.print(sPrompt );

        String sInputValue = "";
        do {
                sInputValue = oInput.nextLine();
                if (sInputValue.length() < iMinLength)
                {
                    System.out.println("Podaj poprawną wartość tekstową, nie krótszą niż  " + iMinLength + " znaków." );
                    sInputValue = "";
                }

            }  while(sInputValue.length() < iMinLength);
        System.out.println("" );

        return sInputValue;
    }


}
