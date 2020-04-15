package bsuir.vmsis.kpp.intreface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Interface
{
	private JFrame jframe;
	private JLabel[] mas=new JLabel[7];
	private JButton jb;
	
	public Interface()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
	public void run()
	{
	    jframe= new JFrame("Новый самолет");
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension screensize=kit.getScreenSize();
		jframe.setBounds(screensize.width/2-175,screensize.height/2-125,350,250);
		jframe.setVisible(true);
		jframe.setLayout(null);
		jframe.setResizable(false);
	    for(int i=0;i<7;i++)
	    {
	    	mas[i]=new JLabel("");
	    	mas[i].setBounds(10,10+i*30,330,20);
	    	jframe.add(mas[i]);
	    }
	    jb = new JButton("Выход");
	    jb.setBounds(220,180,100,30);
	    jframe.add(jb);
	    jb.setVisible(false);
	}
		});
	}
	public void SetLabel(String str,int i)
	{
		EventQueue.invokeLater(new Runnable()
		{
	         public void run()
	         {
	        	 mas[i].setText(str);
	         }
	    });
	}
	public void Exit()
	{
		EventQueue.invokeLater(new Runnable()
		{
	         public void run()
	         {
	        	 jb.setVisible(true);
	        	 jb.addActionListener(new ActionListener() {
	 				public void actionPerformed(ActionEvent a) 
	 				{
	 					jframe.setVisible(false);
	 				}}); 
	         }
	    });
	}
}