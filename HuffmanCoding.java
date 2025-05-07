import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class HuffmanCoding implements HuffmanInterface{
    private class HuffmanNode implements Comparable<HuffmanNode>
    {
        int count;
        char value;
        HuffmanNode left;
        HuffmanNode right;
        public HuffmanNode(int count, char value)
        {
            this.count = count;
            this.value = value;
            left = right = null;
        }
        public HuffmanNode(HuffmanNode left, HuffmanNode right)
        {
            this.left = left;
            this.right = right;
            this.count = left.count + right.count;
            this.value = 0;
        }

        public boolean isLeaf()
        {
            return left == null & right == null;
        }
        @Override
        public int compareTo(HuffmanNode o) {
            return this.count - o.count;
        }
    }

    class HuffmanCode
    {
        String code;
        char value;
        HuffmanCode(String code, char value)
        {
            this.code = code;
            this.value = value;
        }
    }

    PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
    HuffmanNode root;
    List<HuffmanCode> codes = new ArrayList<>();

    @Override
    public String decode(String codedMessage) {
        if (codedMessage == null || codedMessage.length() == 0 || root == null)
        {
            return "";
        }
        String decodedString = "";
        HuffmanNode node = root;
        for(char c : codedMessage.toCharArray())
        {
            if(c == '0')
            {
                node = node.left;
            }
            if(c == '1')
            {
                node = node.right;
            }

        }
        if(node.isLeaf())
        {
            decodedString += node.value;
            node = root;
        }
        return decodedString;
    }

    @Override
    public String encode(String message) {
        int[] counts = new int[256];
        for(char c : message.toCharArray())
        {
            counts[c]++;
        }
        for(char c = 0; c < counts.length; c++)
        {
            if(counts[c] > 0)
            {
                HuffmanNode huffmanNode = new HuffmanNode(counts[c], c);
                priorityQueue.add(huffmanNode);
            }
        }
        while(priorityQueue.size() > 1)
        {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode composite = new HuffmanNode(left, right);
            priorityQueue.add(composite);
        }
        root = priorityQueue.poll();

        generateCodes(root, "");
        String encodedMessage = "";
        for(char c : message.toCharArray())
        {
            encodedMessage += findCodes(c);
        }

        return encodedMessage;
    }

    String findCodes(char c)
    {
        for(HuffmanCode code : codes)
        {
            if(code.value == c)
            {
                return code.code;
            }
        }
        return "";
    }

    void generateCodes(HuffmanNode node, String code)
    {
        if(node == null)
        {
            return;
        }
        if(node.left == null || node.right == null)
        {
            codes.add(new HuffmanCode(code, node.value));
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code+ "1");
    }
}
