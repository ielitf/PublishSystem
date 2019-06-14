package com.danggui.publishsystem.control;

public class CodeConstants {

    public static String API_HOST = "192.168.10.199";
    public static int API_PORT = 8085;
    public static String URL_Query = "http://" + API_HOST + ":" + API_PORT + "/cprs";

    public static String USER_NAME = "username";//用户名
    public static String PASSWORD = "password";//用户密码

    public static String PAGE = "page";//分页查询：页数
    public static String PAGE_SIZE = "pageSize";//分页查询：每页查询单额条数
    public static String STATUS = "status";//查询条目的状态
    public static String PUBLISH_STATUS = "publishStatus";//发表信息的状态：已发布，未发布等
    public static String OPERATION = "operation";//审核操作：通过/不通过
    public static String DESCRIPTION = "description";//审核意见描述
    public static  String TOKEN = "Token";

    public static String ID = "id";//列表排期的id
    public static String NAME = "name";//列表名
    public static String START_DATA = "startData";
    public static String END_DATA = "endData";

    public static  String MAIN_PAGE_CACHED="main_page_cached";
    public static  String  HEADERS = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcmVhdGVUaW1lIjoxNTU5MDk3NTcyODk0LCJleHAiOjE1NjAzOTM1NzIsInVzZXJuYW1lIjoid2FuZ3l1ZSJ9.QbMTOHpdLebVRfx_ryw_6of-6ofGbZOSgvxJyBARyqg";

}
