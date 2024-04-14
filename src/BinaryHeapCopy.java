// BinaryHeapCopy class
//
// CONSTRUCTION: with optional capacity (that defaults to 100)
//               or an array containing initial items
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item (modified)
// Comparable findMin( )  --> Return smallest item (modified)
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinaryHeapCopy<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the binary heap.
     */
    public BinaryHeapCopy( )
    {
        this( DEFAULT_CAPACITY );
    }

    /**
     * Construct the binary heap.
     * @param capacity the capacity of the binary heap.
     */
    public BinaryHeapCopy( int capacity )
    {
        currentSize = 0;
        array = (AnyType[]) new Comparable[ capacity + 1 ];
    }
    
    /**
     * Construct the binary heap given an array of items.
    */
    public BinaryHeapCopy( AnyType [ ] items )
    {
        currentSize = items.length;
        array = (AnyType[]) new Comparable[ ( currentSize + 2 ) * 11 / 10 ];
        
        int i = 1;
        for( AnyType item : items )
            array[ i++ ] = item;
        buildHeap( );
	}

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * @param x the item to insert.
     * change the x.compareTo( array[ hole / 2 ] ) < 0; to x.compareTo( array[ hole / 2 ] ) > 0; for Max-heap.
     */
    public void insert( AnyType x ) // this should be changed to max
    {
        if( currentSize == array.length - 1 )
            enlargeArray( array.length * 2 + 1 );

        // Percolate up
        int hole = ++currentSize;
        for( ; hole > 1 && x.compareTo( array[ hole / 2 ] ) > 0; hole /= 2 )
            array[ hole ] = array[ hole / 2 ];
        array[ hole ] = x;
    }


    private void enlargeArray( int newSize )
    {
	    AnyType [] old = array;
		array = (AnyType []) new Comparable[ newSize ];
        for( int i = 0; i < old.length; i++ )
            array[ i ] = old[ i ];
    }
			       
    /**
     * Find the smallest item in the priority queue. -> change findMin() to findMax() for hw6_huffman_tree
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMax( ) // this should be findMax()
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return array[ 1 ];
    }

    /**
     * Remove the smallest item from the priority queue. -> change deleteMin() to deleteMax() for hw6_huffman_tree
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMax( ) // This should be deleteMax()
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        AnyType maxItem = findMax( );
        array[ 1 ] = array[ currentSize-- ];
        percolateDown( 1 );

        return maxItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap( )
    {
        for( int i = currentSize / 2; i > 0; i-- )
            percolateDown( i );
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
    }

    private static final int DEFAULT_CAPACITY = 10;

    private int currentSize;   // Number of elements in heap
    private AnyType [ ] array; // The heap array

    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     * change the 'array[ child + 1 ].compareTo( array[ child ] ) < 0' to 'array[ child + 1 ].compareTo( array[ child ] ) > 0' for Max-heap.
     * change array[ child ].compareTo( tmp ) < 0 to array[ child ].compareTo( tmp ) > 0  
     */
    private void percolateDown( int hole )
    {
        int child;
        AnyType tmp = array[ hole ];

        for( ; hole * 2 <= currentSize; hole = child )
        {
            child = hole * 2; // left chiled

            if( child != currentSize && array[ child + 1 ].compareTo( array[ child ] ) > 0 )
            child++; // right child

            if( array[ child ].compareTo( tmp ) > 0 )
                array[ hole ] = array[ child ];
            else
                break;
        }
        array[ hole ] = tmp;
    }

    /**
     * getSize() returns the number of elements in the heap
     */
    public int getSize() {
        return currentSize;
    }

    // Test code get() item in heap array
    public AnyType get(int index) {
        if (index < 1 || index > currentSize) {
            throw new IndexOutOfBoundsException("Index " + index + " not in heap range.");
        }
        return array[index]; // Ensure this respects your class's encapsulation
    }

    // Test code
    public static void main(String[] args) {
        // Initialize a new BinaryHeapCopy
        // BinaryHeapCopy<Integer> heap = new BinaryHeapCopy<>();
    
        // Create a new heap from an array
        Integer[] nums = {50, 40, 60, 30, 20, 10};
        BinaryHeapCopy<Integer> heapFromArray = new BinaryHeapCopy<>(nums);
        System.out.println("Heap created from array:");
        for (int i = 1; i <= heapFromArray.getSize(); i++) {
            System.out.print(heapFromArray.get(i) + " ");
        }
        System.out.println();
        System.out.println("Maximum element in new heap: " + heapFromArray.findMax());
        System.out.println("Size of new heap: " + heapFromArray.getSize());

        // Create a new heap from an array
        Integer[] nums2 = {1, 23, 13, 45, 123, 47, 9, 3, 4, 5, 34, 34, 23};
        BinaryHeapCopy<Integer> heapFromArray2 = new BinaryHeapCopy<>(nums2);
        System.out.println("Heap created from array:");
        for (int i = 1; i <= heapFromArray2.getSize(); i++) {
            System.out.print(heapFromArray2.get(i) + " ");
        }
        System.out.println();
        System.out.println("Maximum element in new heap: " + heapFromArray2.findMax());
        System.out.println("Size of new heap: " + heapFromArray2.getSize());
    }
    
}