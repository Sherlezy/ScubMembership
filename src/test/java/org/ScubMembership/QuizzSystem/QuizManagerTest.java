package org.ScubMembership.QuizzSystem;

import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizManagerTest {

    private static final String QUIZ_FOLDER = "quizzes/";
    private static final String TEST_SPORT = "UnitTestSport";

    @BeforeEach
    void setup() {
        // Ensure test file is clean before each test
        deleteQuizFile(TEST_SPORT);
    }

    @AfterEach
    void cleanup() {
        // Clean up after each test
        deleteQuizFile(TEST_SPORT);
    }

    @Test
    void testSaveAndLoadQuiz() {
        List<QuizQuestion> questions = new ArrayList<>();
        questions.add(new QuizQuestion("Capital of France?", List.of("Paris", "Berlin", "Madrid", "Rome"), 0));
        questions.add(new QuizQuestion("2+2?", List.of("3", "4", "5", "22"), 1));

        QuizManager.saveQuiz(TEST_SPORT, questions);

        List<QuizQuestion> loaded = QuizManager.loadQuiz(TEST_SPORT);
        assertEquals(2, loaded.size());

        assertEquals("Capital of France?", loaded.get(0).getQuestion());
        assertEquals("Paris", loaded.get(0).getOptions().get(0));
        assertEquals(0, loaded.get(0).getCorrectIndex());
    }

    @Test
    void testLoadNonexistentQuizReturnsEmptyList() {
        List<QuizQuestion> result = QuizManager.loadQuiz("NonexistentSport");
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Expected empty list for nonexistent quiz file");
    }

    @Test
    void testSaveQuizCreatesFile() {
        List<QuizQuestion> questions = List.of(
                new QuizQuestion("Dummy?", List.of("Yes", "No", "Maybe", "IDK"), 1)
        );

        QuizManager.saveQuiz(TEST_SPORT, questions);
        File file = new File(QUIZ_FOLDER + TEST_SPORT + ".quiz");
        assertTrue(file.exists() && file.length() > 0, "Quiz file should be created and non-empty");
    }

    @Test
    void testSaveAndLoadEmptyQuiz() {
        List<QuizQuestion> empty = new ArrayList<>();
        QuizManager.saveQuiz(TEST_SPORT, empty);

        List<QuizQuestion> loaded = QuizManager.loadQuiz(TEST_SPORT);
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }



    private void deleteQuizFile(String sport) {
        File file = new File(QUIZ_FOLDER + sport + ".quiz");
        if (file.exists()) file.delete();
    }
}
