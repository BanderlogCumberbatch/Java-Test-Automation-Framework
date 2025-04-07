package apitest;

import org.helpers.BaseRequests;
import org.helpers.PropertyProvider;
import org.pojo.Addition;
import org.pojo.Entity;
import org.pojo.EntityListResponse;
import org.pojo.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.requestSpecification;
import static org.testng.AssertJUnit.assertFalse;

/**
 * Класс теста получения списка сущностей.
 */
public class GetEntitiesTest {

    @DataProvider(name = "Add entities data")
    public Object[][] dpMethod() {
        return new Object[][]{
                {PropertyProvider.getInstance().getProperty("prof.entity.title"), true, PropertyProvider.getInstance().getProperty("prof.entity.info")},
        };
    }

    /**
     * Список ID сущностей.
     */
    private final List<String> entitiesId = new ArrayList<>();

    /**
     * Действия перед тестами.
     */
    @BeforeClass
    public void setup() throws IOException {
        requestSpecification = BaseRequests.initRequestSpecification();
    }


    /**
     * Тест получения списка сущностей.
     */
    @Test(description = "Get entities API test", dataProvider = "Add entities data")
    public void testGetEntities(String title, Boolean verified, String additional_info) {
        Entity entityPojo = Entity.builder()
                .title(title).verified(verified)
                .addition(Addition.builder().additionalInfo(additional_info).build())
                .build();

        BaseRequests.createEntity(entitiesId, entityPojo);

        EntityListResponse responseWrapper = BaseRequests.getEntities();

        List<Response> entities = responseWrapper.getEntity();

        assertFalse("Список сущностей не должен быть пустым", entities.isEmpty());
    }

    /**
     * Действия после тестов.
     */
    @AfterTest
    public void deleteUserAfterCreation() { BaseRequests.deleteEntitiesById(entitiesId);}
}
