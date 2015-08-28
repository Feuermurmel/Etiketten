import javafx.application.Application;
import javafx.application.Platform;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public final class TestPrint extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Platform.runLater(
			() -> {
				PrinterJob p = PrinterJob.createPrinterJob();
				
				if (p.showPrintDialog(null)) {
					System.out.println(p.getPrinter());
					
					Pane pane = new Pane();
					pane.setLayoutX(-50);
					pane.setLayoutY(-50);
					
					Label label1 = new Label("Test 123\nFoo Bar");
					label1.setLayoutX(50);
					label1.setLayoutY(50);
					label1.setFont(Font.font("Helvetica", 12));
					
					pane.getChildren().add(label1);
					
					PageLayout pageLayout = p.getJobSettings().getPageLayout();
					
					PageLayout p2 = p.getPrinter().createPageLayout(pageLayout.getPaper(), pageLayout.getPageOrientation(), Printer.MarginType.HARDWARE_MINIMUM);
					
					p.printPage(p2, pane);
					p.endJob();
				}
				
				Platform.exit();
			});
	}
	
	public static void main(String[] args) {
		launch();
	}
}
