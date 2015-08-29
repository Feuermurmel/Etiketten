package ch.feuermurmel.etiketten.gui;

import ch.feuermurmel.javafx.View;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

@View("settings.fxml")
interface SettingsView {
	AnchorPane root();
	TextField labelWidthTextField();
	TextField labelHeightTextField();
	TextField columnCountTextField();
	TextField rowCountTextField();
	TextField leftOffsetTextField();
	TextField topOffsetTextField();
}
