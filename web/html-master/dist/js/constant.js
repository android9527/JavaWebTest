/**
 * Created by chenfeiyue on 16/8/30.
 */
var app_url = "http://127.0.0.1:8080";
var get_pm_data = app_url + "/servlet/GetPmServlet";
var login_url = app_url + "/servlet/LoginServlet";
var get_avg_data_url = app_url + "/servlet/GetAvgDataServlet";

user = function () {
    this.name = "";
    this.password = "";
    this.id = "";
    this.img = "";
};
function resetUser() {
    user.name = "";
    user.password = "";
    user.id = "";
    user.img = "";
}

function fillUser(result) {
    user.name = result.name;
    user.password = result.password;
    user.id = result.id;
    user.img = result.img;
    console.log(user.name);
}

// function User(){
//     this.name = "";
//     this.password = "";
//     this.id = "";
//     this.img = "";
// }