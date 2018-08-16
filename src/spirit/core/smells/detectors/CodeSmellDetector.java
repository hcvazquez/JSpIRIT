package spirit.core.smells.detectors;

import spirit.core.smells.CodeSmell;
import spirit.metrics.storage.NodeMetrics;

public abstract class CodeSmellDetector {
	
	public abstract boolean codeSmellVerify(NodeMetrics nodeMetrics);
	
	public abstract CodeSmell codeSmellDetected(NodeMetrics nodeMetrics);
	
}
