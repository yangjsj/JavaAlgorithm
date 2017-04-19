package IsotonicRegression;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

public class IsotonicRegression {

	public TreeMap<Double, IsotonicNode> model = new TreeMap<Double, IsotonicNode>();

	public TreeMap<Double, IsotonicNode> isotonicRegressionTrain(List<IsotonicNode> ls) {
		Stack<IsotonicNode> stack = new Stack<IsotonicNode>();
		NodeComparator nc = new NodeComparator();
		Collections.sort(ls, nc);
		stack.push(ls.get(0));
		for (int i = 1; i < ls.size(); i++) {
			IsotonicNode curnode = ls.get(i);
			while (!stack.isEmpty() && curnode.getY() < stack.peek().getY()) {
				IsotonicNode last = stack.pop();
				curnode = mergerNode(curnode, last);
			}
			stack.push(curnode);
		}
		// print
		for (IsotonicNode obj : stack) {
			if (model.containsKey(obj.getY())) {
				IsotonicNode curnode = model.get(obj.getY());
				curnode.setMax_x(curnode.getMax_x() > obj.getMax_x() ? curnode.getMax_x() : obj.getMax_x());
				curnode.setMin_x(curnode.getMin_x() < obj.getMin_x() ? curnode.getMin_x() : obj.getMin_x());
				model.put(obj.getY(), curnode);
			} else {
				model.put(obj.getY(), obj);
			}
		}
		// print result
		for (Double obj : model.keySet()) {
			System.out.println(model.get(obj).toString());
		}
		return model;
	}

	IsotonicNode mergerNode(IsotonicNode n1, IsotonicNode n2) {
		double nweight = n1.getWeight() + n2.getWeight();
		double nx = (n1.getX() * n1.getWeight() + n2.getX() * n2.getWeight()) / (n1.getWeight() + n2.getWeight());
		double ny = (n1.getY() * n1.getWeight() + n2.getY() * n2.getWeight()) / (n1.getWeight() + n2.getWeight());
		IsotonicNode out = new IsotonicNode(nx, ny, nweight);
		out.setMax_x(n1.getMax_x() > n2.getMax_x() ? n1.getMax_x() : n2.getMax_x());
		out.setMin_x(n1.getMax_x() < n2.getMax_x() ? n1.getMax_x() : n2.getMax_x());
		return out;
	}

	class NodeComparator implements Comparator<IsotonicNode> {
		public int compare(IsotonicNode o1, IsotonicNode o2) {
			if (o1.getX() > o2.getX()) {
				return 1;
			}
			return -1;
		}
	}

	public static void main(String[] args) {
		IsotonicRegression ir = new IsotonicRegression();
		double indata[][] = { { 1, 2 }, { 2, 9 }, { 3, 5 }, { 4, 3 }, { 5, 5 }, { 7, 7 } };
		List<IsotonicNode> ls = new LinkedList<IsotonicNode>();
		for (int i = 0; i < indata.length; i++) {
			ls.add(new IsotonicNode(indata[i][0], indata[i][1]));
		}
		ir.isotonicRegressionTrain(ls);
	}
}
