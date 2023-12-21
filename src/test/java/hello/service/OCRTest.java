package hello.service;

import hello.domain.Recipt;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Slf4j
public class OCRTest {

    // [START vision_spring_autowire]
    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;
    // [END vision_spring_autowire]

    @Test
    void textExtract() {

        String imagePath = "C:\\recipt\\영수증.jpg";

        Resource imageResource = new FileSystemResource(imagePath);

        String textFromImage = this.cloudVisionTemplate.extractTextFromImage(imageResource);

        log.info("Text={}", textFromImage);
    }

    @Test
    void setMember() {
        Member test = new Member();
        Recipt re = new Recipt();

        re.setMember(test);
    }
}

