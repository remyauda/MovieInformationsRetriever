//current package
package akinator.graphic_design;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


//imports for graphism

//Actions
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Components
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import akinator.init.Initialisation;
import akinator.init.StoredComponent;
import akinator.sparqlEngine.filter.Filter;
import akinator.sparqlEngine.request.Request;
import akinator.sparqlEngine.translator.Translator;




// TODO: Auto-generated Javadoc
/**
 * The Class Main_window.
 */
public class Main_window extends JFrame implements ActionListener {

	/**
	 * The Class Compteur.
	 */
	//intern class: each instance increment the attribut compteur
	public static class Compteur {

		/** The compteur. */
		public static int compteur = 0;

		/**
		 * Instantiates a new compteur.
		 */
		public Compteur() {
			compteur++;
		}

		/**
		 * Gets the compteur.
		 *
		 * @return the compteur
		 */
		public int getCompteur(){
			return compteur;
		}		  

	}

	/** ****ATTRIBUTS*****. */

	private static final long serialVersionUID = 1L;

	/** The compteur. */
	public int compteur = 0;

	/** The list storedcomponent. */
	java.util.ArrayList <StoredComponent> listStoredcomponent = new java.util.ArrayList <StoredComponent>();

	/** The initialisation. */
	public Initialisation initialisation= new Initialisation(); //Load the file and the ontology 
	
	/** The t. */
	public Translator t =  new Translator();//instanciate a new Translator to construct the questions
	
	/** The f. */
	public Filter f = new Filter(); //instanciate a new filter to construct the filter of the query
	
	/** The r. */
	public Request r  = new Request(); //instanciate a translator to create the question in a natural language

	/** The reponse of the user to the question. */
	boolean reponse;

	/** The yes button. */
	/*Components of the window*/
	protected JButton yes = new JButton("Yes");

	/** The no button. */
	protected JButton no = new JButton("No");

	/** The noanswer button. */
	protected JButton noanswer = new JButton("I don't know");

	/** The question JLabel. */
	protected JLabel question = new JLabel();
	
	/** The text pane to display the result (film name and summary). */
	protected JTextArea textPane = new JTextArea("Hello Master!", 21, 33);
		
	/** The pan JPanel to put our components. */
	//On create a JPanel
	private JPanel pan = new JPanel();
	
	/** The window width. */
	protected int window_width; //width of the window
	
	/** The window height. */
	protected int window_height; //height of the window


	/**
	 * ****CONSTRUCTORS*****.
	 */

	/**
	 * Instantiates a new Main_window.
	 */
	public Main_window(){
		
		window_width=this.getWidth(); //initialize the attribute window_width with the width of the window
	    window_height=this.getHeight(); //initialize the attribute window_height with the height of the window

	    //add StoredComponent to the list. This list will define what type of questions we will ask to the user. 
		listStoredcomponent.add(new StoredComponent("hasActor", "Orlando_Bloom", ""));
		listStoredcomponent.add(new StoredComponent("isTypeOf", "Pirate", ""));
		listStoredcomponent.add(new StoredComponent("wasReleasedIn", "2002", "rdfs"));
		

		/***Set the window***/
		//Define a title to the window
		this.setTitle("MovieRetriever");
		//Define the size of the window : 400 pixels width and 400 pixels height
		this.setSize(400, 400);
		//We now ask our window to position itself at the center.
		this.setLocationRelativeTo(null);
		//Complete the process when clicking on the red cross
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Prevents resizing
		this.setResizable(false);
		//The windows will be always on top 
		this.setAlwaysOnTop(false);

		//add the JPanel pan to the window
		this.getContentPane().add(pan);
        
		//Centering the components in the window
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;


		//////////////////////////////////////////////////////////////////////
		//Formatting content of the window

		//Creating a Container with Horizontal Management
		Box b1 = Box.createHorizontalBox();
		b1.add(question); //add the JLabel which will contain the question ask to the user in the JPanel

		Box b2 = Box.createHorizontalBox();
		b2.add(new JLabel("<html><br><br></html>"));//add new line between the question and the buttons in the JPanel

		//Creating a Container with Horizontal Management
		Box b3 = Box.createHorizontalBox();
		//add the buttons
		b3.add(yes);
		b3.add(new JLabel("  "));
		b3.add(no);
		b3.add(new JLabel("  "));
		b3.add(noanswer);

		//Creating a Container with Vertical Management
		Box b4 = Box.createVerticalBox();
		//add the three precedents Box (the question, the new lines and the buttons)
		b4.add(b1);
		b4.add(b2);
		b4.add(b3);

		//add all these components to the JPanel
		pan.add(b4);

		
		//We put all the listeners so that each component can listen to the interactions.
		yes.addActionListener((ActionListener) this);
		no.addActionListener((ActionListener) this);
		noanswer.addActionListener((ActionListener) this);

		this.setVisible(true);//Make the window visible
	}

