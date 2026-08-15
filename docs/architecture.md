# ENERGYAI 架构

版权所有 © 2026 上海如静知华信息科技有限公司。

Vue 3 管理端和 H5 工作台通过 JWT 调用 Spring Boot REST API。领域服务 `EnergyOptimizationService` 组合基准能耗、实际用能、峰值负荷、绿电比例和电价，输出能效偏差、节能潜力与削峰建议；JPA 与 Flyway 管理 MySQL 数据，Docker Compose 负责本地编排。

生产落地时应接入企业 SSO、智能电表、BMS、生产计划、碳因子与企业模型网关，并对调度建议保留人工确认、权限控制和可追溯证据。
