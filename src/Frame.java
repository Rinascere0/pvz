import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class Frame extends JFrame{
	private static final int DEFAULT_WIDTH=1000; //600	
	private static final int DEFAULT_HEIGHT=670;
	final int plant_num=12,zombie_num=4;
	ArrayList<Bullet>bullets=new ArrayList<Bullet>();
	ArrayList<Zombie>zombies=new ArrayList<Zombie>();
	ArrayList<Sun>suns=new ArrayList<Sun>();
	Soil[][] soil=new Soil[5][9];
	ArrayList<Soil>tree=new ArrayList<Soil>();
	ArrayList<Soil>effect=new ArrayList<Soil>();
	JLabel[]sample=new JLabel[plant_num];
	Plant[]plants;
	int[]cost=new int[plant_num];
	int[]time=new int[plant_num];
	int[]iter=new int[plant_num];
	int[]life=new int[zombie_num];
	int[]spd=new int[zombie_num]; 
	ArrayList<Integer>eff_time=new ArrayList<Integer>();
	ImageIcon icon_n,icon_b,icon_5,icon_7,icon_fire,icon_11;
	ImageIcon icon[]=new ImageIcon[plant_num];
	ImageIcon icon_z[]=new ImageIcon[zombie_num];
	ImageIcon icon_zhurt[]=new ImageIcon[zombie_num];
	ImageIcon icon_zslow[]=new ImageIcon[zombie_num];
	ImageIcon icon_boom;
	ImageIcon icon_3;
	int select=-1,sun_val;
	Sun sun;
	JLabel sun_lab,brain_lab,time_lab;
	BufferedImage[] img_z=new BufferedImage[zombie_num];
	BufferedImage img;
	int[]choose;
	String path;
	
	Frame(int[]c,String p){
		setTitle("Plants vs Zombies");
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		path=p;
		
		choose=c;
		plants=new Plant[c.length];
		
		sun_lab=new JLabel("Sun: 0");
		sun_lab.setFont(new Font("Times New Roman",Font.BOLD,35));
		sun_lab.setForeground(new Color(255,128,0));
		sun_lab.setBounds(780,530,150,30);
		getLayeredPane().add(sun_lab);
		
		brain_lab=new JLabel("Brain: 5");
		brain_lab.setFont(new Font("Times New Roman",Font.BOLD,35));
		brain_lab.setForeground(Color.pink);
		brain_lab.setBounds(780,580,150,30);
		getLayeredPane().add(brain_lab);
		
		time_lab=new JLabel("0");
		time_lab.setFont(new Font("Times New Roman",Font.BOLD,35));
		time_lab.setBounds(930,540,150,30);
		getLayeredPane().add(time_lab);
		
		BufferedReader br=null; //导入植物数据
		try {   
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path+"config\\plant.txt"),"UTF-8"));
			for (int i=0;i<plant_num;i++)  
			{	
					cost[i]=Integer.valueOf(br.readLine());
					time[i]=Integer.valueOf(br.readLine());
					iter[i]=Integer.valueOf(br.readLine());
			}
		} catch (Exception e1) {
		}  		

		try {   	//导入僵尸数据
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path+"config\\zombie.txt"),"UTF-8"));
			for (int i=0;i<zombie_num;i++)  
			{	
					life[i]=Integer.valueOf(br.readLine());
					spd[i]=Integer.valueOf(br.readLine());
			}
		} catch (Exception e1) {
		}  
			
		try {   //导入植物图像数据
			String img_path=path+"img\\plant\\";
			for (int k=0;k<plant_num;k++)
			{
				img=ImageIO.read(new File(img_path+String.valueOf(k)+".jpg"));
				icon[k]=new ImageIcon(img);
			}
			img=ImageIO.read(new File(img_path+"grass.jpg"));	 //空地
			icon_n=new ImageIcon(img);
			img=ImageIO.read(new File(img_path+"boom.jpg"));  //爆炸
			icon_b=new ImageIcon(img);
			img=ImageIO.read(new File(img_path+"5_1.jpg")); //地雷出土
			icon_5=new ImageIcon(img);
			img=ImageIO.read(new File(img_path+"7_1.jpg")); //食人花冷却
			icon_7=new ImageIcon(img);
			img=ImageIO.read(new File(path+"img\\Bullet\\3.png"));  //燃烧弹
			icon_fire=new ImageIcon(img);
			img=ImageIO.read(new File(img_path+"11.png"));  //窝瓜
			icon_11=new ImageIcon(img);
		
		} catch (IOException e1) {}

		  
		try {   //导入僵尸图像数据
			String img_path=path+"img\\zombie\\";
			for (int k=0;k<zombie_num;k++)
			{	
				img=ImageIO.read(new File(img_path+String.valueOf(k)+".png"));  //僵尸原图
				icon_z[k]=new ImageIcon(img);
				img=ImageIO.read(new File(img_path+String.valueOf(k)+"_hurt.png"));  //受攻击
				icon_zhurt[k]=new ImageIcon(img);
				img=ImageIO.read(new File(img_path+String.valueOf(k)+"_slow.png"));  //被减速
				icon_zslow[k]=new ImageIcon(img);
			}
			img=ImageIO.read(new File(img_path+"boom.png"));  //死亡
			icon_boom=new ImageIcon(img);
			img=ImageIO.read(new File(img_path+"3_1.png"));  //死亡
			icon_3=new ImageIcon(img);

		} catch (IOException e1) {}

		
		for (int i=0;i<5;i++)  //生成土地
			for (int j=0;j<9;j++)
			{
				soil[i][j]=new Soil(i,j);
				soil[i][j].setIcon(icon_n);
				soil[i][j].addMouseListener(new MouseListener()  //播种
				{	
					public void mousePressed(MouseEvent e) {
						if (e.getButton()==1)
						{
							Soil s=(Soil)e.getSource();
							if (s.getIcon()==icon_n)
							{
								if (select!=-1)
								{
									s.setIcon(icon[choose[select]]);
									s.type=choose[select];	
									s.time=iter[choose[select]];
									if (select==3)
										s.hp=70	;
									else
										s.hp=6;
									sun_val-=plants[select].cost;
									sun_lab.setText("Sun: "+String.valueOf(sun_val));
									plants[select].setForeground(null);
									plants[select].timer=plants[select].time;
									tree.add(s);
									s.timer=0;
									if (choose[select]==5) s.timer=2;
									if (choose[select]==4||choose[select]==10) 
									{
										effect.add(s);
										eff_time.add(2);
									}
									for (Plant p:plants)
										if (p.cost>sun_val) p.setEnabled(false);
									select=-1;
								}
							}
						}
						else if (e.getButton()==3)
						{
							Soil s=(Soil)e.getSource();
							s.setIcon(icon_n);
							s.type=-1;
							tree.remove(s);
						}
					}
					public void mouseClicked(MouseEvent e) {}
					public void mouseReleased(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
				});
				getLayeredPane().add(soil[i][j],-1);
			}
		
		for (int i=0;i<choose.length;i++)  //植物预览图片
		{
			sample[i]=new JLabel();
			try {
				sample[i].setIcon(new ImageIcon(ImageIO.read(new File(path+"img\\plant\\"+String.valueOf(choose[i])+"_s.png"))));
			} catch (IOException e) {
			}
			sample[i].setBounds(i*85,500,100,100);
			getLayeredPane().add(sample[i]);
		}
		
		for (int i=0;i<choose.length;i++)  //植物选择按钮
		{
			plants[i]=new Plant(i,cost[choose[i]],time[choose[i]]);
			plants[i].addMouseListener(new MouseListener()  //选择植物
			{
					public void mousePressed(MouseEvent e) {
						if (select!=-1) plants[select].setForeground(Color.black);
						Plant p=(Plant)e.getSource();
						if (p.isEnabled())
						{
							select=p.no;
							p.setForeground(Color.red);
						}
					}
					public void mouseClicked(MouseEvent e) {}
					public void mouseReleased(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
			});
			if (plants[i].cost<=sun_val&&plants[i].timer==0)
				plants[i].setEnabled(true);
			else
				plants[i].setEnabled(false);
			getLayeredPane().add(plants[i]);
		}
		

		
		getLayeredPane().setLayout(null);	
	}
	
	void start() 
	{
		Bullet bullet;
		Zombie zombie;
		
		ArrayList<Zombie>waste=new ArrayList<Zombie>();
		ArrayList<Soil>bad=new ArrayList<Soil>();

		int timer=0;
		int zombie_cd=10000;
		int brain=5,kill_count=0;
	
		boolean set=true;
		sun_val=0;
		Random r=new Random();
		
		int type0=10,type1=15,type2=40;
		while(true)
		{
			time_lab.setText(String.valueOf(timer));
			if (timer==Integer.MAX_VALUE) timer=0;
			if (timer==99&&set) {zombie_cd=100;set=false;}
		/*	if (kill_count>=3) zombie_cd=80;
			if (kill_count>=6)	zombie_cd=60;
			if (kill_count>=10) zombie_cd=40;
			if (kill_count>=15) zombie_cd=30;
			if (kill_count>=21) zombie_cd=20;
			if (kill_count>=28) zombie_cd=10;
			if (kill_count>=36) zombie_cd=5;
			if (kill_count>=45) zombie_cd=3;*/
			if (timer==500) zombie_cd=70;
			if (timer==800) zombie_cd=50;
			if (timer==1000) zombie_cd=60;
			if (timer==1100) zombie_cd=40;
			if (timer==1300) zombie_cd=30;
			if (timer==1400) zombie_cd=50;
			if (timer==1700) zombie_cd=60;
			if (timer==2000) zombie_cd=40;
			if (timer==2200) zombie_cd=20;
			
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {}
			timer++;
			
			for (Zombie z:waste) //删除死亡僵尸
			{
				z.setIcon(null);
				getLayeredPane().remove(z);
				repaint();
			}
			
			waste.removeAll(waste);
				
			for (Zombie z:zombies)  //僵尸受攻击后恢复
				if (z.stimer>0)
					z.stimer--;
				else z.revive();
			
			for (Soil s:bad)
				s.setIcon(icon_n);
			
			bad.removeAll(bad);
			
			for (int i=0;i<effect.size();i++)  //延迟生效
			{
				if (eff_time.get(i)>0)
				{
					eff_time.set(i,eff_time.get(i)-1);
					continue;
				}
				
				Soil s=effect.get(i);			
				if (s.type==4)  //樱桃炸弹
				{
					for (int j=0;j<zombies.size();j++)
					{
						Zombie y=zombies.get(j);
						if ((y.line>=s.i-1&&y.line<=s.i+1)&&(y.getX()>=s.getX()-100&&y.getX()<=s.getX()+200))
						{
							zombies.remove(j);
							y.boom();
							waste.add(y);
							kill_count++;
							j--;
						}
					}
					s.setIcon(icon_b);
					tree.remove(s);
					effect.remove(i);
					eff_time.remove(i);
					i--;
					bad.add(s);
					s.type=-1;
					repaint();
				}
				
				else if (s.type==10)  //胡椒
				{
					for (int j=0;j<zombies.size();j++)
					{
						Zombie y=zombies.get(j);
						if (y.line==s.i)
						{
							zombies.remove(j);
							y.boom();
							waste.add(y);
							kill_count++;
							j--;
						}
					}
					s.setIcon(icon_b);
					tree.remove(s);
					effect.remove(i);
					eff_time.remove(i);
					i--;
					bad.add(s);
					s.type=-1;
					repaint();
				}
				else if (s.type==11)  //窝瓜
				{
					for (int j=0;j<zombies.size();j++)
					{
						Zombie y=zombies.get(j);
						if (y.line==s.i&&y.getX()>=s.getX()-50&&y.getX()<=s.getX()+50)
						{
							zombies.remove(j);
							y.boom();
							waste.add(y);
							kill_count++;
							j--;
						}
					}
					s.setBounds(s.getX(),s.getY()+100,100,100);
					bad.add(s);
					effect.remove(i);
					eff_time.remove(i);
					i--;
					repaint();
				}
			}
			
			if (timer%zombie_cd==0)  //生成僵尸
			{
				int t=r.nextInt(100);
				if (t<=type0) t=3;
				else if (t<=type1) t=2;
				else if (t<=type2) t=1;
				else t=0;
				zombie=new Zombie(t,life[t],spd[t],icon_z[t],icon_zhurt[t],icon_boom,icon_zslow[t]);
				zombies.add(zombie);
				getLayeredPane().add(zombie,new Integer(Integer.MAX_VALUE-1));
			}
			
			if (timer%50==0)  //生成阳光
			{
				sun=new Sun(path);
				suns.add(sun);
				sun.addMouseListener(new MouseListener()
				{
					public void mousePressed(MouseEvent e) {
						Sun s=(Sun)e.getSource();
						getLayeredPane().remove(s);
						repaint();
						suns.remove(s);
						sun_val+=50;
						sun_lab.setText("Sun: "+String.valueOf(sun_val));
						
						for (Plant p:plants)
							if (p.cost<=sun_val&&p.timer==0)
								p.setEnabled(true);
							else
								p.setEnabled(false);
					}
					public void mouseClicked(MouseEvent e) {}
					public void mouseReleased(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}		
				});
				getLayeredPane().add(sun,new Integer(Integer.MAX_VALUE));
			}
			
			for (int i=0;i<5;i++)   //生成副产品
				for (int j=0;j<9;j++)
				{
					Soil s=soil[i][j];
					if (s.timer==0)
					{
						switch(s.type)
						{
							case 0:   //向日葵
								sun=new Sun(50+s.getX(),50+s.getY(),path);
								sun.addMouseListener(new MouseListener()
								{
									public void mousePressed(MouseEvent e) {
										Sun s=(Sun)e.getSource();
										getLayeredPane().remove(s);
										repaint();
										suns.remove(s);
										sun_val+=25;
										sun_lab.setText("Sun: "+String.valueOf(sun_val));								
										for (Plant p:plants)
											if (p.cost<=sun_val&&p.timer==0)
												p.setEnabled(true);
											else
												p.setEnabled(false);
									}
									public void mouseClicked(MouseEvent e) {}
									public void mouseReleased(MouseEvent e) {}
									public void mouseEntered(MouseEvent e) {}
									public void mouseExited(MouseEvent e) {}		
								});
								suns.add(sun);
								getLayeredPane().add(sun,new Integer(Integer.MAX_VALUE));
								break;
							case 1:	  //豌豆射手
								bullet=new Bullet(1,i,50+s.getX(),50+s.getY(),path);
								bullets.add(bullet);
								getLayeredPane().add(bullet,new Integer(Integer.MAX_VALUE-1));		
								break;
							case 2:  //寒冰射手
								bullet=new Bullet(2,i,50+s.getX(),50+s.getY(),path);
								bullets.add(bullet);
								getLayeredPane().add(bullet,new Integer(Integer.MAX_VALUE-1));		
								break;
							case 6:  //双射手
								bullet=new Bullet(1,i,50+s.getX(),50+s.getY(),path);
								bullets.add(bullet);
								getLayeredPane().add(bullet,new Integer(Integer.MAX_VALUE-1));		
								bullet=new Bullet(1,i,75+s.getX(),50+s.getY(),path);
								bullets.add(bullet);
								getLayeredPane().add(bullet,new Integer(Integer.MAX_VALUE-1));	
								break;	
							case 9: //卷心菜
								bullet=new Bullet(4,i,s.getX()-5,35+s.getY(),path);
								bullets.add(bullet);
								getLayeredPane().add(bullet,new Integer(Integer.MAX_VALUE-1));		
								break;
						}
					}
					if (s.type!=-1) s.inc();
					if (s.type==5&&s.timer==0)
						s.setIcon(icon_5);
					if (s.type==7&&s.timer==0)
						s.setIcon(icon[7]);
				}
			
			for (int i=0;i<bullets.size();i++)   //判定子弹击中
			{

				Bullet b=bullets.get(i);
				b.move();
				if (b.getX()>870||b.timer==23) 
				{
					bullets.remove(i);
					i--;
					getLayeredPane().remove(b);
					repaint();
					continue;
				}
				for (int j=0;j<zombies.size();j++)
				{
					Zombie z=zombies.get(j);
					if (z.line==b.line&&b.getX()-b.getWidth()/2>=z.getX()&&b.getX()<z.getX()+z.getWidth()/2&&b.getY()>=z.getY())
					{
						z.hp-=b.atk;
						z.hurt();
						if (z.hp<=4)
						{
							z.set(icon_z[0],icon_zhurt[0],icon_boom,icon_zslow[0]);
							if (z.type==3)
							{
								z.set(icon_3,icon_zhurt[0],icon_boom,icon_zslow[0]);
								z.spd=2;
							}						
						}
										
						if (b.type==2)  //减速
							z.slow();
						bullets.remove(i);
						i--;
						getLayeredPane().remove(b);
						repaint();
						
						if (z.hp<=0)  //死亡
						{
							zombies.remove(j);
							z.boom();
							repaint();
							waste.add(z);
							kill_count++;
						}
						
						break;
					}
				}
				for (Soil s:soil[b.line])
					if(s.type==8&&b.getX()>=s.getX()+20&&b.getX()<=s.getX()+80) 
					{
						b.atk=2;
						b.setIcon(icon_fire);
					}
			}
			
			for (int i=0;i<zombies.size();i++)  //僵尸移动
			{
				Zombie z=zombies.get(i);
				if (z.eat==null)
				{
					z.move();
					if (z.getX()<=0)
					{
						brain--;
						brain_lab.setText("Brain: "+String.valueOf(brain));
						kill_count++;
						if (brain==0)
						{
							JOptionPane.showMessageDialog( null,"Ooooooops, You lost your BRAIN!!!");
							return;
						}
						zombies.remove(i);
						i--;
						getLayeredPane().remove(z);
						repaint();
					}
					for (Soil s:tree)
					{
						if (s.i==z.line)
							if(z.getX()<=s.getX()+40)
							{
								if (s.type==5&&s.timer==0)  //土豆地雷
								{
									for (int j=0;j<zombies.size();j++)
									{
										Zombie y=zombies.get(j);
										if ((y.line==s.i)&&(y.getX()>=s.getX()&&y.getX()<=s.getX()+100))
										{
											zombies.remove(j);
											y.boom();
											waste.add(y);
											kill_count++;
											j--;
										}
									}
									s.setIcon(icon_b);
									tree.remove(s);
									bad.add(s);
									s.type=-1;
									repaint();
									break;
								}
								else if (s.type==7&&s.timer==0) //食人花
								{
									zombies.remove(i);
									i--;
									z.boom();
									waste.add(z);
									kill_count++;
									s.timer=1;
									s.setIcon(icon_7);
								}
								else
								{
									z.eat=s;
									z.etimer=1;
								}
							}
							else if (z.getX()>=s.getX()+100&&z.getX()<=s.getX()+150)  //窝瓜
							{
								s.type=-1;
								s.setIcon(icon_n);
								Soil p=new Soil(s.i,s.j,s.getX()+100,s.getY()-100,11);
								p.setIcon(icon_11);
								getLayeredPane().add(p,new Integer(Integer.MAX_VALUE));
								effect.add(p);
								eff_time.add(2);
								repaint();
							}
					}
				}
				else
				{
					if (z.eat.type==-1)
						z.eat=null;
					else if (z.etimer!=0)
						z.etimer=(z.etimer+1)%20;
					else
					{
						z.eat.hp--;
						if (z.eat.hp==0)
						{
							z.eat.setIcon(icon_n);
							tree.remove(z.eat);
							z.eat.type=-1;
							z.eat=null;
							repaint();
						}
					}		
				}
				
			}
			
			for (int i=0;i<suns.size();i++)  //阳光消失
			{
				Sun s=suns.get(i);
				s.timer++;
				if (s.timer==100)
				{
					getLayeredPane().remove(s);
					suns.remove(i);
					repaint();
				}
			}
			
			if (timer%10==0)  //植物播种冷却
				for (Plant p:plants)
				{
					if (p.timer>0) p.timer--;
					p.renew();
					if (p.cost<=sun_val&&p.timer==0)
						p.setEnabled(true);
					else
						p.setEnabled(false);
				}
			
			if (timer%150==0)
				type2++;
			
			if (timer%300==0)
				type1++;
		}
	}
}
