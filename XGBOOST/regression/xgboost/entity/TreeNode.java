package regression.xgboost.entity;

public class TreeNode {

	public String featureName;
	private double thresholdValue;
	private double predict;
	private int featureIndex;
	private boolean isLeaf;
	private long instanceNum;
	private double gain;
	private double avgLabel=0;
	private double weight;
	
	public TreeNode()
	{
		this.isLeaf=true;
		this.thresholdValue=0;
		this.featureIndex=-1;
		this.predict=0;
		this.instanceNum=0;
		this.featureName=null;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public double getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(double thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	public double getPredict() {
		return predict;
	}

	public void setPredict(double predict) {
		this.predict = predict;
	}

	public int getFeatureIndex() {
		return featureIndex;
	}

	public void setFeatureIndex(int featureIndex) {
		this.featureIndex = featureIndex;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public long getInstanceNum() {
		return instanceNum;
	}

	public void setInstanceNum(long instanceNum) {
		this.instanceNum = instanceNum;
	}

	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}

	public double getAvgLabel() {
		return avgLabel;
	}

	public void setAvgLabel(double avgLabel) {
		this.avgLabel = avgLabel;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "TreeNode [featureName=" + featureName + ", thresholdValue=" + thresholdValue + ", predict=" + predict
				+ ", featureIndex=" + featureIndex + ", isLeaf=" + isLeaf + ", instanceNum=" + instanceNum + ", gain="
				+ gain + ", avgLabel=" + avgLabel + ", weight=" + weight + "]";
	}
	
	
}
