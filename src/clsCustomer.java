public class clsCustomer extends clsFirm {


    private enumState State;
    public Double Pocket;


    public enum enumState{
        ACTIVE,
        INACTIVE,
        BLOCKED
    }

    public enumState getState() {
        return State;
    }

    public void setState(enumState state) {
        State = state;
    }

    public Double getPocket() {
        return Pocket;
    }

    public void setPocket(Double pocket) {
        Pocket = pocket;
    }

    public void PrintData() {
        try {

            System.out.print(String.format("%-20s", super.getFirm_name()));
            System.out.print(String.format("%-15s", super.getNIP()));
            System.out.print(FormatDouble(getPocket()));
            System.out.print("    ");
            System.out.print(String.format("%-15s", super.getStreet()));
            System.out.print(String.format("%-8s", super.getPostal_code()));
            System.out.print(String.format("%-15s", super.getCity()));
            System.out.print(String.format("%-5s", super.getCountry()));
            System.out.print(String.format("%-15s", super.getPhone_number()));
            System.out.print(String.format("%-15s", super.getCell_phone_number()));
            System.out.print(String.format("%-15s", super.getEmail()));
            System.out.print(String.format("%-15s", super.getWWW()));

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
