package kuit.springbasic.db;

import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;

import java.util.Collection;
import java.util.List;

public interface QuestionRepository {
    void insert(Question question);

    Question findByQuestionId(int questionId);

    List<Question> findAll();

    void update(Question question);
}
