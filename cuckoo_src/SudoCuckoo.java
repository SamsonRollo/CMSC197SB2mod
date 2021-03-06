import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Container;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
public class SudoCuckoo{
	private generalPanel GP;
	private UIGame game;
	private UIExit exit;
	private UIBoard board;
	private UIStatus status;
	private UIPop pop;
	private UIOptions options;
	private UISave save;
	private UISolve solve;
	private UILoad load;
	private UIHelp help;
	private int btnX, btnY;
	private int numOnlook, numEmp, numCycle;
	private boolean isAns=false, generate=true, start=false, gameMode=true, isSolved=false;
	private Tunog snd, error;
	private JFrame frame=new JFrame();
	private Container container=frame.getContentPane();
	private String saveFileName="", curFile="File";

	SudoCuckoo(){
		frame.setTitle(" Sudoku Cuckoo");
		snd = new Tunog("snd/1.mid");
		error = new Tunog("snd/error.wav");
		snd.loop(); //not working sound on linux
		menu();
		options();
		frame.setSize(800,630);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void menu(){
		GP=new generalPanel(container);
		GP.play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mainGame();
				status("");
				isAns=true;
				int size=(int)Math.pow((options.sz+3),2);
				board(new int[size][size][2], true);
				numEmp=100;
				numOnlook=200;
				numCycle=100000000;
				generate=true;
				gameMode=true;
				isSolved=false;
				try{
					SudokuRun sr2 = new SudokuRun(getSudokuBee(), false);
					sr2.start(); //generate a puzzle
					}
				catch(Exception ee){
					start=true;
					}
				popUp(size);
				}
			});
		GP.open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				GP.setVisibleButton(false);
				int size=(int)Math.pow((options.sz+3),2);
				isSolved=false;
				board(new int[size][size][2], true);
				loadSudoku(7);
				}
			});
		GP.create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mainGame();
				isAns=false;
				int size=(int)Math.pow((options.sz+3),2);
				isSolved=false;
				board(new int[size][size][2], true);
				game.setVisible(false);
				status("create");
				popUp(size);
				}
			});
		GP.options.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				GP.setVisibleButton(false);
				options.setVisible(true, 0);
				}
			});
		GP.help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				help(7);
				}
			});
		GP.exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				GP.setVisibleButton(false);
				exit(0);
				}
			});
	}
	
	private void loadSudoku(int num){
		GP.setVisible(num);
		load=new UILoad(GP.solve);
		load.lists.grabFocus();
		final int number=num;
		try{
			status.setVisible(false);
			}
		catch(Exception e){}
		load.cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					game.setVisible(true);
					status.setVisible(true);
					}
				catch(Exception ee){}
				GP.setVisibleButton(true);
				load.decompose();
				load=null;
				GP.setVisible(number);
				}
			});
		load.load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				open(load.lists.getSelectedValue()+"");
				load.decompose();
				load=null;
				}
			});
	}
	
	private void open(String str){
		LoadSudoku sod=new LoadSudoku("save/"+str+".sav");
		curFile = str;
		if(sod.getStatus()){
			board.decompose();
			try{
				game.decompose();
				}
			catch(Exception e){}
			mainGame();
			status("");
			isAns=true;
			board=null;
			board(sod.getArray(), false);
			popUp(sod.getSize());
			options.setSize(sod.getSize());
			}
		else{
			exit(3);
			}
		sod=null;
		}
	protected void boardRelay(int sudokuArray[][][], boolean isNull){
		board(sudokuArray, isNull);
	}

	private void board(int sudokuArray[][][], boolean isNull){ //0=start square, 1= not
		GP.setVisible(5);
		board=new UIBoard(sudokuArray, isNull, GP.panel[5]);
		int size=board.getSize();
		for(btnX=0; btnX<size; btnX++){
			for(btnY=0; btnY<size; btnY++){
				if(board.getStatus(btnX, btnY)!=0){
					final int x=btnX;
					final int y=btnY;
					board.btn[btnX][btnY].addMouseListener(new MouseAdapter(){
						public void mouseClicked(MouseEvent e){
							if(!isSolved && e.getModifiers()==4){
								pop.setVisible(true, x, y, board.getValue(x, y));
								status.setVisible(false);
								game.setVisible(false);
								}
							}
						});
					}
				}
			}
		}
	protected void popUpRelay(int size){
		popUp(size);
	}

	private void popUp(int size){
		try{
			pop.decompose();
			pop=null;
			}
		catch(Exception e){}
		pop=new UIPop(size, GP.panel[3]);
		for(int ctr=0; ctr<size; ctr++){
			final int popCounter=ctr+1;
			pop.btn[ctr].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					int size=pop.size;
					GP.changePicture(board.btn[pop.btnX][pop.btnY],"img/box/"+size+"x"+size+"/normal/"+popCounter+".png");
					board.setSudokuArray(popCounter, isAns,pop.btnX, pop.btnY);
					pop.field.setForeground(java.awt.Color.black);
					pop.setVisible(false,0,0,0);
					status.setVisible(true);
					game.setVisible(isAns);
					if(isAns && board.getAns()==size*size){
						int sudoku[][][]=board.getSudokuArray();
						Subgrid subgrid[]=new Subgrid[sudoku.length];
						int subDimY=(int)Math.sqrt(sudoku.length);
						int subDimX=sudoku.length/subDimY;
						for(int ctr=0, xCount=0; ctr<sudoku.length; ctr++, xCount++){
							subgrid[ctr]=new Subgrid(xCount*subDimX, ((ctr/subDimY)*subDimY), subDimX, subDimY);
							if((ctr+1)%subDimY==0 && ctr>0)
								xCount=-1;
							}
						if(new Validator(sudoku, subgrid).checkAnswer()){
							exit(5);
						}
						}
					}
				});
			}
		pop.field.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent eee){
				String str=pop.field.getText();
				if(str.length()>2 || !(eee.getKeyCode()>47 && eee.getKeyCode()<58 || eee.getKeyCode()>95 && eee.getKeyCode()<106 || eee.getKeyCode()==KeyEvent.VK_BACK_SPACE || eee.getKeyCode()==KeyEvent.VK_ENTER) ){
					try{
						pop.field.setText(str.substring(0,str.length()-1));
						}
					catch(Exception ee){}
					}
				else if(eee.getKeyCode()>47 && eee.getKeyCode()<58 || eee.getKeyCode()>95 && eee.getKeyCode()<106 || eee.getKeyCode()==KeyEvent.VK_BACK_SPACE){
					try{
						if(Integer.parseInt(str)>pop.size)
							pop.field.setForeground(java.awt.Color.red);
						else
							pop.field.setForeground(java.awt.Color.black);
						}
					catch(Exception e){}
					}
				}
			public void keyTyped(KeyEvent eee){}
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					String str=pop.field.getText();
					if(str.length()==0){
						GP.changePicture(board.btn[pop.btnX][pop.btnY],"img/box/"+pop.size+"x"+pop.size+"/normal/0.png");
						board.setSudokuArray(0, false,pop.btnX, pop.btnY);
						pop.setVisible(false,0,0,0);
						status.setVisible(true);
						game.setVisible(isAns);
						}
					else{
						try{
							int size=pop.size, num=Integer.parseInt(str);
							if(num<=size && num>=1){
								GP.changePicture(board.btn[pop.btnX][pop.btnY],"img/box/"+size+"x"+size+"/normal/"+num+".png");
								board.setSudokuArray(num, isAns,pop.btnX, pop.btnY);
								pop.setVisible(false,0,0,0);
								status.setVisible(true);
								game.setVisible(isAns);
								pop.field.setForeground(java.awt.Color.black);
								if(isAns && board.getAns()==size*size){
									int sudoku[][][]=board.getSudokuArray();
									Subgrid subgrid[]=new Subgrid[sudoku.length];
									int subDimY=(int)Math.sqrt(sudoku.length);
									int subDimX=sudoku.length/subDimY;
									for(int ctr=0, xCount=0; ctr<sudoku.length; ctr++, xCount++){
										subgrid[ctr]=new Subgrid(xCount*subDimX, ((ctr/subDimY)*subDimY), subDimX, subDimY);
										if((ctr+1)%subDimY==0 && ctr>0)
											xCount=-1;
										}
									if(new Validator(sudoku, subgrid).checkAnswer())
										exit(5);
									}
								}
							else
								throw new Exception();
							}
						catch(Exception eee){
							error.play();
							}
						}
					}
				}
			});
		pop.erase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				GP.changePicture(board.btn[pop.btnX][pop.btnY],"img/box/"+pop.size+"x"+pop.size+"/normal/0.png");
				board.setSudokuArray(0, false,pop.btnX, pop.btnY);
				pop.setVisible(false,0,0,0);
				status.setVisible(true);
				game.setVisible(isAns);
				}
			});
		pop.cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pop.setVisible(false,0,0,0);
				status.setVisible(true);
				game.setVisible(isAns);
				}
			});
		}
	private void mainGame(){
		GP.setVisible(6);
		game=new UIGame(GP.panel[6]);
		game.newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				game.setVisible(false);
				status.setVisible(false);
				isSolved=false;
				exit(2);
				}
			});
		game.exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				game.setVisible(false);
				status.setVisible(false);
				exit(1);
				}
			});
		game.options.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				game.setVisible(false);
				status.setVisible(false);
				options.setVisible(true,1);
				}
			});
		game.solve.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				game.setVisible(false);
				status.setVisible(false);
				game.solve.setEnabled(false);
				solve();
				game.solve.setEnabled(true);
				}
			});
		game.help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				help(5);
				}
			});
		}
	private void help(int num){
		GP.setVisible(0);
		help=new UIHelp(GP.panel[0], num);
		help.next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				help.increase();
				}
			});
		help.back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				help.decrease();
				}
			});
		help.cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				help.decompose();
				GP.setVisible(help.panelNum);
				help=null;
				}
			});
		}
	private void solve(){
		solve=new UISolve(GP.solve);
		solve.cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				status.setVisible(true);
				game.setVisible(true);
				solve.decompose();
				solve=null;
				GP.setVisible(5);
				}
			});
		solve.mode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				solve.changeMode();
				}
			});
		solve.left.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				solve.setPenalty(false);
				}
			});
		solve.right.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				solve.setPenalty(true);
				}
			});

		solve.solve.addActionListener(new ActionListener(){ //solving the puzzle using ABC
			public void actionPerformed(ActionEvent e){
				status.setVisible(false);
				try{
					numEmp=Integer.parseInt(solve.numEmployed.getText());
					numOnlook=Integer.parseInt(solve.numOnlook.getText());
					numCycle=Integer.parseInt(solve.numCycles.getText());
					generate=false;
					status.setVisible(false);
					if(numEmp>=numOnlook || numEmp<2)
						throw new Exception();
					if(solve.modeNum==0)
						gameMode=true;
					else
						gameMode=false;
					try{
						SudokuRun sr = new SudokuRun(getSudokuBee(), false);
						sr.start();
						}
					catch(Exception ee){
						start=true;
						}
					}
				catch(Exception ee){
					solve.decompose();
					solve=null;
					GP.setVisible(5);
					exit(7);
					}
				}
			});
		}

	protected void statusRelay(String str){
		status(str);
	}

	private void status(String str){
		status=new UIStatus(str, GP.panel[4]);
		status.yes.addActionListener(new ActionListener(){ //creating user made sudoku
			public void actionPerformed(ActionEvent e){
				status.setVisible(false);
				try{
					SudokuRun sr4 = new SudokuRun(getSudokuBee(), true);
					sr4.start();
				}catch(Exception ee){status.setVisible(true);};
				}
			});
		status.no.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				exit(1);
				}
			});
		status.open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				game.setVisible(false);
				status.setVisible(false);
				isSolved=false;
				loadSudoku(5);
				}
			});
		status.save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				game.setVisible(false);
				status.setVisible(false);
				save();
				}
			});
		status.reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				game.setVisible(false);
				status.setVisible(false);
				exit(10);
				}
			});
		}
	private void save(){
		save=new UISave(GP.panel[2]);
		save.field.grabFocus();
		status.setVisible(false);
		game.setVisible(false);
		save.cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				game.setVisible(true);
				status.setVisible(true);
				save.decompose();
				GP.setVisible(5);
				}
			});
		save.save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				saveFileName=save.field.getText();
				SaveSudoku saving=new SaveSudoku();
				int num=saving.save(saveFileName, board.getSudokuArray());
				save.decompose();
				GP.setVisible(5);
				if(saveFileName.length()>0 && !(saveFileName.contains("/") || saveFileName.contains("\\") || saveFileName.contains(":")  || saveFileName.contains("*") || saveFileName.contains("?")  || saveFileName.contains("\"") || saveFileName.contains("<") || saveFileName.contains(">"))&& num==0){
					saveFileName="";
					game.setVisible(true);
					status.setVisible(true);
					}
				else if(saveFileName.length()==0 && (saveFileName.contains("/") || saveFileName.contains("\\") || saveFileName.contains(":")  || saveFileName.contains("*") || saveFileName.contains("?")  || saveFileName.contains("\"") || saveFileName.contains("<") || saveFileName.contains(">")) ||  num==1){
					exit(6);
					status.setVisible(false);
					game.setVisible(false);
					saveFileName="";
					}
				else{
					status.setVisible(false);
					game.setVisible(false);
					exit(9);
					}
				}
			});
		save.field.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent ee){}
			public void keyTyped(KeyEvent eee){}
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					saveFileName=save.field.getText();
					SaveSudoku saving=new SaveSudoku();
					int num=saving.save(saveFileName, board.getSudokuArray());
					save.decompose();
					GP.setVisible(5);
					if(saveFileName.length()>0 && !(saveFileName.contains("/") || saveFileName.contains("\\") || saveFileName.contains(":")  || saveFileName.contains("*") || saveFileName.contains("?")  || saveFileName.contains("\"") || saveFileName.contains("<") || saveFileName.contains(">"))&& num==0){
						game.setVisible(true);
						status.setVisible(true);
						saveFileName="";
						}
					else if(saveFileName.length()==0 && (saveFileName.contains("/") || saveFileName.contains("\\") || saveFileName.contains(":")  || saveFileName.contains("*") || saveFileName.contains("?")  || saveFileName.contains("\"") || saveFileName.contains("<") || saveFileName.contains(">")) ||  num==1){
						exit(6);
						status.setVisible(false);
						game.setVisible(false);
						saveFileName="";
						}
					else{
						status.setVisible(false);
						game.setVisible(false);
						exit(9);
						}
					}
				}
			});
		}
	protected void exitRelay(int num){
		exit(num);
	}

	private void exit(int num){
		if(exit==null){
			exit=new UIExit(GP.panel[0], num);
			exit.yes.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					GP.setVisibleButton(true);
					try{
						game.setVisible(true);
						status.setVisible(true);
						}
					catch(Exception ee){}
					exit.decompose();
					if(exit.num==0)
						System.exit(0);
					else if(exit.num==1){
						board.decompose();
						board=null;
						game.decompose();
						game=null;
						status.decompose();
						status=null;
						pop.decompose();
						pop=null;
						GP.setVisible(7);
						}
					else if(exit.num==2){
						board.decompose();
						board=null;
						game.decompose();
						game=null;
						status.decompose();
						status=null;
						pop.decompose();
						pop=null;
						mainGame();
						status("");
						isAns=true;
						int size=(int)Math.pow((options.sz+3),2);
						board(new int[size][size][2], true);
						numEmp=100;
						numOnlook=200;
						numCycle=100000000;
						generate=true;
						gameMode=true;
						try{
						SudokuRun sr3 = new SudokuRun(getSudokuBee(), false);
							sr3.start();
							}
						catch(Exception ee){
							start=true;
							}
						popUp(size);
						}
					else if(exit.num==9){
						GP.setVisible(5);
						SaveSudoku saving=new SaveSudoku();
						saving.delete(saveFileName);
						saving.save(saveFileName, board.getSudokuArray());
						}
					else if(exit.num==10){
						isSolved=false;
						GP.setVisible(5);
						board.changePic();
						int[][][] sudoku=board.getSudokuArray();
						board.decompose();
						board=null;
						board(sudoku, false);
						}
					exit=null;
					}
				});
			exit.no.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					GP.setVisibleButton(true);
					try{
						game.setVisible(true);
						status.setVisible(true);
						}
					catch(Exception ee){}
					exit.decompose();
					if(exit.num==0)
						GP.setVisible(7);
					else if(exit.num==1 || exit.num==2 || exit.num==6 || exit.num==10)
						GP.setVisible(5);
					else if(exit.num==9){
						GP.setVisible(5);
						save();
						}
					exit=null;
					}
				});
			exit.okay.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					GP.setVisibleButton(true);
					game.setVisible(true);
					status.setVisible(true);
					if(exit.num==7)
						solve();
					else if(exit.num==5){
						GP.setVisible(5);
						isSolved=true;
						board.changeCursor();
						}
					else if(exit.num==8 || exit.num==12){
						GP.setVisible(5);
						isSolved=true;
						}
					else if(exit.num!=4 && exit.num!=6 && exit.num!=11){
						board.decompose();
						board=null;
						GP.setVisible(7);
						}
					else if(exit.num==4 || exit.num==11){
						GP.setVisible(5);
						status.setVisible(true);
						game.setVisible(false);
						game.setVisible(-1);
						}
					else{
						GP.setVisible(5);
						}
					exit.decompose();
					exit=null;
					}
				});
			}
		}
	private void options(){
		options=new UIOptions(GP.panel);
		options.exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					game.setVisible(true);
					status.setVisible(true);
					}
				catch(Exception ee){}
				GP.setVisibleButton(true);
				if(options.num==0)
					GP.setVisible(7);
				else
					GP.setVisible(5);
				}
			});
		options.left[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				options.setSize(false);
				}
			});
		options.left[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				options.setSound(false);
				if(options.snd==1)
					snd.stop();
				else
					snd.loop();
				}
			});
		options.left[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				options.setGivenPercent(false);
				}
			});
		options.left[3].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				options.setPenalty(false);
				}
			});
		options.right[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				options.setSize(true);
				}
			});
		options.right[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				options.setSound(true);
				if(options.snd==1)
					snd.stop();
				else
					snd.loop();
				}
			});
		options.right[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				options.setGivenPercent(true);
				}
			});
		options.right[3].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				options.setPenalty(true);
				}
			});
		}
	public SudoCuckoo getSudokuBee(){
		return this;
	}
	public UISolve getSolve(){
		return solve;
	}
	public void setSolve(UISolve solve){
		this.solve = solve;
	}
	public UIBoard getBoard(){
		return board;
	}
	public void setBoard(UIBoard board){
		this.board = board;
	}
	public boolean getStart(){
		return start;
	}
	public void setStart(boolean start){
		this.start = start;
	}
	public UIGame getGame(){
		return game;
	}
	public UIStatus getStatus(){
		return status;
	}
	public void setStatus(UIStatus status){
		this.status = status;
	}
	public generalPanel getGP(){
		return GP;
	}
	public boolean getGameMode(){
		return gameMode;
	}
	public boolean getGenerate(){
		return generate;
	}
	public void setIsSolved(boolean isSolved){
		this.isSolved = isSolved;
	}
	public boolean getIsSolved(){
		return isSolved;
	}
	public UIPop getPop(){
		return pop;
	}
	public void setPop(UIPop pop){
		this.pop = pop;
	}
	public int[] getSubjects(){
		int[] subs = {numEmp,numOnlook,numCycle};
		return subs;
	}
	public String getCurrentFile(){
		return curFile;
	}
	public int getGivenPercent(){
		return options.percent*5;
	}
	public int getPenaltyType(){
		return options.penalID;
	}
	public void setIsAns(boolean isAns){
		this.isAns = isAns;
	}
	private void sop(Object obj){
		System.out.println(obj+"");
		}
	public static void main(String args[]){
		SudoCuckoo app=new SudoCuckoo();
		}
	}
