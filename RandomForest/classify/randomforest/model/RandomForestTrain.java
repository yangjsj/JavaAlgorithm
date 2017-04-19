package classify.randomforest.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import classify.randomforest.entity.Corpus;
import classify.randomforest.entity.LocalSplitInfo;
import classify.randomforest.entity.Model;
import classify.randomforest.entity.SplitInfo;
import classify.randomforest.impurity.FindBestSplitGreedyCall;
import classify.randomforest.utils.DeamonThreadFactory;
import classify.randomforest.utils.DummyExecutorService;

public class RandomForestTrain {
	private ExecutorService es;
	public Model model = null;
	public Corpus corpus = null;

	public RandomForestTrain(Corpus corpus) {
		this.corpus = corpus;
		int nThreads = LocalConfig.threadsNum;
		if (nThreads == -1) {
			nThreads = corpus.getFeaturesNum();
			es = Executors.newFixedThreadPool(nThreads, new DeamonThreadFactory());
		} else if (nThreads > 0) {
			es = Executors.newFixedThreadPool(nThreads, new DeamonThreadFactory());
		} else if (nThreads == 0) {
			es = new DummyExecutorService();

		}
	}

	public Model xgboostRun() {
		initBias();
		train();
		return model;
	}

	private void initBias() {
		double bias = 0;
		for (int i = 0; i < 3; i++) {
			for (long instanceId : corpus.getInstances().keySet()) {
				model.getLossFunction().setTarget(bias, corpus.getInstances(instanceId));
			}
			bias += model.getLossFunction().getImpurity().computeTreeNodePredictValue(corpus) * LocalConfig.SHRINKAGE;
		}
		for (long instanceId : corpus.getInstances().keySet()) {
			model.getLossFunction().setTarget(bias, corpus.getInstances(instanceId));
			corpus.getInstances(instanceId).setRecentPredict(bias);
		}
		model.setLoss(corpus.overallLoss(model));
		model.setBias(bias);
	}

	private void train() {
		//long start = System.currentTimeMillis();
		for (int treeIndex = 0; treeIndex < LocalConfig.TREE_NUM; treeIndex++) {
			corpus.samplingInstanceNoReplacement(model, LocalConfig.DATE_SELELCT_RATE);
			corpus.samplingFeature(LocalConfig.FEATURE_SELECT_RATE);
			for (int treeDeep = 1; treeDeep <= LocalConfig.TREE_DEPTHD; treeDeep++) {
				model.nextStep();
				//start = System.currentTimeMillis();
				model.getCurrentTree().addSplit(findBestSplitGreedy());
				corpus.updateSplitTreeNode(model);
			}
			corpus.updateInstance(model);
			corpus.overallLoss(model);
		}
	}

	public Map<Integer, SplitInfo> findBestSplitGreedy() {
		Map<Integer, SplitInfo> splitInfoMsg = new HashMap<Integer, SplitInfo>();
		Set<Integer> treeNodeIndex = model.getTreeNodeSet();
		List<Future<Map<Integer, LocalSplitInfo>>> results = new ArrayList<Future<Map<Integer, LocalSplitInfo>>>();
		for (int featureIndex : corpus.getSelectedFeatureSet().keySet()) {
			int featureId = corpus.getSelectedFeatureSet().get(featureIndex);
			results.add(es.submit(new FindBestSplitGreedyCall(corpus, treeNodeIndex, featureId, model)));

		}
		try {
			for (Future<Map<Integer, LocalSplitInfo>> result : results) {
				Map<Integer, LocalSplitInfo> ret = result.get();
				for (int curTreeNodeIndex : ret.keySet()) {
					LocalSplitInfo splitInfoNode = ret.get(curTreeNodeIndex);
					if (splitInfoNode.isValid()) {
						if (!splitInfoMsg.containsKey(curTreeNodeIndex)) {
							SplitInfo siw = new SplitInfo();
							splitInfoMsg.put(curTreeNodeIndex, siw);
						}
						SplitInfo siw = splitInfoMsg.get(curTreeNodeIndex);
						if (ret.get(curTreeNodeIndex) != null && splitInfoNode.getBestImpurity() < siw.getImpurity()
								&& splitInfoNode.getBestImpurity() < LocalConfig.MAX_VALUE) {
							siw.setTreeNodeIndex(curTreeNodeIndex);
							siw.setFeatureIndex(splitInfoNode.getFeatureId());
							siw.setThreshold(splitInfoNode.getBestSplitThreshold());
							siw.setImpurity(splitInfoNode.getBestImpurity());
							siw.setGain(splitInfoNode.getBestGain());
							siw.setPredict(splitInfoNode.getPredict());
							siw.setNodeInstancesNum(splitInfoNode.getNodeCount());
							siw.setAvgLabel(splitInfoNode.getAvgLabel());
							splitInfoMsg.put(curTreeNodeIndex, siw);
						}
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return splitInfoMsg;
	}
}
