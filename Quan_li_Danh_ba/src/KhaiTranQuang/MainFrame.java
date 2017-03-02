package KhaiTranQuang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;



public class MainFrame extends JFrame implements ActionListener, KeyListener, Serializable{
	public static final int width=600;
	public static final int height=400;
	public static final int tableWidth=width-10;
	public static final int tableHeight=height-130;
	private final Color ColorDefault=Color.white, colorNotFound=Color.pink;
	
	private String[] listSearch={"Tên", "Số điện thoại", "Nhóm", "Địa chỉ"};
	private String[] titleItem=listSearch;
	
	private JTable table;
	private JTextField tfSearch;
	private JComboBox<String> cbSearchType;
	private JLabel lbStatus; 
	private int typeSearch=0;
	
	private Data data= new Data();
	private ArrayList<Person> dataSearch = new ArrayList<Person>();
	
	private AddPerson addPerson;
	private EditPerson editPerson;
	
	public Data getData(){
		return data;
	}
	public void setData(Data data){
		this.data=data;
	}
	
	public MainFrame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setTitle("Demo Quản lí danh bạ by KhaiTranQuang");
		setResizable(false);
		setLocationRelativeTo(null);
		
		add(creatMainPanel());
		read();
		updateTable(this.data.getListPerson());
	}
	
	private JPanel creatMainPanel(){
		JPanel mainPanel =new JPanel(new BorderLayout());
		mainPanel.add(creatSearchPanel(), BorderLayout.PAGE_START);
		mainPanel.add(creatContactsPanel(), BorderLayout.CENTER);
		mainPanel.add(creatStatusPanel(), BorderLayout.PAGE_END);
		return mainPanel;
	}
	
	private JPanel creatSearchPanel(){
		JPanel searchPanel=new JPanel(new BorderLayout(5,5));
		//searchPanel.setBorder();
		searchPanel.add(new JLabel("Tìm kiếm"), BorderLayout.WEST);
		tfSearch=creatJTextField();
		searchPanel.add(tfSearch, BorderLayout.CENTER);
		
		cbSearchType=creatListSearch();
		searchPanel.add(cbSearchType, BorderLayout.EAST);
		return searchPanel;
	}
	
	private JTextField creatJTextField(){
		JTextField tf =new JTextField(20);
		tf.addKeyListener(this);
		return tf;
	}
	
	private JComboBox<String> creatListSearch(){
		JComboBox<String> cb=new JComboBox<String>(listSearch);
		cb.addActionListener(this);
		return cb;
	}
	
	private JPanel creatContactsPanel(){
		JPanel panel =new JPanel();
		panel.add(creatTablePanel());
		return panel;
	}
	
	private JPanel creatTablePanel(){
		JPanel panel =new JPanel(new BorderLayout());
		table=creatTable();
		loadData(table);
		JScrollPane scrollPanel = new JScrollPane(table);
		scrollPanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
		
		panel.add(scrollPanel, BorderLayout.CENTER);
		return panel;
	}
	
	private JTable creatTable(){
		JTable table =new JTable();
		table.setCellSelectionEnabled(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//Che do khi chon (o day chi chon duoc mot lien he)
		return table;
	}
	
	private void loadData(JTable table){
		String data[][]=null;
		DefaultTableModel tableModel=new DefaultTableModel(data, titleItem){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		table.setModel(tableModel);	
	}
	
	//Creat Status Panel
	private JPanel creatStatusPanel(){
		JPanel panel=new JPanel (new BorderLayout());
		//panel.setBorder();
		lbStatus = new JLabel();
		searchStatus(0, "", "");
		panel.add(lbStatus, BorderLayout.CENTER);
		panel.add(creatButtonPanel(), BorderLayout.EAST);
		return panel;
	}
	
	private void searchStatus(int count, String status, String textFind){
		tfSearch.setBackground(ColorDefault);
		
		if(textFind.length()==0){
			lbStatus.setText("  Nhập thông tin tìm kiếm");
		}
		else if(count>0 && textFind.length()>0){
			lbStatus.setText("  Tìm thấy "+count + status);
		}
		else if( count == 0 && textFind.length()>0){
			tfSearch.setBackground(colorNotFound);
			lbStatus.setText("  Không tìm thấy "+status);
		}
	}
	
	private JPanel creatButtonPanel(){
		JPanel buttonPanel =new JPanel();
		buttonPanel.add(creatButton("Thêm"));
		buttonPanel.add(creatButton("Sửa"));
		buttonPanel.add(creatButton("Xoá"));
		return buttonPanel;
	}
	
	private JButton creatButton(String btnName){
		JButton btn =new JButton(btnName);
		btn.addActionListener(this);
		return btn;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		updateTable(search(typeSearch));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command=e.getActionCommand();
		if(command=="Thêm"){
			themDanhBa();
			return;
		}
		if(command=="Sửa"){
			suaDanhBa();
			return;
			
		}
		
		if(command=="Xoá"){
			delete();
			return;
		}
		
		if(e.getSource()==cbSearchType){
			resetSearch();
			return;
		}
	}
	
	//Update data
	public void updateData(){
		Collections.sort(data.getListPerson(), Person.PersonNameComparator);
		write();
		if(dataSearch.size()>0){
			updateTable(search(typeSearch));
		}
		else{
			updateTable(this.data.getListPerson());
		}
	}
	
	private void updateTable(ArrayList<Person> list){
		String data[][]=convertData(list);
		DefaultTableModel tableModel =new DefaultTableModel(data, titleItem){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		table.setModel(tableModel);
	}
	
	private String[][] convertData(ArrayList<Person> list){
		int size=list.size();
		String data[][] =new String[size][titleItem.length];
		for(int i=0; i<size; i++){
			Person person =list.get(i);
			data[i][0] =person.getName();
			data[i][1] =person.getPhone();
			data[i][2] =person.getGroup();
			data[i][3] =person.getAddres();
		}
		return data;
	}
	
	public void write(){  //Ghi them Object vao file
		try{
			FileOutputStream f = new FileOutputStream("data");
			ObjectOutputStream oStream = new ObjectOutputStream(f);
			oStream.writeObject(data);
			oStream.close();
		} catch(IOException e){
			System.out.println("Error write File");
		}
	}
	
	//Them Danh ba
	private void themDanhBa(){
		if(addPerson==null){
			addPerson =new  AddPerson(this);
		}
		addPerson.display(true);
		tfSearch.requestFocus();
	}
	
	//Sua Danh ba
	private void suaDanhBa(){
		System.out.println(table.getSelectedRow());
		int index = findIndexOfData();
		if(index>=0){
			if(editPerson==null){
				editPerson=new EditPerson(this);
			}
			editPerson.setIndexRow(index);
			editPerson.display(true);
		}
		else{
			JOptionPane.showMessageDialog(null, "Chọn một người để sửa");
		}
		tfSearch.requestFocus();
	}
	
	//Xoa danh ba
	private void delete(){
		int index=findIndexOfData();
		if(index>=0){
			int select = JOptionPane.showOptionDialog(null, "Bạn có muốn xoá "+data.getListPerson().get(index).getName()+"?",
						 "Xoá", JOptionPane.YES_NO_OPTION,
						 JOptionPane.QUESTION_MESSAGE, null, null, null);
			if(select==0){
				data.getListPerson().remove(index);
				updateData();
			}
			else{
				JOptionPane.showMessageDialog(null, "Chọn một người để xoá");
			}
		}
		tfSearch.requestFocus();
	}
	
	private int findIndexOfData(){
		int index = table.getSelectedRow();
		if(dataSearch.size()>0){
			for(int i=0; i<data.getListPerson().size();i++){
				if(dataSearch.get(index)==data.getListPerson().get(i)){
					System.out.println("index of data"+i);
					return i;
				}
			}
		}
		return index;
	}
	
	private ArrayList<Person> search(int typeSearch){
		int size=data.getListPerson().size();
		dataSearch.clear();
		String textFind = tfSearch.getText().trim().toLowerCase();
		if(textFind.length()==0){
			searchStatus(0, "", "");
			return this.data.getListPerson();
		}
		String textType="";
		for(int i=0; i<size;i++){
			Person p= data.getListPerson().get(i);
			String text="";
			if(typeSearch==0){
				text=p.getName();
				textType="  Người có tên phù hợp với \"";
			}
			if(typeSearch==1){
				text=p.getPhone();
				textType="  Người có số điện thoại phù hợp với \"";
			}
			if(typeSearch==2){
				text=p.getGroup();
				textType="  Người có nhóm phù hợp với \"";
			}
			if(typeSearch==3){
				text=p.getAddres();
				textType="  Người có địa chỉ phù hợp với \"";
			}
			text = text.trim().toLowerCase();
			if (text.indexOf(textFind) >= 0) {
				dataSearch.add(p);
			}
		}
		searchStatus(dataSearch.size(), textType + textFind + "\".", textFind);
		return dataSearch;
	}
	
	private void resetSearch(){
		typeSearch=cbSearchType.getSelectedIndex();
		tfSearch.setText("");
		tfSearch.requestFocus();
		searchStatus(0, "", "");
		updateData();
	}
	
	public void read(){
		Data data=null;
		try{
			File file=new File("data");
			if(!file.exists()){
				file.createNewFile();
			}
			FileInputStream is = new FileInputStream(file);
			ObjectInputStream inStream =new ObjectInputStream(is);
			data=(Data) inStream.readObject();
			this.data=data;
			inStream.close();
		}catch(ClassNotFoundException e){
			System.out.println("Class Not Found");
		}catch(IOException e){
			System.out.println("Error Read File");
		}
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
	
}
