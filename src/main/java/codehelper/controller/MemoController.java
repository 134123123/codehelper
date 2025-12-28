package codehelper.controller;

import codehelper.model.dto.MemoAddDTO;
import codehelper.model.dto.MemoUpdateDTO;
import codehelper.model.entity.Memo;
import codehelper.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memo")
public class MemoController {

    @Autowired
    private MemoService memoService;

    // 获取当前用户的备忘录列表
    @GetMapping
    public List<Memo> list() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memoService.listByUserId(userId);
    }

    // 添加备忘录
    @PostMapping
    public String add(@RequestBody MemoAddDTO dto) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memoService.add(userId, dto);
        return "添加成功";
    }

    // 更新备忘录
    @PutMapping
    public String update(@RequestBody MemoUpdateDTO dto) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memoService.update(userId, dto);
        return "更新成功";
    }

    // 删除备忘录
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memoService.delete(userId, id);
        return "删除成功";
    }
}