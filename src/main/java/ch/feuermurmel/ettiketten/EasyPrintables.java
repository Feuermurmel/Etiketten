package ch.feuermurmel.ettiketten;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import java.util.List;

public final class EasyPrintables {
	private EasyPrintables() {
	}

	public static void print(String jobName, final List<EasyPrintable> printables) {
		final PrinterJob job = PrinterJob.getPrinterJob();

		job.setJobName(jobName);

		//job.printDialog(attributes);
		job.setPageable(new Pageable() {
			@Override
			public int getNumberOfPages() {
				return printables.size();
			}

			@Override
			public PageFormat getPageFormat(int pageIndex) {
				return job.defaultPage();
			}

			@Override
			public Printable getPrintable(int pageIndex) {
				return new Printable() {
					@Override
					public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
						printables.get(pageIndex).print((Graphics2D) graphics, pageFormat);

						return PAGE_EXISTS;
					}
				};
			}
		});

		boolean accepted = job.printDialog();
		//boolean accepted = true;

		if (accepted) {
			try {
				job.print();
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}
}
