package com.sse.g4.proj;

import lombok.Data;
import java.util.List;

@Data
public class Team {
    protected String teamName;
    protected String leader;
    protected List<String> members;

}
