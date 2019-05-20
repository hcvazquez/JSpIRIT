package spirit.metrics.constants;

public class MetricThresholds {

	public static final double FEW = 2; //Access To Foreign Data
	
	public static final double ONE_THIRD = 0.333;
	public static final double TWO_THIRD = 0.666;
	public static final double HALF = 0.5;
	
	private static final double cycloLOCHigh = 0.25;
	private static final double LOCNONHigh = 13;
	private static final double NONNOCHigh = 9;
	public static final double WMC_HIGH = cycloLOCHigh * LOCNONHigh * NONNOCHigh;
	private static final double OUTLIER_FACTOR = 1.5;
	
	public static final double WMC_VERY_HIGH = WMC_HIGH * OUTLIER_FACTOR; //Weigth method cyclomatic
	
	public static final double LOCVeryHigh = LOCNONHigh * NONNOCHigh * (OUTLIER_FACTOR - 1.0); //Method line of code Very High
	public static final double LOCClassVeryHigh = LOCNONHigh * NONNOCHigh * OUTLIER_FACTOR; //Class lines of code Very High
	
	public static final double MLOC_VERY_HIGH = 40;
	
	public static final double DEEP = 3;//3
	public static final double MANY = 4;//4
	
	public static final double SMemCap = 7;
	
	public static final double SHALLOW = 1;

	public static final double ONE_QUARTER = 0.25;

	private static final double cycloLOCAvg = 0.21;
	private static final double LOCNONAvg = 9;
	private static final double NONNOCAvg = 7;
	
	public static final double AMWAvg = cycloLOCAvg * LOCNONAvg;
	public static final double WMCAvg = cycloLOCAvg * LOCNONAvg * NONNOCAvg;	
	public static final double NOMAvg = NONNOCAvg;

	public static final double NOMHigh = NONNOCHigh;
	
}
