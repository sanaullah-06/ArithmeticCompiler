package arithmeticcompiler;

public class Node 
{
	int level;//层数
	String value;
	int father;
	int []child = new int[3];
	int id;
	boolean has_child;
	
	public Node(int _count)
	{
		id = _count;
		child[0] = 0;
		child[1] = 0;
		child[2] = 0;
		has_child = false;
	}
}
