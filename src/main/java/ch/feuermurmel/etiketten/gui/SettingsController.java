package ch.feuermurmel.etiketten.gui;

import ch.feuermurmel.etiketten.Settings;
import ch.feuermurmel.javafx.Loader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public final class SettingsController {
	private final SettingsView view = Loader.load(SettingsView.class);
	private final Settings settings;
	private final Stage stage = new Stage();
	
	private SettingsController(Settings settings) {
		this.settings = settings;
		
		outputSettingsValues();
		
		stage.setOnCloseRequest(this::handleStageCloseRequested);
		stage.setTitle("Einstellungen");
		stage.setScene(new Scene(view.root()));
		stage.setResizable(false);
	}
	
	private void handleStageCloseRequested(WindowEvent event) {
		inputSettingsValues();
		stage.hide();
	}
	
	public void outputSettingsValues() {
		Helpers.outputInteger(view.columnCountTextField(), settings.columnCount);
		Helpers.outputInteger(view.rowCountTextField(), settings.rowCount);
		Helpers.outputDimension(view.labelWidthTextField(), settings.labelWidth);
		Helpers.outputDimension(view.labelHeightTextField(), settings.labelHeight);
		Helpers.outputDimension(view.leftOffsetTextField(), settings.leftOffset);
		Helpers.outputDimension(view.topOffsetTextField(), settings.topOffset);
	}
	
	public void inputSettingsValues() {
		settings.columnCount = Helpers.inputInteger(view.columnCountTextField());
		settings.rowCount = Helpers.inputInteger(view.rowCountTextField());
		settings.labelWidth = Helpers.inputDimension(view.labelWidthTextField());
		settings.labelHeight = Helpers.inputDimension(view.labelHeightTextField());
		settings.leftOffset = Helpers.inputDimension(view.leftOffsetTextField());
		settings.topOffset = Helpers.inputDimension(view.topOffsetTextField());
		
		settings.trySave();
	}
	
	public void show() {
		stage.show();
	}
	
	public static SettingsController create(Settings settings) {
		return new SettingsController(settings);
	}
}
