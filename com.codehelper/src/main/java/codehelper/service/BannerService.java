package codehelper.service;

import codehelper.model.dto.BannerAddDTO;
import codehelper.model.dto.BannerUpdateDTO;
import codehelper.model.entity.Banner;

import java.util.List;

public interface BannerService {
    void add(BannerAddDTO dto);
    List<Banner> getEnabled();
    List<Banner> getAll();
    void update(BannerUpdateDTO dto);
    void delete(Long id);
}
