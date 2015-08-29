package ch.feuermurmel.etiketten.gui;

import ch.feuermurmel.javafx.View;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

@View("editor.fxml")
interface EditorView {
	AnchorPane root();
	TextArea addressTextField();
	TextField currentRowTextField();
	TextField currentColumnTextField();
	Button printButton();
	Button settingsButton();
}
