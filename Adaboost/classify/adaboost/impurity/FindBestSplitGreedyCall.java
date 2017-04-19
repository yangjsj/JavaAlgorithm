package classify.adaboost.impurity;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import classify.adaboost.entity.Corpus;
import classify.adaboost.entity.LocalSplitInfo;
import classify.adaboost.entity.Model;
import classify.adaboost.lossFunction.LossFucntion;

public class FindBestSplitGreedyCall implements Callable<Map<Integer, LocalSplitInfo>> {
	Set<Integer> treeIndexSet = null;
	Corpus corpus = null;
	LossFucntion lossFucntion = null;
	int curTreeNodeIndex = 0;
	int featureId = 0;

	public FindBestSplitGreedyCall(Corpus corpus, Set<Integer> treeIndexSet, int featureId, Model model) {
		this.treeIndexSet = treeIndexSet;
		this.corpus = corpus;
		this.lossFucntion = model.getLossFunction();
		this.featureId = featureId;
	}

	@Override
	public Map<Integer, LocalSplitInfo> call() throws Exception {
		return lossFucntion.getImpurity().greedyFindSplit(corpus, treeIndexSet, featureId);
	}

}
