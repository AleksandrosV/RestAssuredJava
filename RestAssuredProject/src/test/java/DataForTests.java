import org.testng.annotations.DataProvider;

public class DataForTests {
	
	@DataProvider(name = "DataForPost")
	public Object[][] dataForPost() {
//		Object[][] data = new Object[2][2];
//		data[0][0] = "Aleber";
//		data[0][1] = "QA";
//		
//		data[1][0] = "Alex";
//		data[1][1] = "DEV";
//		return data;
		
		return new Object[][] {
			{"Alex", "QA"},
			{"Ivan", "DEV"}
		};
	}
}
