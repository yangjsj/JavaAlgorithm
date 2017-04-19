package classify.adaboost.entity;

import classify.adaboost.model.LocalConfig;

public class Instance {
	private long instanceId;
	private long groupId;
	private String instanceIdStr;

	private double[] features;
	private double label;
	private double target;
	private double recentPredict;
	private boolean selected;
	private double weight;
	private double reweight;
	private int treeNodeIndex;

	public Instance() {
		instanceId = -1;
		groupId = -1;
		instanceIdStr = null;
		features = null;
		label = 0;
		target = 0;
		recentPredict = 0;
		selected = false;
		weight = 1.;
		reweight = 1.;
		treeNodeIndex = 0;
	}

	public Instance(long instanceId,String featureStr) {
		this.instanceId=instanceId;
		this.recentPredict = 0;
		String featuresstr[] = featureStr.split(LocalConfig.Separator);
		this.label = Double.parseDouble(featuresstr[1]);
		features = new double[featuresstr.length];
		for (int i = 0; i < features.length; i++) {
			features[i] = Double.parseDouble(featuresstr[i]);
		}
	}

	public Instance(Instance instance) {
		this.label = instance.getLabel();
		this.instanceId = instance.getInstanceId();
		this.groupId = instance.getGroupId();
		this.instanceIdStr = instance.getInstanceIdStr();

		this.target = instance.getTarget();
		this.recentPredict = instance.getRecentPredict();
		this.selected = true;
		this.weight = instance.getWeight();
		this.reweight = instance.getRecentPredict();
		this.treeNodeIndex = instance.getTreeNodeIndex();
		this.features = new double[instance.getFeaturesNum()];
		for (int i = 0; i < features.length; i++) {
			features[i] = instance.getFeature(i);
		}
	}

	public double getFeature(int index) {
		return features[index];
	}

	public int getFeaturesNum() {
		return features.length;
	}

	public long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(long instanceId) {
		this.instanceId = instanceId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getInstanceIdStr() {
		return instanceIdStr;
	}

	public void setInstanceIdStr(String instanceIdStr) {
		this.instanceIdStr = instanceIdStr;
	}

	public double[] getFeatures() {
		return features;
	}

	public void setFeatures(double[] features) {
		this.features = features;
	}

	public double getLabel() {
		return label;
	}

	public void setLabel(double label) {
		this.label = label;
	}

	public double getTarget() {
		return target;
	}

	public void setTarget(double target) {
		this.target = target;
	}

	public double getRecentPredict() {
		return recentPredict;
	}

	public void setRecentPredict(double recentPredict) {
		this.recentPredict = recentPredict;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getReweight() {
		return reweight;
	}

	public void setReweight(double reweight) {
		this.reweight = reweight;
	}

	public int getTreeNodeIndex() {
		return treeNodeIndex;
	}

	public void setTreeNodeIndex(int treeNodeIndex) {
		this.treeNodeIndex = treeNodeIndex;
	}

	public double getG() {
		return target * reweight;
	}

	public double getH() {
		return reweight;
	}

	public void setGH(double g, double h) {
		if (h <= 0) {
			this.target = 0;
			this.reweight = 00;
		} else {
			this.target = g / h;
			this.reweight = h * weight;
		}
	}

	public String toString() {
		if (features == null)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append("label:" + label);
		return sb.toString();
	}

	public double getCurrentPredict(Model model) {
		if (model.getCurTreeIndex() < 0 || model.getCurrentTree() == null)
			return 0;
		return model.getCurrentTree().getTreeNodes(this.treeNodeIndex).getPredict();
	}
}
