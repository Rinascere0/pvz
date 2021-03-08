import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Zombie extends JLabel{
	int type;
	int spd,stimer,etimer;
	int mul;
	Soil eat;
	ImageIcon icon,icon_h,icon_b,icon_s;
	int x,y;
	int line;
	int hp;
	Zombie(int t,int l,int s,ImageIcon i0,ImageIcon i1,ImageIcon i2,ImageIcon i3)
	{
		hp=l;
		type=t;
		spd=s;
		mul=1;
		line=new Random().nextInt(5);
		x=900;
		y=100*line-20;
		stimer=0;
		etimer=0;
		eat=null;
		icon=i0;
		icon_h=i1;
		icon_b=i2;
		icon_s=i3;
		
		setBounds(x,y,120,120);
	}
	
	void set(ImageIcon i0,ImageIcon i1,ImageIcon i2,ImageIcon i3)
	{
		icon=i0;
		icon_h=i1;
		icon_b=i2;
		icon_s=i3;
	}
	
	void move()
	{
		if (spd==0)
			x-=2/mul;
		else if (spd==1)
			x-=4/mul;
		else
			x-=6/mul;
		setBounds(x,y,120,120);
	}
	
	void hurt()
	{
		if (spd!=0) setIcon(icon_h);
	}
	
	void slow()
	{
		mul=2;
		stimer=25;
		setIcon(icon_s);
	}
	void revive()
	{
		setIcon(icon);
		mul=1;
	}
	
	void boom()
	{
		setIcon(icon_b);
	}
}
