package KhaiTranQuang;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Information extends JPanel implements ActionListener, KeyListener{
	private String lbStringName="Tên", lbStringPhone="Điện thoại", 
				   lbStringGroup="Nhóm", lbStringAddres="Địa chỉ";
	private JTextField tfName, tfPhone, tfAddres;
	private JComboBox<String> cbGroup;
	private int padding=10;   //Do rong giua cac duong bien
	private ArrayList<String> listGroup;
	
	public JTextField getTfName(){
		return tfName;
	}
	public void setTfName(JTextField tfName){
		this.tfName=tfName;
	}
	
	public JTextField getTfPhone(){
		return tfPhone;
	}
	public void setTfPhone(JTextField tfPhone){
		this.tfPhone=tfPhone;
	}
	
	public JTextField getTfAddres(){
		return tfAddres;
	}
	public void setTfAddres(JTextField tfAddres){
		this.tfAddres=tfAddres;
	}
	
	public JComboBox<String> getCbGroup(){
		return cbGroup;
	}
	public void setCbGroup(JComboBox<String> cbGroup){
		this.cbGroup=cbGroup;
	}
	
	public ArrayList<String> getListGroup(){
		return listGroup;
	}
	public void setListGroup(ArrayList<String> listGroup){
		this.listGroup=listGroup;
	}
	
	public Information(ArrayList<String> listGroup){
		this.listGroup=listGroup;
		setLayout(new BorderLayout());
		setBorder(new TitledBorder("Thông tin"));
		add(creatPanelLabel(), BorderLayout.WEST);
		add(creatPanelTextField(), BorderLayout.CENTER);
	}
	
	
	private JPanel creatPanelLabel() {
		// TODO Auto-generated method stub
		JPanel panel =new JPanel(new GridLayout(4,1,5,5));
		panel.setBorder(new EmptyBorder(padding, padding, padding, padding));
		panel.add(creatJLabel(lbStringName));
		panel.add(creatJLabel(lbStringPhone));
		panel.add(creatJLabel(lbStringAddres));
		panel.add(creatJLabel(lbStringGroup));
		return panel;
	}
	
	private JLabel creatJLabel(String name){
		JLabel label=new JLabel(name);
		return label;
	}
		
	//Creat Panel TextField
	private JPanel creatPanelTextField() {
		// TODO Auto-generated method stub
		JPanel panel =new JPanel(new GridLayout(4,1,5,5));
		panel.setBorder(new EmptyBorder(padding, padding, padding, padding));
		tfName=creatJTextField();
		tfPhone=creatJTextField();
		tfAddres=creatJTextField();
		
		String[] list =new String[listGroup.size()];
		list=listGroup.toArray(list);
		cbGroup=new JComboBox<String>(list);
		JPanel groupPanel=new JPanel(new BorderLayout(5,5));
		groupPanel.add(cbGroup, BorderLayout.CENTER);
		groupPanel.add(creatJButton("Nhóm mới"), BorderLayout.EAST);
		
		panel.add(tfName);
		panel.add(tfPhone);
		panel.add(tfAddres);
		panel.add(groupPanel);
		
		return panel;
	}
	
	private JTextField creatJTextField(){
		JTextField tf =new JTextField(20);
		tf.addKeyListener(this);
		return tf;
	}
	
	private JButton creatJButton(String btnName){
		JButton btn =new JButton(btnName);
		btn.addActionListener(this);
		btn.setMargin(new Insets(5,2,5,2));
		return btn;
	}
	
	
	//Phuong thuc tra ve thong tin mot nguoi
	public Person getInfor(){
		if(checkInfor(tfName) && checkInfor(tfPhone) && checkInfor(tfAddres) && checkInfor(cbGroup)){
			Person p= new Person(tfName.getText().trim().toString(), tfPhone.getText().trim().toString(), 
					cbGroup.getSelectedItem().toString(), tfAddres.getText().trim().toString());
			return p;
		}
		else return null;
	}
	
	//Check Infor
	private boolean checkInfor(JTextField tf){
		//Neu khong co data
		if(tf.getText().trim().equals("")){
			tf.requestFocus();   //Cho con tro nhay den cho khong co du lieu
			return false;
		}
		
		//Kiem tra xem So ddien thoai co la so khong
		if(tf.equals(tfPhone)){
			try{
				Double.parseDouble(tfPhone.getText());
			}catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Số điện thoại phải là sô", "Nhập lại số điện thoại", JOptionPane.OK_OPTION);
				return false;
			}
		}
		return true;
	}
	private boolean checkInfor(JComboBox cb){
		if(listGroup.size()==0){
			cb.requestFocus();
			return false;
		}
		return true;
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
	
	//Bat su kien khi nhan vao button "Nhom moi"
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="Nhóm mới"){
			String groupName=JOptionPane.showInputDialog("Nhập nhóm mới");
			if(groupName!=null && groupName.trim().length()>0){
				addGroup(groupName);
			}
		}
	}
	
	private void addGroup(String groupName){
		int index=indexGroupName(groupName);
		if(index<0){
			listGroup.add(groupName);
			Collections.sort(listGroup);
			index=indexGroupName(groupName);
		}
		loadListGroup();
		cbGroup.setSelectedItem(index);
	}
	
	public int indexGroupName(String groupName){
		for(int i=0; i<listGroup.size();i++){
			if(listGroup.get(i).equals(groupName)) return i;
		}
		return -1;
	}
	
	public void loadListGroup(){
		String list[] =new String[listGroup.size()];
		list=listGroup.toArray(list);
		cbGroup.setModel(new DefaultComboBoxModel<String>(list));
	}
}
