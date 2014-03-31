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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Toolkit;

/**
 * Represents the game window.
 * 
 * @author Henrique Ferrolho
 * @version 1.0
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton btnNewGame;
	private JButton btnSaveload;
	private JButton btnCreateGame;
	private JButton btnOptions;
	private JButton btnQuitGame;
	private JPanel buttonsPanel;
	private JPanel buttonsPanel2;
	private GamePanel gamePanel;
	private GameConfig gameConfig;
	private JDialog options;
	private SaveLoadDialog saveLoad;

	/**
	 * Class constructor.
	 * 
	 * @throws IOException
	 */
	public GameFrame() throws IOException {
		setTitle("Dungeon Explorer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		gamePanel = new GamePanel();
		buttonsPanel = new JPanel();
		buttonsPanel2 = new JPanel();
		gameConfig = new GameConfig();
		options = new OptionsDialog(this, gamePanel, gameConfig);
		saveLoad = new SaveLoadDialog(this, gamePanel);

		setUpButtons();
		getContentPane().setLayout(new BorderLayout(0, 0));
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

					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					setLocation(dim.width / 2 - getSize().width / 2, dim.height
							/ 2 - getSize().height / 2);

					// starting new game with new options
					gamePanel.startNewGame(gameConfig);
				}
			}
		});

		// Save Load button
		btnSaveload = new JButton("Save/Load");
		btnSaveload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveLoad.UpdateFilesList();
				saveLoad.setVisible(true);
			}
		});

		// Create Game button
		btnCreateGame = new JButton("Create Game");
		btnCreateGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setSize(642, 598);

				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
						- getSize().height / 2);

				// starting new game with new options
				gamePanel.startGameCreation();
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
		btnQuitGame = new JButton("Quit");
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
		buttonsPanel.setLayout(new GridLayout(1, 3));
		buttonsPanel.add(btnNewGame);
		buttonsPanel.add(btnSaveload);
		buttonsPanel.add(btnCreateGame);

		buttonsPanel2.setLayout(new GridLayout(1, 2));
		buttonsPanel2.add(btnOptions);
		buttonsPanel2.add(btnQuitGame);

		getContentPane().add(buttonsPanel, BorderLayout.NORTH);
		getContentPane().add(buttonsPanel2, BorderLayout.SOUTH);
	}

	/**
	 * Starts the game window.
	 */
	public void start() {
		setSize(534, 401);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
				- getSize().height / 2);

		setVisible(true);
	}
}
