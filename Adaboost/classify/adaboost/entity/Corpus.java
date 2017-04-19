package classify.adaboost.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import classify.adaboost.lossFunction.LossFucntion;
import classify.adaboost.model.LocalConfig;

import java.util.Random;
import java.util.Set;

public class Corpus {
	private int featuresNum = 0;
	private long instancesNum = 0;
	private Map<Integer, Integer> selectedFeatureSet = null;
	private Map<Long, Instance> instances = null;
	private Map<Integer, List<Long>> sortedFeature = null;

	public Corpus() {
		selectedFeatureSet = new HashMap<Integer, Integer>();
		instances = new HashMap<Long, Instance>();
		sortedFeature = new HashMap<Integer, List<Long>>();
	}

	public void readTrainData(String TrainfileName) {
		File file = new File(TrainfileName);
		BufferedReader reader = null;
		long instanceId = 1;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;

			while ((tempString = reader.readLine()) != null) {
				Instance instacnce = new Instance(instanceId, tempString);
				instances.put(instanceId, instacnce);
				instanceId++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
					System.out.println("load " + instanceId + " date end!");
				} catch (IOException e1) {
				}
			}
		}
		instancesNum = instances.size();
		sort();
	}

	public void sort() {
		Map<Long, Double> newmap = null;
		List<Map.Entry<Long, Double>> list = null;
		for (int featureIndex = 0; featureIndex < featuresNum; featureIndex++) {
			newmap = new HashMap<Long, Double>();
			Iterator<Entry<Long, Instance>> iterator = instances.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Long, Instance> nextEntry = iterator.next();
				newmap.put(nextEntry.getKey(), nextEntry.getValue().getFeature(featureIndex));
			}
			list = new ArrayList<Map.Entry<Long, Double>>(newmap.entrySet());
			Collections.sort(list, new Comparator<Map.Entry<Long, Double>>() {
				@Override
				public int compare(Entry<Long, Double> o1, Entry<Long, Double> o2) {
					if ((double) o1.getValue() < (double) o2.getValue())
						return -1;
					else if ((double) o1.getValue() == (double) o2.getValue())
						return 0;
					else
						return 1;
				}
			});
			List<Long> sortedInstancesId = new ArrayList<Long>();
			for (int i = 0; i < list.size(); i++) {
				sortedInstancesId.add(list.get(i).getKey());
			}
			sortedFeature.put(featureIndex, sortedInstancesId);
		}
	}

	public void updateInstance(Model model) {
		Iterator<Entry<Long, Instance>> it = instances.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Long, Instance> instanceEntry = it.next();
			Instance t = instanceEntry.getValue();
			t.setRecentPredict(t.getRecentPredict() + t.getCurrentPredict(model) * LocalConfig.SHRINKAGE);
			model.getLossFunction().setTarget(t.getRecentPredict(), t);
		}
	}

	public void updateSplitTreeNode(Model model) {
		Map<Integer, Double> mapS = new HashMap<Integer, Double>();
		Map<Integer, Double> mapC = new HashMap<Integer, Double>();
		Map<Integer, Double> mapLabel = new HashMap<Integer, Double>();
		Map<Integer, Long> mapCount = new HashMap<Integer, Long>();
		Tree treeI = model.getCurrentTree();
		Set<Integer> treeNodeSet = model.getTreeNodeSet();
		Iterator<Entry<Long, Instance>> it = instances.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Long, Instance> instanceEntry = it.next();
			Instance oneInstance = instanceEntry.getValue();
			long instanceId = oneInstance.getInstanceId();
			int treeNodeIndex = oneInstance.getTreeNodeIndex();
			if (treeNodeSet.contains(treeNodeIndex) && treeI.getTreeNodes(treeNodeIndex) != null
					&& !treeI.getTreeNodes(treeNodeIndex).isLeaf()) {
				int featureIndex = treeI.getTreeNodes(treeNodeIndex).getFeatureIndex();
				if (oneInstance.getFeature(featureIndex) < treeI.getTreeNodes(treeNodeIndex).getThresholdValue()) {
					oneInstance.setTreeNodeIndex(2 * treeNodeIndex);
				} else {
					oneInstance.setTreeNodeIndex(2 * treeNodeIndex + 1);
				}
				// update predict value
				if (!mapS.containsKey(oneInstance.getTreeNodeIndex())) {
					mapS.put(oneInstance.getTreeNodeIndex(), 0.0);
					mapC.put(oneInstance.getTreeNodeIndex(), 0.0);
					mapLabel.put(oneInstance.getTreeNodeIndex(), 0.0);
					mapCount.put(oneInstance.getTreeNodeIndex(), 0l);
				}
				if (oneInstance.isSelected()) {
					mapS.put(oneInstance.getTreeNodeIndex(),
							mapS.get(oneInstance.getTreeNodeIndex() + oneInstance.getG()));
					mapC.put(oneInstance.getTreeNodeIndex(),
							mapC.get(oneInstance.getTreeNodeIndex() + oneInstance.getH()));
					mapLabel.put(oneInstance.getTreeNodeIndex(),
							mapLabel.get(oneInstance.getTreeNodeIndex() + oneInstance.getLabel()));
				}
				mapCount.put(oneInstance.getTreeNodeIndex(), mapCount.get(oneInstance.getTreeNodeIndex() + 1));
			}
			instances.put(instanceId, oneInstance);
		}
		for (int treeNodeIndex : mapS.keySet()) {
			double predict = model.getLossFunction().getImpurity().calPred(mapS.get(treeNodeIndex),
					mapC.get(treeNodeIndex));
			if (treeI.getTreeNodes(treeNodeIndex) == null) {
				TreeNode childNode = new TreeNode();
				childNode.setPredict(predict);
				childNode.setInstanceNum(mapCount.get(treeNodeIndex));
				childNode.setAvgLabel(mapLabel.get(treeNodeIndex) / mapCount.get(treeNodeIndex));
				treeI.setTreeNodes(treeNodeIndex, childNode);
			} else {
				treeI.getTreeNodes(treeNodeIndex).setPredict(predict);
				treeI.getTreeNodes(treeNodeIndex).setInstanceNum(mapCount.get(treeNodeIndex));
				treeI.getTreeNodes(treeNodeIndex)
						.setAvgLabel(mapLabel.get(treeNodeIndex) / mapCount.get(treeNodeIndex));
			}
		}
		model.setTree(model.getCurTreeIndex(), treeI);
	}

	public void samplingInstanceNoReplacement(Model model, double rate) {
		Random random = new Random();
		Iterator<Entry<Long, Instance>> it = instances.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Long, Instance> instanceEntry = it.next();
			Instance t = instanceEntry.getValue();
			t.setSelected(false);
			t.setTreeNodeIndex(1);
			if (random.nextDouble() <= rate) {
				t.setSelected(true);
			}
		}
	}

	public void samplingInstanceWithReplacement(Model model, double rate) {

		Iterator<Entry<Long, Instance>> it = instances.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Long, Instance> instanceEntry = it.next();
			Instance t = instanceEntry.getValue();
			t.setSelected(false);

		}
		Random random = new Random();
		long num = (long) (rate * instancesNum);
		long selectedNum = 0;

		while (selectedNum < num) {
			long instanceId = random.nextLong() % instancesNum;
			Instance t = instances.get(instanceId);
			t.setTreeNodeIndex(1);
			t.setSelected(true);
			selectedNum++;
		}
	}

	public void samplingFeature(double rate) {
		selectedFeatureSet = new HashMap<Integer, Integer>();
		int sampleNumber = (int) (this.featuresNum * rate);
		if (featuresNum <= 0 || rate <= 0) {
			return;
		}
		if (sampleNumber > featuresNum) {
			sampleNumber = featuresNum;
		}
		for (int i = 0; i < sampleNumber; i++) {
			selectedFeatureSet.put(i, i);
		}
		Random r = new Random();
		for (int i = sampleNumber; i < featuresNum; i++) {
			int randomNumber = r.nextInt(i);
			if (randomNumber < sampleNumber) {
				selectedFeatureSet.put(randomNumber, i);
			}
		}
	}

	public double overallLoss(Model model) {
		double newpredict = 0;
		double loss = 0;
		double sumGap = 0, sumReal = 0;
		double mareS = 0;
		LossFucntion lossfucntion = model.getLossFunction();
		Iterator<Entry<Long, Instance>> it = instances.entrySet().iterator();
		while (it.hasNext()) {
			Instance t = it.next().getValue();
			loss += lossfucntion.getLoss(t.getLabel(), t.getRecentPredict());
			newpredict = lossfucntion.getInvLink(t.getRecentPredict());
			sumGap += Math.abs(newpredict - t.getLabel());
			sumReal += t.getLabel();
			if (t.getLabel() > 0) {
				mareS += Math.abs(newpredict - t.getLabel()) / t.getLabel();
			}
		}
		System.out.println("loss:" + (loss / this.instancesNum) + ",mae:" + (1 - sumGap / sumReal) + ",mare:"
				+ (mareS / this.instancesNum));
		return loss;
	}

	public int getFeaturesNum() {
		return featuresNum;
	}

	public long getInstancesNum() {
		return instancesNum;
	}

	public Map<Integer, Integer> getSelectedFeatureSet() {
		return selectedFeatureSet;
	}

	public Instance getInstances(long instanceId) {
		return instances.get(instanceId);
	}

	public Map<Long, Instance> getInstances() {
		return instances;
	}

	public List<Long> getSortedFeature(int featureId) {
		return sortedFeature.get(featureId);
	}

}
