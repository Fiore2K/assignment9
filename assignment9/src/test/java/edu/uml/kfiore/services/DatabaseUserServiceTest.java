package edu.uml.kfiore.services;

import edu.uml.kfiore.model.User;
import edu.uml.kfiore.util.DatabaseInitializationException;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class DatabaseUserServiceTest extends DatabaseServiceTest{


    private UserService databaseUserService;

    @Before
    public void setUp() throws DatabaseInitializationException {
        super.setUp();
        databaseUserService = ServiceFactory.getUserService();
    }

    @Test
    public void testAddPerson() throws Exception{
        String sam = "Vic";
        User user = new User(sam);
        databaseUserService.addPerson(user);
    }

    @Test(expected = DuplicateUserNameException.class)
    public void testAddPersonDuplicateUser() throws Exception{
        String sam = "Sam";
        User user = new User(sam);
        databaseUserService.addPerson(user);

    }

}
