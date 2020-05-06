
import java.util.List;

public class SegmentTree {
    // segment tree
    private SegmentNode root = null;

    // Player information provided as an ArrayList of Player objects to SegmentTree constructor
    public SegmentTree(List<Player> players) {
        // set to first person in list
        int minId = players.get(0).getPlayerId();
        int maxId = players.get(0).getPlayerId();
        for (Player player : players) {
            // set to smallest id
            minId = Math.min(minId, player.getPlayerId());
            // set to highest id
            maxId = Math.max(maxId, player.getPlayerId());
        }
        // retrieve the rull range
        for (Player player : players) {
            root = build(root, player, minId, maxId);
        }
    }

    // helper method to build each node of the Segment Tree, given a list of players, start and end index
    private SegmentNode build(SegmentNode root, Player player, int from, int to) { // from lowest playerdId to highest
        SegmentNode node = root;

        if (from == to) {
            // start and end ids are the same
            return new SegmentNode(from, to, player.getScore());
        }


        if (node == null) {
            node = new SegmentNode(from, to, 0);
        }

        // start is different from end id
        int mid = (from + to) / 2;
        if (player.getPlayerId() <= mid) {
            // go left since it belongs to the left child
            node.left = build(node.left, player, from, mid);
        }
        else {
            // go right since it belongs to the right child
            node.right = build(node.right, player, mid + 1, to);
        }

        node.maxScore = Math.max(node.maxScore, player.getScore());
        return node;
    }

    // return the highest score among the players within the given id range. Segment tree --> O(logn)
    public int rangeHighestQuery(int lowId, int highId) {
        return rangeHighestQuery_helper(this.root, lowId, highId);
    }

    // helper func
    private int rangeHighestQuery_helper(SegmentNode node, int lowId, int highId) {
        if (node == null) {
            return 0;
        }

        if (node.lowId >= lowId && node.highId <= highId) {
            // CASE 1 : node interval lies within needed interval i.e complete overlap
            return node.maxScore;
        }

        else if (node.lowId > highId || node.highId < lowId) {
            // CASE 2 : node interval falls outside needed interval i.e no overlap
            return 0;
        }

        else {
            // CASE 3 : partial overlap so call left or right child to find respective answer
            int midIndex = (node.lowId + node.highId) / 2;
            int max = 0;

            if (highId <= midIndex) {
                // go left
                max = Math.max(max, rangeHighestQuery_helper(node.left, lowId, highId));
            }
            else if (lowId > midIndex) {
                // go right
                max = Math.max(max, rangeHighestQuery_helper(node.right, lowId, highId));
            }
            else {
                // call both children
                int left = rangeHighestQuery_helper(node.left, lowId, highId);
                int right = rangeHighestQuery_helper(node.right, lowId, highId);
                max = Math.max(max, Math.max(left, right));
            }

            return max;
        }
    }

    // update method called by BST update
    public void update(int id, int score) {
        int res = update_helper(this.root, id, score);
    }

    // helper func
    private int update_helper(SegmentNode node, int id, int score) {
        if (node.lowId == id && node.highId == id && score > node.maxScore) {
            // base case: score to insert > existing node's maxScore and we're at the leaf node
            node.maxScore = score;
        }

        else {
            // start is different from end id
            int mid = (node.lowId + node.highId) / 2;
            if (id <= mid) {
                // go left since it belongs to the left child
                node.maxScore = Math.max(node.maxScore, update_helper(node.left, id, score));
            }
            else {
                // go right since it belongs to the right child
                node.maxScore = Math.max(node.maxScore, update_helper(node.right, id, score));
            }
        }
        return node.maxScore;
    }
}


