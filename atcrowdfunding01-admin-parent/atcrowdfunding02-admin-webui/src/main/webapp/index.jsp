<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>测试页面</title>
  <script type="text/javascript" src="js/jquery/jquery-2.1.1.min.js"></script>
  <script type="text/javascript" src="layer/layer.js"></script>
  <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
  <script type="text/javascript">
    $(function (){

      // 准备好要发送到服务端的数组
      var array = [5,8,12];

      // 将JSON数组转换为JSON字符串
      var requestBody = JSON.stringify(array);

      $("#btn02").click(function (){
        $.ajax({
          "url": "send/array2.html",
          "type": "post",
          "data": requestBody,
          contentType: "application/json;character=UTF-8",
          "dataType": "text",
          "success": function (response){
            alert(response);
          },
          "error": function (response){
            alert(response);
          }
        });
      });

      $("#btn01").click(function (){
        $.ajax({
          "url": "send/array.html",
          "type": "post",
          "data": {
            "array":[5,8,12]
          },
          "dataType": "text",
          "success": function (response){
            alert(response);
          },
          "error": function (response){
            alert(response);
          }
        });
      });
  
      $("#btn04").click(function (){
        layer.msg("layer的弹框");
      });
      
      $("#btn03").click(function (){
        // 准备要发送的数据
        var student = {
          stuId: 5,
          stuName: "tom",
          address: {
            province: "江苏",
            city: "南京",
            street: "秣陵街道"
          },
          subjectList: [
            {
              subName: "java",
              subScore: 100
            },
            {
              subName: "c++",
              subScore: 98
            }
          ],
          map:{
            key1: "value1",
            key2: "value2"
          }
        };
        // 将JSON对象转换为JSON字符串
        var requestBody = JSON.stringify(student);

        // 发送Ajax请求
        $.ajax({
          url: "send/compose/object.json",
          type: "post",
          data: requestBody,
          contentType: "application/json;character=UTF-8",
          dataType: "json",
          success: function (resp){
            console.log(resp);
          },
          error: function (resp) {
            console.log(resp)
          }
        })
      })
    })


  </script>
</head>
<body>
  <button id="btn01">Send text</button>
  <button id="btn02">Send text2</button>
  <button id="btn03">Send text3</button>
  <button id="btn04">Send text4</button>
  <a href="${pageContext.request.contextPath}/admin/to/login/page.html">点击进入登录页面</a>
  <form action="/test/ssm.html" method="post">
    <input type="submit" class="button">
  </form>
</body>
</html>