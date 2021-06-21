// 生成树形结构的函数
function generateTree() {
    // 1.准备生成树形结构的JSON数据，数据来源是发送Ajax请求得到
    $.ajax({
        url: "menu/get/whole/tree.do",
        type: "post",
        dataType: "json",
        success: function (resp) {
            var result = resp.result;
            if (result === "SUCCESS") {

                // 2.创建json对象用于存储对zTree所做的设置
                var setting = {
                    view: {
                        addDiyDom: myAddDiyDom,
                        addHoverDom: myAddHoverDom,
                        removeHoverDom: myRemoveHoverDom
                    },
                    data: {
                        key:{
                            url: "nothing", // url值改为不存在的属性名，则点击页面响应菜单时不会跳转
                        }
                    }
                };

                // 3.从响应体中获取用来生成树形结构的JSON数据
                var zNodes = resp.data;

                // 4.初始化树形结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if (result === "FAILED") {
                layer.msg(resp.message);
            }
        },
    });
}

function myAddDiyDom(treeId, treeNode) {

    // treeId是整个树形结构附着的ul标签的id
    console.log("treeId="+treeId);

    // 当前树形节点的全部数据、
    console.log(treeNode);

    /*    zTree 生成 id 的规则
        例子： treeDemo_7_ico
        解析： ul 标签的 id_当前节点的序号_功能
        提示： “ul 标签的 id_当前节点的序号” 部分可以通过访问 treeNode 的 tId 属性得到
        根据 id 的生成规则拼接出来 span 标签的 id
    */
    var spanId = treeNode.tId + "_ico";

    // 根据控制图标的span标签的id找到这个span标签
    // 删除旧的class
    // 添加新的class
    $("#"+spanId).removeClass().addClass(treeNode.icon);
}

// 在鼠标移入节点范围添加按钮组
function myAddHoverDom(treeId, treeNode){

    //设置id
    var btnGroupId = treeNode.tId + "_btnGrp";

    // html标签
    var addBtn = "<a id='"+treeNode.id+"' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn = "<a id='"+treeNode.id+"' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title=' 删 除 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn = "<a id='"+treeNode.id+"' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title=' 修 改 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";

    // 判断是否存在
    if($("#"+btnGroupId).length > 0){
        return;
    }

    // 获取当前节点的级别
    var level = treeNode.level;

    //  存储拼装的按钮代码
    var btnHTML = "";

    if(level === 0){
        btnHTML = addBtn;
    }

    if(level === 1){
        btnHTML = addBtn + " " + editBtn;

        // 获取叶子节点数量
        var length = treeNode.children.length;

        if(length === 0){
            btnHTML = btnHTML + " " + removeBtn;
        }
    }

    // 叶子节点
    if(level === 2){
        btnHTML = editBtn + " " + removeBtn;
    }


    // 找到按钮组超链接
    var anchorId = treeNode.tId + "_a";

    // 附着元素
    $("#"+anchorId).after("<span id='"+btnGroupId+"'>"+btnHTML +"</span>");

}

// 在鼠标离开节点范围删除按钮组

function myRemoveHoverDom(treeId, treeNode){

    //设置id
    var btnGroupId = treeNode.tId + "_btnGrp";

    // 附着元素
    $("#"+btnGroupId).remove();

}