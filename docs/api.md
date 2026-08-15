# ENERGYAI API 摘要

版权所有 © 2026 上海如静知华信息科技有限公司。

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/auth/login` | 登录并获取 JWT |
| GET | `/api/admin/dashboard` | 知华能源优化 AI 平台运营控制台 |
| GET | `/api/admin/work-orders` | 能源优化任务列表 |
| GET | `/api/shopfloor/dashboard` | 能源专员工作台 |
| POST | `/api/shopfloor/work-orders/{id}/reports` | 提交节能处置反馈 |
| POST | `/api/ai/energy/optimize` | 能效偏差、节能潜力和削峰建议 |
| POST | `/api/shopfloor/ai-risk-assessment` | AI 功能上线风险初筛 |

除登录外均需 `Authorization: Bearer <token>`。社区演示实现不调用外部模型，不需要 API Key。
