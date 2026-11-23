
package com.johan.timetable.academic.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record AcademicYearRequest(

        @NotBlank
        @Size(max = 20)
        String name,

        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate

) {
    @AssertTrue(message = "End date must be after start date")
    public boolean isDateRangeValid() {
        if (startDate == null || endDate == null) {
            return true;
        }

        return endDate.isAfter(startDate);
    }
}
