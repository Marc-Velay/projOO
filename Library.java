import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;

public class Library implements ActionListener {

    String name;
    Dimension winDim;
    JTextArea testInput;
    JButton addDocBtn;
    JButton checkoutDocBtn;
    JButton createUserBtn;

    JButton saveBook;

    JButton checkout;

    JButton createUser;
    
    JFrame frame;
    JFrame newDocframe;
    JFrame checkoutFrame;
    JFrame createUserFrame;
    JFrame loginFrame;

    private static final String dbClassName = "org.sqlite.JDBC";
    private static final String LIBDB = "jdbc:sqlite:library.db";
    private Connection connLib = null;
    private Statement connStat = null;

    public Library(String name) throws ClassNotFoundException,SQLException{
        winDim = new Dimension(1240,780);
        String sql =null;
        try {
            Class.forName(dbClassName).newInstance();
            connLib = DriverManager.getConnection(LIBDB);
            connLib.setAutoCommit(false);
            connStat = connLib.createStatement();
            System.out.println("Opened database successfully");
            try {
                System.out.println("querying");
                //ResultSet rs = connStat.executeQuery( "SELECT name FROM sqlite_master WHERE type='table' AND name='loanLibrary';" );
                //System.out.println(rs.getString("table") + "table found?");
                System.out.println("table found?");
                sql = "CREATE TABLE if not exists loanLibrary "+
                " ( userID INTEGER, "+
                " docID INTEGER, "+
                " loanDate INTEGER, "+
                " loanEnd INTEGER, "+
                " loanReminderDate INTEGER, "+
                " overdue INTEGER, "+
                " fare float )";
                connStat.executeUpdate(sql);
                System.out.println("table accessed?");
                //rs.close();
            } catch (SQLException e) {
                    System.out.println("SQLException: " + e.getMessage());
                    System.out.println("SQLState: " + e.getSQLState());
                    System.out.println("VendorError: " + e.getErrorCode());
            }
            try {
                sql = " CREATE TABLE if not exists userLibrary " +
                " ( lastname VARCHAR(255), " + 
                " name VARCHAR(255), " +
                " address VARCHAR(255), " + 
                " inscriptiondate INTEGER, "+
                " renewaldate INTEGER, "+
                " nbLoanfinished INTEGER, "+
                " nbLoanoverdue INTEGER, "+
                " nbLoanopen INTEGER,"+ 
                " reductioncode INTEGER )";
                connStat.executeUpdate(sql);
                System.out.println("table accessed?");
            } catch (SQLException e) {
                    System.out.println("SQLException: " + e.getMessage());
                    System.out.println("SQLState: " + e.getSQLState());
                    System.out.println("VendorError: " + e.getErrorCode());
            }
            try {
                sql = "CREATE TABLE if not exists documentLibrary "+
                "(code VARCHAR (255), "+
                "author VARCHAR (255), "+
                "title VARCHAR (255), "+
                "year INTEGER, "+
                "borrowable INTEGER, "+
                "loaned INTEGER, "+
                "nbLoans INTEGER)";
                connStat.executeUpdate(sql);
                System.out.println("table accessed?");
            } catch (SQLException e) {
                    System.out.println("SQLException: " + e.getMessage());
                    System.out.println("SQLState: " + e.getSQLState());
                    System.out.println("VendorError: " + e.getErrorCode());
            }


        connStat.close();
        connLib.commit();
        connLib.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        /*
        INSERT INTO userLibrary VALUES ('Chaffre', 'Thomas', 'ESIEA', 1480026056, 1480028056, 0,  0, 0, 1);
        INSERT INTO userLibrary VALUES ('Velay', 'Marc', 'ESIEA', 1480026056, 1480028056, 0,  0, 0, 1);
        INSERT INTO documentLibrary VALUES ('0003', 'Wilbur Smith', 'Assegai', 1997,  1, 0, 0);
        INSERT INTO documentLibrary VALUES ('0002', 'Wilbur Smith', 'Birds of prey', 1997,  1, 0, 0);
        INSERT INTO documentLibrary VALUES ('0001', 'Hobb', 'L assassin royal', 1995,  1, 0, 0);
        */


        /*connLib = DriverManager.getConnection(LIBDB);
        connLib.setAutoCommit(false);
        connStat = connLib.createStatement();
        ResultSet rs = connStat.executeQuery( "SELECT * FROM userLibrary;" );
        while ( rs.next() ) {
            System.out.println( "lastname = " + rs.getString("lastname") );
            System.out.println( "NAME = " +  rs.getString("name") );
            System.out.println( "address = " +  rs.getString("address") );
            System.out.println();
        }
        rs.close();
        connStat.close();
        connLib.close();*/

        this.name = name;

        frame = new JFrame(this.name);
        frame.setMinimumSize(winDim);
        frame.setMaximumSize(winDim);
        frame.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setMinimumSize(new Dimension(1240, 100));
        searchPanel.setPreferredSize(new Dimension(1240, 100));
        searchPanel.setBackground(Color.RED);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setPreferredSize(new Dimension(299, 780));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Search"));

        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        outputPanel.setPreferredSize(new Dimension(939, 780));
        outputPanel.setBorder(BorderFactory.createTitledBorder("Results"));

        /*testInput = new JTextArea(1, 20);
        JScrollPane scrollPane = new JScrollPane(testInput); 
        testInput.setEditable(true);*/


        JPanel toolBar = new JPanel();
        toolBar.setMinimumSize(new Dimension(1240, 60));
        toolBar.setPreferredSize(new Dimension(1240, 60));

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

        searchPanel.add(inputPanel);
        searchPanel.add(outputPanel);


        //toolBar.add(testInput);
        toolBar.add(addDocBtn);
        toolBar.add(checkoutDocBtn);
        toolBar.add(createUserBtn);

        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(searchPanel, BorderLayout.CENTER);
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

        } 
    }

    private void createNewDoc() {
        newDocframe = new JFrame("Document Creation");
        newDocframe.setMinimumSize(winDim);
        newDocframe.setLayout(new BorderLayout());

        JPanel getDocInfo = new JPanel();
        getDocInfo.setMinimumSize(winDim);

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
        checkoutFrame.setMinimumSize(winDim);
        checkoutFrame.setLayout(new BorderLayout());

        JPanel getDocInfo = new JPanel();
        getDocInfo.setMinimumSize(winDim);

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
        createUserFrame.setMinimumSize(winDim);
        createUserFrame.setLayout(new BorderLayout());

        JPanel getDocInfo = new JPanel();
        getDocInfo.setMinimumSize(winDim);

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