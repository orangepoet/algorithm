package cn.orangepoet.inaction.algorithm

import cn.orangepoet.inaction.algorithm.leetcode.ListNode
import cn.orangepoet.inaction.algorithm.leetcode.Solution
import cn.orangepoet.inaction.algorithm.leetcode.TreeNode
import spock.lang.Specification

/**
 * 使用Spock示例
 *
 * @author chengzhi* @date 2019/09/27k
 */
class SolutionTest extends Specification {
    def solution = new Solution()

    def "最大连续子序列"() {
        expect:
        solution.getMaxSubSet(sequence) == max

        where:
        sequence                       | max
        [-1] as int[]                  | -1
        [-1, 1] as int[]               | 1
        [2, -1] as int[]               | 2
        [-2, 3] as int[]               | 3
        [-2, 1] as int[]               | 1
        [1, 2, 3] as int[]             | 6
        [-1, 2, -2, 1, 1, -3] as int[] | 2
    }

    def "斐波那契数列 (非递归解法)"() {
        expect:
        solution.fibonacci0(n) == arr

        where:
        n | arr
        1 | 1
        2 | 1
        3 | 2
        4 | 3

    }

    def "斐波那契数列(递归解法)"() {
        expect:
        solution.fibonacci1(n) == arr

        where:
        n | arr
        1 | 1
        2 | 1
        3 | 2
        4 | 3

    }

    def "硬币找零"() {

        expect:
        solution.makeChange(coins, n, new HashMap<>()) == cnt
        solution.makeChange2(coins, n) == cnt

        where:
        coins              | n    | cnt
        [1, 7, 9] as int[] | 3000 | 334
        [1, 7, 9] as int[] | 1    | 1
        [1, 7, 9] as int[] | 2    | 2
        [1, 7, 9] as int[] | 6    | 6
        [1, 7, 9] as int[] | 7    | 1
        [1, 7, 9] as int[] | 8    | 2
        [1, 7, 9] as int[] | 9    | 1
        [1, 7, 9] as int[] | 10   | 2
        [1, 7, 9] as int[] | 0    | 0
        [1, 7, 9] as int[] | 16   | 2
        [1, 7, 9] as int[] | 17   | 3
    }

    def '查找目标数的范围'() {


        expect:
        solution.searchRange(nums, target) == range

        where:
        nums                         | target | range
        [5, 7, 7, 8, 8, 10] as int[] | 6      | [-1, -1] as int[]
        [5, 7, 7, 8, 8, 10] as int[] | 7      | [1, 2] as int[]
        [5, 7, 7, 8, 8, 10] as int[] | 8      | [3, 4] as int[]
    }

    def '组合数的和'() {
        expect:
        solution.combination(candidates, target) == ans

        where:
        candidates                           | target | ans
        [1, 2, 3, 4, 5, 6, 7, 8, 9] as int[] | 12     | [[5, 7], [4, 8], [3, 9], [3, 4, 5], [2, 4, 6], [2, 3, 7], [1, 5, 6], [1, 4, 7], [1, 3, 8], [1, 2, 9], [1, 2, 4, 5], [1, 2, 3, 6]] as List<List<Integer>>
    }

