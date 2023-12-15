package com.example.surveyapplication.controllers.surveys;

import com.example.surveyapplication.models.dtos.SurveyDTO;
import com.example.surveyapplication.models.dtos.SurveyStatistic;
import com.example.surveyapplication.models.entities.Answer;
import com.example.surveyapplication.models.entities.Question;
import com.example.surveyapplication.models.entities.Survey;
import com.example.surveyapplication.services.AnswerService;
import com.example.surveyapplication.services.QuestionService;
import com.example.surveyapplication.services.SurveyService;
import com.example.surveyapplication.services.UserService;
import com.example.surveyapplication.sessionClasses.SurveySessionAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/survey")
public class SurveyController {
    private final AnswerService answerService;
    private final UserService userService;
    private final SurveyService surveyService;

    private final QuestionService questionService;
    private SurveySessionAttributes surveySessionAttributes;

    public SurveyController(AnswerService answerService, UserService userService,
                            SurveyService surveyService,
                            SurveySessionAttributes surveySessionAttributes,
                            QuestionService questionService) {
        this.answerService = answerService;
        this.userService = userService;
        this.surveyService = surveyService;
        this.surveySessionAttributes = surveySessionAttributes;
        this.questionService = questionService;
    }

    @GetMapping("")
    public String survey(Model model){
        if(surveySessionAttributes.getCurrentSurvey() != null){
            model.addAttribute("question",surveySessionAttributes.getCurrentQuestion());
            int chosenAnswerIndex = receiveChosenAnswerIndex(surveySessionAttributes.getCurrentQuestion());
            model.addAttribute("chosenAnswerIndex",chosenAnswerIndex);
            return "question/question";
        } else{
            return "redirect:/home";
        }
    }

    private int receiveChosenAnswerIndex(Question question){
        Optional<Answer> existingAnswer = userService.receiveCurrentUserAnswerToQuestion(question);
        return existingAnswer.map(answer->question.getAnswers().stream().filter(x->x.getId().equals(answer.getId())).findFirst().map(value->question.getAnswers().indexOf(value)).orElse(0)).orElse(0);
    }

    @GetMapping("/choose")
    public String chooseSurvey(Model model){
        model.addAttribute("surveys",surveyService.findAllDTO());
        return "survey/surveyList";
    }

    @PostMapping("/choose/{id}")
    public String chooseSurveyPost(@PathVariable Long id){
        Optional<Survey> survey = surveyService.findById(id);
        return survey.map(value->{
            surveySessionAttributes.setCurrentSurvey(value);
            surveySessionAttributes.setQuestionIndex(0);
            surveyService.addCurrentUserToSurvey(value);
            return "redirect:/survey";
        }).orElse("redirect:/home");
    }

    @PostMapping("/nextQuestion")
    public String nextQuestion(@Valid Question question, Model model){
        if(surveySessionAttributes.getCurrentSurvey()==null || question.getAnswers().isEmpty()){
            return "redirect:/home";
        } else {
            answerService.addUserToAnswer(question.getAnswers().get(0));
            if(surveySessionAttributes.isLastQuestion()){
                model.addAttribute("survey",surveySessionAttributes.getCurrentSurvey());
                surveySessionAttributes.setToDefaultValue();
                return "survey/surveyFinished";
            } else{
                surveySessionAttributes.increaseQuestionIndex();
                return "redirect:/survey";
            }
        }
    }

    @PostMapping("/previousQuestion")
    @Transactional
    public String previousQuestion(){
        if(surveySessionAttributes.getCurrentSurvey()==null){
            return "redirect:/home";
        } else if(surveySessionAttributes.getQuestionIndex() > 0){
            surveySessionAttributes.decreaseQuestionIndex();
        }
        return "redirect:/survey";
    }

    @GetMapping("/list")
    public String surveyList(Model model){
        List<SurveyDTO> surveys = surveyService.findAllDTO();
        model.addAttribute("surveys",surveys);
        return "survey/surveyList";
    }


    @GetMapping("/{id}/statistic")
    public String surveyStatistic(@PathVariable Long id, Model model){
        Optional<Survey> survey = surveyService.findById(id);
        return survey.map(value->{
            model.addAttribute("statistic",new SurveyStatistic(value));
            return "survey/surveyStatistic";
        }).orElse("redirect:/home");
    }

    @PostMapping("/createSurvey")
    public String createSurveyPost(@ModelAttribute Survey survey){
        surveyService.add(survey);

       return "redirect:/survey/surveyList";
    }

    @GetMapping("/createSurvey")
    public String createNewSurveyGet(Model model){
        Survey survey = new Survey();
        model.addAttribute("survey",survey);
        return "survey/surveyCreate";
    }

    @PostMapping("/editSurvey/{id}")
    public String editSurveyGetPost(@ModelAttribute Survey survey){
        surveyService.update(survey);

        return "survey/surveyList";
    }
    @GetMapping("/editSurvey/{id}")
    public String editSurveyGet(@PathVariable Long id, Model model){
        Optional<Survey> survey = surveyService.findById(id);

        model.addAttribute("survey",survey);
        return "survey/surveyEdit";
    }


    @GetMapping("/editQuestion/{questionId}")
    public String editQuestionGet(@PathVariable Long questionId, Model model){
        Optional<Question> survey = questionService.findById(questionId);

        return "redirect:/editQuestion/" + questionId;
    }

    @PostMapping("/editAnswer/{surveyId}")
    public String createQuestionPost(@PathVariable Long surveyId, @ModelAttribute Question question){
        Optional<Survey> survey = surveyService.findById(surveyId);
        survey.get().getQuestionList().add(question);

        return "redirect:/editSurvey/" + surveyId;
    }

    @GetMapping("/createQuestion/{surveyId}")
    public String createNewQuestion(@PathVariable Long surveyId, Model model){
        Question question = new Question();
        model.addAttribute("question",question);
        return "redirect:/createQuestion/" + surveyId;
    }

    @PostMapping("/createAnswer/{questionId}")
    public String addAnswer(@PathVariable Long questionId, @ModelAttribute Answer answer){
        Optional<Question> question = questionService.findById(questionId);

        question.get().getAnswers().add(answer);

        return "redirect:/surveyList/";
    }

    @GetMapping("/createAnswer/{questionId}")
    public String addAnswer(@PathVariable Long questionId, Model model){
        Answer answer = new Answer();
        model.addAttribute("answer",answer);
        return "redirect:/answerCreate/" + questionId;
    }
}
