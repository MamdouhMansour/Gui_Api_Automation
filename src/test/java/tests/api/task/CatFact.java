package tests.api.task;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;
import com.shaft.validation.Assertions;
import com.shaft.validation.Assertions.AssertionType;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;

public class CatFact {
	@Test(description = "TC001 - Check text is not empty ")
	@Description("Check that text in the response body is not empty in the random fact")
	@Severity(SeverityLevel.NORMAL)
	public void testCatFactResponse() {

		RestActions apiObject = new RestActions("https://cat-fact.herokuapp.com");

		// using random to get random cat fact, amount = 1 to get only one fact.
		Response facts = apiObject.performRequest(RequestType.GET, 200, "/facts/random?amount=1");

		boolean isTextEmpty = RestActions.getResponseJSONValue(facts, "text").isEmpty();

		Assertions.assertTrue(isTextEmpty, AssertionType.NEGATIVE, "Text Is Not Empty");
	}

	@Test(description = "TC001 - Check text is not empty ")
	@Description("Check that text in the response body is not empty in the random fact")
	@Severity(SeverityLevel.NORMAL)
	public void testCatFactResponse1() {

		// get the response of cat facts that have two animals cat,horse in the request
		// query then assert the following
		RestActions apiObject = new RestActions("https://cat-fact.herokuapp.com");

		// using random to get random cat fact, amount = 1 to get only one fact.
		Response facts = apiObject.performRequest(RequestType.GET, 200, "/facts?animal_type=cat,horse");

		int listSize = getListSize(facts, "_id", "\\,");

		System.out.println(listSize);

		Assertions.assertTrue(listSize == 5);// senior check that response array length is 5

		Assertions.assertNull(RestActions.getResponseJSONValue(facts, "status[1].feedback"));// senior check that field
																								// doesn't exist

		for (int i = 1; i < listSize; i++) {
			Assertions.assertTrue(!("_id[" + (i - 1) + "]").equalsIgnoreCase("_id[" + i + "]"));
		}
	}

	public int getListSize(Response response, String JsonPath, String regex) {
		List<String> list = Arrays.asList(RestActions.getResponseJSONValue(response, JsonPath));
		String[] array = list.get(0).split(regex);
		return array.length;
	}

}
