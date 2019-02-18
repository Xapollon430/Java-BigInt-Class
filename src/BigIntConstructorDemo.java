
	
	public class BigIntConstructorDemo
	{
	    public static void main(String[] args)
	    {
	    	// debugging demonstration
	    	
	    
	    			
	    	BigInt b1; 
	    	BigInt b2; 
	    	BigInt b3; 
	    	b1 = new BigInt("999");
	    	b2 = new BigInt("24");
	    	b3 = b1.multiplyHelper(b2);
	    	System.out.println(b3);
	    }
	}