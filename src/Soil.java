import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Soil extends JLabel{
	int i,j,timer,type,time,hp;
	Soil(int a,int b) 
	{
		timer=0;
		i=a;
		j=b;
		type=-1;
		setBounds(j*100,i*100, 100,100);
	}
	
	Soil(int a,int b,int x,int y,int t)
	{
		timer=0;
		i=a;
		j=b;
		type=t;
		setBounds(x,y,100,100);
	}
	
	void inc()
	{
		if ((type==5||type==7)&&timer==0)
			return;
		else 
			timer=(timer+1)%time;
	}
	
	
	
}
