import java.util.*;
import java.lang.*;

public class LoanCard {

    Date loanDate;
    Date loanEnd;
    Date loanReminderDate;
    boolean overdue;
    double fare;

    public LoanCard() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        this.loanDate = new Date();
        this.loanEnd = cal.getTime();
        this.loanReminderDate = cal.getTime();
        this.overdue = false;
        this.fare = false;
    }
}