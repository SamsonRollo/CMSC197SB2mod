import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigDecimal;

public class TestRunner{
	
	public TestRunner(){
		System.out.println("Running");
		BufferedWriter bw = null;
		String[] names = {"sabuncu9","reddwarf", "aiescargot"};
		SaveSudoku ss = new SaveSudoku();

		// for (int k=1;k<3 ;k++ ) { //penalties
		 	//for (int j=1;j<3 ;j++ ) { //file name
				try{
					bw = new BufferedWriter(new FileWriter("results/5test.txt", true));
					bw.write(names[1]+"\n");
				//	bw.close();
				}catch(Exception e){e.printStackTrace();};

				LoadSudoku ls = new LoadSudoku("save/"+names[1]+".sav");
				int[][][] sudokuCopy = getCopy(ls.getArray());
				
				//for(int i=1; i<6; i++){ //number of tests
					//try{
					//	bw = new BufferedWriter(new FileWriter("results/5test.txt", true));
					//}catch(Exception e){e.printStackTrace();};

					String file="results/result.xls";
					PrintResult printer=new PrintResult(file);
					String cycle="", time="";
					int numEmp = 1000;
					int numOnlook = 1200;
					int numCycle = 100000;
					ABC abc = new ABC(printer, sudokuCopy, numEmp,numOnlook, numCycle, 1); //change k to penaltyType
					abc.start();
					double startTime=printer.getTime();
					try{
						abc.join();
					}catch(InterruptedException ie){ie.printStackTrace();}
					
					//while(!abc.isDone()); //sentinel to wait for abc to be done
					BigDecimal end = new BigDecimal(printer.getTime());
					BigDecimal seconds=((end.subtract(new BigDecimal(startTime))).divide(new BigDecimal(1000), new java.math.MathContext(100)));
					
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

					if(abc.getFitness().compareTo(new BigDecimal(1))==0){;
						solved = true;
						}
					
					ss.save("type2/"+names[1]+"-"+6, abc.getBestSolution());
					System.out.println(names[1]+" "+6+" done"); 
					
					try{
						bw.write(abc.getCycles()+" "+seconds+" "+solved+"\n");
						bw.close();
					}catch(Exception e){System.out.println("bw is null");};
					abc.decompose();
					abc=null;
					rt=null;
				//}
			//}
		//}
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

	public static void main(String[] args){
		new TestRunner();
	}
}
