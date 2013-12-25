package ch.feuermurmel.ettiketten;

import java.nio.file.Path;
import java.nio.file.Paths;

import ch.feuermurmel.json.JsonMap;
import javafx.scene.control.TextField;

import static ch.feuermurmel.json.Json.*;

public final class Settings {
	private final Path path = Paths.get(System.getProperty("user.home"), "Library", "Preferences", Settings.class.getPackage().getName() + ".json");
	public int labelRows = 2;
	public int labelColumns = 2;
	public double labelWidth = .05;
	public double labelHeight = .05;
	public double labelOffsetX = 0;
	public double labelOffsetY = 0;
	public double fontSize = 12;

	public String currentAddress = "";
	public int currentRow = 0;
	public int currentColumn = 0;

	public void tryLoadSettings() {
		try {
			JsonMap data = Util.loadJsonFile(path);

			loadFromJson(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadFromJson(JsonMap data) {
		labelRows = data.get("labelRows").asInt();
		labelColumns = data.get("labelColumns").asInt();
		labelWidth = data.get("labelWidth").asDouble();
		labelHeight = data.get("labelHeight").asDouble();
		labelOffsetX = data.get("labelOffsetX").asDouble();
		labelOffsetY = data.get("labelOffsetY").asDouble();
		currentAddress = data.get("currentAddress").asString();
		currentRow = data.get("currentRow").asInt();
		currentColumn = data.get("currentColumn").asInt();
	}

	public void trySaveSettings() {
		JsonMap data = saveToJson();

		try {
			Util.writeJsonFile(path, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JsonMap saveToJson() {
		return map()
			.put("labelRows", labelRows)
			.put("labelColumns", labelColumns)
			.put("labelWidth", labelWidth)
			.put("labelHeight", labelHeight)
			.put("labelOffsetX", labelOffsetX)
			.put("labelOffsetY", labelOffsetY)
			.put("currentAddress", currentAddress)
			.put("currentRow", currentRow)
			.put("currentColumn", currentColumn);
	}

	public void outputEditorValues(EditorController controller) {
		controller.address().setText(currentAddress);
		outputInteger(controller.row(), currentRow + 1);
		outputInteger(controller.column(), currentColumn + 1);
	}

	public void inputEditorValues(EditorController controller) {
		currentAddress = controller.address().getText();
		currentRow = inputInteger(controller.row()) - 1;
		currentColumn = inputInteger(controller.column()) - 1;
	}

	public void outputSettingsValues(SettingsController controller) {
		outputInteger(controller.labelColumns(), labelColumns);
		outputInteger(controller.labelRows(), labelRows);
		outputDimension(controller.labelWidth(), labelWidth);
		outputDimension(controller.labelHeight(), labelHeight);
		outputDimension(controller.offsetX(), labelOffsetX);
		outputDimension(controller.offsetY(), labelOffsetY);
	}

	public void inputSettingsValues(SettingsController controller) {
		labelColumns = inputInteger(controller.labelColumns());
		labelRows = inputInteger(controller.labelRows());
		labelWidth = inputDimension(controller.labelWidth());
		labelHeight = inputDimension(controller.labelHeight());
		labelOffsetX = inputDimension(controller.offsetX());
		labelOffsetY = inputDimension(controller.offsetY());
	}

	public void rollPosition() {
		currentColumn += 1;

		if (currentColumn >= labelColumns) {
			currentColumn = 0;
			currentRow += 1;

			if (currentRow >= labelRows)
				currentRow = 0;
		}
	}

	private static void outputInteger(TextField node, int value) {
		node.setText(String.format("%s", value));
	}

	private static int inputInteger(TextField node) {
		return Integer.parseInt(node.getText());
	}

	private static void outputDimension(TextField node, double value) {
		node.setText(String.format("%.2f", value * 1e3));
	}

	private static double inputDimension(TextField node) {
		return Double.parseDouble(node.getText()) / 1e3;
	}
}
