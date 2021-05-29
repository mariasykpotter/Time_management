package model;

import java.util.Locale;

public enum Approve {
    UNAPPROVED(), APPROVED();

    public static String getStatus(int n) {
        return Approve.values()[n].name().toLowerCase();
    }

    public static Approve getApprove(TimeLog timeLog) {
        return Approve.values()[timeLog.getStatus()];
    }
}
