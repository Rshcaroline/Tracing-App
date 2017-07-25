package com.fdwireless.trace.ui.team;

/**
 * Created by caroline on 2017/2/23.
 */

public class FourTeam {
    private String name;
    private String numberrate;

    private int imageId;
    private int userone;
    private int usertwo;
    private int userthree;
    private int userfour;

    public FourTeam(String name, int imageId, int userone, int usertwo, int userthree, int userfour, String numberrate) {
        this.name = name;
        this.numberrate = numberrate;
        this.imageId = imageId;
        this.userone = userone;
        this.usertwo = usertwo;
        this.userthree = userthree;
        this.userfour = userfour;
    }

    public String getName() {
        return name;
    }
    public String getNumberrate() { return numberrate; }

    public int getImageId() {
        return imageId;
    }

    public int getUserone() { return userone; }

    public int getUsertwo() { return usertwo; }

    public int getUserthree() { return userthree; }

    public int getUserfour() { return userfour; }
}