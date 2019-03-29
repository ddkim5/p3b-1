//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: HashTable.java
// Files: HashTable.java, HashTableTest.java
// Course: Comp Sci 400, Spring Semester, 2019
//
// Author: Yaseen Najeeb
// Email: ynajeeb@wisc.edu
// Lecturer's Name: Debra Deppeler
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
/**
 * Hashtable is the main program class of this project. The class implements HashTableADT. This
 * class implements HashTable and deals with collisions. This class also extends Comparable.
 *
 * 
 * @author Yaseen Najeeb
 *
 * @param <K> Keys
 * @param <V> Values
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT <K,V> {
  private HashNode<K, V>[] table;
  int arrayContents = 0; // 0 items in array
  int sizeOfTable = 0; // Array size
  double loadThresh;


  /**
   * This is a constructor that allows for creating HashTables
   */
  public HashTable() {
    sizeOfTable = 13;
    loadThresh = 0.75;
    table = new HashNode[sizeOfTable];
  }


  /**
   * Another constructor creates hash with both a load factor and size that is given
   * 
   * @param beginningCapacity the size of the hashTable initially
   * @param ldFactorThresh    the load threshold
   */
  public HashTable(int beginningCapacity, double ldFactorThresh) {
    sizeOfTable = beginningCapacity;
    loadThresh = ldFactorThresh;
    table = new HashNode[sizeOfTable];

  }

  /**
   * Bucket for hashtable traversal. This is private so stays in this class.
   * 
   * @author Yaseen Najeeb
   *
   * @param <K> Keys
   * @param <V> Values
   */
  private class HashNode<K, V> { // node for hashTable
    private V value;
    private K key;
    private HashNode<K, V> next;


    HashNode(K key, V value) { // node for hashTable
      this.key = key;
      this.value = value;
      this.next = null;
    }
  }

  /**
   * returns the loadThreshold
   * 
   * @return loadThreshold
   */
  public double getLoadFactorThreshold() {
    return loadThresh;

  }

  /**
   * returns loadFactor at that moment- items in Array/ size of table
   * 
   * @return loadFactor
   */
  public double getLoadFactor() {
    double loadFactor; // num items/size of table
    loadFactor = (double) arrayContents / sizeOfTable;
    return loadFactor;

  }

  /**
   * Returns current capacity, hash table
   * 
   * @return tableSize
   */
  public int getCapacity() {
    return sizeOfTable;

  }

  /**
   * Gets the collision resolution scheme and returns it
   * 
   * @return int one of the collision strategies 1-9
   */
  public int getCollisionResolution() {
    return 5;

  }

  /**
   * Calcultates hashcode for key
   * 
   * @param key
   * @return code for hash
   */
  private int hash(K key) {
    return (Math.abs(key.hashCode())) % sizeOfTable;
    // This is the mathematical formula to return a hashcode
  }

  @Override
  /**
   * Number of keys increased as a new key and value pair is added
   * 
   * @throws IllegalNullKeyException when the key is null
   * @throws DuplicateKeyException   if the key is already there
   */
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    // Connect value with the specific key.
    // The precondition is key is not null.
    if (key == null)
      throw new IllegalNullKeyException();
    int hashBucket = hash(key);
    HashNode<K, V> list = table[hashBucket]; // bucket for hashCode
    if (contains(key) == true)
      throw new DuplicateKeyException();

    while (list != null) { // list is null
      list = list.next;
    }
    if (list == null) {
      // if list is null
      if (getLoadFactor() >= getLoadFactorThreshold()) {
        // and if the list is almost full
        resize();
        // hashTable is resized
      }
      // else { //add new node
      HashNode<K, V> addNode = new HashNode(key, value);
      addNode.next = table[hashBucket];
      table[hashBucket] = addNode;
      arrayContents++; // add the new key
      // }
    }
  }

  /**
   * Checks if key even has a value in the hashTable
   * 
   * @param key
   * @return only true if key is in the table
   */
  private boolean contains(K key) { // Checks if key even has a value in the hashTable
    int hashBucket = hash(key); // In what location should key be?
    HashNode<K, V> tableList = table[hashBucket]; // For traversing the list.
    while (tableList != null) {
      if (tableList.key.equals(key)) // return true if key is found
        return true;
      tableList = tableList.next; // or continue
    }
    return false;
  }

  /**
   * This resizes and rehashes if table is reaching capacity.
   */
  private void resize() {
    this.sizeOfTable = (sizeOfTable * 2) + 1;
    HashNode<K, V>[] newTable = new HashNode[sizeOfTable];
    for (int i = 0; i < table.length; i++) {
      HashNode<K, V> list = table[i];
      while (list != null) { // list can't be null
        HashNode<K, V> next = list.next; // next Node (in list)
        int newHashCode = (Math.abs(list.key.hashCode())) % newTable.length;
        // Mathematical equation for the hashCode
        list.next = newTable[newHashCode];
        newTable[newHashCode] = list;
        list = list.next; //
      }
    }
    table = newTable; // add new table
  }

  @Override
  /**
   * removes key from the hashTable
   * 
   * @param key
   * @throws IllegalNullKeyException if null key
   * @return only true if the key is removed
   */
  public boolean remove(K key) throws IllegalNullKeyException {

    if (key == null)
      throw new IllegalNullKeyException();
    int hashBucket = hash(key);
    if (table[hashBucket] == null) { // table is false if null
      return false;
    }
    if (table[hashBucket].key.equals(key)) {
      table[hashBucket] = table[hashBucket].next;
      arrayContents--; //
      return true;
    }

    HashNode previousNode = table[hashBucket];
    HashNode currentNode = previousNode.next; // current set as next after previous
    while (currentNode != null && !currentNode.key.equals(key)) {
      currentNode = currentNode.next;
      previousNode = currentNode;
    }
    if (currentNode != null) {
      previousNode.next = currentNode.next;
      arrayContents--; // subtracts from contents when node is removed
      return true;
    }
    return false;
  }

  @Override
  /**
   * Method to get key and return the value of the key
   * 
   * @param key
   * @return value of the key
   */
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    int hashBucket = hash(key);
    HashNode<K, V> hashList = table[hashBucket]; // goes through list
    if (contains(key) == false) // if doesn't contain the key, throw exception
      throw new KeyNotFoundException();

    while (hashList != null) {
      if (hashList.key.equals(key))
        return hashList.value;
      else {
        hashList = hashList.next; // continue on list
      }

    }
    return null;
  }

  @Override
  /**
   * return the number of key in the list
   * 
   * @return numKeys
   */
  public int numKeys() {
    // TODO Auto-generated method stub
    return arrayContents;
  }

}
