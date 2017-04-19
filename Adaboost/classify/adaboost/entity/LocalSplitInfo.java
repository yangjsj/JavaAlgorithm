package classify.adaboost.entity;

public class LocalSplitInfo {

	private boolean isValid = true;
	private int treeNodeIndex = 0;
	private int featureId = 0;
	private long nodeCount = 0;
	// impurity
	private double bestImpurity = Double.MAX_VALUE;
	private double bestGain = 0;
	private double bestSplitThreshold = 0;
	private double impurity = 0;
	// gain
	private double initFitness = 0;
	private double predict = 0;
	private double ss = 0;
	private double allS = 0;
	private double allC = 0;
	private double leftS = 0;
	private double leftC = 0;
	private double rightS = 0;
	private double rightC = 0;
	// no relate with model
	private double avgLabel = 0;

	public LocalSplitInfo(int treeNodeIndex, int featureId) {
		this.isValid = true;
		this.treeNodeIndex = treeNodeIndex;
		this.featureId = featureId;
		this.ss = 0;
		this.allS = 0;
		this.allC = 0;
		this.leftC = 0;
		this.leftS = 0;
		this.rightC = 0;
		this.rightS = 0;
		this.impurity = 0;
		this.bestGain = 0;
		this.bestImpurity = 0;
		this.bestSplitThreshold = 0;
		this.avgLabel = 0;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public int getTreeNodeIndex() {
		return treeNodeIndex;
	}

	public void setTreeNodeIndex(int treeNodeIndex) {
		this.treeNodeIndex = treeNodeIndex;
	}

	public int getFeatureId() {
		return featureId;
	}

	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}

	public long getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(long nodeCount) {
		this.nodeCount = nodeCount;
	}

	public double getBestImpurity() {
		return bestImpurity;
	}

	public void setBestImpurity(double bestImpurity) {
		this.bestImpurity = bestImpurity;
	}

	public double getBestGain() {
		return bestGain;
	}

	public void setBestGain(double bestGain) {
		this.bestGain = bestGain;
	}

	public double getBestSplitThreshold() {
		return bestSplitThreshold;
	}

	public void setBestSplitThreshold(double bestSplitThreshold) {
		this.bestSplitThreshold = bestSplitThreshold;
	}

	public double getImpurity() {
		return impurity;
	}

	public void setImpurity(double impurity) {
		this.impurity = impurity;
	}

	public double getInitFitness() {
		return initFitness;
	}

	public void setInitFitness(double initFitness) {
		this.initFitness = initFitness;
	}

	public double getPredict() {
		return predict;
	}

	public void setPredict(double predict) {
		this.predict = predict;
	}

	public double getSs() {
		return ss;
	}

	public void setSs(double ss) {
		this.ss = ss;
	}

	public double getAllS() {
		return allS;
	}

	public void setAllS(double allS) {
		this.allS = allS;
	}

	public double getAllC() {
		return allC;
	}

	public void setAllC(double allC) {
		this.allC = allC;
	}

	public double getLeftS() {
		return leftS;
	}

	public void setLeftS(double leftS) {
		this.leftS = leftS;
	}

	public double getLeftC() {
		return leftC;
	}

	public void setLeftC(double leftC) {
		this.leftC = leftC;
	}

	public double getRightS() {
		return rightS;
	}

	public void setRightS(double rightS) {
		this.rightS = rightS;
	}

	public double getRightC() {
		return rightC;
	}

	public void setRightC(double rightC) {
		this.rightC = rightC;
	}

	public double getAvgLabel() {
		return avgLabel;
	}

	public void setAvgLabel(double avgLabel) {
		this.avgLabel = avgLabel;
	}

	@Override
	public String toString() {
		return "LocalSplitInfo [isValid=" + isValid + ", treeNodeIndex=" + treeNodeIndex + ", featureId=" + featureId
				+ ", nodeCount=" + nodeCount + ", bestImpurity=" + bestImpurity + ", bestGain=" + bestGain
				+ ", bestSplitThreshold=" + bestSplitThreshold + ", impurity=" + impurity + ", initFitness="
				+ initFitness + ", predict=" + predict + ", ss=" + ss + ", allS=" + allS + ", allC=" + allC + ", leftS="
				+ leftS + ", leftC=" + leftC + ", rightS=" + rightS + ", rightC=" + rightC + ", avgLabel=" + avgLabel
				+ "]";
	}

}
