package storageAdministration;

import static org.junit.jupiter.api.Assertions.*;

class StorageFacadeTest {

    @org.junit.jupiter.api.Test
    void getStorage_notNull() {
        StorageFacade facadeUnterTest = new StorageFacade(new Storage(10));
        assertNotNull(facadeUnterTest.getStorage());
    }

    @org.junit.jupiter.api.Test
    void getStorage_equals() {
        Storage storage = new Storage(10);
        StorageFacade facadeUnterTest = new StorageFacade(storage);
        assertEquals(storage, facadeUnterTest.getStorage());
    }

    @org.junit.jupiter.api.Test
    void setStorage_equals() {
        StorageFacade facadeUnterTest = new StorageFacade(new Storage(10));
        Storage newStorage = new Storage(10);
        facadeUnterTest.setStorage(newStorage);
        assertEquals(newStorage, facadeUnterTest.getStorage());
    }

}