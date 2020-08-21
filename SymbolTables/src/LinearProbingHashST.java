public class LinearProbingHashST<Key, Value>
{
    /*
    this symbol-table implementation keeps keys and values in parallel arrays (as in BinarySearchST)
but uses empty spaces (marked by null) to terminate clusters of keys. If a new key hashes to an empty
entry, it is stored there; if not, we scan sequentially to find an empty position. To search for a key, we
scan sequentially starting at its hash index until finding null (search miss) or the key (search hit).

    we implement the table with parallel arrays, one for the keys and one
for the values, and use the hash function as an index to access the data as just discussed

    COME BACK AND TRACE THIS CODE FOR MIDTERM -PLUS SeparateChainingHashST()

    https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/LinearProbingHashST.java.html

    
     */
    private int N; // number of key-value pairs in the table
    private int M; // size of linear-probing table
    private Key[] keys; // the keys
    private Value[] vals; // the values

    // constructor that takes a fixed capacity as argument
    // initializes an empty symbol table with the specified initial capacity
    public LinearProbingHashST(int cap) {
        M = cap;
        N = 0;
        // cast since we cannot have arrays w generics
        keys = (Key[])   new Object[M];
        vals = (Value[]) new Object[M];
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M; }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    private void resize(int cap)
    {
        LinearProbingHashST<Key, Value> t;
        t = new LinearProbingHashST<Key, Value>(cap);
        for (int i = 0; i < M; i++)
            if (keys[i] != null)
                t.put(keys[i], vals[i]);
        keys = t.keys;
        vals = t.vals;
        M = t.M;
    }

    public void put(Key key, Value val)
    {
        // call resize to ensure that the table is at most one-half full
        if (N >= M/2) resize(2*M); // double M (see text)
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key)) { vals[i] = val; return; }
        keys[i] = key;
        vals[i] = val;
        N++;
    }
    public Value get(Key key)
    {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }
    public void delete(Key key)
    {
        if (!contains(key)) return;
        int i = hash(key);
        while (!key.equals(keys[i]))
            i = (i + 1) % M;
        keys[i] = null;
        vals[i] = null;
        i = (i + 1) % M;
        while (keys[i] != null)
        {
            Key keyToRedo = keys[i];
            Value valToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N == M/8) resize(M/2);
    }
}