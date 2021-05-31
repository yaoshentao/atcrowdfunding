package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Student;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestHandler {
    @Autowired
    private AdminService adminService;

    private final Logger logger = LoggerFactory.getLogger(TestHandler.class);

    @ResponseBody
    @RequestMapping("/send/array.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array){
        for (Integer number : array){
            System.out.println(number);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/array2.html")
    public String testReceiveArrayTwo(@RequestBody List<Integer> array){
        for (Integer number : array){
            logger.info("number="+number);
        }
        return "success";
    }
    
    @RequestMapping("/test/ssm.html")
    public String testSSM(ModelMap modelMap, HttpServletRequest request){
        boolean judgeRequest = CrowdUtil.judgeRequestType(request);
        logger.info("judgeRequest:"+judgeRequest);

        List<Admin> adminList = adminService.getAllAdmin();
        modelMap.addAttribute("adminList", adminList);
//        System.out.println(10/0);
        String a=null;
        System.out.println(a.length());
        return "target";
    }

    @ResponseBody
    @RequestMapping("/send/compose/object.json")
    public ResultEntity<Student> testReceiveComplicatedObject(@RequestBody Student student, HttpServletRequest request){
        boolean judgeRequest = CrowdUtil.judgeRequestType(request);
        String a=null;
        System.out.println(a.length());
        logger.info("judgeRequest:"+judgeRequest);
        logger.info(student.toString());
        return ResultEntity.successWithData(student);
    }
}
