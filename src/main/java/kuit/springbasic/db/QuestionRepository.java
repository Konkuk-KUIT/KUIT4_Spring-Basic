package kuit.springbasic.db;

import kuit.springbasic.domain.Question;

import java.util.List;

public interface QuestionRepository {
    void insert(Question question);

    Question findByQuestionId(int questionId);

    List<Question> findAll();

    void update(Question question);

    void updateCountOfAnswer(Question question);
}
