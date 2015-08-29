package ch.feuermurmel.etiketten.gui;

import javafx.scene.control.TextField;

final class Helpers {
	private Helpers() {
	}
	
	static void outputInteger(TextField node, int value) {
		node.setText(String.format("%s", value));
	}
	
	static int inputInteger(TextField node) {
		return Integer.parseInt(node.getText());
	}
	
	static void outputDimension(TextField node, double value) {
		node.setText(String.format("%.2f", value * 1e3));
	}
	
	static double inputDimension(TextField node) {
		return Double.parseDouble(node.getText()) / 1e3;
	}
}
