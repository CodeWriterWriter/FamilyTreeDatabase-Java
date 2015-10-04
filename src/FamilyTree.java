
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * A graphical user interface for the FamilyTree.  This class is responsible just for putting up the display on 
 * screen. It then refers to the "FamilyTree" to do all the real work.
 * 
 * @author Isaac Mahon
 */



/** Reads a genealogy database from a file, and allows the user
     to display information about people in the database.
    Each line of the file (except the first) contains information
     about one person:
      - their name
      - their gender (M or F)
      - the year of their birth
      - the name of their mother (or ? if the mother is unknown)
      - the name of their father (or ? if the father is unknown)

    The program will read the data into an list of Person objects.

    The program then allows the user to print out
      - the names of all the people in the database
        (note, names are just the first name - no spaces)
      - name, gender and date of birth of a given person
      - parents of a person (if known) and their gender / dates of birth
      - the number of (known) children of a person and all their names
        and dates of birth.

 */

public class FamilyTree implements ActionListener {
    // Fields
    private ArrayList<Person> db = new ArrayList<Person>();  // list of Person objects
    private Person currentPerson;  // The person currently specified by the user.
    private JFrame frame;
    private JTextArea display, output;
    private JTextField name;
    /**
     * Construct a new Genealogy object
     * and set up the GUI
     */
    public FamilyTree(){
     
        makeFrame();
        frame.setVisible(true);
    }
    
    

