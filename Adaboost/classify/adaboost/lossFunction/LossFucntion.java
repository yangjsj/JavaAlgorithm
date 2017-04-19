package classify.adaboost.lossFunction;

import classify.adaboost.entity.Instance;
import classify.adaboost.impurity.Impurity;

public abstract class LossFucntion {

	protected Impurity impurity = null;

	public Impurity getImpurity() {
		return this.impurity;
	}

	abstract public double getLoss(double label, double predict);

	// negative gradient to predict
	abstract public double getPseudoRes(double label, double predict);

	// gradient to predict 2 times
	abstract public double getPseudoRes2times(double label, double predict);

	abstract public double getInvLink(double predict);

	abstract public void setTarget(double predict, Instance instance);

}
