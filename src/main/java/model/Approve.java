package model;

public enum Approve {
    UNAPPROVED,APPROVED;
    public static Approve getApprove(TimeLog timeLog) {
        return Approve.values()[timeLog.getStatus()];
    }
}
