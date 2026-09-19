/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.energyai.config;
import cn.zhuatech.energyai.model.*; import cn.zhuatech.energyai.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import java.time.LocalDate; import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Configuration public class DataInitializer {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Bean CommandLineRunner seed(OperatingUnitRepository units,WorkRecordRepository orders,ResourceRegisterRepository resources,ReviewRecordRepository reviews,UserRepository users,PasswordEncoder encoder){return args->{if(units.count()>0)return;
 var u1=units.save(new OperatingUnit("ENERGY-OPS","集团能源管理组","运营中心",180));var u2=units.save(new OperatingUnit("ENERGY-PLANT","华东工厂能源组","制造中心",120));var u3=units.save(new OperatingUnit("ENERGY-CARBON","双碳管理组","战略中心",96));
 var t1=orders.save(new WorkRecord("EO-260815-018","PLANT-SH-01","上海工厂峰谷用能优化",u1,24,16,1,LocalDate.now().plusDays(1),WorkRecord.Status.RUNNING,"电表+生产计划"));
 var t2=orders.save(new WorkRecord("EO-260815-021","CHILLER-02","冷站负荷协同优化",u2,18,8,0,LocalDate.now().plusDays(2),WorkRecord.Status.RUNNING,"BMS+气象"));
 var t3=orders.save(new WorkRecord("EO-260815-026","PV-ROOF-03","屋顶光伏消纳预测",u1,12,0,0,LocalDate.now().plusDays(3),WorkRecord.Status.RELEASED,"光伏+负荷"));
 var t4=orders.save(new WorkRecord("EO-260814-015","CARBON-HD-12","华东区域碳排复盘",u3,20,20,1,LocalDate.now(),WorkRecord.Status.COMPLETED,"能耗+碳因子"));
 resources.saveAll(List.of(new ResourceRegister("METER-01","能源计量数据平台",u1,ResourceRegister.Status.RUNNING,98),new ResourceRegister("MODEL-02","负荷预测模型",u2,ResourceRegister.Status.IDLE,94),new ResourceRegister("BMS-03","楼宇自控连接器",u2,ResourceRegister.Status.RUNNING,93),new ResourceRegister("CARBON-04","碳因子服务",u3,ResourceRegister.Status.ALARM,88)));
 reviews.saveAll(List.of(new ReviewRecord("RV-260804-032",t1,"人工复核",6,0,ReviewRecord.Result.PASSED,"程越"),new ReviewRecord("RV-260804-011",t2,"质量检查",3,0,ReviewRecord.Result.PASSED,"许知"),new ReviewRecord("RV-260803-018",t4,"结果抽查",5,1,ReviewRecord.Result.FAILED,"程越"),new ReviewRecord("RV-260804-003",t3,"上线确认",4,0,ReviewRecord.Result.PENDING,"许知")));
 String demo=encoder.encode("Demo@2026");users.saveAll(List.of(new UserAccount("operator",demo,"许知",UserAccount.Role.DOMAIN_USER,"SEARCH-OPS"),new UserAccount("planner",demo,"程越",UserAccount.Role.DOMAIN_OPERATOR,null),new UserAccount("quality",demo,"顾清",UserAccount.Role.QUALITY,null),new UserAccount("admin",encoder.encode("ZhuaTech@2026"),"系统管理员",UserAccount.Role.ADMIN,null)));};}
}
