package com.DoItNow.PersonalTaskManagementSoftware.model;

import java.time.LocalDate;

public interface NewTask {
    String getTitle();
    LocalDate getStart();
    LocalDate getEnd();
}
