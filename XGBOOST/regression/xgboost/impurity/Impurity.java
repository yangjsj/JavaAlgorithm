package regression.xgboost.impurity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import regression.xgboost.entity.Corpus;
import regression.xgboost.entity.Instance;
import regression.xgboost.entity.LocalSplitInfo;
import regression.xgboost.model.LocalConfig;

public abstract class Impurity {

	public Map<Integer, LocalSplitInfo> greedyFindSplit(Corpus corpus, Set<Integer> treeIndexSet, int featureId) {
		int treeNodeIndex = 0;
		Instance oneInstance = null;
		Map<Integer, LocalSplitInfo> mapSplitInfo = new HashMap<Integer, LocalSplitInfo>();
		// compute all
		Iterator<Integer> setIt = treeIndexSet.iterator();
		while (setIt.hasNext()) {
			treeNodeIndex = setIt.next();
			LocalSplitInfo splitNode = new LocalSplitInfo(treeNodeIndex, featureId);
			mapSplitInfo.put(treeNodeIndex, splitNode);
		}
		Iterator<Entry<Long, Instance>> it = corpus.getInstances().entrySet().iterator();
		while (it.hasNext()) {
			Entry<Long, Instance> instanceEntry = it.next();
			oneInstance = instanceEntry.getValue();
			treeNodeIndex = oneInstance.getTreeNodeIndex();
			if (oneInstance.isSelected() && mapSplitInfo.containsKey(treeNodeIndex)) {
				LocalSplitInfo valueNode = mapSplitInfo.get(treeNodeIndex);
				valueNode.setNodeCount(valueNode.getNodeCount() + 1);
				valueNode.setAllS(valueNode.getAllS() + oneInstance.getG());
				valueNode.setAllC(valueNode.getAllC() + oneInstance.getH());
				valueNode.setSs(valueNode.getSs() + calResidue(oneInstance.getG(), oneInstance.getH()));
				valueNode.setAvgLabel(valueNode.getAvgLabel() + oneInstance.getLabel());
				mapSplitInfo.put(treeNodeIndex, valueNode);
			}
		}
		for (int keyid : mapSplitInfo.keySet()) {
			LocalSplitInfo valueNode = mapSplitInfo.get(keyid);
			if (valueNode.getNodeCount() <= LocalConfig.MIN_LEAFS_NUM) {
				valueNode.setValid(false);
			} else {
				valueNode.setValid(true);
				valueNode.setRightS(valueNode.getAllS());
				valueNode.setRightC(valueNode.getAllC());
				valueNode.setInitFitness(calFitness(valueNode.getAllS(), valueNode.getAllC()));
				valueNode.setBestImpurity(valueNode.getSs());
				valueNode.setAvgLabel(valueNode.getAvgLabel() / valueNode.getNodeCount());
			}
			valueNode.setPredict(calPred(valueNode.getAllS(), valueNode.getAllC()));
			mapSplitInfo.put(keyid, valueNode);
		}
		/*** compute tree split point **/
		double featureCurrentValue = 0;
		double featureNextValue = 0;
		List<Long> sortedInstancs = corpus.getSortedFeature(featureId);
		for (int j = 0; j < sortedInstancs.size(); j++) {
			oneInstance = corpus.getInstances(sortedInstancs.get(j));
			treeNodeIndex = oneInstance.getTreeNodeIndex();
			if (oneInstance.isSelected() && mapSplitInfo.containsKey(treeNodeIndex)) {
				featureCurrentValue = oneInstance.getFeature(featureId);
				LocalSplitInfo valueNode = mapSplitInfo.get(treeNodeIndex);
				valueNode.setLeftS(valueNode.getLeftS() + oneInstance.getG());
				valueNode.setLeftC(valueNode.getLeftC() + oneInstance.getH());
				valueNode.setRightS(valueNode.getRightS() - oneInstance.getG());
				valueNode.setRightC(valueNode.getRightC() - oneInstance.getH());
				// find next
				int nextIndex = j + 1;
				for (nextIndex = j + 1; nextIndex < sortedInstancs.size(); nextIndex++) {
					oneInstance = corpus.getInstances(sortedInstancs.get(nextIndex));
					if (oneInstance.isSelected() && oneInstance.getTreeNodeIndex() == treeNodeIndex) {
						break;
					}
				}
				featureNextValue = featureCurrentValue;
				if (nextIndex < sortedInstancs.size()) {
					featureNextValue = oneInstance.getFeature(featureId);
				} else {
					continue;
				}
				if (almostEqual(featureNextValue, featureCurrentValue)) {
					continue;
				}
				double impurity = valueNode.getSs() + calFitness(valueNode.getLeftS(), valueNode.getLeftC())
						+ calFitness(valueNode.getRightS(), valueNode.getRightC());
				double gain = valueNode.getInitFitness() - (calFitness(valueNode.getLeftS(), valueNode.getLeftC())
						+ calFitness(valueNode.getRightS(), valueNode.getRightC()));

				valueNode.setImpurity(impurity);
				if (valueNode.getImpurity() < valueNode.getBestImpurity()) {
					valueNode.setBestImpurity(valueNode.getImpurity());
					valueNode.setBestSplitThreshold((featureCurrentValue + featureNextValue) / 2);
					valueNode.setBestGain(gain);
					mapSplitInfo.put(treeNodeIndex, valueNode);
				}
			}
		}
		return mapSplitInfo;
	}

	public double computeTreeNodePredictValue(Corpus corpus) {
		double s = 0;
		double c = 0;
		if (corpus.getInstancesNum() > 0) {
			for (long instanceId : corpus.getInstances().keySet()) {
				s += corpus.getInstances(instanceId).getG();
				s += corpus.getInstances(instanceId).getH();
			}

		}
		double result = calPred(s, c);
		return result;
	}

	abstract public double calPred(double t, double c);

	abstract public double calResidue(double t, double c);

	abstract public double calFitness(double t, double c);

	public static boolean almostEqual(double v1, double v2) {
		double diff = Math.abs(v1 - v2);
		if (diff < 1.0e-5)
			return true;
		return false;
	}
}
