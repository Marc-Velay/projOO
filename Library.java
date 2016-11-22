import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Library implements ActionListener {

    String name;
    
    JTextArea testInput;
    JButton addDocBtn;
    JButton checkoutDocBtn;
    JButton createUser;
    JButton login;

    JButton saveBook;

    JButton checkout;
    
    JFrame frame;
    JFrame newDocframe;
    JFrame checkoutFrame;

    public Library(String name) {
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

        createUser = new JButton("Create a new user");
        createUser.setMinimumSize(new Dimension(60, 50));
        createUser.setActionCommand("createUser");
        createUser.addActionListener(this);

        login = new JButton("login");
        login.setMinimumSize(new Dimension(60, 50));
        login.setActionCommand("login");
        login.addActionListener(this);

        toolBar.add(testInput);
        toolBar.add(addDocBtn);
        toolBar.add(checkoutDocBtn);
        toolBar.add(createUser);
        toolBar.add(login);

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

            case "createUser":                            
                        frame.setVisible(false);
                        createUser();
                        //Document newDoc = new Document(frame);
                break;

            case "login":                            
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

    }

    public void login() {
        
    }

    public static void main(String[] args){
        Library library = new Library("Library");
    }
}