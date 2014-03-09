package game.gui;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton btnNewGame;
	private JButton btnOptions;
	private JButton btnQuitGame;
	private JPanel buttonsPanel;
	private GamePanel gamePanel;

	public GameFrame() throws IOException {
		setTitle("Dungeon Explorer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		gamePanel = new GamePanel();
		buttonsPanel = new JPanel();

		setUpButtons();
		addButtons();
	}

	private void setUpButtons() {
		// Play Game button
		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = "Do you want to start a new game?";
				int res = JOptionPane.showConfirmDialog(rootPane, msg);

				if (res == JOptionPane.YES_OPTION) {
					setSize(642, 598);
					gamePanel.startNewDemoGame();
				}
			}
		});

		// Settings button
		btnOptions = new JButton("Options");
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = "This is not implemented yet.";
				JOptionPane.showMessageDialog(rootPane, msg);
			}
		});

		// Quit Game button
		btnQuitGame = new JButton("Quit Game");
		btnQuitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = "Are you sure you want to quit?";
				int res = JOptionPane.showConfirmDialog(rootPane, msg);

				if (res == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
	}

	private void addButtons() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		buttonsPanel.setLayout(new GridLayout(1, 3));
		buttonsPanel.add(btnNewGame);
		buttonsPanel.add(btnOptions);
		buttonsPanel.add(btnQuitGame);
		getContentPane().add(buttonsPanel, BorderLayout.NORTH);
		getContentPane().add(gamePanel);
	}

	public void start() {
		setSize(534, 401);
		setVisible(true);
	}
}
