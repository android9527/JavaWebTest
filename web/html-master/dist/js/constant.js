/**
 * Created by chenfeiyue on 16/8/30.
 */
var app_url = "http://127.0.0.1:8080";
var get_pm_data = app_url + "/servlet/GetPmServlet";
var login_url = app_url + "/servlet/LoginServlet";

var user = new User();

function resetuser() {
    user.name = "";
    user.password = "";
    user.id = "";
    user.img = "";
}

function User(){
    this.name = "";
    this.password = "";
    this.id = "";
    this.img = "";
}