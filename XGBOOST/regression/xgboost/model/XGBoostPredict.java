package regression.xgboost.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import regression.xgboost.entity.Model;
import regression.xgboost.entity.Tree;
import regression.xgboost.entity.TreeNode;

public class XGBoostPredict {

	public class ModelStr {
		Map<Integer, String> contents = null;

		public ModelStr(int rowNum, String rowContent) {
			contents = new TreeMap<Integer, String>();
			contents.put(rowNum, rowContent);
		}

		public Model toModel() {
			Model model = null;
			Iterator<Entry<Integer, String>> it = contents.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Integer, String> entry = it.next();
				int index = entry.getKey();
				String rowValues[] = entry.getValue().split(LocalConfig.Separator);
				if (index > 0) {
					int treeIndex = Integer.parseInt(rowValues[0].toString());
					String strIndex[] = rowValues[1].split(";");
					String strValue[] = rowValues[2].split(";");
					String strPredict[] = rowValues[3].split(";");
					Tree treei = model.getTree(treeIndex);
					for (int i = 0; i < strIndex.length; i++) {
						TreeNode newTreeNode = new TreeNode();
						if (Integer.parseInt(strIndex[i]) != -1) {
							newTreeNode.setFeatureIndex(Integer.parseInt(strIndex[i]));
							newTreeNode.setThresholdValue(Double.parseDouble(strValue[i]));
							newTreeNode.setPredict(Double.parseDouble(strPredict[i]));
							newTreeNode.setLeaf(false);
						} else {
							newTreeNode.setPredict(Double.parseDouble(strPredict[i]));
							newTreeNode.setLeaf(true);
						}
						treei.setTreeNodes(i + 1, newTreeNode);
					}
					model.setTree(treeIndex, treei);
				} else {
					double bias = Double.parseDouble(rowValues[1].toString());
					double shrinkage = Double.parseDouble(rowValues[1].toString());
					int treeNum = Integer.parseInt(rowValues[1].toString());
					int treeDepth = Integer.parseInt(rowValues[1].toString());
					String loss = "";
					model = new Model(loss, bias, shrinkage, treeNum, treeDepth);
				}
			}
			return model;
		}
	}
}
