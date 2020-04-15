package bsuir.vmsis.kpp.thread.controlcenter;

import java.util.concurrent.locks.*;

import bsuir.vmsis.kpp.intreface.Interface;
import bsuir.vmsis.kpp.thread.MainThread;
import bsuir.vmsis.kpp.thread.plane.Plane;

public class ControlCenter
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
	public int RequestLine(Interface inter)
	{
		inter.SetLabel("Проверка на свободных полос",2);
		try
		{
			Thread.sleep(1000);
		}
		catch(InterruptedException ex )
		{
			Thread.currentThread().interrupt();
		}
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
					inter.SetLabel("На данный момент все полосы заняты.Ожидайте освобождения полосы.",3);
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
	
	public void LeaveTheAirport(Plane plane,Interface inter)
	{
		controlCenterLock.lock();
		runway[plane.getLine()-1]=false;
		cond.signal();
		controlCenterLock.unlock();
		inter.SetLabel("Полоса номер "+plane.getLine()+" освобождена",6);
	}
	
	public void TakeToRunway(int line)
	{
		runway[line-1]=true;
	}
}