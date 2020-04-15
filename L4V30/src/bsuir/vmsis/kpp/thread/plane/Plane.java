package bsuir.vmsis.kpp.thread.plane;

import bsuir.vmsis.kpp.intreface.Interface;
import bsuir.vmsis.kpp.thread.MainThread;
import bsuir.vmsis.kpp.thread.controlcenter.ControlCenter;

public class Plane
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
	public void Land (ControlCenter conCenter,MainThread mThread,Interface inter)
	{
		try
		{
			mTObject=mThread;
			this.line=conCenter.RequestLine(inter);
			inter.SetLabel("Вашему самолёту назначена полоса номер "+this.line,3);
			Thread.sleep(1000);
			this.LandedAtTheAirport(conCenter,inter);
		}
		catch(InterruptedException ex )
		{
			Thread.currentThread().interrupt();
		}
		
	}
	public void LandedAtTheAirport(ControlCenter conCenter,Interface inter)
	{
		try
		{    
			inter.SetLabel("Самолёт посажен на полосу",4);
			Thread.sleep(time*1000);
			inter.SetLabel("Взлет самолета",5);
			Thread.sleep(1000);
			conCenter.LeaveTheAirport(this,inter);
		}
		catch(InterruptedException ex )
		{
			Thread.currentThread().interrupt();
		}
		
	}
}
