package esp_demineur;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.w3c.dom.events.MouseEvent;

public class Gui extends JFrame{
	

	int espace=11;
	int mx=-100;
	int my=-100;
	public int sourirex=505;
	public int sourirey=5;
	
	public int timex=800;
	public int timey=5;
	
	public Date endDate;
	
	public int vicMesx=800;
	public int vicMesy=-50;
	
	 String message="En cours";
	
	public boolean content=false;
	public boolean victoire=false;
	
	public boolean perdu=false;
	
	Date startDate=new Date();
	
	public int sourisMilieux=sourirex+28;
	public int sourisMilieuy=sourirey+28;
	
	public boolean resetter=false;
	
	public int[][] nbre_click_droit= new int[16][9];
	
	
	
	
	public long sec=0;

	
	
	int freres=0;
	 Random rand=new Random();
	 
	int[][] mines=new int[16][9];
	int[][] voisins=new int [16][19];
	boolean[][] reveler=new boolean[16][9];
	boolean[][] drapeau=new boolean[16][9];
	
	
	
	
	public Gui() {
		this.setTitle("MARIAME");
		//donner une taille a lecran
		this.setSize(1286,830);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(   rand.nextInt(100)     <20) {
					mines[i][j]=1;
					
				}else {
					mines[i][j]=0;
				}
				reveler[i][j]=false;
				nbre_click_droit[i][j]=0;
				
			}
		}
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				freres=0;
				for(int fx=0; fx<16; fx++) {
					for (int fy=0; fy<9; fy++) {
						//compter les frères miné
						if(frere(i, j, fx, fy)==true) {
							freres++;
						}
						
						
					}
				}//faire -1 si le clique a ete fait sur une mine
				if(mines[i][j]==1) voisins[i][j]=freres-1; else voisins[i][j]=freres;
						
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
			
			//g.fillRect( 200, 200,80);
			 g.setColor(Color.DARK_GRAY);
			//fonction pour créer un rectangle
			g.fillRect(0,0,1280,800);
			
			for(int i=0; i<16; i++) {
				for (int j=0; j<9; j++) {
					g.setColor(Color.gray);
					
					//verifier si la case es reveler
					
					
					if(reveler[i][j]==true) {
						g.setColor(Color.blue);
						if(mines[i][j]==1) {
							g.setColor(Color.red);
						}
					
					}
				
					if(mx>=espace+i*60&& mx<espace+i*60+80-2*espace &&my>=espace+j*80+80 && my< espace+j*80+80+80-2*espace) {
						g.setColor(Color.LIGHT_GRAY); 
					}//
					g.fillRect( espace+i*60, espace+j*80+60, 80-2*espace,80-2*espace);
				
					if(reveler[i][j]==true) {
						//g.setColor(Color.white);
						if(mines[i][j]==0 || voisins[i][j]==3) {
							g.setColor(Color.white);
							g.setFont(new Font("SansSerif",Font.BOLD,40));
							g.drawString(Integer.toString(voisins[i][j]), i*60+27, j*80+112);
						}else {
							g.setColor(Color.black);
							g.fillRect(espace+i*60+8+5, espace+j*80+5+60, 20, 40);
							g.fillRect(espace+i*60+5, espace+j*80+8+5+60, 40, 20);
							g.fillRect(espace+i*60+5+5, espace+j*80+5+5+60, 30, 30);
							g.fillRect(espace+i*60+5+5, espace+j*80+5+5+60, 30, 30);
						}
					}
					if(nbre_click_droit[i][j]==2 && reveler[i][j]==false) {
						g.setColor(Color.white);
						g.setFont(new Font("SansSerif",Font.BOLD,40));
						g.drawString("?", i*60+27, j*80+112);
						
					}
					
					
					if(nbre_click_droit[i][j]==1) {
						//inserer le drapeau dans la case
						g.setColor(Color.white);
						g.fillRect( i*60+27, j*80+112, 20, 5);
						g.fillRect( i*60+27+5, j*80+112-10, 10, 10);
						g.fillRect( i*60+27+5, j*80+112-10-10, 10, 10);
						g.fillRect( i*60+27+5, j*80+112-10-10-10, 10, 10);
						g.setColor(Color.red);
						g.fillRect( i*60+27+5-10, j*80+112-10-10-10, 10, 12);
						
						
						
					}	if(nbre_click_droit[i][j]==2) {
						//inserer le point d'intérogation dans la case
						g.drawString("?", i*60+27, j*80+112);
					}
					
					
					
					
				}
			}
			g.setColor(Color.yellow);
			g.fillOval(sourirex, sourirey, 55, 55);
			g.setColor(Color.black);
			g.fillOval(sourirex+12, sourirey+15, 10, 10);
			g.fillOval(sourirex+37, sourirey+15, 10, 10);
			if(content==true) {
				g.fillRect(sourirex+12,sourirey+40,30 , 5);
				g.fillRect(sourirex+10,sourirey+38,3 , 6);
				g.fillRect(sourirex+40,sourirey+38,3 , 6);
			}else {
				g.fillRect(sourirex+12,sourirey+38,30 , 5);
				g.fillRect(sourirex+10,sourirey+41,3 , 6);
				g.fillRect(sourirex+40,sourirey+41,3 , 6);
				
			}
			
			//timer
			g.setColor(Color.black);
			g.fillRect(timex, timey, 150, 55);
		sec=((new Date().getTime()-startDate.getTime())/1000);
		if(sec>1000) {
			sec=1000;
		}
		g.setColor(Color.white);
		if(victoire==true) {
			g.setColor(Color.green);
		}else if(perdu==true) g.setColor(Color.red);
		
		g.setFont(new Font("Tahoma",Font.PLAIN,60));
		if(sec<10) {
			g.drawString("00"+Long.toString(sec), timex	,timey+45);
		}else if(sec<100) {
			g.drawString("0"+Long.toString(sec), timex	,timey+45);
		}else {
			g.drawString(Long.toString(sec), timex	,timey+45);
		}
		//message a afficher lorsquil gagne
		
		if(victoire==true) {
			g.setColor(Color.green);
			message="Vous avec ganez";
			
		}else if(perdu==true) {
			g.setColor(Color.red);
			message="vous avez perdu";
		}
		if(victoire==true || perdu==true) {
			vicMesy=-50 +(int)(new Date().getTime()-endDate.getTime())/10;
			if(vicMesy>70) {
				//vicMesy=70;
			}
			//g.setFont(new Font)
			g.drawString(message, vicMesx	, vicMesy);
			//g.setColor(Color.white);
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
			System.out.print("-----cordonnee en x ="+mx +"cordonnee en y="+my+"\n");
			
		}
		
	}
	public class Click implements MouseListener{
		
		
	
		//lorsquon fera un clique de souris
		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
			
			
			if(e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
				
				
				if(dansX()!=-1 && dansY()!=-1) {
					nbre_click_droit[dansX()][dansY()] +=1;
					
					
					if(nbre_click_droit[dansX()][dansY()]==1) {
						System.out.println("---------------------------------le click droit"+nbre_click_droit[dansX()][dansY()]+" -----------------------");
						
						
					}else if(nbre_click_droit[dansX()][dansY()]==2) {
						System.out.println("---------------------------------le click droit"+nbre_click_droit[dansX()][dansY()]+" -----------------------");
						
					}else if(nbre_click_droit[dansX()][dansY()]==3) {
						System.out.println("---------------------------------le click droit"+nbre_click_droit[dansX()][dansY()]+" -----------------------");
						nbre_click_droit[dansX()][dansY()]=0;
					}
					
					System.out.print("--------le nombre de frère non miné est "+ voisins[dansX()][dansY()]+"----\n");
					
				}
	          }
			
		
			
			 mx=e.getX();
			   my=e.getY();
			//mettons reveler a true pour la case cliquer
		//	System.out.print("-----------cliquer----------\n");
			if(dansX()!=-1 && dansY()!=-1 && !(e.getButton() == java.awt.event.MouseEvent.BUTTON3) ) {
				reveler[dansX()][dansY()]=true;
				System.out.print("--------le nombre de frère non miné est "+ voisins[dansX()][dansY()]+"----\n");
				
			}
			//a chaque fois que le user clique on appelle les fonction pour verifier si le clique a ete faite sur une case 
			if(dansX()!=-1 && dansY()!=-1) {
				//System.out.print(mines[dansX()][dansY()]+"bon\n");
			}else System.out.print("mauvais\n");
			
			if(intSouris()==true) {
				resetAll();
			}
		}
		//lorsquon va presser
		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
		//	System.out.print("presser\n");
			
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			//System.out.print("relacher\n");
			
		}
		//lorsquon entre dans la vue
		@Override
		public void mouseEntered(java.awt.event.MouseEvent e) {
			//System.out.print("Entrez\n");
			
		}
		//lorsquon ferme la vu

		@Override
		public void mouseExited(java.awt.event.MouseEvent e) {
			//System.out.print("Sortie\n");
			
		}
		
		
	}
	public void checkVitoryStatus() {
		if(perdu==false) {
			for(int i=0; i<16; i++) {
				for (int j=0; j<9; j++) {
					if(reveler[i][j]==true && mines[i][j]==1) {
						perdu=true;
						content=true;
						endDate=new Date();
						
					}
					
				}
			}
		}
		if(totalBoxeReveled()>=144-totoalMines() && victoire==false) {
			
			victoire=true;
			endDate=new Date();
		}
		
	}
	public int totoalMines() {
		int total=0;
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(mines[i][j]==1) {
					total++;
				}
				
			}
		}
		return total;
		
	}
	public int  totalBoxeReveled() {
		int total=0;
		
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(reveler[i][j]==true) {
				total++;
				}
				
			}
		}
	
		return total;
		
	}
	public void resetAll() {
		resetter=true;
		startDate=new Date();
		content=true;
		victoire=false;
		perdu=false;
		 vicMesy=-50;
		
		
		
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(   rand.nextInt(100)     <20) {
					mines[i][j]=1;
					
				}else {
					mines[i][j]=0;
				}
				reveler[i][j]=false;
				drapeau[i][j]=false;
				
			}
		}
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				freres=0;
				for(int fx=0; fx<16; fx++) {
					for (int fy=0; fy<9; fy++) {
						//compter les frères miné
						if(frere(i, j, fx, fy)==true) {
							freres++;
						}
						
						
					}
				}//faire -1 si le clique a ete fait sur une mine
				if(mines[i][j]==1) voisins[i][j]=freres-1; else voisins[i][j]=freres;
						
			}
		}
		resetter=false;
		
	}
	public boolean intSouris() {
		int dif=(int)(Math.sqrt(Math.abs(mx-sourisMilieux)*Math.abs(mx-sourisMilieux)+Math.abs(my-sourisMilieuy)*Math.abs(my-sourisMilieuy)));
		if(dif<28)return true;
		return false;
	}
	public int dansX() {
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(    mx>=espace+i*60&& mx<espace+i*60+80-2*espace &&my>=espace+j*80+80 && my< espace+j*80+80+80-2*espace) {
					return i;  
				}
				
				
			}
		}
		return -1;
		
	}
	public int dansY() {
		for(int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(mx>=espace+i*60&& mx<espace+i*60+80-2*espace &&my>=espace+j*80+80 && my< espace+j*80+80+80-2*espace) {
					return j;
				}	
			}
		}return -1;
		
	}
	public boolean frere(int i,int j, int fx,int fy) {
		if(i-fx<2 && i-fx>-2 && j-fy<2 && j-fy>-2 && mines[fx][fy]==1 ) {
			return true;
		}
		return false;
		
	}

}
