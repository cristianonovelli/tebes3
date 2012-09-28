package it.enea.xlab.tebes.users;


import javax.naming.InitialContext;
import javax.naming.NamingException;

import it.enea.xlab.tebes.common.Properties;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.entity.UserGroup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserManagerImplITCase {

	User currentUser;
	SUT currentSUT;
	UserGroup currentGroup;
	UserManagerRemote userManagerBean;
	
	@Before
	public void setup() throws Exception {
		
		currentUser = new User();
		currentUser.setCode(Properties.TEMP_USER);
		currentUser.setName("Cristiano");
		currentUser.setSurname("Novelli");
		currentUser.seteMail("cristiano.novelli@enea.it");

		currentSUT = new SUT("xmldocument", "XML document uploaded by web interface");
		currentGroup = new UserGroup("administrators", "Administrators Group of TeBES Platform");
		
		InitialContext ctx = new InitialContext();
		userManagerBean = (UserManagerRemote) ctx.lookup("TeBES-ear/UserManagerImpl/remote");
	}
	
	
	@Test
	public void testCreateUser() {
		
		Long id = userManagerBean.createUser(currentUser);
		Assert.assertTrue(id > 0);
		
		User user = userManagerBean.readUser(id);
		Assert.assertNotNull(user);
		Assert.assertEquals(Properties.TEMP_USER, user.getCode());
		
		// userManagerBean.createSUT(currentUser);
		
		userManagerBean.addUserSUT(user, currentSUT);
		//userManagerBean.setUserGroup(user, currentGroup);
		
		user = userManagerBean.readUser(id);
		Assert.assertEquals(1, user.getUserSut().size());

	}


	
}
