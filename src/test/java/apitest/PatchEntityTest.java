package apitest;

import org.helpers.BaseRequests;
import org.helpers.PropertyProvider;
import org.pojo.Addition;
import org.pojo.Entity;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.requestSpecification;

/**
 * Класс теста обновления сущности.
 */
public class PatchEntityTest {

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
     * Тест обновления сущности.
     */
    @Test(description = "Patch entity API test", dataProvider = "Add entities data")
    public void testPatchEntity(String title, Boolean verified, String additional_info) {
        Entity entityPojo = Entity.builder()
                .title(title).verified(verified)
                .addition(Addition.builder().additionalInfo(additional_info).build())
                .build();

        BaseRequests.createEntity(entitiesId, entityPojo);

        entityPojo = Entity.builder()
                .title(PropertyProvider.getInstance().getProperty("prof.unverified.title")).verified(false)
                .addition(Addition.builder().additionalInfo(PropertyProvider.getInstance().getProperty("prof.unverified.info")).build())
                .build();

        BaseRequests.patchEntityById(entitiesId.get(0), entityPojo);
    }

    /**
     * Действия после тестов.
     */
    @AfterTest
    public void deleteUserAfterCreation() { BaseRequests.deleteEntitiesById(entitiesId);}
}
