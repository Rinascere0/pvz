import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Plant extends JButton{
	int timer;
	int cost,time;
	int no;
	Plant(int i,int c,int t) 
	{
		no=i;
		cost=c;
		time=t;
		timer=0;
		setBounds(85*i, 590,80 ,40);
		setEnabled(false);
		setText("<html><p>Cost:"+String.valueOf(cost)+"</p><p>Time:0:00</p></html>");

	}
	
	void renew()
	{
		String s;
		if (timer>9) 
			s=String.valueOf(timer);
		else
			s="0"+String.valueOf(timer);
		setText("<html><p>Cost:"+String.valueOf(cost)+"</p><p>Time:0:"+s+"</p></html>");
	}
}
