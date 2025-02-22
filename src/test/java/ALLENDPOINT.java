import com.apiautomation.request.ResponseItem;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class ALLENDPOINT {
    ResponseItem responseItem;

    //    ListOfAllObject();
//    ListOfobjectsByIDs();
//    SingleObject();
//    AddObject();
//    UpdateObject();
//    PartiallyUpdateObject();
//    DeleteObject();
    @Test
    public void ListOfAllObject() {
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();
        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                .when()
                .get("{path}");
        System.out.println("Response API" + response.asPrettyString());
        JsonPath jsonPath = response.jsonPath();
        List<ResponseItem> responseItems = jsonPath.getList("", ResponseItem.class);
        for (ResponseItem responseItem : responseItems) {
            System.out.println("Response API" + responseItem.id);
            System.out.println("Response API" + responseItem.name);
            System.out.println("Response API" + responseItem.createdAt);
            if (responseItem.data != null) {
                System.out.println("Response API" + responseItem.data.year);
                System.out.println("Response API" + responseItem.data.color);
                System.out.println("Response API" + responseItem.data.Color);
                System.out.println("Response API" + responseItem.data.description);
                System.out.println("Response API" + responseItem.data.generation);
                System.out.println("Response API" + responseItem.data.Generation);
                System.out.println("Response API" + responseItem.data.price);
                System.out.println("Response API" + responseItem.data.Price);
                System.out.println("Response API" + responseItem.data.Capacity);
                System.out.println("Response API" + responseItem.data.capacity);
                System.out.println("Response API" + responseItem.data.strapColour);
                System.out.println("Response API" + responseItem.data.screenSize);
                System.out.println("Response API" + responseItem.data.CPUModel);
                System.out.println("Response API" + responseItem.data.hardDiskSize);
            }
        }
    }

    @Test
    public void ListOfObjectsByIDs() {
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                .given();
        Response response = requestSpecification
                .log()
                .all()
                .pathParam("path", "objects")
                // Use "id" instead of "ids", and pass multiple values
                .queryParam("id", "3", "5", "10")  // <-- This is the corrected line
                .when()
                .get("{path}");
        System.out.println("Response API" + response.asPrettyString());
        JsonPath jsonPath = response.jsonPath();
        List<ResponseItem> responseItems = jsonPath.getList("", ResponseItem.class);
        for (ResponseItem responseItem : responseItems) {
            System.out.println("Response API" + responseItem.id);
            System.out.println("Response API" + responseItem.name);
            if (responseItem.data != null) {
                System.out.println("Response API " + responseItem.data.color);
                System.out.println("Response API " + responseItem.data.capacityGB);
                System.out.println("Response API" + responseItem.data.price);
                System.out.println("Response API" + responseItem.data.Capacity);
                System.out.println("Response API" + responseItem.data.screenSize);
            }
        }
    }

    @Test
    public void AddObject() {
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
    public void SingleObject() {
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
    public void DeleteObject() {
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

