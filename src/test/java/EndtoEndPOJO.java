import com.apiautomation.request.ResponseItem;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class EndtoEndPOJO {

    ResponseItem responseItem;

    /*
     * Suggestion
     * untuk test deleteProduct , kita parsing PID. Ini bisa ditambahkan annotation dependsOnMethods di test 
     * createProduct(). Nanti setelah createProduct() bakal dapat pid, nah itu bisa di set dulu,
     * nanti di deleteProduct() bisa dipanggil PID nya
     */

    String pidProduct;

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
                "" +
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
        Assert.assertEquals(response.getStatusCode(), 200, "Status code check");

        JsonPath jsonPath = response.jsonPath();
        ResponseItem responseItem = jsonPath.getObject("", ResponseItem.class);

        // Validate root fields
        Assert.assertNotNull(responseItem.id, "ID should not be null");
        Assert.assertTrue(responseItem.id.startsWith("ff"), "ID should start with 'ff'");
        Assert.assertEquals(responseItem.name, "Apple MacBook Pro 16", "Product name check");

        // Validate createdAt format using raw JSON string
        String createdAt = jsonPath.getString("createdAt");
        Assert.assertTrue(createdAt.startsWith("2025"), "CreatedAt year validation");

        // Validate data object
        Assert.assertNotNull(responseItem.data, "Data object should not be null");

        // Validate data fields
        Assert.assertEquals(responseItem.data.year, 2019, "Year validation");
        Assert.assertEquals(responseItem.data.price, 1849.99, 0.001, "Price validation");
        Assert.assertEquals(responseItem.data.CPUModel, "Intel Core i9", "CPU Model validation");
        Assert.assertEquals(responseItem.data.hardDiskSize, "1 TB", "Hard disk size validation");

        pidProduct = responseItem.id;
    }

    @Test(dependsOnMethods = "createProduct")
    public void GetSingleProduct() {
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();
        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .pathParam("id", pidProduct)
                .when()
                .get("{path}/{id}");
        System.out.println("Response API" + response.asPrettyString());
        JsonPath addJsonPath = response.jsonPath();
        responseItem = addJsonPath.getObject("", ResponseItem.class);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(responseItem.id.contains("ff"));
        Assert.assertNotNull(responseItem.data);
        Assert.assertEquals(2019, responseItem.data.year);
        Assert.assertEquals(1849.99, responseItem.data.price, 0.001); // Delta untuk double
        Assert.assertEquals("Intel Core i9", responseItem.data.CPUModel);
        Assert.assertEquals("1 TB", responseItem.data.hardDiskSize);


    }

    @Test(dependsOnMethods = "createProduct")
    public void deleteProduct() {
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();
        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .pathParam("id", pidProduct)
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


