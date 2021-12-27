import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Block {

    String prev;
    int nonce = 1;
    String data = "23";
    String hash = getMd5(Integer.toString(nonce) + data);

    // MD5 Hashing Algorithm
    private static String getMd5(String input) {
        try {
            MessageDigest msgDst = MessageDigest.getInstance("MD5");
            byte[] msgArr = msgDst.digest(input.getBytes());
            BigInteger bi = new BigInteger(1, msgArr);
            StringBuilder hshtxt = new StringBuilder(bi.toString(16));

            while (hshtxt.length() < 32) {
                hshtxt.insert(0, "0");
            }
            return hshtxt.toString();
        }
        catch (NoSuchAlgorithmException abc) {
            throw new RuntimeException(abc);
        }
    }

    private static void connectBlocks(Block prevBlock, Block nextBlock) {
        nextBlock.prev = prevBlock.hash;
    }

    public static Block addBlock(Block toBlock) {
        Block newBlock = new Block();
        connectBlocks(toBlock, newBlock);
        return newBlock;
    }

    public static void setHash(Block block) {
        block.hash = getMd5(Integer.toString(block.nonce) + block.data);
    }

    // returns:
    // 1 if block1 -> block2
    // -1 if block1 <- block2
    // 0 if not connected directly
    public static int isConnected(Block block1, Block block2) {
        if (Objects.equals(block2.prev, block1.hash)) {
            return 1;
        } else if (Objects.equals(block1.prev, block2.hash)) {
            return -1;
        } else {
            return 0;
        }
    }

    public static void mine(Block block) {
        // Problem: Hash must start with 2

        int nonce = block.nonce;
        String data = block.data;
        String hash = block.hash;

        while (true) {
            String temp = getMd5(Integer.toString(nonce)+data);
            if (temp.startsWith("2")) {
                block.nonce = nonce;
                setHash(block);
                break;
            } else {
                nonce++;
            }
        }
    }

        public static void main(String[] args) {
            Block b = new Block();
            Block f = addBlock(b);
            System.out.println(isConnected(b, f));
        }
    }
