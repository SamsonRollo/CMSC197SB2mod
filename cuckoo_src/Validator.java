class Validator{
	private int[][][] grid;
	private Subgrid subgrid[];
	private int subDimX;
	private int subDimY;
	private int numberOfGiven = 10; //default
	Validator(int[][][] grid, Subgrid subgrid[]){
		this.grid=grid;
		this.subgrid=subgrid;
		}
	Validator(int[][][] grid, int subDimX, int subDimY){
		this.grid=grid;
		this.subDimX=subDimX;
		this.subDimY=subDimY;
		}
	protected boolean isValid(int num, int locX, int locY, Subgrid subgrid){
		for(int i=0; i<grid.length; i++){
			if(num==grid[i][locY][0] || num==grid[locX][i][0])
				return false;
			}
		return true;
		}
	protected void updateGrid(int[][][] newGrid){
		grid=null;
		grid=newGrid;
		}
	private void sop(Object obj){
		System.out.println(obj+"");
		}
	protected int userPuzzleCheckValidity(int givenPercent){ // 1= ok, 0 = invalid input, -1 = givens exceed
		this.numberOfGiven = (int)((grid.length*grid.length)*(double)((double)givenPercent/100));
		int numberOfInputs = 0;
		for(int i = 0; i<grid.length; i++){
			for(int j=i; j<grid.length; j++){
				if(numberOfInputs>numberOfGiven)
					return -1;
				if(grid[i][j][0]!=0){
					grid[i][j][1]=0;
					numberOfInputs++;
				}
				if(grid[j][i][0]!=0 && j!=i){
					grid[j][i][1]=0;
					numberOfInputs++;
				}
			}
		}
		if(numberOfInputs>numberOfGiven)
			return -1;
		if(!checkValidity()) //initial check
			return 0;
		if(numberOfInputs<numberOfGiven){ //add more inputs randomly if input is less
			GenerateSudoku gs = new GenerateSudoku(this.grid, numberOfGiven, numberOfInputs);
			updateGrid(gs.getSudoku());
		}
		return 1;
	}
	protected boolean checkValidity(){
		customSet hor=new customSet();
		customSet ver=new customSet();
		customSet sub=new customSet();
		for(int ctr=0; ctr<grid.length; ctr++){
			hor.clear();
			ver.clear();
			sub.clear();
			for(int y=subgrid[ctr].getStartY(), limY=y+subgrid[ctr].getDimY(); y<limY; y++){
				for(int x=subgrid[ctr].getStartX(), limX=x+subgrid[ctr].getDimX(); x<limX; x++){
					if(grid[y][x][1]==0 && sub.contains(grid[y][x][0])){
						return false;
						}
					else if(grid[y][x][1]==0){
						sub.add(new Integer(grid[y][x][0]));
						}
					}
				}
			for(int ct=0; ct<grid.length; ct++){
				if(grid[ctr][ct][1]==0 && hor.contains(grid[ctr][ct][0])){
					return false;
					}
				else if(grid[ctr][ct][1]==0)
					hor.add(new Integer(grid[ctr][ct][0]));
				if(grid[ct][ctr][1]==0 && ver.contains(grid[ct][ctr][0])){
					return false;
					}
				else if(grid[ct][ctr][1]==0)
					ver.add(new Integer(grid[ct][ctr][0]));
				}
			}
		return true;
		}

	protected boolean checkAnswer(){
		customSet hor=new customSet();
		customSet ver=new customSet();
		customSet sub=new customSet();
		for(int ctr=0; ctr<grid.length; ctr++){
			hor.clear();
			ver.clear();
			sub.clear();
			for(int y=subgrid[ctr].getStartY(), limY=y+subgrid[ctr].getDimY(); y<limY; y++){
				for(int x=subgrid[ctr].getStartX(), limX=x+subgrid[ctr].getDimX(); x<limX; x++){
					if(sub.contains(grid[y][x][0])){
						return false;
						}
					else{
						sub.add(new Integer(grid[y][x][0]));
						}
					}
				}
			for(int ct=0; ct<grid.length; ct++){
				if(hor.contains(grid[ctr][ct][0])){
					return false;
					}
				else
					hor.add(new Integer(grid[ctr][ct][0]));
				if(ver.contains(grid[ct][ctr][0])){
					return false;
					}
				else
					ver.add(new Integer(grid[ct][ctr][0]));
				}
			}
		return true;
		}

		protected int[][][] getSudoku(){
			return this.grid;
		}

		protected int[][][] getCopy(int[][][] grid){
			int[][][] copy = new int[grid.length][grid.length][2];

			for(int i = 0; i<grid.length; i++){
				for(int j=i; j<grid.length; j++){
					copy[i][j][0] = grid[i][j][0];
					copy[i][j][1] = grid[i][j][1];

					if(i!=j){
						copy[j][i][0] = grid[j][i][0];
						copy[j][i][1] = grid[j][i][1];
					}

				}
			}
			return copy;
		}
	}