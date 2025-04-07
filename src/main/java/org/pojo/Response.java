package org.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Response {

    /**
     * Дополнительная информация
     */
    private Addition addition;

    /**
     * ID сущности
     */
    private int id;

    /**
     * "Важные" номера
     */
    @SerializedName("important_numbers")
    int[] importantNumbers;

    /**
     * Заголовок сущности
     */
    private String title;

    /**
     * Верифицирован
     */
    private Boolean verified;
}
