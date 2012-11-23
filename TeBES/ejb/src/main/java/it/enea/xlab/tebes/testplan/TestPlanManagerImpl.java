package it.enea.xlab.tebes.testplan;

import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.TestPlan;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TestPlanManagerImpl implements TestPlanManagerRemote {

	@PersistenceContext(unitName="TeBESPersistenceLayer")
	private EntityManager eM; 
	
	
	public String createTestPlan(TestPlan testPlan) {
		
		List<TestPlan> testPlanList = this.findTestPlanById(testPlan.getTestPlanId());
		
		if ( (testPlanList == null) || (testPlanList.size() == 0) ) {

			eM.persist(testPlan);
			
		}
		else {
			testPlan = testPlanList.get(0);
		}
		
		return testPlan.getTestPlanId();
	}

	
	@SuppressWarnings("unchecked")
	private List<TestPlan> findTestPlanById(String testPlanId) {
		
        String queryString = "SELECT t FROM TestPlan AS t WHERE t.testPlanId = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, testPlanId);
        return query.getResultList();
	}


	/**
	 * Questo metodo importa il Test Plan (da file o bancadati) 
	 * e crea la struttura java corrispondente utilizzando TestPlanDOM
	 * @param tempUser 
	 * @param testplan 
	 * 
	 * @return TestPlan
	 */
	/*public TestPlanOLD importTestPlan(String testPlanFilePath, String userId) {

		TestPlanOLD testPlan = null;
		
		System.out.println("Start Test Plan import process.");
		
		// Open Test Plan XML Source
		TestPlanDOM testPlanDOM = getTestPlanDOM(testPlanFilePath);
		
		// Read testPlanId and userId from Test Plan
		String id_tp = testPlanDOM.getIdAttribute(testPlanDOM.root);
		String userId_tp = testPlanDOM.getUserIDAttribute(testPlanDOM.root);
		
		// Check consistence between userId parameter and userId from test plan
		if ( userId.equals(userId_tp) ) {
			
			testPlan = new TestPlanOLD(id_tp, userId_tp);
			
			// Get other TestPlan root attributes			
			String userSUT = testPlanDOM.getUserSUTAttribute(testPlanDOM.root);
			String datetime = testPlanDOM.getDatetimeAttribute(testPlanDOM.root);
			String state = testPlanDOM.getStateAttribute(testPlanDOM.root);
			testPlan.setUserSUT(userSUT);
			testPlan.setDatetime(datetime);
			testPlan.setState(state);
			
			// Ottengo lista actions
			NodeList actionNodes = testPlanDOM.getTestActionNodeList();
			System.out.println("READ " + actionNodes.getLength() + " Test Actions.");
			
			// TODO estraggo lista action e workflow e creo strutture dati
			// per il momento creo una struttura lineare, un array
			Vector<Action> testActionList = new Vector<Action>();
		
			// Ciclo su ogni azione
			Element actionElement = null;
			for (int i = 0; i < actionNodes.getLength(); i++) {
				
				actionElement = (Element) actionNodes.item(i);
				
				int number = new Integer(testPlanDOM.getNumberAttribute(actionElement)).intValue();
				
				String name = testPlanDOM.getActionName(actionElement);
				String description = testPlanDOM.getActionDescription(actionElement);				
				
				Node testNode = testPlanDOM.getTestNode(actionElement);
				String lg = testPlanDOM.getLgAttribute(testNode);
	
				String type = testPlanDOM.getTypeAttribute(testNode);		
				String value = testNode.getFirstChild().getNodeValue();				
				String jumpString = testPlanDOM.getJumpAttribute(testNode);		
				boolean jump;
				if (jumpString.equals("true"))
					jump = true;
				else 
					jump = false;
				
				String location = testPlanDOM.getLocationAttribute(testNode);
				
				location = TeBESDAO.url2localLocation(location);
				
				Action action = new Action(number, name, lg, type, location, value, jump, description);
				System.out.println(action.getActionSummaryString());
				
				testActionList.add(action);
			}	
	
	
			testPlan.setWorkflow(testActionList);
			
		}
		else
			System.out.println("ERROR: Detect inconsistence between userId parameter and userId of Test Plan!");
		
		System.out.println("End Test Plan import process.");
		
		return testPlan;
	}*/
	
	






	private TestPlanDOM getTestPlanDOM(String testPlanFilePath) {
		
		try {
			return new TestPlanDOM(testPlanFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	public TestPlan getTestPlanFromXML(String testPlanAbsFileName) {
		
		TestPlan testPlan = null;	
		
		try {

			TestPlanDOM testPlanDOM = new TestPlanDOM(testPlanAbsFileName);
			testPlan = new TestPlan(testPlanDOM.getRootIdAttribute(), testPlanDOM.getRootUserIDAttribute(), testPlanDOM.getXMLString(), testPlanDOM.getRootDatetimeAttribute(), testPlanDOM.getRootStateAttribute(), testPlanAbsFileName);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
		return testPlan;
	}



}
