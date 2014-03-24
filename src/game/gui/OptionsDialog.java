package game.gui;

import game.logic.Dragon.DragonBehavior;
import game.logic.GameConfig;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

/**
 * Class to control game settings.
 * 
 * @author Henrique Ferrolho
 * 
 */
public class OptionsDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private GameConfig config;
	private GamePanel panel;

	private JSlider widthSlider;
	private JSlider heightSlider;
	private JSlider numDragonsSlider;
	private JComboBox<?> behaviorSelector;

	private JTextField txtUp;
	private JTextField txtDown;
	private JTextField txtLeft;
	private JTextField txtRight;
	private JTextField txtSendEagle;

	/**
	 * Class constructor.
	 * 
	 * @param frame
	 * @param gamePanel
	 * @param gameConfig
	 */
	public OptionsDialog(GameFrame frame, GamePanel gamePanel,
			GameConfig gameConfig) {
		this.panel = gamePanel;
		config = gameConfig;

		setTitle("Options");
		getContentPane().setLayout(new GridLayout(11, 1));

		// Setting up dialog content
		SetUpMazeDimensionSection();
		SetUpDragonSettingsSection();
		SetUpGameControlsSection();
		SetUpButtonsSection();

		// Dialog Details
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
	}

	/**
	 * Sets up the maze dimension section.
	 */
	public void SetUpMazeDimensionSection() {
		JLabel lblMazeDimension = new JLabel("Maze Dimension");
		lblMazeDimension.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblMazeDimension);

		JPanel mazeW = new JPanel();
		getContentPane().add(mazeW);
		JLabel lblWidth = new JLabel("Width");
		mazeW.add(lblWidth);
		widthSlider = new JSlider();
		widthSlider.setMinorTickSpacing(2);
		widthSlider.setPaintLabels(true);
		widthSlider.setPaintTicks(true);
		widthSlider.setSnapToTicks(true);
		widthSlider.setMajorTickSpacing(10);
		widthSlider.setMaximum(55);
		widthSlider.setMinimum(5);
		widthSlider.setValue(11);
		mazeW.add(widthSlider);

		JPanel mazeH = new JPanel();
		getContentPane().add(mazeH);
		mazeH.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel lblHeight = new JLabel("Height");
		mazeH.add(lblHeight);
		heightSlider = new JSlider();
		heightSlider.setSnapToTicks(true);
		heightSlider.setPaintTicks(true);
		heightSlider.setPaintLabels(true);
		heightSlider.setMinorTickSpacing(2);
		heightSlider.setMinimum(5);
		heightSlider.setMaximum(55);
		heightSlider.setMajorTickSpacing(10);
		heightSlider.setValue(11);
		mazeH.add(heightSlider);
	}

	/**
	 * Sets up the dragon settings section
	 */
	public void SetUpDragonSettingsSection() {
		JLabel lblDragonsSettings = new JLabel("Dragon Settings");
		lblDragonsSettings.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblDragonsSettings);

		JPanel dragonsBehavior = new JPanel();
		getContentPane().add(dragonsBehavior);
		JLabel lblDragons = new JLabel("Behavior");
		lblDragons.setHorizontalAlignment(SwingConstants.LEFT);
		dragonsBehavior.add(lblDragons);
		String[] behaviorStrings = { "Not Moving", "Moving",
				"Moving and Sleeping" };
		behaviorSelector = new JComboBox<Object>(behaviorStrings);
		behaviorSelector.setSelectedIndex(0);
		dragonsBehavior.add(behaviorSelector);

		JPanel numDragonsPanel = new JPanel();
		getContentPane().add(numDragonsPanel);
		JLabel lblNumDragonsSlideLabel = new JLabel("Dragons to spawn");
		numDragonsPanel.add(lblNumDragonsSlideLabel);
		numDragonsSlider = new JSlider();
		numDragonsPanel.add(numDragonsSlider);
		numDragonsSlider.setPaintTicks(true);
		numDragonsSlider.setPaintLabels(true);
		numDragonsSlider.setMinorTickSpacing(5);
		numDragonsSlider.setMajorTickSpacing(10);
		numDragonsSlider.setMaximum(51);
		numDragonsSlider.setMinimum(1);
		numDragonsSlider.setValue(5);
	}

	/**
	 * Sets up the game controls section
	 */
	public void SetUpGameControlsSection() {
		JLabel lblGameControls = new JLabel("Game Controls");
		lblGameControls.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblGameControls);

		JPanel upAndDownControl = new JPanel();
		getContentPane().add(upAndDownControl);
		upAndDownControl.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel lblUp = new JLabel("Up:");
		upAndDownControl.add(lblUp);
		txtUp = new JTextField();
		txtUp.setText(Utilities.getStringOfKey(config.getUpKeyAssignment()));
		upAndDownControl.add(txtUp);
		txtUp.setColumns(10);
		JLabel lblDown = new JLabel("Down:");
		upAndDownControl.add(lblDown);
		txtDown = new JTextField();
		upAndDownControl.add(txtDown);
		txtDown.setText(Utilities.getStringOfKey(config.getDownKeyAssignment()));
		txtDown.setColumns(10);

		JPanel leftAndRightControl = new JPanel();
		getContentPane().add(leftAndRightControl);
		JLabel lblLeft = new JLabel("Left:");
		leftAndRightControl.add(lblLeft);
		txtLeft = new JTextField();
		txtLeft.setText(Utilities.getStringOfKey(config.getLeftKeyAssignment()));
		txtLeft.setColumns(10);
		leftAndRightControl.add(txtLeft);
		JLabel lblRight = new JLabel("Right:");
		leftAndRightControl.add(lblRight);
		txtRight = new JTextField();
		leftAndRightControl.add(txtRight);
		txtRight.setText(Utilities.getStringOfKey(config
				.getRightKeyAssignment()));
		txtRight.setColumns(10);

		JPanel sendEagleControl = new JPanel();
		getContentPane().add(sendEagleControl);
		JLabel lblSendEagle = new JLabel("Send Eagle:");
		sendEagleControl.add(lblSendEagle);
		txtSendEagle = new JTextField();
		txtSendEagle.setText(Utilities.getStringOfKey(config
				.getSendEagleKeyAssignment()));
		txtSendEagle.setColumns(10);
		sendEagleControl.add(txtSendEagle);
	}

	/**
	 * Sets up the buttons section
	 */
	public void SetUpButtonsSection() {
		JPanel buttons = new JPanel();
		getContentPane().add(buttons);

		// Submit Button
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int width = widthSlider.getValue();
				int height = heightSlider.getValue();

				int behaviorIndex = behaviorSelector.getSelectedIndex();
				DragonBehavior behavior = DragonBehavior.values()[behaviorIndex];
				int numDragons = numDragonsSlider.getValue();

				// updating game configurations
				config.setGameConfig(width, height, behavior, numDragons);
				config.setUpKeyAssignment(Utilities.getKeyFromString(txtUp
						.getText()));
				config.setDownKeyAssignment(Utilities.getKeyFromString(txtDown
						.getText()));
				config.setLeftKeyAssignment(Utilities.getKeyFromString(txtLeft
						.getText()));
				config.setRightKeyAssignment(Utilities
						.getKeyFromString(txtRight.getText()));
				config.setSendEagleKeyAssignment(Utilities
						.getKeyFromString(txtSendEagle.getText()));

				// updating game controls
				panel.setUpKey(Utilities.getKeyFromString(txtUp.getText()));
				panel.setDownKey(Utilities.getKeyFromString(txtDown.getText()));
				panel.setLeftKey(Utilities.getKeyFromString(txtLeft.getText()));
				panel.setRightKey(Utilities.getKeyFromString(txtRight.getText()));
				panel.setSendEagleKey(Utilities.getKeyFromString(txtSendEagle
						.getText()));

				// closing options dialog
				setVisible(false);
			}
		});
		buttons.add(btnSubmit);

		// Cancel button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		buttons.add(btnCancel);
	}

	/**
	 * Gets this game configuration
	 * 
	 * @return this game configuration
	 */
	public GameConfig getConfig() {
		return config;
	}
}
