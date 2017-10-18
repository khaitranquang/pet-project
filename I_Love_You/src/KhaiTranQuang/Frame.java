package KhaiTranQuang;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Frame extends JFrame implements MouseListener, ActionListener, MouseMotionListener{
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JPanel hiddenPanel;
	private JPanel questionPanel;
	private JPanel answerPanel;
	
	private JButton btnNo;
	private JButton btnHiddenNo;
	private JPanel hiddenButtonPanel;
	
	private boolean isVisible = false;
	
	//Create JFrame
	public Frame(){
		setTitle("Do you love me Demo");
		setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
		setSize(300, 150);
		setResizable(false);
		setLocationRelativeTo(null);	
		add(createMainPanel());
	}
	
	// Create MainPanel
	private JPanel createMainPanel(){
		mainPanel = new JPanel (new GridLayout(3, 1, 5, 5));
		mainPanel.add(createPanelQuestion());
		mainPanel.add(createHiddenPanel());
		mainPanel.add(createPanelAnswer());
		return mainPanel;
	}
	
	// Create panel Question - Do you love me?
	private JPanel createPanelQuestion(){
		questionPanel = new JPanel();
		questionPanel.add(new JLabel("Do you love me?"));
		return questionPanel;
	}
	
	// Create panel Answer with 2 buttons
	private JPanel createPanelAnswer(){
		answerPanel = new JPanel();
		answerPanel.add(createButtonPanel());
		return answerPanel;
	}
	
	//Create ButtonPanel
	private JPanel createButtonPanel(){
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		buttonPanel.add(createButtonYes());
		buttonPanel.add(createButtonNo());
		
		return buttonPanel;
	}
	
	private JButton createButtonYes(){
		JButton btnYes = new JButton("Yes");
		btnYes.addActionListener(this);
		return btnYes;
	}
	
	private JButton createButtonNo(){
		btnNo = new JButton("No");
		btnNo.addMouseMotionListener(this);
		return btnNo;
	}
	

	// Create Hidden Panel
	private JPanel createHiddenPanel(){
		hiddenPanel = new JPanel();
		hiddenPanel.add(createHiddenButtonPanel());
		//hiddenPanel.setVisible(isVisible);
		return hiddenPanel;
	}
	
	// Create Hidden Button Panel
	private JPanel createHiddenButtonPanel(){
		hiddenButtonPanel = new JPanel (new GridLayout(1, 2, 5, 5));
		hiddenButtonPanel.add(new JButton("      ")).setVisible(false);
		hiddenButtonPanel.add(createHiddenButtonNo()).setVisible(false);
		return hiddenButtonPanel;
	}
	
	private JButton createHiddenButtonNo(){
		btnHiddenNo = new JButton("No");
		btnHiddenNo.addMouseMotionListener(this);
		return btnHiddenNo;
	}
	

	@Override
	public void mouseClicked(MouseEvent evtClick) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent evtMouse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent evtClick) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		if (evt.getActionCommand() == "Yes"){
			JOptionPane.showMessageDialog(mainFrame, "I know <3");
		}
	}

	@Override
	public void mouseDragged(MouseEvent evtMouse) {
		
	}

	@Override
	public void mouseMoved(MouseEvent evtMouse) {
		// TODO Auto-generated method stub
		isVisible = !isVisible;
		hiddenPanel.setVisible(isVisible);
		btnHiddenNo.setVisible(isVisible);
		btnNo.setVisible(!isVisible);	
	}
	
}
