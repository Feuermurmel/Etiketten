package ch.feuermurmel.ettiketten;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Printing {
	private Printing() {
	}
	
	private static final double metersToPoints = 72 / 25.4e-3;
	
	@SuppressWarnings("DynamicRegexReplaceableByCompiledPattern")
	public static boolean paintAddress(Settings settings) {
		return printNode(
			pageLayout -> {
				Font font = Font.font("Helvetica Neue", FontWeight.NORMAL, FontPosture.REGULAR, settings.fontSize);
				
				double pageWidth = pageLayout.getPaper().getWidth() / metersToPoints;
				double pageHeight = pageLayout.getPaper().getHeight() / metersToPoints;
				
				double originX = (pageWidth - settings.labelWidth * settings.columnCount) / 2;
				double originY = (pageHeight - settings.labelHeight * settings.rowCount) / 2;
				double labelOriginX = originX + settings.labelWidth * settings.currentColumn + settings.leftOffset;
				double labelOriginY = originY + settings.labelHeight * settings.currentRow + settings.topOffset;
				
				HBox hbox = new HBox();
				hbox.setLayoutX(labelOriginX * metersToPoints);
				hbox.setLayoutY(labelOriginY * metersToPoints);
				
				hbox.getChildren().addAll(
					Arrays
						.asList(settings.currentAddress.split("\n"))
						.stream()
						.map(
							x -> {
								Label label = new Label(x);
								
								label.setFont(font);
								
								return label;
							})
						.collect(Collectors.toList()));
				
				Pane pane = new Pane();
				
				pane.setLayoutX(-pageLayout.getLeftMargin());
				pane.setLayoutY(-pageLayout.getTopMargin());
				pane.getChildren().add(hbox);
				
				return pane;
			});
	}
	
	private static boolean printNode(Function<PageLayout, Node> getNode) {
		PrinterJob job = PrinterJob.createPrinterJob();
		
		if (job.showPrintDialog(null)) {
			Pane pane = new Pane();
			pane.setLayoutX(-50);
			pane.setLayoutY(-50);
			
			Label label1 = new Label("Test 123\nFoo Bar");
			label1.setLayoutX(50);
			label1.setLayoutY(50);
			label1.setFont(Font.font("Helvetica", 12));
			
			pane.getChildren().add(label1);
			
			PageLayout originalPageLayout = job.getJobSettings().getPageLayout();
			PageLayout newPageLayout = job.getPrinter().createPageLayout(originalPageLayout.getPaper(), originalPageLayout.getPageOrientation(), Printer.MarginType.HARDWARE_MINIMUM);
			
			job.printPage(newPageLayout, getNode.apply(newPageLayout));
			job.endJob();
			
			return true;
		} else {
			return false;
		}
	}
}
