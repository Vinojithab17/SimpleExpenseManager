package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MyDataBase extends SQLiteOpenHelper {

    private Context context;
    public static final String LOG ="log";
    public static final String ID ="id";
    public static final String DATE ="date";
    public static final String ACCOUNT_NO ="account_No";
    public static final String TYPE="type";
    public static final String AMOUNT="amount";

    public static final String ACCOUNT ="account";
    public static final String ACC_NO ="account_No";
    public static final String BANK="bank";
    public static final String ACC_HOLDER="acc_holder";
    public static final String BALANCE ="balance";

    public MyDataBase (@Nullable Context context) {

        super(context, "190650B.db", null, 1);
        this.context= context;

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String Logtable ="CREATE TABLE "+ LOG +" ("+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + DATE +" TEXT, " +
                ACCOUNT_NO +" TEXT, "+
                TYPE + " TEXT,"+
                AMOUNT + " NUMERIC, FOREIGN KEY ("+ ACCOUNT_NO +") REFERENCES "+ ACCOUNT +"("+ ACC_NO +"));";

        db.execSQL(Logtable);

        String AccountTable = "CREATE TABLE "+ ACCOUNT +" ("+ ACC_NO +" TEXT PRIMARY KEY,"+ BANK +" TEXT, "
                +ACC_HOLDER+" TEXT,"+
                BALANCE +" NUMERIC);";
        db.execSQL(AccountTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    public void addAccount(Account account){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();

        cv.put(ACC_NO,account.getAccountNo());
        cv.put(BANK,account.getBankName());
        cv.put(ACC_HOLDER,account.getAccountHolderName());
        cv.put(BALANCE,account.getBalance());

       db.insert(ACCOUNT,null,cv);

    }

    public void removeAccount(String acc_no){
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(ACCOUNT, ACC_NO +" = " +acc_no,null);
    }

    public Account getAccount(String acc){
        String GetAccount = "SELECT * FROM "+ ACCOUNT +" WHERE "+ ACC_NO +" = "+acc ;
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor c=db.rawQuery(GetAccount ,null);
        c.moveToNext();

       Account A =  new Account(c.getString(0),c.getString(1),c.getString(2),Double.parseDouble(c.getString(3)));
       c.close();
       return  A;

    }

    public List<String> getAccountNumbersList(){
        SQLiteDatabase db = this.getReadableDatabase();
        
        String Getlist = "SELECT * FROM "+ ACCOUNT ;
        Cursor c =db.rawQuery(Getlist,null);
        List<String> accountNumbers = new ArrayList<String>();
        if (c.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                accountNumbers.add(c.getString(0));

            } while (c.moveToNext());
            // moving our cursor to next.
        }
        c.close();
        return accountNumbers;
    }

    public List<Account> getAccounts(){
        SQLiteDatabase db = this.getReadableDatabase();
        
        String GetALL = "SELECT * FROM "+ ACCOUNT ;
        Cursor c =db.rawQuery(GetALL,null);
        List<Account> accounts = new ArrayList<Account>();
        if (c.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                accounts.add(new Account(c.getString(0),c.getString(1),c.getString(2),c.getDouble(3)));
            } while (c.moveToNext());
            // moving our cursor to next.
        }
        c.close();
        return accounts;

    }



    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) {

        SQLiteDatabase db = this.getReadableDatabase();
        if (expenseType==ExpenseType.EXPENSE){
            double finalAmount= this.getAccount(accountNo).getBalance() - amount;
            if (finalAmount >=0){

                Cursor cursor=db.rawQuery("UPDATE "+ ACCOUNT +" SET "+ BALANCE +" = '"+finalAmount+"' WHERE "+ ACC_NO +" = '"+ accountNo +"';",null);
                cursor.moveToFirst();
                cursor.close();

            }
            else{

            }

        }else{
            double finalAmount= this.getAccount(accountNo).getBalance() + amount;
            String NewfinalAmount=new Double(finalAmount).toString();
            Cursor cursor=db.rawQuery("UPDATE "+ ACCOUNT +" SET "+ BALANCE +" = "+NewfinalAmount+" WHERE "+ ACC_NO +" = '"+ accountNo +"';",null);
            cursor.moveToFirst();
            cursor.close();
        }


    }

    public void addLog(Date date, String acc_No, ExpenseType type, Double amount){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();



        cv.put(DATE, date.toString());
        cv.put(ACCOUNT_NO,acc_No);
        cv.put(TYPE, String.valueOf(type));
        cv.put(AMOUNT,amount);

        db.insert(LOG,null,cv);


    }



    public List<Transaction> getTransactions() {
        SQLiteDatabase db = this.getReadableDatabase();
        String test = "SELECT "+ DATE +","+ ACC_NO +","+TYPE+","+AMOUNT+" FROM "+ LOG;
        Cursor c =db.rawQuery(test,null);
        List<Transaction> transactions = new ArrayList<Transaction>();
        if (c.moveToFirst()) {
            do {
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Date date = null;
                try {
                    date = format.parse(this.parseTodaysDate(c.getString(0)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                transactions.add(new Transaction(date,c.getString(1),ExpenseType.valueOf(c.getString(2)),c.getDouble(3)));
            } while (c.moveToNext());
            // moving our cursor to next.
        }
        c.close();
        return transactions;

    }

    public static String parseTodaysDate(String time) {



        String inputPattern = "EEE MMM d HH:mm:ss zzz yyyy";

        String outputPattern = "dd-MM-yyyy";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);

            Log.i("mini", "Converted Date Today:" + str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
