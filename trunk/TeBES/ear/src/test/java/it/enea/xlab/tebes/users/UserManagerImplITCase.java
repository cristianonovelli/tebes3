package it.enea.xlab.tebes.users;


import java.util.List;

import javax.naming.InitialContext;

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
		
		// USER
		Long idUser = userManagerBean.createUser(currentUser);	
		
		Assert.assertTrue(idUser > 0);
		//Assert.assertTrue(idUser == 0);
		
		
		User user = userManagerBean.readUser(idUser);
		Assert.assertNotNull(user);
		Assert.assertEquals(Properties.TEMP_USER, user.getCode());
		
		
		// SUT
		Long idSUT = userManagerBean.createSUT(currentSUT);
		Assert.assertTrue(idSUT > 0);
				
		SUT sut = userManagerBean.readSUT(idSUT);
		Assert.assertNotNull(sut);
		
		userManagerBean.addUserSUT(idUser, idSUT);
		
		user = userManagerBean.readUser(idUser);
		List<SUT> sutList = user.getUserSut();
		Assert.assertTrue(sutList.size() > 0);

		
		// GROUP
		Long idGroup = userManagerBean.createGroup(currentGroup);
		Assert.assertTrue(idGroup > 0);
		
		UserGroup group = userManagerBean.readGroup(idGroup);
		Assert.assertNotNull(group);		
		
		userManagerBean.setUserGroup(user, group);
		//user = userManagerBean.readUser(idUser);
		//UserGroup g = user.getUserGroup();
		//Assert.assertEquals(g.getId(), group.getId());
		
	}
	
}

