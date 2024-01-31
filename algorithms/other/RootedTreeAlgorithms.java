import java.util.Stack;

/**
 * This program implements rooted trees traversal functions.
 * @version 1.0 2024-01-31
 * @author Strokin Constantine
 */
 
public class RootedTreeAlgorithms
{
    public static void binaryTreeTraversalUsingRecursion(BinaryTreeNode root) // O(n)
    {
        System.out.println(root);

        if (root.leftChild != null)
            binaryTreeTraversalUsingRecursion(root.leftChild);
        if (root.rightChild != null)
            binaryTreeTraversalUsingRecursion(root.rightChild);
    }

    public static void binaryTreeTraversalDataStructure(BinaryTreeNode root) // O(n)
    {
        Stack<BinaryTreeNode> dataStructure = new Stack<BinaryTreeNode>();

        dataStructure.push(root);
		
        while (!dataStructure.isEmpty())
        {
            BinaryTreeNode node = dataStructure.pop();
			
            System.out.println(node);

            if (node.leftChild != null)
                dataStructure.push(node.leftChild);
            if (node.rightChild != null)
                dataStructure.push(node.rightChild);
        }
    }

    public static void traversalOfTreeNodeInLeftChildRightSiblingRepresentationUsingRecursion(  // O(n)
            TreeNodeInLeftChildRightSiblingRepresentation root)
    {
        System.out.println(root);
		
        TreeNodeInLeftChildRightSiblingRepresentation node = root.leftChild;
		
		// Print all children
        while (node != null)
        {
            traversalOfTreeNodeInLeftChildRightSiblingRepresentationUsingRecursion(node);
            node = node.rightSibling;
        }
    }

    public static void main(
             String[] arguments)
	{
        BinaryTreeNode b7 = new BinaryTreeNode(7);
        BinaryTreeNode b4 = new BinaryTreeNode(4);
        BinaryTreeNode b2 = new BinaryTreeNode(2);
        BinaryTreeNode b21 = new BinaryTreeNode(21);
        BinaryTreeNode b12 = new BinaryTreeNode(12);
        BinaryTreeNode b10 = new BinaryTreeNode(10);
        BinaryTreeNode b18 = new BinaryTreeNode(18);

        b18.leftChild = b12;
        b18.rightChild = b10;

        b12.leftChild = b7;
        b12.rightChild = b4;
		
        b10.leftChild = b2;
        b10.rightChild = b21;

        binaryTreeTraversalUsingRecursion(b18);
		System.out.println();

        binaryTreeTraversalDataStructure(b18);
		System.out.println();
		

		TreeNodeInLeftChildRightSiblingRepresentation root = new TreeNodeInLeftChildRightSiblingRepresentation(1);
		
		root.leftChild = new TreeNodeInLeftChildRightSiblingRepresentation(2);
		root.rightSibling = null;
		root.leftChild.rightSibling = new TreeNodeInLeftChildRightSiblingRepresentation(3);
		root.leftChild.rightSibling.rightSibling = new TreeNodeInLeftChildRightSiblingRepresentation(4);
		
		root.leftChild.leftChild = new TreeNodeInLeftChildRightSiblingRepresentation(5);
		root.leftChild.rightSibling.leftChild = new TreeNodeInLeftChildRightSiblingRepresentation(6);
		
		traversalOfTreeNodeInLeftChildRightSiblingRepresentationUsingRecursion(root);
		
    }
}


class BinaryTreeNode
{
    int key;
    BinaryTreeNode leftChild;
    BinaryTreeNode rightChild;

    public BinaryTreeNode(int key)
    {
        this.key = key;
    }

    public BinaryTreeNode(int key, BinaryTreeNode leftChild, BinaryTreeNode rightChild)
    {
        this.key = key;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
	
	@Override
	public String toString()
	{
		return String.valueOf(this.key);
	}
}

class TreeNodeInLeftChildRightSiblingRepresentation
{
    int key;
    TreeNodeInLeftChildRightSiblingRepresentation leftChild;
    TreeNodeInLeftChildRightSiblingRepresentation rightSibling;

    public TreeNodeInLeftChildRightSiblingRepresentation(int key)
    {
        this.key = key;
    }

    public TreeNodeInLeftChildRightSiblingRepresentation(int key,
                                                     TreeNodeInLeftChildRightSiblingRepresentation leftChild,
                                                     TreeNodeInLeftChildRightSiblingRepresentation rightSibling)
    {
        this.key = key;
        this.leftChild = leftChild;
        this.rightSibling = rightSibling;
    }
	
	@Override
	public String toString()
	{
		return String.valueOf(this.key);
	}
}