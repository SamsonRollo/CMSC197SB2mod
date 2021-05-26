import java.io.BufferedWriter;
import java.io.FileWriter;

public class SudokuRun extends Thread{
	SudoCuckoo sudoCuckoo;
	boolean userGenerate = false;

	SudokuRun(SudoCuckoo sudoCuckoo, boolean userGenerate){
		this.sudoCuckoo = sudoCuckoo;
		this.userGenerate = userGenerate;
	}

	public void run(){
		int penaltyType = 0;
		try{
			penaltyType = sudoCuckoo.getSolve().getPenaltyType();
			sudoCuckoo.getSolve().decompose();
			sudoCuckoo.setSolve(null);
			}
		catch(Exception e){}
		sudoCuckoo.getGame().setVisible(1);

		if(sudoCuckoo.getGameMode() && !userGenerate){
			sudoCuckoo.getStatus().setVisible(false);
			PrintResult printer=new PrintResult("results/.xls");
			int sudoku[][][]=sudoCuckoo.getBoard().getSudokuArray();
			int numEmp = sudoCuckoo.getSubjects()[0];
			int numOnlook = sudoCuckoo.getSubjects()[1];
			int numCycle = sudoCuckoo.getSubjects()[2];
			
			ABC abc = new ABC(printer, sudoku, numEmp, numOnlook, numCycle, penaltyType);
			Animation animate=new Animation(sudoku, sudoCuckoo.getGP().special);
			sudoCuckoo.getBoard().decompose();
			sudoCuckoo.setBoard(null);
			abc.start();
			delay(100);
			while(!abc.isDone()){
				delay(100);
				animate.changePic(abc.getBestSolution());
				}
			animate.decompose();
			animate=null;
			if(sudoCuckoo.getGenerate()){ //generation of puzzle
				GenerateSudoku gen = new GenerateSudoku(abc.getBestSolution(), sudoCuckoo.getGivenPercent());
				sudoCuckoo.boardRelay(gen.getSudoku(), false);
				gen=null;
				sudoCuckoo.setIsSolved(false);
				abc=null;
				}

			else{ //solving of puzzle using abc
				if(abc.getFitness()==1){
					sudoCuckoo.exitRelay(8);
					sudoCuckoo.setBoard(new UIBoard(abc.getBestSolution(), sudoCuckoo.getGP().panel[5]));
					}
				else{
					sudoCuckoo.boardRelay(abc.getBestSolution(), false);
					sudoCuckoo.setIsSolved(false);
					}
				abc=null;
				}
			printer.close();
			printer.delete();
			printer=null;
			sudoCuckoo.getStatus().setVisible(true);
		}
		else if(userGenerate){
			int sudoku[][][]=sudoCuckoo.getBoard().getSudokuArray();
			Subgrid subgrid[]=new Subgrid[sudoku.length];
			int subDimY=(int)Math.sqrt(sudoku.length);
			int subDimX=sudoku.length/subDimY;
			for(int ctr=0, xCount=0; ctr<sudoku.length; ctr++, xCount++){
				subgrid[ctr]=new Subgrid(xCount*subDimX, ((ctr/subDimY)*subDimY), subDimX, subDimY);
				if((ctr+1)%subDimY==0 && ctr>0)
					xCount=-1;
				}
			Animation animate=new Animation(sudoku, sudoCuckoo.getGP().special);
			Validator val=new Validator(sudoku, subgrid);
			int state = val.userPuzzleCheckValidity(sudoCuckoo.getGivenPercent()); //1 = ok, 0 = invalid inputs, -1 = number of givens exceeds
			animate.decompose();
			if(state==1){ //puzzle okay!
				sudoCuckoo.getBoard().decompose();
				sudoCuckoo.setBoard(null);
				sudoku = val.getSudoku(); //make sure to have the same sudoku array
				sudoCuckoo.setIsAns(true);
				sudoCuckoo.getStatus().decompose();
				sudoCuckoo.setStatus(null);
				sudoCuckoo.getPop().decompose();
				sudoCuckoo.setPop(null);
				sudoCuckoo.boardRelay(sudoku,false);
				sudoCuckoo.getGame().setVisible(0);
				sudoCuckoo.popUpRelay(sudoku.length);
				sudoCuckoo.statusRelay("");
			}
			else if(state==0){ //invalid inputs
				sudoCuckoo.exitRelay(4);
			}else{ //number of givens exceed
				sudoCuckoo.exitRelay(11);
			}
			val=null;
			sudoCuckoo.setIsSolved(false);		
			return;
		}
		else{ //solving of puzzle in experiment mode
			BufferedWriter bw = null;
			try{
				bw = new BufferedWriter(new FileWriter("results/5test.txt", true));
				bw.write(sudoCuckoo.getCurrentFile()+"\n");
			}catch(Exception e){e.printStackTrace();};
			//String[] names = {"sabuncu9","aiescargot","reddwarf"};

			// boolean lastHasSolved = false;
			// int[][][] lastSudokSol = null;
			SaveSudoku ss = new SaveSudoku();
			// for (int k=1;k<3 ;k++ ) { //penalties
			// 	for (int j=0;j<4 ;j++ ) { //file name
			// 		LoadSudoku ls = new LoadSudoku("save/"+names[j]+".sav");
			// 		sudoCuckoo.boardRelay(ls.getArray(), false);

			// 		try{
			// 			bw = new BufferedWriter(new FileWriter("results/5test.txt", true));
			// 			bw.write(names[j]+"\n");
			// 		}catch(Exception ee){};

					boolean lastHasSolved = false;
					int[][][] lastSudokSol = null;
					int[][][] sudokuCopy = getCopy(sudoCuckoo.getBoard().getSudokuArray());
					//int[][][] sudokuCopy = getCopy(ls.getArray());
					Animation animate=new Animation(sudokuCopy, sudoCuckoo.getGP().special);
					sudoCuckoo.getBoard().decompose();
					sudoCuckoo.setBoard(null);
					for(int i=1; i<6; i++){
						try{
							sudoCuckoo.getSolve().decompose();
							sudoCuckoo.setSolve(null);
						}catch(Exception e){}
						sudoCuckoo.getGame().setVisible(1);
						String file="results/result.xls";
						PrintResult printer=new PrintResult(file);
						sudoCuckoo.getStatus().setVisible(false);
						String cycle="", time="";
						int numEmp = sudoCuckoo.getSubjects()[0];
						int numOnlook = sudoCuckoo.getSubjects()[1];
						int numCycle = sudoCuckoo.getSubjects()[2];
						ABC abc = new ABC(printer, sudokuCopy, numEmp,numOnlook, numCycle, penaltyType); //change k to penaltyType
						abc.start();
						double startTime=printer.getTime();
						try{
							abc.join();
						}catch(InterruptedException ie){}
						
						//while(!abc.isDone()); //sentinel to wait for abc to be done
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

						if(abc.getFitness()==1){;
							solved = true;
							}
						lastSudokSol = abc.getBestSolution();
						lastHasSolved = solved;
						ss.save("type"+penaltyType+"/"+sudoCuckoo.getCurrentFile()+"-"+i, abc.getBestSolution());
						//ss.save("type"+penaltyType+"/"+names[j]+"-"+i, abc.getBestSolution());
						try{
							bw.write(abc.getCycles()+" "+seconds+" "+solved+"\n");
						}catch(Exception e){System.out.println("bw is null");};
						abc.decompose();
						abc=null;
						rt=null;
					}
					// animate.decompose();//delete later
					// try{
					// 	bw.newLine();
					// 	bw.close();
					// }catch(Exception e){};
			// 	}
			// }
			
			sudoCuckoo.boardRelay(lastSudokSol, false);
			sudoCuckoo.setIsSolved(lastHasSolved);
			sudoCuckoo.exitRelay(12);
			animate.decompose();
			sudoCuckoo.getGame().setVisible(0);
			sudoCuckoo.getStatus().setVisible(true);
			try{
				bw.newLine();
				bw.close();
			}catch(Exception e){};
		}

		sudoCuckoo.getGame().setVisible(0);
		sudoCuckoo.setStart(false);

		//while(!start); //add as running thread unless interrupted by other means
		//}
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
