

import javax.naming.InitialContext;

import utils.ContextUtils;

/**
*
* @author Piotr Soróbka <psorobka@gmail.com>
* @author Cristiano Novelli <cristiano.novelli@enea.it>
*/
public class HelloController {
    //@EJB
    //private HelloLocal helloService;

    //properties
    private String name;
    private String greeting;

    /**
     * default empty constructor
     */
    public HelloController() {
    }

    //-------------------getter & setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGreeting() {
        return greeting;
    }

    //-------------------actions

    public String sendAction() {
    	HelloLocal helloService = null;
    	try {
    		InitialContext context = ContextUtils.getInitialContext("localhost:1099");
    		helloService = (HelloLocal) context.lookup("TeBES-ear-1.0-SNAPSHOT/HelloBean/local");
    		} catch (Exception e) { // Error getting the home interface
    		   e.printStackTrace();
    		}
    	if (helloService != null) {
    		greeting = helloService.sayHello();
            return "success";
    	}
    	else {
    		// TODO: gestire navigazione verso pagina di errore
    		return "error";
    	}
        
    }
}

