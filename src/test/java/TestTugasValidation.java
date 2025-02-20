import com.apiautomation.request.ResponseItem;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTugasValidation {

    @Test
    public void createProduct() {
        String json = "{\n" +
                "   \"name\": \"Apple MacBook Pro 16\",\n" +
                "   \"data\": {\n" +
                "      \"year\": 2019,\n" +
                "      \"price\": 1849.99,\n" +
                "      \"CPU model\": \"Intel Core i9\",\n" +
                "      \"Hard disk size\": \"1 TB\"\n" +
                "   }\n" +
                "}";
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();
        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .body(json)
                .contentType("application/json")
                .when()
                .post("{path}");
        System.out.println("Response API" + response.asPrettyString());
        JsonPath jsonPath = response.jsonPath();
        ResponseItem responseItem = jsonPath.getObject("", ResponseItem.class);
        System.out.println("Response API" + responseItem.id);
        System.out.println("Response API" + responseItem.name);
        System.out.println("Response API" + responseItem.createdAt);
        if (responseItem.data != null) {
            System.out.println("Response API" + responseItem.data.year);
            System.out.println("Response API" + responseItem.data.price);
            System.out.println("Response API" + responseItem.data.CPUModel);
            System.out.println("Response API" + responseItem.data.hardDiskSize);
        }
    }

    @Test
    public void GetSingleProduct() {
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();
        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .pathParam("id", "ff808181932badb6019513f5bb1844ba")
                .when()
                .get("{path}/{id}");
        System.out.println("Response API" + response.asPrettyString());
        JsonPath jsonPath = response.jsonPath();
        ResponseItem responseItem = jsonPath.getObject("", ResponseItem.class);
        if (responseItem.data != null) {
            System.out.println("Response API" + responseItem.data.year);
            System.out.println("Response API" + responseItem.data.price);
            System.out.println("Response API" + responseItem.data.CPUModel);
            System.out.println("Response API" + responseItem.data.hardDiskSize);
        }
    }

    @Test
    public void deleteProduct() {
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();
        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .pathParam("id", "ff808181932badb6019522a12ec66c1a")
                .when()
                .delete("{path}/{id}");
        System.out.println("Response API" + response.asPrettyString());
        JsonPath jsonPath = response.jsonPath();
        ResponseItem responseItem = jsonPath.getObject("", ResponseItem.class);
        if (responseItem.message != null) {
            // Jika berhasil
            System.out.println("Success: " + responseItem.message);
            Assert.assertTrue(responseItem.message.contains("has been deleted"), "Delete operation failed: Message not as expected.");
        } else if (responseItem.error != null) {
            System.out.println("Error: " + responseItem.error);
            Assert.assertTrue(responseItem.error.contains("doesn't exist"), "Delete operation failed: Error message not as expected.");
        } else {
            Assert.fail("Unexpected response: No message or error found.");
        }
    }
}

