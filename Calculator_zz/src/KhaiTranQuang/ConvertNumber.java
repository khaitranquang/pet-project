package KhaiTranQuang;

public class ConvertNumber {
	private String myPowAxB(int number){
		String numString = number +"";
		String numPow = "⁰¹²³⁴⁵⁶⁷⁸⁹";
		String result = "";
		for(int i = 0; i< numString.length(); i++){
			result = result + numPow.charAt(Integer.parseInt(numString.charAt(i)+""));
		}
		return result;
	}
	
	// Doi so numberDouble thanh tich cac thua so nguyen to
	protected String primeMutil (double numberDouble){
		if( numberDouble >=0 && numberDouble == (long) numberDouble){	//Voi numberDouble la so khong am va la so nguyen thi...
			long num = (long) numberDouble;		//num la so nguyen tu numberDouble
			//powCount la so mu tach ra duoc
			int powCount = 0, i =2;
			int m = (int) Math.sqrt(num) + 1;
			String s = "";
			
			//Duyet tu 2 den can bac 2 cua num
			while(i<m){
				powCount = 0; // so mu cua i
				//tach thanh i^n
				while (num >0 && num % i ==0){
					powCount ++;
					num = num/i;
				}
				//Cong them phan tu neu no chia het
				if(powCount >0){
					if(s.length()>0){
						s+="x";		//Them dau x vao
					}
					s +=i;			//Them so vao
					if(powCount>=2){
						s+= myPowAxB(powCount);		//Cong them so mu
					}
				}
				if(i==2) i++;
				else i +=2;		//Bo qua cac so chan
			}
			//Kiem tra num sau vong lap co la so nguyen to khong?
			//Neu la so nguyen to thi cho them vao
			if(num > 1){
				if (s.length()> 0) s+="x";
				s+= num;
			}
			else if(s.length()==0){
				s+=num;
			}
			return s;
		}
		return "-1";
	}
	
	//Kiem tra 1 chuoi co la chuoi trong he radix (2,8,10,16) khong?
	protected boolean isRadixString(String str, int radix){
		str = str.toUpperCase();		//bien chuoi da cho thanh chuoi in hoa
		int length = str.length();		//Luu do dai cua chuoi
		int countDot = 0; 				//Bien dem so luong dau .
		String radixChar ="";
		
		if(radix == 2){
			radixChar="01.";
		}
		else if(radix == 8){
			radixChar ="01234567.";
		}
		else if(radix == 10){
			radixChar ="0123456789.";
		}
		else if(radix == 16){
			radixChar ="0123456789ABCDEF.";
		}
		else return false;
		
		for(int i=0; i<length ; i++){
			char c = str.charAt(i);
			if(c=='.') countDot ++;			//Neu thay dau . thi tang bien countDot len 1;4
			if(radixChar.indexOf(c) <0|| countDot >1){		//Neu khong co ton tai ki tu trong radixChar. hoac so dau . >1 thi  false
				return false;
			}
		}
		return true;
	}
	
	//Chuyem so tu he radix (2, 8, 16) sang he 10
	protected double stringRadixToDouble (String str, int radix){
		str = str.toUpperCase();
		int length = str.length();
		int indexDot = -1;		//bien bieu thi vi tri cua dau .
		double num = -1;		//so sex duoc chuyen tu radixChar thanh Double
		String radixChar = "0123456789ABXCDEF.";
		//Neu khong phai la chuoi trong he radix thi tra ve -1
		if(isRadixString(str, radix)==false) return num;
		else{
			//Xac dinh vi tri dau .
			indexDot = str.indexOf('.');
			if(indexDot > 0){		//Ton tai dau .	
				String intString = str.substring(0, indexDot);		//Chuoi phan nguyen
				String floatString = str.substring(indexDot+1, length);		//Chuoi phan thap phan
				
				//Chuyen phan nguyen vao num
				num = Integer.parseInt(intString, radix);		//Chuyen tu intString o he radix sang he 10
				//Cong them phan thap phan
				for(int i=0; i<floatString.length(); i++){
					num += radixChar.indexOf(floatString.charAt(i)) / (Math.pow(radix, i+1));
				}
			}
			else{		//Khong ton tai dau .
				num = Integer.parseInt(str, radix);
			}
		}
		return num;
	}
	
	//Chuyen tu so he 10 sang chuoi he radix (2, 8,16)
	protected String doubleToStringRadix(double num, int radix, int countRount){
		String str = "";		//Chuoi tra ve
		String radixChar = "0123456789ABCDEF.";
		long intNum = (long) num;
		double floatNum = num - intNum;
		
		//Doi so nguyen intNum sang he radix
		String intString = Long.toString(intNum, radix).toUpperCase();
		String floatString = "";
		//Doi phan thap phan
		while(floatNum > 0 && countRount >0){
			floatNum = floatNum * radix;
			floatString += radixChar.charAt((int) floatNum);
			floatNum = floatNum - (int) floatNum;
			countRount --;
		}
		
		str = str + intString;		//Them phan nguyen vao chuoi ket qua
		if(floatString.length()>0){
			str +="."+floatString;	//Them phan thap pahn
		}
		return str;
	}
	
}
