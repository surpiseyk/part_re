package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Money;
import com.example.service.MoneyService;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.example.exception.CustomException;
import cn.hutool.core.util.StrUtil;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/money")
public class MoneyController {
    @Resource
    private MoneyService moneyService;
    @Resource
    private HttpServletRequest request;

    public User getUser() {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("-1", "请登录");
        }
        return user;
    }

    @PostMapping
    public Result<?> save(@RequestBody Money money) {
        return Result.success(moneyService.save(money));
    }

    @PutMapping
    public Result<?> update(@RequestBody Money money) {
        return Result.success(moneyService.updateById(money));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        moneyService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(moneyService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(moneyService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Money> query = Wrappers.<Money>lambdaQuery().orderByDesc(Money::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Money::getName, name);
        }
        return Result.success(moneyService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<Money> all = moneyService.list();
        for (Money obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("缴费时间", obj.getCreateTime());
            row.put("描述", obj.getDescription());
            row.put("用户ID", obj.getId());
            row.put("缴费数", obj.getMoney());
            row.put("用户名", obj.getName());
            row.put("用户职位", obj.getRole());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("缴费记录信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    @GetMapping("/upload/{fileId}")
    public Result<?> upload(@PathVariable String fileId) {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/static/file/";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String file = fileNames.stream().filter(name -> name.contains(fileId)).findAny().orElse("");
        List<List<Object>> lists = ExcelUtil.getReader(basePath + file).read(1);
        List<Money> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            Money obj = new Money();
            obj.setCreateTime(DateUtil.parseDateTime((String) row.get(1)));
            obj.setDescription((String) row.get(2));
            obj.setMoney((String) row.get(3));
            obj.setName((String) row.get(4));
            obj.setRole((String) row.get(5));

            saveList.add(obj);
        }
        moneyService.saveBatch(saveList);
        return Result.success();
    }

}
