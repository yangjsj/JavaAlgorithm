package classify.randomforest.model;

public class LocalConfig {
	public static final String Separator = "@";
	public static double MAX_VALUE = Double.MAX_VALUE;
	public static int startIdx = 0;
	public static int TREE_NUM = 0;
	public static int TREE_DEPTHD = 0;
	public static int MIN_LEAFS_NUM = 20;
	public static double SHRINKAGE = 0.1;
	public static double FEATURE_SELECT_RATE = 0.1;
	public static double DATE_SELELCT_RATE = 0.1;
	public static double REGULARIZATION = 0.1;
	public static double EPS = 0.1;
	public static String LOSS_FUNCTION = null;
	public static int threadsNum = -1;
	public static boolean normalizedLabel = false;

	public static void setup(String parameterStr)

	{
		String parameters[] = parameterStr.split(Separator);
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].split(":")[0].equals("threadsNum"))
				threadsNum = Integer.parseInt(parameters[i].split(":")[1]);
			if (parameters[i].split(":")[0].equals("startIdx"))
				startIdx = Integer.parseInt(parameters[i].split(":")[1]);
			if (parameters[i].split(":")[0].equals("TREE_DEPTHD"))
				TREE_DEPTHD = Integer.parseInt(parameters[i].split(":")[1]);
			if (parameters[i].split(":")[0].equals("MIN_LEAFS_NUM"))
				MIN_LEAFS_NUM = Integer.parseInt(parameters[i].split(":")[1]);
			if (parameters[i].split(":")[0].equals("SHRINKAGE"))
				SHRINKAGE = Double.parseDouble(parameters[i].split(":")[1]);
			if (parameters[i].split(":")[0].equals("FEATURE_SELECT_RATE"))
				FEATURE_SELECT_RATE = Double.parseDouble(parameters[i].split(":")[1]);
			if (parameters[i].split(":")[0].equals("DATE_SELELCT_RATE"))
				DATE_SELELCT_RATE = Double.parseDouble(parameters[i].split(":")[1]);
			if (parameters[i].split(":")[0].equals("REGULARIZATION"))
				REGULARIZATION = Double.parseDouble(parameters[i].split(":")[1]);
			if (parameters[i].split(":")[0].equals("LOSS_FUNCTION"))
				LOSS_FUNCTION = parameters[i].split(":")[1];
			if (parameters[i].split(":")[0].equals("normalizedLabel"))
				normalizedLabel = Boolean.parseBoolean(parameters[i].split(":")[1]);

		}
	}
}
