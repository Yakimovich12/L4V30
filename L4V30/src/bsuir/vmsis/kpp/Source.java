package bsuir.vmsis.kpp;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.locks.*;

import javax.swing.*;

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
			jb1.setBounds(75,40,150,30);
			jframe1.add(jb1);
			jb1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) 
				{
					System.out.println("Создаем самолет");
			        mThread.CreatePlane(conCenter); 
			        
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
	private MainThread mTObject;

	public ControlCenter()
	{
		this.runway=new boolean[5];
		for(int i=0;i<5;i++)
			this.runway[i]=false;
		
		controlCenterLock = new ReentrantLock();
		cond = controlCenterLock.newCondition();
	}
	
	@SuppressWarnings("finally")
	public int RequestLine()
	{
		System.out.println("Проверка на свободные полосы");
		int line=0;
		controlCenterLock.lock();
		try
		{
			do
			{
				for(int i=0;i<5;i++)
				{
					if(runway[i]==false)
					{
						line=i+1;
						break;
					}
				}
				if(line==0)
				{
					System.out.println("На данный момент все полосы заняты.Ожидайте освобождения полосы.");
					/*JLabel jl1 = new JLabel("На данный момент все полосы заняты.Ожидайте освобождения полосы.");
					jl1.setBounds(10,100,280,20);
					jframe2.add(jl1);*/
					cond.await();
				}
			}while(line==0);
			
			this.TakeToRunway(line);
		}
		finally
		{
			controlCenterLock.unlock();
			return line;
		}
				
	}
	
	public void LeaveTheAirport(Plane plane)
	{
		controlCenterLock.lock();
		runway[plane.getLine()-1]=false;
		/*JLabel jl1 = new JLabel("Полоса номер "+plane.getLine()+" освобождена");
		jl1.setBounds(10,220,280,20);
		jframe2.add(jl1);*/
		cond.signal();
		controlCenterLock.unlock();
		System.out.println("Полоса номер "+plane.getLine()+" освобождена");
		//cond.signal();
	}
	
	public void TakeToRunway(int line)
	{
		runway[line-1]=true;
	}
}

class Plane
{
	private String name;
	private int time;
	private MainThread mTObject;
	private int line;
	
	public Plane()
	{
		name="";
		time=0;
		line=0;
	}
	public void setName(String name){this.name=name;}
	public String getName() {return this.name;}
	public void setTime(int time) {this.time=time;}
	public int getTime() {return this.time;}
	public int getLine() {return this.line;}
	public void Land (ControlCenter conCenter,MainThread mThread)
	{
		mTObject=mThread;
		this.line=conCenter.RequestLine();
		/*JLabel jl3 = new JLabel("Вашему самолёту назначена полоса номер "+this.line); 
		jl3.setBounds(10,130,280,20);
		jframe2.add(jl3);*/
		System.out.println("Вашему самолёту назначена полоса номер "+this.line);
		this.LandedAtTheAirport(conCenter);
		
	}
	public void LandedAtTheAirport(ControlCenter conCenter)
	{
		try
		{
			System.out.println("Самолёт посажен на полосу");
			/*JLabel jl4 = new JLabel("Самолет посажен на полосу");
			jl4.setBounds(10,160,280,20);
			jframe2.add(jl4);*/
			
			Thread.sleep(time*1000);
			
			/*JLabel jl5 = new JLabel("Взлет самолета");
			jl5.setBounds(10,190,280,20);
			jframe2.add(jl5);*/
			System.out.println("Взлет самолета");
			conCenter.LeaveTheAirport(this);
		}
		catch(InterruptedException ex )
		{
			Thread.currentThread().interrupt();
		}
		
	}
}

class MainThread
{
	public MainThread() {}
	public void CreatePlane(ControlCenter conCenter)
	{
		Runnable r=()->
		{
			//EventQueue.invokeLater(()->
			//{
				/*JFrame jframe2= new JFrame("Новый самолет");
				Toolkit kit=Toolkit.getDefaultToolkit();
				Dimension screensize=kit.getScreenSize();
				jframe2.setBounds(screensize.width/2-150,screensize.height/2-100,300,200);
				jframe2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jframe2.setVisible(true);
				jframe2.setLayout(null);
				jframe2.setResizable(false);*/
				
				Plane planeObj=new Plane();
				planeObj.setTime(Source.GenerateTime());
				System.out.println("Вам назначается время: "+planeObj.getTime()+" секунд");
				/*JLabel jl1= new JLabel("Вам назначается время: "+planeObj.getTime()+" секунд");
				jl1.setBounds(10,10,280,20);
				jframe2.add(jl1);
				
				JLabel jl2= new JLabel("Запрашиваю полосу");
				jl2.setBounds(10,40,280,20);
				jframe2.add(jl2);*/
				System.out.println("Запрашиваю поcадку");
				planeObj.Land(conCenter,this);
			
			
			
			
		};
		Thread thread = new Thread(r);
		thread.start();
		
	}
		
}
