// Person Class
// for use with the Family Tree Assignment

import java.util.*;


/**
 * A person has a name, a gender, a date of birth, and the names of their mother and father.
 * The class has three diffferent constructors.
 * @author Isaac Mahon
 */
public class Person{
    // Fields
    private String name;
    private String gender;
    private int dob;      
    private String mother;   
    private String father;   

    
    /**
     * Construct a new Person object, passing all attribute values.
     */
    public Person(String n, String g, int dob, String m, String f){
        this.gender = g;
        this.name = n;
        this.dob = dob;
        this.mother = m;
        this.father = f;
    }

    /**
     * Construct a new Person object, given a single String containing the five values.
     */
    public Person(String line){
        Scanner scan = new Scanner(line);
        this.name = scan.next();
        this.gender = scan.next();
        this.dob = scan.nextInt();
        String m = scan.next();
        if (!m.equals("?")) this.mother = m;
        String f = scan.next();
        if (!f.equals("?")) this.father = f;
    }

    /**
     * Construct a new Person object, reading field values from a scanner. 
     * The scanner can be linked to a file.
     */
    public Person(Scanner scan){
        this.name = scan.next();  //assumes input format is name gender dateOfBirth(int) mother father
        this.gender = scan.next();
        this.dob = scan.nextInt();
        String m = scan.next();
        if (!m.equals("?")) {
            this.mother = m;
        }
        String f = scan.next();
        if (!f.equals("?")) {
            this.father = f;
        }
    }

    // Methods
    /**
     * Returns true if and only if the Person has the specified name
     */
    public boolean hasName(String n){
        return (this.name.equalsIgnoreCase(n));
    }

    /**
     * Returns the name of the person
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the gender of the person
     */
    public String getGender(){
        return this.gender;
    }

    /**
     * Returns the DoB of the person
     */
    public int getDoB(){
        return this.dob;
    }

    /**
     * Returns the name of the mother of the person (null if not known)
     */
    public String getMotherName(){
        return this.mother;
    }

    /**
     * Returns the name of the father of the person (null if not known)
     */
    public String getFatherName(){
        return this.father;
    }

    /**
     * Returns true if this person is the same as the given person
     */
    public boolean equals(Person other){
        return (this.name.equals(other.name) && this.dob==other.dob);
    }

    /**
     * Returns a string describing the Person
     */
    public String toString(){
        return this.name+"("+this.gender+"), born:"+this.dob;
    }

}