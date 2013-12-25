package ch.feuermurmel.ettiketten;

import ch.feuermurmel.javafx.Controller;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

@Controller("settings.fxml")
interface SettingsController {
	AnchorPane root();
	TextField labelWidth();
	TextField labelHeight();
	TextField labelColumns();
	TextField labelRows();
	TextField offsetX();
	TextField offsetY();
}
