import java.util.*;
import java.io.*;
/* *
 * This is a class that decodes a .huff file
 * using a .code file and prints it out.
 *
 * @author Thomas H. Le
 * @version 12-12-2022
*/
public class CodeToText {

   /** This is the main driver method, that the OS uses to start the program
    *
    * @param args The list of options from the OS
    */
   public static void main(String[] args) {
      Scanner scan = new Scanner(System.in);
      String fileName = ".";
      while (fileName.contains(".")) {
         System.out.print("Name Text File: ");
         fileName = scan.nextLine();
         if (fileName.contains(".")) {
            System.out.println();
            System.out.println("File Name Only");
         }
      }
      String huff = fileName + ".huff";
      String code = fileName + ".code";
      
      HashMap<String, Character> decodeMap = new HashMap<String, Character>();
      FileInputStream scanHuff = null;
      FileInputStream scanCode = null;
      
      try {
         scanHuff = new FileInputStream(huff);
         scanCode = new FileInputStream(code);
         int lineNum = 1;
         Character char1 = null;
         Character char2 = null;
         int b = 0;
         String line = "";
         
         while (scanCode.available() > 0) {
            char1 = (char) scanCode.read();
            if (char1 == '\n') { //Checks if end of line
               if (lineNum == 1) {
                  b = Integer.parseInt(line);
                  char2 = (char) b;
                  line = "";
                  lineNum++;
               } else if (lineNum == 2) {
                  decodeMap.put(line, char2);
                  line = "";
                  lineNum = 1;
               }
            } else {
               if (char1 == '\r') { //Gets rid of whitespace
               
               } else {
                  line = line + char1;
               }
            }
         }
      } catch (Exception e) {
         System.out.println("One or more files were not found");
      }
      
      
      String huffString = "";
      try {
         while (scanHuff.available() > 0) { //reads .huff file as single string
            char x = (char) scanHuff.read();
            huffString = huffString + x;
         }
      } catch (Exception e) {
         System.out.println("Unable to read Huff File");
      }   
      
      
      String miniString = "";
      String final1 = "";
      for (int i = 0; i < huffString.length(); i++) { //converts huffman file to string.
         if (decodeMap.containsKey(miniString)) {
            System.out.print(decodeMap.get(miniString));
            miniString = "";
            i--;
         } else {
            miniString = miniString + huffString.charAt(i);
         }
      }
   }
}