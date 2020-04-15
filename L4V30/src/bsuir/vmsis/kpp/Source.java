package bsuir.vmsis.kpp;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.locks.*;

import javax.swing.*;

import bsuir.vmsis.kpp.thread.MainThread;
import bsuir.vmsis.kpp.thread.controlcenter.ControlCenter;

public class Source 
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(()->
		{
			ControlCenter conCenter=new ControlCenter();
			MainThread mThread=new MainThread();
			JFrame jframe1= new JFrame("Lab4Var30");
			Toolkit kit=Toolkit.getDefaultToolkit();
			Dimension screensize=kit.getScreenSize();
			jframe1.setBounds(screensize.width/2-150,screensize.height/2-100,300,250);
			jframe1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jframe1.setVisible(true);
			jframe1.setLayout(null);
			jframe1.setResizable(false);
			
			JLabel jl1=new JLabel("Выберите операцию:");
			jl1.setBounds(85,10,130,20);
			jframe1.add(jl1);
			
			JButton jb1=new JButton("Добавить самолёт");
			jb1.setBounds(75,90,150,30);
			jframe1.add(jb1);
			jb1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) 
				{
					Runnable r=()->
					{
			             mThread.CreatePlane(conCenter); 
				    };
			        Thread thread = new Thread(r);
					thread.start();
			        
				}});
		});
	}
	public static int GenerateTime()
	{
		int time=(int) (Math.random()*(100-10))+10;
		return time;
	}
}





