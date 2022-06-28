package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnStatistiche;

    @FXML
    private Button btnRicerca;

    @FXML
    private ComboBox<String> boxLocalizzazione;

    @FXML
    private TextArea txtResult;

    @FXML
    void doRicerca(ActionEvent event) {
    	
    	

    }

    @FXML
    void doStatistiche(ActionEvent event) {
    	String localizzazione= boxLocalizzazione.getValue();
    	if(localizzazione==null) {
    		txtResult.setText("Selezionare una Localizzazione \n");
    		return;
    	}
    	
    	List<Adiacenza> adiacenze= model.getAdiacenze(localizzazione);
    	if(adiacenze.isEmpty())
    		txtResult.setText("Adiacenze non trovate \n\n");
    	
    	txtResult.appendText("\n"+"Adiacenti a: "+localizzazione+"\n");
    	for(Adiacenza a: adiacenze) {
    		txtResult.appendText(a.getV1()+" peso: "+a.getPeso()+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert btnStatistiche != null : "fx:id=\"btnStatistiche\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxLocalizzazione != null : "fx:id=\"boxLocalizzazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		model.creaGrafo();
		txtResult.appendText("Grafo creato. #vertici: "+model.getNVertici()+" #archi: "+model.getNArchi()+"\n\n");
		
		boxLocalizzazione.getItems().addAll(model.getLocalizzazioni());
	}
}
