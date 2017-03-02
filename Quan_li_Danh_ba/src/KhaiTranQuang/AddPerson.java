package KhaiTranQuang;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AddPerson extends JDialog implements ActionListener, KeyListener {
	private Information infor;
	private MainFrame mainFrame;
	
	public AddPerson(MainFrame mainFrame){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Thêm liên hệ");
		
		this.mainFrame=mainFrame;
		infor=new Information(mainFrame.getData().getListGroup());
		
		add(creatMainPanel());
		pack();      //Auto update UI
		setLocationRelativeTo(null);
	}
	
	private JPanel creatMainPanel(){
		JPanel panel =new JPanel (new BorderLayout());
		panel.add(infor, BorderLayout.CENTER);
		panel.add(creatButtonPanel(), BorderLayout.PAGE_END);
		return panel;
	}
	
	private JPanel creatButtonPanel(){
		JPanel panel =new JPanel(new GridLayout(1,3,10,10));
		panel.setBorder(new EmptyBorder(10,10,10,10));
		panel.add(creatButton("Thêm"));
		panel.add(creatButton("Nhập lại"));
		panel.add(creatButton("Huỷ"));
		return panel;
	}
	
	private JButton creatButton(String btnName){
		JButton btn = new JButton(btnName);
		btn.addActionListener(this);
		return btn;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="Nhập lại"){
			reset();
		}
		if(e.getActionCommand()=="Huỷ"){
			cancel();
		}
		if(e.getActionCommand()=="Thêm"){
			addPerson();
		}
		
	}
	
	private void cancel(){
		clearInput();
		setVisible(false);
	}
	
	private void reset(){
		clearInput();
	}
	
	private void clearInput(){
		infor.getTfName().setText("");
		infor.getTfPhone().setText("");
		infor.getTfAddres().setText("");
		infor.getTfName().requestFocus();
	}
	
	private void addPerson(){
		Person p= infor.getInfor();
		if(p!=null){
			clearInput();
			setVisible(false);
			mainFrame.getData().getListPerson().add(p);
			mainFrame.updateData();
		}
	}
	
	//Display
	public void display(boolean visible){
		loadInfor();
		setVisible(visible);
	}
	
	private void loadInfor(){
		infor.setListGroup(mainFrame.getData().getListGroup());
		infor.loadListGroup();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
