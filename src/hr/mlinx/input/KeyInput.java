package hr.mlinx.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import hr.mlinx.viewer.JuliaViewer;

public class KeyInput extends KeyAdapter {
	
	private JuliaViewer viewer;
	
	public KeyInput(JuliaViewer viewer) {
		super();
		
		this.viewer = viewer;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			ConstantInput ci = new ConstantInput(viewer);
			
			int option = JOptionPane.showConfirmDialog(viewer,
													   ci,
													   "Input",
													   JOptionPane.OK_CANCEL_OPTION);
			
			if (option == JOptionPane.OK_OPTION) {
				
				try {
					
					double real = Double.parseDouble(ci.getReal().getText());
					double imaginary = Double.parseDouble(ci.getImaginary().getText());
					
					viewer.setConstant(real, imaginary);
					
				} catch (NumberFormatException ex) {
				}
				
			}
		}
	}
}
