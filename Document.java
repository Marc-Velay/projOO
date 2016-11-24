import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Document implements ActionListener{
    String code;
    String author;
    String title;
    String year;
    boolean borrowable;
    boolean loaned;
    int nbLoans;

    public Document(String code, String author, String title, int year) {
        this.code = code;
        this.author = author;
        this.title = title;
        this.year = year;
        this.borrowable = true;
        this.loaned = false;
        this.nbLoans = 0;
        

        System.out.println("New document created");
    }
}