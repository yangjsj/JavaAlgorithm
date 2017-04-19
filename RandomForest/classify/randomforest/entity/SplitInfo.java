package classify.randomforest.entity;

public class SplitInfo {

	private int treeNodeIndex=0;
	private int featureIndex=0;
	private long nodeInstancesNum=0;
	private boolean isLeaf=false;
	private double threshold=0;
	private double impurity=0;
	private double gain=0;
	private double predict=0;
	private double avgLabel=0;
	public SplitInfo()
	{
		this.impurity=Double.MAX_VALUE;
		this.gain=0;
	}
	public int getTreeNodeIndex() {
		return treeNodeIndex;
	}
	public void setTreeNodeIndex(int treeNodeIndex) {
		this.treeNodeIndex = treeNodeIndex;
	}
	public int getFeatureIndex() {
		return featureIndex;
	}
	public void setFeatureIndex(int featureIndex) {
		this.featureIndex = featureIndex;
	}
	public long getNodeInstancesNum() {
		return nodeInstancesNum;
	}
	public void setNodeInstancesNum(long nodeInstancesNum) {
		this.nodeInstancesNum = nodeInstancesNum;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	public double getImpurity() {
		return impurity;
	}
	public void setImpurity(double impurity) {
		this.impurity = impurity;
	}
	public double getGain() {
		return gain;
	}
	public void setGain(double gain) {
		this.gain = gain;
	}
	public double getPredict() {
		return predict;
	}
	public void setPredict(double predict) {
		this.predict = predict;
	}
	public double getAvgLabel() {
		return avgLabel;
	}
	public void setAvgLabel(double avgLabel) {
		this.avgLabel = avgLabel;
	}
	
}
