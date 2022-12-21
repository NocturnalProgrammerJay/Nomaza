package UnitTesting.TestingDataTypes;

import Model.NozamaSystem;
import ReadWrite.Json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Test Class
import DataTypes.Item;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

	private String ID, name, invoicePrice, sellPrice, description, quantity, vendor;
	private Item testItem;

	@BeforeEach
	void setUp() {
		this.ID = "000";
		this.name = "T-Shirt";
		this.invoicePrice = "10.00";
		this.sellPrice = "15.00";
		this.description = "It's a T-shirt bro... calm down";
		this.quantity = "15";
		this.vendor = "seller1";

		this.testItem = new Item("000","T-Shirt","10.00","15.00","It's a T-shirt bro... calm down","15","seller1");
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testToString() {
		String ExpectedString = "Item: " + "T-Shirt" + " \n" +
				"Price: $" + "15.00" + " \n" +
				"Description: " + "It's a T-shirt bro... calm down" + " \n" +
				"Quantity: " + "15" + " \n" +
				"Vendor: " + "seller1" + " \n";

		assertEquals(ExpectedString,testItem.toString());
	}

	@Test
	void getID() {
		assertEquals("000", testItem.getID());
	}

	@Test
	void getName() {
		assertEquals("T-Shirt", testItem.getName());
	}

	@Test
	void toJSONObject() {
		// Create Expected JSONObject
		JSONObject data = new JSONObject();
		data.put("name", this.name);
		data.put("invoice_price", this.invoicePrice);
		data.put("sell_price", this.sellPrice);
		data.put("description", this.description);
		data.put("quantity", this.quantity);
		data.put("vendor", this.vendor);

		JSONObject header = new JSONObject();
		header.put(this.ID, data);
		JSONObject ExpectedItem = header;

		System.out.println(header.toJSONString());

		// Test to see if JSONStrings are equal
		assertEquals(ExpectedItem, testItem.toJSONObject());
		String ExpectedJSONString = "{\"000\":{\"name\":\"T-Shirt\",\"invoice_price\":\"10.00\",\"sell_price\":\"15.00\",\"description\":\"It's a T-shirt bro... calm down\",\"quantity\":\"15\",\"vendor\":\"seller1\"}}";
		// Test to see if strings are equal
		assertEquals(ExpectedJSONString, testItem.toJSONObject().toJSONString());
	}

	@Test
	void getSellPrice() {
		assertEquals(this.sellPrice, testItem.getSellPrice());
	}

	@Test
	void isBundle() {
		assertEquals(false, testItem.isBundle());
	}

	@Test
	void getQuantity() {
		// Converts String quantity to Integer
		Integer Expectedquantity = Integer.parseInt(this.quantity);
		assertEquals(Expectedquantity, testItem.getQuantity());
	}

	/**
	 * Leave for later
	 */
	@Test
	void getVendor() {
		String ExpectedVendor = "SellerAccount{" +
				"userName='" + "Seller1" + '\'' +
				", profit=" + "264.0" +
				", revenues=" + "475.0" +
				", costs=" + "211.0" + '}';
		NozamaSystem.getInstance().getSeller(vendor);
		assertEquals(ExpectedVendor ,testItem.getVendor());
	}

	@Test
	void getInvoicePrice() {
		assertEquals(this.invoicePrice, testItem.getInvoicePrice());
	}

	@Test
	void getDescription() {
		assertEquals(this.description, testItem.getDescription());
	}

	@Test
	void testEquals() {
		assertEquals(this.equals(testItem), testItem.equals(this));
	}

	@Test
	void testHashCode() {
		Item ExpectedTestItem = new Item("000","T-Shirt","10.00","15.00","It's a T-shirt bro... calm down","15","seller1");
		assertEquals(ExpectedTestItem.hashCode(), testItem.hashCode());
	}

	@Test
	void setID() {
		testItem.setID("005");
		Item ExpectedTestItem = new Item("005","T-Shirt","10.00","15.00","It's a T-shirt bro... calm down","15","seller1");
		assertEquals(ExpectedTestItem, testItem);
	}

	/**
	 * Come back to determine
	 */
	@Test
	void setName() {
		testItem.setName("BestShirt");
		Item ExpectedTestItem = new Item("005","BestShirt","10.00","15.00","It's a T-shirt bro... calm down","15","seller1");
		assertEquals(ExpectedTestItem.equals(testItem), testItem.equals(ExpectedTestItem));
	}

	@Test
	void setInvoicePrice() {
		testItem.setInvoicePrice("16.00");
		Item ExpectedTestItem = new Item("005","T-Shirt","16.00","15.00","It's a T-shirt bro... calm down","15","seller1");
		assertEquals(ExpectedTestItem.equals(testItem), testItem.equals(ExpectedTestItem));
	}

	@Test
	void setSellPrice() {
		testItem.setSellPrice("17.00");
		Item ExpectedTestItem = new Item("005","T-Shirt","10.00","17.00","It's a T-shirt bro... calm down","15","seller1");
		assertEquals(ExpectedTestItem.equals(testItem), testItem.equals(ExpectedTestItem));
	}

	@Test
	void setDescription() {
		//testItem.setDescription("This is a better Description, I promise");
		Item ExpectedTestItem = new Item("005","T-Shirt","10.00","15.00","This is a better Description, I promise","15","seller1");
		assertEquals(ExpectedTestItem.equals(testItem), testItem.equals(ExpectedTestItem));
	}

	@Test
	void setQuantity() {
		testItem.setQuantity("7");
		Item ExpectedTestItem = new Item("005","T-Shirt","10.00","15.00","This is a better Description, I promise","7","seller1");
		assertEquals(ExpectedTestItem.equals(testItem),testItem.equals(ExpectedTestItem));
	}

	@Test
	void setVendor() {
		testItem.setVendor("seller2");
		Item ExpectedTestItem = new Item("005","T-Shirt","10.00","15.00","This is a better Description, I promise","15","seller2");
		ExpectedTestItem.setVendor("seller2");
		assertEquals(ExpectedTestItem.equals(testItem), testItem.equals(ExpectedTestItem));
	}
}