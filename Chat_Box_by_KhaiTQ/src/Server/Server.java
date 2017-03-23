package Server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame implements ActionListener{
	private JButton close;
	public JTextArea user;		//Vung hien thi thong tin server,thong tin ket noi
	private ServerSocket server;
	public Hashtable<String, ClientConnect> listUser;
	
	public Server(){
		super("Server Demo");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
				try {		//Gui tin nhan den tat ca server
					server.close();
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		setSize(400, 400);
		addItem();		//Thiet lap cac button, area hien thi
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	private void addItem(){
		setLayout(new BorderLayout());
		add(new JLabel("Trạng thái Server..."), BorderLayout.NORTH);
		
		user=new JTextArea(10,10);
		user.setEditable(false);
		add(new JScrollPane(user), BorderLayout.CENTER);
		
		close=new JButton("Close Server");
		close.addActionListener(this);
		add(close, BorderLayout.SOUTH);
		
		user.append("Máy chủ được mở! \n");
	}
	
	public static void main(String[] args) {
		new Server().go();
	}
	
	private void go(){		//Tien hanh ket noi
		try {
			listUser= new Hashtable<String, ClientConnect>();
			server =new ServerSocket(8797);		//Khoi tao serverSocket 
			while(true){
				Socket client =server.accept();	//Cho phep client ket noi toi socketServer
				new ClientConnect(this, client);
				user.append("Máy chủ được kết nôi, sẵn sàng thực hiện");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			user.append("Kết nối không thành công");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		//Bat su kien n]khi nhan nut CloseServer
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="Close Server"){
			try {
				server.close();							//Dong server
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				user.append("Không thể đóng Server");
			}
			System.exit(0);
		}
	}
	
	public void sendAll(String from, String msg){
		Enumeration e =listUser.keys();			//Lay keys tu HashMap
												//Enumeration lop liet ke
		String name= null;
		while(e.hasMoreElements()){
			name=(String) e.nextElement();		//Ep kieu ve String, name la 1 ten lay tu listUser
			if(name.compareTo(from)!=0){
				listUser.get(name).sendMSG("3", msg);
			}
		}
	}
	
	public void sendAllUpdate(String from){		//Gui thong tin update tu server toi client
		Enumeration e =listUser.keys();
		String name= null;
		while(e.hasMoreElements()){
			name=(String) e.nextElement();
			if(name.compareTo(from)!=0){
				listUser.get(name).sendMSG("4", getAllName());
			}
		}
	}
	
	public String getAllName(){		//Tra ve tat ca ten user
		Enumeration e =listUser.keys();
		String name="";
		while(e.hasMoreElements()){
			name += (String) e.nextElement() +"\n";
		}
		
		return name;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
