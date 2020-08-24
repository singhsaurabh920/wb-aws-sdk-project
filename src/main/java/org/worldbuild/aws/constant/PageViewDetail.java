package org.worldbuild.aws.constant;

public enum PageViewDetail {

    DASHBOARD_V1("pages/Dashboard/index1","Dashboard V1"),
    DASHBOARD_V2("pages/Dashboard/index2","Dashboard V2"),
    DASHBOARD_V3("pages/Dashboard/index3","Dashboard V3"),
    GALLERY("pages/Gallery/gallery","Gallery");

    public String path;
    public String pageName;

    PageViewDetail( String path,String pageName) {
        this.path=path;
        this.pageName = pageName;
    }
}
