package hashtable;
import java.util.*;

public class StringTable {
    private LinkedList<Record>[] buckets;
    private int nBuckets;
    public int size;
    
    

    // Create an empty table with nBuckets buckets
    @SuppressWarnings("unchecked")
	public StringTable(int nBuckets)
    {
        //initialize number of buckets and make an array with the given number of buckets
    	this.nBuckets = nBuckets;
    	buckets = new LinkedList[nBuckets];
        
        //create a list for each bucket for chaining
    	for(int i = 0; i < buckets.length; i++) {
    		buckets[i] = new LinkedList<Record>();
    	}
        //initialize size
    	this.size = 0;
    }
    
    
    public boolean insert(Record r) 
    {  
    	//object with same key is already in the table, return false
    	if(find(r.key)!=null) {
    		return false;
    	}
        //obtain hashcode for the record we are trying to insert
    	int hashcode = stringToHashCode(r.key);
        //convert hashcode to an index
    	int index = toIndex(hashcode);
        //add record in the calculated bucket index
    	buckets[index].add(r);
        //update size
    	this.size++;
    	return true;
    }
    
    public Record find(String key) 
    {
    	//calculate the bucket index of the given key and get the list at that index
    	int hashcode = stringToHashCode(key);
    	int index = toIndex(hashcode);
    	LinkedList<Record> list = buckets[index];
    	
        //iterate through the list until we find the key
    	for(int i = 0; i < list.size(); i++) {
    		if(list.get(i).key.equals(key)) {
    			return list.get(i);
    		}
    	}	
    	//at this point we have iterate through the list where the record object should have been
        //but did not find it. return null to indicate not found
    	return null;
    }
    
    
    public void remove(String key) 
    {
        //calculate bucket index of given key and obtain the list at that key
    	int hashcode = stringToHashCode(key);
    	int index = toIndex(hashcode);
    	LinkedList<Record> list = buckets[index];
    	
        //remove key once an object with a matching key is found
        //do nothing if the key does not exist in that list
    	for(int i = 0; i < list.size(); i++) {
    		if(list.get(i).key.equals(key)) {
    			list.remove(i);
    			this.size--;
    		}
    	}	
    }
    

    /**
     * toIndex - convert a string's hashcode to a table index
     *
     * As part of your hashing computation, you need to convert the
     * hashcode of a key string (computed using the provided function
     * stringToHashCode) to a bucket index in the hash table.
     *
     * You should use a multiplicative hashing strategy to convert
     * hashcodes to indices.  
     */
    private int toIndex(int hashcode)
    {
    	//use multiplicative hashing strategy to convert hashcode to index and try to achieve uniform hashing
    	double A = (Math.sqrt(7) - 5)/2;
    	int i = (int)((Math.abs(hashcode * A)%1.0)*nBuckets);
	
    	return i;
    }
    
    
    //THIS METHOD WAS PRE-PROVIDED
    //converts a string into a hashcode, which is used in the toIndex function
    int stringToHashCode(String key)
    {
    	int A = 1952786893;
	
    	int v = A;
    	for (int j = 0; j < key.length(); j++)
	    {
    		char c = key.charAt(j);
    		v = A * (v + (int) c + j) >> 16;
	    }
	
    	return v;
    }
}
