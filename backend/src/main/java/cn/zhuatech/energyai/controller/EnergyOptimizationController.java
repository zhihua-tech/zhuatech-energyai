/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.energyai.controller;
import cn.zhuatech.energyai.common.ApiResponse; import cn.zhuatech.energyai.service.EnergyOptimizationService; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/ai/energy") @PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')")
public class EnergyOptimizationController { private final EnergyOptimizationService service; public EnergyOptimizationController(EnergyOptimizationService service){this.service=service;} @PostMapping("/optimize") public ApiResponse<EnergyOptimizationService.Result> optimize(@Valid @RequestBody EnergyOptimizationService.Request request){return ApiResponse.ok("能源优化方案已生成",service.optimize(request));} }
