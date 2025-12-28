package codehelper.service;

import codehelper.model.dto.MemoAddDTO;
import codehelper.model.dto.MemoUpdateDTO;
import codehelper.model.entity.Memo;

import java.util.List;

public interface MemoService {
    void add(Long userId, MemoAddDTO dto);
    List<Memo> listByUserId(Long userId);
    void update(Long userId, MemoUpdateDTO dto);
    void delete(Long userId, Long memoId);
}