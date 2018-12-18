package game;

public class GameState {

	String grid[][] = new String[6][9];

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
	
	public void savePlayerChoice(String choice, int playControl) {
		int col = Integer.parseInt(choice);;
		for (int row = grid.length-1; row >= 0; row--) {
			if(grid[row][col-1].equals("[ ] ")) {
				grid[row][col-1] = playControl == 0 ?"[X] ":"[O] ";
				row = 0;
			}
		}
	}


	public Boolean checkResult(String value) {
		
		for (int row = 0; row < grid.length; row++) {
			int countCol = 0;
			for (int col = 0; col < grid[row].length; col++) {
				if(grid[row][col].equals(value)) {
					countCol ++;
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
				if(grid[row][col].equals(value)) {
					countRow ++;
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
