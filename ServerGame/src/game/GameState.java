package game;

public class GameState {
	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private static Integer GAME_ID = 0;

	String grid[][] = new String[6][9];

	public GameState() {
		GAME_ID++;
	}

	public Integer getGameId() {
		return GAME_ID;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
		currentPlayer = player1;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public void startGame() {
		player1.youStart();
		player2.opponentStart();
	}

	public void PopulateArray() {
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				grid[row][col] = "[ ] ";
			}
		}
	}

	public String PrintGrid() {
		String gridPrint = "";
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				gridPrint += grid[row][col];
			}
			gridPrint += "\n";
		}
		return gridPrint;
	}

	public synchronized void savePlayerChoice(int choice, String message) {
		int col = choice;
		for (int row = grid.length - 1; row >= 0; row--) {
			if (grid[row][col - 1].equals("[ ] ")) {
				grid[row][col - 1] = message.substring(0, 12).equals("MOVE PLAYER1") ? "[X] " : "[O] ";
				row = 0;
			}
		}
	}

	public Boolean checkResult(String value) {

		for (int row = 0; row < grid.length; row++) {
			int countCol = 0;
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col].equals(value)) {
					countCol++;
					if (countCol == 5) {
						return true;
					}
				} else {
					countCol = 0;
				}
			}
		}

		for (int col = 0; col < grid[0].length; col++) {
			int countRow = 0;
			for (int row = 0; row < grid.length; row++) {
				if (grid[row][col].equals(value)) {
					countRow++;
					if (countRow == 5) {
						return true;
					}
				} else {
					countRow = 0;
				}
			}
		}
		return false;
	}
}
