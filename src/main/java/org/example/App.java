package org.example;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner in = new Scanner(System.in);
        while(1==1) {
            String newLine = in.nextLine();
            if("exit".equalsIgnoreCase(newLine)) {
                break;
            }
            System.out.println("You entered: " + newLine);
        }
    }
}
