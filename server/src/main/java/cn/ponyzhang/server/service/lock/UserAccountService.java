package cn.ponyzhang.server.service.lock;

import cn.ponyzhang.server.dto.UserAccountDto;
import cn.ponyzhang.server.entity.UserAccount;
import cn.ponyzhang.server.entity.UserAccountRecord;
import cn.ponyzhang.server.mapper.UserAccountMapper;
import cn.ponyzhang.server.mapper.UserAccountRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class UserAccountService {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private UserAccountRecordMapper userAccountRecordMapper;

    public String takeMoney(UserAccountDto userAccountDto) {
        UserAccount userAccount = userAccountMapper.selectByUserId(userAccountDto.getUserId());
        if (userAccount != null && userAccount.getAmount().doubleValue() - userAccountDto.getMoney() >= 0) {
            int res = userAccountMapper.updateByUserIdAndAmount(userAccount.getId(), new BigDecimal(userAccountDto.getMoney()));
            if (res > 0) {
                UserAccountRecord userAccountRecord = new UserAccountRecord();
                userAccountRecord.setAccountId(userAccount.getId());
                userAccountRecord.setMoney(new BigDecimal(userAccountDto.getMoney()));
                userAccountRecord.setCreateTime(new Date());
                logger.info("提取金额为：{},账户余额为：{}",
                        userAccountDto.getMoney(),
                        userAccount.getAmount().doubleValue() - userAccountDto.getMoney());
                userAccountRecordMapper.insert(userAccountRecord);
                return "取钱成功";
            } else {
                return "账户不存在或者金额不足";
            }
        } else {
            return "账户不存在或者金额不足";
        }
    }

    public String takeMoneyWithLock(UserAccountDto userAccountDto) throws Exception {
        UserAccount userAccount = userAccountMapper.selectByUserId(userAccountDto.getUserId());
        if (userAccount != null && userAccount.getAmount().doubleValue() - userAccountDto.getMoney() >= 0) {
            int res = userAccountMapper.updateByUserIdAndAmountAndVersion(userAccount.getId(),
                    new BigDecimal(userAccountDto.getMoney()),
                    userAccount.getVersion());
            if (res > 0) {
                UserAccountRecord userAccountRecord = new UserAccountRecord();
                userAccountRecord.setAccountId(userAccount.getId());
                userAccountRecord.setMoney(new BigDecimal(userAccountDto.getMoney()));
                userAccountRecord.setCreateTime(new Date());
                userAccountRecordMapper.insert(userAccountRecord);
                logger.info("提取金额为：{},账户余额为：{}",
                        userAccountDto.getMoney(),
                        userAccount.getAmount().doubleValue() - userAccountDto.getMoney());
                return "取钱成功";
            } else {
                return "账户不存在或者金额不足";
            }
        } else {
            return "账户不存在或者金额不足";
        }
    }

    public void takeMoneyWithNegativeLock(UserAccountDto dto) throws Exception {
        UserAccount userAccount = userAccountMapper.selectByUserIdAndLock(dto.getUserId());
        if (userAccount != null && userAccount.getAmount().doubleValue() - dto.getMoney() >= 0) {
            int res = userAccountMapper.updateAmountLock(userAccount.getId(), new BigDecimal(dto.getMoney()));
            if (res > 0) {
                UserAccountRecord userAccountRecord = new UserAccountRecord();
                userAccountRecord.setAccountId(userAccount.getId());
                userAccountRecord.setMoney(new BigDecimal(dto.getMoney()));
                userAccountRecord.setCreateTime(new Date());
                userAccountRecordMapper.insert(userAccountRecord);
                logger.info("悲观锁提取金额为：{},账户余额为：{}",
                        dto.getMoney(),
                        userAccount.getAmount().doubleValue() - dto.getMoney());
            }
        } else {
            throw new Exception("账户余额不足");
        }
    }
}