    def 'C(n,m)组合'() {
        expect:
        solution.combination0(arr, m) == ans
        where:
        arr                   | m | ans
        [1, 2, 3, 4] as int[] | 2 | [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]] as List<List<Integer>>
    }

    def '三数之和'() {

        expect:
        solution.threeSum(nums) == result

        where:
        nums                                                              | result
        [-1, 0, 1, 2, -1, -4] as int[]                                    | [[-1, -1, 2], [-1, 0, 1]] as List
        [-4, -2, 1, -5, -4, -4, 4, -2, 0, 4, 0, -2, 3, 1, -5, 0] as int[] | [[-5, 1, 4], [-4, 0, 4], [-4, 1, 3], [-2, -2, 4], [-2, 1, 1], [0, 0, 0]] as List
    }

    def '树的路径总和'() {
        given:
        TreeNode n2 = new TreeNode(2)
        TreeNode n3 = new TreeNode(3)
        TreeNode n1 = new TreeNode(1, n2, n3)

        when:
        def ans = solution.pathSum(n1, 4)

        then:
        ans == 1
    }

    def '数组单调测试'() {


        expect:
        solution.isMonotonic(arr) == ans

        where:
        arr                   | ans
        [1, 3, 2] as int[]    | false
        [1, 2, 4, 5] as int[] | true
    }

    def '数独是否合理'() {

        when:

        def board = [
                ["5", "3", ".", ".", "7", ".", ".", ".", "."],
                ["6", ".", ".", "1", "9", "5", ".", ".", "."],
                [".", "9", "8", ".", ".", ".", ".", "6", "."],
                ["8", ".", ".", ".", "6", ".", ".", ".", "3"],
                ["4", ".", ".", "8", ".", "3", ".", ".", "1"],
                ["7", ".", ".", ".", "2", ".", ".", ".", "6"],
                [".", "6", ".", ".", ".", ".", "2", "8", "."],
                [".", ".", ".", "4", "1", "9", ".", ".", "5"],
                [".", ".", ".", ".", "8", ".", ".", "7", "9"]] as char[][]
        def isValid = solution.isValidSudoku(board)
        then:
        isValid

        when:
        board = [[".", ".", ".", ".", "5", ".", ".", "1", "."],
                 [".", "4", ".", "3", ".", ".", ".", ".", "."],
                 [".", ".", ".", ".", ".", "3", ".", ".", "1"],
                 ["8", ".", ".", ".", ".", ".", ".", "2", "."],
                 [".", ".", "2", ".", "7", ".", ".", ".", "."],
                 [".", "1", "5", ".", ".", ".", ".", ".", "."],
                 [".", ".", ".", ".", ".", "2", ".", ".", "."],
                 [".", "2", ".", "9", ".", ".", ".", ".", "."],
                 [".", ".", "4", ".", ".", ".", ".", ".", "."]] as char[][]
        isValid = solution.isValidSudoku(board)
        then:
        !isValid
    }

    def '字母移位'() {


        expect:
        solution.shiftingLetters(S, arr).equals(ans)

        where:
        S                      | arr                                                                                                                                                                                                                                  | ans
        "abc"                  | [3, 5, 9] as int[]                                                                                                                                                                                                                   | "rpl"
        "mkgfzkkuxownxvfvxasy" | [505870226, 437526072, 266740649, 224336793, 532917782, 311122363, 567754492, 595798950, 81520022, 684110326, 137742843, 275267355, 856903962, 148291585, 919054234, 467541837, 622939912, 116899933, 983296461, 536563513] as int[] | "wqqwlcjnkphhsyvrkdod"
    }

    def "分组最大数"() {


        expect:
        solution.maxSumAfterPartitioning(A, K) == ans

        where:
        A                                          | K | ans
        [1, 15, 7, 9, 2, 5, 10] as int[]           | 3 | 84
        [1, 4, 1, 5, 7, 3, 6, 1, 9, 9, 3] as int[] | 4 | 83
    }

    def "模式匹配"() {


        expect:
        solution.wordPattern(pattern, str) == ans

        where:
        pattern | str               | ans
        "abba"  | "dog cat cat dog" | true
        "abba"  | "dog dog dog dog" | false
    }

    def "优美子数组"() {


        expect:
        solution.subarraySum(nums, k) == ans

        where:
        nums                     | k | ans
        [100, 99, 2, 1] as int[] | 3 | 1
        [1, 1, 2, 1, 1] as int[] | 3 | 2
        [2, 4, 6] as int[]       | 1 | 0
        [1, 100, 1] as int[]     | 2 | 0
    }

    def '和大于等于target的最短子数组'() {
        expect:
        solution.minSubArrayLen(k, nums) == ans
        solution.minSubArrayLen2(k, nums) == ans
        where:
        nums                        | k  | ans
        [2, 3, 1, 2, 4, 3] as int[] | 7  | 2
        [1, 2, 3, 4, 5] as int[]    | 15 | 5
    }

    def '大小为K且平均值大于等于阈值的子数组数目'() {


        expect:
        solution.numOfSubarrays(arr, k, threshold) == nums

        where:
        nums | arr                                           | k | threshold
        5    | [1, 1, 1, 1, 1] as int[]                      | 1 | 0
        3    | [2, 2, 2, 2, 5, 5, 5, 8] as int[]             | 3 | 4
        6    | [11, 13, 17, 23, 29, 31, 7, 5, 2, 3] as int[] | 3 | 6
        1    | [7, 7, 7, 7, 7, 7, 7] as int[]                | 7 | 7
        6    | [11, 13, 17, 23, 29, 31, 7, 5, 2, 3] as int[] | 3 | 5
        1    | [4, 4, 4, 4] as int[]                         | 4 | 1
    }

    def '两数相加'() {
        given:
        def left = ListNode.create([2, 4, 3] as int[])
        def right = ListNode.create([5, 6, 4] as int[])
        def sum = ListNode.create([7, 0, 8] as int[])
        expect:
        solution.addTwoNumbers(left, right) == sum
    }

    def '最大无重复子序列'() {


        expect:
        solution.lengthOfLongestSubstring(s) == max

        where:
        s          | max
        "abcabcbb" | 3
        "bbbbbb"   | 1
        "pwwkew"   | 3
        " "        | 1
        "au"       | 2
    }

    def '目标和'() {


        expect:
//        solution.findTargetSumWays(arr, S) == num
        solution.findTargetSumWays1(arr, S) == num

        where:
        arr                      | S | num
        [1, 1, 1, 1, 1] as int[] | 3 | 5
//        [1, 0] as int[] | 1 | 2
    }

    def '搜索词汇'() {
        expect:
        solution.suggestedProducts(products, searchWord) == ans

        where:
        products                                                           | searchWord | ans
        ["mobile", "mouse", "moneypot", "monitor", "mousepad"] as String[] | "mouse"    | [
                ["mobile", "moneypot", "monitor"],
                ["mobile", "moneypot", "monitor"],
                ["mouse", "mousepad"],
                ["mouse", "mousepad"],
                ["mouse", "mousepad"] as List<List<String>>
        ]
    }

    def '验证分数到小数'() {
        expect:
        solution.fractionToDecimal(numerator, denominator) == ans

        where:
        numerator   | denominator | ans
        1           | 2           | "0.5"
        2           | 1           | "2"
        2           | 3           | "0.(6)"
        -1          | -2147483648 | "0.0000000004656612873077392578125"
        -2147483648 | 1           | "-2147483648"
        7           | -12         | "-0.58(3)"
    }

    def '最长重复子字符串'() {
        expect:
        solution.longestDupSubstring(S) == longestDup

        where:
        S | longestDup
        "banana" | "ana"
        "abcd" | ""
        "moplvidmaagmsiyyrkchbyhivlqwqsjcgtumqscmxrxrvwsnjjvygrelcbjgbpounhuyealllginkitfaiviraqcycjmskrozcdqylbuejrgfnquercvghppljmojfvylcxakyjxnampmakyjbqgwbyokaybcuklkaqzawageypfqhhasetugatdaxpvtevrigynxbqodiyioapgxqkndujeranxgebnpgsukybyowbxhgpkwjfdywfkpufcxzzqiuglkakibbkobonunnzwbjktykebfcbobxdflnyzngheatpcvnhdwkkhnlwnjdnrmjaevqopvinnzgacjkbhvsdsvuuwwhwesgtdzuctshytyfugdqswvxisyxcxoihfgzxnidnfadphwumtgdfmhjkaryjxvfquucltmuoosamjwqqzeleaiplwcbbxjxxvgsnonoivbnmiwbnijkzgoenohqncjqnckxbhpvreasdyvffrolobxzrmrbvwkpdbfvbwwyibydhndmpvqyfmqjwosclwxhgxmwjiksjvsnwupraojuatksjfqkvvfroqxsraskbdbgtppjrnzpfzabmcczlwynwomebvrihxugvjmtrkzdwuafozjcfqacenabmmxzcueyqwvbtslhjeiopgbrbvfbnpmvlnyexopoahgmwplwxnxqzhucdieyvbgtkfmdeocamzenecqlbhqmdfrvpsqyxvkkyfrbyolzvcpcbkdprttijkzcrgciidavsmrczbollxbkytqjwbiupvsorvkorfriajdtsowenhpmdtvamkoqacwwlkqfdzorjtepwlemunyrghwlvjgaxbzawmikfhtaniwviqiaeinbsqidetfsdbgsydkxgwoqyztaqmyeefaihmgrbxzyheoegawthcsyyrpyvnhysynoaikwtvmwathsomddhltxpeuxettpbeftmmyrqclnzwljlpxazrzzdosemwmthcvgwtxtinffopqxbufjwsvhqamxpydcnpekqhsovvqugqhbgweaiheeicmkdtxltkalexbeftuxvwnxmqqjeyourvbdfikqnzdipmmmiltjapovlhkpunxljeutwhenrxyfeufmzipqvergdkwptkilwzdxlydxbjoxjzxwcfmznfqgoaemrrxuwpfkftwejubxkgjlizljoynvidqwxnvhngqakmmehtvykbjwrrrjvwnrteeoxmtygiiygynedvfzwkvmffghuduspyyrnftyvsvjstfohwwyxhmlfmwguxxzgwdzwlnnltpjvnzswhmbzgdwzhvbgkiddhirgljbflgvyksxgnsvztcywpvutqryzdeerlildbzmtsgnebvsjetdnfgikrbsktbrdamfccvcptfaaklmcaqmglneebpdxkvcwwpndrjqnpqgbgihsfeotgggkdbvcdwfjanvafvxsvvhzyncwlmqqsmledzfnxxfyvcmhtjreykqlrfiqlsqzraqgtmocijejneeezqxbtomkwugapwesrinfiaxwxradnuvbyssqkznwwpsbgatlsxfhpcidfgzrc" | "akyj"
    }

    def "删列造序"() {
        expect:
        solution.minDeletionSize(A) == N

        where:
        A | N
        ["ca", "bb", "ac"] as String[] | 1
        ["xc", "yb", "za"] as String[] | 0
        ["zyx", "wvu", "tsr"] as String[] | 3
        ["xga", "xfb", "yfa"] as String[] | 1
        ["vqyoysnpxbjiitandmvugsqpfmggkv", "uzdfeclxepjzfecmsxrqqkcomtrnvm", "yvhwrsapfffwehdmvqwxstgeexfeua", "awjymwysjpazpgdeqtvdiebfwuapin", "odhihlbvsnximvdwqntdeqptigiyik", "qtrfpwiilxskcieilfvarqbnpdxham", "whvrqkdwuzbcaagsmlfvfbeataygud", "kncwqrmejjmhtfhppsrdmzqperwlww", "hgphuwaumjjibzhvvejpniopjxizie", "bxvccswqevnudqicgrvjecfqpeppob", "nnmvncnpbksdjyjjelsjizliicxpgz", "oifmofrkbgpxlhkcbibwaoiygmqqio", "ekdfyvsumngcfjlydgpmhgjjyfovfi", "fyqryrpkvauhkylmfzhuasjxpqrohx", "rdvjglvpavzdmtobnpjfwdwivhrpsj", "zahrkuiejecndfprwysunznialtfok", "jlrgpfdptlolmlqoophhciiqjnxdkh", "bhbsdukebqvvemrcunboipprcbrfcl", "kreyeyvsmufolvsrzdyeqpuqlieeij", "vgosaxsfnbsndstjohgyknyionhoga", "igmnlibpadandgtugbgxpxwlqbknmv", "mjdbxxprxbjegvtthlrenhfpdlamww", "qfssehellhvqyntozbrizixptppfpr", "utghfndlcturahtcvmqrjyxqfhrsxt", "xvminqhybbiadetniqfwubqxmjokjv", "udfckncwvhcrmxtbkqbqqptymlqnss", "gwwcmterazvyakuvwtyhthfiohlywq", "mpieryurvarojfvhfbbcwepdeoedri", "lpaonsugmlzuweyvrrlgwwdjsgwmoh", "kexyawgkinwvjvzwvofqlthmhaicgs"] as String[] | 22
    }

    def '四因数'() {
        expect:
        solution.sumFourDivisors(nums) == ans


        where:
        nums                | ans
        [21, 4, 7] as int[] | 32
    }

    def '合并区间'() {
        expect:
        solution.merge2(intervals) == ans

        where:
        intervals                                      | ans
        [[1, 3], [2, 6], [8, 10], [15, 18]] as int[][] | [[1, 6], [8, 10], [15, 18]] as int[][]
        [[1, 4], [0, 1]] as int[][]                    | [[0, 4]] as int[][]
    }

    def '生成螺旋'() {
        expect:
        solution.generateMatrix(n) == matrix

        where:
        n | matrix
        3 | [[1, 2, 3], [8, 9, 4], [7, 6, 5]] as int[][]
    }

    def '生成所有括号的组合'() {
        expect:
        solution.generateParenthesis(n) == brack

        where:
        n | brack
        3 | ["((()))", "(()())", "(())()", "()(())", "()()()"] as ArrayList<String>

    }

    def '组合和2'() {
        expect:
        solution.combination2(candidates, target) == ans

        where:
        candidates                      | target | ans
        [10, 1, 2, 7, 6, 1, 5] as int[] | 8      | [
                [1, 1, 6],
                [1, 2, 5],
                [1, 7],
                [2, 6]
        ] as List<List<Integer>>
    }

    def '缺失的第一个正数'() {
        expect:
        solution.firstMissingPositive(nums) == ans
        solution.firstMissingPositive2(nums) == ans

        where:
        nums                       | ans
        [1, 2, 0] as int[]         | 3
        [3, 4, -1, 1] as int[]     | 2
        [7, 8, 9, 11, 12] as int[] | 1
        [1, 1] as int[]            | 2
    }

    def '外观数列'() {
        expect:
        solution.countAndSay(n) == str
        where:
        n | str
        4 | "1211"
    }

    def '判断包含位置'() {
        expect:
        solution.strStr(haystack, needle) == n

        where:
        haystack      | needle        | n
        "hello"       | "ll"          | 2
        "aaaaa"       | "bba"         | -1
        "a"           | "a"           | 0
        "mississippi" | "mississippi" | 0
        "mississippi" | "issipi"      | -1
    }

    def '判断回文字'() {
        expect:
        solution.longestPalindrome(s) == s1

        where:
        s             | s1
        "babad"       | "bab"
        "aacabdkacaa" | "aca"
    }

    def '删除链表节点'() {
        expect:
        solution.removeNthFromEnd(head, n) == result

        where:
        head                                         | n | result
        ListNode.create([1, 2, 3, 4, 5, 6] as int[]) | 2 | ListNode.create([1, 2, 3, 4, 6] as int[])
        ListNode.create([1, 2, 3, 4, 5, 6] as int[]) | 0 | ListNode.create([1, 2, 3, 4, 5, 6] as int[])
    }

    def '测试编码组合'() {
        expect:
        solution.numDecodings(s) == ret

        where:
        s      | ret
        "06"   | 0
        "12"   | 2
        "226"  | 3
        "2101" | 1
        "1123" | 5
    }

    def '罗马数字'() {
        expect:
        ret == solution.intToRoman(num)

        where:
        ret       | num
        "MCMXCIV" | 1994
    }

    def '螺旋矩阵'() {
        expect:
        ans == solution.spiralOrder(matrix)
        ans == solution.spiralOrder2(matrix)

        where:
        matrix                                                   | ans
        [[1, 2, 3], [4, 5, 6], [7, 8, 9]] as int[][]             | [1, 2, 3, 6, 9, 8, 7, 4, 5]
        [[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]] as int[][] | [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]
    }

    def '搜索旋转排序数组 II'() {
        expect:
        solution.search(nums, target) == ans

        where:
        nums                                                               | target | ans
        [2, 5, 6, 0, 0, 1, 2] as int[]                                     | 0      | true
        [2, 5, 6, 0, 0, 1, 2] as int[]                                     | 3      | false
        [1, 0, 1, 1, 1] as int[]                                           | 0      | true
        [1] as int[]                                                       | 1      | true
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1] as int[] | 2      | true
    }

    def '下一个排列'() {
        given:
        int[] nums = [1, 3, 2]
        when:
        solution.nextPermutation(nums)
        then:
        nums == [2, 1, 3] as int[]
    }

    def '跳跃游戏'() {
        expect:
        solution.canJump(nums) == canJump
        where:
        nums                     | canJump
        [2, 3, 1, 1, 4] as int[] | true
        [3, 2, 1, 0, 4] as int[] | false
        [1, 2, 3] as int[]       | true
        [2, 5, 0, 0] as int[]    | true
    }

    def '等式逻辑判断'() {
        expect:
        solution.equationsPossible(equations) == result

        where:
        equations                                                                                            | result
        ["g==c", "f!=e", "e==b", "j==b", "g!=a", "e==c", "b!=f", "d!=a", "j==g", "f!=i", "a==e"] as String[] | false
        ["a!=a"] as String[]                                                                                 | false
        ["a==b", "b==c", "a==c"] as String[]                                                                 | true
        ["a==b", "b==c", "a==c", "b!=c"] as String[]                                                         | false
    }

    def '判断字符串子数组满足K长最大长度'() {
        expect:
        solution.longestSubstring(s, k) == n

        where:
        s        | k | n
        "aaabb"  | 3 | 3
        "ababbc" | 2 | 5
    }

    def "乘积小于 K 的子数组"() {
        expect:
        solution.numSubarrayProductLessThanK(nums, k) == ans

        where:
        nums                   | k   | ans
        [10, 5, 2, 6] as int[] | 100 | 8
    }

    def "质因数分解"() {
        expect:
        solution.resolvePrime(num) == ans

        where:
        num        | ans
        10         | [2, 5] as List
        20         | [2, 2, 5] as List
        2083497593 | [67, 271, 114749] as List
    }

    def '路径种类'() {
        expect:
        solution.uniquePaths(m, n) == ans

        where:
        m | n | ans
        3 | 2 | 3
    }

    def '最小路径'() {
        expect:
        solution.minPathSum(grid) == ans

        where:
        grid                                         | ans
        [[1, 3, 1], [1, 5, 1], [4, 2, 1]] as int[][] | 7
    }

    def '合并排序'() {
        given:
        def arr = [3, 5, 2, 1, 4, 7, 6] as int[]
        when:
        solution.mergeSort(arr)

        then:
        for (i in 1..<arr.length) {
            assert arr[i] >= arr[i - 1]
        }
    }

    def '快排'() {
        given:
        def arr = [3, 5, 2, 1, 4, 7, 6] as int[]

        when:
        solution.quickSort(arr)

        then:
        for (i in 1..<arr.length) {
            assert arr[i] >= arr[i - 1]
        }
    }

    def '快排2'() {
        given:
        def arr = [7, 6, 5, 4, 3, 2, 1] as int[]

        when:
        solution.quickSort(arr)

        then:
        for (i in 1..<arr.length) {
            assert arr[i] >= arr[i - 1]
        }
    }

    def '快排3'() {
        given:
        def arr = [1, 2, 3, 4, 5, 6, 7] as int[]

        when:
        solution.quickSort(arr)

        then:
        for (i in 1..<arr.length) {
            assert arr[i] >= arr[i - 1]
        }
    }

    def '单词拆分'() {
        expect:
        solution.wordBreak(s, wordDict) == ans

        where:
        s          | wordDict         | ans
        "leetcode" | ["leet", "code"] | true
    }


    def '岛屿数量'() {
        expect:
        solution.numIslands(grid) == count

        where:
        grid                                    | count
        [["1", "1", "0", "0", "0"],
         ["1", "1", "0", "0", "0"],
         ["0", "0", "1", "0", "0"],
         ["0", "0", "0", "1", "1"]] as char[][] | 3
    }

    def "股票买卖最佳时间（带冻结）"() {
        expect:
        solution.maxProfit(prices) == ans
        where:
        prices                   | ans
        [1, 2, 3, 0, 2] as int[] | 3
    }

    def "二叉树的最近公共祖先"() {
        given:
        def three = new TreeNode(3)

        def five = new TreeNode(5)
        def one = new TreeNode(1)
        three.left = five
        three.right = one

        def six = new TreeNode(6)
        def two = new TreeNode(2)
        five.left = six
        five.right = two

        def zero = new TreeNode(0)
        def eight = new TreeNode(8)
        one.left = zero
        one.right = eight

        def seven = new TreeNode(7)
        def four = new TreeNode(4)
        two.left = seven
        two.right = four


        when:
        def parent = solution.lowestCommonAncestor(three, five, four)

        then:
        parent == five
    }

    def "分割等和子集"() {
        expect:
        solution.canPartition(nums) == ans
        solution.canPartition2(nums) == ans
        where:
        nums                     | ans
        [3, 3, 3, 4, 5] as int[] | true
        [1, 1, 1, 1] as int[]    | true
        [1, 1, 2, 2] as int[]    | true
        [1, 2, 5] as int[]       | false
    }

    def '接雨点'() {
        expect:
        solution.trap(heights) == ans
        where:
        heights                     | ans
        [4, 2, 0, 3, 2, 5] as int[] | 9
        [2, 0, 2] as int[]          | 2
        [5, 4, 1, 2] as int[]       | 1
    }

    def '柱状最大矩形'() {
        expect:
        solution.largestRectangleArea(heights) == ans
        where:
        heights                     | ans
        [2, 1, 5, 6, 2, 3] as int[] | 10
        [2, 1, 2] as int[]          | 3
        [0] as int[]                | 0
    }

    def '数字组成的最大数'() {
        expect:
        solution.largestNum(arr, high) == ans
        where:
        arr                           | high | ans
        ["1", "3", "5", "7"] as int[] | 102  | 77
        ["1", "3", "5", "7"] as int[] | 145  | 137
        ["1", "3", "5", "7"] as int[] | 155  | 153
        ["1", "3", "5", "7"] as int[] | 165  | 157
    }

    def "重复数字"() {
        expect:
        solution.findDuplicates(nums) == ans
        where:
        nums                              | ans
        [4, 3, 2, 7, 8, 2, 3, 1] as int[] | [3, 2]
    }

    def "解码"() {
        expect:
        solution.decodeString(s) == ans
        where:
        s                              | ans
        "3[z]2[2[y]pq4[2[jk]e1[f]]]ef" | "zzzyypqjkjkefjkjkefjkjkefjkjkefyypqjkjkefjkjkefjkjkefjkjkefef"
    }

    def "查找"() {
        expect:
        solution.search0(arr, target) == ans
        where:
        arr                            | target | ans
        [1, 0, 1, 1, 1] as int[]       | 0      | true
        [2, 5, 6, 0, 0, 1, 2] as int[] | 0      | true
        [2, 5, 6, 0, 0, 1, 2] as int[] | 3      | false
        [3, 1] as int[]                | 1      | true
    }

    def '查找数组最大i~j元素'() {
        expect:
        solution.findTopIJ(arr, i, j) == ans

        where:
        arr                                       | i | j | ans
        [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] as int[]  | 8 | 9 | [8, 9]
        [10, 2, 3, 1, 5, 8, 6, 7, 9, 4] as int[]  | 8 | 9 | [8, 9]
        [10, 2, 3, 10, 3, 8, 6, 9, 9, 2] as int[] | 8 | 9 | [9, 10]
    }

    def '链表分隔'() {
        expect:
        solution.partition(head, x) == y

        where:
        head                                         | x | y
        ListNode.create([1, 4, 3, 2, 5, 2] as int[]) | 3 | ListNode.create([1, 2, 2, 4, 3, 5] as int[])
    }

    def '删除链表重复元素'() {
        expect:
        solution.deleteDuplicates(head) == ans

        where:
        head                                            | ans
        ListNode.create([1, 2, 3, 3, 4, 4, 5] as int[]) | ListNode.create([1, 2, 5] as int[])
        ListNode.create([1, 1, 1, 2, 3] as int[])       | ListNode.create([2, 3] as int[])
        ListNode.create([1, 2, 2] as int[])             | ListNode.create([1] as int[])
    }
}
