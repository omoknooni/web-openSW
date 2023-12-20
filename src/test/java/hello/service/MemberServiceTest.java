package hello.service;

import com.google.cloud.language.v1.ClassifyTextRequest;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;

import hello.domain.Member;
import hello.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@Slf4j
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @AfterEach
    void afterEach() {
        memberRepository.clear();
    }

    @Test
    void 아이디중복테스트() {

        Member member1 = new Member();
        member1.setLoginId("pswan10");
        member1.setName("박수완12");
        member1.setPassword("2580");
        memberService.join(member1);
        Member member2 = new Member();
        member2.setLoginId("pswan10");
        member2.setName("박수아");
        member2.setPassword("2580");
       /* memberService.join(member2);
        assertThat(member2.getId()).isEqualTo("pswan10");*/
        assertThatThrownBy(() -> memberService.join(member2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 로그인아이디찾기() {
        Member member1 = new Member();
        member1.setLoginId("pswan10");
        member1.setName("박수완12");
        member1.setPassword("2580");
        memberService.join(member1);
        Optional<Member> byLoginId = findByLoginId(member1.getLoginId());
        log.info(String.valueOf(byLoginId));
    }

    @Test
    public Optional<Member> findByLoginId(String loginId){
        List<Member> members =  em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList();

        return members.stream().findAny();
    }

    public static void main(String... args) throws Exception {
        // Instantiates a client
        try (LanguageServiceClient language = LanguageServiceClient.create()) {

            // The text to analyze
            String text = "Hello, world!";
            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();

            // Detects the sentiment of the text
            Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();

            System.out.printf("Text: %s%n", text);
            System.out.printf("Sentiment: %s, %s%n", sentiment.getScore(), sentiment.getMagnitude());
        }
    }

/*    public static void classifyText(String text) throws Exception {
        // [START language_classify_text]
        // Instantiate the Language client com.google.cloud.language.v1.LanguageServiceClient
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            // Set content to the text string
            Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
            V2Model v2Model = V2Model.newBuilder()
                    .setContentCategoriesVersion(ContentCategoriesVersion.V2)
                    .build();
            ClassificationModelOptions options =
                    ClassificationModelOptions.newBuilder().setV2Model(v2Model).build();
            ClassifyTextRequest request =
                    ClassifyTextRequest.newBuilder()
                            .setDocument(doc)
                            .setClassificationModelOptions(options)
                            .build();
            // Detect categories in the given text
            ClassifyTextResponse response = language.classifyText(request);

            for (ClassificationCategory category : response.getCategoriesList()) {
                System.out.printf(
                        "Category name : %s, Confidence : %.3f\n",
                        category.getName(), category.getConfidence());
            }
        }
        // [END language_classify_text]
    }*/
}