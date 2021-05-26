import java.util.Random;
import java.awt.Point;

class GenerateSudoku{
	private int[][][] sudoku;
	private Random rand=new Random();
	GenerateSudoku(int[][][] sudoku){
		this.sudoku=sudoku;
		for(int ctr=0; ctr<sudoku.length; ctr++){
			for(int ct=ctr; ct<sudoku.length; ct++){
				double first=rand.nextDouble(), second=rand.nextDouble();
				if(first>1-second){ //clear the number at point ct,ctr; row-column traversal
					this.sudoku[ct][ctr][0]=0;
					this.sudoku[ct][ctr][1]=1;
					}
				else{ // make it an ss
					this.sudoku[ct][ctr][1]=0;
					}
				if(ct!=ctr && first>1-second){ //column-row traversal
					this.sudoku[ctr][ct][0]=0;
					this.sudoku[ctr][ct][1]=1;
					}
				else if(ct!=ctr){
					this.sudoku[ctr][ct][1]=0;
					}
				}
			}
		}
	GenerateSudoku(int[][][] sudoku, int givenPercent){
		this.sudoku = sudoku;
		int puzzLen = sudoku.length;
		int numberOfGiven = (int)((puzzLen*puzzLen)*(double)((double)givenPercent/100));
		customSet coords = new customSet();
		while(coords.size()<numberOfGiven){ //find random places
			int curcoord= rand.nextInt(puzzLen*puzzLen); //0-max-1
			if(!coords.contains(curcoord)){
				coords.add(curcoord);
				this.sudoku[(int)(curcoord/puzzLen)][(int)(curcoord%puzzLen)][1] = 0; //set the number at coord as ss
			}
		}
		fillEmpty();
	}
	GenerateSudoku(int[][][] sudoku, int numberOfGiven, int numberOfInputs){
		this.sudoku = sudoku;
		int numberOfNeeded = numberOfGiven-numberOfInputs;
		
		ABC abc = new ABC(new PrintResult("results/.xls"), this.sudoku, 100, 200, 100000, 0);
		abc.start();		
		try{
			abc.join();
		}catch(InterruptedException ie){}
		this.sudoku = null;
		this.sudoku = abc.getBestSolution();

		customSet coords = new customSet();
		while(coords.size()<numberOfNeeded){ //find random places
			int curcoord= rand.nextInt(sudoku.length*sudoku.length); //0-max-1
			if(!coords.contains(curcoord) && sudoku[(int)(curcoord/sudoku.length)][(int)(curcoord%sudoku.length)][1]!=0){
				coords.add(curcoord);
				this.sudoku[(int)(curcoord/sudoku.length)][(int)(curcoord%sudoku.length)][1] = 0; //set the number at coord as ss
			}
		}
		fillEmpty();
	}

	private void fillEmpty(){
		for(int ctr=0; ctr<sudoku.length; ctr++){
			for(int ct=ctr; ct<sudoku.length; ct++){
				if(this.sudoku[ctr][ct][1]!=0){
					this.sudoku[ctr][ct][0]=0;
					this.sudoku[ctr][ct][1]=1;
				}
				if(this.sudoku[ct][ctr][1]!=0){
					this.sudoku[ct][ctr][0]=0;
					this.sudoku[ct][ctr][1]=1;
				}
			}
		}
	}
	protected int[][][] getSudoku(){
		return sudoku;
		}
	private void sop(Object obj){
		System.out.println(obj+"");
		}
	}