package UnitTesting.TestingDataTypes;

import DataTypes.Bundle;
import DataTypes.Item;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BundleTest {


    @Test
    void getSellPrice() {
        Item i = new Item("99", "carrots", "35","33","carrotse bad", "1", "");
        Item j = new Item("100", "carrots", "35","38","cats gonbad", "1", "");
        Bundle b = new Bundle("44", "Package", "Jamar", "6", i , j);
        assertEquals("71.0" ,b.getSellPrice());
    }


    @Test
    void getQuantity() {
        Bundle b = new Bundle("44", "Package", "Jamar", "6");
        assertEquals(6,b.getQuantity());
    }

    @Test
    void getName() {
        Bundle b = new Bundle("44", "Carrots", "Jamar", "6");
        assertEquals("Carrots",b.getName());
    }


    @Test
    void addItem() {
        Item i = new Item("99", "carrots", "35","33","carrotse bad", "1", "");
        Bundle b = new Bundle("44", "Carrots", "Jamar", "6");
        b.addItem(i);
        assertEquals("33.0",b.getSellPrice());
    }

    @Test
    void testEquals() {
        Bundle b1 = new Bundle("44", "Carrots", "Jamar", "6");
        Bundle b2 = new Bundle("44", "Carrots", "Jamar", "6");
        assertEquals(b1,b2);
    }

    @Test
    void getID() {
        Bundle b = new Bundle("44", "Carrots", "Jamar", "6");
        Item i = new Item("99", "carrots", "35","33","carrotse bad", "1", "");
        b.addItem(i);
        assertEquals("99", b.getID());
    }

    @Test
    void setQuantity() {
        Bundle b = new Bundle("44", "Carrots", "Jamar", "6");
        assertEquals(6, b.getQuantity());
    }
}