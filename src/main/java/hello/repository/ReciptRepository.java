package hello.repository;

import hello.domain.Recipt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReciptRepository {

    private final EntityManager em;

    @Value("${file.dir}")
    public String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public void save(Recipt recipt) {
        em.persist(recipt);
    }

    public Recipt findOne(Long id) {
        return em.find(Recipt.class, id);
    }

    public List<Recipt> findAllByMemberId(Long id) {
        return em.createQuery("SELECT r FROM Recipt r WHERE r.member.id =:memberId", Recipt.class)
                .setParameter("memberId", id)
                .getResultList();
    }

}
