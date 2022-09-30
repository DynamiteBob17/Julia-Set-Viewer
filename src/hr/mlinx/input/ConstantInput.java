package hr.mlinx.input;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.mlinx.viewer.JuliaViewer;


public class ConstantInput extends JPanel {
	private static final long serialVersionUID = 6029735245890812958L;
	
	private JTextField real;
	private JTextField imaginary;
	
	public ConstantInput(JuliaViewer viewer) {
		super();
		
		real = new JTextField(Double.toString(viewer.getConstant()[0]));
		imaginary = new JTextField(Double.toString(viewer.getConstant()[1]));
		
		setLayout(new GridLayout(3, 2));
		add(new JLabel("Enter constant:"));
		add(new JLabel(""));
		add(new JLabel("Real:"));
		add(real);
		add(new JLabel("Imaginary:"));
		add(imaginary);
	}

	public JTextField getReal() {
		return real;
	}

	public JTextField getImaginary() {
		return imaginary;
	}
	
}
