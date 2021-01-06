package arithmeticcompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculate
{
	private  List<SemanticTableItem>  table = null;//数据栈
	private List<String> Re = null;
	private static int count = 0;
	
	public Calculate()
	{
		table=new ArrayList<SemanticTableItem>();
		Re=new ArrayList<String>();	
	}
	
	public void calculate(String a,String b)
	{  
		int len=0;
		Scanner s1=new Scanner(b);
		Scanner s2=new Scanner(b);
		while(s1.hasNext()==true)
		{ //计算长度
			s1.next();
			len++;
		}
		ArrayList <String> arr=new ArrayList<String>();
		for(int i=0;i<len;i++)
		{  //调整四元式的格式
			if(s2.hasNext()==true)
			{
				arr.add(s2.next());
			}
			else
			{
				arr.add("_");
			}
		}
		
		if(len==1)
		{
			SemanticTableItem V=new SemanticTableItem(count,a,b);
			this.table.add(V);
		}
		else
		{
			if(!arr.get(0).equals("("))
			{
				if(Re.size()==0)
				{
					double n2=table.get(table.size()-1).getVal();
					table.remove(table.size()-1);
					double n1=table.get(table.size()-1).getVal();
					table.remove(table.size()-1);
					operation(a,n1,arr.get(1),n2);
				}
				
			}
		}
		s1.close();
		s2.close();
	}
	
	public String check(int no,String b)
	{  //查字符对应的值
		Double r = null;
		for(int i=0;i<table.size();i++)
		{
			if(table.get(i).getNo()==no && table.get(i).getResult().equals(b))
			{
				r=table.get(i).getVal();
			}
		}
		System.out.println(b+"的值是："+r);
		return Double.toString(r);
	}

	public static boolean isNum(String str)
	{  //判断是否为数字
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	public void operation(String a,double x,String op,double y)
	{
		if(op.equals("+")) //+
		{
			x=x+y;
		}
		else if(op.equals("-")) //-
		{
			x=x-y;
		}
		else if(op.equals("*")) //*
		{
			x=x*y;
		}
		else if(op.equals("/")) // /
		{
			if(y==0.0)
			{
				Re.add("ERROR!");
			}
			else
			{
				x=x/y;
			}
		}
		else
		{
			double sum=Math.pow(x, y);
			x=sum;
		}
		if(y!=0.0)
		{
			SemanticTableItem V=new SemanticTableItem(count,a,Double.toString(x));
			this.table.add(V);
			count++;
		}
	}
	
	public void displayStack()
	{  //输出数据栈
		String result = new String();
		if(Re.size()==0)
		{
			result += Double.toString(table.get(table.size()-1).getVal());
			MainUI.resultTextField.setText(result);
		}
		else
		{
			MainUI.resultTextField.setText("Div 0 is not allowed!");
		}
	}
}
