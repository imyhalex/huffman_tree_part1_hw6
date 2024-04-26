public class HuffmanTree 
{
   HuffmanNode root;

   public HuffmanTree(HuffmanNode huff) {
      this.root = huff;
   } 

   public void printLegend() {
      printLegend(root, "");
   }

   private void printLegend(HuffmanNode t, String s) {
      if (t == null) {
         return;
      }

      if (t.letter.length() == 1) {

         if (t.letter.equals("\n")) 
            System.out.println("\\n" + "="+ s);

         else if (t.letter.equals("\t")) 
            System.out.println("\\t" + "="+ s);

         else if (t.letter.equals("\r")) 
            System.out.println("\\r" + "="+ s);

         else 
            System.out.println(t.letter +"="+ s);
      } else { 
         printLegend(t.left, s + "0");
         printLegend(t.right, s + "1");
      }
   }

   public static BinaryHeap legendToHeap(String legend) {
      String[] list = legend.split(" ");

      HuffmanNode[] nodes = new HuffmanNode[list.length / 2];
      for (int i = 0; i < list.length; i += 2) {
         String letter = list[i];
         Double frequency = Double.parseDouble(list[i + 1]);

         nodes[i / 2] = new HuffmanNode(letter, frequency);
      }

      return new BinaryHeap<>(nodes);
   }

   public static HuffmanTree createFromHeap(BinaryHeap b) {
      while (b.getSize() > 1) {
         HuffmanNode left = (HuffmanNode) b.deleteMin();
         HuffmanNode right = (HuffmanNode) b.deleteMin();

         HuffmanNode combined = new HuffmanNode(left, right);
         b.insert(combined);
      }

      return new HuffmanTree((HuffmanNode) b.deleteMin());
   }
   
   public static void main(String[] args) {
      String legend = "A 20 E 24 G 3 H 4 I 17 L 6 N 5 O 10 S 8 V 1 W 2";
      // String legend = "R 3 E 1 O 1";
      BinaryHeap bheap = legendToHeap(legend);
      System.out.println("Heap before constructing the Huffman Tree:");
      bheap.printHeap();
      HuffmanTree tree = createFromHeap(bheap);
      tree.printLegend();
   }
}