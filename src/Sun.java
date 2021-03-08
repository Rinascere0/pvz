import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Sun extends JLabel{
	int timer;
	Sun(String path)
	{
		BufferedImage img=null;
		try {
			img=ImageIO.read(new File(path+"img/sun.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setIcon(new ImageIcon(img));
		Random r=new Random();
		setBounds(r.nextInt(800),r.nextInt(400),70,70);
		timer=0;
	}
	
	Sun(int x,int y,String path)
	{
		BufferedImage img=null;
		try {
			img=ImageIO.read(new File(path+"img/sun.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setIcon(new ImageIcon(img));
		setBounds(x,y,70,70);
		timer=0;
	}
}
