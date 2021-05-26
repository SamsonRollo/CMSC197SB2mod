import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
public class UIOptions extends generalPanel{
	private JPanel panel[];
	private JLabel bg;

	private String size[]={"img/exit/size/9x9.png","img/exit/size/16x16.png","img/exit/size/25x25.png"};
	private String sound[]={"img/exit/sound/on.png","img/exit/sound/off.png"};
	private String penalString[] = {"img/exit/penalty/missing.png","img/exit/penalty/SPwSC.png","img/exit/penalty/SPwoSC.png"};

	protected JLabel sizeLabel, levelLabel, soundLabel, givenPercentLabel, penaltyLabel;
	protected JButton exit, no;
	protected JButton left[]=new JButton[4];
	protected JButton right[]=new JButton[4];

	protected int sz, lvl, snd, num, percent, penalID;

	UIOptions(JPanel panel[]){
		this.panel=panel;
		panel[1].setOpaque(true);
		exit=addButton(panel[1], "img/exit/okay.png", "img/exit/h_okay.png",352,510);

		for(int ctr=0; ctr<4; ctr++){
			left[ctr]=addButton(panel[1], "img/exit/left.png", "img/exit/h_left.png",326,148+83*ctr);
			right[ctr]=addButton(panel[1], "img/exit/right.png", "img/exit/h_right.png",595,148+83*ctr);
			}
		sizeLabel=addLabel(panel[1], size[0], 389,153);
		soundLabel=addLabel(panel[1], sound[0], 389,234);
		givenPercentLabel = addLabel(panel[1], "img/exit/given/10.png", 389, 319);
		penaltyLabel = addLabel(panel[1], penalString[0], 389, 399);
		bg=addLabel(panel[1],"img/bg/options.png",100,0);

		sz=0;
		num=lvl=snd=penalID=0;
		percent = 2;
		//panel[1].setVisible(true);
		}
	protected void setSize(boolean isRight){
		if(isRight){
			sz++;
			if(sz==3)
				sz=0;
			}
		else{
			sz--;
			if(sz==-1)
				sz=2;
			}
		changePicture(sizeLabel, size[sz]);
		}
	protected void setSound(boolean isRight){
		if(isRight){
			snd++;
			if(snd==2)
				snd=0;
			}
		else{
			snd--;
			if(snd==-1)
				snd=1;
			}
		changePicture(soundLabel, sound[snd]);
		}
	protected void setGivenPercent(boolean isRight){
		if(isRight){
			percent++;
			if(percent==20)
				percent=0;
			}
		else{
			percent--;
			if(percent==-1)
				percent=19;
			}
		changePicture(givenPercentLabel, "img/exit/given/"+(percent*5)+".png");
		}
	protected void setPenalty(boolean isRight){
		if(isRight){
			penalID++;
			if(penalID==3)
				penalID=0;
			}
		else{
			penalID--;
			if(penalID==-1)
				penalID=2;
			}
		changePicture(penaltyLabel, penalString[penalID]);
		}
	protected void setVisible(boolean isVisible, int num){
		this.num=num;
		panel[1].setVisible(isVisible);
		}
	protected void decompose(){
		panel[1].removeAll();
		bg=sizeLabel=levelLabel=soundLabel=null;
		exit=no=null;
		left[0]=right[0]=left[1]=right[1]=null;
		}

	protected void setSize(int sizeIn){ //0=9x9,1=16x16,2=25x25
		switch(sizeIn){
			case 9:
				sz = 0;
				break;
			case 16:
				sz = 1;
				break;
			case 25:
				sz = 2;
				break;
		}
		changePicture(sizeLabel, size[sz]);
	}
	}
