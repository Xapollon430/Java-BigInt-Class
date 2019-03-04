import java.util.Scanner;
import java.util.ArrayList;
public class BigInt {

	private ArrayList<Integer> bigNum = new ArrayList<Integer>();
	private boolean positive;
	private static boolean isValid;

	public BigInt() {
		setbigNum("0");
	}

	// Object Constructor
	public BigInt(BigInt b){
		String result = "";
		for (int i = b.bigNum.size()-1; i >= 0 ; i--) {
			result += Integer.toString(b.bigNum.get(i));
		}
		setbigNum(result);
		this.positive = b.positive;
	}

	//Integer Constructor
	public BigInt(int num) {
		String number = Integer.toString(num);
		setbigNum(number);
	}

	// String Constructor
	public BigInt(String num) {
		setbigNum(num);
	}

	// ArrayList Constructor
	public BigInt(ArrayList<Integer> list) {
		String result = "";
		for (int i = list.size()-1; i >= 0 ; i--) {
			result += Integer.toString(list.get(i));
		}
		setbigNum(result);
	}

	private void setbigNum(String num) {
		//this.num = num;
		if (isValid(num)) {
			this.setpositive(num);
			this.toArrayList(num);		
		}
		else { 
			throw new BadStringInput("Wrong Input");
		} 
	}

	public BigInt add(BigInt num){
		BigInt result = new BigInt();
		if(!num.positive && !this.positive) {
			result = this.addHelper(num);
			result.positive = false;//result.num = "-" + result.num;
			//System.out.println(result.positive);
		}

		else if(!num.positive && this.positive) {
			if (num.isGreaterEqual(this)) {
				result = this.subtractHelper(num);
				result.positive = false;//result.num = "-" + result.num;
				//System.out.println("sssssss");
			}
			else
				result = this.subtractHelper(num);
		}

		else if(num.positive && !this.positive) {
			if (this.isGreaterEqual(num)) {
				result = this.subtractHelper(num);
				result.positive = false;//result.num = "-" + result.num;
				//System.out.println("sssssss");
			}
			else {
				result = this.subtractHelper(num);
				//System.out.println("sssssss");
			}

		}
		else
			result = this.addHelper(num);
		if (result.bigNum.get(0) == 0 && result.bigNum.size() == 1) {
			result.positive = true;
			//System.out.println("sssssss");
		}
	
		return result;
	}
	
	private BigInt addHelper(BigInt num){
		BigInt result = new BigInt();
		ArrayList <Integer> temp = new ArrayList <Integer>();
		int digit = 0;
		if(this.bigNum.size() <= num.bigNum.size()) {
			digit = num.bigNum.size() - this.bigNum.size();
			for (int i = 0; i < digit; i++) {
				this.bigNum.add(0);
			}
		}
		else if(this.bigNum.size() > num.bigNum.size()) {
			digit = this.bigNum.size() - num.bigNum.size();
			for (int i = 0; i < digit; i++) {
				num.bigNum.add(0);
			}
		}
		for (int i = 0; i < num.bigNum.size(); i++) {
			temp.add(this.bigNum.get(i) + num.bigNum.get(i));	
		}
		result.bigNum = temp;
		result.sortArrayListAdd();
		return result;
	}

	public BigInt subtract(BigInt num){
		BigInt result = new BigInt();
		if (this.bigNum.size() == 1 && this.bigNum.get(0) == 0) {
			if (num.positive) {
				result = new BigInt(this);
				result.positive = false;
			}
			else {
				result = new BigInt(this);
				result.positive = false;
			}
		}
		if (this.equals(num)){
			result = new BigInt ("0");
			//result.positive = true;
		}
		else if(!num.positive && this.positive) {

			result = this.addHelper(num);

		}

		else if(!num.positive && !this.positive) {		
			if (num.isGreaterEqual(this))
				result = this.subtractHelper(num);
			else  {
				result = this.subtractHelper(num);
				result.positive = false;//result.num = "-" + result.num;
				//System.out.println("sssssss");
			}
		}		
		else if(num.positive && !this.positive) {	
			result = this.addHelper(num);
			result.positive = false;//result.num = "-" + result.num;
		}

		else if (num.positive && this.positive) {
			if (this.isGreaterEqual(num)) {
				result = this.subtractHelper(num);
			}
			else {
				result = this.subtractHelper(num);
				result.positive = false;//result.num = "-" + result.num;
			}
		}
		if (result.bigNum.size()==1 && result.bigNum.get(0) == 0)
			result.positive = true;
		return result;
	}
	
