package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnect extends Thread{
	public Socket client;		//Socket ket noi toi Client
	public Server server;
	
	private String nickName;
	
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	private boolean run;
	
	public ClientConnect(Server server, Socket client){
		try {
			this.server=server;
			this.client=client;
			dataOutputStream = new DataOutputStream(client.getOutputStream());
			dataInputStream  = new DataInputStream (client.getInputStream());
			run = true ;
			this.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Xu li dang nhap
	public void run(){
		String msg = null;
		while(run){
			nickName = getMSG();
			if(nickName.compareTo("0") == 0) logout();
			else {
				if(checkNick(nickName)){		//Da co 1 nickName nhu vay ton tai (bi trung)
					sendMSG("0");
				}
				else{
					server.user.append(nickName +" đã vào room.");
					server.sendAll(nickName, nickName +" đã tham gia cùng anh chị em...");
					server.listUser.put(nickName, this);	//Them user vua tham gia vao listUser
					server.sendAllUpdate(nickName);
					sendMSG("1");
					displayAllUser();
					
					while(run){
						int stt= Integer.parseInt(getMSG());
						switch(stt){
							case 0:		//User log out
								run = false;
								server.listUser.remove(nickName);
								exit();		//User thoat va thong bao dem cac client khac
								break;
							case 1:
								msg = getMSG();
								server.sendAll(nickName, nickName+": "+ msg);
								break;
						}
					}
				}
			}
		}
	}
	
	//Kiem tra xem nickName co trong listUser khong?
	private boolean checkNick(String nickName){
		return server.listUser.contains(nickName);
	}
	
	
	//Logout
	private void logout(){
		try {
			dataOutputStream.close();		//Dong tat ca outputStream, inputStream
			dataInputStream.close();
			client.close();					//Dong client
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//User thoat
	private void exit(){
		//Xu li viec khi co user thoat, Server gui thong tin den cac client khac con hoat dong
		try {
			server.sendAllUpdate(nickName);
			dataOutputStream.close();
			dataInputStream.close();
			client.close();
			server.user.append(nickName +" đã thoát!");
			server.sendAll(nickName, nickName+" đã thoát \n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//Send MSG
	private void sendMSG(String data){		//Gui tin nhan den cac client
		try {
			dataOutputStream.writeUTF(data);
			dataOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendMSG(String msg1, String msg2){
		sendMSG(msg1);
		sendMSG(msg2);
	}
	
	private String getMSG(){
		String data=null;
		try {
			data=dataInputStream.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	private void displayAllUser(){
		String name = server.getAllName();
		sendMSG("4");
		sendMSG(name);
	}
}
