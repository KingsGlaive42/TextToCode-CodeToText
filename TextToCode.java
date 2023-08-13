import java.util.*;
import java.io.*;
/* *
 * This is a class that takes a textfile and
 * converts it into huffman code.
 *
 * @author Thomas H. Le
 * @version 12-12-2022
*/
public class TextToCode {
   private static HashMap<Character, String> newMap;
   
   /** This is the main driver method, that the OS uses to start the program
    *
    * @param args The list of options from the OS
    */
   public static void main(String[] args) {
      boolean isFile = false;
      Scanner scan = new Scanner(System.in);
      FileInputStream read = null;
      String file = "";
      while (isFile == false) {
         System.out.print("Give Text File: ");
         file = scan.nextLine();
         if (!file.substring(file.length() - 4, file.length()).equals(".txt")) { //Checks if given text file.
            isFile = false;
            System.out.println();
            System.out.println("This isn't a file");
         } else {
            isFile = true;
         }
      }
      
      List<Character> charList = new ArrayList<Character>();
      try {
         read = new FileInputStream(file);
         while (read.available() > 0) { //add every char from file to list;
            char c = (char) read.read();
            charList.add(c);
         }
      } catch(Exception e) {
         System.out.println("File not found");
      }
      
      HashMap<Character, Integer> CharacterCount = new HashMap<Character, Integer>();
      for (Character i : charList) {
         if (CharacterCount.containsKey(i)) {
            int z = CharacterCount.get(i);
            CharacterCount.put(i, z+1);
         } else {
            CharacterCount.put(i, 1);
         }
      }
      
      PriorityQueue<Node> pq = new PriorityQueue<Node>();
      for (char i : CharacterCount.keySet()) {
         Node CharacterNode = new Node(i, CharacterCount.get(i));
         pq.add(CharacterNode);
      }
      
      while (pq.size() != 1) {
         Node x = pq.remove();
         Node y = pq.remove();
         Node z = new Node(null, x.getFrequency() + y.getFrequency(), x, y);
         pq.add(z);
      }
      
      newMap = new HashMap<Character, String>();
      huffManString(pq.peek(), ""); //Recursive
      String codeFile = file.substring(0, file.length() - 4) + ".code";
      String huffFile = file.substring(0, file.length()-4) + ".huff";
      try {
         File myObj = new File(codeFile);
         FileOutputStream outPut = new FileOutputStream(myObj);
         for (char i : newMap.keySet()) {
            int h = (int) i;
            String h1 = h + "";
            outPut.write(h1.getBytes());
            outPut.write('\n');
            byte[] byteArray = newMap.get(i).getBytes();
            outPut.write(byteArray);
            outPut.write('\n');
         }
         outPut.close();
         FileOutputStream output2 = new FileOutputStream(new File(huffFile));
         for (Character i : charList) {
            byte[] byteArray2 = newMap.get(i).getBytes();
            output2.write(byteArray2);
         }
      } catch (Exception e) {
         System.out.println("Could not create files");
      }
   }

   private static void huffManString(Node x, String y) {
      if (x == null) { 
         return;
      } else if (x.isLeaf()) {
         newMap.put(x.getChar(), y);
      } else {
         huffManString(x.getLeft(), y + "1");
         huffManString(x.getRight(), y + "0");
      }
   }
}