import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;

public class Library implements ActionListener {

    String name;
    Dimension winDim;
    GroupLayout newDocLayout;
    JButton addDocBtn;
    JButton checkoutDocBtn;
    JButton createUserBtn;
    ButtonGroup docTypeGroup;

    JButton saveBook;
    JTextField authorNew;
    JTextField titleNew;
    JTextField yearNew;
    JTextField sizeNew;
    JTextField descNew;
    JLabel nbPageLabel;
    JLabel movieLengthLabel;
    JLabel legalLabel;
    JLabel catLabel;

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
                sql = "CREATE TABLE if not exists loanLibrary "+
                " ( userID INTEGER, "+
                " docID INTEGER, "+
                " loanDate INTEGER, "+
                " loanEnd INTEGER, "+
                " loanReminderDate INTEGER, "+
                " overdue INTEGER, "+
                " fare float )";
                connStat.executeUpdate(sql);
                System.out.println("table loanLibrary accessed");
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
                System.out.println("table userLibrary accessed?");
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
                "length INTEGER, "+     //pages or time, depending on book or video
                "description TEXT, "+   //legal mentions, music category
                "type CHAR (1), "+      //B book, V video, A audio
                "borrowable INTEGER, "+
                "loaned INTEGER, "+
                "nbLoans INTEGER)";
                connStat.executeUpdate(sql);
                System.out.println("table documentLibrary accessed");
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
            /************* new document frame *****************/
            case "saveDoc":
                        newDocframe.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        String author = authorNew.getText();
                        String title = titleNew.getText();
                        String desc = descNew.getText();
                        int year = 0;
                        String yearInput = yearNew.getText();
                        if(yearInput.matches("-?\\d+")) {
                            year = Integer.parseInt(yearInput);
                        }  
                        String sizeInput = sizeNew.getText();
                        int size = 0;
                        if(sizeInput.matches("-?\\d+")) {
                            size = Integer.parseInt(sizeInput);
                        }                        
                        String type = getSelectedButtonText(docTypeGroup);
                        try {
                            Class.forName(dbClassName).newInstance();
                            connLib = DriverManager.getConnection(LIBDB);
                            connLib.setAutoCommit(false);
                            connStat = connLib.createStatement();
                            try {
                                ResultSet rs = connStat.executeQuery("SELECT Count(*) AS nbRow FROM documentLibrary;");
                                int rows = rs.getInt("nbRow");
                                String sql = "INSERT INTO documentLibrary VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                                PreparedStatement ps = connLib.prepareStatement(sql);
                                ps.setString(1, type + rows);
                                ps.setString(2, author);
                                ps.setString(3, title);
                                ps.setInt(4, year);
                                ps.setInt(5, size);
                                ps.setString(6, desc);
                                ps.setString(7, type);
                                ps.setInt(8, 1);
                                ps.setInt(9, 0);
                                ps.setInt(10, 0);
                                ps.executeUpdate();

                            } catch (SQLException exc) {
                                    System.out.println("SQLException: " + exc.getMessage());
                                    System.out.println("SQLState: " + exc.getSQLState());
                                    System.out.println("VendorError: " + exc.getErrorCode());
                            }
                        connStat.close();
                        connLib.commit();
                        connLib.close();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                break;

            case "bookRdBtn":
                        nbPageLabel.setVisible(true);
                        sizeNew.setVisible(true);
                        movieLengthLabel.setVisible(false);
                        legalLabel.setVisible(false);
                        catLabel.setVisible(false);
                        descNew.setVisible(false);  
                        System.out.println("selected new book");
                break;
            case "videoRdBtn":
                        nbPageLabel.setVisible(false);
                        sizeNew.setVisible(true);
                        movieLengthLabel.setVisible(true);
                        legalLabel.setVisible(true);
                        catLabel.setVisible(false);
                        descNew.setVisible(true);
                        System.out.println("selected new video");
                break;
            case "audioRdBtn":
                        nbPageLabel.setVisible(false);
                        sizeNew.setVisible(false);
                        movieLengthLabel.setVisible(false);
                        legalLabel.setVisible(false);
                        catLabel.setVisible(true);
                        descNew.setVisible(true);
                        System.out.println("selected new audio");
                break;

            /************* new loan page **************************/    
            case "chkoutDoc":                            
                        frame.setVisible(false);
                        checkoutDoc();
                break;

            /************* new user frame *************************/
            case "createUserBtn":                            
                        frame.setVisible(false);
                        createUser();
                break;

            /************* library starting frame *****************/
            case "addDoc":                            
                        frame.setVisible(false);
                        createNewDoc();
                break; 

            case "checkout":
                        checkoutFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        System.out.println("checked out doc");
                break;

            case "createUser":
                        createUserFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        System.out.println("created user");
                break;

            /**************************************************/
        } 
    }

    private void createNewDoc() {
        newDocframe = new JFrame("Document Creation");
        newDocframe.setPreferredSize(winDim);
        newDocframe.setLayout(new BorderLayout());

        JPanel getDocInfo = new JPanel();
        getDocInfo.setPreferredSize(winDim);
        newDocLayout = new GroupLayout(getDocInfo);
        getDocInfo.setLayout(newDocLayout);

        /************************** first line: doc type selection ***************************/
        JLabel rdBtnLabel = new JLabel("Select your document type: ");

        JRadioButton bookRdBtn = new JRadioButton("Book");
        bookRdBtn.setActionCommand("bookRdBtn");
        bookRdBtn.addActionListener(this);
        bookRdBtn.setSelected(true);

        JRadioButton audioRdBtn = new JRadioButton("Audio");
        audioRdBtn.setActionCommand("audioRdBtn");
        audioRdBtn.addActionListener(this);

        JRadioButton videoRdBtn = new JRadioButton("Video");
        videoRdBtn.setActionCommand("videoRdBtn");
        videoRdBtn.addActionListener(this);
        
        docTypeGroup = new ButtonGroup();
        docTypeGroup.add(bookRdBtn);
        docTypeGroup.add(audioRdBtn);
        docTypeGroup.add(videoRdBtn);

        /************************** second line: get author name ***************************/

        JLabel authorLabel = new JLabel("* Author: ");
        authorNew = new JTextField(50);

        /************************** third line: doc type selection ***************************/

        JLabel docTitleLabel = new JLabel("* Title: ");
        titleNew = new JTextField(50);

        /************************** fourth line: doc type selection ***************************/

        JLabel yearLabel = new JLabel("* Year of production: ");
        yearNew = new JTextField(25);

        /************************** first line: doc type selection ***************************/

        nbPageLabel = new JLabel("* Number of pages: ");
        movieLengthLabel = new JLabel("* Length of movie: ");
        sizeNew = new JTextField(25);

        /************************** first line: doc type selection ***************************/

        legalLabel = new JLabel("* Legal Mentions: ");
        catLabel = new JLabel("* Category of music: ");
        descNew = new JTextField(25);

        nbPageLabel.setVisible(true);
        sizeNew.setVisible(true);
        movieLengthLabel.setVisible(false);
        legalLabel.setVisible(false);
        catLabel.setVisible(false);
        descNew.setVisible(false);

        saveBook = new JButton("create document");
        saveBook.setPreferredSize(new Dimension(60, 50));
        saveBook.setActionCommand("saveDoc");
        saveBook.addActionListener(this);

    
        newDocLayout.setAutoCreateGaps(true);
        newDocLayout.setAutoCreateContainerGaps(true);
        newDocLayout.setHonorsVisibility(true);
        newDocLayout.setHorizontalGroup(newDocLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING, false)
            .addGroup(newDocLayout.createSequentialGroup()
                .addComponent(rdBtnLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bookRdBtn, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(audioRdBtn, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(videoRdBtn, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(newDocLayout.createSequentialGroup()
                .addComponent(authorLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(authorNew, 0, GroupLayout.DEFAULT_SIZE, 300))
            .addGroup(newDocLayout.createSequentialGroup()
                .addComponent(docTitleLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(titleNew, 0, GroupLayout.DEFAULT_SIZE, 300))
            .addGroup(newDocLayout.createSequentialGroup()
                .addComponent(yearLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(yearNew, 0, GroupLayout.DEFAULT_SIZE, 150))
            .addGroup(newDocLayout.createSequentialGroup()
                .addComponent(nbPageLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(movieLengthLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sizeNew, 0, GroupLayout.DEFAULT_SIZE, 300))
            .addGroup(newDocLayout.createSequentialGroup()
                .addComponent(legalLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(catLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(descNew, 0, GroupLayout.DEFAULT_SIZE, 300))
            .addComponent(saveBook, 0, GroupLayout.DEFAULT_SIZE, 200));

        newDocLayout.setVerticalGroup(newDocLayout
            .createSequentialGroup()
                .addGroup(newDocLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(rdBtnLabel)
                    .addComponent(bookRdBtn)
                    .addComponent(audioRdBtn)
                    .addComponent(videoRdBtn))
                .addGroup(newDocLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(authorLabel)
                    .addComponent(authorNew))
                .addGroup(newDocLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(docTitleLabel)
                    .addComponent(titleNew))
                .addGroup(newDocLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(yearLabel)
                    .addComponent(yearNew))
                .addGroup(newDocLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nbPageLabel)
                    .addComponent(movieLengthLabel)
                    .addComponent(sizeNew))
                .addGroup(newDocLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(legalLabel)
                    .addComponent(catLabel)
                    .addComponent(descNew))
                .addComponent(saveBook));



        newDocframe.add(getDocInfo, BorderLayout.CENTER);
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

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                String btn = button.getText();
                System.out.println(btn);
                if(btn.equals("Book")) {
                    return "B";
                } else if(btn.equals("Video")) {
                    return "V";
                } else if(btn.equals("Audio")) {
                    return "A";
                }
            }
        }

        return null;
    }
}