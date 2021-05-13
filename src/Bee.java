import java.util.Random;
import java.math.BigDecimal;
class Bee{
	private int[][][] solution;
	private BigDecimal fitness;
	private Subgrid[] subgrid;
	private Random rand=new Random();
	Bee(Subgrid[] subgrid){
		this.subgrid=subgrid;
		}
	Bee(int[][][] prob, Subgrid[] subgrid){
		solution=prob;
		this.subgrid=subgrid;
		for(int ctr=0; ctr<subgrid.length; ctr++){
			int[] needed=neededNumbers(subgrid[ctr]);
			for(int y=subgrid[ctr].getStartY(), indexRand=needed.length, limY=y+subgrid[ctr].getDimY(); y<limY; y++){
				for(int x=subgrid[ctr].getStartX(), limX=x+subgrid[ctr].getDimX(); x<limX; x++){
					if(solution[y][x][1]==1){ //1 =not start square
						int tmp=rand.nextInt(indexRand);
						solution[y][x][0]=needed[tmp];
						needed[tmp]=needed[indexRand-1];
						needed[indexRand-1]=solution[y][x][0];
						indexRand=indexRand-1;
						}
					}
				}
			}
		}
	protected void copyProblem(int[][][] prob){
		solution=prob;
		}
	protected void printResult(){
		for(int ctr=0; ctr<solution.length; ctr++){
			for(int ctr1=0; ctr1<solution[ctr].length; ctr1++){
				System.out.print(solution[ctr][ctr1][0]+"");
				}
			System.out.println("");
			}
		}
	protected long getPenaltyValue(int type){
		long penalty=0;
		if(type==1 || type==2){ //Sum-Product without intrinsic sub-grid constraint	
			int sumOfRows = 0;
			int sumOfCols = 0;
			int sumOfSubs = 0;
			long productOfRows = 0; //sum of product
			long productOfCols = 0;
			long productOfSubs = 0;
			long puzzleProduct = factorial(solution.length); //N!
			int puzzleSum = (solution.length*(solution.length+1))/2; //(N(N+1))/2

			for(int i = 0; i <solution.length; i++){
				long curProRow = 1;
				long curProCol = 1;
				int curSumRow = 0;
				int curSumCol = 0;

				for(int j=0; j<solution.length; j++){ 
					curSumRow += solution[i][j][0]; //current Row
					curSumCol += solution[j][i][0]; //current column
					curProRow *= solution[i][j][0]; 
					curProCol *= solution[j][i][0];
				}
				if(type==2){ // w/o subgrid constraint
					long[] subAns = subgridCompute(i);
					sumOfSubs += (subAns[0]-puzzleSum);
					productOfSubs += (subAns[1]-puzzleProduct);
				}

				sumOfRows += (curSumRow-puzzleSum);
				sumOfCols += (curSumCol-puzzleSum);
				productOfRows += (curProRow-puzzleProduct);
				productOfCols += (curProCol-puzzleProduct);
			}
			if(type==2)
				penalty = sumOfRows+sumOfCols+productOfRows+productOfCols+sumOfSubs+productOfSubs;
			else
				penalty = sumOfRows+sumOfCols+productOfRows+productOfCols;

		}else{ //deafult: Missing numbers
			customSet hor=new customSet();
			customSet ver=new customSet();
			for(int ctr=0; ctr<solution.length; ctr++){
				hor.clear();
				ver.clear();
				for(int ct=0; ct<solution.length; ct++){
					if(hor.contains(solution[ctr][ct][0]))
						penalty++;
					else
						hor.add(new Integer(solution[ctr][ct][0]));
					if(ver.contains(solution[ct][ctr][0]))
						penalty++;
					else
						ver.add(new Integer(solution[ct][ctr][0]));
					}
			}
		}
		return penalty;
	}

	private long[] subgridCompute(int subgridID){ //we assume a perfect square puzzle
		long[] curSub = {0, 1};//0 = sum, 1= product
		int sub = (int)Math.sqrt(solution.length);

		for(int i = 0; i<sub; i++){
			for(int j = 0; j<sub; j++){
				int idxX = ((int)Math.floor(subgridID/sub))*sub+i;
				int idxY = (int)((subgridID+sub)%sub)*sub+j;
				
				curSub[0]+=solution[idxX][idxY][0];
				curSub[1]*=solution[idxX][idxY][0];
			}
		}
		return curSub;
	}

	private long factorial(int n){ // n>=0
		if(n<=0)
			return 1;
		return factorial(n-1)*n;
	}
	protected int[][][] getSolution(){
		return solution;
		}
	protected void setFitness(BigDecimal fit){
		fitness=fit;
		}
	protected BigDecimal getFitness(){
		return fitness;
		}
	protected int getElement(int j){
		int row=j/solution.length, column=j%solution.length;
		if(solution[row][column][1]==0)
			return 0;
		return solution[row][column][0];
		}
	protected int[] neededNumbers(Subgrid grid){
		int[] needed=new int[solution.length];
		int removed=0;
		for(int ctr=1; ctr<=solution.length; ctr++)
			needed[ctr-1]=ctr;
		for(int y=grid.getStartY(), limY=y+grid.getDimY(); y<limY; y++){
			for(int x=grid.getStartX(), limX=x+grid.getDimX(); x<limX; x++){
				if(solution[y][x][1]==0){
					needed[solution[y][x][0]-1]=0;
					removed=removed+1;
					}
				}
			}
		int[] neededNum=new int[solution.length-removed];
		for(int ctr=0, ctr2=0; ctr<solution.length; ctr++){
			if(needed[ctr]>0){
				neededNum[ctr2]=needed[ctr];
				ctr2=ctr2+1;
				}
			}
		return neededNum;
		}
	protected int[][][] getCopy(){
		int[][][] copy=new int[solution.length][solution.length][2];
		for(int ctr=0; ctr<copy.length; ctr++){
			for(int ct=0; ct<copy.length; ct++){
				copy[ctr][ct][0]=solution[ctr][ct][0];
				copy[ct][ctr][0]=solution[ct][ctr][0];
				copy[ctr][ct][1]=solution[ctr][ct][1];
				copy[ct][ctr][1]=solution[ct][ctr][1];
				}
			}
		return copy;
		}
	protected int[][][] swap(int[][][] solution, int subgridNum, int row, int column, int xij, int vij){
		this.solution=solution;
		for(int y=subgrid[subgridNum].getStartY(), limY=y+subgrid[subgridNum].getDimY(); y<limY; y++){
			for(int x=subgrid[subgridNum].getStartX(), limX=x+subgrid[subgridNum].getDimX(); x<limX; x++){
				if(solution[y][x][0]==vij){
					solution[y][x][0]=xij;
					solution[row][column][0]=vij;
					return solution;
					}
				}
			}
		return null;
		}
	}