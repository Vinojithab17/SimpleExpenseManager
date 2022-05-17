/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ApplicationProvider;


import org.junit.Before;
import org.junit.Test;

import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.MyDataBase;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest {

//        private ExpenseManager expenseManager;
        private MyDataBase db;

        @Before
        public void startUp(){
            Context context = ApplicationProvider.getApplicationContext();
            try {
//                expenseManager = new PersistentExpenseManager(context);
                db = new MyDataBase(context);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Test
        public void testAddAccount(){
            Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
            db.addAccount(dummyAcct1);
            List<String>  accNumbers= db.getAccountNumbersList();
            assertTrue(accNumbers.contains("12345A"));
        }
        @Test
        public void testAddLog(){
            assertTrue(db.addLog("17-02-2022","12345A", ExpenseType.INCOME,2000.0));
        }

        @Test
        public void testGetAccount(){
            Account A = db.getAccount("'12345A'");
            assertTrue(A.getAccountNo().equals("12345A"));
        }
}