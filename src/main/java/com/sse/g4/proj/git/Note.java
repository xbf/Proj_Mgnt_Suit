package com.sse.g4.proj.git;

import lombok.Data;

@Data
public class Note {
    private String id;
    private String body;
    private String username;
    private String state;
    private String noteableIid;
    private Author author;

}