	/*****GETTERS AND SETTERS*****/
	 
	 
	/**
	 * @return true, if is reponse
	 */
	public boolean isReponse() {
		return reponse;
	}


	/**
	 * Sets the reponse.
	 *
	 * @param reponse the new reponse
	 */
	public void setReponse(boolean reponse) {
		this.reponse = reponse;
	}

	
	
	/*****METHODS*****/
	 
	//We implement the actionPerformed method to define the behavior of the buttons.
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	/**
	 * @param e the e
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==yes){//We implement the action of the button yes
			System.out.println("User say: yes");
			reponse=true; //set the response of the user to yes
			
		}

		if(e.getSource()==no){//We implement the action of the button yes
			System.out.println("User say: no");
			reponse=false; //set the response of the user to no
			
		}

		if(e.getSource()==noanswer){//We implement the action of the button noanswer
			System.out.println("User press: I don't know");
			

		}
		this.MainEngine(reponse);
	}

	/**
	 * Sets the question in the window.
	 *
	 * @param newQuestion the new question
	 * @return the string
	 */
	public String setQuestion(String newQuestion){
		this.question.setText(newQuestion); 
		return newQuestion;
	}
	
	/**
	 * Translate and show the question in the window.
	 */
	public void translateAndShow(){
		String question = t.translate(listStoredcomponent.get(compteur)); // use the storedcomponent to create the question in a natural language (PB: nullPointeurException, is the problem due to the issue in the load method ?)
		System.out.println(question);
		this.setQuestion(question);
	}
	
	/**
	 * Main engine. If we find one film, we show the result. Else return NONE and do translateAndShow
	 *
	 * @param reponse the reponse
	 */
	public void MainEngine(boolean reponse){
		f.constructFilter(listStoredcomponent.get(this.compteur), reponse);
		r.addFilter(f);
		//System.out.println(r.getResult());
		this.compteur++;
		if(r.getResult()=="NONE"){
			this.translateAndShow();
		}
		else{
			this.showResult();
		}
			//System.out.println("final :"+r.getResult());
	}
	
	/**
	 * Show result.
	 */
	public void showResult(){
		r.setResult();
		String result = r.result;
		result = result.replace("<br/>","\n\nSummary: \n\n");
		result = result.replace("<html>","");
		result = result.replace("</html>","");
		pan.removeAll(); //remove all the components in the JPanel
		pan.setBounds(window_width,window_height, 400, 400); //Set the dimension of the JPanel
		textPane.setLineWrap(true); //Sets the line-wrapping policy of the text area. If set to true the lines will be wrapped if they are too long to fit within the allocated width.
		textPane.setText(result);//Set the text of the textPane with the comments of the film
		pan.add(new JScrollPane(textPane)); //add the scroll
		pan.setBackground(Color.BLUE);//Set the background color of the pan
		pan.validate();// Validate our new JPanel
		pan.repaint(); //redraw our new JPanel
		
		System.out.println(result); //print the result
		//enlever les boutons et le remplacer 
	}

}
