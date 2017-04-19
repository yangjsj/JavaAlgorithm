package classify.randomforest.model;

import classify.randomforest.entity.Corpus;
import classify.randomforest.entity.Model;

public class RandomForest {

	public static void main(String args[]) {
		RandomForest xbgoost = new RandomForest();
		String trainFileName = "";
		String modelSavePath = "";
		xbgoost.train(trainFileName, modelSavePath);
	}

	public void train(String trainFileName, String modelSavePath) {
		Corpus corpus = new Corpus();
		corpus.readTrainData(trainFileName);
		System.out.println("init date end ...");
		RandomForestTrain xgboost = new RandomForestTrain(corpus);
		Model model = xgboost.xgboostRun();
		System.out.println("train model end ...");
		model.saveModel(modelSavePath);
	}

	public void predict(String predictFileName, String modelLoadPath) {
		Model model =new Model();
		model.loadModel(modelLoadPath);
		
	}
}
