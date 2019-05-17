package demo;

public class Calculator {

		
	// TODO Auto-generated constructor stub
		
		public int addition(int num1, int num2) {
	
	
			// dummy code
			int param = 1;
			
			if (param == 1)
				  ; // do nothig
			else if (param == 2)
				  ; // do nothing
			else if (param == 1)  // Noncompliant
				; // do nothing
		
			
			return (num1 + num2);
	    }

	    public int substraction(int num1, int num2) {
	        return (num1 - num2);
	    }

	    public int multiplication(int num1, int num2) {
	        return (num1 * num2);
	    }

	    public int division(int num1, int num2) {
	        return (num1 / num2);
	    }
		
	}

