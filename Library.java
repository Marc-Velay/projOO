import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Library implements ActionListener {

    String name;
    JTextArea testInput;
    JButton addBookBtn;
    JButton saveBook;
    JFrame frame;
    JFrame newDocframe;

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

        addBookBtn = new JButton("Add a new document");
        addBookBtn.setMinimumSize(new Dimension(60, 50));
        addBookBtn.setActionCommand("addDoc");
        addBookBtn.addActionListener(this);

        toolBar.add(testInput);
        toolBar.add(addBookBtn);
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

            case "saveDoc":
                        newDocframe.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        System.out.println("refreshed page");
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

    public void windowClosing(WindowEvent e){
        System.out.println("closing");
    }

    public static void main(String[] args){
        Library library = new Library("Library");
    }
}