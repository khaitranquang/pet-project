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

public class EditPerson extends JDialog implements ActionListener, KeyListener{
	private Information infor;
	private MainFrame mainFrame;
	private int indexRow;
	
	public int getIndexRow(){
		return indexRow;
	}
	public void setIndexRow(int indexRow){
		this.indexRow=indexRow;
	}
	
	public EditPerson(MainFrame mainFrame){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setTitle("Sửa liên hệ");
		
		this.mainFrame=mainFrame;
		infor=new Information(mainFrame.getData().getListGroup());
		
		add(creatMainPanel());
		pack();
		setLocationRelativeTo(null);
	}
	
	private JPanel creatMainPanel(){
		JPanel panel =new JPanel(new BorderLayout());
		panel.add(infor, BorderLayout.CENTER);
		panel.add(creatButtonPanel(), BorderLayout.PAGE_END);
		return panel;
	}
	
	private JPanel creatButtonPanel(){
		JPanel panel =new JPanel(new GridLayout(1,2,10,10));
		panel.setBorder(new EmptyBorder(10,10,10,10));
		panel.add(creatButton("Xong"));
		panel.add(creatButton("Huỷ"));
		return panel;
	}
	
	private JButton creatButton(String btnName){
		JButton btn=new JButton(btnName);
		btn.addActionListener(this);
		return btn;
	}
	
	private void loadInfor(){
		Person p=mainFrame.getData().getListPerson().get(indexRow);
		infor.setListGroup(mainFrame.getData().getListGroup());
		infor.loadListGroup();
		infor.getTfName().setText(p.getName());
		infor.getTfPhone().setText(p.getPhone());
		infor.getTfAddres().setText(p.getAddres());
		infor.getCbGroup().setSelectedIndex(infor.indexGroupName(p.getGroup()));
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
		if(e.getActionCommand()=="Xong"){
			editPerson();
		}
		if(e.getActionCommand()=="Huỷ"){
			cancel();
		}
	}
	
	private void editPerson(){
		Person p=infor.getInfor();
		if(p!=null){
			clearInput();
			setVisible(false);
			mainFrame.getData().getListPerson().set(indexRow, p);
			mainFrame.updateData();
		}
	}
	
	private void cancel(){
		clearInput();
		setVisible(false);
	}
	
	private void clearInput(){
		infor.getTfName().setText("");
		infor.getTfPhone().setText("");
		infor.getTfAddres().setText("");
		infor.getTfName().requestFocus();
	}
	public void display(boolean visible){
		loadInfor();
		setVisible(visible);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
