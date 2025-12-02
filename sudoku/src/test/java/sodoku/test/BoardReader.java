package sodoku.test;

import static java.util.Arrays.sort;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BoardReader {
	public List<int[][]> boards() throws IOException, URISyntaxException {
		File[] files = getFile();
		List<int[][]> boards = new ArrayList<int[][]>();
		for (File file : files) {
			int[][] board = readFileInput(file);
			boards.add(board);
		}
		return boards;
	}

	private int[][] readFileInput(File file) throws IOException {
		FileInputStream input = new FileInputStream(file);
		byte[] b = new byte[input.available()];
		input.read(b);
		int[][] board = new int[9][9];
		String[] row = new String(b).split("\n");
		for (int iRow = 0; iRow < 9; iRow++) {
			for (int iColumn = 0; iColumn < 9; iColumn++) {
				board[iRow][iColumn] = Integer.valueOf("" + row[iRow].charAt(iColumn));
			}
		}
		input.close();
		return board;

	}

	private File[] getFile() throws IOException, URISyntaxException {
		URL resources = Thread.currentThread().getContextClassLoader().getResource("data");
		Path path = Path.of(resources.toURI());
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		};
		File[] listFiles = path.toFile().listFiles(filter);
		Comparator<File> fileNames = new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return o1.getAbsoluteFile().compareTo(o2.getAbsoluteFile());
			}
		};
		sort(listFiles, fileNames);
		return listFiles;
	}

}
