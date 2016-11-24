import java.util.*;
import java.lang.*;

public class Client {
    String lastName;
    String name;
    String address;
    Date inscriptionDate;
    Date renewalDate;
    int nbLoanFinished;
    int nbLoanOverdue;
    int nbLoanOpen;
    int reductionCode;
    int userID;

    public Client(String lastName,  String name, String address) {
        this.lastName = lastName;
        this.name = name;
        this.address = address;
        this.inscriptionDate = new Date();
        this.renewalDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1); // to get previous year add -1
        this.renewalDate = cal.getTime();
        this.nbLoanFinished=0;
        this.nbLoanOpen=0;
        this.nbLoanOverdue=0;
        this.reductionCode=rand()%1000;
    }
}