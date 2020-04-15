package bsuir.vmsis.kpp.thread;

import bsuir.vmsis.kpp.Source;
import bsuir.vmsis.kpp.intreface.Interface;
import bsuir.vmsis.kpp.thread.controlcenter.ControlCenter;
import bsuir.vmsis.kpp.thread.plane.Plane;

public class MainThread
{
	public MainThread() {}
	public void CreatePlane(ControlCenter conCenter)
	{
		try
		{
			MainThread obj=this;
			Interface inter=new Interface();
			Plane planeObj=new Plane();
			planeObj.setTime(Source.GenerateTime());
			inter.SetLabel("Вам назначается время: "+planeObj.getTime()+" секунд",0);
			Thread.sleep(1000);
			inter.SetLabel("Обрабатываем запрос на  поcадку",1);
			Thread.sleep(1000);
			planeObj.Land(conCenter,obj,inter);
			inter.Exit();
		}
		catch (InterruptedException ex )
		{
			Thread.currentThread().interrupt();
		}
	}	
}