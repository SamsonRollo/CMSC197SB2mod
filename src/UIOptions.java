import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
public class UIOptions extends generalPanel{
	private JPanel panel[];
	private JLabel bg;

	private String size[]={"img/exit/size/9x9.png","img/exit/size/16x16.png","img/exit/size/25x25.png"};
	private String sound[]={"img/exit/sound/on.png","img/exit/sound/off.png"};

	protected JLabel sizeLabel, levelLabel, soundLabel, givenPercentLabel;
	protected JButton exit, no;
	protected JButton left[]=new JButton[3];
	protected JButton right[]=new JButton[3];

	protected int sz, lvl, snd, num, percent;

	UIOptions(JPanel panel[]){
		this.panel=panel;
		panel[1].setOpaque(true);
		exit=addButton(panel[1], "img/exit/okay.png", "img/exit/h_okay.png",385,488);

		for(int ctr=0; ctr<3; ctr++){
			left[ctr]=addButton(panel[1], "img/exit/left.png", "img/exit/h_left.png",356,236+70*ctr);
			right[ctr]=addButton(panel[1], "img/exit/right.png", "img/exit/h_right.png",568,236+70*ctr);
			}
		sizeLabel=addLabel(panel[1], size[0], 389,237);
		soundLabel=addLabel(panel[1], sound[0], 389,308);
		givenPercentLabel = addLabel(panel[1], "img/exit/given/10.png", 389, 379);
		bg=addLabel(panel[1],"img/bg/options.png",100,99);

		sz=0;
		num=lvl=snd=0;
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
				percent=2;
			}
		else{
			percent--;
			if(percent==1)
				percent=19;
			}
		changePicture(givenPercentLabel, "img/exit/given/"+(percent*5)+".png");
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
