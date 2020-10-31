package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a Student's attendance in Trackr. This includes the student's participation score.
 * Guarantees: immutable; is valid as declared in {@link #isValidAttendance(String)} and
 * {@link #isValidParticipation(String)}
 */
public class Attendance {

    public static final String MESSAGE_CONSTRAINTS = "Attendance covers week 1 to 13.\n"
            + "Please provide participation score in integers only (from -100 to 100).\n"
            + "Note that the minimum score is 0 and maximum score is 100.";
    public static final String VALIDATION_REGEX = "[-]?\\d+";

    // attendance
    public static final int MIN_WEEK = 1;
    public static final int MAX_WEEK = 13;

    // class participation
    public static final int MIN_VALUE = -100;
    public static final int MIN_SCORE = 0;
    public static final int MAX_SCORE = 100;

    private boolean[] isPresent = new boolean[MAX_WEEK];
    private int value;

    public Attendance() {
        this.value = 0;
    }

    /**
     * Returns true if a given string is a valid week number.
     */
    public static boolean isValidAttendance(String attendance) {
        if (attendance.matches(VALIDATION_REGEX)) {
            int week = Integer.parseInt(attendance);
            return MIN_WEEK <= week
                    && week <= MAX_WEEK;
        } else {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid participation score.
     */
    public static boolean isValidParticipation(String participation) {
        if (participation.matches(VALIDATION_REGEX)) {
            int score = Integer.parseInt(participation);
            return MIN_VALUE <= score
                    && score <= MAX_SCORE;
        } else {
            return false;
        }
    }

    private void parseAddingAttendance(String attendance) {
        int week = Integer.parseInt(attendance) - 1;
        isPresent[week] = true;
    }

    private void parseDeletingAttendance(String attendance) {
        int week = Integer.parseInt(attendance) - 1;
        isPresent[week] = false;
    }

    /**
     * Records the student as present on the given week number.
     */
    public void addAttendance(String attendance) throws IllegalArgumentException {
        checkArgument(isValidAttendance(attendance), MESSAGE_CONSTRAINTS);
        parseAddingAttendance(attendance);
    }

    /**
     * Records the student as absent on the given week number.
     */
    public void deleteAttendance(String attendance) throws IllegalArgumentException {
        checkArgument(isValidAttendance(attendance), MESSAGE_CONSTRAINTS);
        parseDeletingAttendance(attendance);
    }

    private void parseParticipation(String participation) {
        int score = Integer.parseInt(participation);
        value += score;
        if (value > MAX_SCORE) {
            value = MAX_SCORE;
        } else if (value < MIN_SCORE) {
            value = MIN_SCORE;
        }
    }

    /**
     * Adjusts the student's participation score.
     */
    public void editParticipation(String participation) {
        checkArgument(isValidParticipation(participation), MESSAGE_CONSTRAINTS);
        parseParticipation(participation);
    }

    /**
     * Returns a {@code String} listing out the week(s) where the student is present.
     */
    public String listOutAttendedWeeks() {
        StringBuilder result = new StringBuilder("week(s): ");
        for (int i = 0; i < isPresent.length; i++) {
            if (isPresent[i]) {
                result.append(String.format("%d ", i + 1));
            }
        }
        return result.toString();
    }

    public int getParticipationScore() {
        return value;
    }

    public String getParticipationScoreAsString() {
        return String.valueOf(value);
    }

    public String getMaxParticipationScore() {
        return String.valueOf(MAX_SCORE);
    }

    public boolean[] getIsPresent() {
        return isPresent;
    }

    /**
     * Records the student as present on the given week numbers.
     */
    public void setIsPresent(boolean[] attendance) {
        requireNonNull(attendance);
        this.isPresent = attendance.clone();
    }

    @Override
    public String toString() {
        int result = 0;
        for (boolean b : isPresent) {
            if (b) {
                result++;
            }
        }
        return String.format("%d/%d weeks attended, with a participation score of: %d/%d",
                result,
                MAX_WEEK,
                value,
                MAX_SCORE
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Attendance // instanceof handles nulls
                && Arrays.equals(isPresent, ((Attendance) other).isPresent)) // state check
                && value == ((Attendance) other).value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPresent, value);
    }
}
