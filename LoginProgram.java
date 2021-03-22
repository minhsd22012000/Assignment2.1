/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md5.encryption;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author pc
 */
public class LoginProgram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AccountManager accountManager = new AccountManager();
        boolean run = true;
        do {
            printMenu();
            String choice = DataInput.inputString("your choice");
            switch (choice) {
                case "1":
                    accountManager.addAccount(createAccount(accountManager));
                    break;
                case "2":
                    login(accountManager);
                    break;
                case "3":
                    run = false;
                    break;
                default:
                    break;
            }
        } while (run);
    }
    
    //print menu option
    public static void printMenu() {
        System.out.println("============ Login Program =========");
        System.out.println("1. Add User");
        System.out.println("2. Login");
        System.out.println("3) Exit");
    }
    
    //create new account object , fill all infor and return that object
    public static Account createAccount(AccountManager accountManager) {
        Account account = new Account();
        //username cannot duplicate
        do {
            account.setUsername(DataInput.inputString("user name"));
            if (accountManager.checkUsernameExist(account.getUsername())) {
                System.out.println("UserName is already registered");
            }
        } while (accountManager.checkUsernameExist(account.getUsername()));
        account.setPassword(encryptMD5(DataInput.inputString("password")));
        account.setName(DataInput.inputString("name"));
        account.setPhone(DataInput.inputPhoneNumber("phone number"));
        account.setEmail(DataInput.inputEmail("email"));
        account.setDateOfBirth(DataInput.checkInputDate("date of birth"));
        return account;
    }
    
    //user must input correct username and password to get an account 
    public static void login(AccountManager accountManager) {
        //if hashtalbe is empty , return to menu
        if (accountManager.getAccountTable().isEmpty()) {
            System.err.println("Data is empty");
            return;
        }
        System.out.println("-------------Login-------------");
        String username = DataInput.inputString("user name");
        String password = DataInput.inputString("password");
        String passwordEncrypted = encryptMD5(password);
        Account accoutLogin = accountManager.findAccount(username, passwordEncrypted);
        //if accountLogin is null , return to menu .Else , go to change password function
        if (accoutLogin != null) {
            System.out.println("--------Wellcome---------");
            System.out.print("Hi " + accoutLogin.getUsername()
                    + ", do you want chage password now? Y/N: ");
            System.out.println("new pass: " + passwordEncrypted);
            changePassword(accoutLogin, accountManager);
        } else {
            System.err.println("Invalid username or password");
        }
    }
    
    //encrypt a string follow MD5 algorism , then return that string 
    public static String encryptMD5(String input) {
        try {
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5");
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes());
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest);
            // Convert message digest into hex value 
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //check user input the same String in newPassword and newPasswordRetype . 
    //If they are the same , repalce old accountLogin's password to new one
    private static void changePassword(Account accoutLogin, AccountManager accountManager) {
        //user input a string , must equal accountLogin's password 
        while (!accoutLogin.getPassword().equalsIgnoreCase(encryptMD5(DataInput.inputString("old password")))) {       
                System.out.println("Password Incorrect");          
        }
        //user must input the same String of newPassword and newPasswordRetype
        while(true){
            String newPassword = DataInput.inputString("new password");
            String newPasswordRetype = DataInput.inputString("renew password");
            if (newPassword.equals(newPasswordRetype)) {
                accountManager.updatePassword(accoutLogin.getUsername(), encryptMD5(newPassword));
                System.out.println("Password changed Successfully");
                return;
            }
        }
    }
}
