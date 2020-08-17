/*
 * Course: CS2852 - 071
 * Spring 2020
 * Morse Encoder Lab
 * Name: Tyler Faulkner
 * Created: 05/07/2020
 */
package faulknert;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Used to encode text files and save them as morse code.
 */
public class MorseEncoder {
    private static LookupTable<Character, String> table;

    public static void main(String[] args) {
        table = new LookupTable();
        try {
            loadEncoder();
            System.out.print("Enter name of file to encode: ");
            Scanner in = new Scanner(System.in);
            String fileToEncode = in.nextLine();
            System.out.println("Enter name of sile to save to: ");
            String toSave = in.nextLine();
            try {
                encode(fileToEncode, toSave);
            } catch (FileNotFoundException e) {
                System.out.println("The chosen file to encode could not be found.");
            } catch (IOException e) {
                System.out.println("Critical Error Occurred.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("morsecode.txt file not found.");
        }
    }

    private static void loadEncoder() throws FileNotFoundException {
        Scanner in = new Scanner(new File("morsecode.txt"));
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.charAt(0) == '\\') {
                table.put(line.charAt(0), line.substring(2));
            } else {
                table.put(line.charAt(0), line.substring(1));
            }
        }
    }

    private static void encode(String fileToEncode, String toSave) throws IOException {
        Scanner in = new Scanner(new File(fileToEncode));
        PrintWriter out = new PrintWriter(new FileWriter(new File(toSave)));
        while (in.hasNextLine()) {
            String line = in.nextLine().toUpperCase();
            if (!line.equals("")) {
                for (int i = 0; i < line.length(); ++i) {
                    String morseCode = table.get(line.charAt(i));
                    if (morseCode != null) {
                        out.print(morseCode);
                    } else {
                        System.out.println("Skipping unknown symbol: " + line.charAt(i));
                    }
                }
                out.print(" ");
            }
            out.println(table.get('\\'));
        }
        out.flush();
        System.out.println("Done");
    }
}
