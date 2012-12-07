package it.enea.xlab.tebes.common;

import java.util.Hashtable;

import javax.naming.InitialContext;

public class ContextUtils {
	
	@SuppressWarnings("unchecked")
	public static InitialContext getInitialContext(String hostPort)
			throws Exception {
		Hashtable props = getInitialContextProperties(hostPort);
		return new InitialContext(props);
	}

	@SuppressWarnings("unchecked")
	private static Hashtable getInitialContextProperties(String hostPort) {
		Hashtable props = new Hashtable();
		props.put("java.naming.factory.initial",
				"org.jnp.interfaces.NamingContextFactory");
		props.put("java.naming.factory.url.pkgs",
				"org.jboss.naming:org.jnp.interfaces");
		props.put("java.naming.provider.url", hostPort);

		return props;
	}

}
