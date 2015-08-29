package ch.feuermurmel.ettiketten;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
				
				VBox vbox = new VBox();
				vbox.setLayoutX(labelOriginX * metersToPoints);
				vbox.setLayoutY(labelOriginY * metersToPoints);
				
				vbox.getChildren().addAll(
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
				pane.getChildren().add(vbox);
				
				return pane;
			});
	}
	
	private static boolean printNode(Function<PageLayout, Node> getNode) {
		PrinterJob job = PrinterJob.createPrinterJob();
		
		if (job.showPrintDialog(null)) {
			PageLayout originalPageLayout = job.getJobSettings().getPageLayout();
			PageLayout newPageLayout = job.getPrinter().createPageLayout(originalPageLayout.getPaper(), originalPageLayout.getPageOrientation(), Printer.MarginType.HARDWARE_MINIMUM);
			
			job.getJobSettings().setJobName("Etiketten");
			job.printPage(newPageLayout, getNode.apply(newPageLayout));
			job.endJob();
			
			return true;
		} else {
			return false;
		}
	}
}
