package com.greatwall.recharge.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greatwall.clientapi.service.JudianService;
import com.greatwall.clientapi.service.LiulService;
import com.greatwall.clientapi.service.ShunpayService;
import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dao.UserDao;
import com.greatwall.platform.system.dto.User;
import com.greatwall.recharge.dao.ConsumeDao;
import com.greatwall.recharge.dao.ProductDao;
import com.greatwall.recharge.dao.RechargeDao;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.dto.ConsumeConditions;
import com.greatwall.recharge.dto.Recharge;
import com.greatwall.recharge.dto.RechargeConditions;
import com.greatwall.recharge.service.RechargeConsumeService;
import com.greatwall.util.MathUtil;
import com.greatwall.util.RMSConstant;

@Service("rechargeConsumeService")
public class RechargeConsumeServiceImpl implements RechargeConsumeService {

	@Autowired
	private RechargeDao rechargeDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ShunpayService shunpayService;
	@Autowired
	private LiulService liulService;

	ExecutorService fixedThreadPool = Executors.newFixedThreadPool(30);

//	@Transactional(rollbackFor=Exception.class)
//	public Boolean offsetConsume(Consume consume) throws Exception{
//		
//		return true;
//	}

	@Transactional(rollbackFor=Exception.class)
	public Boolean saveRecharge(Recharge recharge,Integer agentId) throws Exception{
		User user = new User();
		user.setUserId(recharge.getUserId());

		User resUser = userDao.getUser(user);

		double balance =MathUtil.add(getTypeBalance(resUser,recharge.getType()),recharge.getRechargeAmount(), 2);
		if(MathUtil.compareTo(balance, new Double(0)) == -1){
			throw new DaoException(RMSConstant.ERROR_CODE_102+" 充值后金额为："+balance);
		}
		//		int updateBalanceCount = userDao.updateBalance(resUser);
		int updateBalanceCount = 0;
		if(RMSConstant.PRODUCT_TYPE_FLOW.equals(recharge.getType())){
			updateBalanceCount = userDao.updateFlowBalance(recharge.getRechargeAmount(),resUser.getVersion(),resUser.getUserId());
		}
		if(RMSConstant.PRODUCT_TYPE_PHONE.equals(recharge.getType())){
			updateBalanceCount = userDao.updateBalance(recharge.getRechargeAmount(),resUser.getVersion(),resUser.getUserId());
		}
		if(updateBalanceCount!=1){
			throw new DaoException(RMSConstant.ERROR_CODE_101+" 更新数量为："+updateBalanceCount);
		}
		recharge.setCreateTime(new Date());
		recharge.setRemark(recharge.getRemark()+"充值后金额为"+balance);
//		if(agentId!=null && agentId>0){
//			recharge.setRemark("会从操作者扣除相应金额");
//		}else{
//			recharge.setRemark("充值后金额为"+balance);
//		}
		int updateRechargeCount = rechargeDao.insert(recharge);
		if(updateRechargeCount!=1){
			throw new DaoException(RMSConstant.ERROR_CODE_103+"插入充值消息记录数："+updateRechargeCount);
		}

		if(agentId!=null && agentId>0){
			User agent = new User();
			agent.setUserId(agentId);

			agent = userDao.getUser(agent);
			double agentBalance =MathUtil.sub(getTypeBalance(agent,recharge.getType()),recharge.getRechargeAmount(), 2);
			if(MathUtil.compareTo(agentBalance, new Double(0)) == -1){
				throw new DaoException(RMSConstant.ERROR_CODE_102+" 充值后代理商余额为："+agentBalance);
			}
			int updateAgentBalance = 0;
			if(RMSConstant.PRODUCT_TYPE_FLOW.equals(recharge.getType())){
				updateBalanceCount = userDao.updateFlowBalance(-recharge.getRechargeAmount(),agent.getVersion(),agent.getUserId());
			}
			if(RMSConstant.PRODUCT_TYPE_PHONE.equals(recharge.getType())){
				updateBalanceCount = userDao.updateBalance(-recharge.getRechargeAmount(),agent.getVersion(),agent.getUserId());
			}

			if(updateAgentBalance!=1){
				throw new DaoException(RMSConstant.ERROR_CODE_101+" 代理商更新数量为："+updateAgentBalance);
			}
			//TODO 记录消费记录
		}
		//		throw new Exception(RMSConstant.ERROR_CODE_103+"事物管理测试");
		return true;
	}
	
	private Double getTypeBalance(User user ,String type) throws Exception{
		if(RMSConstant.PRODUCT_TYPE_FLOW.equals(type)){
			return user.getFlowBalance();
		}
		if(RMSConstant.PRODUCT_TYPE_PHONE.equals(type)){
			return user.getBalance();
		}
		throw new Exception("type充值类型不能为空");
	}
	//	public List<Recharge> getSelective(Recharge recharge){
	//		return rechargeDao.getSelective(recharge);
	//	}
	//	
	public List<RechargeConditions> getRechargesPage(RechargeConditions rechargeConditions,PageParameter page) throws DaoException{
		return rechargeDao.getRechargesPage(rechargeConditions, page);
	}


