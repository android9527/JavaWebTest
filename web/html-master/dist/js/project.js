var toast_opts = {
    "closeButton": true,
    "debug": false,
    "positionClass": "toast-bottom-right",
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
};

function _deleteRow(project_id, project_name) {

    swal({
        title: "确定要删除项目: " + project_name + "吗？",
        type: "warning",
        showCancelButton: true,
        closeOnConfirm: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        confirmButtonColor: "#ec6c62"

    }, function () {
        $.ajax({
            url: "/v1/project",
            type: "DELETE",
            dataType: "json",
            data: {
                "proj_id": project_id
            },
            success: function (resp, status, xhr) {
                if (resp.respcd === "0000") {
                    toastr.success("删除成功", null, toast_opts);
                    table.ajax.reload(null, false);
                } else {
                    toastr.error(resp.resperr, null, toast_opts);
                    table.ajax.reload(null, false);
                }

            },
            error: function (xhr, status, info) {
                sweetAlert("请求失败!");
            }
        });
    });
}

function _editRow(proj_id, proj_name, git_addr, desc) {
    $("#project_id_edit").val(proj_id);
    $("#project_id_edit").attr("disabled", "");
    $("#project_edit").val(proj_name);
    $("#git_edit").val(git_addr);
    $("#intro_edit").val(desc);
    $("#myEdit").modal("show");
}

table = $("#example2").DataTable({
    "responsive": {"details": {"type": "column", "target": "tr"}},
    "language": {"url": "../../dist/json/Chinese.json"},
    "fixedHeader": true,
    "bJqueryUI": true,
    "order": [[0, "desc"]],
    "paging": true,
    "lengthChange": false,
    "searching": false,
    "ordering": true,
    "info": true,
    "autoWidth": false,
    "bDestroy": true,
    "lengthMenu": [[10, 20, 50, 100, -1], [10, 20, 50, 100, "ALL"]],
    "ajax": {
        url: get_pm_data,
        type: "GET",
        dataSrc: function (data) {
            console.log("---------> " + data);
            console.log(typeof data);
            for (var i = 0; i < data.length; i++) {
                var entry = data[i];
                console.log(entry);
                var date = new Date(parseInt(entry.time)); //传个时间戳过去就可以了
                var Y = date.getFullYear() + '-';
                var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                var D = date.getDate() + ' ';
                var h = date.getHours() + ':';
                var m = date.getMinutes() + ':';
                var s = date.getSeconds();
                if (s < 10) {
                    s = "0" + s;
                }
                entry.time = Y + M + D + h + m + s;
            }
            return data;
        },
        error: function (data) {
            sweetAlert("请求失败!");
        }
    },
    columns: [
        {data: 'id'},
        {data: 'time'},
        {data: 'value'},
        {data: 'temperature'},
        {data: 'humidity'}
    ],
    "columnDefs": [
        {responsivePriority: 1, target: 1},
        {responsivePriority: 2, target: 2},
        {"width": "5%", "target": 0},
        // {targets: [3], visible: false},
        {className: "dt-center", "targets": [0, 1, 2, 3, 4]}
        // {
        //     "targets": -1,
        //     "data": "",
            // "render": function (data, type, row, meta) {
            //     var editstr = '<button class="btn btn-success btn-xs edit_onmouse_func" onclick="_editRow('${row["id"]}', '${row["name"]}', '${row["git_addr"]}', '${row["info"]}' )">编辑</button>'
            //     var delstr = '<button class="btn btn-info btn-xs" onclick="_deleteRow('${row["id"]}', '${row["name"]}')">删除</button>'
            //     return (editstr + delstr);
            // }
        // },
    ],

});

$('#contentAdd').submit(function (e) {
    e.preventDefault();
    var form = $(this)[0];
    $.ajax({
        url: "/v1/project",
        type: "POST",
        traditional: true,
        data: {
            "name": form.project_name.value,
            "git_addr": form.git_addr.value,
            "info": form.desc.value
        },
        dataType: "json",
        success: function (resp, status, xhr) {
            if (resp.respcd === "0000") {
                $("#myAdd").modal("hide");
                toastr.success("添加成功", null, toast_opts);
                table.ajax.reload(null, false);
            } else {
                toastr.error("添加失败：" + resp.resperr, null, toast_opts);
                table.ajax.reload(null, false);
            }

        },
        error: function (xhr, status, info) {
            toastr.error("请求失败：" + status, null, toast_opts);
        }
    });
});


$('#contentEdit').submit(function (e) {
    var data = {
        "proj_id": $("#project_id_edit").val(),
        "name": $("#project_edit").val(),
        "git_addr": $("#git_edit").val(),
        "info": $("#intro_edit").val()
    };
    e.preventDefault();
    $.ajax({
        url: "/v1/project",
        type: "PUT",
        async: true,
        data: data,
        dataType: "json",
        success: function (resp, status, xhr) {
            if (resp.respcd === "0000") {
                $("#myEdit").modal("hide");
                toastr.success("修改成功", null, toast_opts);
            } else {
                toastr.error("修改失败：" + resp.resperr, null, toast_opts);
            }
            table.ajax.reload(null, false);

        },
        error: function (xhr, status, info) {
            toastr.error("请求失败：" + status, null, toast_opts);
        }
    });

});
