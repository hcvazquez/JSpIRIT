package bandago.utils;

public class Parameters {
	
	// PARAMETROS EN BASE DE DATOS? ARCHIVO? VALE LA PENA?
	
	//----------Simulated Annealing Parameters
	
	private static int MSTE = 3; //Minumum Statements to Extract
	private static double TMP = 10; //Temperature
	private static int MDI = 50; //Max deep iterations
	private static int NOS = 3; //Number of solutions
	private static double CDF = 0.9; //Cooldown factor
	private static long TPS = 30000000000L; //Time Per Solution
	private static double BCR = 2; //Benefit/Cost Ratio
	
	public static final int DMSTE = 3; //Default Minumum Statements to Extract
	public static final double DTMP = 10; //Default Temperature
	public static final int DMDI = 50; //Default Max deep iterations
	public static final int DNOS = 3; //Default Number of solutions
	public static final double DCDF = 0.9; //Default Cooldown factor
	public static final long DTPS = 30000000000L; //Default Time Per Solution
	public static final double DBCR = 2; //Default Benefit/Cost Ratio
	
	
	public static int getMSTE() {
		return MSTE;
	}
	public static void setMSTE(int mSTE) {
		MSTE = mSTE;
	}
	public static double getTMP() {
		return TMP;
	}
	public static void setTMP(double tMP) {
		TMP = tMP;
	}
	public static int getMDI() {
		return MDI;
	}
	public static void setMDI(int mDI) {
		MDI = mDI;
	}
	public static int getNOS() {
		return NOS;
	}
	public static void setNOS(int nOS) {
		NOS = nOS;
	}
	public static double getCDF() {
		return CDF;
	}
	public static void setCDF(double cDF) {
		CDF = cDF;
	}
	public static long getTPS(){
		return TPS;
	}
	public static void setTPS(long tPS){
		TPS = tPS;
	}
	
	public static long nanoToSec(long nano) {
		return (nano/1000000000L);
	}
	
	public static long secToNano(long sec) {
		return (sec*1000000000L);
	}
	public static double getBCR() {
		return BCR;
	}
	public static void setBCR(double bCR) {
		BCR = bCR;
	}

}
