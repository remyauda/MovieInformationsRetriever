package akinator.weightManagement;

import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

//import
import org.apache.jena.base.Sys;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;

import akinator.init.Initialisation;

public class WeightManagement {

	/******ATTRIBUTS******/

	private final String prefixeMovie = "http://www.semanticweb.org/titanium/ontologies/2017/0/untitled-ontology-11#";

	private final String prefix = "prefix mo: <http://www.semanticweb.org/titanium/ontologies/2017/0/untitled-ontology-11#>\n"
			+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";

	private String property;

	private String label_masterBanch;

	private String weight_masterBanch;

	private String range_masterBanch;

	private ArrayList<String> Property_Label_Weight_Triplet = new ArrayList<>();

	private String value;

	private String label_leaf;

	private String weight_leaf;

	private ArrayList<String> Value_Label_Weight_Triplet = new ArrayList<>();

	private String format;


	/*****CONSTRUCTORS*****/
	public WeightManagement() {
		this.property = "";
		this.label_masterBanch = "";
		this.weight_masterBanch = "";
		this.range_masterBanch = "";
		//add the triplet to the list
		addTriplet_MasterBranch(this.property, this.label_masterBanch, this.weight_masterBanch, this.range_masterBanch);

		this.value ="";
		this.label_leaf = "";
		this.weight_leaf = "";
		//add the triplet to the list
		addTriplet_leaf(this.value, this.label_leaf, this.weight_leaf);

	}

	/*public WeightManagement(String property, String label_masterBanch, String poids_masterBanch , String range_masterBanch) {
		this.property = property;
		this.label_masterBanch = label_masterBanch;
		this.weight_masterBanch = poids_masterBanch;

		this.range_masterBanch = range_masterBanch;
		//add the triplet to the list
		addTriplet_MasterBranch(this.property, this.label_masterBanch, this.weight_masterBanch,this.range_masterBanch);

		this.value ="";
		this.label_leaf = "";
		this.weight_leaf = "";
		//add the triplet to the list
		addTriplet_leaf(this.value, this.label_leaf, this.weight_leaf);

	}

	public WeightManagement(String property, String label_masterBanch, String weight_masterBanch, String value,
			String label_leaf, String weight_leaf,String range_masterBanch) {
		this.property = property;
		this.label_masterBanch = label_masterBanch;
		this.weight_masterBanch = weight_masterBanch;

		this.range_masterBanch = range_masterBanch;
		this.value = value;
		this.label_leaf = label_leaf;
		this.weight_leaf = weight_leaf;
	}
*/
	/*****GETTERS AND SETTERS*****/

	public String getPrefix() {
		return prefix;
	}


	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	/*****METHODS*****/


	/***MasterBranch***/

	//add triplet property, label and weight for the masterBranch
	private void addTriplet_MasterBranch (String property, String label_masterBanch, String weight_masterBanch, String range_masterBanch){
		//add the triplet to the list
		this.Property_Label_Weight_Triplet.add(property);
		this.Property_Label_Weight_Triplet.add(label_masterBanch);
		this.Property_Label_Weight_Triplet.add(weight_masterBanch);
		this.Property_Label_Weight_Triplet.add(range_masterBanch);
	}


