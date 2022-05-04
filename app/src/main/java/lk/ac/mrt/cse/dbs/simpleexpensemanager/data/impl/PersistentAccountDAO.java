package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.MyDataBase;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;


public class PersistentAccountDAO  implements AccountDAO{

    private MyDataBase myDb;

    public PersistentAccountDAO(MyDataBase myDb) {
        this.myDb =  myDb;
    }

    @Override
    public List<String> getAccountNumbersList() {

      return myDb.getAccountNumbersList();
    }

    @Override
    public List<Account> getAccountsList() {
        return myDb.getAccounts();
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return myDb.getAccount(accountNo);
    }

    @Override
    public void addAccount(Account account) {

        myDb.addAccount(account);

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

        myDb.removeAccount(accountNo);

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        myDb.updateBalance(accountNo,expenseType,amount);

    }
}
