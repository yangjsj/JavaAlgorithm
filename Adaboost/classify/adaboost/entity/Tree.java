package classify.adaboost.entity;

import java.util.Map;
import java.util.TreeMap;

public class Tree {

	private int treeIndex;
	private int treeDepth;
	private int treeNodeNum;
	private double treeWeigh;
	private TreeNode[] treeNodes;
	private TreeMap<Integer, String> encodeDic = new TreeMap<Integer, String>();

	public Tree(int treeIndex, int treeDepth, int treeNodeNum) {
		this.treeIndex = treeIndex;
		this.treeDepth = treeDepth;
		this.treeNodeNum = treeNodeNum;
		this.treeWeigh = 1.0;
		treeNodes = new TreeNode[treeNodeNum + 1];
		for (int i = 1; i < treeNodes.length; i++) {
			this.treeNodes[i] = null;
		}
	}

	public void addSplit(Map<Integer, SplitInfo> splitInfo) {
		for (int treeNodeIndex : splitInfo.keySet()) {
			SplitInfo node = splitInfo.get(treeNodeIndex);
			treeNodes[treeNodeIndex] = new TreeNode();
			treeNodes[treeNodeIndex].setFeatureName("");
			treeNodes[treeNodeIndex].setFeatureIndex(node.getFeatureIndex());
			treeNodes[treeNodeIndex].setLeaf(node.isLeaf());
			treeNodes[treeNodeIndex].setThresholdValue(node.getThreshold());
			treeNodes[treeNodeIndex].setPredict(node.getPredict());
			treeNodes[treeNodeIndex].setGain(node.getGain());
			treeNodes[treeNodeIndex].setInstanceNum(node.getNodeInstancesNum());
		}
	}

	public void setTreeNode(int treeNodeIndex, TreeNode treeNode) {
		this.treeNodes[treeNodeIndex] = treeNode;
	}

	public int getTreeIndex() {
		return treeIndex;
	}

	public void setTreeIndex(int treeIndex) {
		this.treeIndex = treeIndex;
	}

	public int getTreeDepth() {
		return treeDepth;
	}

	public void setTreeDepth(int treeDepth) {
		this.treeDepth = treeDepth;
	}

	public int getTreeNodeNum() {
		return treeNodeNum;
	}

	public void setTreeNodeNum(int treeNodeNum) {
		this.treeNodeNum = treeNodeNum;
	}

	public double getTreeWeigh() {
		return treeWeigh;
	}

	public void setTreeWeigh(double treeWeigh) {
		this.treeWeigh = treeWeigh;
	}

	public TreeNode[] getTreeNodes() {
		return treeNodes;
	}

	public TreeNode getTreeNodes(int treeNodeIndex) {
		return treeNodes[treeNodeIndex];
	}

	public void setTreeNodes(TreeNode[] treeNodes) {
		this.treeNodes = treeNodes;
	}

	public void setTreeNodes(int treeNodeIndex, TreeNode treeNode) {
		this.treeNodes[treeNodeIndex] = treeNode;
	}

	public TreeMap<Integer, String> getEncodeDic() {
		return encodeDic;
	}

	public void setEncodeDic(TreeMap<Integer, String> encodeDic) {
		this.encodeDic = encodeDic;
	}

}
