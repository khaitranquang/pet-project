package KhaiTranQuang;

import java.util.Arrays;
import java.util.Stack;

public class Balan {
	private boolean isError = false;
	private String varString[] = {"ans", "va", "vb", "vc", "vd", "ve", "vf"};
	private String constString[]={"pi", "π", "e"};
	
	public double var[] = new double[varString.length];
	private double cons[]={Math.PI, Math.PI, Math.E};
	
	private boolean isDegOrRad = true;
	private int radix =10, sizeRound = 10;
	private ConvertNumber convertNumber = new ConvertNumber();
	
	public void setError(boolean isError){
		this.isError = isError;
	}
	public boolean isError(){
		return isError;
	}
	
	public void setSizeRound(int sizeRound){
		this.sizeRound = sizeRound;
	}
	public int getSizeRound (){
		return sizeRound;
	}
	
	public void setRadix(int radix){
		this.radix = radix;
	}
	public int getRadix(){
		return radix;
	}
	
	public void setDegOrRad(boolean isDegOrRad){
		this.isDegOrRad = isDegOrRad;
	}
	public boolean isDegOrRad(){
		return isDegOrRad;
	}
	
	protected boolean isIntegerNumber(double num){		//Kiem tra xem 1 so co la so nguyen khong?
		long a = (long) num;
		if(a==num) return true;
		return false;
	}
	
	private String myRound(double num, int size){
		if(isIntegerNumber(num)){
			return Long.toString((long) num);
		}
		else{
			int n = size - Long.toString((long) num).length();
			num = Math.round(num * Math.pow(10, n))/ Math.pow(10, n);	//Math.round tra ve long hoac int gan nhat
			if(isIntegerNumber(num)){
				return Long.toString((long) num);
			}
			else return Double.toString(num);
		}
	}
	
	// num!
	private long factorial(int num){
		if(num >=0){
			long result = 1;
			for(int i=1; i<= num; i++){
				result *= i;
			}
			return result;
		}
		return -1;
	}
	
	//Chinh hop chap b cua a = a!/(a-b)!
	private long permutation (int a, int b){
		if(a<b) return -1;
		if(a>=0 && b>=0){
			return (factorial(a) / factorial(a-b) );
		}
		return -1;
	}
	
	//To hop chap b cua a
	private long combination(int a, int b){
		if(a < b) return -1;
		if(a >= 0 && b>=0){
			return (factorial(a) / (factorial(b) * factorial(a-b)));
		}
		return -1;
	}
	
	//
	private double convertToDeg (double num){
		return num * 180 / Math.PI;
	}
	
