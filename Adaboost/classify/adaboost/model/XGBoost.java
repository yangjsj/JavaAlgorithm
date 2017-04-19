package classify.adaboost.model;

import classify.adaboost.entity.Corpus;
import classify.adaboost.entity.Model;

public class XGBoost {

	public static void main(String args[]) {
		XGBoost xbgoost = new XGBoost();
		String trainFileName = "";
		String modelSavePath = "";
		xbgoost.train(trainFileName, modelSavePath);
	}

	public void train(String trainFileName, String modelSavePath) {
		Corpus corpus = new Corpus();
		corpus.readTrainData(trainFileName);
		System.out.println("init date end ...");
		XGBoostTrain xgboost = new XGBoostTrain(corpus);
		Model model = xgboost.xgboostRun();
		System.out.println("train model end ...");
		model.saveModel(modelSavePath);
	}

	public void predict(String predictFileName, String modelLoadPath) {
		Model model =new Model();
		model.loadModel(modelLoadPath);
		
	}
}
