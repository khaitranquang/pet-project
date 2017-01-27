//Chuong trinh Co caro Demo

package KhaiTranQuang;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class caro extends JPanel implements ActionListener{
	JPanel pan =new JPanel();
	public JFrame frame=new JFrame();
	public int n=12,m=12,num=0, diem=0;
	public JButton btn[][]=new JButton[n][m];
	int pos[][] =new int[m][n];   //possition
	
	public static void main(String[] args) {
		caro Caro =new caro();
		Caro.add();
	}
	
	//Add
	public void add(){
		frame.setTitle("Cờ caro by KhaiTranQuang");
		frame.add(pan);
		pan.setLayout(new GridLayout(n,m));
		
		//Creat buttons and add into JPanel
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				btn[i][j] = new JButton();  //Creat a button
				btn[i][j].addActionListener(this);
				pan.add(btn[i][j]);
			}
		}
		
		//Set background
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				btn[i][j].setBackground(Color.white);
			}
		}
		frame.setVisible(true);
		frame.setSize(600,600);
		frame.setLocationRelativeTo(null); 						//Setting frame is center of screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Close
	}
	
	//Event
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				if(e.getSource()==btn[i][j] && btn[i][j].getText()!="X" && btn[i][j].getText()!="O"){
					if(diem %2 ==0){       //X danh truoc
						btn[i][j].setText("X");
						btn[i][j].setForeground(Color.RED); //Danh dau o vua danh co mau do
						diem++;
						//Kiem tra xem thang chua?
						if(win(i,j,btn[i][j].getText())){
							btn[i][j].setBackground(Color.red);
							JOptionPane.showMessageDialog(null,"X win!","Game over!",JOptionPane.INFORMATION_MESSAGE);
							for(int i1=0;i1<n;i1++){
								for(int j1=0;j1<m;j1++){
									btn[i1][j1].setText("");
									btn[i1][j1].setBackground(Color.white);
								}
							}
						}
					}
					else{          //Den luot O
						btn[i][j].setText("O");
						btn[i][j].setForeground(Color.BLACK); //Danh dau o vua danh co mau do
						diem++;
						//Kiem tra xem thang chua?
						if(win(i,j,btn[i][j].getText())){
							btn[i][j].setBackground(Color.green);
							JOptionPane.showMessageDialog(null,"O win!","Game over!",JOptionPane.INFORMATION_MESSAGE);
							for(int i1=0;i1<n;i1++){
								for(int j1=0;j1<m;j1++){
									btn[i1][j1].setText("");
									btn[i1][j1].setBackground(Color.white);
								}
							}
						}
					}
					
				}
			}
		}
	}
	
	//Check win
	public boolean win(int x, int y, String name){
		int k, j;
		int d=0;
		//Kiem tra chieu doc
		for(k=-6;k<6;k++){
		
			if(x+k>=0 && x+k<n){    
				if(btn[x+k][y].getText()==name){
					d++;
				}
				else if(d<5){
					d=0;
				}
			}
		}
		if(d>=5) return true;
		else d=0;
		
		//Kiem tra ngang
		for(k=-6;k<6;k++){
			if(y+k>=0 && y+k<n){
				if(btn[x][y+k].getText()==name){
					d++;
				}
				else if(d<5){
					d=0;
				}
			}
		}
		if(d>=5) return true;
		else d=0;
		
		//Kiem tra cheo
		for(k=-6,j=-6;k<6 && j<6;k++,j++){
			if(y+k>=0 && y+k<m && x+j>=0 && x+j<n){
				if(btn[x+j][y+k].getText()==name){
					d++;
				}
				else if(d<5){
					d=0;
				}
			}
		}
		if(d>=5) return true;
		else d=0;
		for(k=-6,j=6;k<6 && j>=-6;k++,j--){
			if(y+k>=0 && y+k<n && x+j>=0 && x+j<m){
				if(btn[x+j][y+k].getText()==name) {
					d++;
				}
				else if(d<5){
					d=0;
				}
			}
		}
		if(d>=5) return true;
		return false;
	}
}
