import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class ViewMedicineTest {

    private ViewMedicine viewMedicine;

    @BeforeEach
    void setUp() {
        viewMedicine = new ViewMedicine(); // Initialize the ViewMedicine UI
    }

    @AfterEach
    void tearDown() {
        viewMedicine.dispose(); // Close the UI after each test
    }

    @Test
    void testComponentInitialization() {
        assertNotNull(viewMedicine.jTable1, "Table should be initialized");
        assertNotNull(viewMedicine.jLabel1, "Label should be initialized");
        assertNotNull(viewMedicine.jButton1, "Button should be initialized");
    }

    @Test
    void testInitialComponentValues() {
        // Debugging output
        System.out.println("Row Count: " + viewMedicine.jTable1.getRowCount());
        System.out.println("Label Text: " + viewMedicine.jLabel1.getText());
        System.out.println("Is Button Enabled? " + viewMedicine.jButton1.isEnabled());

        ArrayList<Boolean> conditions = new ArrayList<>();

        // Assertions to verify UI elements
        boolean isTableEmpty = (viewMedicine.jTable1.getRowCount() == 0);
        boolean isTitleCorrect = "STOCK VIEW".equals(viewMedicine.jLabel1.getText().trim());
        boolean isButtonEnabled = viewMedicine.jButton1.isEnabled();

        conditions.add(isTableEmpty);
        conditions.add(isTitleCorrect);
        conditions.add(isButtonEnabled);

        for (Boolean condition : conditions) {
            assertTrue(condition, "One of the conditions failed!");
        }
    }

    @Test
    void testTableInteraction() {
        // Simulating adding a row manually (since we can't connect to DB in test)
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) viewMedicine.jTable1.getModel();
        model.addRow(new Object[]{"Paracetamol", "XYZ Pharma", 10, 5.0, 4.0});

        assertEquals(1, viewMedicine.jTable1.getRowCount(), "Table should have one row after adding manually");
    }


}
