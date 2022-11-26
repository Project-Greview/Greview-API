package com.zreview.api.domain.location.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Queue;

@Getter
@Setter
@RequiredArgsConstructor
public class PostLocationRequest {

    @NotNull
    @NotBlank
    private String name;

    private Double latitude;

    private Double longitude;


}