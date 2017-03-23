package Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener {
	private JButton btnSend, btnClear, btnExit, btnLogin, btnLogout;
	private JTextField tfNick, tfNick1, tfMessage;
	private JTextArea taMess, taOnline;
	private JPanel panelLogin, panelChat;
	
	private Socket socketClient;
	private DataStream dataStream;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	public Client(){
		super("Client Demo");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				//exit();
			}
		});
		
		setSize(600, 400);
		addItem();
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	//--------------Xay dung giao dien---------------//
	private void addItem(){
		setLayout(new BorderLayout());
		
		btnSend = new JButton("Gửi");
		btnSend.addActionListener(this);
		btnClear = new JButton("Xoá");
		btnClear.addActionListener(this);
		btnExit = new JButton("Thoát");
		btnExit.addActionListener(this);
		btnLogin = new JButton("Đăng nhập");
		btnLogin.addActionListener(this);
		btnLogout = new JButton("Thoát đăng nhập");
		btnLogout.addActionListener(this);
		
		panelLogin =new JPanel(new FlowLayout());
		panelLogin.add(new JLabel("Nick Name: "));
		tfNick1 = new JTextField(20);
		panelLogin.add(tfNick1);
		panelLogin.add(btnLogin);
		panelLogin.add(btnExit);
		//panelLogin.setVisible(false);
		add(panelLogin, BorderLayout.NORTH);
		
		panelChat = new JPanel(new BorderLayout());
		
		JPanel panelViewNickname = new JPanel (new FlowLayout());	//Panel hien thi nickname, nam o dau panelChat
		panelViewNickname.add(new JLabel("Nickname: "));
		tfNick = new JTextField(20);
		panelViewNickname.add(tfNick);
		panelViewNickname.add(btnLogout);
		
		JPanel panelCenter = new JPanel (new BorderLayout());	//Panel nam o giua panelChat, gom textArea hien thi tin nhan, va danh sach online
		taMess = new JTextArea(10,20);
		taMess.setEditable(false);
		panelCenter.add(new JScrollPane(taMess),BorderLayout.CENTER);
		//panelCenter.add(new JLabel("    "));
		
		JPanel panelOnline = new JPanel (new BorderLayout());
		taOnline = new JTextArea(10,10);
		taOnline.setEditable(false);
		panelOnline.add(new JLabel("Danh sách Online: "), BorderLayout.NORTH);
		panelOnline.add(new JScrollPane(taOnline), BorderLayout.CENTER);
		panelCenter.add(panelOnline, BorderLayout.EAST);
		
		JPanel panelMessage = new JPanel(new FlowLayout());	//panel nam o cuoi, noi nhap tin nhan va gui di
		panelMessage.add(new JLabel("Tin nhắn: "));
		tfMessage = new JTextField(30);
		panelMessage.add(tfMessage);
		panelMessage.add(btnSend);
		panelMessage.add(btnClear);
		
		//Them cac panel vao panelChat
		panelChat.add(panelViewNickname, BorderLayout.NORTH);
		panelChat.add(panelCenter, BorderLayout.CENTER);
		panelChat.add(panelMessage, BorderLayout.SOUTH);
		panelChat.setVisible(false);
		add(panelChat, BorderLayout.CENTER);
	}
	
	public void go(){
		try {
			socketClient = new Socket("localhost", 8797);
			dataInputStream = new DataInputStream(socketClient.getInputStream());
			dataOutputStream = new DataOutputStream(socketClient.getOutputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Không thể kết nối, kiểm tra lại đường truyền","Lỗi kết nối", JOptionPane.WARNING_MESSAGE);
			System.exit(0);;
		}
	}
	
	public static void main(String[] args) {
		new Client().go();
	}
	
	private void sendMSG(String data){
		try {
			dataOutputStream.writeUTF(data);
			dataOutputStream.flush();		//Day du lieu vao luong
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getMSG(){
		String data = null;
		try {
			data= dataInputStream.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public void getMSG (String msg1, String msg2){
		int stt = Integer.parseInt(msg1);	//msg1 la su lua chon
		switch(stt){
			case 3:		//Tin nhan tu nguoi khac
				this.taMess.append(msg2);
				break;
			case 4:		//Update danh sach online
				this.taOnline.setText(msg2);
				break;
			case 5: 	//Server dong
				exit();
				break;	
			default: break;
		}
	}
	
	private void checkSend(String msg){
		if(msg.compareTo("\n")!=0){
			this.taMess.append("Tôi: "+msg);
			sendMSG("1");
			sendMSG(msg);
		}
	}
	
	private boolean checkLogin(String nick){		//Kiem tra dang nhap//
		if(nick.compareTo("")==0) return false;
		else if(nick.compareTo("0")==0) return false;
		else{
			sendMSG(nick);
			int stt = Integer.parseInt(getMSG());
			if(stt==0) return false;
			else return true;
		}
	}
	
	private void exit(){
		sendMSG("0");
		try {
			dataOutputStream.close();
			dataInputStream.close();
			socketClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnExit){
			dataStream.StopThread();
			exit();
		}
		else if(e.getSource()==btnClear){
			tfMessage.setText("");
		}
		else if(e.getSource()==btnSend){
			checkSend(tfMessage.getText()+"\n");
			tfMessage.setText("");
		}
		else if(e.getSource()==btnLogout){
			exit();
		}
		else if(e.getSource()==btnLogin){
			if(checkLogin(tfNick1.getText())){
				panelChat.setVisible(true);
				panelLogin.setVisible(false);
				tfNick.setText(tfNick1.getText());
				tfNick.setEditable(false);
				this.setTitle(tfNick.getText());
				taMess.append("Đã đăng nhập thành công \n");
				dataStream = new DataStream(this, this.dataInputStream);
			}
			else{
				JOptionPane.showMessageDialog(this, "Nick này đã tồn tại, vui lòng nhập lại","Lỗi", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}
