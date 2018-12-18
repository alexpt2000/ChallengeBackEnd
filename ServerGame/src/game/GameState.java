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

	public String[][] getGrid() {
		return grid;
	}

	public void setGrid(String[][] grid) {
		this.grid = grid;
	}

}
