import com.sun.jdi.Value;

public class Node <KeyType, ValueType> {
    private ValueType val;
    private KeyType key;
    private boolean isDeleted;


    public Node(KeyType key, ValueType val){
        this.val = val;
        this.key = key;
    }

    public Node(KeyType key, ValueType val, boolean isDeleted){
        this(key, val);
        this.isDeleted = isDeleted;

    }

    public boolean isDeleted(){
        return isDeleted;
    }

    public ValueType getValue(){
        return this.val;
    }

    public KeyType getKey(){
        return this.key;
    }

}
