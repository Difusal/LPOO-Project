package game.gui;

import game.logic.GameConfig;

import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;

/**
 * Represents the game window.
 * 
 * @author Henrique Ferrolho
 * @version 1.0
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton btnNewGame;
	private JButton btnOptions;
	private JButton btnQuitGame;
	private JPanel buttonsPanel;
	private GamePanel gamePanel;
	private JDialog options;
	private GameConfig gameConfig;

	/**
	 * 
	 * Class constructor.
	 * 
	 * @throws IOException
	 */
	public GameFrame() throws IOException {
		setTitle("Dungeon Explorer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		gamePanel = new GamePanel();
		buttonsPanel = new JPanel();
		gameConfig = new GameConfig();
		options = new OptionsDialog(this, gamePanel, gameConfig);

		setUpButtons();
		addButtons();
		getContentPane().add(gamePanel);
	}

	/**
	 * Sets up the game window "Play", "Options" and "Quit Game" buttons.
	 */
	private void setUpButtons() {
		// Play Game button
		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = "Do you want to start a new game?";
				int res = JOptionPane.showConfirmDialog(rootPane, msg);

				if (res == JOptionPane.YES_OPTION) {
					setSize(642, 598);

					// starting new game with new options
					gamePanel.startNewGame(gameConfig);
				}
			}
		});

		// Options button
		btnOptions = new JButton("Options");
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				options.setVisible(true);
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

	/**
	 * Adds buttons to game windows layout.
	 */
	private void addButtons() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		buttonsPanel.setLayout(new GridLayout(1, 3));
		buttonsPanel.add(btnNewGame);
		buttonsPanel.add(btnOptions);
		buttonsPanel.add(btnQuitGame);
		getContentPane().add(buttonsPanel, BorderLayout.NORTH);
	}

	/**
	 * Starts the game window.
	 */
	public void start() {
		setSize(534, 401);
		setVisible(true);
	}
}
