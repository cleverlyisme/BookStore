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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

public class GUI_BookStatistic_Test {
	private Process_Customer process_customer;
    private Process_Book process_book;
    private String name;
    
    @BeforeEach
    public void setUp() {
        process_customer = new Process_Customer();
        process_book = new Process_Book();
        
        name = "test";
    }
    
    public boolean checkNameInput(String nameInput) {
    	try {
    		if (nameInput.length() < 3 || nameInput.length() > 25)
        		return false;
    		
    		if (nameInput.contains(" "))
    			return false;
        	        	
        	return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }

    @Test
    public void testNameInput() {
        boolean checkName = checkNameInput(name);
        
        Assert.assertTrue(checkName);
    }
}
