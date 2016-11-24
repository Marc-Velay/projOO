import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;

public class Library implements ActionListener {

    String name;
    
    JTextArea testInput;
    JButton addDocBtn;
    JButton checkoutDocBtn;
    JButton createUserBtn;
    JButton loginBtn;

    JButton saveBook;

    JButton checkout;

    JButton createUser;

    JButton login;
    
    JFrame frame;
    JFrame newDocframe;
    JFrame checkoutFrame;
    JFrame createUserFrame;
    JFrame loginFrame;

    private static final String dbClassName = "org.sqlite.JDBC";
    private static final String LOANLIB = "jdbc:sqlite:loanLibrary.db";
    private static final String USERLIB = "jdbc:sqlite:userLibrary.db";
    private static final String DOCLIB = "jdbc:sqlite:documentLibrary.db";
    private Connection connLoan = null;
    private Connection connUser = null;
    private Connection connDoc = null;

    public Library(String name) throws ClassNotFoundException,SQLException{
        Properties p = new Properties();
        p.put("user","system");
        p.put("password","Wb7L2sryi!");
        String sql =null;
        try {
            Class.forName(dbClassName).newInstance();
            try {
                connLoan = DriverManager.getConnection(LOANLIB);
                Statement statement = connLoan.createStatement();
                sql = "CREATE TABLE loanLibrary "+
                " ( userID INTEGER, "+
                " docID INTEGER, "+
                " loanDate INTEGER, "+
                " loanEnd INTEGER, "+
                " loanReminderDate INTEGER, "+
                " overdue boolean, "+
                " fare float )";
                statement.executeUpdate(sql);
                statement.close();
            } catch (SQLException e) {
                    System.out.println("SQLException: " + e.getMessage());
                    System.out.println("SQLState: " + e.getSQLState());
                    System.out.println("VendorError: " + e.getErrorCode());
            }
            try {
                connUser = DriverManager.getConnection(USERLIB);
                Statement statement = connUser.createStatement();
                sql = " CREATE TABLE userLibrary " +
                " ( lastname VARCHAR(255), " + 
                " name VARCHAR(255), " +
                " address VARCHAR(255), " + 
                " inscriptiondate INTEGER, "+
                " renewaldate INTEGER, "+
                " nbLoanfinished INTEGER, "+
                " nbLoanoverdue INTEGER, "+
                " nbLoanopen INTEGER,"+ 
                " reductioncode INTEGER )";
                statement.executeUpdate(sql);
                statement.close();
            } catch (SQLException e) {
                    System.out.println("SQLException: " + e.getMessage());
                    System.out.println("SQLState: " + e.getSQLState());
                    System.out.println("VendorError: " + e.getErrorCode());
            }
            try {
                connDoc = DriverManager.getConnection(DOCLIB);
                Statement statement = connDoc.createStatement();
                sql = "CREATE TABLE documentLibrary "+
                "(code VARCHAR (255), "+
                "author VARCHAR (255), "+
                "title VARCHAR (255), "+
                "year INTEGER, "+
                "borrowable boolean, "+
                "loaned boolean, "+
                "nbLoans INTEGER)";
                statement.executeUpdate(sql);
                statement.close();
            } catch (SQLException e) {
                    System.out.println("SQLException: " + e.getMessage());
                    System.out.println("SQLState: " + e.getSQLState());
                    System.out.println("VendorError: " + e.getErrorCode());
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    Connection c = DriverManager.getConnection(LOANLIB,p);
    System.out.println("created first co");
    Connection c2 = DriverManager.getConnection(USERLIB,p);
    System.out.println("created second co");
    Connection c3 = DriverManager.getConnection(DOCLIB,p);
    System.out.println("created third co");

    System.out.println("It works! Access to DBs done");
    c.close();
    c2.close();
    c3.close();


        this.name = name;
        frame = new JFrame(this.name);
        frame.setMinimumSize(new Dimension(640,480));
        frame.setLayout(new BorderLayout());
        
        JPanel toolBar = new JPanel();
        toolBar.setMinimumSize(new Dimension(640, 90));

        testInput = new JTextArea(1, 20);
        JScrollPane scrollPane = new JScrollPane(testInput); 
        testInput.setEditable(true);

        addDocBtn = new JButton("Add a new document");
        addDocBtn.setMinimumSize(new Dimension(60, 50));
        addDocBtn.setActionCommand("addDoc");
        addDocBtn.addActionListener(this);

        checkoutDocBtn = new JButton("Checkout a document");
        checkoutDocBtn.setMinimumSize(new Dimension(60, 50));
        checkoutDocBtn.setActionCommand("chkoutDoc");
        checkoutDocBtn.addActionListener(this);

        createUserBtn = new JButton("Create a new user");
        createUserBtn.setMinimumSize(new Dimension(60, 50));
        createUserBtn.setActionCommand("createUserBtn");
        createUserBtn.addActionListener(this);

        loginBtn = new JButton("login");
        loginBtn.setMinimumSize(new Dimension(60, 50));
        loginBtn.setActionCommand("loginBtn");
        loginBtn.addActionListener(this);

        toolBar.add(testInput);
        toolBar.add(addDocBtn);
        toolBar.add(checkoutDocBtn);
        toolBar.add(createUserBtn);
        toolBar.add(loginBtn);

        frame.add(toolBar);
        System.out.println("created lib");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        switch(e.getActionCommand()) { 
            case "addDoc":                            
                        frame.setVisible(false);
                        createNewDoc();
                        //Document newDoc = new Document(frame);
                break; 
                
            case "chkoutDoc":                            
                        frame.setVisible(false);
                        checkoutDoc();
                        //Document newDoc = new Document(frame);
                break;

            case "createUserBtn":                            
                        frame.setVisible(false);
                        createUser();
                        //Document newDoc = new Document(frame);
                break;

            case "loginBtn":                            
                        frame.setVisible(false);
                        login();
                        //Document newDoc = new Document(frame);
                break;

            case "saveDoc":
                        newDocframe.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        System.out.println("created doc");
                break;

            case "checkout":
                        checkoutFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        System.out.println("checked out doc");
                break;

            case "createUser":
                        createUserFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        System.out.println("created user");
                break;

            case "login":
                        loginFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        System.out.println("login");
                break;
        } 
    }

    private void createNewDoc() {
        newDocframe = new JFrame("Document Creation");
        newDocframe.setMinimumSize(new Dimension(640,480));
        newDocframe.setLayout(new BorderLayout());

        JPanel getDocInfo = new JPanel();
        getDocInfo.setMinimumSize(new Dimension(640, 480));

        saveBook = new JButton("create document");
        saveBook.setMinimumSize(new Dimension(60, 50));
        saveBook.setActionCommand("saveDoc");
        saveBook.addActionListener(this);

        getDocInfo.add(saveBook);

        newDocframe.add(getDocInfo);
        newDocframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        newDocframe.pack();
        newDocframe.setVisible(true);

        newDocframe.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                frame.setVisible(true);
                frame.repaint();
                newDocframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }});
    }

    private void checkoutDoc() {
        checkoutFrame = new JFrame("Document Creation");
        checkoutFrame.setMinimumSize(new Dimension(640,480));
        checkoutFrame.setLayout(new BorderLayout());

        JPanel getDocInfo = new JPanel();
        getDocInfo.setMinimumSize(new Dimension(640, 480));

        checkout = new JButton("checkout document");
        checkout.setMinimumSize(new Dimension(60, 50));
        checkout.setActionCommand("checkout");
        checkout.addActionListener(this);

        getDocInfo.add(checkout);

        checkoutFrame.add(getDocInfo);
        checkoutFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        checkoutFrame.pack();
        checkoutFrame.setVisible(true);

        checkoutFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                frame.setVisible(true);
                frame.repaint();
                checkoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }});
    }

    public void createUser() {
        createUserFrame = new JFrame("Document Creation");
        createUserFrame.setMinimumSize(new Dimension(640,480));
        createUserFrame.setLayout(new BorderLayout());

        JPanel getDocInfo = new JPanel();
        getDocInfo.setMinimumSize(new Dimension(640, 480));

        createUser = new JButton("create account");
        createUser.setMinimumSize(new Dimension(60, 50));
        createUser.setActionCommand("createUser");
        createUser.addActionListener(this);

        getDocInfo.add(createUser);

        createUserFrame.add(getDocInfo);
        createUserFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        createUserFrame.pack();
        createUserFrame.setVisible(true);

        createUserFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                frame.setVisible(true);
                frame.repaint();
                createUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }});
    }

    public void login() {
        loginFrame = new JFrame("Document Creation");
        loginFrame.setMinimumSize(new Dimension(640,480));
        loginFrame.setLayout(new BorderLayout());

        JPanel getDocInfo = new JPanel();
        getDocInfo.setMinimumSize(new Dimension(640, 480));

        login = new JButton("Login");
        login.setMinimumSize(new Dimension(60, 50));
        login.setActionCommand("login");
        login.addActionListener(this);

        getDocInfo.add(login);

        loginFrame.add(getDocInfo);
        loginFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setVisible(true);

        loginFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                frame.setVisible(true);
                frame.repaint();
                loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }});
    }

    public static void main(String[] args){
        try {
            try {
            Library library = new Library("Library");
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage() + "exception class");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "exception sql");
        }
    }
}