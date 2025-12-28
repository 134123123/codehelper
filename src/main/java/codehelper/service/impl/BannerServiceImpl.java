package codehelper.service.impl;

import codehelper.dao.BannerMapper;
import codehelper.model.dto.BannerAddDTO;
import codehelper.model.dto.BannerUpdateDTO;
import codehelper.model.entity.Banner;
import codehelper.service.BannerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    // 添加方法
    @Override
    public void add(BannerAddDTO dto) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(dto, banner);
        Date now = new Date();
        banner.setCreateTime(now);
        banner.setUpdateTime(now); // 新增时创建和更新时间一致
        bannerMapper.insert(banner);
    }

    // 更新方法
    @Override
    public void update(BannerUpdateDTO dto) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(dto, banner);
        banner.setUpdateTime(new Date()); // 更新时仅更新时间
        bannerMapper.update(banner);
    }


    @Override
    public List<Banner> getEnabled() {
        return bannerMapper.selectEnabled();
    }

    @Override
    public List<Banner> getAll() {
        return bannerMapper.selectAll();
    }


    @Override
    public void delete(Long id) {
        bannerMapper.deleteById(id);
    }
}