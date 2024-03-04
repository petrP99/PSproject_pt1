package com.pers.dto;

import com.pers.entity.Status;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FilterClientEntity {
    Long id;
    String firstName;
    String lastName;
    Status status;
}
