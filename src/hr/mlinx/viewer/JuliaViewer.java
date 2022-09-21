package hr.mlinx.viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

import hr.mlinx.plotting.Complex;
import hr.mlinx.util.Util;

public class JuliaViewer extends JPanel implements MouseMotionListener, MouseListener {
	private static final long serialVersionUID = -4042144767211969357L;
	
	private static final int WIDTH = (int) (500 * Util.SCALE);
	private static final int HEIGHT = (int) (500 * Util.SCALE);
	private static final int REAL_MIN = -2;
	private static final int REAL_MAX = 2;
	private static final int IMAG_MIN = -2;
	private static final int IMAG_MAX = 2;
	private static final int MAX = 150;
	
	private Complex constant;
	private BufferedImage juliaSet;
	private int[] pixels;
	private Color[] colors;
	private Color[] gradientColors;
	
	public JuliaViewer() {
		super();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseMotionListener(this);
		addMouseListener(this);
		constant = new Complex(0, 0);
		colors = new Color[] {
				new Color(0, 7, 100),
				new Color(32, 107, 203),
				new Color(237, 255, 255),
				new Color(255, 170, 0),
				new Color(165, 42, 42),
				new Color(0, 2, 0)
		};
		plotJuliaSet();
		
		JFrame frame = new JFrame("Julia Set");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g.drawImage(juliaSet, 0, 0, null);
		
		g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, (int) Util.FONT_SIZE));
		g2.setColor(Color.WHITE);
		g2.drawString(constant.toString().replace('Z', 'C'), 10, 25);
	}
	
	private void plotJuliaSet() {
		juliaSet = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) juliaSet.getRaster().getDataBuffer()).getData();
		gradientColors = Util.createGradientColors(colors);
		
		double x, y;
		Complex z;
		int iterations;
		double smoothed;
		int colorIdx;
		
		for (int i = 0; i < pixels.length; ++i) {
			x = i % WIDTH;
			y = i / WIDTH % HEIGHT;
			x = Util.map(x, 0, WIDTH, REAL_MIN, REAL_MAX);
			y = Util.map(y, 0, HEIGHT, IMAG_MAX, IMAG_MIN);
			z = new Complex(x, y);
			iterations = 0;
			
			while (z.mod() < 2.0 && iterations < MAX) {
				z.squared();
				z.add(constant);
				++iterations;
			}
			
			if (iterations == MAX)
				pixels[i] = 0;
			else {
				smoothed = iterations - 1 - Util.log2(Util.log2(z.mod()));
				if (smoothed < 0.0)
					smoothed = 0;
				colorIdx = ((int) (Math.sqrt(smoothed) * 256)) % gradientColors.length;
				pixels[i] = gradientColors[colorIdx].getRGB();
			}
		}
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		double x = Util.map(e.getX(), 0, WIDTH, REAL_MIN, REAL_MAX);
		double y = Util.map(e.getY(), 0, HEIGHT, IMAG_MAX, IMAG_MIN);
		constant = new Complex(x, y);
		
		plotJuliaSet();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		double x = Util.map(e.getX(), 0, WIDTH, REAL_MIN, REAL_MAX);
		double y = Util.map(e.getY(), 0, HEIGHT, IMAG_MAX, IMAG_MIN);
		constant = new Complex(x, y);
		
		plotJuliaSet();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	public static void main(String[] args) {
		new JuliaViewer();
	}
	
}
