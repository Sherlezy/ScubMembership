package org.ScubMembership.QuizzSystem;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizQuestionTest {

    @Test
    void testConstructorAndGetters() {
        String questionText = "What is the capital of Italy?";
        List<String> options = List.of("Berlin", "Paris", "Rome", "Madrid");
        int correctIndex = 2;

        QuizQuestion question = new QuizQuestion(questionText, options, correctIndex);

        assertEquals(questionText, question.getQuestion());
        assertEquals(options, question.getOptions());
        assertEquals(correctIndex, question.getCorrectIndex());
    }

    @Test
    void testEmptyQuestion() {
        QuizQuestion question = new QuizQuestion("", List.of("A", "B", "C", "D"), 0);
        assertEquals("", question.getQuestion());
        assertEquals(4, question.getOptions().size());
        assertEquals(0, question.getCorrectIndex());
    }

    @Test
    void testInvalidCorrectIndexNegative() {
        QuizQuestion question = new QuizQuestion("Choose one", List.of("A", "B", "C"), -1);
        assertEquals(-1, question.getCorrectIndex());  // test assumes no validation is done
    }

    @Test
    void testInvalidCorrectIndexTooHigh() {
        QuizQuestion question = new QuizQuestion("Choose one", List.of("A", "B", "C"), 5);
        assertEquals(5, question.getCorrectIndex());  // again, testing as-is
    }

    @Test
    void testOptionsCanBeEmpty() {
        QuizQuestion question = new QuizQuestion("Empty?", List.of(), 0);
        assertEquals(0, question.getOptions().size());
        assertEquals(0, question.getCorrectIndex());
    }
}
