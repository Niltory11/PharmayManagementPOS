import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

class ViewMedicineTest {

    private ViewMedicine viewMedicine;

    @BeforeEach
    void setUp() {
        viewMedicine = new ViewMedicine();
    }

    @AfterEach
    void tearDown() {
        viewMedicine.dispose();
    }

    @Test
    void testTableDataWithArrayList() {
        DefaultTableModel model = (DefaultTableModel) viewMedicine.jTable1.getModel();

        // Adding mock data
        model.addRow(new Object[]{"Paracetamol", "XYZ Pharma", 10, 5.0, 4.0});
        model.addRow(new Object[]{"Ibuprofen", "ABC Pharma", 15, 8.5, 7.5});

        // Expected Data in ArrayList format
        List<List<Object>> expectedData = new ArrayList<>();
        expectedData.add(List.of("Paracetamol", "XYZ Pharma", 10, 5.0, 4.0));
        expectedData.add(List.of("Ibuprofen", "ABC Pharma", 15, 8.5, 7.5));

        // Extracting table data into an ArrayList
        List<List<Object>> actualData = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            List<Object> row = new ArrayList<>();
            for (int j = 0; j < model.getColumnCount(); j++) {
                row.add(model.getValueAt(i, j));
            }
            actualData.add(row);
        }

        // Assert that table data matches expected data
        assertIterableEquals(expectedData, actualData, "Table data does not match expected list!");
    }
}
