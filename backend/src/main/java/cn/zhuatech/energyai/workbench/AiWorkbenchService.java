/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.energyai.workbench;

import static cn.zhuatech.energyai.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "intervals");
    var intervals = new ArrayList<>(rows(r, "intervals", 1, 500));
    Set<String> ids = new HashSet<>();
    for (var x : intervals) {
      keys(x, "id", "start", "end", "kwh", "unitRate");
      unique(ids, id(x, "id"));
      var a = instant(text(x, "start", 40));
      var b = instant(text(x, "end", 40));
      if (!a.isBefore(b)) throw new IllegalArgumentException("计量结束时间必须晚于开始");
      decimal(x, "kwh", 0, 1e9);
      decimal(x, "unitRate", 0, 1000);
    }
    intervals.sort(Comparator.comparing(x -> instant(x.get("start").toString())));
    java.time.Instant last = null;
    BigDecimal total = BigDecimal.ZERO, cost = BigDecimal.ZERO;
    List<Map<String, Object>> out = new ArrayList<>();
    for (var x : intervals) {
      var a = instant(x.get("start").toString());
      var b = instant(x.get("end").toString());
      if (last != null && a.isBefore(last)) throw new IllegalArgumentException("同一计量流的区间不能重叠");
      last = b;
      var kwh = decimal(x, "kwh", 0, 1e9);
      var rate = decimal(x, "unitRate", 0, 1000);
      var charge = kwh.multiply(rate).setScale(2, RoundingMode.HALF_UP);
      total = total.add(kwh);
      cost = cost.add(charge);
      out.add(
          Map.of(
              "id",
              x.get("id"),
              "kwh",
              kwh,
              "cost",
              charge,
              "durationSeconds",
              java.time.Duration.between(a, b).getSeconds()));
    }
    return Map.of(
        "totalKwh",
        total.stripTrailingZeros(),
        "totalCost",
        cost,
        "averageRateDefined",
        total.signum() > 0,
        "weightedAverageRate",
        total.signum() == 0 ? BigDecimal.ZERO : cost.divide(total, 6, RoundingMode.HALF_UP),
        "intervals",
        out);
  }
}
