package game.gui;

import game.logic.Dragon.DragonBehavior;
import game.logic.GameConfig;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class OptionsDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	GameConfig config;

	public OptionsDialog(final GameFrame frame, final GamePanel gamePanel,
			GameConfig gameConfig) {
		config = gameConfig;

		setTitle("Options");
		getContentPane().setLayout(new GridLayout(7, 1));

		JLabel lblMazeDimension = new JLabel("Maze Dimension");
		lblMazeDimension.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblMazeDimension);

		JPanel mazeW = new JPanel();
		getContentPane().add(mazeW);

		JLabel lblWidth = new JLabel("Width");
		mazeW.add(lblWidth);

		final JSlider widthSlider = new JSlider();
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

		final JSlider heightSlider = new JSlider();
		heightSlider.setSnapToTicks(true);
		heightSlider.setPaintTicks(true);
		heightSlider.setPaintLabels(true);
		heightSlider.setMinorTickSpacing(2);
		heightSlider.setMinimum(5);
		heightSlider.setMaximum(55);
		heightSlider.setMajorTickSpacing(10);
		heightSlider.setValue(11);
		mazeH.add(heightSlider);

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
		final JComboBox<?> behaviorSelector = new JComboBox<Object>(
				behaviorStrings);
		behaviorSelector.setSelectedIndex(0);
		dragonsBehavior.add(behaviorSelector);

		JPanel numDragonsPanel = new JPanel();
		getContentPane().add(numDragonsPanel);

		JLabel lblNumDragonsSlideLabel = new JLabel("Dragons to spawn");
		numDragonsPanel.add(lblNumDragonsSlideLabel);

		final JSlider numDragonsSlider = new JSlider();
		numDragonsPanel.add(numDragonsSlider);
		numDragonsSlider.setPaintTicks(true);
		numDragonsSlider.setPaintLabels(true);
		numDragonsSlider.setMinorTickSpacing(5);
		numDragonsSlider.setMajorTickSpacing(10);
		numDragonsSlider.setMaximum(51);
		numDragonsSlider.setMinimum(1);
		numDragonsSlider.setValue(5);

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

				// update game configurations
				config.setGameConfig(width, height, behavior, numDragons);

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

		pack();
		setLocation(frame.getLocation().x + frame.getSize().width / 2
				- getSize().width / 2, frame.getLocation().y
				+ frame.getSize().height / 2 - getSize().height / 2);
	}

	public GameConfig getConfig() {
		return config;
	}
}
