import java.util.ArrayList;
public class BigInt {

	private ArrayList<Integer> BigNum = new ArrayList<Integer>();
	private boolean positive;
	private static boolean isValid;

	public BigInt() {
		setBigNum("0");
	}

	// Object Constructor
	public BigInt(BigInt b){
		String result = "";
		for (int i = b.BigNum.size()-1; i >= 0 ; i--) {
			result += Integer.toString(b.BigNum.get(i));
		}
		setBigNum(result);
		this.positive = b.positive;
	}

	//Integer Constructor
	public BigInt(int num) {
		String number = Integer.toString(num);
		setBigNum(number);
	}

	// String Constructor
	public BigInt(String num) {
		setBigNum(num);
	}

	// ArrayList Constructor
	public BigInt(ArrayList<Integer> list) {
		String result = "";
		for (int i = list.size()-1; i >= 0 ; i--) {
			result += Integer.toString(list.get(i));
		}
		setBigNum(result);
	}

	private void setBigNum(String num) {
		//this.num = num;
		if (isItTrue(num)) {
			this.makePositive(num);
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
			if (num.isItBigger(this)) {
				result = this.subtractHelper(num);
				result.positive = false;//result.num = "-" + result.num;
				//System.out.println("sssssss");
			}
			else
				result = this.subtractHelper(num);
		}

		else if(num.positive && !this.positive) {
			if (this.isItBigger(num)) {
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
		if (result.BigNum.get(0) == 0 && result.BigNum.size() == 1) {
			result.positive = true;
			//System.out.println("sssssss");
		}
	
		return result;
	}
	
	private BigInt addHelper(BigInt num){
		BigInt result = new BigInt();
		ArrayList <Integer> temp = new ArrayList <Integer>();
		int digit = 0;
		if(this.BigNum.size() <= num.BigNum.size()) {
			digit = num.BigNum.size() - this.BigNum.size();
			for (int i = 0; i < digit; i++) {
				this.BigNum.add(0);
			}
		}
		else if(this.BigNum.size() > num.BigNum.size()) {
			digit = this.BigNum.size() - num.BigNum.size();
			for (int i = 0; i < digit; i++) {
				num.BigNum.add(0);
			}
		}
		for (int i = 0; i < num.BigNum.size(); i++) {
			temp.add(this.BigNum.get(i) + num.BigNum.get(i));	
		}
		result.BigNum = temp;
		result.sortArrayListAdd();
		return result;
	}

	public BigInt subtract(BigInt num){
		BigInt result = new BigInt();
		if (this.BigNum.size() == 1 && this.BigNum.get(0) == 0) {
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
			if (num.isItBigger(this))
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
			if (this.isItBigger(num)) {
				result = this.subtractHelper(num);
			}
			else {
				result = this.subtractHelper(num);
				result.positive = false;//result.num = "-" + result.num;
			}
		}
		if (result.BigNum.size()==1 && result.BigNum.get(0) == 0)
			result.positive = true;
		return result;
	}
	
	private BigInt subtractHelper(BigInt num){
		BigInt result = new BigInt();
		ArrayList <Integer> temp = new ArrayList <Integer>();
		int digit = 0;
		if(this.BigNum.size() <= num.BigNum.size()) {
			digit = num.BigNum.size() - this.BigNum.size();
			for (int i = 0; i < digit; i++) {
				this.BigNum.add(0);
			}
		}
		else if(this.BigNum.size() > num.BigNum.size()) {
			digit = this.BigNum.size() - num.BigNum.size();
			for (int i = 0; i < digit; i++) {
				num.BigNum.add(0);
			}
		}
		if (this.isItBigger(num)) {
			for (int i = 0; i < this.BigNum.size(); i++) {
				if (this.BigNum.get(i) - num.BigNum.get(i) < 0) {
					this.BigNum.set(i, this.BigNum.get(i)+10 );
					temp.add(this.BigNum.get(i) - num.BigNum.get(i));
					this.BigNum.set(i+1, this.BigNum.get(i+1)-1 );
				}
				else
					temp.add(this.BigNum.get(i) - num.BigNum.get(i));		
			}
		}
		else if (!this.isItBigger(num)) {
			for (int i = 0; i < num.BigNum.size(); i++) {
				if (num.BigNum.get(i) - this.BigNum.get(i) < 0) {
					//digit = this.BigNum.get(i)+10;
					num.BigNum.set(i, num.BigNum.get(i)+10 );
					temp.add(num.BigNum.get(i) - this.BigNum.get(i));
					num.BigNum.set(i+1, num.BigNum.get(i+1)-1 );
				}
				else
					temp.add(num.BigNum.get(i) - this.BigNum.get(i));		
			}
		}
		else if(this.BigNum.equals(num.BigNum))
			temp.add(0);
		result = new BigInt(temp);
		result.removeZero();
		return result;
	}
	
	public BigInt multiply (BigInt num) {
		BigInt result = new BigInt ();
		
		if ((this.BigNum.size() == 1 && this.BigNum.get(0) == 0) || (num.BigNum.size() == 1 && num.BigNum.get(0) == 0)  ) {			
			result = new BigInt("0");
			result.positive = true;
			//System.out.println("ssss");
		}
		else if (this.isItBigger(num)) {
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
		for (int i = 0 ; i < num.BigNum.size(); i++) {
			if (i > 0 ) {
				for (int k = 0; k < i; k++)
					temp.add(0);
			}
			for (int j = 0 ; j < this.BigNum.size(); j++) {
				digit = this.BigNum.get(j) * num.BigNum.get(i) + carryOver;
				if (j == this.BigNum.size() - 1) {
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
			tempResult.BigNum = temp;
			temp = new ArrayList <Integer>();
			result = result.add(tempResult);
			tempResult = new BigInt();
		}
		return result;
	}

	private void sortArrayListAdd(){
		for (int i = 0 ; i < this.BigNum.size(); i++) {
			if(i == this.BigNum.size()-1) {
				if(this.BigNum.get(i) > 9)
					this.BigNum.add(0);
			}

			if(this.BigNum.get(i) > 9) {
				this.BigNum.set(i, this.BigNum.get(i)%10 );
				this.BigNum.set(i+1, this.BigNum.get(i+1)+1 );
			}
			else 
				this.BigNum.set(i, this.BigNum.get(i));
		}
	}
	
	public BigInt divideBy(BigInt num) {
		BigInt result = new BigInt ();
		
		if (this.isItBigger(num)) {
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
		if (result.BigNum.size() == 1 && result.BigNum.get(0) == 0)
			result.positive = true;
		return result;
	}
	
	private BigInt divideHelper(BigInt num) {
		BigInt result = new BigInt();
		BigInt temp = new BigInt();
		BigInt temp2 = new BigInt (this);
		BigInt partialAnswer = new BigInt();
		if (num.BigNum.size() == 1 && num.BigNum.get(0) == 0)
			throw new BadStringInput(" / by zero");
		else
			while (temp2.isItBigger(num)){
				
				partialAnswer = temp2.divideHelper2(num);
				
				temp = temp2.subtractHelper(temp2.divideHelper2(num).multiplyHelper(num));
				
				temp2.BigNum = temp.BigNum; 
				
				result = result.addHelper(partialAnswer);
			}
		return result;
	}
	
	private BigInt divideHelper2(BigInt num) {
		
		BigInt result = new BigInt();
		
		int n = this.BigNum.size() - num.BigNum.size();
		
		if (n <= 1 )
			
			result = new BigInt("1") ;
		
		else { 
			
			for (int i = 0; i < n-1 ; i++ ) {
				
				if (i == n-2)
					
					result.BigNum.add(1);	
				
				else
					
					result.BigNum.add(0);
				
			}
		}
		return result;
	}
	
	public BigInt modulus(BigInt num) {	
		
		BigInt result = new BigInt ();
		
		if (num.BigNum.size() == 1 && num.BigNum.get(0) == 0)
			
			throw new BadStringInput(" / by zero");
		
		else if (this.BigNum.size() == 1 && this.BigNum.get(0) == 0) {
			
			result = new BigInt("0");
			
			result.positive = true;
			
		}
			
		else if (this.isItBigger(num)) {
			
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
		if (this.BigNum.size() == 1) {
			//System.out.println("num issssssssss "+this.num);
		}
		else {
			for (int i = this.BigNum.size()-1; i >= 0; i--) { 
				if (this.BigNum.get(i) == 0) {
					if (i == 0) {
						if (this.BigNum.get(i) == 0) {
							//System.out.println("num issssssssss "+this.num);
						}		
					}		

					else {
						//this.num = (this.num.substring(1));
						this.BigNum.remove(i);					
						// System.out.println(this.BigNum.size());
						//System.out.println("num is "+this.num);
					}
				}
				else 
					break;
			}
		}
	}

	private boolean isItBigger(BigInt num) {
		boolean isGreater = false;
		if (this.BigNum.size() == num.BigNum.size()) {
			for (int i = num.BigNum.size()-1; i >= 0; i--) {

				if (this.BigNum.get(i) == num.BigNum.get(i)) {
					isGreater = true;
				}

				else if(this.BigNum.get(i) > num.BigNum.get(i)) {
					isGreater = true;
					break;
				}

				else if(this.BigNum.get(i) < num.BigNum.get(i)) {
					isGreater = false;
					break;
				}
			}
		}
		else if(this.BigNum.size() > num.BigNum.size())
			isGreater = true;
		else if(this.BigNum.size() < num.BigNum.size())
			isGreater = false;
		return isGreater;
	}

	private boolean isItTrue(String num) {

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
				BigNum.add(Integer.parseInt(num.substring(i-1, i)));
			}
		}		
		else		
			for (int i = num.length(); i > 0 ; i--) {
				BigNum.add(Integer.parseInt(num.substring(i-1, i)));
			}
	}


	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	private void makePositive(String num) {
		if (num.charAt(0) == '-')
			this.positive = false;
		else
			this.positive = true;			
	}

	public String toString() {
		String result = "";
		this.removeZero();
		for (int i = BigNum.size(); i > 0 ; i--) {
			result += Integer.toString(BigNum.get(i-1));
		}
		if (!this.positive)
			return "-" + result;
		else
			return result;
	}
}