package classify.adaboost.utils;

import java.util.List;

public class Evalutions {

	int size = 0;
	double predict[] = null;
	double target[] = null;
	double mae = 0;
	double mare = 0;
	double rmse = 0;
	double sperman = 0;
	double logPerson = 0;
	double gini = 0;
	double giniRank = 0;
	double auc = 0;

	public Evalutions(List<Double> P, List<Double> Y) {
		this.size = P.size();
		predict = new double[this.size];
		target = new double[this.size];
		for (int i = 0; i < this.size; i++) {
			predict[i] = P.get(i);
			target[i] = Y.get(i);
		}

	}

	public double mae() {
		double sumTarget = 0;
		double sumGap = 0;
		for (int i = 0; i < size; i++) {
			sumTarget += Math.abs(target[i]);
			sumGap += Math.abs(predict[i] - target[i]);
		}
		return (sumGap / sumTarget);
	}

	public double mare() {
		double sumGap = 0;
		double n = 0;
		for (int i = 0; i < size; i++) {
			if (target[i] > 0) {
				n++;
				sumGap += Math.abs(predict[i] - target[i]) / target[i];
			}
		}
		return (sumGap / n);
	}

	public double rmse() {
		double sumGap = 0;
		double sumTarget = 0;
		for (int i = 0; i < size; i++) {
			sumTarget += Math.pow(target[i], 2);
			sumGap += Math.pow(predict[i] - target[i], 2);
		}
		return (sumGap / sumTarget);
	}
	
	public double Sperman()
	{
		//double pi[]=rank(predict);
		//double yi[]=rank(target);
		//return Pearson(pi,yi);
		return 0;
	}
	private double  rank(double list[])
	{
		return 0;
	}
}
