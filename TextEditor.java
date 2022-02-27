package labs.lab9;



import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


/**
 * This frame contains a text sample and a control panel to change the font of
 * the text.
 */
public class TextEditor extends JFrame {
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 500;

	private JCheckBox italicCheckBox;
	private JCheckBox boldCheckBox;
	
	
	private JRadioButton size8Button;
	private JRadioButton size16Button;
	private JRadioButton size24Button;
	private JRadioButton size32Button;
	private JRadioButton size40Button;
	
	private JComboBox<String> facenameCombo;
	private ActionListener listener;
	private JTextArea textArea;
	private JMenuBar menuBar;
	private JButton clearButton;
	private Stack<Font> textStack;
	private Stack<Font> redoStack;
	

	

	/**
	 * Constructs the frame.
	 */
	public TextEditor() {
		// Construct text sample

		textStack = new Stack<>();
		redoStack = new Stack<>();
		setTitle("Zhesen Tang - 72984390");
		textArea();
		createClearButton();
		menus();

		// This listener is shared among all components
		listener = new ChoiceListener();

		createControlPanel();
		setLabelFont();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
	}

	class ChoiceListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			setLabelFont();
		}
	}
	
	class ExitItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}
	
	class clearListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			textArea.setText("");
		}
	}
	
	class undoListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				undoEvent();
			} catch (Exception ex) {
				System.out.println("No step to undo!");
			}
//			undoEvent();
			
		}
	}
	
	class redoListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				redoEvent();
			} catch (Exception ex) {
				System.out.println("No step to redo!");
			}
			
		}
	}
	
	//text field
	public void textArea() {
		textArea = new JTextArea();
		textArea.setText("");
		textArea.setLineWrap(true);
		textArea.setEditable(true);
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout());
		JScrollPane scroll = new JScrollPane(textArea);
		textPanel.add(scroll);
		
		add(textPanel, BorderLayout.CENTER);
	}
	
	public void createClearButton() {
		clearButton = new JButton("Clear");
		ActionListener listener = new clearListener();
		clearButton.addActionListener(listener);
		add(clearButton, BorderLayout.SOUTH);
	}
	
	public void menus() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuBar.add(createFileMenu());
		menuBar.add(createEditMenu());
		
	}
	
	public JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem exit = new JMenuItem("Exit");
		ActionListener exitListener = new ExitItemListener();
		exit.addActionListener(exitListener);
		fileMenu.add(exit);
		return fileMenu;
	}
	
	public JMenu createEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		
		JMenuItem undoItem = new JMenuItem("Undo");
		ActionListener undo = new undoListener();
		undoItem.addActionListener(undo);
		editMenu.add(undoItem);
		
		JMenuItem redoItem = new JMenuItem("Redo");
		ActionListener redo = new redoListener();
		redoItem.addActionListener(redo);
		editMenu.add(redoItem);
		
		
		return editMenu;
		
	}

	/**
	 * Creates the control panel to change the font.
	 */
	public void createControlPanel() {
		JPanel facenamePanel = createComboBox();
		JPanel styleGroupPanel = createCheckBoxes();
		JPanel sizeGroupPanel = createRadioButtons();

		// Line up component panels

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(3, 1));
		controlPanel.add(facenamePanel);
		controlPanel.add(styleGroupPanel);
		controlPanel.add(sizeGroupPanel);

		// Add panels to content pane

		add(controlPanel, BorderLayout.NORTH);
	}

	/**
	 * Creates the combo box with the font style choices.
	 * 
	 * @return the panel containing the combo box
	 */
	public JPanel createComboBox() {
		facenameCombo = new JComboBox<String>();
		facenameCombo.addItem("Serif");
		facenameCombo.addItem("SansSerif");
		facenameCombo.addItem("Monospaced");
		facenameCombo.setEditable(true);
		facenameCombo.addActionListener(listener);
		

		JPanel panel = new JPanel();
		panel.add(facenameCombo);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Font"));
		return panel;
	}

	/**
	 * Creates the check boxes for selecting bold and italic styles.
	 * 
	 * @return the panel containing the check boxes
	 */
	public JPanel createCheckBoxes() {
		italicCheckBox = new JCheckBox("Italic");
		italicCheckBox.addActionListener(listener);

		boldCheckBox = new JCheckBox("Bold");
		boldCheckBox.addActionListener(listener);

		JPanel panel = new JPanel();
		panel.add(italicCheckBox);
		panel.add(boldCheckBox);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Style"));

		return panel;
	}

	/**
	 * Creates the radio buttons to select the font size.
	 * 
	 * @return the panel containing the radio buttons
	 */
	public JPanel createRadioButtons() {
		size8Button = new JRadioButton("8 pt.");
		size8Button.addActionListener(listener);
		
		size16Button = new JRadioButton("16 pt.");
		size16Button.addActionListener(listener);
		size16Button.setSelected(true);
		
		size24Button = new JRadioButton("24 pt.");
		size24Button.addActionListener(listener);
		
		size32Button = new JRadioButton("32 pt.");
		size32Button.addActionListener(listener);
		
		size40Button = new JRadioButton("40 pt.");
		size40Button.addActionListener(listener);
		
		ButtonGroup group = new ButtonGroup();
		group.add(size8Button);
		group.add(size16Button);
		group.add(size24Button);
		group.add(size32Button);
		group.add(size40Button);
		
		JPanel panel = new JPanel();
		panel.add(size8Button);
		panel.add(size16Button);
		panel.add(size24Button);
		panel.add(size32Button);
		panel.add(size40Button);
		
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Size"));

		return panel;
	}

	/**
	 * Gets user choice for font name, style, and size and sets the font of the text
	 * sample.
	 */
	public void setLabelFont() {
		// Get font name
		String facename = (String) facenameCombo.getSelectedItem();
		// Get font style

		int style = 0;
		if (italicCheckBox.isSelected()) {
			style = style + Font.ITALIC;
		}
		if (boldCheckBox.isSelected()) {
			style = style + Font.BOLD;
		}

		// Get font size
		int size = 0;



		if (size8Button.isSelected()) {
			size = 8;
		} else if (size16Button.isSelected()) {
			size = 16;
		} else if (size24Button.isSelected()) {
			size = 24;
		} else if (size32Button.isSelected()) {
			size = 32;
		} else if (size40Button.isSelected()) {
			size = 40;
		}
		
		
		textStack.push(new Font(facename, style, size));
		
		// Set font of text field
		textArea.setFont(new Font(facename, style, size));
		textArea.repaint();
	}
	
	
	
	public void undoEvent() {
		
		redoStack.push(textStack.pop());
		textArea.setFont(textStack.lastElement());
		textArea.repaint();
		
	}
	
	public void redoEvent() {
		textArea.setFont(redoStack.lastElement());
		textArea.repaint();
		textStack.push(redoStack.pop());
		
	}
	
	
	public static void main(String[] args){
		TextEditor te = new TextEditor();
		te.setVisible(true);
	   }
}
