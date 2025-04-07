package org.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Entity {

    /**
     * Дополнительная информация
     */
    @Builder.Default
    private Addition addition = Addition.builder().additionalInfo("Дополнительные сведения").additionalNumber(123).build();

    /**
     * "Важные" номера
     */
    @Builder.Default
    @SerializedName("important_numbers")
    int[] importantNumbers = new int[] {42, 87, 15};

    /**
     * Заголовок сущности
     */
    @Builder.Default
    private String title = "Заголовок сущности";

    /**
     * Верификация
     */
    @Builder.Default
    private Boolean verified = true;
}
