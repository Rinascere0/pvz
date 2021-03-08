import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Option extends JFrame{

	JLabel[] sample;
	JButton[] plants;
	JButton confirm,reset;
	boolean[] select;
	boolean ready=false;
	int count=0;
	static int n=12;
	private static final int DEFAULT_WIDTH=520; //600	
	private static final int DEFAULT_HEIGHT=115*((n-1)/6+1)+100;
	Option(String path)
	{
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		setTitle("Choose your plants!");
		
		ImageIcon[]icon=new ImageIcon[n];
		String[]name=new String[n];
		sample=new JLabel[n];
		plants=new JButton[n];
		select=new boolean[n];
		
		BufferedReader br=null; //导入植物数据
		try {   
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path+"config\\plant.txt"),"UTF-8"));
			for (int i=0;i<n;i++)  
			{
				name[i]=br.readLine();
				br.readLine();
				br.readLine();
			}
		} catch (Exception e1) {
		}  	
		
		try {   
		for (int i=0;i<n;i++)
		{
			BufferedImage img=ImageIO.read(new File(path+"img\\plant\\"+String.valueOf(i)+"_s.png"));
			icon[i]=new ImageIcon(img);
		}
		} catch (Exception e1) {
		}  	
		
		for (int i=0;i<n;i++)  //植物预览图片
		{
			sample[i]=new JLabel();
			sample[i].setIcon(icon[i]);
			sample[i].setBounds((i%6)*85,(i/6)*115,80,80);
			getLayeredPane().add(sample[i]);
		}
		
		for (int i=0;i<n;i++)  //植物选择按钮
		{
			plants[i]=new JButton(name[i]);
			plants[i].setFont(new Font("Tw Cen MT",Font.BOLD,16));
			plants[i].setName(String.valueOf(i));
			plants[i].setBounds((i%6)*85,(i/6)*115+80,80,30);
			plants[i].addMouseListener(new MouseListener()  //选择植物
			{
					public void mousePressed(MouseEvent e) {
						JButton p=(JButton)e.getSource();
						int j=Integer.valueOf(p.getName());
						if (select[j])
						{
							select[j]=false;
							count--;
							if (count>0) confirm.setEnabled(true); else confirm.setEnabled(false);
							p.setForeground(Color.black);
						}
						else
						{
							select[j]=true;
							count++;
							if (count<=9) confirm.setEnabled(true); else confirm.setEnabled(false);
							p.setForeground(new Color(250,20,88));							
						}
					}
					public void mouseClicked(MouseEvent e) {}
					public void mouseReleased(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
			});
			getLayeredPane().add(plants[i]);
		}	
		
		confirm=new JButton("Ready!");
		confirm.setFont(new Font("Victorian LET",Font.BOLD,22));
		confirm.setForeground(new Color(128,128,0));
		confirm.setBounds(140,((n-1)/6)*115+125,100,30);
		confirm.setEnabled(false);
		confirm.addMouseListener(new MouseListener()
				{
					public void mouseClicked(MouseEvent e) {
						if (count<=9&&count>0)
							ready=true;
					}
					public void mousePressed(MouseEvent e) {					}
					public void mouseReleased(MouseEvent e) {					}
					public void mouseEntered(MouseEvent e) {					}
					public void mouseExited(MouseEvent e) {					}	
				});
		getLayeredPane().add(confirm);
		
		reset=new JButton("Reset");
		reset.setFont(new Font("Victorian LET",Font.BOLD,22));
		reset.setForeground(new Color(100,100,100));
		reset.setBounds(270,((n-1)/6)*115+125,100,30);
		reset.addMouseListener(new MouseListener()
				{
					public void mouseClicked(MouseEvent e) {
						for (int i=0;i<n;i++)
							select[i]=false;
						for (JButton b:plants)
							b.setForeground(Color.black);
						count=0;
						confirm.setEnabled(false);
					}
					public void mousePressed(MouseEvent e) {					}
					public void mouseReleased(MouseEvent e) {					}
					public void mouseEntered(MouseEvent e) {					}
					public void mouseExited(MouseEvent e) {					}	
				});
		getLayeredPane().add(reset);
		
	}
	
	int[] ret()
	{
		if (ready)
		{
			int[]a=new int[count];
			int num=0;
			for (int i=0;i<n;i++)
				if (select[i]) a[num++]=i;
			return a;
		}
		return null;
	}
}
