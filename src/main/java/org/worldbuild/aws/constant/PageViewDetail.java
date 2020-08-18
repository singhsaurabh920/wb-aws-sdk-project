package org.worldbuild.aws.constant;

public enum PageViewDetail {

    DASHBOARD_V1("dashboard/index1","Dashboard V1"),
    DASHBOARD_V2("dashboard/index2","Dashboard V2"),
    DASHBOARD_V3("dashboard/index3","Dashboard V3"),
    S3_SERVICE("s3","S3 Service");

    public String path;
    public String pageName;

    PageViewDetail( String path,String pageName) {
        this.path=path;
        this.pageName = pageName;
    }
}
