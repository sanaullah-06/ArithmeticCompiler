package arithmeticcompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Semantic
{
	private static int j = 1; //i的的标志位
	private static int t = 1; //T的标志位
	private int tag; //使用情况，为1则使用过，为0则未被使用
	private  List<SemanticTableItem>  table = null;//字符暂存表	
	private  List<SemanticTableItem>  Sitable = null;//四元式序列	
	
	public Semantic()
	{
		this.tag = 0;
		table = new ArrayList<SemanticTableItem>();
		Sitable = new ArrayList<SemanticTableItem>();
	}
	
	public void generateFourElementExp(String a,String b)
	{  //生成四元式
		int len = 0;
		Scanner s1 = new Scanner(b);
		Scanner s2 = new Scanner(b);
		while(s1.hasNext() == true)
		{ //计算长度
			s1.next();
			len++;
		}
		ArrayList <String> sr = new ArrayList<String>();
		for(int i=0;i<len;i++)
		{  //调整四元式的格式
			if(s2.hasNext() == true)
			{
				sr.add(s2.next());
			}
			else
			{
				sr.add("_");
			}
		}
		ArrayList <String> arr = new ArrayList<String>();
		if(len == 1)
		{      //更新暂存表
			a = "i"+j;
			SemanticTableItem V1 = new SemanticTableItem(a,tag);
			table.add(V1);
			j++;
		}
		else
		{
			if(!sr.get(0).equals("("))
			{
				for(int i=table.size()-1,m=0;i>=0 && m<2;i--)
				{
					if(table.get(i).getTag() == 0)
					{
						arr.add(table.get(i).getResult());
						m++;
						table.get(i).setTag(1);
					}
				}
				a = "T"+t;
				SemanticTableItem V2 = new SemanticTableItem(a,tag);
				table.add(V2);
				t++;
				if(arr.size() == 2)
				{
					SemanticTableItem Si = new SemanticTableItem(sr.get(1),arr.get(0),arr.get(1),a);
					Sitable.add(Si);
				}
			}
		}
		s1.close();
		s2.close();
	}
	
	public void displayFourElementExp()
	{//输出四元式
		String fourElementExp = ""; 
		for(int i=0;i<Sitable.size();i++)
		{
			fourElementExp += "("+Sitable.get(i).getA0() + "," + Sitable.get(i).getA2() + ","+Sitable.get(i).getA1() + "," + Sitable.get(i).getA3() + ")" + "\n";
		}
		MainUI.fpeTextArea.setText(fourElementExp);
	}
	
}
