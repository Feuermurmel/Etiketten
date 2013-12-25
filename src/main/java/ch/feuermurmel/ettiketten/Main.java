package ch.feuermurmel.ettiketten;

import java.io.IOException;
import java.util.*;

import ch.feuermurmel.javafx.Loader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.*;

public final class Main extends Application {
	private final Settings settings = new Settings();
	private final EditorController editorController;
	private final SettingsController settingsController;

	public Main() throws IOException {
		editorController = Loader.load(EditorController.class);
		settingsController = Loader.load(SettingsController.class);
	}

	@SuppressWarnings("ResultOfObjectAllocationIgnored")
	@Override
	public void start(final Stage primaryStage) {
		final Stage settingsColor = new Stage();

		settingsColor.initModality(Modality.WINDOW_MODAL);
		settingsColor.initOwner(primaryStage);
		settingsColor.setScene(new Scene(settingsController.root()));
		
		settings.tryLoadSettings();
		settings.outputEditorValues(editorController);

		editorController.printButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if (startJVM(PrintingMain.class, Arrays.asList(settings.saveToJson().toString())))
					settings.rollPosition();

				settings.outputEditorValues(editorController);
				settings.trySaveSettings();
			}
		});
		
		editorController.settingsButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				settingsColor.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent windowEvent) {
						settings.inputSettingsValues(settingsController);
					}
				});
				
				settings.outputSettingsValues(settingsController);
				
				settingsColor.show();
			}
		});

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent windowEvent) {
				settings.inputEditorValues(editorController);
				settings.trySaveSettings();
			}
		});

		primaryStage.setScene(new Scene(editorController.root()));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private static boolean startJVM(Class<?> mainClass, List<String> args) {
		String separator = System.getProperty("file.separator");
		String classpath = System.getProperty("java.class.path");
		String path = System.getProperty("java.home") + separator + "bin" + separator + "java";

		List<String> arguments = new ArrayList<>(Arrays.asList(path, "-cp", classpath, mainClass.getName()));
		arguments.addAll(args);

		ProcessBuilder processBuilder = new ProcessBuilder(arguments);

		processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

		try {
			Process process = processBuilder.start();

			process.waitFor();

			return process.exitValue() == 0;
		} catch (IOException | InterruptedException e) {
			throw new AssertionError(e);
		}
	}
}
