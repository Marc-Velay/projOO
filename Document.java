import java.util.*;
import java.lang.*;

public class Document {
    String code;
    String author;
    String title;
    String year;
    boolean borrowable;
    boolean loaned;
    int nbLoans;

    public Document(String code, String author, String title, String year) {
        this.code = code;
        this.author = author;
        this.title = title;
        this.year = year;
        this.borrowable = true;
        this.loaned = false;
        this.nbLoans = 0;
    }
}