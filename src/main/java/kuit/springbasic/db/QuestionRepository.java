package kuit.springbasic.db;

import kuit.springbasic.domain.Question;

import java.util.Collection;

public interface QuestionRepository {
    void insert(Question question);

    Question findByQuestionId(int questionId);

    Collection<Question> findAll();

    void update(Question question);
}
