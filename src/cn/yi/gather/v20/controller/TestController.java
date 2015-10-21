package cn.yi.gather.v20.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.ItemInstanceLog;
import cn.yi.gather.v20.entity.ItemInstanceLog.OpType;
import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.ItemInstance.ItemInstanceStatus;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.entity.EmailTemplate;
import cn.yi.gather.v20.service.IEmailTemplateService;
import cn.yi.gather.v20.service.IItemInstanceLogService;
import cn.yi.gather.v20.service.IItemInstanceService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.INoticeService;
import cn.yi.gather.v20.service.ISkuService;
import cn.yi.gather.v20.service.ITestService;
import cn.yi.gather.v20.service.IUserAccountService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IUserotherService;
import cn.yi.gather.v20.service.IUserotherinfoService;
import cn.yi.gather.v20.service.serviceImpl.EventlogService;

import com.common.Jr;
import com.tools.utils.JSONUtil;

/**
 * 接口测试专用
 * @author Lee.J.Eric
 *
 */
@Controller("testControllerV20")
@RequestMapping(value = "v20/test")
public class TestController {
	
	@Resource(name = "noticeServiceV20")
	private INoticeService noticeService;
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name = "userotherServiceV20")
	private IUserotherService userotherService;
	
	@Resource(name = "userotherinfoServiceV20")
	private IUserotherinfoService userotherinfoService;
	
	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;
	
	@Resource(name = "testServiceV20")
	private ITestService testService;
	
	@Resource
	private EventlogService eventlogService;
	
	@Resource
	private IUserAccountService userAccountService;
	
	@Resource
	private IItemInstanceService itemInstanceService;
	@Resource
	private IItemInstanceLogService itemInstanceLogService;
	
	@Resource
	private ISkuService skuService;
	
	
	
	@Resource
	private IEmailTemplateService emailTemplateService;
	
	@RequestMapping(value = "importdata")
	public void importData(HttpServletResponse response,Integer min,Integer max){
		Jr jr = new Jr();
		jr.setMethod("importdata");
		try {
//			testService.importData();
//			testService.vipNumLog(min, max);
			jr.setMsg("finished");
		} catch (Exception e) {
			// TODO: handle exception
			jr.setMethod("import data failed:"+e);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "add_vipnumlog")
	public void addVipNumLog(HttpServletResponse response,Integer min,Integer max){
		Jr jr = new Jr();
		jr.setMethod("importdata");
		try {
			testService.vipNumLog(min, max);
			jr.setMsg("finished");
		} catch (Exception e) {
			// TODO: handle exception
			jr.setMethod("import data failed:"+e);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value= "action")
	public void action(HttpServletResponse response,String type){
		Jr jr = new Jr();
		jr.setMethod("action");
		try {
			StringBuilder sb0 = new StringBuilder();
			File file0 = new File("C:/Users/Lee.J.Eric/Documents/2.0.html");
			InputStream fis0 = new FileInputStream(file0);
			InputStreamReader read0 = new InputStreamReader(fis0,"utf8");//考虑到编码格式
            BufferedReader bufferedReader0 = new BufferedReader(read0);
            String lineTxt0 = null;
            while((lineTxt0 = bufferedReader0.readLine()) != null){
            	sb0.append(lineTxt0);
            }
            read0.close();
            EmailTemplate template0 = new EmailTemplate();
            template0.setContents(sb0.toString());
            template0.setTitle("2.0升级模版");
            template0.setCode("2.0");
            emailTemplateService.saveOrUpdate(template0);
            
            StringBuilder sb1 = new StringBuilder();
			File file1 = new File("C:/Users/Lee.J.Eric/Documents/xinren.html");
			InputStream fis1 = new FileInputStream(file1);
			InputStreamReader read1 = new InputStreamReader(fis1,"utf8");//考虑到编码格式
            BufferedReader bufferedReader1 = new BufferedReader(read1);
            String lineTxt1 = null;
            while((lineTxt1 = bufferedReader1.readLine()) != null){
            	sb1.append(lineTxt1);
            }
            read1.close();
            EmailTemplate template1 = new EmailTemplate();
            template1.setContents(sb1.toString());
            template1.setTitle("新会员模版");
            template1.setCode("new");
            emailTemplateService.saveOrUpdate(template1);
            
            StringBuilder sb3 = new StringBuilder();
			File file3 = new File("C:/Users/Lee.J.Eric/Documents/xufeiok.html");
			InputStream fis3 = new FileInputStream(file3);
			InputStreamReader read3 = new InputStreamReader(fis3,"utf8");//考虑到编码格式
            BufferedReader bufferedReader3 = new BufferedReader(read3);
            String lineTxt3 = null;
            while((lineTxt3 = bufferedReader3.readLine()) != null){
            	sb3.append(lineTxt3);
            }
            read3.close();
            EmailTemplate template3 = new EmailTemplate();
            template3.setContents(sb3.toString());
            template3.setTitle("会员续费模版");
            template3.setCode("renewal");
            emailTemplateService.saveOrUpdate(template3);
            
            StringBuilder sb2 = new StringBuilder();
			File file2 = new File("C:/Users/Lee.J.Eric/Documents/xufei.html");
			InputStream fis2 = new FileInputStream(file2);
			InputStreamReader read2 = new InputStreamReader(fis2,"utf8");//考虑到编码格式
            BufferedReader bufferedReader2 = new BufferedReader(read2);
            String lineTxt2 = null;
            while((lineTxt2 = bufferedReader2.readLine()) != null){
            	sb2.append(lineTxt2);
            }
            read2.close();
            EmailTemplate template2 = new EmailTemplate();
            template2.setContents(sb2.toString());
            template2.setTitle("会员过期模版");
            template2.setCode("expire");
            emailTemplateService.saveOrUpdate(template2);
            
			jr.setCord(0);
			jr.setMsg("创建成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			jr.setCord(1);
			jr.setMsg("创建失败");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value= "test")
	public void test(HttpServletResponse response,String userid,String id,String acttype,String eventtype){
		Jr jr = new Jr();
		jr.setMethod("test");
		try {
			/*	User user =userService.findById(tpye);
			Set<UserotherInfo> set=user.getUserothers();
			Userother userother=userotherService.findUserother(title);
			UserotherInfo info=new UserotherInfo();
			info.setTexts(texts);
			info.setUserother(userother);
			info=userotherinfoService.saveOrUpdateUserother(info);
			set.add(info);
			user.setUserothers(set);
			userService.userSaveOrUpdate(user);
			*/
//			Userother userother=new Userother();
//			userother.setZtitle(title);
//			userother.setInfotype(tpye);
//			userotherService.saveOrUpdateUserother(userother);
			
			
			/*Page<User> page=userService.findUserList(0, 20, "vip", "nowhere", "", "desc");
			List<User> us=page.getContent();
			Set<Labels> s=new HashSet<Labels>();
			Page<Labels> pag=labelsService.getLabels(0,10,0);
			List<Labels> labels=pag.getContent();
			for (Labels labels2 : labels) {
				s.add(labels2);
				labels2.setHot(labels2.getHot()+1);
				labelsService.createLabels(labels2);
			}
			for (User user : us) {
				user.setJob(s);
				userService.userSaveOrUpdate(user);
			}
			*/
			/*Labels labels=new Labels();
			labels.setId(System.currentTimeMillis());
			labels.setZname(texts);
			labels.setEname("");
			labels.setLabeltype(0);
			labelsService.createLabels(labels);*/
			jr.setCord(0);
			jr.setMsg("创建成功");
		} catch (Exception e) {
			e.printStackTrace();
			jr.setCord(1);
			jr.setMsg("创建失败");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value= "testaddcoffee")
	public void testAddCoffee(HttpServletResponse response) {
		try {
//			File file=new File("G:\\test.xlsx");
//			FileInputStream fis = new FileInputStream(file);
//			
//			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
//			XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
//			int lastrow = sheet.getLastRowNum();//总行数
//			XSSFRow headRow = sheet.getRow(0);// 取得表头行,第一行，若有改动请作相应修改
//			int headlength = headRow.getPhysicalNumberOfCells(); //总列数
//			XSSFCellStyle cellStyle2 = xssfWorkbook.createCellStyle();
//	        XSSFDataFormat format = xssfWorkbook.createDataFormat();
//			
//			XSSFRow row = null;
//			Cell cell = null;
//			Cell cell2 = null;
//			for (int i = 1; i <= lastrow; i++) {
//				try {
//					row = sheet.getRow(i);
//					String value="";//单元格的内容
//					String value2="";//单元格的内容
//						cell = row.getCell(0);
//						if(cell!=null){
//							if(cell.getCellType()==XSSFCell.CELL_TYPE_STRING){
//								value = cell.getStringCellValue();
//							}else if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
//						        value=cell.getNumericCellValue()+"";
//							}else if(cell.getCellType()==XSSFCell.CELL_TYPE_BOOLEAN){
//								value = cell.getBooleanCellValue()+"";
//							}else if(cell.getCellType()==XSSFCell.CELL_TYPE_FORMULA){
//								cellStyle2.setDataFormat(format.getFormat("@"));
//						        cell.setCellStyle(cellStyle2);
//						        try{
//						        	value=cell.getNumericCellValue()+"";
//						        }catch (Exception e){
//						        	value="0";
//						        }
//							}
//						}
//						
//						cell2 = row.getCell(1);
//						if(cell2!=null){
//							if(cell2.getCellType()==XSSFCell.CELL_TYPE_STRING){
//								value2 = cell2.getStringCellValue();
//							}else if(cell2.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
//						        value2=cell2.getNumericCellValue()+"";
//							}else if(cell2.getCellType()==XSSFCell.CELL_TYPE_BOOLEAN){
//								value2 = cell2.getBooleanCellValue()+"";
//							}else if(cell2.getCellType()==XSSFCell.CELL_TYPE_FORMULA){
//								cellStyle2.setDataFormat(format.getFormat("@"));
//						        cell2.setCellStyle(cellStyle2);
//						        try{
//						        	value2=cell2.getNumericCellValue()+"";
//						        }catch (Exception e){
//						        	value2="0";
//						        }
//							}
//						}
//						User user= userService.findByUnum("00746");
//						if(user!=null){
//							int coffee_num=(int) Double.parseDouble(value2);
//							List<ItemInstance> instances = new ArrayList<ItemInstance>();
//							List<ItemInstanceLog> instanceLogs = new ArrayList<ItemInstanceLog>();
//							if(coffee_num>0){
//								for (int j = 0; j < coffee_num; j++) {
//									//商品实例入库 正常咖啡
//									ItemInstance instance = new ItemInstance();
//									instance.setStatus(ItemInstanceStatus.UNDELIVERED.getCode());
//									SKU sku=skuService.findById("1-1419494727163");//1-1419494727163
//									instance.setSku(sku);
//									instance.setUserId(user.getId());
//									instance.setReceiveuserId(user.getId());
//									instance.setCreateTime(new Timestamp(System.currentTimeMillis()));
//									instance.setModifyTime(new Timestamp(System.currentTimeMillis()));
//									instance.setEffective(0);//无时效性
//									instance.setStatus(ItemInstanceStatus.UNUSED.getCode());
//									instances.add(instance);
//									
//									//商品实例记录  正常咖啡
//									ItemInstanceLog item_log = new ItemInstanceLog();
//									item_log.setItemInstanceId(instance.getId());
//									item_log.setOpType(ItemInstanceLog.OpType.CREATE.getCode());
//									item_log.setSkuId(sku.getId());
//									item_log.setUserId(user.getId());
//									item_log.setUserNickname(user.getNickname());
//									item_log.setOpType(OpType.DELIVER.getCode());
//									item_log.setMemo("众筹咖啡，系统自动创建商品实例");
//									item_log.setDateTime(new Timestamp(System.currentTimeMillis()));
//									instanceLogs.add(item_log);
//									
//								}
//								itemInstanceService.saveOrUpdate(instances);
//								itemInstanceLogService.saveOrUpdate(instanceLogs);
//								row = sheet.getRow(i);
//								cell = row.createCell(headlength);
//								cell.setCellValue("此用户导入	ok	");
//							}
//						}else{
//							row = sheet.getRow(i);
//							cell = row.createCell(headlength);
//							cell.setCellValue("用户不存在");
//						}
//						
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//					row = sheet.getRow(i);
//					cell = row.createCell(headlength);
//					cell.setCellValue("此用户导入失败");
//					continue;
//				}
				
				
				User user= userService.findByUnum("02020");
				if(user!=null){
					int coffee_num=2;
					List<ItemInstance> instances = new ArrayList<ItemInstance>();
					List<ItemInstanceLog> instanceLogs = new ArrayList<ItemInstanceLog>();
					if(coffee_num>0){
						for (int j = 0; j < coffee_num; j++) {
							//商品实例入库 正常咖啡
							ItemInstance instance = new ItemInstance();
							instance.setStatus(ItemInstanceStatus.UNDELIVERED.getCode());
							SKU sku=skuService.findById("1-1419494727163");//1-1419494727163
							instance.setSku(sku);
							instance.setUserId(user.getId());
							instance.setReceiveuserId(user.getId());
							instance.setCreateTime(new Timestamp(System.currentTimeMillis()));
							instance.setModifyTime(new Timestamp(System.currentTimeMillis()));
							instance.setEffective(0);//无时效性
							instance.setStatus(ItemInstanceStatus.UNUSED.getCode());
							instances.add(instance);
							
							//商品实例记录  正常咖啡
							ItemInstanceLog item_log = new ItemInstanceLog();
							item_log.setItemInstanceId(instance.getId());
							item_log.setOpType(ItemInstanceLog.OpType.CREATE.getCode());
							item_log.setSkuId(sku.getId());
							item_log.setUserId(user.getId());
							item_log.setUserNickname(user.getNickname());
							item_log.setOpType(OpType.DELIVER.getCode());
							item_log.setMemo("众筹咖啡，系统自动创建商品实例");
							item_log.setDateTime(new Timestamp(System.currentTimeMillis()));
							instanceLogs.add(item_log);
							
						}
						
						itemInstanceService.saveOrUpdate(instances);
						
						itemInstanceLogService.saveOrUpdate(instanceLogs);
						//row = sheet.getRow(i);
						//cell = row.createCell(headlength);
						//cell.setCellValue("此用户导入	ok	");
					}
			}
			//FileOutputStream os = new FileOutputStream(file);
			//xssfWorkbook.write(os);
			//os.flush();
			//os.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
