import it.enea.xlab.tebes.entity.Action;

import java.util.ArrayList;
import java.util.Iterator;


public class IteratorTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ArrayList<String> actions = new ArrayList<String>();
		
		for (int i=0;i<10;i++)
			actions.add("ciao" + i);
			
		Iterator<String> it = actions.iterator();
        while (it.hasNext()) 
            System.out.println((String) it.next());
                
            
			
		
	}

}
