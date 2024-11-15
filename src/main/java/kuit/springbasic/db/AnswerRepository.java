package kuit.springbasic.db;

import kuit.springbasic.domain.Answer;

import java.util.List;

public interface AnswerRepository {
    Answer insert(Answer answer);

    List<Answer> findAllByQuestionId(int questionId);

}
