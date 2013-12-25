package ch.feuermurmel.ettiketten;

import java.awt.Graphics2D;
import java.awt.print.PageFormat;

public interface EasyPrintable {
	void print(Graphics2D graphics, PageFormat pageFormat);
}
