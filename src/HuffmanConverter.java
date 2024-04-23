import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

//constructor
public class HuffmanConverter{

        // ASCII number of characters
        public static final int NUMBER_OF_CHARACTERS = 256;

        private String contents;
        private HuffmanTree huffmanTree;
        private int count[];
        private String code[];

        // Construct using an input string.
        // Initialize count and code.
        public HuffmanConverter(String input) {
          this.contents = input;
          this.count = new int[NUMBER_OF_CHARACTERS];
          this.code = new String[NUMBER_OF_CHARACTERS];
        }

        // Record how often each character occurs and store in count.
        public void recordFrequencies() {
            for (char c: contents.toCharArray()) {
                count[(int) c]++;
            }

            for (int i = 0; i < NUMBER_OF_CHARACTERS; i++) {
                if (count[i] > 1)
                    System.out.print("< " + (char) i + ", " + count[i] + " >" + " ");
            }
            System.out.println();
        }

        // Construct a Huffman tree from count and 
        // store the tree in huffmanTree.
        public void frequenciesToTree() {
            BinaryHeap<HuffmanNode> heap = new BinaryHeap<>();

            for (int i = 0; i < NUMBER_OF_CHARACTERS; i++) {
                if (count[i] > 0) {
                    heap.insert(new HuffmanNode(String.valueOf((char) i), (double) count[i]));
                }
            }
            huffmanTree = HuffmanTree.createFromHeap(heap);
        }

        // Construct code from huffmanTree.
        public void treeToCode() {
            // Arrays.fill(code, "");

            if (huffmanTree.root != null) {
                treeToCode(huffmanTree.root, "");
            }
            
            huffmanTree.printLegend();
        }

        private void treeToCode(HuffmanNode t, String encoding) {
            if (t != null) {
                // if reach the leaf node
                if (t.left == null && t.right == null) {
                    code[(int) t.letter.charAt(0)] = encoding;
                } else {
                    treeToCode(t.left, encoding + "0");
                    treeToCode(t.right, encoding + "1");
                }
            }
        }

        // Encode content using code.
        public String encodeMessage() {
            StringBuilder builder = new StringBuilder();

            for (char c: contents.toCharArray()) {
                builder.append(code[(int) c]);
            }

            return builder.toString();
        }
        
        // Decode a Huffman encoding.
        public String decodeMessage(String encodedStr) {
            StringBuilder builder = new StringBuilder();

            HuffmanNode current = huffmanTree.root;

            for (int i = 0; i < encodedStr.length(); i++) {
                current = encodedStr.charAt(i) == '0' ? current.left : current.right;

                // if reach the leaf node
                if (current.left == null && current.right == null) {
                    builder.append(current.letter);
                    // assign it back to root
                    current = huffmanTree.root;
                }
            }

            return builder.toString();
        }

        // Read an input file.
        public static String readContents(String filename) {
            String temp = "";
            try {
                File file = new File(filename);
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    temp += sc.nextLine();
                    temp += "\n";
                }
                sc.close();
                return temp;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return "";
        }

        public static void main(String args[]) {
                String input = HuffmanConverter.readContents(args[0]);
                HuffmanConverter h = new HuffmanConverter(input);
                h.recordFrequencies();
                // Print a list of characters and frequencies here!
                h.frequenciesToTree();
                h.treeToCode();
                // Print the Huffman encoding here!
                String encoded = h.encodeMessage();
                System.out.println(encoded+"\n");
                System.out.println("Message size in ASCII encoding: "+h.contents.length()*8);
                System.out.println("Message size in Huffman coding: "+encoded.length()+"\n");
                System.out.println(h.decodeMessage(encoded));
        }

}
