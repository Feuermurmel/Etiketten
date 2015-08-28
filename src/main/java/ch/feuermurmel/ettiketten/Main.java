package ch.feuermurmel.ettiketten;

import ch.feuermurmel.ettiketten.gui.EditorController;
import ch.feuermurmel.ettiketten.gui.SettingsController;
import javafx.application.Application;
import javafx.stage.Stage;

public final class Main extends Application {
	@SuppressWarnings("ResultOfObjectAllocationIgnored")
	@Override
	public void start(Stage primaryStage) {
		Settings settings = new Settings();
		SettingsController settingsController = SettingsController.create(settings);
		EditorController editorController = EditorController.create(settings, settingsController);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
