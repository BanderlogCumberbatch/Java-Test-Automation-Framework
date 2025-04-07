package org.helpers;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.pojo.Entity;
import org.pojo.EntityListResponse;
import org.pojo.Response;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

/**
 * Базовый класс спецификации запроса.
 */
public class BaseRequests {

    /**
     * Подготовка спецификации запроса
     * @return спецификация
     */
    public static RequestSpecification initRequestSpecification() throws IOException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder
                .setContentType(ContentType.JSON)
                .setBaseUri(PropertyProvider.getInstance().getProperty("api.url"))
                .setAccept(ContentType.JSON);
        return requestSpecification = requestSpecBuilder.build();
    }

    /**
     * Создание сущности
     *
     * @param entitiesId список id сущностей
     * @param entityPojo экземпляр создаваемой сущности
     */
    public static void createEntity(List<String> entitiesId, Entity entityPojo) {

        entitiesId.add(given()
                .spec(requestSpecification)
                .body(entityPojo)
                .when()
                .post(PropertyProvider.getInstance().getProperty("api.url.create"))
                .then()
                .statusCode(200)
                .extract().asString());
    }

    /**
     * Получение сущности по id
     *
     * @param entityId id сущности
     * @return экземпляр сущности
     */
    public static Response getEntityById(String entityId) {

        return given()
                .pathParam("id", entityId)
                .spec(requestSpecification)
                .when()
                .get(PropertyProvider.getInstance().getProperty("api.url.get"))
                .then()
                .statusCode(200)
                .extract().as(Response.class, ObjectMapperType.GSON);
    }

    /**
     * Обновление сущности по id
     *
     * @param entityId id сущности
     * @param entityPojo экземпляр сущности для обновления
     */
    public static void patchEntityById(String entityId, Entity entityPojo) {

        given()
                .pathParam("id", entityId)
                .spec(requestSpecification)
                .body(entityPojo)
                .when()
                .patch(PropertyProvider.getInstance().getProperty("api.url.patch"))
                .then()
                .statusCode(204)
                .extract().asString();
    }

    /**
     * Получение списка всех сущностей
     *
     * @return экземпляр списка сущностей
     */
    public static EntityListResponse getEntities() {

        return given()
                .spec(requestSpecification)
                .when()
                .get(PropertyProvider.getInstance().getProperty("api.url.get.all"))
                .then()
                .statusCode(200)
                .extract().as(EntityListResponse.class, ObjectMapperType.GSON);
    }

    /**
     * Удаление сущностей по списку id
     *
     * @param entitiesId список id сущностей, которые необходимо удалить
     */
    public static void deleteEntitiesById(List<String> entitiesId) {

        for (String entityId : entitiesId) {
            given()
                    .pathParam("id", entityId)
                    .spec(requestSpecification)
                    .when()
                    .delete(PropertyProvider.getInstance().getProperty("api.url.delete"))
                    .then()
                    .statusCode(204);
        }
    }
}
