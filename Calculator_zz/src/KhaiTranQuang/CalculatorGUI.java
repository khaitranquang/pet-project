package KhaiTranQuang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/*
 *  Xay dung UI cua ung dung
 */

public class CalculatorGUI extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersonUID = 1L;
	
	private int frameWidth = 300;
	private int frameHeight = 400;
	private int xframeShow	= 400;
	private int yframeShow = 100;
	
	private JTextField tfDisplay;
	private JLabel lbStats, lbAns, lbB, lbDOH;
	private int mode = 0;
	private JFrame frame ;
	
	private JPanel mainPanel;
	private JPanel displayPanel;
	private JPanel buttonPanel;
	
	private JButton btnArr[];		//Mang chua cac button
	private JButton btnArrSub[];	//Mang phu cac button
	private JRadioButton radDeg, radRad;
	private JRadioButton radHex, radOct, radDec, radBin;
	
	private String lbButton[];
	private String mathElement[];
	private String varElement[];
	private double ans = 0;
	
	private Color colorDisableStats = Color.lightGray;
	private Color colorEnnableStats = Color.black;
	
	private boolean isSTO = false;
	private Balan balan;
	private HelpAndAbout help, about;
	
	public CalculatorGUI(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(xframeShow, yframeShow, frameWidth, frameHeight);
		frame.setResizable(false);
		frame.setJMenuBar(createMenuBar());
		
		resetValue();		//Dat lai cac gia tri
		changeMode();		//Thay doi che do
	}
	
	//---------Create Menu Bar -----------//
	private JMenuBar createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		
		//Menu Mode
		JMenu menuMode = createMenu("Mode", KeyEvent.VK_M);
		menuMode.add(createMenuItem("Basic", KeyEvent.VK_B));
		menuMode.add(createMenuItem("Advanced", KeyEvent.VK_A));
		menuMode.add(createMenuItem("Program", KeyEvent.VK_P));
		menuMode.addSeparator();		//Duong ke ngang
		menuMode.add(createMenuItem("Exit", KeyEvent.VK_E));
		
		menuBar.add(menuMode);
		
		//Menu Help and About
		JMenu menuHelp =  createMenu("Help",0);
		menuHelp.add(createMenuItem("Help", KeyEvent.VK_H));
		menuHelp.add(createMenuItem("About", KeyEvent.VK_H));
		
		menuBar.add(menuHelp);
		return menuBar;
	}
	//------------------------------------------------------//
	
	//------------Item of Menu -----------------//
	// Create Menu
	private JMenu createMenu(String title, int key){
		JMenu menu = new JMenu(title);
		menu.setMnemonic(key);
		return menu;
	}
	// Create MenuItem
	private JMenuItem createMenuItem(String title, int key){
		JMenuItem menuItem = new JMenuItem (title, key);
		menuItem.addActionListener(this);
		return menuItem;
	}
	//----------------------------------------//
	
	//-------------Change Mode-------------------//
	private void changeMode(){
		if(mode == 0){
			frameWidth = 300;
			frameHeight = 380;
			frame.setTitle("Calculator - BASIC");
		}
		if(mode == 1){
			frameWidth = 460;
			frameHeight = 440;
			frame.setTitle("Calculator - ADVANCED");
		}
		if(mode == 2){
			frameWidth = 460;
			frameHeight = 440;
			frame.setTitle("Calculator - PROGRAM");
		}
		createListLabelButton(mode);		//Tao list label ung voi mode tuong ung\
		balan.setDegOrRad(true);			//
		balan.setRadix(10);					//
		
		frame.getContentPane().removeAll();
		frame.setSize(frameWidth, frameHeight);
		mainPanel = createMainPanel();
		frame.add(mainPanel);
		
		frame.getContentPane().validate();
		frame.setVisible(true);
		tfDisplay.requestFocus();	//Cho con tro den tfDisplay (noi hien thi ket qua)
	}
	
	//-------------------------Create MainPane---------------------//
	private JPanel createMainPanel(){
		JPanel mainPanel = new JPanel(new BorderLayout());
		displayPanel = createDisplayPanel();
		mainPanel.add(displayPanel, BorderLayout.NORTH);
		
		if(mode == 0){
			buttonPanel = createButtonBasicPanel();
		}
		if(mode == 1){
			buttonPanel = createButtonAdvancedPanel();
		}
		if(mode == 2){
			buttonPanel = createButtonProgramPanel();
		}
		
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.setBorder(new EmptyBorder(10,10,10,10));
		mainPanel.setVisible(true);
		
		return mainPanel;
	}
	
	//--Create Display Panel--//
	private JPanel createDisplayPanel(){
		JPanel panel = new JPanel (new BorderLayout());
		
		if(mode == 1){
			lbStats = new JLabel("sto");
			Font fontStats = lbStats.getFont().deriveFont(Font.PLAIN, 12f);
			lbStats.setFont(fontStats);
			lbStats.setForeground(colorDisableStats);
			lbStats.setBackground(Color.white);
			lbStats.setOpaque(true);
			panel.add(lbStats, BorderLayout.NORTH);
		}
		
		tfDisplay = new JTextField(frameWidth);
		Font fontDisplay = tfDisplay.getFont().deriveFont(Font.PLAIN, 25f);
		tfDisplay.setFont(fontDisplay);
		tfDisplay.setHorizontalAlignment(JTextField.RIGHT);		//Can le phai
		tfDisplay.setBorder(null);
		tfDisplay.addKeyListener(this); 		//Bat su kien an phim
		panel.add(tfDisplay, BorderLayout.CENTER);
		
		lbAns = new JLabel("0");
		Font fonAns = lbAns.getFont().deriveFont(Font.PLAIN, 35f);
		lbAns.setFont(fonAns);
		lbAns.setHorizontalAlignment(JLabel.RIGHT);
		lbAns.setBackground(Color.white);
		lbAns.setOpaque(true);
		panel.add(lbAns, BorderLayout.SOUTH);
		
		panel.setBorder(new EmptyBorder(0,0,10,0));
		
		return panel;
	}
	
	// Create button basic panel
	private JPanel createButtonBasicPanel(){
		JPanel buttonBasicPanel = new JPanel(new GridLayout(5,5,3,3));
		btnArr = addListButtonToPanel(lbButton, buttonBasicPanel);
		return buttonBasicPanel;
	}
	
	// Create button advance panel
	private JPanel createButtonAdvancedPanel(){
		//panel top
		JPanel panelLeft = new JPanel (new FlowLayout(FlowLayout.LEFT));
		ButtonGroup btnGroup = new ButtonGroup();
		radDeg = createRadio("Deg", true, panelLeft);
		btnGroup.add(radDeg);
		radRad = createRadio("Rad", false, panelLeft);
		btnGroup.add(radRad);
		
		JPanel panelRight = new JPanel (new GridLayout(1,7,3,3));
		panelRight.setBorder(new EmptyBorder(0,15,10,0));
		
		String lb[]={"STO", "vA", "vB", "vC", "vD", "vE", "vF"};
		varElement = lb;
		btnArrSub = addListButtonToPanel(lb, panelRight);
		
		JPanel panel1 =new JPanel (new BorderLayout());
		panel1.add(panelLeft, BorderLayout.WEST);
		panel1.add(panelRight, BorderLayout.CENTER);
		
		JPanel panel2 =new JPanel (new GridLayout(5,8,3,3));
		btnArr = addListButtonToPanel(lbButton, panel2);
		
		JPanel buttonAdvancedPanel = new JPanel(new BorderLayout());
		buttonAdvancedPanel.add(panel1, BorderLayout.NORTH);
		buttonAdvancedPanel.add(panel2, BorderLayout.CENTER);
		
		return buttonAdvancedPanel;
	}
	
	// Create button program button panel
	private JPanel createButtonProgramPanel(){
		//panel top
		JPanel panelLeft = new JPanel (new GridLayout(2,2));
		ButtonGroup btnGroup = new ButtonGroup();
		radBin = createRadio("Bin", false , panelLeft);
		btnGroup.add(radBin);
		radOct = createRadio("Oct", false, panelLeft);
		btnGroup.add(radOct);
		radDec = createRadio("Dec", true, panelLeft);
		btnGroup.add(radDec);
		radHex = createRadio("Hex", false, panelLeft);
		btnGroup.add(radHex);
		
		JPanel panelRight = new JPanel(new BorderLayout());
		lbDOH = new JLabel ("0(10)  = 0(16) = 0(8)");
		lbDOH.setHorizontalAlignment(JLabel.RIGHT);
		Font font = lbDOH.getFont().deriveFont(Font.PLAIN, 13f);
		lbDOH.setFont(font);
		panelRight.add(lbDOH, BorderLayout.PAGE_START);

		// panel binary
		String bin1 = "0000  0000  0000  0000  0000  0000  0000  0000(2)";
		lbB = new JLabel(bin1);
		lbB.setHorizontalAlignment(JLabel.RIGHT);
		lbB.setFont(font);
		panelRight.add(lbB, BorderLayout.PAGE_END);
		panelRight.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelRight.setAlignmentX(RIGHT_ALIGNMENT);
		
		JPanel panel1 = new JPanel (new BorderLayout());
		panel1.add(panelLeft, BorderLayout.WEST);
		panel1.add(panelRight, BorderLayout.CENTER);
		
		//panel bottom
		JPanel panel2 = new JPanel (new GridLayout(5,8,3,3));
		btnArr = addListButtonToPanel(lbButton, panel2);
		
		JPanel buttonProgramPanel = new JPanel(new BorderLayout());
		buttonProgramPanel.add(panel1, BorderLayout.NORTH);
		buttonProgramPanel.add(panel2, BorderLayout.CENTER);
		return buttonProgramPanel;
	}
	
	//Create button
	private JButton createButton(String title){
		JButton btn = new JButton(title);
		btn.addActionListener(this);
		btn.setMargin(new Insets(0,0,0,0));
		return btn;
	}
	//Create button and add to panel
	private JButton createButton (String title, JPanel panel){
		JButton btn =  createButton(title);
		panel.add(btn);
		return btn;
	}
	
	//Create Radio Button
	private JRadioButton createRadio(String title, boolean isSelect, JPanel panel){
		JRadioButton rad =new JRadioButton(title);
		rad.addActionListener(this);
		rad.setSelected(isSelect);
		panel.add(rad);
		return rad;
	}
	
	//Create List label Button
	private void createListLabelButton(int mode){
		if(mode == 0){
			String s[] ={"C", "CE", "<-", "(", ")", "7", "8", "9",
							"/", "√","4","5", "6", "*", "x²", "1", "2", "3",
							"-", "*10▫", "0",".", "Ans", "+", "="};
			lbButton = s;
			String s1[] = {"", "", "", "(", ")", "7", "8", "9",
					"/", "√","4","5", "6", "*", "x²", "1", "2", "3",
					"-", "*10^", "0",".", "Ans", "+","" };
			mathElement = s1;
			return ;
		}
		if(mode == 1){
			String s[] ={"C", "CE", "<-", "(", ")", "!", "a*b", "log", "7", "8", "9", "/"
						 ,"√", "▫√", "nCr", "nPr", "4", "5", "6", "*", "x²", "x▫", "sin",
						 "arsin", "1","2", "3", "-", "*10▫", "π", "cos", "arcos",
						 "0",".", "Ans", "+", "=", "e", "tan", "artan"};
			lbButton = s;
			
			String s1[]= {"", "", "", "(", ")", "!", "", "log", "7", "8", "9", "/"
					 ,"√", "▫√", "nCr", "nPr", "4", "5", "6", "*", "x²", "^", "sin",
					 "arcsin", "1","2", "3", "-", "*10^", "π", "cos", "arccos",
					 "0",".", "Ans", "+", "", "e", "tan", "arctan"};
			mathElement = s1;
			return;
		}
		if(mode ==2){
			String s[] = {"C", "CE", "<-", "(", ")", "!", "a*b", "Mod",
						  "7", "8", "9", "/", "√", "<<", ">>", "And",
						  "4", "5", "6", "*", "x²", "A", "B", "Or",
						  "1", "2", "3", "-", "*10▫", "C", "D", "Xor",
						  "0", ".", "Ans", "+", "=", "E", "F", "Not"};
			lbButton = s;
			
			String s1[]= {"","", "", "(", ")", "!", "", "Mod",
					  "7", "8", "9", "/", "√", "<<", ">>", "&",
					  "4", "5", "6", "*", "x²", "A", "B", "Or",
					  "1", "2", "3", "-", "*10^", "C", "D", "Xor",
					  "0", ".", "Ans", "+", "", "E", "F", "Not"};
			mathElement = s1;
			return;
		}
	}
	
	private JButton[] addListButtonToPanel(String lbArr[], JPanel panel){
		JButton[] arr = new JButton[lbArr.length];
		for (int i =0; i<lbArr.length; i++){
			arr[i] = createButton(lbArr[i], panel);
		}
		return arr;
	}
	
	 
	
	//------------------------Action--------------------------//
	// Dat lai gia tri cho Balan
	private void resetValue(){
		balan = new Balan();
		balan.setError(false);
		if(mode == 2){
			setRadix();
		}
		if(mode == 1){
			setDegOrRad();
		}
	}
	
	private void setRadix(){
		if(radBin.isSelected()){
			balan.setRadix(2);
		}
		if(radOct.isSelected()){
			balan.setRadix(8);
		}
		if(radDec.isSelected()){
			balan.setRadix(10);
		}
		if(radHex.isSelected()){
			balan.setRadix(16);
		}
		tfDisplay.requestFocus();
	}
	
	private void setDegOrRad(){
		if(radRad.isSelected()){
			balan.setDegOrRad(false);
		}
		if(radDeg.isSelected()){
			balan.setDegOrRad(true);
		}
		tfDisplay.requestFocus();
	}
	
	// Cho phep chen 1 ki tu vao vi tri con tro
	private void insertMathString(String str){
		int index = tfDisplay.getCaretPosition();
		StringBuilder s = new StringBuilder(tfDisplay.getText() + "");		//Copy
		s.insert(index, str);	//insert text at index control
		String s1 = new String (s);
		tfDisplay.setText(s1);
		tfDisplay.requestFocus();
		tfDisplay.setCaretPosition(index + str.length());
	}
	
	//Tra ve ket qua
	private void result(){
		balan.setError(false);
		ans = balan.valueMath(tfDisplay.getText());
		if(!balan.isError()){
			balan.var[0] = ans;
			lbAns.setText(balan.numberToString(ans, balan.getRadix(), balan.getSizeRound()));
		}
		else{
			lbAns.setText("Math error!");
		}
		//Hien thi trong che do program
		setLabelProgram();
	}
	
	//Hien thi trong che do program
	private void setLabelProgram(){
		if(mode ==  2){
			long num = 0;
			if(balan.isIntegerNumber(ans) && ans >=0 && !balan.isError()){
				num = (int) ans;
				lbDOH.setText(num+ "(10)= " + Long.toHexString(num).toUpperCase() + "(16)= " + Long.toOctalString(num) + "(8)");
				
				String bi = Long.toBinaryString(num);		//Binary
				StringBuilder s = new StringBuilder(bi);
				int i = s.length();
				while (i<32){
					s.insert(0, "0");
					i++;
				}
				bi = "";
				for(i = 1; i<=s.length(); i++){
					bi += s.charAt(i-1);
					if(i%4 == 0 && i<s.length()){
						bi += "  ";
					}
				}
				bi += "(2)";
				lbB.setText(bi);
				enableLabelProgram(true);
			}
			else{
				enableLabelProgram(false);
			}
		}
	}
	
	// Dat che do hien thi hay khong hien thi cho label Program
	private void enableLabelProgram (boolean is){
		if(mode == 2){
			lbDOH.setEnabled(is);
			lbB.setEnabled(is);
		}
	}
	
	//Action CE
	private void actionCE(){
		balan.setError(false);
		isSTO = false;
		if(mode == 1){
			lbStats.setForeground(colorDisableStats);
		}
		tfDisplay.setText("");
		tfDisplay.requestFocus();
		enableLabelProgram(true);
		lbAns.setText("0");
		if(mode == 2){
			lbDOH.setText("0(10)  = 0(16) = 0(8)");
			lbB.setText("0000  0000  0000  0000  0000  0000  0000  0000(2)");
		}
	}
	
	//Action Del
	private void actionDel(){
		int index = tfDisplay.getCaretPosition();
		StringBuilder s = new StringBuilder(tfDisplay.getText() + "");		//Copy
		if(index > 0){
			s.deleteCharAt(index-1);
			String s1 = new String(s);		//Convert
			tfDisplay.setText(s1);
			tfDisplay.setCaretPosition(index-1);
			 
		}
		tfDisplay.requestFocus();
	}
	
	
	@Override
	public void keyPressed(KeyEvent key) {
		// TODO Auto-generated method stub
		if(key.getKeyCode() == KeyEvent.VK_ENTER){
			result();
		}
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
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		String command = evt.getActionCommand();
		
		//Action on menu
		if(command == "Basic"){
			mode = 0;
			changeMode();
			return;
		}
		if(command == "Advanced"){
			mode = 1;
			changeMode();
			setDegOrRad();
			return;
		}
		if(command == "Program"){
			mode = 2;
			changeMode();
			setRadix();
			return;
		}
		if(command == "Exit"){
			System.exit(0);
		}
		/*if(command == "Help"){
			if(help == null){
				help = new HelpAndAbout(0, "Calculator - Help");
			}
			help.setVisisble(true);
		}
		if(command == "About"){
			if(about == null){
				about = new HelpAndAbout(0, "Calculator - About");
			}
			about.setVisible(true);
		}*/
		
		//Action on Radio
		if(evt.getSource() == radRad || evt.getSource()== radDeg){
			setDegOrRad();
			return;
		}
		
		//Action on special button
		if(evt.getSource() == btnArr[0]){
			resetValue();
			actionCE();
			return;
		}
		
		if(evt.getSource() == btnArr[1]){
			actionCE();
			return;
		}
		
		if(evt.getSource() == btnArr[2]){
			actionDel();
			return;
		}
		
		//Neu trong che do Advance
		if(mode == 1){
			if(evt.getSource() == btnArrSub[0]){
				if(!isSTO){
					isSTO = true;
					lbStats.setForeground(colorEnnableStats);
				}
				else{
					isSTO = false;
					lbStats.setForeground(colorDisableStats);
				}
			}
			
			else{
				for(int i = 0; i<btnArrSub.length; i++){
					if(evt.getSource() == btnArrSub[i]){
						if(isSTO){
							result();
							tfDisplay.setCaretPosition(tfDisplay.getText().length());
							insertMathString("->" + varElement[i]);
							balan.var[i]= ans;
							isSTO = false;
						}
						else {
							insertMathString(varElement[i]);
						}
					}
				}
				
				isSTO = false;
				lbStats.setForeground(colorDisableStats);				
	
			}
		}
		
		//Neu trong che do Program
		if(mode == 2){
			if(evt.getSource() == radBin || evt.getSource() == radHex || evt.getSource() == radOct || evt.getSource() == radDec){
				setRadix();
				return;
			}
		}
		
		////
		for(int i = 0 ; i< btnArr.length; i++){
			if(evt.getSource() == btnArr[i] && !mathElement[i].equals("")){
				insertMathString(mathElement[i]);
				return;
			}
			
		}
		
		if(command == "="){
			result();
			return;
		}
		
		if(command == "a*b"){
			result();
			if(!balan.isError()){
				lbAns.setText(balan.primeMulti(ans));
			}
			return;
		}
		
	}
	
}
