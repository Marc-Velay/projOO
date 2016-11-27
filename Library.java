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
    GroupLayout newUserLayout;
    GroupLayout newLoanLayout;
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
    JButton searchDoc;
    JButton searchUser;
    JTextField authorField;
    JTextField nameField;
    JTextField userLNameField;
    JTextField userNameField;
    JLabel notFoundUser;
    JLabel notFoundDoc;
    JLabel foundDoc;
    JLabel foundUser;

    JButton createUser;
    JTextField lastnameNew;
    JTextField nameNew;
    JTextField addressNew;
    JTextField addressVilleNew;
    JTextField addressPaysNew;
    JTextField addressComplementNew;
    JTextField reductionNew;

    String loanUserID;
    String loanDocID;
    
    JFrame frame;
    JFrame newDocframe;
    JFrame checkoutFrame;
    JFrame createUserFrame;
    JFrame loginFrame;

    private static final String dbClassName = "org.sqlite.JDBC";
    private static final String LIBDB = "jdbc:sqlite:library.db";
    private static final String warning = "<html><body>(Closing the window will cancel operation<br>and return you to the home screen)</body></html>";
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
                " ( loanID VARCHAR (255),"+
                " userID VARCHAR (255), "+
                " docID VARCHAR (255), "+
                " loanDate DATE, "+
                " loanEnd DATE, "+
                " loanReminderDate DATE, "+
                " overdue INTEGER, "+
                " fare FLOAT )";
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
                " (userID VARCHAR (255),"+ 
                " lastname VARCHAR(255), " + 
                " name VARCHAR(255), " +
                " address VARCHAR(255), " + 
                " inscriptiondate DATE, "+
                " renewaldate DATE, "+
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

            case "searchDoc":
                        String docAuth = authorField.getText();
                        String docName = nameField.getText();
                        int nbRes =0;                        
                            try {
                            Class.forName(dbClassName).newInstance();
                            connLib = DriverManager.getConnection(LIBDB);
                            connLib.setAutoCommit(false);
                            connStat = connLib.createStatement();
                            try{
                                if(docAuth != null) {
                                    String req = "SELECT * FROM documentLibrary WHERE author LIKE '"+ docAuth +"%';";
                                    ResultSet rs2 = connStat.executeQuery(req);
                                    while(rs2.next()) nbRes++;
                                    System.out.println(nbRes);
                                    if(nbRes == 1) {
                                        rs2 = connStat.executeQuery(req);
                                        loanDocID = rs2.getString("code");
                                        foundDoc.setVisible(true);
                                        notFoundDoc.setVisible(false); 
                                    } else if(docName != null){
                                        nbRes =0;
                                        req = "SELECT * FROM documentLibrary WHERE title LIKE '"+ docName +"%';";
                                        rs2 = connStat.executeQuery(req);
                                        while(rs2.next()) nbRes++;
                                        System.out.println(nbRes);
                                        if(nbRes == 1) {
                                            rs2 = connStat.executeQuery(req);
                                            loanDocID = rs2.getString("code");
                                            foundDoc.setVisible(true);
                                            notFoundDoc.setVisible(false); 
                                        } else {
                                            nbRes =0;
                                            req = "SELECT * FROM documentLibrary WHERE title LIKE '"+ docName +"%' AND author LIKE '"+docAuth+"%';";
                                            rs2 = connStat.executeQuery(req);
                                            while(rs2.next()) nbRes++;
                                            System.out.println(nbRes);
                                            if(nbRes == 1) {
                                                rs2 = connStat.executeQuery(req);
                                                loanDocID = rs2.getString("code");
                                                foundDoc.setVisible(true);
                                                notFoundDoc.setVisible(false); 
                                            } else {
                                                foundDoc.setVisible(false);
                                                notFoundDoc.setVisible(true);  
                                                loanDocID = "";                                  
                                            }
                                        }
                                    }
                                } 
                                checkoutFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                                checkoutFrame.setVisible(false);
                                checkoutFrame.setVisible(true);
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
                        System.out.println("query completed");
                        
                break;

            case "searchUser":                            
                        String loanUserName = userNameField.getText();
                        String loanUserLName = userLNameField.getText();
                        nbRes =0;                        
                            try {
                            Class.forName(dbClassName).newInstance();
                            connLib = DriverManager.getConnection(LIBDB);
                            connLib.setAutoCommit(false);
                            connStat = connLib.createStatement();
                            try{
                                if(loanUserLName != null) {
                                    String requ = "SELECT * FROM userLibrary WHERE lastname LIKE '"+ loanUserLName +"%';";
                                    ResultSet rs3 = connStat.executeQuery(requ);
                                    while(rs3.next()) nbRes++;
                                    System.out.println(nbRes);
                                    if(nbRes == 1) {
                                        rs3 = connStat.executeQuery(requ);
                                        loanUserID = rs3.getString("userID");
                                        foundUser.setVisible(true);
                                        notFoundUser.setVisible(false); 
                                    } else if(loanUserName != null){
                                        nbRes =0;
                                        requ = "SELECT * FROM userLibrary WHERE name LIKE '"+ loanUserName +"%';";
                                        rs3 = connStat.executeQuery(requ);
                                        while(rs3.next()) nbRes++;
                                        System.out.println(nbRes);
                                        if(nbRes == 1) {
                                            rs3 = connStat.executeQuery(requ);
                                            loanUserID = rs3.getString("userID");
                                            foundUser.setVisible(true);
                                            notFoundUser.setVisible(false); 
                                        } else {
                                            nbRes =0;
                                            requ = "SELECT * FROM userLibrary WHERE lastname LIKE '"+ loanUserLName +"%' AND name LIKE '"+loanUserName+"%';";
                                            rs3 = connStat.executeQuery(requ);
                                            while(rs3.next()) nbRes++;
                                            System.out.println(nbRes);
                                            if(nbRes == 1) {
                                                rs3 = connStat.executeQuery(requ);
                                                loanUserID = rs3.getString("userID");
                                                foundUser.setVisible(true);
                                                notFoundUser.setVisible(false); 
                                            } else {
                                                loanUserID = "";
                                                foundUser.setVisible(false);
                                                notFoundUser.setVisible(true);                                    
                                            }
                                        }
                                    }
                                } 
                                checkoutFrame.setVisible(true);
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
                        System.out.println("query completed");
                break; 

            case "checkout":
                        checkoutFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        if(loanUserID != null && loanDocID != null) {
                            String userLName = userLNameField.getText();
                            String userName = userNameField.getText();
                            try {
                                Class.forName(dbClassName).newInstance();
                                connLib = DriverManager.getConnection(LIBDB);
                                connLib.setAutoCommit(false);
                                connStat = connLib.createStatement();
                                try{                                
                                    String loanReq = "INSERT INTO loanLibrary VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                                    ResultSet rs4 = connStat.executeQuery("SELECT Count(*) AS nbRow FROM loanLibrary;");
                                    int loans = rs4.getInt("nbRow");
                                    Calendar cal1 = Calendar.getInstance();
                                    java.util.Date loanDate = cal1.getTime();
                                    cal1.add(Calendar.WEEK_OF_MONTH, 2);
                                    java.util.Date loanReminder = cal1.getTime();
                                    cal1.add(Calendar.WEEK_OF_MONTH, 2);
                                    java.util.Date loanDue = cal1.getTime();
                                    PreparedStatement ps2 = connLib.prepareStatement(loanReq);
                                    ps2.setString(1, Integer.toHexString(loans));
                                    ps2.setString(2, loanUserID);
                                    ps2.setString(3, loanDocID);
                                    ps2.setDate(4, new java.sql.Date(loanDate.getTime()));
                                    ps2.setDate(5, new java.sql.Date(loanReminder.getTime()));
                                    ps2.setDate(6, new java.sql.Date(loanDue.getTime()));
                                    ps2.setInt(7, 0);
                                    ps2.setFloat(8, (float)1.5);
                                    ps2.executeUpdate();
                                                                
                                } catch(SQLException exce) {
                                    System.out.println("SQLException: " + exce.getMessage());
                                    System.out.println("SQLState: " + exce.getSQLState());
                                    System.out.println("VendorError: " + exce.getErrorCode());
                                }
                                connStat.close();
                                connLib.commit();
                                connLib.close();
                            } catch(Exception excep) {
                                System.out.println(excep);
                            }
                            System.out.println("checked out doc");
                        }
                break;

            case "createUser":
                        createUserFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        String lastname = lastnameNew.getText();
                        String name = nameNew.getText();
                        String address = addressNew.getText();
                        address += ", " +addressVilleNew.getText();
                        address += ", " +addressPaysNew.getText();
                        if(addressComplementNew.getText().matches(""));
                        else address += ", " +addressComplementNew.getText();                        
                        String reduc = reductionNew.getText();

                        try {
                            Class.forName(dbClassName).newInstance();
                            connLib = DriverManager.getConnection(LIBDB);
                            connLib.setAutoCommit(false);
                            connStat = connLib.createStatement();
                            try {
                                ResultSet rs1 = connStat.executeQuery("SELECT Count(*) AS nbRow FROM userLibrary;");
                                int rowsU = rs1.getInt("nbRow");
                                Calendar cal = Calendar.getInstance();
                                java.util.Date today = cal.getTime();
                                cal.add(Calendar.YEAR, 1);
                                java.util.Date renewaldate = cal.getTime();
                                String sql = "INSERT INTO userLibrary VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                                PreparedStatement ps = connLib.prepareStatement(sql);
                                ps.setString(1, Integer.toHexString(rowsU));
                                ps.setString(2, lastname);
                                ps.setString(3, name);
                                ps.setString(4, address);
                                ps.setDate(5, new java.sql.Date(today.getTime()));
                                ps.setDate(6, new java.sql.Date(renewaldate.getTime()));
                                ps.setInt(7, 0);
                                ps.setInt(8, 0);
                                ps.setInt(9, 0);
                                ps.setString(10, reduc);
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
        JLabel docWarning = new JLabel(warning);

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
            .addComponent(saveBook, 0, GroupLayout.DEFAULT_SIZE, 200)
            .addComponent(docWarning, 0, GroupLayout.DEFAULT_SIZE, 300));

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
                .addComponent(saveBook)
                .addComponent(docWarning));



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

        JPanel getLoanInfo = new JPanel();
        getLoanInfo.setPreferredSize(winDim);
        newLoanLayout = new GroupLayout(getLoanInfo);
        getLoanInfo.setLayout(newLoanLayout);

        JLabel docAuthor = new JLabel("Author of the document: ");
        JLabel docName = new JLabel("Name of your document: ");
        JLabel userLName = new JLabel("Enter the user's lastname: ");
        JLabel userName = new JLabel(" Enter the user name: ");
        foundDoc = new JLabel("Found! ");
        foundDoc.setVisible(false);
        notFoundDoc = new JLabel(" Please be more precise");
        notFoundDoc.setVisible(false);
        foundUser = new JLabel("Found! ");
        foundUser.setVisible(false);
        notFoundUser = new JLabel(" Please be more precise");
        notFoundUser.setVisible(false);
        JLabel loanWarning = new JLabel(warning);

        searchDoc = new JButton("search for doc");
        searchDoc.setPreferredSize(new Dimension(60, 300));
        searchDoc.setActionCommand("searchDoc");
        searchDoc.addActionListener(this);

        searchUser = new JButton("Search for user");
        searchUser.setPreferredSize(new Dimension(60, 300));
        searchUser.setActionCommand("searchUser");
        searchUser.addActionListener(this);

        authorField = new JTextField(30);
        nameField = new JTextField(30);
        userLNameField = new JTextField(30);
        userNameField = new JTextField(30);

        checkout = new JButton("checkout document");
        checkout.setPreferredSize(new Dimension(60, 50));
        checkout.setActionCommand("checkout");
        checkout.addActionListener(this);

        newLoanLayout.setAutoCreateGaps(true);
        newLoanLayout.setAutoCreateContainerGaps(true);
        newLoanLayout.setHonorsVisibility(true);
        newLoanLayout.setHorizontalGroup(newLoanLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING, false)
            .addGroup(newLoanLayout.createSequentialGroup()
                .addComponent(docAuthor, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(authorField, 0, GroupLayout.DEFAULT_SIZE, 200))
            .addGroup(newLoanLayout.createSequentialGroup()
                .addComponent(docName, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nameField, 0, GroupLayout.DEFAULT_SIZE, 200))
            .addGroup(newLoanLayout.createSequentialGroup()
                .addComponent(searchDoc, 200, GroupLayout.DEFAULT_SIZE, 200)
                .addComponent(foundDoc, 0, GroupLayout.DEFAULT_SIZE, 200)
                .addComponent(notFoundDoc, 0, GroupLayout.DEFAULT_SIZE, 200))
            .addGroup(newLoanLayout.createSequentialGroup()
                .addComponent(userLName, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(userLNameField, 0, GroupLayout.DEFAULT_SIZE, 200))
            .addGroup(newLoanLayout.createSequentialGroup()
                .addComponent(userName, 0, GroupLayout.DEFAULT_SIZE, 200)  
                .addComponent(userNameField, 0, GroupLayout.DEFAULT_SIZE, 200))
            .addGroup(newLoanLayout.createSequentialGroup()
                .addComponent(searchUser, 200, GroupLayout.DEFAULT_SIZE, 200)
                .addComponent(foundUser, 0, GroupLayout.DEFAULT_SIZE, 200)
                .addComponent(notFoundUser, 0, GroupLayout.DEFAULT_SIZE, 200))         
            .addComponent(checkout, 0, GroupLayout.DEFAULT_SIZE, 200)         
            .addComponent(loanWarning, 0, GroupLayout.DEFAULT_SIZE, 300));

        newLoanLayout.setVerticalGroup(newLoanLayout
            .createSequentialGroup()
                .addGroup(newLoanLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(docAuthor)
                    .addComponent(authorField))
                .addGroup(newLoanLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(docName)
                    .addComponent(nameField))
                .addGroup(newLoanLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(searchDoc)
                    .addComponent(foundDoc)
                    .addComponent(notFoundDoc))
                .addGroup(newLoanLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(userLName)
                    .addComponent(userLNameField))
                .addGroup(newLoanLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(userName)
                    .addComponent(userNameField))
                .addGroup(newLoanLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(searchUser)
                    .addComponent(foundUser)
                    .addComponent(notFoundUser))
                .addComponent(checkout)
                .addComponent(loanWarning));


        checkoutFrame.add(getLoanInfo, BorderLayout.CENTER);
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

        JPanel getUserInfo = new JPanel();
        getUserInfo.setPreferredSize(winDim);
        newUserLayout = new GroupLayout(getUserInfo);
        getUserInfo.setLayout(newUserLayout);

        JLabel lastnameLabel = new JLabel("* Last name: ");
        JLabel nameLabel = new JLabel("* Name: ");
        JLabel addressLabel = new JLabel("* Address: ");
        JLabel addressLabel1 = new JLabel("* Ville: ");
        JLabel addressLabel2 = new JLabel("* Pays: ");
        JLabel addressLabel3 = new JLabel("Complement: ");
        JLabel reductionLabel = new JLabel("Reduction code: ");
        JLabel userWarning = new JLabel(warning);

        lastnameNew = new JTextField(30);
        nameNew = new JTextField(30);
        addressNew = new JTextField(30);
        addressVilleNew = new JTextField(30);
        addressPaysNew = new JTextField(30);
        addressComplementNew = new JTextField(30);
        reductionNew = new JTextField(30);

        createUser = new JButton("create account");
        createUser.setPreferredSize(new Dimension(60, 50));
        createUser.setActionCommand("createUser");
        createUser.addActionListener(this);

        newUserLayout.setAutoCreateGaps(true);
        newUserLayout.setAutoCreateContainerGaps(true);
        newUserLayout.setHonorsVisibility(true);
        newUserLayout.setHorizontalGroup(newUserLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING, false)
            .addGroup(newUserLayout.createSequentialGroup()
                .addComponent(lastnameLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lastnameNew, 0, GroupLayout.DEFAULT_SIZE, 200))
            .addGroup(newUserLayout.createSequentialGroup()
                .addComponent(nameLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nameNew, 0, GroupLayout.DEFAULT_SIZE, 200))
            .addGroup(newUserLayout.createSequentialGroup()
                .addComponent(addressLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addressNew, 0, GroupLayout.DEFAULT_SIZE, 200))
            .addGroup(newUserLayout.createSequentialGroup()
                .addPreferredGap(addressNew, addressLabel1, LayoutStyle.ComponentPlacement.INDENT)
                .addComponent(addressLabel1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addressVilleNew, 0, GroupLayout.DEFAULT_SIZE, 200))
            .addGroup(newUserLayout.createSequentialGroup()
                .addPreferredGap(addressNew, addressLabel2, LayoutStyle.ComponentPlacement.INDENT)
                .addComponent(addressLabel2, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addressPaysNew, 0, GroupLayout.DEFAULT_SIZE, 200))
            .addGroup(newUserLayout.createSequentialGroup()
                .addPreferredGap(addressNew, addressLabel3, LayoutStyle.ComponentPlacement.INDENT)
                .addComponent(addressLabel3, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addressComplementNew, 0, GroupLayout.DEFAULT_SIZE, 200))
            .addGroup(newUserLayout.createSequentialGroup()
                .addComponent(reductionLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(reductionNew, 0, GroupLayout.DEFAULT_SIZE, 150))
            .addComponent(createUser, 0, GroupLayout.DEFAULT_SIZE, 200)
            .addComponent(userWarning, 0, GroupLayout.DEFAULT_SIZE, 300));

        newUserLayout.setVerticalGroup(newUserLayout
            .createSequentialGroup()
                .addGroup(newUserLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lastnameLabel)
                    .addComponent(lastnameNew))
                .addGroup(newUserLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameNew))
                .addGroup(newUserLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLabel)
                    .addComponent(addressNew))
                .addGroup(newUserLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLabel1)
                    .addComponent(addressVilleNew))
                .addGroup(newUserLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLabel2)
                    .addComponent(addressPaysNew))
                .addGroup(newUserLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLabel3)
                    .addComponent(addressComplementNew))
                .addGroup(newUserLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(reductionLabel)
                    .addComponent(reductionNew))
                .addComponent(createUser)
                .addComponent(userWarning));

        createUserFrame.add(getUserInfo, BorderLayout.CENTER);
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