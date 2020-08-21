public class SeparateChainingHashST<Key, Value> {
    // this basic symbol-table implementation maintains an array of linked lists, using a hash function to choose a list for each key
    // in a separate-chaining hash table with M lists and N keys, the number of compares (equality tests) for search miss and insert is ~N/M
    private int N; // number of key-value pairs
    private int M; // hash table size
    private SequentialSearchST<Key, Value>[] st; // array of ST objects

    public SeparateChainingHashST()
    // default constructor specifies 997 lists
    { this(997); }
    public SeparateChainingHashST(int M) { // Create M linked lists.
        this.M = M;
        // need a cast when creating st[] because Java prohibits arrays with generics
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++)
            st[i] = new SequentialSearchST();
    }
    private int hash(Key key)
    { return (key.hashCode() & 0x7fffffff) % M; }
    public Value get(Key key)
    { return (Value) st[hash(key)].get(key); }
    public void put(Key key, Value val)
    { st[hash(key)].put(key, val); }
    public Iterable<Key> keys()
// See Exercise 3.4.19.
}
