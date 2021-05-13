import java.io.BufferedWriter;
import java.io.FileWriter;

public class SudokuRun extends Thread{
	SudokuBee2 sudokuBee;
	boolean userGenerate = false;

	SudokuRun(SudokuBee2 sudokuBee, boolean userGenerate){
		this.sudokuBee = sudokuBee;
		this.userGenerate = userGenerate;
	}

	public void run(){
		int penaltyType = 0;
		try{
			penaltyType = sudokuBee.getSolve().getPenaltyType();
			sudokuBee.getSolve().decompose();
			sudokuBee.setSolve(null);
			}
		catch(Exception e){}
		sudokuBee.getGame().setVisible(1);

		if(sudokuBee.getGameMode() && !userGenerate){
			sudokuBee.getStatus().setVisible(false);
			PrintResult printer=new PrintResult("results/.xls");
			int sudoku[][][]=sudokuBee.getBoard().getSudokuArray();
			int numEmp = sudokuBee.getSubjects()[0];
			int numOnlook = sudokuBee.getSubjects()[1];
			int numCycle = sudokuBee.getSubjects()[2];
			ABC abc = new ABC(printer, sudoku, numEmp, numOnlook, numCycle, penaltyType);
			Animation animate=new Animation(sudoku, sudokuBee.getGP().special);
			sudokuBee.getBoard().decompose();
			sudokuBee.setBoard(null);
			abc.start();
			delay(100);
			while(!abc.isDone()){
				delay(100);
				animate.changePic(abc.getBestSolution());
				}
			animate.decompose();
			animate=null;
			if(sudokuBee.getGenerate()){ //generation of puzzle
				GenerateSudoku gen = new GenerateSudoku(abc.getBestSolution(), sudokuBee.getGivenPercent());
				sudokuBee.boardRelay(gen.getSudoku(), false);
				gen=null;
				sudokuBee.setIsSolved(false);
				abc=null;
				}

			else{ //solving of puzzle using abc
				if(abc.getFitness().compareTo(new java.math.BigDecimal(1))==0){
					sudokuBee.exitRelay(8);
					sudokuBee.setBoard(new UIBoard(abc.getBestSolution(), sudokuBee.getGP().panel[5]));
					}
				else{
					sudokuBee.boardRelay(abc.getBestSolution(), false);
					sudokuBee.setIsSolved(false);
					}
				abc=null;
				}
			printer.close();
			printer.delete();
			printer=null;
			sudokuBee.getStatus().setVisible(true);
		}
		else if(userGenerate){
			int sudoku[][][]=sudokuBee.getBoard().getSudokuArray();
			Subgrid subgrid[]=new Subgrid[sudoku.length];
			int subDimY=(int)Math.sqrt(sudoku.length);
			int subDimX=sudoku.length/subDimY;
			for(int ctr=0, xCount=0; ctr<sudoku.length; ctr++, xCount++){
				subgrid[ctr]=new Subgrid(xCount*subDimX, ((ctr/subDimY)*subDimY), subDimX, subDimY);
				if((ctr+1)%subDimY==0 && ctr>0)
					xCount=-1;
				}
			Animation animate=new Animation(sudoku, sudokuBee.getGP().special);
			sudokuBee.getBoard().decompose();
			sudokuBee.setBoard(null);
			Validator val=new Validator(sudoku, subgrid);
			int state = val.userPuzzleCheckValidity(sudokuBee.getGivenPercent()); //1 = ok, 0 = invalid inputs, -1 = number of givens exceeds
			animate.decompose();
			if(state==1){ //puzzle okay!
				sudoku = val.getSudoku(); //make sure to have the same sudoku array
				sudokuBee.setIsAns(true);
				sudokuBee.getStatus().decompose();
				sudokuBee.setStatus(null);
				sudokuBee.getPop().decompose();
				sudokuBee.setPop(null);
				sudokuBee.boardRelay(sudoku,false);
				sudokuBee.getGame().setVisible(0);
				sudokuBee.popUpRelay(sudoku.length);
				sudokuBee.statusRelay("");
			}
			else if(state==0){ //invalid inputs
				sudokuBee.exitRelay(4);
			}else{ //number of givens exceed
				sudokuBee.exitRelay(11);
			}
			val=null;
			sudokuBee.setIsSolved(false);		
			return;
		}
		else{ //solving of puzzle in experiment mode
			BufferedWriter bw = null;
			try{
				bw = new BufferedWriter(new FileWriter("results/5test.txt", true));
				bw.write(sudokuBee.getCurrentFile()+"\n");
			}catch(Exception e){e.printStackTrace();};

			SaveSudoku ss = new SaveSudoku();
			boolean lastHasSolved = false;
			int[][][] lastSudokSol = null;
			int[][][] sudokuCopy = getCopy(sudokuBee.getBoard().getSudokuArray());
			Animation animate=new Animation(sudokuCopy, sudokuBee.getGP().special);
			sudokuBee.getBoard().decompose();
			sudokuBee.setBoard(null);
			for(int i=1; i<6; i++){
				try{
					sudokuBee.getSolve().decompose();
					sudokuBee.setSolve(null);
				}catch(Exception e){}
				sudokuBee.getGame().setVisible(1);
				String file="results/result.xls";
				PrintResult printer=new PrintResult(file);
				sudokuBee.getStatus().setVisible(false);
				String cycle="", time="";
				int numEmp = sudokuBee.getSubjects()[0];
				int numOnlook = sudokuBee.getSubjects()[1];
				int numCycle = sudokuBee.getSubjects()[2];
				ABC abc = new ABC(printer, sudokuCopy,numEmp,numOnlook, numCycle, penaltyType);
				abc.start();
				double startTime=printer.getTime();
				try{
					abc.join();
				}catch(InterruptedException ie){}

				double end=(printer.getTime());
				double seconds=((end-startTime)/1000);
				printer.print("\nCycles:\t "+abc.getCycles()+"\nTime:\t"+seconds);
				printer.close();
				printer=null;
				
				Runtime rt=Runtime.getRuntime();

				try{
					rt.exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+file);
					}
				catch (Exception ee){
					ee.printStackTrace();
					}
				boolean solved=false;

				if(abc.getFitness().compareTo(new java.math.BigDecimal(1))==0){;
					solved = true;
					}
				lastSudokSol = abc.getBestSolution();
				lastHasSolved = solved;
				ss.save("type"+penaltyType+"/"+sudokuBee.getCurrentFile()+"-"+i, abc.getBestSolution());
				try{
					bw.write(abc.getCycles()+" "+seconds+" "+solved+"\n");
				}catch(Exception e){System.out.println("bw is null");};
				abc.decompose();
				abc=null;
				rt=null;
			}
			sudokuBee.boardRelay(lastSudokSol, false);
			sudokuBee.setIsSolved(lastHasSolved);
			sudokuBee.exitRelay(12);
			animate.decompose();
			sudokuBee.getGame().setVisible(0);
			sudokuBee.getStatus().setVisible(true);
			try{
				bw.newLine();
				bw.close();
			}catch(Exception e){};
		}

		sudokuBee.getGame().setVisible(0);
		sudokuBee.setStart(false);
	}
	protected void delay(int newDelay){
		try{
			sleep(newDelay);
			}
		catch(InterruptedException err){}
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
