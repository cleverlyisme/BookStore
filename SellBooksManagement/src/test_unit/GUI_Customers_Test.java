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

public class GUI_Customers_Test {
    private Process_Customer process_customer;
    private String name, phone, email, birth;
    
    @BeforeEach
    public void setUp() {
    	process_customer = new Process_Customer();
        
        name = "teet";
        phone = "0987654321";
        email = "test@gmail.com";
        birth = "2012-02-29";
    }
    
    public boolean checkNameInput(String nameInput) {
    	try {
    		if (nameInput.length() < 3 || nameInput.length() > 25)
        		return false;
    		
    		String pattern = "[~!#$%^&()<>]";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(nameInput);
            
        	if (matcher.find()) 
        		return false;
        	        	
        	return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }
    
    public boolean checkPhoneInput(String phoneInput) {
    	try {
    		if (phoneInput.length() != 10)
        		return false;
    		
    		int checkPhone = Integer.parseInt(phoneInput);
    		
    		String prefix1 = "03";
    		
    		if (!phoneInput.startsWith("03") && !phoneInput.startsWith("05") && 
    				!phoneInput.startsWith("07") && !phoneInput.startsWith("09"))
    			return false;
    		
    		if (process_customer.getCustomerByPhone(phoneInput) != null) 
    			return false;
    		
    		return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }
    
    public boolean checkEmailInput(String emailInput) {
    	try {
    		if (emailInput.length() < 4 || emailInput.length() > 64)
        		return false;
    		
            String regex = "^(?!.*\\.\\.)(?=.*[A-Za-z0-9])[A-Za-z0-9._-]{4,64}@[A-Za-z0-9.-]{2,253}\\.[A-Za-z]{2,63}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(emailInput);
            
            if (!matcher.matches())
            	return false;
        	
            if (process_customer.getCustomerByEmail(emailInput) != null) 
    			return false;
            
        	return true;
    	}
    	catch (Exception ex) {
    		return false;
    	}
    }
    
    public boolean checkBirthInput(String date) {
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

    @Test
    public void testNameInput() {
        boolean checkName = checkNameInput(name);
        
        Assert.assertTrue(checkName);
    }
    
    @Test
    public void testPhoneInput() {
		boolean checkPhone = checkPhoneInput(phone);
        
        Assert.assertTrue(checkPhone);
    }
    
    @Test
    public void testEmailInput() {
        boolean checkEmail = checkEmailInput(email);
        
        Assert.assertTrue(checkEmail);
    }
    
    @Test
    public void testBirthInput() {
        boolean checkBirth = checkBirthInput(birth);
        
        Assert.assertTrue(checkBirth);
    }
    
    @Test
	public void checkValuesInput() {
		boolean checkName = checkNameInput(name);
		boolean checkPhone = checkPhoneInput(phone);
		boolean checkEmail = checkEmailInput(email);
		boolean checkBirth = checkBirthInput(birth);

		Assert.assertTrue(checkName);
		Assert.assertTrue(checkPhone);
		Assert.assertTrue(checkEmail);
		Assert.assertTrue(checkBirth);
	}
}
