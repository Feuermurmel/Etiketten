package ch.feuermurmel.ettiketten.gui;

import ch.feuermurmel.ettiketten.Printing;
import ch.feuermurmel.ettiketten.Settings;
import ch.feuermurmel.javafx.Loader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public final class EditorController {
	private final EditorView view = Loader.load(EditorView.class);
	private final Settings settings;
	
	private EditorController(Settings settings, SettingsController settingsController) {
		this.settings = settings;
		
		view.printButton().setOnAction(this::handlePrintButtonAction);
		view.settingsButton().setOnAction(actionEvent -> settingsController.show());
		
		outputSettingsValues();
		
		Stage stage = new Stage();
		stage.setOnCloseRequest(this::handleStageCloseRequest);
		stage.setScene(new Scene(view.root()));
		stage.setResizable(false);
		stage.show();
	}
	
	private void handlePrintButtonAction(ActionEvent actionEvent) {
		inputSettingsValues();
		
		boolean wasPrinted = Printing.paintAddress(settings);
		
		if (wasPrinted) {
			settings.rollPosition();
			settings.trySave();
			outputSettingsValues();
		}
	}
	
	private void handleStageCloseRequest(WindowEvent event) {
		inputSettingsValues();
		Platform.exit();
	}
	
	private void outputSettingsValues() {
		view.addressTextField().setText(settings.currentAddress);
		Helpers.outputInteger(view.currentRowTextField(), settings.currentRow + 1);
		Helpers.outputInteger(view.currentColumnTextField(), settings.currentColumn + 1);
	}
	
	private void inputSettingsValues() {
		settings.currentAddress = view.addressTextField().getText();
		settings.currentRow = Helpers.inputInteger(view.currentRowTextField()) - 1;
		settings.currentColumn = Helpers.inputInteger(view.currentColumnTextField()) - 1;
		
		settings.trySave();
	}
	
	public static EditorController create(Settings settings, SettingsController settingsController) {
		return new EditorController(settings, settingsController);
	}
}
