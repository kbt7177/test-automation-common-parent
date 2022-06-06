package com.springer.quality.database.model;

import lombok.Data;

@Data
public class DatabaseConfig {
    private String jdbcUrl;
    private String username;
    private String password;
}