    // GUI Methods
    /**
     * Make this interface visible again. (Has no effect if it is already
     * visible.)
     */
    public void setVisible(boolean visible)
    {
        frame.setVisible(visible);
    }
    private void makeFrame()
    {
        frame = new JFrame("Family Tree");
        
        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));

        display = new JTextArea( 15,30 ); 
        output= new JTextArea( 15, 30); 
        name = new JTextField();
        JPanel buttonPanel = new JPanel(new GridLayout(9, 1));
            buttonPanel.add(new JLabel("Enter Name"));
            buttonPanel.add(name);
            addButton(buttonPanel, "Load Database");
            addButton(buttonPanel, "Display all Names");
            addButton(buttonPanel, "Details");
            addButton(buttonPanel, "Parents");
            addButton(buttonPanel, "Children");
            addButton(buttonPanel, "GrandChildren");
            addButton(buttonPanel, "Clear");
        JPanel displayPanel= new JPanel();
            displayPanel.add(new JLabel("Display Area"));
            displayPanel.add(display);
        JPanel outputPanel = new JPanel();
            outputPanel.add(new JLabel("Output Area"));
            outputPanel.add(output);
        contentPane.add(buttonPanel, BorderLayout.WEST);
        contentPane.add(displayPanel, BorderLayout.CENTER);
        contentPane.add(output, BorderLayout.EAST);
            frame.pack();
    }
        
     /**
     * Add a button to the button panel.
     */
    private void addButton(Container panel, String buttonText)
    {
        JButton button = new JButton(buttonText);
        button.addActionListener(this);
        panel.add(button);
    }
    
    /**
     * An interface action has been performed. Find out what it was and
     * handle it.
     */
    public void actionPerformed(ActionEvent event)
    {
        String action = event.getActionCommand();

        if (action.equals("Load Database") ){
            this.loadDatabase();
        }
        else if (action.equals("Display all Names") )     { this.printAllNames(); }
        else if (action.equals("Details") )         { this.printPerson(); }
        else if (action.equals("Parents") )       { this.printParents(); }
        else if (action.equals("Children") )      { this.printChildren(); }
        else if (action.equals("GrandChildren") ) { this.grandChildren(); }
        else if (action.equals("Clear") )         {this.clearText(); }
        

        //redisplay();
    }
    /**
     * Update the interface display to show the current value of the 
     * calculator.
     */
    private void redisplay()
    {
        display.setText("Redisplay" );
    }
    
    

    

    /**
     * Reads the data from the database file into the ArrayList in the db field
     * Ensures that the list in db is empty
     * Reads the data on each line and constructs a Person object,
     *  and puts the Person object into the list
     *   (Should use the second or third Person constructor, either
     *    passing to the constructor a line from the file, or the scanner itself)
     * The method may assume that the database is correctly formatted,
     *  and does not need to do any checking of the input.
     */
    public void loadDatabase(){
        display.append("Reading Database \n");
        try {
        		BufferedReader scan = new BufferedReader(new FileReader("src/large-database.txt"));
        		String empty = "";
        		String delims = " ";
        		while ((scan.readLine() != empty) && scan.readLine() != null)
        		{
        			String[] data= scan.readLine().split(delims);
        			Person person = new Person (data[0], data[1], Integer.parseInt(data[2]), data[3], data[4]);
        			db.add(person);

        		}
        		scan.close();


        	} catch (FileNotFoundException fne) {
        	   System.out.println("File Not Found");
        	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        //BufferedReader scan = new BufferedReader(new FileReader("/large-database.txt"));
            //scan.close();   
    }

    /**
     * Print out names of all the people in the database
     */
    public void printAllNames(){
    	clearText();
        display.append("All names: \n");
        for(int i = 0; i < db.size(); i++)
        {
        	display.append(db.get(i).getName() + " \n");
        }
    }


    /**
     * Looks for, and returns, a Person with the given name in the database.
     * Returns null if not found
     */
    public Person getPerson(String name){
        Person person = null;
        for(int i = 0; i < db.size(); i++)
        {
        	if (db.get(i).getName().equals(name))
        	{
        		person = db.get(i);
        	}
        }
        return person;
    }

    /**
     * Prints the name, gender, and year of birth of the currently selected person.
     * If no current person, prints a message.
     *  [Note, the toString() method of the Person class returns a string
     * containing the name, gender, and year of birth of the person.]
    */
    public void printPerson(){
    	 clearText();
    	 currentPerson = getPerson(name.getText());
         if (currentPerson != null)
         {
        	 display.append(currentPerson.toString() + "\n");
         }
         else
         {
        	 display.append("No person selected" + "\n");
         }
       
    }

    /**
     * Prints the name, gender, and year of birth of the mother and the father of the
     *  currently selected person, if they are known.
     * Prints appropriate messages if they are unknown.
     * (Must find the Person objects in the database)
    */
    public void printParents(){
    	clearText();
        display.append("Printing parents \n");
        Person person = currentPerson;
        Person mother = null;
        Person father = null;
        for (int i = 0; i < db.size(); i++)
        {
        	currentPerson = db.get(i);
        	if (getChildren().contains(person))
        	{
        		if (currentPerson.getGender() == "F")
        		{
        			mother = currentPerson;
        		}
        		else
        		{
        			father = currentPerson;
        		}
        	}
        }
        if (mother != null)
        {
        	display.append(mother.toString() + "\n");
        }
        else
        {
        	display.append("No mother found" + "\n");
        }
        if (father != null)
        {
        	display.append(father.toString() + "\n");
        }
        else
        {
        	display.append("No father found" + "\n");
        }
        currentPerson = person;
        
    }


    /**
     * Prints the number of children of the given person,
     * followed by the names, genders, and years of birth of all the children.
     * Searches the database for Persons who have the currently specified person
     * as one of their parents. Any such person is added to a list
     * It then prints out the information from the list of children.
    */
    public void printChildren(){
    	clearText();
        display.append("Printing children \n");
        ArrayList<Person> children = getChildren();
        display.append("Number of children = " + children.size() + "\n");
        for (int i = 0; i < children.size(); i ++)
        {
        	display.append(children.get(i).toString() + "\n");
        }
    }

    /**
     * Returns a list of the children of a person.
     */
    public ArrayList<Person> getChildren(){
        ArrayList<Person> children = new ArrayList<Person>();
        for(int i = 0; i < db.size(); i++)
        {
        	if ((db.get(i).getFatherName() == currentPerson.getName()) || (db.get(i).getMotherName() == currentPerson.getName()) )
        	{
        		children.add(db.get(i));
        	}
        }
        return children;
    }




    /** Completion:
     * Prints (to textArea) names of all grandchildren (if any) 
     * of the currently specified person
     */
    public void grandChildren(){
    	clearText();
    	Person person = currentPerson;
    	ArrayList<Person> children = getChildren();
    	ArrayList<Person> grandchildren = new ArrayList<Person>();
    	for (Person child: children)
    	{
    		currentPerson = child;
    		ArrayList<Person> grandKid = getChildren();
    		for (Person grandkid: grandKid)
    		{
    			grandchildren.add(grandkid);
    		}
    	}
    	if (grandchildren.size() != 0)
    	{
    		for (Person grandchild: grandchildren)
    		{
    			display.append(grandchild.getName() + "\n");
    		}
    	}
    	else
    	{
    		display.append("No grandchildren found" + "\n");
    	}
    	currentPerson = person;
    }
    
    public void clearText(){
        display.setText(null);
    }
    // Main
    public static void main(String[] arguments){
        FamilyTree f = new FamilyTree();
    }   


}