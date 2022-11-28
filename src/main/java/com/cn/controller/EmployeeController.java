package com.cn.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.R;
import com.cn.entity.Employee;
import com.cn.service.EmployeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;


    //员工登录
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        String password = employee.getPassword();
        //1.将提交的密码进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3.如果没有查询到则返回登录失败结果
        if (emp == null){
            return R.error("登录失败");
        }

        //4.密码比对,如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }

        //5.查看员工状态,如果是已禁用状态,则返回员工已被禁用
        if (emp.getStatus() == 0){
            return R.error("员工已被禁用");
        }

        //6.登录成功,将袁工id存入Session中,并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
    }

    //员工退出
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的信息
        request.getSession().removeAttribute("employee");
        return R.success("退出登录");
    }


    //新增员工
    @PostMapping
    public R<String> save(HttpServletRequest request ,@RequestBody Employee employee){
        //设置初始密码123456,并进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        //设置入职时间和更新时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

//        //获得当前登录用户的id
//        Long emp = (Long) request.getSession().getAttribute("employee");
//
//        employee.setCreateUser(emp);
//        employee.setUpdateUser(emp);

        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    //分页
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        //分页构造器
        Page pageInfo = new Page(page,pageSize);

        //条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);

        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, queryWrapper);


        return R.success(pageInfo);
    }

    //根据id修员工信息
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){

//            Long empId = (Long) request.getSession().getAttribute("employee");
//            employee.setUpdateTime(LocalDateTime.now());
//            employee.setUpdateUser(empId);

            if (employee.getId() != 1){
            employeeService.updateById(employee);

            return R.success("修改员工信息成功");
                                     }

            return R.success("修改失败,不能禁用管理员账号");
    }


    //根据id查询员工信息
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }

}
