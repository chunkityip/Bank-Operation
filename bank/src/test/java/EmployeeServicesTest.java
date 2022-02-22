import java.util.ArrayList;
import java.util.List;

import models.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.EmployeeServices;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;

public class EmployeeServicesTest {

    private EmployeeServices emp = mock(EmployeeServices.class) ;


    @BeforeEach
    public void setupMyEmpServices() {
        this.emp = new EmployeeServices();
    }

    @Test
    public void testisRequestExists() {
        List<Account> accounts = new ArrayList<Account>();
        Account one = new Account(2001, 2001, 600.00);
        Account two = new Account(2002, 2002, 700.00);
        Account three = new Account(2003, 2003, 900.00);

        accounts.add(one);
        accounts.add(two);
        accounts.add(three);

        int[] ans = {2001, 2002};

        // when transaction is in the array
        assertArrayEquals(ans, emp.isRequestExists(accounts, 2002));

        int[] ans1 = {0 , 0};
        // when transaction is not in the array
        assertArrayEquals(ans1, emp.isRequestExists(accounts, 0));

    }

}
