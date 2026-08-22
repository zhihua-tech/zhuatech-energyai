/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.energyai;
import cn.zhuatech.energyai.service.EnergyOptimizationService; import org.junit.jupiter.api.Test; import java.math.BigDecimal; import static org.assertj.core.api.Assertions.assertThat;
class EnergyOptimizationServiceTests { private final EnergyOptimizationService service=new EnergyOptimizationService();
 @Test void identifiesHighSavingOpportunity(){var r=service.optimize(new EnergyOptimizationService.Request("PLANT-SH-01",new BigDecimal("8200"),new BigDecimal("10100"),1600,new BigDecimal("2150"),8,new BigDecimal("1.08")));assertThat(r.optimizationGrade()).isEqualTo("HIGH");assertThat(r.savingPotentialKwh()).isGreaterThan(new BigDecimal("1000"));assertThat(r.actions()).hasSizeGreaterThanOrEqualTo(2);}
 @Test void keepsEfficientSiteOnMonitoringPlan(){var r=service.optimize(new EnergyOptimizationService.Request("PLANT-SZ-02",new BigDecimal("8000"),new BigDecimal("7850"),1700,new BigDecimal("900"),38,new BigDecimal("0.68")));assertThat(r.optimizationGrade()).isEqualTo("LOW");assertThat(r.recommendation()).contains("周度");}}
