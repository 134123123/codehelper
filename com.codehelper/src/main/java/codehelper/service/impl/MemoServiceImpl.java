package codehelper.service.impl;

import codehelper.dao.MemoMapper;
import codehelper.model.dto.MemoAddDTO;
import codehelper.model.dto.MemoUpdateDTO;
import codehelper.model.entity.Memo;
import codehelper.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoServiceImpl implements MemoService {

    @Autowired
    private MemoMapper memoMapper;

    @Override
    public void add(Long userId, MemoAddDTO dto) {
        Memo memo = new Memo();
        memo.setUserId(userId);
        memo.setTitle(dto.getTitle());
        memo.setContent(dto.getContent());
        memo.setIsDeleted(0);
        memoMapper.insert(memo);
    }

    @Override
    public List<Memo> listByUserId(Long userId) {
        return memoMapper.selectByUserId(userId);
    }

    @Override
    public void update(Long userId, MemoUpdateDTO dto) {
        Memo memo = new Memo();
        memo.setId(dto.getId());
        memo.setUserId(userId);
        memo.setTitle(dto.getTitle());
        memo.setContent(dto.getContent());
        memoMapper.update(memo);
    }

    @Override
    public void delete(Long userId, Long memoId) {
        memoMapper.delete(memoId, userId);
    }
}