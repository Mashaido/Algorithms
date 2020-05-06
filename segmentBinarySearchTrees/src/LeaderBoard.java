
public interface LeaderBoard {
    // return the score of the player with the given id
    default int scoreQuery(int id) {
        return -1;
    }

    // update the score of the player with the given id to the given score
    default boolean update(int id, int score) {
        return false;
    }

    // return the highest score among the players within the given id range
    default int rangeHighestQuery(int low_id, int high_id) {
        return -1;
    }

    // return the top 1 ranked player
    default int getTopOne() {
        return -1;
    }

    // return the player id who ranks at the given position. Note that position starts from 1
    default int playerIdAtPosition(int pos) {
        return -1;
    }
}


