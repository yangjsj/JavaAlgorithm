package classify.randomforest.impurity;

public class GiniImpurity extends Impurity {

	@Override
	public double calPred(double s, double c) {
		return (c != 0 ? s / c : 0);
	}

	@Override
	public double calResidue(double t, double c) {
		return c > 0 ? t * t / c : 0;
	}

	@Override
	public double calFitness(double t, double c) {
		return c > 0 ? -t * t / c : 0;
	}

}
