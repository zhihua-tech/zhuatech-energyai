/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.energyai.repository; import cn.zhuatech.energyai.model.UserAccount; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface UserRepository extends JpaRepository<UserAccount,Long>{Optional<UserAccount> findByUsername(String username);}
