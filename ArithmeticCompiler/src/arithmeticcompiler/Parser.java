package arithmeticcompiler;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser
{
	private List<Token> token_list;
	private int [][]pri_table;
	private int top;
	private int tail;
	private int count=0;
	private double tempval;
	private List<Node> m_stack;
	private List<Node> all_node;
	private String next;
	private GenerateExps g; //产生式链表
	private Calculate op;
	private Semantic si;
	private BufferedWriter bwriter;
	private int tree_step;
	private String proString = new String();
	private String syntaxTreeDisplayStr = new String();
	
	public Parser(List<Token> t) throws FileNotFoundException
	{
		token_list = new ArrayList<Token>();//收到的token串
		token_list = t;
		m_stack = new ArrayList<Node>();
		g = new GenerateExps();//产生式
		all_node = new ArrayList<Node>();
		op = new Calculate();
		si = new Semantic();
		bwriter = new BufferedWriter(new  OutputStreamWriter(new FileOutputStream("SyntaxTree.txt")));    //树文件名

		/* 初始化算符优先表 */
		pri_table = new int[9][9];
		pri_table[0][0] = 1;
		pri_table[0][1] = 1;
		pri_table[0][2] = -1;
		pri_table[0][3] = -1;
		pri_table[0][4] = -1;
		pri_table[0][5] = -1;
		pri_table[0][6] = 1;
		pri_table[0][7] = -1;
		pri_table[0][8] = 1;
		
		pri_table[1][0] = 1;
		pri_table[1][1] = 1;
		pri_table[1][2] = -1;
		pri_table[1][3] = -1;
		pri_table[1][4] = -1;
		pri_table[1][5] = -1;
		pri_table[1][6] = 1;
		pri_table[1][7] = -1;
		pri_table[1][8] = 1;
		
		pri_table[2][0] = 1;
		pri_table[2][1] = 1;
		pri_table[2][2] =1 ;
		pri_table[2][3] = 1;
		pri_table[2][4] = -1;
		pri_table[2][5] = -1;
		pri_table[2][6] = 1;
		pri_table[2][7] = -1;
		pri_table[2][8] = 1;
		
		pri_table[3][0] = 1;
		pri_table[3][1] = 1;
		pri_table[3][2] = 1;
		pri_table[3][3] = 1;
		pri_table[3][4] = -1;
		pri_table[3][5] = -1;
		pri_table[3][6] = 1;
		pri_table[3][7] = -1;
		pri_table[3][8] = 1;
		
		pri_table[4][0] = 1;
		pri_table[4][1] = 1;
		pri_table[4][2] = 1;
		pri_table[4][3] = 1;
		pri_table[4][4] = -1;
		pri_table[4][5] = -1;
		pri_table[4][6] = 1;
		pri_table[4][7] = -1;
		pri_table[4][8] = 1;
		
		pri_table[5][0] = -1;
		pri_table[5][1] = -1;
		pri_table[5][2] = -1;
		pri_table[5][3] = -1;
		pri_table[5][4] = -1;
		pri_table[5][5] = -1;
		pri_table[5][6] = 0;
		pri_table[5][7] = -1;
		pri_table[5][8] = 1;
		
		pri_table[6][0] = 1;
		pri_table[6][1] = 1;
		pri_table[6][2] = 1;
		pri_table[6][3] = 1;
		pri_table[6][4] = 1;
		pri_table[6][5] = 99;
		pri_table[6][6] = 1;
		pri_table[6][7] = 99;
		pri_table[6][8] = 1;
		
		pri_table[7][0] = 1;
		pri_table[7][1] = 1;
		pri_table[7][2] = 1;
		pri_table[7][3] = 1;
		pri_table[7][4] = 1;
		pri_table[7][5] = 99;
		pri_table[7][6] = 1;
		pri_table[7][7] = 99;
		pri_table[7][8] = 1;
		
		pri_table[8][0] = -1;
		pri_table[8][1] = -1;
		pri_table[8][2] = -1;
		pri_table[8][3] = -1;
		pri_table[8][4] = -1;
		pri_table[8][5] = -1;
		pri_table[8][6] = -1;
		pri_table[8][7] = -1;
		pri_table[8][8] = -1;
	}
	
	public int getTokenId(String symbol)
	{//得到在矩阵中的行列号
		if(symbol.equals("+"))
		{
			return 0;
		}
		else if(symbol.equals("-"))
		{
			return 1;
		}
		else if(symbol.equals("*"))
		{
			return 2;
		}
		else if(symbol.equals("/"))
		{
			return 3;
		}
		else if(symbol.equals("^"))
		{
			return 4;
		}
		else if(symbol.equals("("))
		{
			return 5;
		}
		else if(symbol.equals(")"))
		{
			return 6;
		}
		else if(symbol.equals("i"))
		{
			return 7;
		}
		else if(symbol.equals("#"))
		{
			return 8;
		}
		return 0;
	}
	
	private int getPriority(String in_stack,String next)
	{
		int a=getTokenId(in_stack);
		int b=getTokenId(next);
		return pri_table[a][b];
	}
	
	public void parse() throws IOException //语法分析
	{
		Node t = new Node(count++);//0号
		all_node.add(t);
		t.value = new String("#");
		m_stack.add(t);
		next = new String(" ");
		top = 0;
		int i = 0;
		proString = "当前输入\t" + "优先关系 \t" + "动作\t" + "栈\t" + "产生式\t" + "语法树\n";
		do
		{
			i=recogTail(i);
			recogHead();
		}
		while(!(next.equals("#") && top==1));
		MainUI.parserTextArea.setText(proString);
		op.displayStack(); //显示分析栈状态
		si.displayFourElementExp(); //显示四元式
		displayTree(); //显示语法树
		bwriter.flush(); 
	    bwriter.close();
	}
	
	public int recogTail(int i)
	{
		for(;i<token_list.size();i++)
		{
			int style = token_list.get(i).getPro();
			if(style == 0)
			{	tempval = token_list.get(i).getOpd();
				next = "i" ;
			}
			else
			{
				next = token_list.get(i).getOpr() + "";
			}	
			String s_tail = (String) m_stack.get(top).value;
			if(s_tail.equals("E") || s_tail.equals("T") ||s_tail.equals("F") ||s_tail.equals("P"))
			{
				tail = top - 1;//栈顶为非终则，取第二个
			}
			else
			{
				tail = top;
			}
			s_tail = (String) m_stack.get(tail).value;
			if(getPriority(s_tail,next)<=0)
			{	
				Node t = new Node(count++);//从1开始
				all_node.add(t);
				t.value = new String(next);
				m_stack.add(t);//压栈
				top++;
				proString += next+'\t';  //当前输出；如果是数值，会变成i输出
				proString += s_tail + "<" + next + '\t';  //比较优先级
				proString += "移进" + next + "\t";  
				for(int j=m_stack.size()-1;j>=0;j--)//输出栈
				{
					proString += m_stack.get(j).value;
				}
				proString += "\n";
				//输出,休眠
			}
			else //非移进
			{
				return i;
			}
		}
		return 0;
	}

	private void recogHead() throws IOException
	{
		int low = tail;
		int high = tail;
		String s_high;
		String s_low;
		do
		{
			high=low;
			s_high= new String(m_stack.get(high).value);	
			String temp=new String(m_stack.get(low-1).value);
			if(temp.equals("E") || temp.equals("T") ||temp.equals("F") || temp.equals("P"))
			{
				low -= 2;
			}
			else
			{
				low--;
			}
			s_low = new String(m_stack.get(low).value);
		}
		while(getPriority(s_low,s_high)>=0);
		StringBuffer lpp = new StringBuffer();//最左素短语
		StringBuffer lpp1 = new StringBuffer();//最左素短语
		List<Node> lpp_node = new ArrayList<Node>();//最左素短语
		for(int i=low+1;i<=top;i++)
		{
			lpp_node.add(m_stack.get(i)); //将lpp中的多个节点串起来
			lpp.append(m_stack.get(i).value);
			lpp1.append(m_stack.get(i).value);
			lpp.append(" ");
		}
		for(int i=top;i>=low+1;i--)
		{//pop lpp
			m_stack.remove(i);
			top--;
		}
		int n= match(lpp.toString());//匹配并返回第n条产生式，从0开始编号
		if(n==99)
		{
			MainUI.resultTextField.setText("Cannot statute!");
		}
		else
		{//进行归约，要先建一个父节点
			Node fa = new Node(count++);
			all_node.add(fa);   //父节点加入总节点链表中
			fa.value = new String(g.getG().get(n).left);//父节点的值
			fa.has_child = true;
			for(int i=0;i<lpp_node.size();i++)
			{ //父节点的儿子
				fa.child[i] = lpp_node.get(i).id;
				lpp_node.get(i).father = fa.id;
			}
			m_stack.add(fa); //压入归约后的
			top++;
			//输出
			proString += next + '\t';  //当前输出；如果是数值，会变成i输出？？？？？？？？？
			proString += s_low + "<" + s_high + ">" + next + '\t';  //比较优先级???????????????
			proString += "归约" + lpp1 + "\t";  //输出栈
			for(int j=m_stack.size()-1;j>=0;j--)
			{
				proString += m_stack.get(j).value;
			}
			proString += "\t";
			proString += g.getG().get(n).left + "->" + g.getG().get(n).right + "\t";//产生式

			//输出语法子树
			tree_step++;
			proString += "语法子树" + tree_step;
			generateTree(fa,n);
			
			//语法制导
			if(lpp1.toString().length()==1)
			{
				si.generateFourElementExp(g.getG().get(n).left,Double.toString(tempval));
			}
			else
			{
				si.generateFourElementExp(g.getG().get(n).left,lpp.toString());
			}
			if(lpp1.toString().length()==1)
			{
				op.calculate(g.getG().get(n).left,Double.toString(tempval));
			}
			else
			{
				op.calculate(g.getG().get(n).left,lpp.toString());
			}
			proString+="\n";
		}
	}

	private void generateTree(Node fa,int n) throws IOException //生成语法树
	{
		List<Node> syntaxTree = new ArrayList<Node>();
		for(int i=1;i<m_stack.size();i++)
		{
			syntaxTree.add(m_stack.get(i));
			m_stack.get(i).level = 0;   //顶层为0，后面每一层都是作为孩子时被赋值
		}
		for(int i=0;i<syntaxTree.size();i++)
		{//BSF，并存放在temp
			Node t = syntaxTree.get(i);	//all_list中的位置标号与每个node的id相同
			if(t.has_child == true)
			{
				for(int j=0;j<t.child.length;j++)
				{
					if(t.child[j] != 0)
					{	
						all_node.get(t.child[j]).level = t.level+1;//设孩子的层
						syntaxTree.add(all_node.get(t.child[j]));//压入孩子
					}
				}
			}
		}
		treeToStr(syntaxTree);
	}
	
	private void treeToStr(List<Node> syntaxTree) throws IOException //语法树转换成字符串，方便显示
	{
		int last_level = 0;
		int fa_id = 0;
		int first = 0;
		int num = 0;
		boolean l = false;
		syntaxTreeDisplayStr = syntaxTreeDisplayStr + "语法子树" + tree_step + "\r\n";
		for(int j=0;j<5;j++)
		{
			syntaxTreeDisplayStr = syntaxTreeDisplayStr + "\t";
		}
		for(int i=0;i<syntaxTree.size();i++)
		{
			if(syntaxTree.get(i).level != last_level)//需换行时
			{	
				last_level = syntaxTree.get(i).level;
				if(l == true)
				{
					syntaxTreeDisplayStr = syntaxTreeDisplayStr + "]\r\n";
				}
				else
				{
					syntaxTreeDisplayStr = syntaxTreeDisplayStr + "\r\n";//层次值不同则换行，然后再输出当前节点值，节点间隔\t
				}
				fa_id = syntaxTree.get(i).father;
				for(int j=0;j<5-syntaxTree.get(i).level;j++)
				{
					syntaxTreeDisplayStr = syntaxTreeDisplayStr + "\t";
				}
				while(syntaxTree.get(first).value.equals("i"))
				{	
					num++;
					first++;
				}
				for(int j=num*3;j>0;j--)
				{
					syntaxTreeDisplayStr = syntaxTreeDisplayStr + "\t";//换行后，根据num决定输出\t的个数
				}
				syntaxTreeDisplayStr = syntaxTreeDisplayStr + "[ ";
				l = true;
				first = i;//记录新行的第一个
			}
			if(fa_id != syntaxTree.get(i).father)
			{
				syntaxTreeDisplayStr = syntaxTreeDisplayStr + "]\t" + "[ ";
				fa_id = syntaxTree.get(i).father;
			}
			syntaxTreeDisplayStr = syntaxTreeDisplayStr + syntaxTree.get(i).value + " ";
		}
		if(l == true)
		{
			syntaxTreeDisplayStr = syntaxTreeDisplayStr + "]\r\n\r\n";
		}
		else
		{
			syntaxTreeDisplayStr = syntaxTreeDisplayStr + "\r\n\r\n";
		}
	}
	
	private void displayTree() throws IOException //显示语法树
	{
		MainUI.syntaxTreeTextArea.setText(syntaxTreeDisplayStr);
		bwriter.write(syntaxTreeDisplayStr);
	}

	private int match(String lpp)
	{
		Scanner s1 = new Scanner(lpp);
		String op_lpp = new String(s1.next());
		if(op_lpp.equals("("))
		{
			s1.close();
			return 9;
		}
		else if(op_lpp.equals("i"))
		{
			s1.close();
			return 8;
		}
		else
		{
			op_lpp = new String(s1.next());
		}
		
		for(int i=0;i<g.getG().size();i++)
		{	
			if(op_lpp.equals(g.getG().get(i).op))
			{
				s1.close();
				return i;
			}
		}
		s1.close();
		return 99;
	}

}
