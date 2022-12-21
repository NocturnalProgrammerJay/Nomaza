package UnitTesting.TestingDataTypes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// Test Class
import DataTypes.SellerAccount;

import static org.junit.jupiter.api.Assertions.*;

class SellerAccountTest {

	private String userName;
	private float profit, revenues, costs;

	@BeforeEach
	void setUp() {
		this.userName = "seller1";
		this.profit = 264.0f;
		this.revenues = 475.0f;
		this.costs = 211.0f;
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void onLogIn() {
	}

	@Test
	void toJSONObject() {
	}

	@Test
	void getUserName() {
	}

	@Test
	void testToString() {
	}

	@Test
	void getProfit() {
	}

	@Test
	void getRevenues() {
	}

	@Test
	void getCosts() {
	}

	@Test
	void testEquals() {
	}

	@Test
	void testHashCode() {
	}

	@Test
	void addToRevenues() {
	}

	@Test
	void addToCosts() {
	}

	@Test
	void calculateProfit() {
	}
}