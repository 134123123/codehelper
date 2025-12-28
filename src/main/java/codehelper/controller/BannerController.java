package codehelper.controller;

import codehelper.model.dto.BannerAddDTO;
import codehelper.model.dto.BannerUpdateDTO;
import codehelper.model.entity.Banner;
import codehelper.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * Android前端展示接口（无需权限）
     */
    @GetMapping("/list")
    public List<Banner> getEnabledBanners() {
        return bannerService.getEnabled();
    }

    /**
     * 管理员获取所有Banner（含禁用）
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Banner> getAllBanners() {
        return bannerService.getAll();
    }

    /**
     * 管理员添加Banner
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String addBanner(@RequestBody BannerAddDTO dto) {
        bannerService.add(dto);
        return "添加成功";
    }

    /**
     * 管理员更新Banner
     */
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String updateBanner(@RequestBody BannerUpdateDTO dto) {
        bannerService.update(dto);
        return "更新成功";
    }

    /**
     * 管理员删除Banner
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBanner(@PathVariable Long id) {
        bannerService.delete(id);
        return "删除成功";
    }
}