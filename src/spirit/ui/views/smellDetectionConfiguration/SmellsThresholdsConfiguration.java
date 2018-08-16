package spirit.ui.views.smellDetectionConfiguration;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import spirit.core.design.CodeSmellsManager;
import spirit.core.design.CodeSmellsManagerFactory;
import spirit.core.design.DesignFlaw;
import spirit.core.smells.detectors.configurationByProject.BrainClassDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.BrainMethodDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.DataClassDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.DispersedCouplingDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.FeatureEnvyDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.GodClassDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.IntensiveCouplingDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.RefusedParentBequestDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.ShotgunSurgeryDetectionConfiguration;
import spirit.core.smells.detectors.configurationByProject.TraditionBreakerDetectionConfiguration;
import spirit.db.DataBaseManager;
import spirit.priotization.RankingManagerForSmells;
import spirit.ui.views.SpIRITSmellsView;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.hibernate.metamodel.relational.Database;

public class SmellsThresholdsConfiguration extends Dialog {
	private List list;
	private SpIRITSmellsView spIRITView;
	private Composite container;
	private static int xFirstIndentation=202;
	private static int xSecondIndentation=212;
	private static int xThirdIndentation=222;
	private Vector<Label> currentLabels;
	private Vector<Text> currentTexts;
	private String currentSelection;
	
