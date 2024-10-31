package dataStoring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import storageAdministration.Storage;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class SerializationMethodsTest {

    private File testFile;

    @BeforeEach
    public void setUp() throws Exception {
        this.testFile = new File("testfile.txt");
    }

    @AfterEach
    public void tearDown() throws Exception {
        this.testFile.delete();
    }

    @org.junit.jupiter.api.Test
    void serialize_testFileLength_true() {
        Storage storage = new Storage(10);
        SerializationMethods serialization = new SerializationMethods("testFile.txt");
        serialization.serialize(storage);
        assertTrue(testFile.length() > 10);

    }

    @org.junit.jupiter.api.Test
    void serialize_nullAsArgument_true() {
        SerializationMethods serialization = new SerializationMethods("testFile.txt");
        serialization.serialize(null);
        assertTrue(testFile.length() < 10);
        testFile.delete();
    }

    @org.junit.jupiter.api.Test
    void deserialize_equals() {
        Storage storage = new Storage(10);
        storage.addCustomer("Bob");
        SerializationMethods serialization = new SerializationMethods("testFile.txt");
        serialization.serialize(storage);
        Storage loaded = serialization.deserialize();
        assertEquals(1, loaded.getCustomerList().size());
    }
}