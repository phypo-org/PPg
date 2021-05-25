package org.phypo.PPg.PPgUtils;




//***********************************
public class PPgPair<KeyType, ValueType> {
		  
	    private final KeyType key;
	    private final ValueType value;

	    public PPgPair(KeyType key, ValueType value) {  
	        this.key = key;
	        this.value = value;
	    }

	    public KeyType getKey() {
	        return key;
	    }

	    public ValueType getValue() {
	        return value;
	    }

	    public String toString() { 
	        return "(" + key + ", " + value + ")";  
	    }
	}
//***********************************
