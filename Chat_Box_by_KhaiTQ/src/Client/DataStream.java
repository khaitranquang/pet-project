package Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class DataStream extends Thread{
	private Client client;
	private boolean run;
	private DataInputStream dataInputStream;
	
	public DataStream (Client client, DataInputStream dataInputStream){
		run = true;
		this.client= client;
		this.dataInputStream= dataInputStream;
		this.start();
	}
	
	public void run(){
		String msg1, msg2;
		while (run){
			try {
				msg1= dataInputStream.readUTF();	//Luong doc du lieu tu Server
				msg2= dataInputStream.readUTF();
				client.getMSG(msg1, msg2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
		dataInputStream.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void StopThread(){
		this.run=false;
	}
}
