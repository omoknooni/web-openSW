package hello.controller;

import hello.domain.Member;
import hello.domain.Recipt;
import hello.service.ReciptService;
import hello.service.SumCost;
import hello.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;


/**
 * Code sample demonstrating Cloud Vision usage within the context of Spring Framework using Spring
 * Cloud GCP libraries. The sample is written as a Spring Boot application to demonstrate a
 * practical application of this usage.
 */
@Controller
@Slf4j
public class VisionController {

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ReciptService reciptService;

    @Value("${file.dir}")
    public String fileDir;

    // [START vision_spring_autowire]
    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;
    // [END vision_spring_autowire]


    @GetMapping("/ocr")
    public String ocr() {
        return "ocr";
    }

    @PostMapping("/ocr")
//    @ResponseBody
    public String extractText(@RequestParam("file") MultipartFile file , HttpServletRequest request, Model model) throws IOException {

        String uploadPath = null;

        if (!file.isEmpty()) {
            uploadPath = fileDir + file.getOriginalFilename();
            log.info("파일 저장 uploadPath={}", uploadPath);
            file.transferTo(new File(uploadPath));
        }

        Resource imageResource = new FileSystemResource(uploadPath);

        String textFromImage = this.cloudVisionTemplate.extractTextFromImage(imageResource);

        SumCost sumCost = new SumCost(textFromImage);

        // 영수증 DB저장
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Recipt recipt = new Recipt();
        recipt.setFilePath(uploadPath);
        recipt.setMember(member);
        recipt.setSum(sumCost.getCost());
        recipt.setCash(sumCost.cash());
        recipt.setDate(sumCost.getDay());
        recipt.setPhoneNumber(sumCost.getCall());

        reciptService.join(recipt);
        model.addAttribute("text",textFromImage);
        model.addAttribute("recipt", recipt);
        return "showText";
        // [END vision_spring_text_extraction]
    }
}