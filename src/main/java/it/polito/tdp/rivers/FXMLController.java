/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.DatiInterfaccia;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void handleInterfaccia(ActionEvent event) {
    	txtResult.clear();
    	River r = boxRiver.getValue();
    	if(r==null) {
    		txtResult.setText("Errore: scegliere un fiume");
    	} else {
    		DatiInterfaccia di = this.model.getDati(r);
    		txtStartDate.setText(di.getDataInizio().toString());
    		txtEndDate.setText(di.getDataFine().toString());
    		txtNumMeasurements.setText(di.getnMisure()+"");
    		txtFMed.setText(di.getfMed()+"");
    	}
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	River r = boxRiver.getValue();
    	if(r==null) {
    		txtResult.setText("Errore: scegliere un fiume");
    	} else {
    		try {
    			this.model.simula(r.getId(),Double.parseDouble(txtFMed.getText()), Double.parseDouble(txtK.getText()));
    			int giorniInsoddisfatti = this.model.getGiorniInsoddisfatti();
        		double occupazioneMedia = this.model.getOccupazioneMedia();
        		txtResult.setText("Giorni insoddisfatti: "+giorniInsoddisfatti);
        		txtResult.appendText("\nOccupazione media in metri cubi: "+occupazioneMedia);
    		}catch (NumberFormatException e) {
    			txtResult.setText("Inserisci un numero decimale per la k");
			}
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	boxRiver.getItems().addAll(this.model.getRivers());
    }
}
