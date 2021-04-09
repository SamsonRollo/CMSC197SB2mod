import java.io.BufferedWriter;
import java.io.FileWriter;
public class SudokuRun extends Thread{
	SudokuBee2 sudokuBee;

	SudokuRun(SudokuBee2 sudokuBee){
		this.sudokuBee = sudokuBee;
	}

	public void run(){
		//start =true;
		//while(start){ //change this, false when done searching
			int penaltyType = 0;
			try{
				penaltyType = sudokuBee.getSolve().getPenaltyType();
				sudokuBee.getSolve().decompose();
				sudokuBee.setSolve(null);
				}
			catch(Exception e){}
			sudokuBee.getGame().setVisible(1);

			if(sudokuBee.getGameMode()){
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
				//abc.setPenaltyFunctionType(penaltyType);//
				abc.start();
				delay(100);
				while(!abc.isDone()){
					delay(100);
					animate.changePic(abc.getBestSolution());
					}
				animate.decompose();
				animate=null;
				if(sudokuBee.getGenerate()){ //generation of puzzle
					GenerateSudoku gen = new GenerateSudoku(abc.getBestSolution());
					sudokuBee.boardRelay(gen.getSudoku(), false);
					gen=null;
					sudokuBee.setIsSolved(false);
					abc=null;
					}
				else{ //solving of puzzle using abc
					if(abc.getFitness()==1){
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
			else{ //solving of puzzle in experiment mode
				BufferedWriter bw = null;
				try{
					bw = new BufferedWriter(new FileWriter("results/5test.txt", true));
					bw.write(sudokuBee.getCurrentFile()+"\n");
				}catch(Exception e){e.printStackTrace();};
				SaveSudoku ss = new SaveSudoku();


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
					ABC abc = new ABC(printer, sudokuBee.getBoard().getSudokuArray(),numEmp,numOnlook, numCycle, penaltyType);
					//abc.setPenaltyFunctionType(penaltyType);//
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
					sudokuBee.getGame().setVisible(0);
					Runtime rt=Runtime.getRuntime();
					try{
						rt.exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+file);
						}
					catch (Exception ee){
						ee.printStackTrace();
						}
					sudokuBee.getBoard().decompose();
					sudokuBee.setBoard(null);
					boolean solved=false;
					if(abc.getFitness()==1){
						sudokuBee.exitRelay(8);
						sudokuBee.setBoard(new UIBoard(abc.getBestSolution(), sudokuBee.getGP().panel[5]));
						solved = true;
						}
					else{
						sudokuBee.boardRelay(abc.getBestSolution(), false);
						sudokuBee.setIsSolved(false);
						}
					ss.save(sudokuBee.getCurrentFile()+"-"+i, abc.getBestSolution());
					try{
						bw.write(abc.getCycles()+" "+seconds+" "+solved+"\n");
					}catch(Exception e){System.out.println("bw is null");};
					abc.decompose();
					abc=null;
					rt=null;
				}
				sudokuBee.getStatus().setVisible(true);
				try{
					bw.newLine();
					bw.close();
				}catch(Exception e){};
			}

			sudokuBee.getGame().setVisible(0);
			sudokuBee.setStart(false);

			//while(!start); //add as running thread unless interrupted by other means
			//}
		}
	protected void delay(int newDelay){
		try{
			sleep(newDelay);
			}
		catch(InterruptedException err){}
		}

}