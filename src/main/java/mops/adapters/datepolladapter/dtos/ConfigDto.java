package mops.adapters.datepolladapter.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfigDto implements Serializable {

    private boolean openForOwnEntries;
    private boolean singleChoice;
    private boolean priorityChoice;
    private boolean anonymous;
    private boolean open;
}