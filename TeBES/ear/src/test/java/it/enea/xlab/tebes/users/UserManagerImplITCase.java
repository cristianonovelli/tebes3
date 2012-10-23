package it.enea.xlab.tebes.users;


import java.util.List;

import javax.naming.InitialContext;

import it.enea.xlab.tebes.common.Properties;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.entity.Role;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserManagerImplITCase {

	User user1, user2;
	SUT currentSUT;
	Role currentGroup;
	UserManagerRemote userManagerBean;
	Long idUser1, idUser2;
	Long idRole1, idRole2, idRole3, idRole4;
	
	@Before
	public void setup() throws Exception {
		


		currentSUT = new SUT("xmldocument", "XML document uploaded by web interface");
		//currentGroup = new Role("administrators", "Administrators Group of TeBES Platform", 1);
		
		InitialContext ctx = new InitialContext();
		userManagerBean = (UserManagerRemote) ctx.lookup("TeBES-ear/UserManagerImpl/remote");


		// Prepare 4 user Roles 
		Role role1 = new Role("standard_user", "Standard Users Group", 1);
		Role role2 = new Role("advanced_user", "Advanced Users Group", 2);
		Role role3 = new Role("system_administrator", "System Administrators Group", 3);
		Role role4 = new Role("xlab", "XLab Staff Group", 4);
		
		// Create dei Ruoli che devono gi� essere fissati come setup del sistema
		idRole1 = userManagerBean.createRole(role1);
		idRole2 = userManagerBean.createRole(role2);
		idRole3 = userManagerBean.createRole(role3);
		idRole4 = userManagerBean.createRole(role4);
/*		Long idRole1 = roleManagerBean.createRole(role1);
		Long idRole2 = roleManagerBean.createRole(role2);
		Long idRole3 = roleManagerBean.createRole(role3);
		Long idRole4 = roleManagerBean.createRole(role4);		*/
		// Get Role List
		List<Role> roleList = userManagerBean.getRoleList();
		Assert.assertTrue(roleList.size() == 4);
		
		// Read and Test
		/*role1 = roleManagerBean.readRole(idRole1);
		Assert.assertNotNull(role1);
		role2 = roleManagerBean.readRole(idRole2);
		Assert.assertNotNull(role2);
		role3 = roleManagerBean.readRole(idRole3);
		Assert.assertNotNull(role3);
		role4 = roleManagerBean.readRole(idRole4);
		Assert.assertNotNull(role4);*/
		
		// XLabUser: Cristiano Novelli
		User user1 = new User();
		user1.setName("Cristiano");
		user1.setSurname("Novelli");
		user1.seteMail("cristiano.novelli@enea.it");
		user1.setPassword("xcristiano");

		// XLabUser: Piero De Sabbata
		User user2 = new User();
		user2.setName("Piero");
		user2.setSurname("De Sabbata");
		user2.seteMail("piero.desabbata@enea.it");
		user2.setPassword("xpiero");

		// CREATE USER: Persist Users in DB through JPA
		idUser1 = userManagerBean.createUser(user1);
		idUser2 = userManagerBean.createUser(user2);
		
		// Test: verifico che gli id siano maggiori di zero
		List<User> userList = userManagerBean.getUserList();
		Assert.assertTrue(userList.size() >= 2);
	}
	
	
	
	@Test
	public void test1() {
		
		// USER
		//Long idUser = userManagerBean.createUser(currentUser);	
		
		//Assert.assertTrue(idUser > 0);
		//Assert.assertTrue(idUser == 0);
		
		
		//User user = userManagerBean.readUser(idUser);
		//Assert.assertNotNull(user);
		//Assert.assertEquals(Properties.TEMP_USER, user.getCode());
		

		
		// GROUP
		//Long idGroup = userManagerBean.createRole(currentGroup);
		//Assert.assertTrue(idGroup > 0);
		
		Role group = userManagerBean.readRole(idRole4);
		Assert.assertNotNull(group);		
		
		user1 = userManagerBean.readUser(idUser1);
		userManagerBean.setRole(user1, group);
		user1 = userManagerBean.readUser(user1.getId());
		//UserGroup g = user.getUserGroup();
		Assert.assertTrue(user1.getRole().getId() > 0);
		
		
		
		// SUT
		Long idSUT = userManagerBean.createSUT(currentSUT);
		Assert.assertTrue(idSUT > 0);
				
		SUT sut = userManagerBean.readSUT(idSUT);
		Assert.assertNotNull(sut);
		
		userManagerBean.addUserSUT(idUser1, idSUT);
		
		user1 = userManagerBean.readUser(idUser1);
		List<SUT> sutList = user1.getSutList();
		Assert.assertTrue(sutList.size() > 0);

		

		
	}
	
}

