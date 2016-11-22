import java.util.*;
import java.lang.*;

public class ClientCategory {
    String clientCat;
    int maxLoans;
    double regFare;
    double fareCoef;
    double lengthCoef;
    boolean activeReductionCode;

    public ClientCategory(String clientCat) {
        this.clientCat = clientCat;
        maxLoans = 5;
        regFare = 1.0;
        fareCoef = 1.0;
        lengthCoef = 1.0;
        activeReductionCode = false;
    }
}