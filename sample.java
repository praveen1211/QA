import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.io.filefilter.AgeFileFilter;

public class sample {

	public static void main(String[] args) throws IOException {

		File directory = new File("C:\\CRM_Interface\\Reports\\");

		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2008, 0, 15, 0, 0, 0); // January 15th, 2008
		Date cutoffDate = cal.getTime();

		System.out.println("All Files");
		displayFiles(directory, null);

		System.out.println("\nBefore " + cutoffDate);
		displayFiles(directory, new AgeFileFilter(cutoffDate));

		System.out.println("\nAfter " + cutoffDate);
		displayFiles(directory, new AgeFileFilter(cutoffDate, false));

	}

	public static void displayFiles(File directory, FileFilter fileFilter) {
		File[] files = directory.listFiles(fileFilter);
		for (File file : files) {
			Date lastMod = new Date(file.lastModified());
			System.out.println("File: " + file.getName() + ", Date: " + lastMod + "");
		}
	}

}