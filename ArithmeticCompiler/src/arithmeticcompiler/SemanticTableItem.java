package arithmeticcompiler;

//import SemanticTableItem;

public class SemanticTableItem 
{
	private String result = null;		
	private double val;
	private int no;
	private int tag;
	private String a0 = null;
	private String a1 = null;
	private String a2 = null;
	private String a3 = null;
	
	public SemanticTableItem(int no,String result,String val)
	{
		this.no = no;
		this.result = new String(result);
		this.val = Double.parseDouble(val);
	}
	
	public SemanticTableItem(String result,int tag)
	{
		this.result = new String(result);
		this.tag = tag;
	}
	
	public SemanticTableItem(String a0,String a1,String a2,String a3)
	{
		this.a0 = new String(a0);
		this.a1 = new String(a1);
		this.a2 = new String(a2);
		this.a3 = new String(a3);
	}

	public SemanticTableItem()
	{
		
	}
	
	public SemanticTableItem(SemanticTableItem temp) 
	{
		this.result = new String(temp.result);
		this.val = temp.val;
	}

	/* setter和getter */
	public String getResult() 
	{
		return result;
	}

	public double getVal() 
	{
		return val;
	}

	public int getNo() 
	{
		return no;
	}

	public int getTag() 
	{
		return tag;
	}
	
	public void setTag(int tag)
	{
		this.tag = tag;
	}

	public String getA0() 
	{
		return a0;
	}

	public String getA1() 
	{
		return a1;
	}

	public String getA2() 
	{
		return a2;
	}

	public String getA3() 
	{
		return a3;
	}
}
