package arithmeticcompiler;

public class Token 
{
	private double opd;		//数
	private char opr;
	private int pro;	
	
	public Token(int d,int p) 
	{
		opd = (double)d;
		pro = p;
		opr = 'i';
	}
	public Token(char r,int p) 
	{
		opr = r;
		pro = p;
	}
	public void setdata(int i)
	{
		opd = opd*10+i;
	}
	public void setdata1(int i,double m)
	{
		opd = (double)(opd+i*m);
	}
	public double getOpd() 
	{
		return opd;
	}
	public void setOpd(double opd) 
	{
		this.opd = opd;
	}
	public char getOpr() 
	{
		return opr;
	}
	public void setOpr(char opr) 
	{
		this.opr = opr;
	}
	public int getPro() 
	{
		return pro;
	}
	public void setPro(int pro) 
	{
		this.pro = pro;
	}
}
