package test_unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui_design.GUI_Pay;
import process.BillDetail;
import process.Customer;
import process.Process_Book;
import process.Process_Customer;
import process.Book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;

public class GUI_Pay_Test {
	private Process_Customer process_customer;
    private Process_Book process_book;
    private String customerId, bookId, amount;
    
    @BeforeEach
    public void setUp() {
        process_customer = new Process_Customer();
        process_book = new Process_Book();
        
        customerId = "1";
        bookId = "1";
        amount = "1";
    }
    
    public boolean checkIdCustomerInput(String idString) {
    	try {
    		if (idString.length() < 1 || idString.length() > 9)
        		return false;
    		
    		int id = Integer.parseInt(idString);
        	
        	if (id < 1) 
        		return false;
        	
        	if (process_customer.getCustomerById(id) == null) 
        		return false;
        	
        	return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }
    
    public boolean checkIdBookInput(String idString) {
    	try {
    		if (idString.length() < 1 || idString.length() > 9)
        		return false;
    		
    		int id = Integer.parseInt(idString);
        	
        	if (id < 1) 
        		return false;
        	
        	if (process_book.getBookById(id) == null) 
        		return false;
        	
        	return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }
    
    public boolean checkAmountInput(String amountString) {
    	try {
    		if (amountString.length() < 1 || amountString.length() > 3)
        		return false;
    		
    		int amount = Integer.parseInt(amountString);
        	
        	if (amount < 1 || amount > 100) 
        		return false;
        	
        	return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }

    @Test
    public void testIdCustomerInput() {
        boolean checkCustomer = checkIdCustomerInput(customerId);
        
        Assert.assertTrue(checkCustomer);
    }
    
    @Test
    public void testIdBookInput() {
        boolean checkBook = checkIdBookInput(bookId);
        
        Assert.assertTrue(checkBook);
    }
    
    @Test
    public void testAmountInput() {
        boolean checkAmount = checkAmountInput(amount);
        
        Assert.assertTrue(checkAmount);
    }
    
    @Test
    public void testAddBookToBill() {
    	 boolean checkCustomer = checkIdCustomerInput(customerId);
         boolean checkBook = checkIdBookInput(bookId);
         boolean checkAmount = checkAmountInput(amount);

         Assert.assertTrue(checkCustomer);
         Assert.assertTrue(checkBook);
         Assert.assertTrue(checkAmount);
    }
}
