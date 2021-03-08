import java.util.Scanner;

import javax.swing.JFrame;

public class Game {
	public static void main(String[] args) throws Exception
	{	
		String path="D:\\git-work\\pvz\\";
		if (args.length>0) 
		{
			path=args[0]+"\\";
			System.out.print(args[0]);
		}
		Option option=new Option(path);
		option.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		option.setVisible(true);
		option.setExtendedState(0);
		option.toFront();
		option.setResizable(false);
		
		int[]select=null;
		while (select==null)
		{
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {}
			select=option.ret();
		}

		option.dispose();
		
		Frame frame=new Frame(select,path);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setExtendedState(0);
		frame.toFront();
		frame.setResizable(false);
	//	frame.setAlwaysOnTop(true);
		
		frame.start();
		
	}
}
