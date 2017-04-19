package classify.adaboost.lossFunction;

import classify.adaboost.entity.Instance;
import classify.adaboost.impurity.GiniImpurity;

public class SquareError extends LossFucntion {

	public SquareError() {
		this.impurity = new GiniImpurity();
	}

	@Override
	public double getLoss(double label, double predict) {
		return Math.pow(label - predict, 2);
	}

	@Override
	public double getPseudoRes(double label, double predict) {
		return (label - predict);
	}

	@Override
	public double getPseudoRes2times(double label, double predict) {
		return 1;
	}

	@Override
	public double getInvLink(double predict) {
		return predict;
	}

	@Override
	public void setTarget(double predict, Instance instance) {
		double g = getPseudoRes(instance.getLabel(), predict);
		double h = getPseudoRes2times(instance.getLabel(), predict);
		instance.setGH(g, h);
	}

}
