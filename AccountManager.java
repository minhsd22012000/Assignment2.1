/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md5.encryption;

import java.util.HashMap;

/**
 *
 * @author pc
 */
public class AccountManager {

    private final HashMap<String, Account> accountTable;

    public AccountManager() {
        accountTable = new HashMap<>();
    }

    public HashMap<String, Account> getAccountTable() {
        return accountTable;
    }

    public void addAccount(Account account) {
        accountTable.put(account.getUsername(), account);
    }

    public boolean checkUsernameExist(String userName) {
        return accountTable.containsKey(userName);
    }

    //return account found by username and passwordEncrypted
    public Account findAccount(String username, String passwordEncrypted) {
        //if username not found , return null
        if (!accountTable.containsKey(username)) {
            return null;
        }
        Account accountLogin = accountTable.get(username);
        //if passwordEncrypted(user input) not equal with username's password , return null
        if (accountLogin.getPassword().equals(passwordEncrypted)) {
            return accountLogin;
        }
        return null;
    }

    public void updatePassword(String userName, String passwordUpdated) {
        if (!accountTable.containsKey(userName)) {
            return ;
        }
        accountTable.get(userName).setPassword(passwordUpdated);
    }

}
