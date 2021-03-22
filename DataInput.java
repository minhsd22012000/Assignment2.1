/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md5.encryption;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author pc
 */

public class DataInput {

    private final static Scanner in = new Scanner(System.in);
    /*
    \\d{10,11} user must be input 10 or 11 number
     */
    private static final String PHONE_VALID = "^\\d{10,11}$";

    /*
    [A-Za-z0-9.-+%]+ user must be input from a-z ignore case,0-9 and .-+% least one times
    @ user must be input "@"
    [A-Za-z.-]+ user mustbe input from a-z ignore case, "." "-" least one times
    \\. user must be input "."
    [A-Za-z]{2,4} user must be input from a-z ignore 2 - 4 times
     */
    private static final String EMAIL_VALID
            = "^[A-Za-z0-9.+-_]+@[A-Za-z.-]+\\.[A-Za-z]{2,4}$";

    //user must input something but not a blank 
    public static String inputString(String nameField) {
        System.out.print("Enter " + nameField + ": ");
        String result = in.nextLine().trim();
        //loop until user input something , not empty
        while (result.isEmpty()) {
            System.out.println("Can not left empty!!!");
            System.out.print("Enter " + nameField + ": ");
            result = in.nextLine().trim();
        }
        return result;
    }

    //user must input a integer value , else function return exception and make user input again
    public static int inputIntWithLimit(int min, int max, String nameField) {
        //loop until user input correct
        while (true) {
            try {
                String number = inputString(nameField);
                int result = Integer.parseInt(number);
                //return exception if user input a number out range
                if (result < min || result > max) {
                    throw new NumberFormatException();

                }
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Please input number in rage [" + min + ", " + max + "]");
            }
        }
    }

    //check phone is number with minimum 10 characters
    public static String inputPhoneNumber(String nameField) {
        String result = inputString(nameField);
        while (!result.matches(PHONE_VALID)) {
            System.err.println("Phone is number with minimum 10 characters");
            result = inputString(nameField);
        }
        return result;
    }

    //check email with format <account name>@<domain>. (eg: annguyen@fpt.edu.vn)
    public static String inputEmail(String nameField) {
        String result = inputString(nameField);
        while (!result.matches(EMAIL_VALID)) {
            System.err.println("Email with format <account name>@<domain>");
            result = inputString(nameField);
        }
        return result;
    }
    //input valid date format 
    public static String checkInputDate(String nameField) {
        while (true) {
            //string must follow simple date format dd/MM/yyyy, else throw parse exception
            try {
                String result = inputString(nameField);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date = format.parse(result);
                if (result.equalsIgnoreCase(format.format(date))) {
                    return result;
                } else {
                    throw new ParseException(result, 0);
                }
            } catch (NumberFormatException | ParseException ex) {
                System.out.println("Date format invalid");
            }
        }
    }

}