	//
	private double convertToRad (double num){
		return num * Math.PI /180;
	}
	
	
	//Kiem tra chuoi s co la number khong ? (bien cung la number)
	protected boolean isNumber(String s){
		if(radix != 10 && convertNumber.isRadixString(s, radix)) return true;
		if( isVarOrConst(s)) return true;
		try{
			Double.parseDouble(s);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	private boolean isNumber(char c){		//Kiem tra 1 ki tu c co la number khong (bien cung la number)
		String numberChar = ".0123456789abcdef";
		int index = numberChar.indexOf(c);	//Vi tri cua c trng numberChar
		if(radix == 10 && index>=0 && index<=10){
			System.out.println(c +" is a number");
			return true;
		}
		if(radix == 16 && index >=0 ){
			System.out.println(c + " is a number");
			return true;
		}
		if(radix == 8 && index >= 0 && index <= 8){
			System.out.println(c + " is a number");
			return true;
		}
		if(radix == 2 && index>=0 && index <= 2){
			System.out.println(c +" is a number");
			return true;
		}
		System.out.println(c + "isn't a number");
		return false;
	}
	
	////////////////////////////////////////////
	private int indexVar(String s){		//Tra ve vi tri cua s trong varString[] hay kiem tra xem s co la var khong?
		for (int i=0; i<varString.length; i++){
			if (s.equals(varString[i])) return i;
		}
		return -1;
	}
	
	private int indexConst (String s){	//Tra ve vi tri cua s trong constString[] hay kiem tra xem s co la const khong?
		for(int i=0; i<constString.length; i++){
			if(s.equals(constString[i])) return i;
		}
		return -1;
	}
	
	private boolean isVarOrConst(String s){			//Kme tra xem s co la var hay const khong?
		if(indexVar(s) >=0 || indexConst(s) >= 0) return true;
		return false;
	}
	//////////////////////////////////////////
	
	//Chuyen chuoi sang so
	private double stringToNumber(String s){
		int index =indexVar(s);
		if(index >= 0){
			return var[index];
		}
		
		index = indexConst(s);
		if(radix != 10){		//Chuoi dang o he radix, khong o he co so 10
			if(convertNumber.isRadixString(s, radix)){		//La mot chuoi trong he radix (2, 8, 16)
				return convertNumber.stringRadixToDouble(s, radix);
			}
			else{		//Chuoi khong ton tai o he radix
				isError = true;
				System.out.println("Number is error in radix = " +radix);
			}
		}
		
		if(index >= 0){
			return cons[index];
		}
		if (s.charAt(s.length() - 1) == '.') {
			isError = true;
			System.out.println("Error number have '.'");
			return -1;
		}
		
		try{
			return Double.parseDouble(s);
		}catch(Exception e){
			isError = true;
			System.out.println("Error parse number");
		}
		return -1;
	}
	
	//Chuyen chuoi sang so
	public String numberToString(double num, int radix, int size){
		if(radix != 10){
			return convertNumber.doubleToStringRadix(num, radix, size);
		}
		return myRound(num, size);
	}
	
	//Kiem tra xem s co la toan tu khong
	private boolean isOperator(String s){
		String operator[]={"+", "-", "*", "/", "√", "▫√","ncr", "npr", "^", "~", "sqrt", "!", "%", ")", "(",
						   "x²","sin", "cos", "tan", "arcsin", "arccos", "arctan", "log", "sto", "mod", "and"
						   ,"or", "xor", "not","&", "<<", ">>"};
		Arrays.sort(operator);
		if(Arrays.binarySearch(operator, s) > -1){
			return true;
		}
		else return false;
	}
	
	// Thiet lap thu tu uu tien 
	private int priority(String s){
		int p = 1;
		if(s.equals("sto")) return p;
		p++;
		
		if(s.equals("+") || s.equals("-")) return p;
		p++;
		
		if(s.equals("*") || s.equals("/")) return p;
		p++;
		
		if(s.equals("&") || s.equals("and") || s.equals("xor") || s.equals("or") ||
		   s.equals("mod") || s.equals("<<") || s.equals(">>")) return p;
		p++;
		
		if(s.equals("ncr") || s.equals("npr")) return p;
		p++;
		
		if(s.equals("not")) return p;
		p++;
		
		if(s.equals("~")) return p;
		p++;
		
		if(s.equals("tan") || s.equals("sin") || s.equals("cos") || s.equals("arctan") || 
		   s.equals("arccos") || s.equals("arcsin") || s.equals("log")) return p;
		p++;
		
		if(s.equals("√") || s.equals("▫√") || s.equals("!") || s.equals("^") || s.equals("x²") || s.equals("sqrt")) return p;
		p++;
		return 0;
	}
	
	//Kiem tra xem co phai toan tu mot ngoi khong?
	private boolean isOneMath (String s){
		String operator[] = {"tan", "sin", "cos", "arcsin", "arccos", "arctan", "!", "log", "sqrt", "~", "not", "(", "!"};	//? (????
		Arrays.sort(operator);
		if(Arrays.binarySearch(operator,s) > -1) return true;
		else return false;
	}
	
	//Kiem tra co phai pheo toan dang sau 
	private boolean isPostOperator(String s){
		String postOperator[] = {"!", "x²" };
		for(int i =0 ; i< postOperator.length; i++){
			if(s.equals(postOperator[i]) ) {
				System.out.println(s + "is post operator");
				return true;
			}
		}
		System.out.println(s + "isn't post operator");
		return false;
	}
	
	//Kiem tra cac ki tu lien nhau co phai la mot tu khong?
	private boolean isWord (char c1, char c2){
		char word[][] = {{'s','i','n'}, {'p', 'i'}, {'c','o','s'}, {'t','a','n'}, {'n','√'}, 
						 {'a','r','c','s','i','n'}, {'a','r','c','c','o','s'}, {'a','r','c','t','a','n'},
						 {'s','q','r','t'}, {'a','n','s'}, {'s','t','o'}, {'n','c','r'}, {'n','p','r'},
						 {'>','>'}, {'<','<'}, {'a','n','d'}, {'x','o','r'}, {'n','o','t'}, {'o','r'}, {'m','o','d'}};
		for(int i = 0; i < word.length; i++){
			for(int j =0 ; j < word[i].length; j++){
				for(int k = j+1; k < word[i].length; k++){
					if(c1 == word[i][j] && c2 == word[i][k]){
						System.out.println("is word: " + c1+ " " + c2);
						return true;
					}
				}
			}
		}
		System.out.println("isn't word:" + c1 + " " + c2);
		return false;
	}
	
	//Kiem trao chuoi s co la mot tu khong?
	private boolean isWord (String s){
		String word[] = {"sin","pi", "cos", "tan", "n√", "arcsin", "arccos", "arctan", "sqrt",
						 "ans", "sto", "ncr", "npr", ">>", "<<", "and", "xor", "not", "or", "mod",
						 "va", "vb", "vc", "vd", "ve"};
		for(int i =0 ; i< word.length; i++){
			if(s.equals(word[i])) return true;
		}
		return false;
	}
	
	//Chuan hoa chuoi
	private String standardize(String s){
		s = s.trim();
		s = s.replaceAll("\\s+", " ");
		return s;
	}
	
	//Cat chuoi thanh cac phan tu
	private String[] trimString(String s){
		String temp[] = s.split(" ");
		return temp;
	}
	
	//Chuan hoa bieu thuc
	private String standardizeMath(String[] s){
		String s1 = "";
		
		int open = 0, close = 0;
		for (int i =0 ; i<s.length; i++){
			if(s[i].equals("(")) open++;
			else if(s[i].equals(")")) close++;
		}
		
		for(int i = 0; i<s.length; i++){
			// chuyen ...)(....  thanh ....)*(.....
			if(i>0 && isOneMath(s[i]) && (s[i-1].equals(")") || isNumber(s[i-1]))){
				s1 = s1 + "* ";
			}
			//3!2!
			if(i>0 && isPostOperator(s[i-1]) && isNumber(s[i])){
				s1 = s1 + "* ";
			}
			//so duong
			if((i == 0 || (i>0 && !isNumber(s[i-1]) && !s[i-1].equals(")") && !isPostOperator(s[i-1])) )
			    && (s[i].equals("+"))
			    && (isNumber(s[i+1]) || s[i+1].equals("+"))) continue;
			//check so am
			if((i == 0 || (i>0 && !isNumber(s[i-1]) && !s[i-1].equals(")") && !isPostOperator(s[i-1])) )
				    && (s[i].equals("-"))
				    && (isNumber(s[i+1]) || s[i+1].equals("-"))) {
				s1 = s1 + "~ ";
			}
			//VD: chuyen 6Pi, ....)Pi thanh 6*Pi, ...) * Pi
			else if(i>0 && ((isNumber(s[i-1]) || s[i-1].equals(")")) && isVarOrConst(s[i]))){
				s1 = s1 + "* " + s[i] + " ";
			}
			else s1 = s1 + s[i] + " ";
		}
		
		//them cac dau ")" vao cuoi beu con thieu
		for(int i = 0; i<(open - close); i++){
			s1 += ") ";
		}
		System.out.println("StandardizeMath: " + s1);
		return s1;
	}
	
	//Xu li bieu thuc nhan vao thanh cac phan tu
	private String processInput(String sMath){
		sMath = sMath.toLowerCase();
		sMath = standardize(sMath);		//Chuan hoa
		String s = "", temp = "";
		for(int i = 0; i<sMath.length(); i++){
			//isn't a number
			if(!isNumber(sMath.charAt(i)) || (i<sMath.length()-1 && isWord(sMath.charAt(i), sMath.charAt(i+1))) ){
				s += " " + temp;
				temp = "" + sMath.charAt(i);
				
				//is a operator and isn't word
				if(isOperator(sMath.charAt(i) + "") && i<sMath.length()-1 && !isWord(sMath.charAt(i), sMath.charAt(i+1)) ){
					s +=" " + temp;
					temp = "";
				}
				//isn't a operator but is a word
				else{
					i++;
					while (i<sMath.length() && !isNumber(sMath.charAt(i)) && (!isOperator(sMath.charAt(i) + "")) || 
						   (i<sMath.length()-1 && isWord(sMath.charAt(i-1), sMath.charAt(i)))){
						temp += sMath.charAt(i);
						i++;
						if(isWord(temp)){
							s += " " + temp;
							temp = "";
							break;
						}
					}
					i--;
					s += " " + temp;
					temp = "";
				}
			}
			//is a Number
			else{
				temp = temp + sMath.charAt(i);
			}
		}
		s += " " + temp;
		
		System.out.println("Process input 1: "+s);
		s = standardize(s);
		s = standardizeMath(trimString(s));
		System.out.println(s);
		return s;
	}
	
	private String postFix(String math){
		String[] elementMath = trimString(math);
		
		String s1 ="";
		Stack<String> S = new Stack<String>();
		for(int i = 0; i<elementMath.length; i++){		//Duyet het cac phan tu
			if(!isOperator(elementMath[i])){		//Neu khong la toan tu
				s1 = s1 + elementMath[i] + " ";		//Xuat them element vao s1
			}
			else{		//c la toan tu
				if(elementMath[i].equals("(")){
					S.push(elementMath[i]); 		// c la "(" -> Day vao Stack
				}
				else{
					if(elementMath[i].equals(")")){		//c la ")"
						//Dyet lai cac phan tu trong Stack
						String temp = "";
						do{
							temp = S.peek();
							if(!temp.equals("(")){
								s1 = s1 + S.peek() + " ";
							}
							S.pop();
						}while(!temp.equals("("));
					}
					else{
						//Stack khong rong trong khi phan tu cua stack co do u tien >= phan tu hien tai
						while(!S.isEmpty() && priority(S.peek()) >= priority(elementMath[i]) && !isOneMath(elementMath[i])){
							s1 = s1 + S.pop() + " ";
						}
						S.push(elementMath[i]); 		//Dua phan tu hien tai vao Stack
					}
				}
			}
		}
		
		while(!S.isEmpty()){
			s1 = s1 + S.pop() + " ";	//Neu Stack con phan tu thi day het vao s1
		}
		System.out.println("Balan: " + s1);
		return s1;
	}
	
	public Double valueMath(String math){
		math = processInput(math);
		math = postFix(math);
		String elementMath[] = trimString(math);
		Stack<Double> S = new Stack<Double>();
		double num = 0.0;
		double ans = 0.0;
		
		System.out.println("Element math: ");
		for (int i = 0; i<elementMath.length; i++){
			System.out.print(elementMath[i] + "\t");
			if(!isOperator(elementMath[i])){
				S.push(stringToNumber(elementMath[i]));
			}
			else{		//is operator
				if(S.isEmpty()){
					System.out.println("Stack is empty");
					isError = true;
					return 0.0;
				}
				double num1 = S.pop();
				String ei = elementMath[i];
				if(ei.equals("~")){
					num = - num1;
				}
				else if(ei.equals("sin")){
					if(isDegOrRad){
						num1 = convertToRad(num1);
					}
					num = Math.sin(num1);
				}
				else if(ei.equals("cos")){
					if(isDegOrRad){
						num1 = convertToRad(num1);
					}
					num = Math.cos(num1);
				}
				else if(ei.equals("tan")){
					if(isDegOrRad){
						num1 = convertToRad(num1);
					}
					num = Math.tan(num1);
				}
				else if(ei.equals("arcsin")){
					num = Math.asin(num1);
					if(isDegOrRad){
						num = convertToDeg(num);
					}
				}
				else if(ei.equals("arccos")){
					num = Math.acos(num1);
					if(isDegOrRad){
						num = convertToDeg(num);
					}
				}
				else if(ei.equals("arctan")){
					num = Math.atan(num1);
					if(isDegOrRad){
						num = convertToDeg(num);
					}
				}
				else if(ei.equals("log")){
					num = Math.log10(num1);
				}
				else if(ei.equals("%")){
					num = num1/100;
				}
				else if(ei.equals("x²")){ 
					num = Math.pow(num1, 2);
				}
				else if(ei.equals("√") || ei.equals("sqrt")){
					if(num1 >= 0){
						num = Math.sqrt(num1);
					}
					else {
						isError = true;
						System.out.println("Error sqrt");
						return 0.0;
					}
				}
				/*else if(ei.equals("not") || ei.equals("!")){
					if(isIntegerNumber(num1) && num1 >= 0 ){
						if(ei.equals("not")){
							num = ~(long) num1;
						}
						else if(ei.equals("!")){
							num = factorial((int) num1);
						}
					}
				}*/
				else if(ei.equals("not")){
					if(isIntegerNumber(num1) && num1 >= 0){
						num = ~ (long) num1;
					}
				}
				
				else if(ei.equals("!")){
					if(isIntegerNumber(num1) && num1 >= 0){
						num = factorial((int) num1);
					}
				}
				
				else if(!S.empty()){
					double num2 = S.peek();
					
					if(ei.equals("sto")){
						if(indexVar(elementMath[i-1]) >= 0){
							var[indexVar(elementMath[i-1])] = num2;
							ans = num2;
							return ans;
						}
						else{
							isError = true;
							System.out.println("Error sto");
							return 0.0;
						}
					}
					else if(ei.equals("+")){
						num = num2 + num1;
						S.pop();
					}
					else if(ei.equals("-")){
						num = num2 - num1;
						S.pop();
					}
					else if(ei.equals("*")){
						num = num2 * num1;
						S.pop();
					}
					else if(ei.equals("/")){
						if(num1 != 0){
							num = num2 / num1;
						}
						else{
							isError = true;
							//System.out.println("Error / 0");
							return 0.0;
						}
						S.pop();
					}
					else if(ei.equals("^")){
						num = Math.pow(num2, num1);
						S.pop();
					}
					else if(ei.equals("n√")){
						num = Math.pow(num1, (double) 1/num2);
						S.pop();
					}
					else if(isIntegerNumber(num1) && isIntegerNumber(num2)){
						if(ei.equals("ncr")){
							num = combination((int) num2, (int) num1);
							S.pop();
						}
						else if(ei.equals("npr")){
							num = permutation((int) num2, (int) num1);
							S.pop();
						}
						else if(ei.equals("and") || ei.equals("&")){
							num = (long) num2 & (long) num1;
							S.pop();			
						}
						else if(ei.equals("or")){
							num = (long) num2 | (long) num1;
							S.pop();
						}
						else if(ei.equals("xor")){
							num = (long) num2 ^ (long) num1;
							S.pop();
						}
						else if(ei.equals("mod")){
							num = (long) num2 % (long) num1;
							S.pop();
						}
						else if(ei.equals("<<")){
							num = (long) num2 << (long) num1;
							S.pop();
						}
						else if(ei.equals(">>")){
							num = (long) num2 >> (long) num1;
							S.pop();
						}
					}
				}
				else{
					System.out.println("Stack is empty");
					isError = true;
					return 0.0;
				}
				S.push(num);
			}
		}
		ans = S.pop();
		System.out.println("\nans = "+ans+"\t radix = "+radix);
		return ans;
	}
	
	public String primeMulti(double num){
		return convertNumber.primeMutil(num);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}


