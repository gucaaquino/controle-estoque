package visao;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import modelo.dao.ConexaoBD;

public class LumanferApp {

	public static void main(String[] args) {
		LumanferMenu l = new LumanferMenu();

		l.setSize(1400, 850);
		l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l.setResizable(true);
		l.setLocationRelativeTo(null);
		l.getContentPane().setBackground(Color.WHITE);
		l.setExtendedState(JFrame.MAXIMIZED_BOTH);
		l.setVisible(true);
		
		l.addWindowListener(new WindowListener() {
			
			public void windowOpened(WindowEvent e) {
				ConexaoBD.getConexaoBD();
			}
			
			public void windowIconified(WindowEvent e) {
				
			}
			
			public void windowDeiconified(WindowEvent e) {
				
			}
			
			public void windowDeactivated(WindowEvent e) {

			}
			
			public void windowClosing(WindowEvent e) {

			}
			
			public void windowClosed(WindowEvent e) {
				
			}
			
			public void windowActivated(WindowEvent e) {
				
			}
		});

	}
}
