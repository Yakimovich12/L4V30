package bsuir.vmsis.kpp;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.locks.*;

import javax.swing.*;

public class Source 
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(()->
		{
			
			JFrame jframe1= new JFrame("Lab4Var30");
			Toolkit kit=Toolkit.getDefaultToolkit();
			Dimension screensize=kit.getScreenSize();
			jframe1.setBounds(screensize.width/2-150,screensize.height/2-100,300,200);
			jframe1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jframe1.setVisible(true);
			jframe1.setLayout(null);
			jframe1.setResizable(false);
			
			JLabel jl1=new JLabel("Выберите операцию:");
			jl1.setBounds(85,10,130,20);
			jframe1.add(jl1);
			
			JButton jb1=new JButton("Добавить самолёт");
			jb1.setBounds(75,40,150,30);
			jframe1.add(jb1);
			jb1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) 
				{
					
			        MainThread.CreatePlane(); 
			        
				}});
		});
	}
	public static int GenerateTime()
	{
		int time=(int) (Math.random()*(100-10))+10;
		return time;
	}
}

class ControlCenter
{
	private Lock controlCenterLock;
	private Condition cond;
	private boolean[] runway;
	
	
	
	public ControlCenter()
	{
		this.runway=new boolean[5];
		for(int i=0;i<5;i++)
			this.runway[i]=false;
		
		controlCenterLock = new ReentrantLock();
		cond = controlCenterLock.newCondition();
	}
	
	public int RequestLine()
	{
		controlCenterLock.lock();
		
		int line=0;
		for(int i=0;i<5;i++)
		{
			if(runway[i]==false)
			{
				line=i;
				runway[i]=true;
				cond.signal();
				break;
			}
		}
		controlCenterLock.unlock();
		return line;
	}
	
	
	public void TakeToRunway()
	{
		
	}
}

class Plane
{
	private String name;
	private int time;
	
	public Plane()
	{
		name="";
		time=0;
	}
	public void setName(String name){this.name=name;}
	public String getName() {return this.name;}
	public void setTime(int time) {this.time=time;}
	public int getTime() {return this.time;}
	public void land()
	{
		
	}
}

class MainThread
{
	private Plane planeObject;
	private ControlCenter controlCenterObject;
	
	public MainThread()
	{
		controlCenterObject=new ControlCenter();
	}
	
	public static void CreatePlane()
	{
		
		Plane planeObj=new Plane();
		planeObj.setTime(Source.GenerateTime());
		
		Runnable r=()->
		{
			
			int line=0;
			do
			{
				line=controlCenterObject.RequestLine();
				if(line==0)
					controlCenterObject.cond.await();
			}while(line==0);
			
			
		};
		Thread thread = new Thread(r);
		thread.start();
		
	}
	
	
}
