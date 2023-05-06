package io.github.jwdeveloper.ff.extension.mysql.implementation.models;

import lombok.Data;

@Data
public class SqlConnectionModel
{
    private String server;
    private String database;
    private boolean autoReconnect = true;
    private boolean useSll = false;
    private String user;
    private String password;
}
