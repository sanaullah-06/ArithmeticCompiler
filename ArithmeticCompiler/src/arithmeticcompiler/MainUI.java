package arithmeticcompiler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainUI extends JFrame implements ActionListener {

	public static void main(String[] args) throws IOException 
	{

		new MainUI();

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 233L;
	/**
	 * @param args
	 * @throws IOException
	 */

	// Expression panel.
	JPanel expPanel = new JPanel();
	JLabel expJLabel = new JLabel("Expression:");
	public static JTextField expTextField = new JTextField();
	JButton expCompilerBtn = new JButton("Compiler");

	// Tokenizer panel.
	JPanel tokenizerPanel = new JPanel();
	JLabel tokenizerLabel = new JLabel("Tokenizer",JLabel.CENTER);
	public static JTextArea tokenizerTextArea = new JTextArea();


	// Result panel.
	JPanel resultPanel = new JPanel();
	JLabel resultJLabel = new JLabel("Result:");
	public static JTextField resultTextField = new JTextField();
	JButton resultClearBtn = new JButton("Clear");

	// Parser panel
	JPanel parserPanel = new JPanel();
	JLabel parserLabel = new JLabel("Parser",JLabel.CENTER);
	public static JTextArea parserTextArea = new JTextArea();

	// Syntax Tree panel.
	JPanel syntaxTreePanel = new JPanel();
	JLabel syntaxTreeLabel = new JLabel("Syntax Tree",JLabel.CENTER);
	public static JTextArea syntaxTreeTextArea = new JTextArea();
	
	// Expression and SyntaxTree Panel
	JPanel expAndSyntaxTreePanel = new JPanel();
	
	// Four Parameter Equation(FPE) panel.
	JPanel fpePanel = new JPanel();
	JLabel fpeLabel = new JLabel("FPE",JLabel.CENTER);
	public static JTextArea fpeTextArea = new JTextArea();

	// Result panel and Fpe panel
	JPanel resultAndFpePanel = new JPanel();
	
	public MainUI() {
		getContentPane().setBackground(Color.WHITE);
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int frameWidth = 800;
		int frameHeight = 640;
		this.setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(2, 2,10,10));
		this.setTitle("ArithmeticCompiler");
		this.setResizable(false);
		
		// Tokenizer panel.
		tokenizerPanel.setLayout(new BorderLayout(5,5));
		tokenizerPanel.add(tokenizerLabel, BorderLayout.NORTH);
		tokenizerTextArea.setEditable(false);
		tokenizerTextArea.setBackground(Color.WHITE);
		tokenizerPanel.add(new JScrollPane(tokenizerTextArea), BorderLayout.CENTER);
		tokenizerPanel.setBackground(Color.WHITE);
		getContentPane().add(tokenizerPanel);

		// Parser panel.
		parserPanel.setLayout(new BorderLayout(5,5));
		parserPanel.add(parserLabel, BorderLayout.NORTH);
		parserTextArea.setEditable(false);
		parserTextArea.setBackground(Color.WHITE);
		parserPanel.add(new JScrollPane(parserTextArea), BorderLayout.CENTER);
		parserPanel.setBackground(Color.WHITE);
		getContentPane().add(parserPanel);
		

		// SyntaxTree panel.
		syntaxTreePanel.setLayout(new BorderLayout(5,5));
		syntaxTreePanel.add(syntaxTreeLabel, BorderLayout.NORTH);
		syntaxTreeTextArea.setTabSize(4);
		syntaxTreeTextArea.setEditable(false);
		syntaxTreeTextArea.setBackground(Color.WHITE);
		syntaxTreePanel.add(new JScrollPane(syntaxTreeTextArea), BorderLayout.CENTER);
		syntaxTreePanel.setBackground(Color.WHITE);

		// Expression panel.
		expPanel.setBackground(Color.WHITE);
		expPanel.setLayout(new BorderLayout(10,10));
		expJLabel.setBackground(Color.WHITE);
		expPanel.add(expJLabel, BorderLayout.WEST);
		expPanel.add(expTextField, BorderLayout.CENTER);
		expCompilerBtn.addActionListener(this);
		expPanel.add(expCompilerBtn, BorderLayout.EAST);
		expAndSyntaxTreePanel.setBackground(Color.WHITE);
		
		// Expression and SyntaxTree panel
		expAndSyntaxTreePanel.setLayout(new BorderLayout(5,5));
		expAndSyntaxTreePanel.add(syntaxTreePanel, BorderLayout.CENTER);
		expAndSyntaxTreePanel.add(expPanel, BorderLayout.SOUTH);
		getContentPane().add(expAndSyntaxTreePanel);
				
		// FPE Panel
		fpePanel.setLayout(new BorderLayout(5,5));
		fpePanel.add(fpeLabel, BorderLayout.NORTH);
		fpeTextArea.setEditable(false);
		fpeTextArea.setBackground(Color.WHITE);
		fpePanel.add(new JScrollPane(fpeTextArea), BorderLayout.CENTER);
		fpePanel.setBackground(Color.WHITE);

		// Result Panel
		resultPanel.setBackground(Color.WHITE);
		resultPanel.setLayout(new BorderLayout(10,10));
		resultTextField.setEditable(false);
		resultTextField.setBackground(Color.WHITE);
		resultTextField.setForeground(Color.BLACK);
		resultJLabel.setBackground(Color.WHITE);
		resultPanel.add(resultJLabel, BorderLayout.WEST);
		resultPanel.add(resultTextField, BorderLayout.CENTER);
		resultClearBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)  //Clear按钮
			{
				tokenizerTextArea.setText("");
				resultTextField.setText("");
				parserTextArea.setText("");
				syntaxTreeTextArea.setText("");
				fpeTextArea.setText("");
			}
		});
		resultPanel.add(resultClearBtn, BorderLayout.EAST);
		resultAndFpePanel.setBackground(Color.WHITE);

		// Result and FPE Panel 
		resultAndFpePanel.setLayout(new BorderLayout(5,5));
		resultAndFpePanel.add(fpePanel, BorderLayout.CENTER);
		resultAndFpePanel.add(resultPanel, BorderLayout.SOUTH);
		getContentPane().add(resultAndFpePanel);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(expTextField.getText().isEmpty())
		{
			return;
		}
		resultTextField.setText("");
		tokenizerTextArea.setText("");
		parserTextArea.setText("");
		syntaxTreeTextArea.setText("");
		fpeTextArea.setText("");
		Tokenizer tokenizer = new Tokenizer();
		if (tokenizer.getTokenArray().size() == 0)
		{
			return;
		}
		Parser parser;
		try 
		{
			parser = new Parser(tokenizer.getTokenArray());
			parser.parse();
		}
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
	}
}
