package com.app.myapp;

import com.app.myapp.controller.CarController;
import com.app.myapp.pojo.Car;
import com.app.myapp.repository.ICarRepository;
import com.app.myapp.repository.ISlotRepository;
import com.app.myapp.service.CarServiceImpl;
import com.app.myapp.service.ICarService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = AutoParkingSystemApplication.class
)
@AutoConfigureMockMvc
//@TestPropertySource(
//		locations = "classpath:application-test.properties"
//)
class DemoApplicationTests {
	private static final ObjectMapper om = new ObjectMapper();
	private static final Logger logger= LoggerFactory.getLogger(CarController.class);
	@Autowired
	ICarRepository carRepository;
	@LocalServerPort
	private int port;
    @Autowired
    ICarService carService;
    HttpHeaders headers = new HttpHeaders();
	TestRestTemplate restTemplate = new TestRestTemplate();

	@BeforeEach
	void init() {
		logger.info("before test start\n\nPORT:"+port);
	}

	@AfterEach
	void afterEach() {
		logger.info("test was finished");
	}

	@Test
	void testGetAllCar(){
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/cars") ,
				HttpMethod.GET, entity, String.class);
		logger.info(response.toString());
		assertNotNull(response.getBody());
	}

	@Test
	void testCarEntry() throws Exception {
		String color="XYZ",plateNumber="456";
		Car car=new Car();
		car.setColor(color);
		car.setPlateNumber(plateNumber);
		boolean flag=carService.checkFoundDuplicateCar(plateNumber);
		if(!flag){
			logger.info("car not found with "+plateNumber);
		}else{
			logger.info("car found with "+plateNumber);
		}
		assertThat(flag).isEqualTo(false);

		ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/cars"), car, String.class);
		logger.info("response"+response.toString());
		logger.info("response getBody"+response.getBody());

		String responseStr = response.getBody();
		int begin = responseStr.indexOf("{");
		int end = responseStr.lastIndexOf("}") + 1;
		responseStr = responseStr.substring(begin, end);
		logger.info("response str"+responseStr);

		JSONObject jsonObject = new JSONObject(responseStr);
		JSONObject messageJsonObject=jsonObject.getJSONObject("message");
		JSONObject dataJsonObject=jsonObject.getJSONObject("data");
		logger.info("jsonObject:Data"+jsonObject);
		logger.info("message:"+messageJsonObject);
		logger.info("data json"+dataJsonObject);

		assertThat(messageJsonObject.get("status")).isEqualTo("successful");
		assertThat(dataJsonObject.get("carId")).isNotNull();
		assertThat(dataJsonObject.get("slotNumber")).isNotNull();
		assertThat(dataJsonObject.get("color")).isEqualTo(color);
		assertThat(dataJsonObject.get("plateNumber")).isEqualTo(plateNumber);

		Car carDetails=carRepository.findByPlateNumber(plateNumber);
		logger.info("carDetails:"+carDetails.toString());
		assertThat(dataJsonObject.get("color")).isEqualTo(carDetails.getColor());
		assertThat(dataJsonObject.get("plateNumber")).isEqualTo(carDetails.getPlateNumber());
	}
	@Test
	void testGetCarByPlateNumber() throws JSONException {
		String plateNumber="444",slotNumber="0";
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/cars/search/plateNumber/"+plateNumber),
				HttpMethod.GET, entity, String.class);

		String responseStr = response.getBody();
		int begin = responseStr.indexOf("{");
		int end = responseStr.lastIndexOf("}") + 1;
		responseStr = responseStr.substring(begin, end);
		logger.info("res str"+responseStr);

		JSONObject jsonObject = new JSONObject(responseStr);
		logger.info("jsonObject:Data"+jsonObject.getJSONObject("data"));
		logger.info("jsonObject:"+jsonObject.getJSONObject("data").get("carPlateNumber"));
		assertNotNull(response);
		assertThat(jsonObject.getJSONObject("data").get("carPlateNumber")).isEqualTo(plateNumber);
		assertThat(jsonObject.getJSONObject("data").get("slotNumber")).isEqualTo(slotNumber);
		assertThat(jsonObject.getJSONObject("data").get("slotId")).isNotNull();
		assertThat(jsonObject.getJSONObject("data").get("carId")).isNotNull();
	}
	@Test
	void testCarExit() throws JSONException {
		String plateNumber = "995555";
		Car carDetails=null;

		boolean flag=carService.checkFoundDuplicateCar(plateNumber);
		if(!flag){
			logger.info("car not found with "+plateNumber);
		}else{
			logger.info("car found with "+plateNumber);
		}
		assertThat(flag).isEqualTo(true);

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/cars/"+plateNumber),
				HttpMethod.DELETE, entity, String.class);
		logger.info("response :"+response);

		String responseStr = response.getBody();
		int begin = responseStr.indexOf("{");
		int end = responseStr.lastIndexOf("}") + 1;
		responseStr = responseStr.substring(begin, end);
		logger.info("response str"+responseStr);

		JSONObject jsonObject = new JSONObject(responseStr);
		JSONObject messageJsonObject=jsonObject.getJSONObject("message");
		JSONObject dataJsonObject=jsonObject.getJSONObject("data");
		logger.info("jsonObject:Data"+jsonObject);
		logger.info("message:"+messageJsonObject);
		logger.info("data json"+dataJsonObject);

		assertThat(messageJsonObject.get("status")).isEqualTo("successful");
		assertThat(dataJsonObject.get("carId")).isNotNull();
		assertThat(dataJsonObject.get("slotNumber")).isNotNull();
		assertThat(dataJsonObject.get("plateNumber")).isEqualTo(plateNumber);
		try {
			carDetails = carRepository.findByPlateNumber(plateNumber);
			logger.info("carDetails:" + carDetails.toString());
			assertThat(carDetails).isEqualTo(null);
		}catch (Exception e){
			logger.info("car not found:deleted Successfully");
			assertThat(carDetails).isEqualTo(null);
		}
	}

	@Test
	void getCarByColor() throws Exception {
		String color="red";
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange((createURLWithPort("/cars/search/color/"+color)) ,
				HttpMethod.GET, entity, String.class);
		logger.info(response.toString());
		assertNotNull(response);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port +uri;
	}

	@Test
	void contextLoads() {
	}

	private static void printJSON(Object object) {
		String result;
		try {
			result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			System.out.println(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}




//
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//		ResponseEntity<String> response=restTemplate.exchange(
//				createURLWithPort("/cars"),
//				HttpMethod.POST,entity,String.class
//		);
//response.body(carList.get(0));

//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/cars"),
//				HttpMethod.GET, entity, String.class);
//		logger.info("***"+response.getBody());
//		Car testCar = carList.get(0);
//		carRepository.save(testCar);
//
//		String expected = "[{\"id\":1,\"name\":\"First Location\",\"principle\":\"Mr. Ranvir\",\"address\":\"California\"}]";
//		JSONAssert.assertEquals(expected, response.getBody(), false);




//		Car car=carList.get(0);
//		when(carRepository.save(any(Car.class))).thenReturn(car);
//		mockMvc.perform(post("/cars")
//				.content(om.writeValueAsString(car))
//				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//				.andExpect(status().isNotFound())
//				//.andExpect(jsonPath("carId", is("123")))
//				.andExpect(jsonPath("color", is("red")))
//				.andExpect(jsonPath("plateNumber", is("123")))
//				.andExpect(jsonPath("slotNumber", is("0")));
//		verify(carRepository, times(1)).save(any(Car.class));

//		carList=new ArrayList();
//		carList.add(new Car("123","red","123","0"));
//		carList.add(new Car("222","white","959","1"));
//		carList.add(new Car("333","black","198","2"));
//		carList.add(new Car("444","silver","648","3"));
//		carList.add(new Car("555","red","972","4"));
//		when(carRepository.findAll()).thenReturn(carList);



//Car car= restTemplate.getForObject(createURLWithPort("http://localhost:"+port+"/AutoParking/cars/search/plateNumber/444"),Car.class);
//Car car= restTemplate.getForObject(createURLWithPort("/cars/search/plateNumber/0101") , Car.class);
