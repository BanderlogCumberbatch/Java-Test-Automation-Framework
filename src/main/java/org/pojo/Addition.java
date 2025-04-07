package org.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Addition {

    /**
     * Дополнительные сведения
     */
    @SerializedName("additional_info")
    private String additionalInfo;

    /**
     * Дополнительный номер
     */
    @SerializedName("additional_number")
    private int additionalNumber;

    /**
     * ID
     */
    private String id;
}