	private BigInt subtractHelper(BigInt num){
		BigInt result = new BigInt();
		ArrayList <Integer> temp = new ArrayList <Integer>();
		int digit = 0;
		if(this.bigNum.size() <= num.bigNum.size()) {
			digit = num.bigNum.size() - this.bigNum.size();
			for (int i = 0; i < digit; i++) {
				this.bigNum.add(0);
			}
		}
		else if(this.bigNum.size() > num.bigNum.size()) {
			digit = this.bigNum.size() - num.bigNum.size();
			for (int i = 0; i < digit; i++) {
				num.bigNum.add(0);
			}
		}
		if (this.isGreaterEqual(num)) {
			for (int i = 0; i < this.bigNum.size(); i++) {
				if (this.bigNum.get(i) - num.bigNum.get(i) < 0) {
					this.bigNum.set(i, this.bigNum.get(i)+10 );
					temp.add(this.bigNum.get(i) - num.bigNum.get(i));
					this.bigNum.set(i+1, this.bigNum.get(i+1)-1 );
				}
				else
					temp.add(this.bigNum.get(i) - num.bigNum.get(i));		
			}
		}
		else if (!this.isGreaterEqual(num)) {
			for (int i = 0; i < num.bigNum.size(); i++) {
				if (num.bigNum.get(i) - this.bigNum.get(i) < 0) {
					//digit = this.bigNum.get(i)+10;
					num.bigNum.set(i, num.bigNum.get(i)+10 );
					temp.add(num.bigNum.get(i) - this.bigNum.get(i));
					num.bigNum.set(i+1, num.bigNum.get(i+1)-1 );
				}
				else
					temp.add(num.bigNum.get(i) - this.bigNum.get(i));		
			}
		}
		else if(this.bigNum.equals(num.bigNum))
			temp.add(0);
		result = new BigInt(temp);
		result.removeZero();
		return result;
	}
	
	public BigInt multiply (BigInt num) {
		BigInt result = new BigInt ();
		
		if ((this.bigNum.size() == 1 && this.bigNum.get(0) == 0) || (num.bigNum.size() == 1 && num.bigNum.get(0) == 0)  ) {			
			result = new BigInt("0");
			result.positive = true;
			//System.out.println("ssss");
		}
		else if (this.isGreaterEqual(num)) {
			result = this.multiplyHelper(num);
			if ((this.positive && num.positive) || (!this.positive && !num.positive))
				result.positive = true;
			
			else
				result.positive = false;
		}
		else {
			result = num.multiplyHelper(this);
			if ((this.positive && num.positive) || (!this.positive && !num.positive))
				result.positive = true;
			
			else
				result.positive = false;
			//System.out.println("ssss");
		}
		
		
		return result;
	}
	
	private BigInt multiplyHelper(BigInt num) {
		BigInt result = new BigInt();
		BigInt tempResult = new BigInt();
		ArrayList <Integer> temp = new ArrayList <Integer>();
		int digit = 0;
		int carryOver = 0;
		for (int i = 0 ; i < num.bigNum.size(); i++) {
			if (i > 0 ) {
				for (int k = 0; k < i; k++)
					temp.add(0);
			}
			for (int j = 0 ; j < this.bigNum.size(); j++) {
				digit = this.bigNum.get(j) * num.bigNum.get(i) + carryOver;
				if (j == this.bigNum.size() - 1) {
					if (digit > 9) {
						temp.add(digit % 10);
						temp.add(digit / 10);
					}
					else {
						temp.add(digit);
						carryOver = 0;
					}					
				}		
				else if (digit > 9) {
					temp.add(digit % 10);
					carryOver = digit / 10;
				}
				else {
					temp.add(digit);
					carryOver = 0;					
				}
				digit = 0;
			}
			carryOver = 0;
			tempResult.bigNum = temp;
			temp = new ArrayList <Integer>();
			result = result.add(tempResult);
			tempResult = new BigInt();
		}
		return result;
	}

	private void sortArrayListAdd(){
		for (int i = 0 ; i < this.bigNum.size(); i++) {
			if(i == this.bigNum.size()-1) {
				if(this.bigNum.get(i) > 9)
					this.bigNum.add(0);
			}

			if(this.bigNum.get(i) > 9) {
				this.bigNum.set(i, this.bigNum.get(i)%10 );
				this.bigNum.set(i+1, this.bigNum.get(i+1)+1 );
			}
			else 
				this.bigNum.set(i, this.bigNum.get(i));
		}
	}
	
	public BigInt divideBy(BigInt num) {
		BigInt result = new BigInt ();
		
		if (this.isGreaterEqual(num)) {
			result = this.divideHelper(num);
			if ((this.positive && num.positive) || (!this.positive && !num.positive))
				result.positive = true;
			
			else
				result.positive = false;
		}
		else {
			result = new BigInt ("0");
			result.positive = true;
		}
		if (result.bigNum.size() == 1 && result.bigNum.get(0) == 0)
			result.positive = true;
		return result;
	}
	
	private BigInt divideHelper(BigInt num) {
		BigInt result = new BigInt();
		BigInt temp = new BigInt();
		BigInt temp2 = new BigInt (this);
		BigInt partialAnswer = new BigInt();
		if (num.bigNum.size() == 1 && num.bigNum.get(0) == 0)
			throw new BadStringInput(" / by zero");
		else
			while (temp2.isGreaterEqual(num)){
				
				partialAnswer = temp2.divideHelper2(num);
				
				temp = temp2.subtractHelper(temp2.divideHelper2(num).multiplyHelper(num));
				
				temp2.bigNum = temp.bigNum;
				
				result = result.addHelper(partialAnswer);
			}
		return result;
	}
	
