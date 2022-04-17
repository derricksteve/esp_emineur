package esp_demineur;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.w3c.dom.events.MouseEvent;

public class Gui extends JFrame{
	int espace=15;
	int mx=-100;
	int my=-100;
	 Random rand=new Random();
	int[][] mines=new int[16][9];
	int[][] freres=new int [16][19];
	boolean[][] reveler=new boolean[16][9];
	boolean[][] drapeau=new boolean[16][9];
	
	
	
	
	public Gui() {
		this.setTitle("ESP DEMINEUR");
		//donner une taille a lecran
		this.setSize(1286,830);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(rand.nextInt(100)<20) {
					mines[i][j]=1;
				}else {
					mines[i][j]=0;
				}
				
			}
		}
		
		
		
		
		
		
		
		
		Board board=new Board();
		this.setContentPane(board);
		Move move=new Move();
		this.addMouseMotionListener(move);  
		Click click=new Click();
		this.addMouseListener(click);
		
	
	}
	public class Board extends JPanel{
		//fonction pour créer une composant
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			//fonction pour créer un rectangle
			g.fillRect(0,0,1280,800);
			
			for(int i=0; i<16; i++) {
				for (int j=0; j<9; j++) {
					g.setColor(Color.gray);
				/*	if(mines[i][j]==1) {
						g.setColor(Color.yellow);
					}*/
					if(mx>=espace+i*60&& mx<espace+i*60+80-2*espace &&my>=espace+j*80+26 && my< espace+j*80+26+80-2*espace) {
						g.setColor(Color.red); 
					}
					g.fillRect( espace+i*60, espace+j*80, 80-2*espace,80-2*espace);
					
				}
			}	
		}
	

	}
	public class Move implements MouseMotionListener{

		@Override
		public void mouseDragged(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(java.awt.event.MouseEvent e) {
			  mx=e.getX();
			   my=e.getY();
			System.out.print(mx+"le e e"+my);
			
		}
		
	}
	public class Click implements MouseListener{
		//lorsquon fera un clique de souris
		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
			if(dansX()!=-1 && dansY()!=-1) {
				System.out.print(mines[dansX()][dansY()]+"bon\n");
			}else System.out.print("mauvais\n");
			
		}
		//lorsquon va presser
		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
			System.out.print("presser\n");
			
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			System.out.print("relacher\n");
			
		}
		//lorsquon entre dans la vue
		@Override
		public void mouseEntered(java.awt.event.MouseEvent e) {
			System.out.print("Entrez\n");
			
		}
		//lorsquon ferme la vu

		@Override
		public void mouseExited(java.awt.event.MouseEvent e) {
			System.out.print("Sortie\n");
			
		}
		
		
	}
	public int dansX() {
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(mx>=espace+i*60&& mx<espace+i*60+80-2*espace &&my>=espace+j*80+26 && my< espace+j*80+26+80-2*espace) {
					return i;  
				}
				
				
			}
		}
		return -1;
		
	}
	public int dansY() {
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(mx>=espace+i*60&& mx<espace+i*60+80-2*espace &&my>=espace+j*80+26 && my< espace+j*80+26+80-2*espace) {
					return j;
				}	
			}
		}return -1;
		
	}

}