	private BrainClassDetectionConfiguration brainClassConf;
	private BrainMethodDetectionConfiguration brainMethodConf;
	private DataClassDetectionConfiguration dataClassConf;
	private DispersedCouplingDetectionConfiguration dipersedCouplingConf;
	private FeatureEnvyDetectionConfiguration featureEnvyConf;
	private GodClassDetectionConfiguration godClassConf;
	private IntensiveCouplingDetectionConfiguration intensiveCouplingConf;
	private RefusedParentBequestDetectionConfiguration refusedParentBequestConf;
	private ShotgunSurgeryDetectionConfiguration shotgunSurgeryConf;
	private TraditionBreakerDetectionConfiguration traditionBreakerConf;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SmellsThresholdsConfiguration(Shell parentShell, SpIRITSmellsView spIRITView) {
		super(parentShell);
		this.spIRITView=spIRITView;
		String projectName=RankingManagerForSmells.getInstance().getCurrentProject();
		brainClassConf=DataBaseManager.getInstance().getBrainClassDetectionConfiguration(projectName);
		brainMethodConf=DataBaseManager.getInstance().getBrainMethodDetectionConfiguration(projectName);
		dataClassConf=DataBaseManager.getInstance().getDataClassDetectionConfiguration(projectName);
		dipersedCouplingConf=DataBaseManager.getInstance().getDispersedCouplingDetectionConfiguration(projectName);
		featureEnvyConf=DataBaseManager.getInstance().getFeatureEnvyDetectionConfiguration(projectName);
		godClassConf=DataBaseManager.getInstance().getGodClassDetectionConfiguration(projectName);
		intensiveCouplingConf=DataBaseManager.getInstance().getIntensiveCouplingDetectionConfiguration(projectName);
		refusedParentBequestConf=DataBaseManager.getInstance().getRefusedParentBequestDetectionConfiguration(projectName);
		shotgunSurgeryConf=DataBaseManager.getInstance().getShotgunSurgeryDetectionConfiguration(projectName);
		traditionBreakerConf=DataBaseManager.getInstance().getTraditionBreakerDetectionConfiguration(projectName);
		currentSelection=null;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		list = new List(container, SWT.BORDER);
		
		
		list.setBounds(10, 10, 170, 215);
		list.add("Brain Class");
		list.add("Brain Method");
		list.add("Data Class");
		list.add("Dispersed Coupling");
		list.add("Feature Envy");
		list.add("God Class");
		list.add("Intensive Coupling");
		list.add("Refused Parent Bequest");
		list.add("Shotgun Surgery");
		list.add("Tradition Breaker");
		
		currentLabels=new Vector<Label>();
		currentTexts=new Vector<Text>();
		
		/*list.addListener(SWT.Selection, new Listener() {
	            @Override
	            public void handleEvent(Event arg0) {
	                if(printerList.getSelectionCount() > 0)
	                    System.out.println(Arrays.toString(printerList.getSelection()));
	            }
	        });*/
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				smellSelected();
			}
		});
		return container;
	}
	private void smellSelected(){
		if(list.getSelectionCount()==1){
			//SAVE AND REMOVE
			saveValues();
			removeControls();
			
			currentSelection=list.getSelection()[0];
			if(currentSelection.equals("Brain Class")){
				loadControlsForBrainClass();
			}else{
				if(currentSelection.equals("Brain Method")){
					loadControlsForBrainMethod();
				}else{
					if(currentSelection.equals("Data Class")){
						loadControlsForDataClass();
					}else{
						if(currentSelection.equals("Dispersed Coupling")){
							loadControlsForDispersedCoupling();
						}else{
							if(currentSelection.equals("Feature Envy")){
								loadControlsForFeatureEnvy();
							}else{
								if(currentSelection.equals("God Class")){
									loadControlsForGodClass();
								}else{
									if(currentSelection.equals("Intensive Coupling")){
										loadControlsForIntensiveCoupling();
									}else{
										if(currentSelection.equals("Refused Parent Bequest")){
											loadControlsForRefusedParentBequest();
										}else{
											if(currentSelection.equals("Shotgun Surgery")){
												loadControlsForShotgunSurgery();
											}else{
												if(currentSelection.equals("Tradition Breaker")){
													loadControlsForTraditionBreaker();
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	

	private void removeControls() {
		
		for (Iterator<Label> iterator = currentLabels.iterator(); iterator.hasNext();) {
			Label label = (Label) iterator.next();
			label.dispose();
		}
		currentLabels.clear();
		for (Iterator<Text> iterator = currentTexts.iterator(); iterator.hasNext();) {
			Text text = (Text) iterator.next();
			text.dispose();
		}
		currentTexts.clear();
		//container.pack();
	}

	private void saveValues() {
		if(currentSelection!=null){
			if(currentSelection.equals("Brain Class")){
				brainClassConf.setLOC_GreaterEqual_VeryHigh(new Double(currentTexts.get(0).getText()));
				brainClassConf.setLOC_GreaterEqual_2xVeryHigh(new Double(currentTexts.get(1).getText()));
				brainClassConf.setWMC_GreaterEqual_2xVeryHigh(new Double(currentTexts.get(2).getText()));
				brainClassConf.setWMC_GreaterEqual_VeryHigh(new Double(currentTexts.get(3).getText()));
				brainClassConf.setTCC_Less_Half(new Double(currentTexts.get(4).getText()));
			}else{
				if(currentSelection.equals("Brain Method")){
					brainMethodConf.setLOC_Greater_VeryHigh(new Double(currentTexts.get(0).getText()));
					brainMethodConf.setWMC_GreaterEqual_Many(new Double(currentTexts.get(1).getText()));
					brainMethodConf.setMAXNESTING_GreaterEqual_DEEP(new Double(currentTexts.get(2).getText()));
					brainMethodConf.setNOF_GreaterEqual_SMemCap(new Double(currentTexts.get(3).getText()));
				}else{
					if(currentSelection.equals("Data Class")){
						dataClassConf.setWOC_Less_OneThird(new Double(currentTexts.get(0).getText()));
						dataClassConf.setNOAP_SOAP_Greater_Few(new Double(currentTexts.get(1).getText()));
						dataClassConf.setWMC_Less_High(new Double(currentTexts.get(2).getText()));
						dataClassConf.setNOAP_SOAP_Greater_Many(new Double(currentTexts.get(3).getText()));
						dataClassConf.setWMC_Less_VeryHigh(new Double(currentTexts.get(4).getText()));
					}else{
						if(currentSelection.equals("Dispersed Coupling")){
							dipersedCouplingConf.setCINT_Greater_SMemCap(new Double(currentTexts.get(0).getText()));
							dipersedCouplingConf.setCDISP_GreaterEqual_Half(new Double(currentTexts.get(1).getText()));
							dipersedCouplingConf.setMAXNESTING_Greater_Shallow(new Double(currentTexts.get(2).getText()));
						}else{
							if(currentSelection.equals("Feature Envy")){
								featureEnvyConf.setATFD_Greater_Few(new Double(currentTexts.get(0).getText()));
								featureEnvyConf.setLAA_Less_OneThird(new Double(currentTexts.get(1).getText()));
								featureEnvyConf.setFDP_LessEqual_FEW(new Double(currentTexts.get(2).getText()));
							}else{
								if(currentSelection.equals("God Class")){
									godClassConf.setATFD_Greater_FEW(new Double(currentTexts.get(0).getText()));
									godClassConf.setWMC_GreaterEqual_VeryHigh(new Double(currentTexts.get(1).getText()));
									godClassConf.setTCC_Less_OneThird(new Double(currentTexts.get(2).getText()));
								}else{
									if(currentSelection.equals("Intensive Coupling")){
										intensiveCouplingConf.setCINT_Greater_SMemCap(new Double(currentTexts.get(0).getText()));
										intensiveCouplingConf.setCDISP_Less_Half(new Double(currentTexts.get(1).getText()));
										intensiveCouplingConf.setCINT_Greater_Few(new Double(currentTexts.get(2).getText()));
										intensiveCouplingConf.setCDISP_Less_OneQuarter(new Double(currentTexts.get(3).getText()));
										intensiveCouplingConf.setMAXNESTING_Greater_SHALLOW(new Double(currentTexts.get(4).getText()));
									}else{
										if(currentSelection.equals("Refused Parent Bequest")){
											refusedParentBequestConf.setNProtM_Greater_Few(new Double(currentTexts.get(0).getText()));
											refusedParentBequestConf.setBUR_Less_OneThird(new Double(currentTexts.get(1).getText()));
											refusedParentBequestConf.setBOvR_Less_OneThird(new Double(currentTexts.get(2).getText()));
											refusedParentBequestConf.setAMW_Greater_AMWAvg(new Double(currentTexts.get(3).getText()));
											refusedParentBequestConf.setWMC_Greater_WMCAvg(new Double(currentTexts.get(4).getText()));
											refusedParentBequestConf.setNOM_Greater_NOMAvg(new Double(currentTexts.get(5).getText()));
										}else{
											if(currentSelection.equals("Shotgun Surgery")){
												shotgunSurgeryConf.setCM_Greater_SMemCap(new Double(currentTexts.get(0).getText()));
												shotgunSurgeryConf.setCC_GreaterEqual_MANY(new Double(currentTexts.get(1).getText()));
											}else{
												if(currentSelection.equals("Tradition Breaker")){
													traditionBreakerConf.setNAS_GreaterEqual_NOMAvg(new Double(currentTexts.get(0).getText()));
													traditionBreakerConf.setPNAS_GreaterEqual_TWO_THIRD(new Double(currentTexts.get(1).getText()));
													traditionBreakerConf.setAMW_Greater_AMWAvg(new Double(currentTexts.get(2).getText()));
													traditionBreakerConf.setWMC_GreaterEqual_VeryHigh(new Double(currentTexts.get(3).getText()));
													traditionBreakerConf.setNOM_GreatherEqual_High(new Double(currentTexts.get(4).getText()));
													traditionBreakerConf.setAMW_Greater_AMWAvg_2(new Double(currentTexts.get(5).getText()));
													traditionBreakerConf.setNOM_GreatherEqual_HighDiv2(new Double(currentTexts.get(6).getText()));
													traditionBreakerConf.setWMC_Greater_VeryHighDiv2(new Double(currentTexts.get(7).getText()));
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void loadControlsForBrainMethod() {
		createLabel(xFirstIndentation, 15, 347,"Method is excessively large");
		createLabel(xSecondIndentation, 35, 67,"LOC >");
		createText(35);
		createLabel(354, 35, 59,"AND");
		createLabel(xFirstIndentation, 55, 347,"Method has many conditional branches");
		createLabel(xSecondIndentation, 75, 67,"CYCLO >=");
		createText(75);
		createLabel(354, 75, 59,"AND");
		
		createLabel(xFirstIndentation, 95, 347,"Method has deep nesting");
		createLabel(xSecondIndentation, 115, 100,"MAXNESTING >=");
		createText(315,115);
		createLabel(389, 115, 59,"AND");
		
		createLabel(xFirstIndentation, 135, 347,"Method uses many variables");
		createLabel(xSecondIndentation, 155, 67,"NOAV >");
		createText(155);
		
		currentTexts.get(0).setText(new Double(brainMethodConf.getLOC_Greater_VeryHigh()).toString());
		currentTexts.get(1).setText(new Double(brainMethodConf.getWMC_GreaterEqual_Many()).toString());
		currentTexts.get(2).setText(new Double(brainMethodConf.getMAXNESTING_GreaterEqual_DEEP()).toString());
		currentTexts.get(3).setText(new Double(brainMethodConf.getNOF_GreaterEqual_SMemCap()).toString());
		
	}

	private void loadControlsForBrainClass() {
		 
		createLabel(xFirstIndentation, 15, 347,"(Class contains more than one Brain Method AND");
		createLabel(xSecondIndentation, 35, 337,"total size of methods in class is very high");
		createLabel(xThirdIndentation, 55, 67,"LOC >=");
		
		createText(55);
		
		createLabel(xFirstIndentation, 75, 59, "OR");
		createLabel(xFirstIndentation, 95, 347,"Class contains only one Brain Method AND");
		createLabel(xSecondIndentation, 115, 337,"total size of methods in class is extremely high");
		createLabel(xThirdIndentation, 135, 59,"LOC >=");
		
		createText(135);
		
		createLabel(354, 135, 59,"AND");
		
		createLabel(xSecondIndentation, 155, 337,"functional complexity of class is extremely high");
		createLabel(xThirdIndentation, 175, 59,"WMC >=");
		
		createText(175);
		
		createLabel(354, 175, 59,")");
		createLabel(xFirstIndentation, 195, 59,"AND");
		createLabel(xFirstIndentation, 215, 347,"Functional complexity of class is very high");
		createLabel(xThirdIndentation, 235, 59,"WMC >=");
		
		createText(235);
		
		createLabel(354, 235, 59,"AND");
		createLabel(xSecondIndentation, 255, 281,"Class cohesion is low");
		createLabel(xThirdIndentation, 275, 59,"TCC <");

		createText(275);
		
		currentTexts.get(0).setText(new Double(brainClassConf.getLOC_GreaterEqual_VeryHigh()).toString());
		currentTexts.get(1).setText(new Double(brainClassConf.getLOC_GreaterEqual_2xVeryHigh()).toString());
		currentTexts.get(2).setText(new Double(brainClassConf.getWMC_GreaterEqual_2xVeryHigh()).toString());
		currentTexts.get(3).setText(new Double(brainClassConf.getWMC_GreaterEqual_VeryHigh()).toString());
		currentTexts.get(4).setText(new Double(brainClassConf.getTCC_Less_Half()).toString());
		
	}
	private void loadControlsForDataClass() {
		createLabel(xFirstIndentation, 15, 347,"Interface of class reveals data rather than offering services");
		createLabel(xSecondIndentation, 35, 67,"WOC <");
		createText(35);
		createLabel(354, 35, 59,"AND");
		createLabel(xFirstIndentation, 55, 347,"(More than a few public data");
		createLabel(xSecondIndentation, 75, 100,"NOAP + NOAM >");
		createText(315,75);
		createLabel(389, 75, 59,"AND");
		createLabel(xFirstIndentation, 95, 347,"Complexity of class is not high");
		createLabel(xSecondIndentation, 115, 67,"WMC <");
		createText(115);
		createLabel(xFirstIndentation, 135, 59,"OR");
		
		createLabel(xFirstIndentation, 155, 347,"Class has many public data");
		createLabel(xSecondIndentation, 175, 100,"NOAP + NOAM >");
		createText(315,175);
		createLabel(389, 175, 59,"AND");
		createLabel(xFirstIndentation, 195, 347,"Complexity of class is not very high");
		createLabel(xSecondIndentation, 215, 67,"WMC <");
		createText(215);
		createLabel(354, 215, 59,")");
		
		currentTexts.get(0).setText(new Double(dataClassConf.getWOC_Less_OneThird()).toString());
		currentTexts.get(1).setText(new Double(dataClassConf.getNOAP_SOAP_Greater_Few()).toString());
		currentTexts.get(2).setText(new Double(dataClassConf.getWMC_Less_High()).toString());
		currentTexts.get(3).setText(new Double(dataClassConf.getNOAP_SOAP_Greater_Many()).toString());
		currentTexts.get(4).setText(new Double(dataClassConf.getWMC_Less_VeryHigh()).toString());
		
	}
	private void loadControlsForDispersedCoupling() {
		createLabel(xFirstIndentation, 15, 347,"Operation calls too many method");
		createLabel(xSecondIndentation, 35, 67,"CINT >");
		createText(35);
		createLabel(354, 35, 59,"AND");
		createLabel(xFirstIndentation, 55, 347,"Calls are dispersed in many classes");
		createLabel(xSecondIndentation, 75, 67,"CDISP >=");
		createText(75);
		createLabel(354, 75, 59,"AND");
		
		createLabel(xFirstIndentation, 95, 347,"Operation has few nested conditionals");
		createLabel(xSecondIndentation, 115, 100,"MAXNESTING >");
		createText(315,115);
		
		currentTexts.get(0).setText(new Double(dipersedCouplingConf.getCINT_Greater_SMemCap()).toString());
		currentTexts.get(1).setText(new Double(dipersedCouplingConf.getCDISP_GreaterEqual_Half()).toString());
		currentTexts.get(2).setText(new Double(dipersedCouplingConf.getMAXNESTING_Greater_Shallow()).toString());
	}
	private void loadControlsForFeatureEnvy() {
		createLabel(xFirstIndentation, 15, 397,"Method uses directly more than a few attributes of other classes");
		createLabel(xSecondIndentation, 35, 67,"ATFD >");
		createText(35);
		createLabel(354, 35, 59,"AND");
		createLabel(xFirstIndentation, 55, 397,"Method uses far more attributes of other classes than its own");
		createLabel(xSecondIndentation, 75, 67,"LAA <");
		createText(75);
		createLabel(354, 75, 59,"AND");
		
		createLabel(xFirstIndentation, 95, 397,"The used foreign attributes belong to very few other classes");
		createLabel(xSecondIndentation, 115, 67,"FDP <=");
		createText(115);
		
		currentTexts.get(0).setText(new Double(featureEnvyConf.getATFD_Greater_Few() ).toString());
		currentTexts.get(1).setText(new Double(featureEnvyConf.getLAA_Less_OneThird() ).toString());
		currentTexts.get(2).setText(new Double(featureEnvyConf.getFDP_LessEqual_FEW() ).toString());
		
	}
	private void loadControlsForGodClass() {
		createLabel(xFirstIndentation, 15, 397,"Class uses directly more than a few attributes of other classes");
		createLabel(xSecondIndentation, 35, 67,"ATFD >");
		createText(35);
		createLabel(354, 35, 59,"AND");
		createLabel(xFirstIndentation, 55, 397,"Functional complexity of the class is very high");
		createLabel(xSecondIndentation, 75, 67,"WMC >=");
		createText(75);
		createLabel(354, 75, 59,"AND");
		
		createLabel(xFirstIndentation, 95, 397,"Class cohesion is low");
		createLabel(xSecondIndentation, 115, 67,"TCC <");
		createText(115);
		
		currentTexts.get(0).setText(new Double(godClassConf.getATFD_Greater_FEW() ).toString());
		currentTexts.get(1).setText(new Double(godClassConf.getWMC_GreaterEqual_VeryHigh() ).toString());
		currentTexts.get(2).setText(new Double(godClassConf.getTCC_Less_OneThird() ).toString());
		
	}
	
	private void loadControlsForIntensiveCoupling() {
		createLabel(xFirstIndentation, 15, 347,"(Operation calls to many methods");
		createLabel(xSecondIndentation, 35, 67,"CINT >");
		createText(35);
		createLabel(354, 35, 59,"AND");
		createLabel(xFirstIndentation, 55, 347,"Calls are dispersed in few classes");
		createLabel(xSecondIndentation, 75, 67,"CDISP <");
		createText(75);
		
		createLabel(xFirstIndentation, 95, 59,"OR");
		
		createLabel(xFirstIndentation, 115, 347,"Operation calls more than a few methods");
		createLabel(xSecondIndentation, 135, 67,"CINT >");
		createText(135);
		createLabel(354, 135, 59,"AND");
		createLabel(xFirstIndentation, 155, 347,"Calls are dispersed in very few classes");
		createLabel(xSecondIndentation, 175, 67,"CDISP <");
		createText(175);
		createLabel(354, 175, 59,")");
		
		createLabel(xFirstIndentation, 195, 59,"AND");
		
		createLabel(xFirstIndentation, 215, 347,"Method has few nested conditionals");
		createLabel(xSecondIndentation, 235, 100,"MAXNESTING >");
		createText(315,235);
		
		currentTexts.get(0).setText(new Double(intensiveCouplingConf.getCINT_Greater_SMemCap()).toString());
		currentTexts.get(1).setText(new Double(intensiveCouplingConf.getCDISP_Less_Half()).toString());
		currentTexts.get(2).setText(new Double(intensiveCouplingConf.getCINT_Greater_Few() ).toString());
		currentTexts.get(3).setText(new Double(intensiveCouplingConf.getCDISP_Less_OneQuarter()).toString());
		currentTexts.get(4).setText(new Double(intensiveCouplingConf.getMAXNESTING_Greater_SHALLOW()).toString());
	}
	
	private void loadControlsForTraditionBreaker() {
		createLabel(xFirstIndentation, 15, 347,"More newly added services than average NOM/class");
		createLabel(xSecondIndentation, 35, 67,"NAS >=");
		createText(35);
		createLabel(354, 35, 59,"AND");
		createLabel(xFirstIndentation, 55, 347,"Newly added services are dominant in child class");
		createLabel(xSecondIndentation, 75, 67,"PNAS >=");
		createText(75);
		
		createLabel(xFirstIndentation, 95, 59,"AND");
		
		
		createLabel(xFirstIndentation, 115, 347,"(Method complexity in child class above average");
		createLabel(xSecondIndentation, 135, 67,"AMW >");
		createText(135);
		createLabel(354, 135, 59,"OR");
		
		createLabel(xFirstIndentation, 155, 347,"Functional complexity of child class is very high");
		createLabel(xSecondIndentation, 175, 67,"WMC >=");
		createText(175);
		createLabel(354, 175, 59,")");
		
		createLabel(xFirstIndentation, 195, 59,"AND");
		
		createLabel(xFirstIndentation, 215, 347,"Class has substancial number of methods");
		createLabel(xSecondIndentation, 235, 67,"NOM >=");
		createText(235);
		
		createLabel(xFirstIndentation, 255, 59,"AND");
		
		
		createLabel(xFirstIndentation, 275, 397,"Parent's functional complexity above average");
		createLabel(xSecondIndentation, 295, 67,"AMW >");
		createText(295);
		createLabel(354, 295, 59,"AND");
		createLabel(xFirstIndentation, 315, 397,"Parent has more than half of child's methods");
		createLabel(xSecondIndentation, 335, 67,"NOM >");
		createText(335);
		createLabel(354, 335, 59,"AND");
		
		createLabel(xFirstIndentation, 355, 397,"Parent's complexity more than half of child");
		createLabel(xSecondIndentation, 375, 67,"WMC >=");
		createText(375);
		
		currentTexts.get(0).setText(new Double(traditionBreakerConf.getNAS_GreaterEqual_NOMAvg()).toString());
		currentTexts.get(1).setText(new Double(traditionBreakerConf.getPNAS_GreaterEqual_TWO_THIRD()).toString());
		currentTexts.get(2).setText(new Double(traditionBreakerConf.getAMW_Greater_AMWAvg()).toString());
		currentTexts.get(3).setText(new Double(traditionBreakerConf.getWMC_GreaterEqual_VeryHigh()).toString());
		currentTexts.get(4).setText(new Double(traditionBreakerConf.getNOM_GreatherEqual_High()).toString());
		currentTexts.get(5).setText(new Double(traditionBreakerConf.getAMW_Greater_AMWAvg_2()).toString());
		currentTexts.get(6).setText(new Double(traditionBreakerConf.getNOM_GreatherEqual_HighDiv2()).toString());
		currentTexts.get(7).setText(new Double(traditionBreakerConf.getWMC_Greater_VeryHighDiv2()).toString());
	}

	private void loadControlsForShotgunSurgery() {
		createLabel(xFirstIndentation, 15, 347,"Operation is called by too many other methods");
		createLabel(xSecondIndentation, 35, 67,"CM >");
		createText(35);
		createLabel(354, 35, 59,"AND");
		createLabel(xFirstIndentation, 55, 347,"Incoming calls are from many classes");
		createLabel(xSecondIndentation, 75, 67,"CC >");
		createText(75);
		
		currentTexts.get(0).setText(new Double(shotgunSurgeryConf.getCM_Greater_SMemCap()).toString());
		currentTexts.get(1).setText(new Double(shotgunSurgeryConf.getCC_GreaterEqual_MANY()).toString());
	}

	private void loadControlsForRefusedParentBequest() {
		createLabel(xFirstIndentation, 15, 347,"(Parent provides more than a few protected members");
		createLabel(xSecondIndentation, 35, 67,"NProtM >");
		createText(35);
		createLabel(354, 35, 59,"AND");
		
		createLabel(xFirstIndentation, 55, 347,"Childs uses only little of parent bequest");
		createLabel(xSecondIndentation, 75, 67,"BUR <");
		createText(75);
		
		createLabel(xFirstIndentation, 95, 59,"OR");
		
		createLabel(xFirstIndentation, 115, 347,"Overriden methods are rare in child");
		createLabel(xSecondIndentation, 135, 67,"BOvR <");
		createText(135);
		createLabel(354, 135, 59,")");
		
		createLabel(xFirstIndentation, 155, 59,"AND");
		
		
		createLabel(xFirstIndentation, 175, 347,"(Functional Complexity above average");
		createLabel(xSecondIndentation, 195, 67,"AMW >");
		createText(195);
		createLabel(354, 195, 59,"OR");
		
		createLabel(xFirstIndentation, 215, 347,"Class complexity not lower than average");
		createLabel(xSecondIndentation, 235, 67,"WMC >");
		createText(235);
		createLabel(354, 235, 59,")");
		
		createLabel(xFirstIndentation, 255, 59,"AND");
		
		createLabel(xFirstIndentation, 275, 347,"Class size is above average");
		createLabel(xSecondIndentation, 295, 67,"NOM >");
		createText(295);
		
		currentTexts.get(0).setText(new Double(refusedParentBequestConf.getNProtM_Greater_Few()).toString());
		currentTexts.get(1).setText(new Double(refusedParentBequestConf.getBUR_Less_OneThird()).toString());
		currentTexts.get(2).setText(new Double(refusedParentBequestConf.getBOvR_Less_OneThird()).toString());
		currentTexts.get(3).setText(new Double(refusedParentBequestConf.getAMW_Greater_AMWAvg()).toString());
		currentTexts.get(4).setText(new Double(refusedParentBequestConf.getWMC_Greater_WMCAvg()).toString());
		currentTexts.get(5).setText(new Double(refusedParentBequestConf.getNOM_Greater_NOMAvg()).toString());
		
	}
	
	private void createLabel(int x, int y, int width, String text){
		Label label = new Label(container, SWT.NONE);
		label.setBounds(x, y, width, 19);
		label.setText(text);
		currentLabels.add(label);
	}
	private void createText(int x,int y){
		Text text = new Text(container, SWT.BORDER);
		text.setBounds(x, y, 64, 19);
		currentTexts.add(text);
	}
	private void createText(int y){
		createText(280,y);
	}
	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				okButtonPressed();
			}

			
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(613, 500);
	}
	private void okButtonPressed() {
		if(list.getSelectionCount()==1){
			saveValues();
		}
		DataBaseManager.getInstance().saveOrUpdateEntity(brainClassConf);
		DataBaseManager.getInstance().saveOrUpdateEntity(brainMethodConf);
		DataBaseManager.getInstance().saveOrUpdateEntity(dataClassConf);
		DataBaseManager.getInstance().saveOrUpdateEntity(dipersedCouplingConf);
		DataBaseManager.getInstance().saveOrUpdateEntity(featureEnvyConf);
		DataBaseManager.getInstance().saveOrUpdateEntity(godClassConf);
		DataBaseManager.getInstance().saveOrUpdateEntity(intensiveCouplingConf);
		DataBaseManager.getInstance().saveOrUpdateEntity(refusedParentBequestConf);
		DataBaseManager.getInstance().saveOrUpdateEntity(shotgunSurgeryConf);
		DataBaseManager.getInstance().saveOrUpdateEntity(traditionBreakerConf);		
		
		
		CodeSmellsManager smellsManager = CodeSmellsManagerFactory.getInstance().getCurrentProjectManager();
		smellsManager.detectCodeSmells();
		
		Vector<DesignFlaw> flaws = new Vector<DesignFlaw>();
		flaws.addAll(smellsManager.getSmells());
		
		RankingManagerForSmells.getInstance().setDesingFlaws(flaws,RankingManagerForSmells.getInstance().getCurrentProject());
		spIRITView.updateView();
	}
}
