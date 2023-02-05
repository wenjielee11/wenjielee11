import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.NoSuchElementException;

public class HashtableMap <KeyType,ValueType> implements MapADT<KeyType, ValueType>{
    protected Node<KeyType, ValueType>[] hashTable;
    private int size = 0;
    private int loadSize = 0;



    public HashtableMap(int capacity) {
        hashTable = (Node<KeyType,ValueType>[]) new Node[capacity];

    }

    /**
     * add a new key-value pair/mapping to this collection
     *  throws exception when key is null or duplicate of one already stored
     * @param key key to add
     * @param value value of the key
     * @throws IllegalArgumentException when key is null or duplicate
     */
    @Override
    public void put(KeyType key, ValueType value) throws IllegalArgumentException {
        if(key == null || value == null){
            throw new IllegalArgumentException("Error, key or value cant be null");
        }
        if(containsKey(key)){
            throw new IllegalArgumentException("Error, key is duplicate");
        }
        Node newNode = new Node(key,value);
        int index = hashFunction(key,getCapacity());
        //Open address and linear probing if collision occurs
        for(int i = 0; i<hashTable.length;i++){
            if(hashTable[index] == null || hashTable[index].isDeleted()){
                hashTable[index] = newNode;
                size++;
                break;
            }else{
                index = (index+1) % hashTable.length;
            }
        }
        if(getLoadFactor() >= 0.7){
            rehash();
        }




    }
    private void rehash(){
        Node<KeyType, ValueType>[] oldArray = hashTable;
        hashTable = (Node<KeyType,ValueType>[]) new Node[oldArray.length*2];
        for(int i=0;i<oldArray.length;i++){
            put(oldArray[i].getKey(),oldArray[i].getValue());
        }

    }

    /**
     * check whether a key maps to a value within this collection
     * @param key to check if the hashtable contains or not
     * @return true if the key exists, false if not
     */
    @Override
    public boolean containsKey(KeyType key) {
        try{
            get(key);
            return true;
        }catch(NoSuchElementException e){
            return false;
        }
    }

    /**
     * @param key the key of the value pair that we want to find
     * @return the value of the key pair
     * @throws NoSuchElementException if key is null, or if key does not exist
     */
    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        // Prevents NPE's
        if(key == null){
            throw new NoSuchElementException("Error, key cannot be null");
        }
        // Hashing function
        int index =  hashFunction(key, getCapacity());

            //Linear probing if collision occurs
            // O(N) worst case
            for (int i = 0; i < hashTable.length; i++) {
                if(hashTable[index] == null) {
                    break;
                } else if (hashTable[index].getKey().equals(key)) {
                    return hashTable[index].getValue();
                }
                // % wraps the index to the beginning if it reaches end of array, and if i<arr.length
                index = (index+1) % hashTable.length;
            }
        //If element does not exist, throw the exception to be caught in other parts of code
        throw new NoSuchElementException("Error, element does not exist when trying to get with key");
    }

    /**
     * Calculates the hash index of the key
     * @param key to find the hashcode and calculate its index
     * @return the index where the key is located
     */
    private int hashFunction(KeyType key, int capacity){
        return Math.abs(key.hashCode())%capacity;
    }

    /**
     * @param key
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public ValueType remove(KeyType key) throws NoSuchElementException {
        return null;
    }

    /**
     *
     */
    @Override
    public void clear() {

    }

    /**
     * retrieve the number of keys stored within this collection
     * @return the number of keys stored
     */
    @Override
    public int getSize() {
        return this.size;
    }

    /**
     *  retrieve this collection's capacity (size of its underlying array)
     * @return capacity with the length of the array
     */
    @Override
    public int getCapacity() {
        return hashTable.length;
    }

    /**
     * Calculates the load factor by dividing the number of pairs in table with
     * The array length
     * @return the load factor
     */
    private double getLoadFactor(){
        return this.size/this.getCapacity();
    }

}
