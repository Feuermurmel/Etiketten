package ch.feuermurmel.ettiketten;

import ch.feuermurmel.javafx.Controller;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

@Controller("editor.fxml")
interface EditorController {
	AnchorPane root();
	TextArea address();
	TextField row();
	TextField column();
	Button printButton();
	Button settingsButton();
}
