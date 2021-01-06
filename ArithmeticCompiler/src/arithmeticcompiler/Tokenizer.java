package arithmeticcompiler;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer 
{
	private List<Token> tokenArray;

	public Tokenizer() 
	{
		tokenArray = new ArrayList<Token>();
		getExpression();
	}
	
	public List<Token> getTokenArray()
	{
		return this.tokenArray;
	}

	public void getExpression()  //获取输入的表达式
	{
		char[] expStrChars;
		String expStr = MainUI.expTextField.getText() + "#";
		expStrChars = expStr.toCharArray();
		if (expStrChars.equals("END"))
		{
			return;
		}
		if(lexAnalyze(expStrChars) != 0)
		{
			lexScan(expStrChars);
		}
	}

	public int lexAnalyze(char[] expStrChars) //词法分析，返回值为0时是不合法的表达式
	{
		int count = 0; // 检查括号是否匹配
		int sign = 1; // 用于标志种别 0--数 1--+-*/^
		for (int i = 0; expStrChars[i] != '#'; i++)
		{
			if (expStrChars[i] >= '0' && expStrChars[i] <= '9')
			{
				sign = 0;
			}
			else if (expStrChars[i] == '.' && sign == 0)
			{
				i++;
				if (!(expStrChars[i] >= '0' && expStrChars[i] <= '9'))
				{
					MainUI.resultTextField.setText("No figures after the decimal point at column " + String.valueOf(i+1));
					return 0;
				}
				while (expStrChars[i] >= '0' && expStrChars[i] <= '9')
				{
					i++;
				}
				if (expStrChars[i] == '.')
				{
					MainUI.resultTextField.setText("Decimal point error at column " + String.valueOf(i+1));
					return 0;
				}
				i--;
			}
			else if (expStrChars[i] == '+' || expStrChars[i] == '-' || expStrChars[i] == '*' || expStrChars[i] == '/' || expStrChars[i] == '^' || expStrChars[i] == '#')
			{
				if (sign == 1)
				{ // 连续输入运算符，表达式错误
					MainUI.resultTextField.setText("Operator error at column " + String.valueOf(i+1));
					return 0;
				}
				else
				{
					sign = 1;
				}
			}
			else if (expStrChars[i] == '(' && sign == 1) // 统计左括号,左括号前面为运算符
			{
				count++;
			}
			else if (expStrChars[i] == ')') // 统计右括号，左括号前面为运算数
			{
				if (sign != 0)
				{
					MainUI.resultTextField.setText("No operands in front of symbol ')' at column " + String.valueOf(i+1));
					return 0;
				}
				if (count == 0)
				{
					MainUI.resultTextField.setText("Symbol ')' first appeared at column " + String.valueOf(i+1));
					return 0;
				}
				count--;
			}
			else
			{
				MainUI.resultTextField.setText("Invalide symbol at column " + String.valueOf(i+1));
				return 0; // 非法字符
			}
		}
		if (count != 0)
		{
			MainUI.resultTextField.setText("Bracket do not match");
			return 0;
		}
		if (expStrChars[expStrChars.length - 1] == '#') // 防止最后输入的不是等号
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	public int lexScan(char[] expStrChars) //扫描成token串
	{
		tokenArray = new ArrayList<Token>();
		int n = 0; // p指示OPND的栈顶元素，n判断是否连续取得数字
		int no = 0;
		char c = expStrChars[no];
		int number = 0;
		while (c != '#') // c为‘#’时结束
		{
			number = 0;
			if (c >= '0' && c <= '9') // c为数字
			{
				if (n == 0) // n为0表示c是新数，进栈
				{
					n++;
					number = c - 48; // 将该操作数的值压入数栈（0在ASCII代码中排48）
					Token t = new Token(number, 0);
					tokenArray.add(t);
					no++;
					c = expStrChars[no];
				}
				else
				{
					tokenArray.get(tokenArray.size() - 1).setdata(c - 48);
					no++;
					c = expStrChars[no];
				}
			}
			else if (c == '.')
			{
				no++;
				c = expStrChars[no];
				if (!(c >= '0' && c <= '9'))
				{
					return 0;
				}
				double m = 1;
				while (c >= '0' && c <= '9')
				{
					m = m / 10;
					tokenArray.get(tokenArray.size() - 1).setdata1(c - 48, m);
					no++;
					c = expStrChars[no];
				}
			}
			else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')' || c == '^' || c == '#') //为运算符(+,-,*,/,(,),#)
			{
				int pro = 0;
				switch (c)
				{
				case '+':
					pro = 1;
					break;
				case '-':
					pro = 2;
					break;
				case '*':
					pro = 3;
					break;
				case '/':
					pro = 4;
					break;
				case '^':
					pro = 5;
					break;
				case '(':
					pro = 6;
					break;
				case ')':
					pro = 7;
					break;
				case '#':
					pro = 8;
					break;
				}
				n = 0; // c不是数，是运算符
				Token t = new Token(c, pro);
				tokenArray.add(t);
				no++;
				c = expStrChars[no];
			}
		}
		Token t = new Token('#', 8);
		tokenArray.add(t);
		displayToken();
		return 0;
	}

	public void displayToken() //显示Token
	{
		String tokenStr = "Input exp:\t\t";
		for (int i = 0; i < tokenArray.size(); i++)
		{
			tokenStr = tokenStr + tokenArray.get(i).getOpr();
		}
		tokenStr += "\n\n";
		tokenStr += "token\t\tid\n";
		for (int i=0;i<tokenArray.size();i++)
		{
			if(tokenArray.get(i).getPro() == 0)
			{
				tokenStr = tokenStr + tokenArray.get(i).getOpd() + "\t\t" + tokenArray.get(i).getPro() + "\n";
			}
			else
			{
				tokenStr = tokenStr + tokenArray.get(i).getOpr() + "\t\t" + tokenArray.get(i).getPro() + "\n";
			}
		}
		MainUI.tokenizerTextArea.setText(tokenStr);
	}

}
