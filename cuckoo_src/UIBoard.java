import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Cursor;
public class UIBoard{
	private JPanel pane;
	private JLabel board;
	private int sudokuArray[][][];
	private int size, startX, startY, inc, btnX, btnY, ans;
	private int increment[]={56, 31, 20}; //change 1 and 2 for 16 and 25
	protected JButton btn[][];
	private generalPanel gp=new generalPanel();
	UIBoard(){}
	UIBoard(int sudokuArray[][][], JPanel pane){
		this.pane=pane;
		this.sudokuArray=sudokuArray;
		setConstants(false);
		}
	UIBoard(int sudokuArray[][][],boolean isNull, JPanel pane){
		this.pane=pane;
		this.sudokuArray=sudokuArray;
		ans=0;
		if(isNull)
			fill();
		setConstants(true);
		}
	private void fill(){
		size=sudokuArray.length;
		for(int ctr=0; ctr<size;ctr++){
			for(int count=0; count<size;count++){
				sudokuArray[ctr][count][0]=0; //3rd index 0 = value content
				sudokuArray[ctr][count][1]=1; //3rd index 1 = ss or not;0 = ss,1 = not
				}
			}
		}
	private void setConstants(boolean setCursor){
		size=sudokuArray.length;
		startX=size/6+6;
		if(size==25)
			startX-=2;
		startY=86;
		inc=increment[(int)Math.sqrt(size)-3];
		btn=new JButton[size][size];
		for(int ctr=0, X=startX, Y=startY; ctr<size; ctr++, Y+=inc, X=startX){
			for(int count=0; count<size; count++, X+=inc){
				String img="normal";
				if(sudokuArray[ctr][count][1]==0) // here is the proof that 3d index 1 = 0 is ss
					img="given";
				btn[ctr][count]=gp.gameButton(pane, "img/box/"+size+"x"+size+"/"+img+"/"+sudokuArray[ctr][count][0]+".png", X, Y);
				if(setCursor && img.equals("normal"))
					btn[ctr][count].setCursor(new Cursor(12));
				else
					btn[ctr][count].setCursor(new Cursor(0));
				if(sudokuArray[ctr][count][0]!=0)
					ans++;
				}
			}
		board=gp.addLabel(pane,"img/board/"+size+"x"+size+".png",0,84);
		}
	protected JButton getButton(){
		return btn[btnX][btnY];
		}
	protected int getStatus(int x, int y){
		return sudokuArray[x][y][1];
		}
	protected int[][][] getSudokuArray(){
		return sudokuArray;
		}
	protected void changeCursor(){
		for(int row=0; row<size; row++){
			for(int col=0; col<size; col++){
				btn[row][col].setCursor(new Cursor(0));
				btn[col][row].setCursor(new Cursor(0));
				}
			}
		}
	protected void changePic(){
		for(int row=0; row<size; row++){
			for(int col=row; col<size; col++){
				if(sudokuArray[row][col][1]==1)
					sudokuArray[row][col][0]=0;
				if(sudokuArray[col][row][1]==1)
					sudokuArray[col][row][0]=0;
				}
			}
		}
	protected void setSudoku(int solution[][][]){
		sudokuArray=solution;
		}
	protected void setSudokuArray(int value, boolean isAns, int x, int y){
		if(sudokuArray[x][y][0]==0 && value!=0)
			ans++;
		if(sudokuArray[x][y][0]!=0 && value==0)
			ans--;
		sudokuArray[x][y][0]=value;
		int num=1;
		if(!isAns && value!=0)
			num=0;
		sudokuArray[x][y][1]=num;
		sudokuArray[x][y][0]=value;
		}
	protected int getValue(int x, int y){
		return sudokuArray[x][y][0];
		}
	protected int getSize(){
		return size;
		}
	protected int getAns(){
		return ans;
		}
	protected void btnEnable(boolean enable){
		for(int i=0; i<sudokuArray.length; i++){
			for(int j=i; j<sudokuArray.length; j++){
				btn[i][j].setEnabled(enable);
				btn[j][i].setEnabled(enable);
			}
		}
	}
	protected void decompose(){
		pane.removeAll();
		board=null;
		sudokuArray=null;
		btn=null;
		gp=null;
		}
	}