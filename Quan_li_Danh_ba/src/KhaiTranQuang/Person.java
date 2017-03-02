package KhaiTranQuang;

import java.io.Serializable;
import java.util.Comparator;

public class Person implements Serializable {      //Lop sap xep tung hang, doc lap voi platform
	private String name, group, addres, phone, lastName;
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	
	public String getGroup(){
		return group;
	}
	public void setGroup(String group){
		this.group=group;
	}

	public String getAddres(){
		return addres;
	}
	public void setAddres(String addres){
		this.addres=addres;
	}
	
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone=phone;
	}
	
	public String getLastName(){
		return lastName;
	}
	public void setLastName(String lastName){
		this.lastName=lastName;
	}
	
	public Person(){
	}
	
	public String getLastOfName(){
		String str=getName();
		String subString="";
		String sub="";
		for (int i=str.length()-1;i>=0;i--){
			char ch=str.charAt(i);
			if(ch!=' '){
				subString+=ch;
			}
			else{
				 break;
			}
		}
		
		for (int i=subString.length()-1; i>=0;i--){
			char ch=subString.charAt(i);
			sub+=ch;
		}
		if(sub.equals("")) return str;
		return sub;
	}
	
	public Person(String name, String phone, String group, String addres){
		this.name=name;
		this.phone=phone;
		this.group=group;
		this.addres=addres;
		this.lastName=getLastOfName();
		System.out.println("Last name is "+ this.lastName);
	}
	
	public String toString(){
		return name+", "+phone+", "+group+", "+addres;
	}
	
	public static Comparator<Person> PersonNameComparator=new Comparator<Person>(){
		@Override
		public int compare(Person p1, Person p2) {
			// TODO Auto-generated method stub
			return p1.getLastName().compareTo(p2.getLastName());
		}
	};
	
	
	
	
	
	
	
}
