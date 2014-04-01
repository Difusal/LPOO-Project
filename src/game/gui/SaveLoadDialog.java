package game.gui;

import game.logic.Game;

import javax.swing.JDialog;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class to control game saving/loading.
 * 
 * @author Henrique Ferrolho
 * 
 */
public class SaveLoadDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private GamePanel gamePanel;
	private JTextField saveName;
	private List gamesList;

	/** Saved games folder path */
	private static final String savedGamesFolder = System
			.getProperty("user.dir") + "/Saved Games/";

	/**
	 * Class constructor.
	 * 
	 * @param frame
	 * @param gamePanel
	 */
	public SaveLoadDialog(GameFrame frame, GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		setTitle("Save/Load Game");

		getContentPane().setLayout(new GridLayout(2, 1, 0, 0));

		SetUpSaveSection();
		SetUpLoadSection();

		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
	}

	/**
	 * Sets up save game section of the panel
	 */
	private void SetUpSaveSection() {
		JPanel SaveGameSection = new JPanel();
		SaveGameSection.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(SaveGameSection);
		SaveGameSection.setLayout(new BorderLayout(0, 0));

		JLabel lblSaveGame = new JLabel("Save Game");
		lblSaveGame.setBackground(Color.LIGHT_GRAY);
		SaveGameSection.add(lblSaveGame, BorderLayout.NORTH);
		lblSaveGame.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel SaveGameInputPanel = new JPanel();
		SaveGameSection.add(SaveGameInputPanel, BorderLayout.CENTER);

		JLabel lblName = new JLabel("Name:");
		SaveGameInputPanel.add(lblName);

		saveName = new JTextField();
		SaveGameInputPanel.add(saveName);
		saveName.setColumns(10);

		JButton saveButton = new JButton("Save");
		SaveGameInputPanel.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveGame();
			}
		});
	}

	/**
	 * Sets up load game section of the panel
	 */
	private void SetUpLoadSection() {
		JPanel LoadGameSection = new JPanel();
		LoadGameSection.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(LoadGameSection);
		LoadGameSection.setLayout(new BorderLayout(0, 0));

		JLabel lblLoadGame = new JLabel("Load Game");
		lblLoadGame.setBackground(Color.LIGHT_GRAY);
		lblLoadGame.setHorizontalAlignment(SwingConstants.CENTER);
		LoadGameSection.add(lblLoadGame, BorderLayout.NORTH);

		JPanel GamesListPanel = new JPanel();
		LoadGameSection.add(GamesListPanel);

		gamesList = new List();
		GamesListPanel.add(gamesList);

		JButton loadButton = new JButton("Load");
		GamesListPanel.add(loadButton);
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Game loadedGame = LoadGame();

				if (loadedGame != null)
					gamePanel.loadGame(loadedGame);
			}
		});
	}

	/**
	 * Saves game with the current name on the text box
	 */
	private void SaveGame() {
		if (!gamePanel.hasAnActiveGame()) {
			JOptionPane.showMessageDialog(null,
					"There is no active game to be saved.");
			return;
		}

		if (saveName.getText().length() == 0) {
			JOptionPane.showMessageDialog(null,
					"You must specify a name to save the game.");
			return;
		}

		File savesFolder = new File(savedGamesFolder);
		if (!savesFolder.exists())
			savesFolder.mkdir();

		ObjectOutputStream file = null;
		try {
			file = new ObjectOutputStream(new FileOutputStream(savedGamesFolder
					+ saveName.getText()));
			file.writeObject(gamePanel.getGame());
			file.close();
			JOptionPane.showMessageDialog(null, "Game successfully saved.");
			setVisible(false);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"An error occured while saving the game.");
		}
	}

	/**
	 * Loads the selected game
	 * 
	 * @return loaded game
	 */
	private Game LoadGame() {
		if (gamesList.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null,
					"You must select a game to load.");
			return null;
		}

		try {
			FileInputStream fin = new FileInputStream(savedGamesFolder
					+ gamesList.getSelectedItem());
			ObjectInputStream ois = new ObjectInputStream(fin);
			Game game = (Game) ois.readObject();
			ois.close();
			setVisible(false);

			return game;
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"An error occured while loading the game.");
			return null;
		}
	}

	/**
	 * Updates list of files according to existing saved games
	 */
	public void UpdateFilesList() {
		File folder = new File(savedGamesFolder);
		if (!folder.isDirectory())
			return;

		gamesList.removeAll();
		for (File file : folder.listFiles())
			gamesList.add(file.getName());
	}
}
