package classify.adaboost.entity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import classify.adaboost.lossFunction.LossFucntion;
import classify.adaboost.lossFunction.SquareError;
import classify.adaboost.model.LocalConfig;

public class Model {
	private int curTreeIndex = 0;
	private int curLevel = 0;
	private int startTreeNodeIndex = 1;
	private int endTreeNodeIndex = 1;
	private LossFucntion lossFunction = null;
	private String lossFunctionName = null;
	private double bias = 0;
	private double loss = 0;
	private double shrinkage = 0;
	private int treeDepth = 0;
	private int treeNum = 0;
	private List<Tree> treeList = null;
	private Map<Integer, FeatureImportance> featureImportance = null;

	public Model() {
		this.lossFunctionName = LocalConfig.LOSS_FUNCTION;
		this.lossFunction = generateLossFunction(LocalConfig.LOSS_FUNCTION);
		this.curTreeIndex = -1;
		this.curLevel = LocalConfig.TREE_DEPTHD;
		this.startTreeNodeIndex = 1;
		this.endTreeNodeIndex = 1;
		this.shrinkage = LocalConfig.SHRINKAGE;
		this.treeDepth = LocalConfig.TREE_DEPTHD;
		this.treeNum = LocalConfig.TREE_NUM;
		this.featureImportance = new HashMap<Integer, FeatureImportance>();
		treeList = new ArrayList<Tree>();
		int treeNodeNum = (int) Math.pow(2, treeDepth + 1) - 1;
		for (int i = 0; i < this.treeNum; i++) {
			Tree rtw = new Tree(i, treeDepth, treeNodeNum);
			treeList.add(rtw);
		}
	}

	public Model(String lossFunctionName, double bias, double shrinkage, int treeNum, int treeDepth) {
		this.lossFunctionName = lossFunctionName;
		this.lossFunction = generateLossFunction(lossFunctionName);
		this.bias = bias;
		this.shrinkage = shrinkage;
		this.treeDepth = treeDepth;
		this.treeNum = treeNum;
		this.featureImportance = new HashMap<Integer, FeatureImportance>();
		treeList = new ArrayList<Tree>();
		int treeNodeNum = (int) Math.pow(2, treeDepth + 1) - 1;
		for (int i = 0; i < this.treeNum; i++) {
			Tree rtw = new Tree(i, treeDepth, treeNodeNum);
			treeList.add(rtw);
		}
	}

	private LossFucntion generateLossFunction(String lossfunction) {
		LossFucntion func = null;
		if (lossfunction == null) {
			System.out.println("Not select loss!!");
			return func;
		}
		if (lossfunction.equals("SquareError")) {
			func = new SquareError();
		}
		return func;
	}

	public void nextStep() {
		this.curLevel = this.curLevel + 1;
		if (this.curLevel > LocalConfig.TREE_DEPTHD) {
			curLevel = 1;
			this.curTreeIndex = this.curTreeIndex + 1;
		}
		setStartTreeNodeIndex();
		setEndTreeNodeIndex();
	}

	public void setStartTreeNodeIndex() {
		this.startTreeNodeIndex = (int) (Math.pow(2, this.curLevel - 1));
	}

	public void setEndTreeNodeIndex() {
		this.endTreeNodeIndex = (int) (Math.pow(2, this.curLevel) - 1);
	}

	public Set<Integer> getTreeNodeSet() {
		Set<Integer> out = new HashSet<Integer>();
		for (int curTreeNodeIndex = startTreeNodeIndex; curTreeNodeIndex <= endTreeNodeIndex; curTreeNodeIndex++) {
			out.add(curTreeNodeIndex);
		}
		return out;
	}

	public Tree getCurrentTree() {
		return treeList.get(this.curTreeIndex);
	}

	public Tree getTree(int treeIndex) {
		return treeList.get(treeIndex);
	}

	public void setTree(int treeIndex, Tree tree) {
		treeList.set(treeIndex, tree);
	}

	public int getCurTreeIndex() {
		return curTreeIndex;
	}

	public void setCurTreeIndex(int cutTreeIndex) {
		this.curTreeIndex = cutTreeIndex;
	}

	public int getCurLevel() {
		return curLevel;
	}

	public void setCurLevel(int curLevel) {
		this.curLevel = curLevel;
	}

	public int getStartTreeNodeIndex() {
		return startTreeNodeIndex;
	}

	public void setStartTreeNodeIndex(int startTreeNodeIndex) {
		this.startTreeNodeIndex = startTreeNodeIndex;
	}

	public int getEndTreeNodeIndex() {
		return endTreeNodeIndex;
	}

	public void setEndTreeNodeIndex(int endTreeNodeIndex) {
		this.endTreeNodeIndex = endTreeNodeIndex;
	}

	public LossFucntion getLossFunction() {
		return lossFunction;
	}

	public void setLossFunction(LossFucntion lossFunction) {
		this.lossFunction = lossFunction;
	}

	public String getLossFunctionName() {
		return lossFunctionName;
	}

	public void setLossFunctionName(String lossFunctionName) {
		this.lossFunctionName = lossFunctionName;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public double getLoss() {
		return loss;
	}

	public void setLoss(double loss) {
		this.loss = loss;
	}

	public double getShrinkage() {
		return shrinkage;
	}

	public void setShrinkage(double shrinkage) {
		this.shrinkage = shrinkage;
	}

	public int getTreeDepth() {
		return treeDepth;
	}

	public void setTreeDepth(int treeDepth) {
		this.treeDepth = treeDepth;
	}

	public int getTreeNum() {
		return treeNum;
	}

	public void setTreeNum(int treeNum) {
		this.treeNum = treeNum;
	}

	public List<Tree> getTreeList() {
		return treeList;
	}

	public void setTreeList(List<Tree> treeList) {
		this.treeList = treeList;
	}

	public Map<Integer, FeatureImportance> getFeatureImportance() {
		return featureImportance;
	}

	public void setFeatureImportance(Map<Integer, FeatureImportance> featureImportance) {
		this.featureImportance = featureImportance;
	}

	public void saveModel(String modelSavePath) {
		try {
			File file = new File(modelSavePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(modelSavePath);
			bufferWritter.close();
			System.out.println("Model Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadModel(String modelLoadPath) {
		
	}

}
