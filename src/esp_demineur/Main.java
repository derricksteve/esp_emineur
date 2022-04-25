package esp_demineur;

public class Main implements Runnable {
Gui gui=new Gui();


	public static void main(String[] args) {
	new Thread(new Main()).start();
	}
	
	@Override
	public void run() {
		while(true) {
			gui.repaint();
			if(gui.resetter==false) {
				gui.checkVitoryStatus();
				//System.out.println("victory "+gui.victoire + gui.perdu );
			}
			
		}
		
	}

}
