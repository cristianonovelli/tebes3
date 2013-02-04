package it.enea.xlab.tebes.common;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * @author X-Lab
 */

public class Profile {

	@AroundInvoke
	public Object interceptor(InvocationContext ctx) throws Exception 
	{
		System.out.println("Classe: "+ctx.getTarget()+ " Metodo: " + ctx.getMethod().getName());
		
		return ctx.proceed();
	}
}
