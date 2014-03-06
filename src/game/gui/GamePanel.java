package game.gui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class GamePanel extends JPanel {
	private Image smaug;

	public GamePanel() {
		setUpPanel();
	}

	private void setUpPanel() {
		setBackground(Color.GREEN);

		ImageIcon ii = new ImageIcon(this.getClass().getResource(
				"res/Smaug.jpg"));
		smaug = ii.getImage();
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(smaug, 10, 10, null);
	}
}
