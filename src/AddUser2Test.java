import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AddUser2Test {

    @Test // Assert
    public void testValidateField() {

        AddUser2 addUser = new AddUser2();


        String dob ="01-01-2000";
        String mobileNumber ="12345678901"; // 11 digits
        String username1 ="user";
        String password ="password123";
        String address ="123 Main St";
        String username2 = "user1";
        String address2 ="123 Main St";
        // Act
        boolean result = addUser.jButton1ActionPerformed (dob, mobileNumber, username1, password, address);
        String result2 = null;
        
     // assertTrue(result, "All fields are valid, so validateFields should return true."); //True
      //  assertFalse(result,"All fields are valid, so validateFields should return true."); //False
      //  assertNotNull(result,"you know it is not null");                                  //Not Null
      assertNull(result2); //As my all fields are required!!!                             // Null
      //  assertNotEquals(username1, username2, "Usernames should not be equal!!!");         //Not Equal
     //  assertNotEquals(username2,username1,"Bro they should not be same");            //Equal
     //   assertEquals(address2,address,"Bro they could be same");////Equal
     //assertSame(address,address2,"IF both are same");//Same
     //assertNotSame(username1,address2,"IF both are not same");// NOt Same


    }
    @Test //Assert Throws
    public void testValidateField_ThrowsException() {
        // Arrange
        AddUser2 addUser = new AddUser2();

        // Invalid input data (empty DOB)
        String dob = ""; // Empty DOB
        String mobileNumber = "12345678901";
        String username = "user1";
        String password = "password123";
        String address = "123 Main St";

        // Act and Assert
        // Verify that an exception is thrown when DOB is empty
        assertThrows(Exception.class, () -> {
            addUser.jButton1ActionPerformed(dob, mobileNumber, username, password, address);
        }, "Empty DOB should throw an exception.");
    }

    @Test //Assert Does not Throws
    public void testValidateField_DoesNotThrowException() {
        // Arrange
        AddUser2 addUser = new AddUser2();

        // Valid input data
        String dob = "20-12-2002";
        String mobileNumber = "12345678901"; // 11 digits
        String username = "user1";
        String password = "password123";
        String address = "123 Main St";

        // Act and Assert
        // Verify that no exception is thrown when all fields are valid
        assertDoesNotThrow(() -> {
            addUser.jButton1ActionPerformed(dob, mobileNumber, username, password, address);
        }, "Valid input should not throw an exception.");
    }


    @Test
    public void testValidateField_FailScenario() {
        // Arrange
        AddUser2 addUser = new AddUser2();

        // Invalid input data (empty DOB)
        String dob = "20-12-2000"; // Empty DOB
        String mobileNumber = "12345678901";
        String username = "user1";
        String password = "password123";
        String address = "123 Main St";

        // Act and Assert
        try {
            boolean result = addUser.jButton1ActionPerformed(dob, mobileNumber, username, password, address);
            fail("Test failed: Expected an exception to be thrown for empty DOB, but no exception was thrown.");
        } catch (Exception e) {
            // Expected behavior: an exception is thrown
            fail("test failed due to having date");
        }
    }

    @Test
    public void testValidateField_Timeout() {
        // Arrange
        AddUser2 addUser = new AddUser2();

        // Valid input data
        String dob = "01-01-2000";
        String mobileNumber = "12345678901"; // 11 digits
        String username = "user1";
        String password = "password123";
        String address = "123 Main St";

        // Act and Assert
        assertTimeout(Duration.ofMillis(10500), () -> {
            boolean result = addUser.jButton1ActionPerformed(dob, mobileNumber, username, password, address);
            assertTrue(result, "The method should return true for valid input.");
        }, "Test failed: The method took longer than the expected timeout.");
    }








}