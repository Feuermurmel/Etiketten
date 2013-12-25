package ch.feuermurmel.ettiketten;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.util.Arrays;

import ch.feuermurmel.json.Json;
import ch.feuermurmel.json.JsonParseException;
import geometry.Geometries;

public class PrintingMain {
	private PrintingMain() {
	}

	private static final double metersToPoints = 72 / 25.4e-3;

	public static void main(String[] args) throws JsonParseException {
		final Settings settings = new Settings();

		settings.loadFromJson(Json.parse(args[0]).asMap());

		EasyPrintables.print("Ettiketten", Arrays.<EasyPrintable>asList(new EasyPrintable() {
			@Override
			public void print(Graphics2D graphics, PageFormat pageFormat) {
				try {
					double pageWidth = pageFormat.getWidth() / metersToPoints;
					double pageHeight = pageFormat.getHeight() / metersToPoints;

					graphics.transform(AffineTransform.getScaleInstance(metersToPoints, metersToPoints));
					//graphics.setColor(Color.black);

					paintAddress(graphics, pageWidth, pageHeight, settings);
				} catch (Exception e) {
					// For some reason, exceptions thrown in this method are eaten.
					e.printStackTrace();

					throw e;
				}
			}
		}));
	}

	@SuppressWarnings("DynamicRegexReplaceableByCompiledPattern")
	private static void paintAddress(Graphics2D graphics, double pageWidth, double pageHeight, Settings settings) {
		Font font = new Font("Helvetica Neue", Font.PLAIN, 1).deriveFont((float) (settings.fontSize / metersToPoints));
		String[] lines = settings.currentAddress.split("\n");
		LineMetrics metrics = font.getLineMetrics("", graphics.getFontRenderContext());
		double originX = (pageWidth - settings.labelWidth * settings.labelColumns) / 2;
		double originY = (pageHeight - settings.labelHeight * settings.labelRows) / 2;
		double labelOriginX = originX + settings.labelWidth * settings.currentColumn + settings.labelOffsetX;
		double labelOriginY = originY + settings.labelHeight * settings.currentRow + settings.labelOffsetY;
		double addressHeight = lines.length * metrics.getHeight();

		graphics.translate(labelOriginX, labelOriginY + (settings.labelHeight - addressHeight) / 2);
		
		for (String i : lines) {
			graphics.fill(Shapes.text(Geometries.zero, font, i));
			graphics.translate(0, metrics.getHeight());
		}
	}
}
