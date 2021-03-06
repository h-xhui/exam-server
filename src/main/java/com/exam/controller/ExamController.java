package com.exam.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.exam.common.ResultCode;
import com.exam.dao.*;
import com.exam.pojo.dto.CreateExamDto;
import com.exam.pojo.result.ApiResult;
import com.exam.pojo.result.PapersList;
import com.exam.pojo.result.ScoreResult;
import com.exam.service.ExamService;
import com.exam.service.ScoreService;
import com.exam.service.UserExamQuestionService;
import com.exam.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author hongjinhui
 * 2022/5/20
 */
@Controller
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    private ExamService examService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    JWTUtils jwtUtils;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private PapersMapper papersMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserExamQuestionService userExamQuestionService;


    @ResponseBody
    @RequestMapping("/queryUserExam")
    public ApiResult<?> getExam(Long stuId) {
        return ApiResult.success(examService.getUserExamInfo(stuId));
    }

    @ResponseBody
    @RequestMapping("/get_question")
    public ApiResult<?> getQuestion(Long paperId) {
        return ApiResult.success(examService.getExamInfo(paperId));
    }

    @ResponseBody
    @RequestMapping("/submit_paper")
    public ApiResult<?> submitPaper(Long stuId, Long examId, String answer, String questionIds) {
        examService.submitPaper(stuId, examId, answer, questionIds);
        return ApiResult.success();
    }

    /**
     * @author hzh
     * ??????????????????????????? key ??????????????? value??????
     **/
    @RequestMapping("/inquire")
    @ResponseBody
    public ApiResult<?> inquire(@Valid String searchKey,String searchValue, Long examId){
        if(searchKey!=null&&searchValue!=null&&examId!=null){

            if(searchKey.equals("??????")){
                List<ScoreResult> scoreList = scoreService.getScoreByClazz(searchValue,examId);
                return ApiResult.success(scoreList);
            }
            if(searchKey.equals("??????")){

                List<ScoreResult> scoreList = scoreService.getScoreByStudentId(searchValue,examId);
                return ApiResult.success(scoreList);
            }
            if(searchKey.equals("??????")){
                List<ScoreResult> scoreList = scoreService.getScoreByName(searchValue,examId);
                return ApiResult.success(scoreList);
            }else{
                return ApiResult.fail(ResultCode.MISSING_PARAM,null,"?????????????????????");
            }
        }
        return ApiResult.fail(ResultCode.MISSING_PARAM,null,"???????????????????????????");
    }
    /**
     * @author hzh
     * ????????????????????????????????????
     **/
    @RequestMapping("/get_exam_list_byClass")
    @ResponseBody
    public ApiResult<?> getExamListByClass(String clas,Long examId){

        return examService.getExamListByClass(clas,examId);
    }
    /**
     * @author hzh
     * ????????????????????????
     **/
    @RequestMapping("/get_exam_list")
    @ResponseBody
    public ApiResult<?> getExamList(){
        return examService.getExamListBy();
    }

    /**
     * @author hzh
     * ????????????????????????
     **/
    @RequestMapping("/get_papers")
    @ResponseBody
    public ApiResult<?> getPapers(){
//        List<PapersList> allList = examMapper.getAllList();
        List<PapersList> allList=papersMapper.getAllList();
        return ApiResult.success(allList);
    }
    /**
     * @author hzh
     * ????????????
     **/
    @RequestMapping("/create_exam")
    @ResponseBody
    public ApiResult<?> createExam(CreateExamDto createExamDto){
        return examService.createExam(createExamDto);
    }

    @RequestMapping("/getAllClassName")
    @ResponseBody
    public ApiResult<?> getAllClass() {
        return ApiResult.success(userMapper.getClazzName());
    }

}
