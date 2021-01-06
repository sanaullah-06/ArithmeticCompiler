package arithmeticcompiler;

import java.util.ArrayList;
import java.util.List;

public class GenerateExps //产生式
{
	class GenerateExpItem
	{
		String left;
		String right;
		String op;
	}
	private List<GenerateExpItem> G;
	public GenerateExps()
	{
		G=new ArrayList<GenerateExpItem>();
		GenerateExpItem t=new GenerateExpItem();
		t.left=new String("E");
		t.right=new String("T");
		t.op=null;
		G.add(t);//E->T
		
		t=new GenerateExpItem();
		t.left=new String("E");
		t.right=new String("E + T");
		t.op=new String("+");
		G.add(t);//E->E+T
		
		t=new GenerateExpItem();
		t.left=new String("E");
		t.right=new String("E - T");
		t.op=new String("-");
		G.add(t);//E->E-T
		
		t=new GenerateExpItem();
		t.left=new String("T");
		t.right=new String("F");
		G.add(t);//T->F
		
		t=new GenerateExpItem();
		t.left=new String("T");
		t.right=new String("T * F");
		t.op=new String("*");
		G.add(t);//T->T*F
		
		t=new GenerateExpItem();
		t.left=new String("T");
		t.right=new String("T / F");
		t.op=new String("/");
		G.add(t);//T->T/F
		
		t=new GenerateExpItem();
		t.left=new String("F");
		t.right=new String("P");
		G.add(t);//F->P
		
		t=new GenerateExpItem();
		t.left=new String("F");
		t.right=new String("P ^ F");
		t.op=new String("^");
		G.add(t);//F->p^F
		
		t=new GenerateExpItem();
		t.left=new String("P");
		t.right=new String("i");
		t.op=new String("=");
		G.add(t);//P->i
		
		t=new GenerateExpItem();
		t.left=new String("P");
		t.right=new String("( E )");
		t.op=new String("(");
		G.add(t);//P->(E)
	}
	
	public List<GenerateExpItem> getG()
	{
		return this.G;
	}
}
