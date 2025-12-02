package sodoku.test;

import static java.lang.Integer.valueOf;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InlineReader {
	public List<int[][]> boards() throws IOException, URISyntaxException {
		Path file = getFile();
		String content = Files.readString(file);
		List<int[][]> boards = new ArrayList<int[][]>();
		String[] rawBoards = content.split("\n");
		for (String rawBoard : rawBoards) {
			int[][] board = new int[9][9];
			for (int i = 0; i < rawBoard.length(); i++) {
				board[i / 9][i % 9] = valueOf(""+rawBoard.charAt((i)));
			}
			boards.add(board);
		}
		return boards;

	}

	private Path getFile() throws IOException, URISyntaxException {
		URL resources = Thread.currentThread().getContextClassLoader().getResource("data/sudoku17.in.line");
		return Path.of(resources.toURI());
	}

}
