package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Jr;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.entity.Answer;
import cn.yi.gather.v20.entity.Question;
import cn.yi.gather.v20.service.IAnswerService;
import cn.yi.gather.v20.service.IQuestionService;

@Controller("answerControllerV20")
@RequestMapping(value="v20/answer")
public class AnswerController {

	@Resource (name="answerServiceV20")
	private IAnswerService answerService;
	
	@Resource(name="questionServiceV20")
	private IQuestionService questionService;
	
	@RequestMapping(value="createanswer")
	@ResponseBody
	public void createAnswer(HttpServletResponse response,Answer answer, String questions,String type){
		Jr jr = new Jr();
		jr.setMethod("createanswer");
		
		if(answer==null||answer.getObjid()==null||answer.getUser()==null||answer.getUser().getId()==null||questions==null||questions.length()<2){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}else{
			List<Question> qlist=new ArrayList<Question>();
			
			//questions="[{'num':'0','questiontext':'电话','questiontype':'text','questionoptions':'','answertext':'12312345678'},{'num':'1','questiontext':'昵称','questiontype':'text','questionoptions':'','answertext':'张三'}]";
			JSONArray jsonarr=JSONArray .fromObject(questions);
			JSONObject jsonObject=null;
			Question q=null;
			
			for (int i = 0; i < jsonarr.size(); i++) {
				jsonObject=JSONObject.fromObject(jsonarr.get(i));
				q=new Question();
				q.setNum(jsonObject.get("num").toString());
				q.setQuestiontext(jsonObject.get("questiontext").toString());
				q.setQuestiontype(jsonObject.get("questiontype").toString());
				q.setQuestionoptions(jsonObject.get("questionoptions").toString());
				q.setAnswertext(jsonObject.get("answertext").toString());
				q=questionService.save(q);
				if(q!=null)
				qlist.add(q);
			}
			if(qlist!=null&&qlist.size()>0)
			answer.setQlist(qlist);
			answer=answerService.save(answer);
			if(answer!=null){
				jr.setData(answer);
				jr.setMsg("保存成功");
				jr.setCord(0);
			}else{
				jr.setMsg("保存失败");
				jr.setCord(-2);
			}
		}
		
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