	private BigInt divideHelper2(BigInt num) {
		
		BigInt result = new BigInt();
		
		int n = this.bigNum.size() - num.bigNum.size();
		
		if (n <= 1 )
			
			result = new BigInt("1") ;
		
		else { 
			
			for (int i = 0; i < n-1 ; i++ ) {
				
				if (i == n-2)
					
					result.bigNum.add(1);	
				
				else
					
					result.bigNum.add(0);
				
			}
		}
		return result;
	}
	
	public BigInt modulus(BigInt num) {	
		
		BigInt result = new BigInt ();
		
		if (num.bigNum.size() == 1 && num.bigNum.get(0) == 0)
			
			throw new BadStringInput(" / by zero");
		
		else if (this.bigNum.size() == 1 && this.bigNum.get(0) == 0) {
			
			result = new BigInt("0");
			
			result.positive = true;
			
		}
			
		else if (this.isGreaterEqual(num)) {
			
			result = this.modulusHelper(num);
			
			if (!this.positive && !num.positive)
				
				result.positive = false;
			
			else if (this.positive && num.positive)
				
				result.positive = true;
			
			else if (!this.positive && num.positive)
				
				result.positive = false;
			
			else if (this.positive && !num.positive)	
				
				result.positive = true;
		}
		else 
			result = this;			
		return result;
	}	

	
	private BigInt modulusHelper(BigInt num) {
		
		BigInt result = new BigInt();
		
		BigInt temp = new BigInt(this);
		
		BigInt temp2 = this.divideHelper(num);
		
		BigInt temp3 = temp2.multiplyHelper(num);
		
		result = temp.subtractHelper(temp3);
		
		return result;
	}
	
	


	private void removeZero() {
		if (this.bigNum.size() == 1) {
			//System.out.println("num issssssssss "+this.num);
		}
		else {
			for (int i = this.bigNum.size()-1; i >= 0; i--) { 
				if (this.bigNum.get(i) == 0) {
					if (i == 0) {
						if (this.bigNum.get(i) == 0) {
							//System.out.println("num issssssssss "+this.num);
						}		
					}		

					else {
						//this.num = (this.num.substring(1));
						this.bigNum.remove(i);					
						// System.out.println(this.bigNum.size());
						//System.out.println("num is "+this.num);
					}
				}
				else 
					break;
			}
		}
	}

	private boolean isGreaterEqual(BigInt num) {
		boolean isGreater = false;
		if (this.bigNum.size() == num.bigNum.size()) {
			for (int i = num.bigNum.size()-1; i >= 0; i--) {

				if (this.bigNum.get(i) == num.bigNum.get(i)) {
					isGreater = true;
				}

				else if(this.bigNum.get(i) > num.bigNum.get(i)) {
					isGreater = true;
					break;
				}

				else if(this.bigNum.get(i) < num.bigNum.get(i)) {
					isGreater = false;
					break;
				}
			}
		}
		else if(this.bigNum.size() > num.bigNum.size())
			isGreater = true;
		else if(this.bigNum.size() < num.bigNum.size())
			isGreater = false;
		return isGreater;
	}

	private boolean isValid(String num) {

		if (num.length() == 1) {
			if (!Character.isDigit(num.charAt(0)))
				isValid = false;
			else
				isValid = true;
		}
		else {	
			for (int i = 0; i < num.length(); i++) {
				if (i == 0) {
					if (num.charAt(i) == '+' || num.charAt(i) == '-' )
						isValid = true;
					else if (!Character.isDigit(num.charAt(i))) {
						isValid = false;
						break;	
					}
				}
				else if (!Character.isDigit(num.charAt(i))) {
					isValid = false;
					break;	
				}
				else 
					isValid = true;
			}
		}
		return isValid;
	}

	private void toArrayList(String num){
		//num = this.num;
		if(num.charAt(0) == '+' || num.charAt(0) == '-') {
			num = num.substring(1);
			for (int i = num.length(); i > 0 ; i--) {
				bigNum.add(Integer.parseInt(num.substring(i-1, i)));
			}
		}		
		else		
			for (int i = num.length(); i > 0 ; i--) {
				bigNum.add(Integer.parseInt(num.substring(i-1, i)));
			}
	}


	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	private void setpositive(String num) {
		if (num.charAt(0) == '-')
			this.positive = false;
		else
			this.positive = true;			
	}

	public String toString() {
		String result = "";
		this.removeZero();
		for (int i = bigNum.size(); i > 0 ; i--) {
			result += Integer.toString(bigNum.get(i-1));
		}
		if (!this.positive)
			return "-" + result;
		else
			return result;
	}
}