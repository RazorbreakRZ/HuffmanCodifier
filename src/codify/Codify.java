/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codify;

/**
 *
 * @author Razorbreak
 */
public class Codify {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length==2 && args[0].equals("-c")){
            Huffman huf = new Huffman();
            huf.codify(args[1]);
            if(huf.errorlevel==0){
                System.out.print("\nInput: ");
                huf.PrintDecodifiedString();
                System.out.print("\n");
                huf.PrintDictionary(false);
                huf.PrintBinaryTree();
                System.out.print("Output: ");
                huf.PrintCodifiedString();
                System.out.print("\n");
            }else{
                System.exit(1);
            }
        }else if(args.length==3 && args[0].equals("-d")){
            Huffman huf = new Huffman();
            huf.decodify(args[1],args[2]);
            if(huf.errorlevel==0){
                System.out.print("\nInput: ");
                huf.PrintCodifiedString();
                System.out.print("\n");
                huf.PrintDictionary(false);
                huf.PrintBinaryTree();
                System.out.print("Output: ");
                huf.PrintDecodifiedString();
                System.out.print("\n");
            }else{
                System.exit(1);
            }
        }else{
            System.out.println("Error(1): wrong sintax\n\n"
                    + "Code or Decode a string using the Huffman binary tree:\n"
                    + "\n\t java -jar codify.jar [-c <String> | -d <InputString> <key>]"
                    + "\n\nCreated by Dani Jimenez (razorbreak@gmail.com)");
            System.exit(1);
        }
        System.exit(0);
    }
}