	@Autowired
	private ConsumeDao consumeDao;

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Boolean addConsume(Consume consume) throws Exception {

		User user = new User();
		user.setUserId(consume.getUserId());
		user = userDao.getUser(user);

		if(RMSConstant.PRODUCT_TYPE_PHONE.equals(consume.getConsumeType())){
			consume.setDiscount(user.getDiscountPhone());
		}else if(RMSConstant.PRODUCT_TYPE_FLOW.equals(consume.getConsumeType())){
			consume.setDiscount(user.getDiscountFlow());
		}else{
			consume.setDiscount(RMSConstant.DEFAULT_DISCOUNT);
		}

		double discountR = MathUtil.div(new Double(consume.getDiscount()), new Double(100), 2);
		consume.setPayment(MathUtil.mul(consume.getConsumeAmount(), discountR, 2));

		double balance = MathUtil.sub(this.getTypeBalance(user, consume.getConsumeType()),consume.getPayment(), 2);
		if(MathUtil.compareTo(balance, new Double(0)) == -1){
			throw new DaoException(RMSConstant.ERROR_CODE_102+" 消费后余额为："+balance);
		}

		int updateBalanceCount = 0;
		if(RMSConstant.PRODUCT_TYPE_FLOW.equals(consume.getConsumeType())){
			updateBalanceCount = userDao.updateFlowBalance(-consume.getPayment(),user.getVersion(),user.getUserId());
		}
		if(RMSConstant.PRODUCT_TYPE_PHONE.equals(consume.getConsumeType())){
			updateBalanceCount = userDao.updateBalance(-consume.getPayment(),user.getVersion(),user.getUserId());
		}

		if(updateBalanceCount!=1){
			throw new DaoException(RMSConstant.ERROR_CODE_101+" 更新数量为："+updateBalanceCount);
		}

		consume.setCreateTime(new Date());
		consume.setBalance(balance);
		consume.setConsumeId(UUID.randomUUID().toString().replaceAll("-", ""));
		
		//TODO 调用电话或流量接口充值
		if("phone".equals(consume.getConsumeType())){
			
			if(RMSConstant.INTERFACE_NAME_SHUNPAY.equals(consume.getInterfaceName())){
				if(shunpayService.sendMsg(consume)){
					consume.setState(RMSConstant.CONSUME_STATE_SENDED);
				}else{
					throw new DaoException(RMSConstant.ERROR_CODE_104+" "+consume.getRemark());
				}
			}else{
				throw new Exception("未找到对应接口");
			}
		}else if("flow".equals(consume.getConsumeType())){
			if(RMSConstant.INTERFACE_NAME_LIUL.equals(consume.getInterfaceName())){
				if(liulService.sendMsg(consume)){
					consume.setState(RMSConstant.CONSUME_STATE_SENDED);
				}else{
					throw new DaoException(RMSConstant.ERROR_CODE_104+" "+consume.getRemark());
				}
			}else{
				throw new Exception("未找到对应接口");
			}
		}
		
		int insertConsume = consumeDao.insert(consume);
		if(insertConsume !=1 ){
			throw new DaoException(RMSConstant.ERROR_CODE_103+" 插入消费记录数："+insertConsume);
		}

		//调用接口处理，用于接口隔离
		//run(fixedThreadPool,consume);

		return true;
	}


	private void run(ExecutorService threadPool,final Consume consume) {
		threadPool.execute(new Runnable() {  
			@Override
			public void run() {  

				try {  
					if(shunpayService.sendMsg(consume)){
						consume.setState(RMSConstant.CONSUME_STATE_SENDED);
					}else{
						consume.setState(RMSConstant.CONSUME_STATE_SEND_FAIL);
					}
				} catch (Exception e) {  
					e.printStackTrace();  
					consume.setState(RMSConstant.CONSUME_STATE_SEND_FAIL);
					consume.setRemark(e.getMessage().length()>500?e.getMessage().substring(0, 499):e.getMessage());
				}finally{
					consumeDao.updateState(consume);
				}


			}  
		});  
		//threadPool.shutdown();// 任务执行完毕，关闭线程池  
	}
	
	
	public Boolean confirmConsume(String consumeId,String opstatus){
		Consume consume = consumeDao.getConsume(consumeId);
		if(consume!=null){
//			consume.setState("1".equals(opstatus)?RMSConstant.CONSUME_STATE_SUC:RMSConstant.CONSUME_STATE_FAIL);
			consume.setState(opstatus);
			consumeDao.updateState(consume);
			return true;
		}else{
			return false;
		}
	}
	
	public Consume getConsume(String consumeId){
		return consumeDao.getConsume(consumeId);
	}

	@Override
	public List<ConsumeConditions> getConsumesPage(ConsumeConditions consume,PageParameter page) throws DaoException {
		return consumeDao.getConsumes(consume, page);
	}
	
	public List<ConsumeConditions> getConsumesByStatePage(ConsumeConditions consume,PageParameter page) throws DaoException {
		return consumeDao.getConsumesByStatePage(consume, page);
	}
}
