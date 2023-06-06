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

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.junit.Assert;

public class GUI_Books_Test {
    private Process_Book process_book;
    private String title, author, category, publisher, importDay, price, amount;
    
    @BeforeEach
    public void setUp() {
    	process_book = new Process_Book();
        
        title = "Test";
        author = "Test";
        category = "de";
        publisher = "de";
        importDay = "2012-12-10";
        price = "10";
        amount = "1";
    }
    
    public boolean checkTitleInput(String titleInput) {
    	try {
    		if (titleInput.length() < 3 || titleInput.length() > 50)
        		return false;
    		
    		String pattern = "[~!#$%^&()<>]";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(titleInput);
            
        	if (matcher.find()) 
        		return false;
        	        	
        	return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }
    
    public boolean checkAuthorInput(String authorInput) {
    	try {
    		if (authorInput.length() < 1)
        		return false;

    		return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }
    
    public boolean checkCategoryInput(String cateInput) {
    	try {
    		if (cateInput.length() < 1)
        		return false;

    		return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }
    
    public boolean checkPublisherInput(String publisherInput) {
    	try {
    		if (publisherInput.length() < 1)
        		return false;

    		return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }
    
    public boolean checkImportInput(String date) {
    	try {
    		int y = Integer.parseInt(date.split("-")[0]);
    		int m = Integer.parseInt(date.split("-")[1]);
    		int d = Integer.parseInt(date.split("-")[2]);
    		
    		if (m < 1 || m > 12 || d < 1 || d > 31) 
    			return false;
    		else {
    			int maxDay;
    			if (m == 4 || m == 6 || m == 9 || m == 11) {
    	            maxDay = 30;
    	        } else if (m == 2) {
    	        	if (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0))
    	        		maxDay = 29;
    	        	else maxDay = 28; 
    	        } else maxDay = 31;
    			
    			if (d > maxDay) 
    				return false;
    		}
        	
        	return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }
    
    public boolean checkPriceInput(String priceString) {
    	try {
    		if (priceString.length() < 1)
        		return false;
    		
    		int price = Integer.parseInt(priceString);
        	
        	if (price < 1) 
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
    public void testTitleInput() {
        boolean checkTitle = checkTitleInput(title);
        
        Assert.assertTrue(checkTitle);
    }
    
    @Test
    public void testAuthorInput() {
        boolean checkAuthor = checkAuthorInput(author);
        
        Assert.assertTrue(checkAuthor);
    }
    
    @Test
    public void testCategoryInput() {
        boolean checkCate = checkCategoryInput(category);
        
        Assert.assertTrue(checkCate);
    }
    
    @Test
    public void testPublisherInput() {
        boolean checkPub = checkPublisherInput(publisher);
        
        Assert.assertTrue(checkPub);
    }
    
    @Test
    public void testImportInput() {
        boolean checkImport = checkImportInput(importDay);
        
        Assert.assertTrue(checkImport);
    }
    
    @Test
    public void testPriceInput() {
        boolean checkPrice = checkPriceInput(price);
        
        Assert.assertTrue(checkPrice);
    }
    
    @Test
    public void testAmountInput() {
        boolean checkAmount = checkAmountInput(amount);
        
        Assert.assertTrue(checkAmount);
    }

	@Test
	public void checkValuesInput() {
		boolean checkTitle = checkTitleInput(title);
		boolean checkAuthor = checkAuthorInput(author);
		boolean checkCate = checkCategoryInput(category);
		boolean checkPub = checkPublisherInput(publisher);
		boolean checkImport = checkImportInput(importDay);
		boolean checkPrice = checkPriceInput(price);
		boolean checkAmount = checkAmountInput(amount);

		Assert.assertTrue(checkTitle);
		Assert.assertTrue(checkAuthor);
		Assert.assertTrue(checkCate);
		Assert.assertTrue(checkPub);
		Assert.assertTrue(checkImport);
		Assert.assertTrue(checkPrice);
		Assert.assertTrue(checkAmount);
	}
}
