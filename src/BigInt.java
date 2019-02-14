import java.util.Scanner;
import java.util.ArrayList;
public class BigInt {

	//private Scanner input = new Scanner(System.in);
	private ArrayList<Integer> BigNum = new ArrayList<Integer>();
	private String num;
	private boolean sign;
	private static boolean isValid;

	// My Default Constructor 
	public BigInt() {
		setBigNum("0");
	}

	// Object Constructor
	public BigInt(BigInt b) {
		setBigNum(b.num);
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
		if (isItValid(num)) {
			this.setsign(num);
			this.toArrayList(num);		
		}
		else { 

			throw new BadStringInput("Wrong Input");
		} 
	}

	public BigInt Add(BigInt num){
		BigInt result = new BigInt();
		if(!num.sign && !this.sign) {
			result = this.addHelper(num);
			result.sign = false;
		}

		else if(!num.sign && this.sign) {
			if (num.firstOneBigger(this)) {
				result = this.subtractHelper(num);
				result.sign = false;
			
			}
			else
				result = this.subtractHelper(num);
		}

		else if(num.sign && !this.sign) {
			if (this.firstOneBigger(num)) {
				result = this.subtractHelper(num);
				result.sign = false;
			
			}
			else
				result = this.subtractHelper(num);
		}

		else
			result = this.addHelper(num);
		return result;
	}

	public BigInt Subtract(BigInt num){
		BigInt result = new BigInt();
		if (this.equals(num)){
			result = new BigInt (0);
		}
		else if(!num.sign && this.sign) {

			result = this.addHelper(num);

		}

		else if(!num.sign && !this.sign) {		
			if (num.firstOneBigger(this))
				result = this.subtractHelper(num);// why not assign sign here also
			else  {
				result = this.subtractHelper(num);
				result.sign = false;
		
			}
		}		
		else if(num.sign && !this.sign) {	
			result = this.addHelper(num);
			result.sign = false;
		}

		else if (num.sign && this.sign) {
			if (this.firstOneBigger(num)) {
				result = this.subtractHelper(num);
			}
			else {
				result = this.subtractHelper(num);
				result.sign = false;
			}
		}
		return result;
	}

	public BigInt multiplyHelper(BigInt num) {
		BigInt result = new BigInt();
		ArrayList <Integer> temp = new ArrayList <Integer>();
		int ultimateSize;
		int digit = 0;
		int carryOver = 0;
		int inferior;
		if (this.BigNum.size() >= num.BigNum.size()) {
			inferior = num.BigNum.size();
			ultimateSize = this.BigNum.size();
			for (int i = 0 ; i < inferior; i++) {
				for (int j = 0 ; j < ultimateSize; j++) {
					digit = this.BigNum.get(j) * num.BigNum.get(i) + carryOver;
					
					if (j == ultimateSize - 1) {
						digit = this.BigNum.get(j) * num.BigNum.get(i) + carryOver;
						//System.out.println(digit);
						if (digit > 9) {
							
							temp.add(digit % 10);
							//System.out.println(temp.get(j));

							temp.add(digit / 10);
							//break;
//							temp.set(j, digit % 10 );
//							temp.set(j+1, digit / 10);
						}
					}
					else if (digit > 10) {
						temp.add(digit % 10);
						carryOver = digit / 10;
					}
					
					else {
						temp.add(digit);
						carryOver = 0;
					}
					digit = 0;
				}
				System.out.println(temp);
				//System.out.println(digit);

			}
			
		}
		else {
			ultimateSize = this.BigNum.size();
			//inferior = num.BigNum.size();
		}

		for (int i = 0 ; i < ultimateSize; i++) {
			for (int j = 0 ; j < ultimateSize; j++) {
				
			}
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

		//System.out.println(result.BigNum.size());
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
		if (this.firstOneBigger(num)) {
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
		else if (!this.firstOneBigger(num)) {
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
		result.removeAllZeros();

		return result;

	}

	private void removeAllZeros() {
			for (int i = this.BigNum.size()-1; i >= 0; i--) { 
				if (this.BigNum.get(i) == 0) {
					if (i == 0) {
						if (this.BigNum.get(i) == 0) {
						}		
					}		

					else {
						this.BigNum.remove(i);					
				
					}
				}
				else 
					break;
			}
		}
	

	private boolean firstOneBigger(BigInt num) {
		boolean isGreater = false;
		if (this.BigNum.size() == num.BigNum.size()) {
			for (int i = num.BigNum.size()-1; i >= 0; i--) {

				if (this.BigNum.get(i) == num.BigNum.get(i)) {

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

	private boolean isItValid(String num) {

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

	private void setsign(String num) {
		if (num.charAt(0) == '-')
			this.sign = false;
		else
			this.sign = true;			
	}

	public String toString() {
		String result = "";
		for (int i = BigNum.size(); i > 0 ; i--) {
			result += Integer.toString(BigNum.get(i-1));
		}
		if (!this.sign)
			return "-" + result;
		else
			return result;
	}

}