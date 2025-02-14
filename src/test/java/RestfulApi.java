import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestfulApi {
    public static void main(String[] args) {
//        getAllProducts();
//        getSingleProduct();
//        getObjectsById();
//        AddObject();
//        UpdateObject();
//        PartiallyUpdateObject();
        DeleteObject();
    }
public static void getAllProducts(){
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();
        Response response = requestSpecification
                                .log()
                                .all()
                            .when()
                                .get("objects");
        Response response2 = requestSpecification
                                .log()
                                .all()
                            .when()
                                .get("objects");

        System.out.println("Hasilnya Adalah " + response.asPrettyString());
        System.out.println("Hasilnya Adalah " + response2.asPrettyString());
    }
    public static void getObjectsById() {
        RestAssured.baseURI = "https://api.restful-api.dev";

        Response response = RestAssured
                .given()
                .log().all()
                .queryParam("id", 3)
                .queryParam("id", 5)
                .queryParam("id", 10)
                .when()
                .get("/objects");

        System.out.println("Multiple Objects: " + response.asPrettyString());
    }
public static void getSingleProduct(){
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();
        Response response = requestSpecification
                                .log()
                                .all()
                                .pathParam("idProduct", 1)
                                .pathParam("path", "objects")
                            .when()
                                .get("{path}/{idProduct}");
        System.out.println("Single Product " + response.asPrettyString());
    }
    public static void AddObject(){
        String json = "{\n" +
                "   \"name\": \"Poco F6\",\n" +
                "   \"data\": {\n" +
                "      \"year\": 2024,\n" +
                "      \"price\": 5500000,\n" +
                "      \"CPU model\": \"Snapdragon 8s Gen 3\",\n" +
                "      \"Hard disk size\": \"512 GB\"\n" +
                "   }\n" +
                "}";

        RestAssured.baseURI = "https://api.restful-api.dev";

        Response response = RestAssured.given()
                .log()
                .all()
                .body(json)
                .contentType("application/json")
                .when()
                .post("/objects"); // <-- Perubahan utama di sini

        System.out.println("Add Object " + response.asPrettyString());
    }
    public static void UpdateObject(){
        String json = "{\n" +
                "   \"name\": \"Poco F6\",\n" +
                "   \"data\": {\n" +
                "      \"year\": 2024,\n" +
                "      \"price\": 5500000,\n" +
                "      \"CPU model\": \"Snapdragon 8s Gen 3\",\n" +
                "      \"Hard disk size\": \"512 GB\"\n" +
                "   }\n" +
                "}";

        RestAssured.baseURI = "https://api.restful-api.dev";

        Response response = RestAssured.given()
                .log()
                .all()
                .body(json)
                .contentType("application/json")
                .pathParam("idProduct", "ff808181932badb6019503414e6924d7")
                .when()
                .put("/objects/{idProduct}");

        System.out.println("Update Object " + response.asPrettyString());
    }
    public static void PartiallyUpdateObject(){
        String json =  "{\n" +
                "   \"name\": \"Apple MacBook Pro 16 (Updated Name)\"\n" +
                "}";

        RestAssured.baseURI = "https://api.restful-api.dev";

        Response response = RestAssured.given()
                .log()
                .all()
                .body(json)
                .contentType("application/json")
                .pathParam("idProduct", "ff808181932badb6019503414e6924d7")
                .when()
                .patch("/objects/{idProduct}");

        System.out.println("Partially Update Object " + response.asPrettyString());
    }
    public static void DeleteObject() {
        RestAssured.baseURI = "https://api.restful-api.dev";

        Response response = RestAssured.given()
                .log()
                .all()
                .pathParam("idProduct", "ff808181932badb6019503414e6924d7")
                .when()
                .delete("/objects/{idProduct}");

        System.out.println("Delete Object " + response.asPrettyString());
    }
}

