import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bullet extends JLabel{

	int type;
	BufferedImage img;
	int x,y;
	int line;
	int atk;
	int timer,centre;
	Bullet(int t,int i,int x0,int y0,String path)
	{
		type=t;
		x=x0+20;
		y=y0-35;
		line=i;
		if (type==4)
			atk=2;
		else
			atk=1;
		try {
			img=ImageIO.read(new File(path+"img\\Bullet\\"+String.valueOf(t)+".png"));
		} catch (IOException e) {}
		ImageIcon icon=new ImageIcon(img);
		setIcon(icon);
		setBounds(x,y,img.getWidth(),img.getHeight());
		if (type==4) 
		{
			timer=0;
			centre=x+200;
		}
	}
	
	void move()
	{
		if (type==4)
		{
			y+=2.5*(timer-15)+15;
			x+=30;
			timer++;
			setBounds(x,y,img.getWidth(),img.getHeight());
		}
		else
		{
			x+=10;
			setBounds(x,y,img.getWidth(),img.getHeight());
		}
	}
	
}
