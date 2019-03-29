//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: MyProfiler.java
// Files: HashTable.java, HashTableTest.java
// Course: Comp Sci 400, Spring Semester, 2019
//
// Author: Yaseen Najeeb
// Email: ynajeeb@wisc.edu
// Lecturer's Name: Debra Deppeler
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////



import java.util.ArrayList;
import java.util.TreeMap;
/**
 * The class is used to will profile to determine relative performance between
 * your HashTable and Java's TreeMap structures.
 * 
 * @author Arshad Habib
 *
 * @param <K> keys
 * @param <V> values
 */
public class MyProfiler<K extends Comparable<K>, V> {

    HashTableADT<K, V> hashtable;
    TreeMap<K, V> treemap;

    /**
     * Empty myProfiler constructor instantiates HashTable and TreeMap data
     * structures.
     */
    public MyProfiler() {
        // TODO: complete the Profile constructor
        // Instantiate your HashTable and Java's TreeMap
        hashtable = new HashTable<K, V>();
        treemap = new TreeMap<K, V>();
    }

    /**
     * Inserts a key and value into HashTable and Treemap data structures accounting
     * for any exceptions.
     * 
     * @param key
     * @param value
     */
    public void insert(K key, V value) {
        // TODO: complete insert method
        // Insert K, V into both data structures
        try {
            treemap.put(key, value);
            hashtable.insert(key, value);
            //hashmap.put(key, value);
        } catch (DuplicateKeyException e) {
            // TODO Auto-generated catch block
            System.out.println("DuplicateKeyException throw");
            System.exit(1);
            
        } 
        catch (IllegalNullKeyException e) {
            System.out.println("DuplicateKeyException throw");
            System.exit(1);
        }
    }

    /**
     * Retrieves value for key specified in argument for the two data structures.
     * 
     * @param key
     */
    public void retrieve(K key) {
        // TODO: complete the retrieve method
        // get value V for key K from data structures
        try {
            if (treemap.containsKey(key)) {
                treemap.get(key);
            }
            hashtable.get(key);
        } catch (IllegalNullKeyException e) {
            System.out.println("DuplicateKeyException throw");
            System.exit(1);
            
        }
        catch (KeyNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("KeyNotFoundException throw");
            System.exit(1);
            
        } 
    }

    /**
     * Main method used for running and testing dual functionality and performance
     * through many insert and retrieve executions.
     * 
     * @param args the number of elements to insert and retrieve
     */
    public static void main(String[] args) {
        try {
            int numElements = Integer.parseInt(args[0]);

            // TODO: complete the main method.
            // Create a profile object.
            // For example, Profile<Integer, Integer> profile = new Profile<Integer,
            // Integer>();
            // execute the insert method of profile as many times as numElements
            // execute the retrieve method of profile as many times as numElements
            // See, ProfileSample.java for example.

            MyProfiler<Integer, Integer> profile = new MyProfiler<Integer, Integer>();

            for (Integer i = 0; i < numElements; i++) {
                profile.insert(i, i);
            }
            for (Integer i = 0; i < numElements; i++) {
                profile.retrieve(i);
            }
            String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
            System.out.println(msg);
        } catch (Exception e) {
            System.out.println("Usage: java MyProfiler <number_of_elements>");
            System.exit(1);
        }
    }
}
