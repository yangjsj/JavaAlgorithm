package IsotonicRegression;

public class IsotonicNode {

	private double x=0;
	private double y=0;
	private double weight=1;
	private double min_x=0;
	private double max_x=0;
	
	public IsotonicNode(double nx,double ny)
	{
		this.x=nx;
		this.y=ny;
		this.max_x=nx;
		this.min_x=nx;
		this.weight=1;
	}
	public IsotonicNode(double nx,double ny,double nweight)
	{
		this.x=nx;
		this.y=ny;
		this.max_x=nx;
		this.min_x=nx;
		this.weight=nweight;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getMin_x() {
		return min_x;
	}
	public void setMin_x(double min_x) {
		this.min_x = min_x;
	}
	public double getMax_x() {
		return max_x;
	}
	public void setMax_x(double max_x) {
		this.max_x = max_x;
	}
	
	
}
