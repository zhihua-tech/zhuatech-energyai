/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.energyai.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnergyOptimizationService {
    public Result optimize(Request request) {
        BigDecimal intensity = request.actualKwh().divide(BigDecimal.valueOf(Math.max(1, request.productionUnits())), 3, RoundingMode.HALF_UP);
        BigDecimal deviation = request.actualKwh().subtract(request.baselineKwh()).divide(request.baselineKwh(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        int score = 0;
        if (deviation.compareTo(BigDecimal.valueOf(15)) >= 0) score += 40; else if (deviation.compareTo(BigDecimal.valueOf(5)) >= 0) score += 20;
        if (request.peakLoadKw().compareTo(request.actualKwh().multiply(new BigDecimal("0.18"))) > 0) score += 25;
        if (request.renewableRatioPercent() < 15) score += 20;
        if (request.tariffPerKwh().compareTo(new BigDecimal("0.95")) > 0) score += 15;
        score = Math.min(100, score);
        String grade = score >= 65 ? "HIGH" : score >= 35 ? "MEDIUM" : "LOW";
        BigDecimal savingPotential = request.actualKwh().multiply(BigDecimal.valueOf(score >= 65 ? 0.12 : score >= 35 ? 0.07 : 0.03)).setScale(1, RoundingMode.HALF_UP);
        BigDecimal peakReduction = request.peakLoadKw().multiply(BigDecimal.valueOf(score >= 65 ? 0.15 : 0.08)).setScale(1, RoundingMode.HALF_UP);
        List<String> actions = new ArrayList<>();
        if (deviation.signum() > 0) actions.add("核查高耗能工序与空载设备");
        if (request.renewableRatioPercent() < 15) actions.add("提高低谷时段与绿电消纳比例");
        if (request.tariffPerKwh().compareTo(new BigDecimal("0.95")) > 0) actions.add("执行峰谷负荷转移模拟");
        if (actions.isEmpty()) actions.add("保持当前能效基线并持续监测");
        return new Result(request.siteCode(), intensity, deviation.setScale(1, RoundingMode.HALF_UP), score, grade, savingPotential, peakReduction,
            grade.equals("HIGH") ? "启动节能专项并安排能源经理复核" : "纳入周度能效优化", actions);
    }
    public record Request(@NotBlank String siteCode, @DecimalMin("1") BigDecimal baselineKwh,
                          @DecimalMin("0") BigDecimal actualKwh, @Min(1) int productionUnits,
                          @DecimalMin("0") BigDecimal peakLoadKw, @Min(0) @Max(100) int renewableRatioPercent,
                          @DecimalMin("0") BigDecimal tariffPerKwh) {}
    public record Result(String siteCode, BigDecimal energyIntensity, BigDecimal deviationPercent,
                         int opportunityScore, String optimizationGrade, BigDecimal savingPotentialKwh,
                         BigDecimal recommendedPeakReductionKw, String recommendation, List<String> actions) {}
}