	//Set the property, the label and the weight for the masterBranch. Set also these three result in the array Property_Label_Weight_Triplet
	private void MasterBranch_MaxWeight(){
		final String querySring = this.prefix+"Select distinct ?uri_property ?label ?range ?poids where {?uri_property rdfs:label ?label.?uri_property rdfs:range ?range. ?uri_property rdfs:isDefinedBy ?poids . } order by desc (?poids) LIMIT 1";
		//System.out.print(queryString);
		Query query = QueryFactory.create(querySring);
		//System.out.println(this.mainQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, Initialisation.getModel());
		try{
			org.apache.jena.query.ResultSet results = qexec.execSelect();
			//System.out.println(results.getRowNumber());

			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();
				Resource property = soln.getResource("uri_property");
				Literal label_masterBanch = soln.getLiteral("label");
				Literal poids_masterBanch  = soln.getLiteral("poids");

				Resource range_masterBanch  = soln.getResource("range");
				this.property = property.toString();
				this.label_masterBanch = label_masterBanch.getString();
				this.weight_masterBanch = poids_masterBanch.getString();
				this.range_masterBanch = range_masterBanch.toString().split("#")[1];
				//add the triplet to the list
				addTriplet_MasterBranch(this.property, this.label_masterBanch, this.weight_masterBanch,this.range_masterBanch);
			}
		}
		finally{
			qexec.close();
		}

	}



	//return the triplet (property, label, weight) for the masterBranch
	/*public ArrayList<String> getMasterBranch_MaxWeightTriplet(){
		MasterBranch_MaxWeight();
		return this.Property_Label_Weight_Triplet;
	}*/


	//return the property of the masterBranch
	public String getMasterBranch_MaxWeight_Property(){
		MasterBranch_MaxWeight(); 
		StringBuffer foundProperty = new StringBuffer(this.property);
		try{
			foundProperty = foundProperty.replace(0, prefixeMovie.length(), "");//remove the prefix to get only the property
		}
		catch(StringIndexOutOfBoundsException e){
			System.out.print("StringIndexOutOfBoundsException Error. Cause: "+e.getCause()+" Message: "+e.getMessage());
			e.getStackTrace();
		}

		System.out.println("property "+foundProperty.toString());
		return this.property = foundProperty.toString();

	}

	//return the label of the masterBranch
	public String getMasterBranch_MaxWeight_Label(){
		MasterBranch_MaxWeight(); 
		return this.label_masterBanch;
	}


	//return the weight of the masterBranch
	public String getMasterBranch_MaxWeight_Weight(){
		MasterBranch_MaxWeight(); 
		return this.weight_masterBanch;
	}

	//Set the weight for the masterBranch specified by its label to 0.
	public void MasterBranch_SetNullWeight(String Label_MasterBranch){
		final String querySring = this.prefix+" delete{ ?uri_property rdfs:isDefinedBy ?poids. } INSERT { ?uri_property rdfs:isDefinedBy 0 } where { ?uri_property rdfs:label \""+Label_MasterBranch+"\". ?uri_property rdfs:isDefinedBy ?poids. }";
		UpdateRequest query = UpdateFactory.create(querySring);
		UpdateAction.execute( query, Initialisation.getModel() );
	}

	//Decrement the weight for the masterBranch specified by its label.
	public void MasterBranch_DecrementWeight(String Label_MasterBranch){
		int max_weight = Integer.parseInt(getMasterBranch_MaxWeight_Weight());
		max_weight--;
		final String querySring = this.prefix+" delete{ ?uri_property rdfs:isDefinedBy ?poids. } INSERT { ?uri_property rdfs:isDefinedBy "+max_weight+" } where { ?uri_property rdfs:label \""+Label_MasterBranch+"\". ?uri_property rdfs:isDefinedBy ?poids. }";
		//System.out.print(queryString);
		UpdateRequest query = UpdateFactory.create(querySring);
		//System.out.println(this.mainQuery);
		UpdateAction.execute( query, Initialisation.getModel() );

		//this.weight_masterBanch= Integer.toString(max_weight);
	}
	
	//Decrement the weight for the masterBranch specified by its label.
	public void MasterBranch_DecrementWeight(String Label_MasterBranch, int numberToDecrement){
		int max_weight = Integer.parseInt(getMasterBranch_MaxWeight_Weight());
		max_weight-=numberToDecrement;
		final String querySring = this.prefix+" delete{ ?uri_property rdfs:isDefinedBy ?poids. } INSERT { ?uri_property rdfs:isDefinedBy "+max_weight+" } where { ?uri_property rdfs:label \""+Label_MasterBranch+"\". ?uri_property rdfs:isDefinedBy ?poids. }";
		//System.out.print(queryString);
		UpdateRequest query = UpdateFactory.create(querySring);
		//System.out.println(this.mainQuery);
		UpdateAction.execute( query, Initialisation.getModel() );

		//this.weight_masterBanch= Integer.toString(max_weight);
	}

	//return the format of the masterBranch specified by its label
	public String getMasterBranch_MaxWeight_Format(String Label_MasterBranch){

		final String querySring = this.prefix+"Select distinct ?uri_property ?label ?format  where {?uri_property rdfs:range ?format.?uri_property rdfs:label \""+Label_MasterBranch+"\".}";
		//System.out.print(queryString);
		Query query = QueryFactory.create(querySring);
		//System.out.println(this.mainQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, Initialisation.getModel());
		try{
			org.apache.jena.query.ResultSet results = qexec.execSelect();
			//System.out.println(results.getRowNumber());
			Resource format = null ;
			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();
				format = soln.getResource("format");
			}

			String foundFormat = new String(format.toString());
			String[] foundUrl = null;
			try{
				foundUrl = foundFormat.split("#");//remove the prefix to get only the property
			}
			catch(PatternSyntaxException e){
				System.out.print("PatternSyntaxException Error. Cause: "+e.getCause()+" Message: "+e.getMessage());
				e.getStackTrace();
			}
			if(foundUrl[0].equals(prefixeMovie.substring(0, prefixeMovie.length()-1))){
				this.format="";
			}
			else{
				this.format="rdfs";
			}
		}
		finally{
			qexec.close();
		}

		return this.format;

	}

	/***Leaves***/

	//add triplet property, label and weight for the Leaf
	private void addTriplet_leaf (String value, String label_leaf, String weight_leaf){
		//add the triplet to the list
		this.Value_Label_Weight_Triplet.add(value);
		this.Value_Label_Weight_Triplet.add(label_leaf);
		this.Value_Label_Weight_Triplet.add(weight_leaf);
	}

	//Set the value, the label and the weight for the Leaf. Set also these three result in the array Value_Label_Weight_Triplet. Need of the label of the masterBranch to know which leaf we must query.
	private void leaf_MaxWeight(String range_MasterBranch){
		final String countQuerySring = this.prefix+"Select distinct (count(?uri_value) as ?count) where { ?uri_value rdf:type mo:"+range_MasterBranch+". ?uri_value rdfs:label ?label. ?uri_value rdfs:seeAlso ?poids . }order by desc (?poids) LIMIT 1";
		final String querySring = this.prefix+"Select distinct ?uri_value ?label ?poids where {?uri_value rdf:type mo:"+range_MasterBranch+". ?uri_value rdfs:label ?label. ?uri_value rdfs:seeAlso ?poids . }order by desc (?poids) LIMIT 1";
		//System.out.print(queryString);
		Query query = QueryFactory.create(countQuerySring);
		//System.out.println(this.mainQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, Initialisation.getModel());
		org.apache.jena.query.ResultSet results = qexec.execSelect();
		int compteur = 0;
		while (results.hasNext()){
			QuerySolution soln = results.nextSolution();
		    compteur  = soln.getLiteral("count").getInt();
		}
		if(compteur==1){
			this.MasterBranch_SetNullWeight(this.label_masterBanch);
		}
		////////////////////////////////////////
		query = QueryFactory.create(querySring);
		qexec = QueryExecutionFactory.create(query, Initialisation.getModel());
		try{
			 results = qexec.execSelect();
			//System.out.println(results.getRowNumber());
			
			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();
				Resource value = soln.getResource("uri_value");
				Literal label_leaf = soln.getLiteral("label");
				Literal weight_leaf  = soln.getLiteral("poids");
				this.value = value.toString();
				this.label_leaf = label_leaf.getString();
				this.weight_leaf = weight_leaf.getString();
				
				//add the triplet to the list
				addTriplet_leaf(this.value, this.label_leaf, this.weight_leaf);
			}
			
		}
		finally{
			qexec.close();
		}

	}

	//return the triplet (value, label, weight) for the leaf. Need to know the label of the masterBranch to know which leaf we must query
	public ArrayList<String> getLeaf_MaxWeightTriplet(String Label_MasterBranch){
		leaf_MaxWeight(this.range_masterBanch);
		return this.Value_Label_Weight_Triplet;
	}

	//return the value of the leaf. Need to know the label of the masterBranch to know the which leaf we must query.
	public String getLeaf_MaxWeight_Value(String Label_MasterBranch){
		leaf_MaxWeight(this.range_masterBanch);
		StringBuffer foundValue = new StringBuffer(this.value);
		try{
			foundValue = foundValue.replace(0, prefixeMovie.length(), "");//remove the prefix to get only the property
		}
		catch(StringIndexOutOfBoundsException e){
			System.out.print("StringIndexOutOfBoundsException Error. Cause: "+e.getCause()+" Message: "+e.getMessage());
			e.getStackTrace();
		}

		return value = foundValue.toString();

	}

	//return the label of the leaf. Need to know the label of the masterBranch to know the which leaf we must query.
	public String getLeaf_MaxWeight_Label(String Label_MasterBranch){
		leaf_MaxWeight(this.range_masterBanch);
		return this.label_leaf;
	}

	//return the weight of the leaf. Need to know the label of the masterBranch to know the which leaf we must query.
	public String getLeaf_MaxWeight_Weight(String Label_MasterBranch){
		leaf_MaxWeight(this.range_masterBanch);
		return this.weight_leaf;
	}

	//Set the weight for the masterBranch specified by its label to 0.
	public void Leaf_SetNullWeight(String Label_MasterBranch, String Label_Leaf){
		final String querySring = this.prefix+" DELETE {?uri_value rdfs:seeAlso ?poids } where { ?uri_value rdf:type mo:"+this.range_masterBanch+". ?uri_value rdfs:label \""+Label_Leaf+"\". ?uri_value rdfs:seeAlso ?poids . }";
		UpdateRequest query = UpdateFactory.create(querySring);
		UpdateAction.execute( query, Initialisation.getModel() );
	}

	//Decrement the weight for the masterBranch specified by its label.
	/*public void Leaf_DecrementWeight(String Label_MasterBranch, String Label_Leaf){
		int max_weight = Integer.parseInt(getLeaf_MaxWeight_Weight(Label_MasterBranch));
		max_weight--;
		final String querySring = this.prefix+" DELETE {?uri_value rdfs:seeAlso ?poids } INSERT { ?uri_value rdfs:seeAlso "+max_weight+" } where { ?uri_value owl:versionInfo \""+Label_MasterBranch+"\". ?uri_value rdfs:label \""+Label_Leaf+"\". ?uri_value rdfs:seeAlso ?poids . }";
		//System.out.print(queryString);
		UpdateRequest query = UpdateFactory.create(querySring);
		//System.out.println(this.mainQuery);
		UpdateAction.execute( query, Initialisation.getModel() );

		//this.weight_masterBanch= Integer.toString(max_weight);
	}

	//Decrement the weight for the masterBranch specified by its label.
	public void Leaf_DecrementWeight(String Label_MasterBranch, String Label_Leaf, int numberToDecrement){
		int max_weight = Integer.parseInt(getLeaf_MaxWeight_Weight(Label_MasterBranch));
		max_weight-=numberToDecrement;
		final String querySring = this.prefix+" DELETE {?uri_value rdfs:seeAlso ?poids } INSERT { ?uri_value rdfs:seeAlso "+max_weight+" } where { ?uri_value owl:versionInfo \""+Label_MasterBranch+"\". ?uri_value rdfs:label \""+Label_Leaf+"\". ?uri_value rdfs:seeAlso ?poids . }";
		//System.out.print(queryString);
		UpdateRequest query = UpdateFactory.create(querySring);
		//System.out.println(this.mainQuery);
		UpdateAction.execute( query, Initialisation.getModel() );

		//this.weight_masterBanch= Integer.toString(max_weight);
	}*/

	/***********************************/
	
	//Set the property, the label and the weight for the masterBranch. Set also these three result in the array Property_Label_Weight_Triplet
		private String getDataValue(String Label_MasterBranch, String property){
			final String querySring = this.prefix+"Select distinct ?value  where {?uri_value rdfs:label \""+Label_MasterBranch+"\".?uri_value rdfs:label ?label.?uri_value rdfs:isDefinedBy ?poids.?uri mo:"+property+" ?value.filter regex (str(?value), \"OK\")}order by desc (?poids) LIMIT 1";
			//System.out.print(querySring);
			String getDataValue = new String();
			Query query = QueryFactory.create(querySring);
			//System.out.println(this.mainQuery);
			QueryExecution qexec = QueryExecutionFactory.create(query, Initialisation.getModel());
			try{
				org.apache.jena.query.ResultSet results = qexec.execSelect();
				//System.out.println(results.getRowNumber());

				while (results.hasNext()){
					QuerySolution soln = results.nextSolution();
					Literal valueData = soln.getLiteral("value");
					getDataValue = valueData.toString();
				}
				
			}
			finally{
				qexec.close();
			}
			return getDataValue;

		}
		
		private String deleteOK(String getDataValue){
			return getDataValue.substring(0, getDataValue.length()-2);
		}
		
		private String getDataValueWithoutOK(String Label_MasterBranch, String property){
			return deleteOK(getDataValue(Label_MasterBranch,property));
		}
		
		//Decrement the weight for the masterBranch specified by its label.
		private void DeleteDataValue(String ValueWithoutOK, String valueWithOK ,String property){
			final String querySring = this.prefix+" DELETE { ?uri mo:"+property+" ?value. } INSERT { ?uri mo:"+property+" \""+ValueWithoutOK+"NONE\". } where { ?uri mo:"+property+" \""+valueWithOK+"\". ?uri mo:"+property+" ?value. }";
			//System.out.println(querySring);
			UpdateRequest query = UpdateFactory.create(querySring);
			//System.out.println(this.mainQuery);
			UpdateAction.execute( query, Initialisation.getModel() );

			//this.weight_masterBanch= Integer.toString(max_weight);
		}
	
	
	//method to get the property, the value and the format with the greater weight to construct StoredComponent
	public ArrayList<String> getPropertyValueFormat (){
		ArrayList<String> propertyAndValue = new ArrayList<>();
		WeightManagement wm = new WeightManagement();
		String property = wm.getMasterBranch_MaxWeight_Property();
		//System.out.println("property getPropertyValue format " +property);
		//System.out.println(wm.getMasterBranch_MaxWeight_Label());
		String format = wm.getMasterBranch_MaxWeight_Format(wm.getMasterBranch_MaxWeight_Label());
		String value = "";
		String valueWithOK = "";
		if(format.equals("rdfs")){
			value = getDataValueWithoutOK(wm.getMasterBranch_MaxWeight_Label(),property);
			///System.out.println(value+"OK");
			valueWithOK = value+"OK";
			DeleteDataValue(value, valueWithOK, property);
			//System.out.println("DeleteDataValue");
		}
		else{
		value = wm.getLeaf_MaxWeight_Value(wm.getMasterBranch_MaxWeight_Label());
		}
		propertyAndValue.add(property);
		propertyAndValue.add(value);
		propertyAndValue.add(format);

		return propertyAndValue;

	}



}


