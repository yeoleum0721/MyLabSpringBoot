package com.rookies4.mylabspringboot.config.env;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter @Setter
@ToString
public class MyEnvironment {
    private String mode;
}
