CREATE DATABASE  IF NOT EXISTS `oj_crawling_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `oj_crawling_db`;
-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: oj_crawling_db
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `submissions`
--

DROP TABLE IF EXISTS `submissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `submissions` (
  `user_id` int NOT NULL,
  `submission_id` bigint NOT NULL,
  `contest_id` int NOT NULL,
  `data_structure_rate` decimal(2,1) NOT NULL,
  `data_structure_analyse` varchar(900) NOT NULL,
  `algo_rate` decimal(2,1) NOT NULL,
  `algo_analyse` varchar(900) NOT NULL,
  `using_AI_rate` decimal(2,1) NOT NULL,
  `using_AI_analyse` varchar(900) NOT NULL,
  `source_code` varchar(900) NOT NULL,
  PRIMARY KEY (`user_id`,`submission_id`),
  CONSTRAINT `submissions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submissions`
--

LOCK TABLES `submissions` WRITE;
/*!40000 ALTER TABLE `submissions` DISABLE KEYS */;
INSERT INTO `submissions` VALUES (1,371062827,2220,7.5,'The use of `std::vector` is appropriate for storing the input numbers. The `std::map<int, int> exist` is used correctly to check for duplicates. While `std::map` guarantees logarithmic time complexity per operation (O(log N)), for simple existence checks, `std::unordered_map` (average O(1)) or a boolean array/`std::vector<bool>` (O(1) if values are within a limited range) might offer better constant factors or worst-case linear time. However, `std::map` is a safe and correct choice, making the data structure selection good but not necessarily the most performant for this specific task.',9.0,'The algorithm correctly first checks for duplicates using the map. If duplicates are found, it prints -1 as required. If all elements are distinct, it proceeds to sort the `std::vector` using `std::sort` (which is typically an efficient hybrid sort, O(N log N)). Finally, it iterates in reverse to print the elements in descending order. This is a standard and efficient approach to solve a problem that involves checking for distinctness and then sorting, achieving an overall time complexity of O(N log N) due to map operations and sorting. The logic is sound and robust.',1.0,'The code exhibits typical competitive programming style, including `#include <bits/stdc++.h>`, `using namespace std;`, terse variable names (`t`, `n`, `flg`), and common problem-solving patterns (multiple test cases, checking for distinctness, sorting). There are no unusual comments, structures, or overly generic patterns that would strongly suggest AI generation. The approach is straightforward and indicative of human thought process in competitive programming.','#include <bits/stdc++.h>\nusing namespace std;\n int main() {\n    int t;\n    cin >> t;\n    while(t--) {\n        map<int, int> exist;\n        bool flg = true;\n        int n;\n        cin >> n;\n        vector<int> a(n + 1);\n         for (int i = 1; i <= n; i++) {\n            cin >> a[i];\n            if (exist[a[i]]) {\n                flg = false;\n            }\n            exist[a[i]] = 1;\n        }\n         if (!flg) {\n            cout << -1 << \"\\n\";\n            continue;\n        }\n         sort(a.begin() + 1, a.end());\n        for (int i = n; i >= 1; i--) cout << a[i] << \" \";\n        cout << \"\\n\";\n    }\n}'),(1,372163853,2225,1.0,'The code exclusively uses primitive data types such as `int` and `long long` to store input values. No complex or custom data structures like arrays, vectors, maps, or user-defined types are present.',1.0,'The algorithm involves a simple loop for multiple test cases. Inside the loop, it performs basic integer division and a conditional check (`if-else`) to output \'YES\' or \'NO\'. This is a very fundamental algorithm, lacking any advanced techniques like sorting, searching, dynamic programming, or graph traversal.',0.5,'The code is exceptionally simple, directly translating a basic mathematical condition into C++. Its brevity and directness suggest it was most likely written manually without AI assistance. There are no complex patterns or structures that would typically warrant the use of AI for generation.','#include <bits/stdc++.h>\nusing namespace std;\n int main() {\n    int t;\n    cin >> t;\n    while(t--) {\n        long long x, y;\n        cin >> x >> y;\n        if ((y / x) <= 2) cout << \"NO\\n\";\n        else cout << \"YES\\n\"; \n    }\n }'),(1,372231959,2203,1.0,'The code exclusively uses primitive data types (`int`, `long long`) to store numerical values. No complex or custom data structures such as arrays, vectors, maps, or trees are employed, indicating a minimal data structure requirement for this problem.',1.0,'The algorithm is a straightforward, constant-time mathematical calculation per test case. It involves basic arithmetic operations (integer division, addition, subtraction) to directly compute the result using a predefined formula. No complex algorithmic paradigms like sorting, searching, dynamic programming, or graph traversal are utilized.',1.0,'The code is extremely concise, uses standard competitive programming headers (`bits/stdc++.h`), and employs a common idiom for integer ceiling division (`(a + b - 1) / b`). Its simplicity and direct mathematical nature suggest it was derived by a human understanding the problem\'s core logic and simplifying it to a formula. There are no complex patterns, unusual structures, or verbose comments that would typically indicate AI generation.','#include <bits/stdc++.h>\nusing namespace std;\n int main() {\n    int t;\n    cin >> t;\n    while(t--) {\n        long long n, m, d;\ncin >> n >> m >> d;\n long long k = (d / m) + 1;\ncout << (n + k - 1) / k << \"\\n\";\n            }\n }'),(1,372233141,996,2.0,'The code uses a simple static integer array `k` to store a fixed set of denomination values. This is a very basic data structure, effectively utilized for its purpose but not complex or advanced in itself.',8.0,'The algorithm employs a greedy approach, iterating from the largest denomination down to the smallest. For each denomination, it calculates the maximum number of times it can be used and updates the remaining amount. This is an optimal solution for this specific change-making problem given the canonical denominations and achieves O(1) time complexity due to the fixed number of iterations.',1.0,'There is no strong indication that this code was generated by AI. It\'s a straightforward solution to a common competitive programming problem, exhibiting typical human-written style with concise variable names and standard library includes.','#include <bits/stdc++.h>\nusing namespace std;\nint k[6] = {0, 1, 5, 10, 20, 100};\nint main() {\n    int n;\n    cin >> n;\n    int i = 5;\n    int ans = 0; \n    while(i > 0) {\n        ans += n / k[i];\n        n = n % k[i];\n        i--;\n    }\n     cout << ans << \"\\n\";\n  }'),(1,372329791,2218,9.0,'The code implements a Binary Trie (also known as a Bitwise Trie or Xor Trie). The `Node` struct correctly holds child pointers for bits 0 and 1, and an `isANumber` flag. The `TrieTree` class encapsulates the Trie\'s root and provides methods for insertion, searching, and finding the maximum XOR sum. This data structure is perfectly suited for the problem of finding maximum XOR pairs. The implementation is clear and standard.',9.5,'The algorithm correctly utilizes the Bitwise Trie to efficiently solve a common competitive programming problem variant: finding the maximum XOR sum of an element in the array with any other element already inserted into the Trie. The `insert` operation takes O(W) time per number (where W is the number of bits, here 32), and the `maxXOR_of` query also takes O(W) time. The overall time complexity is O(N*W), which is optimal for this problem. The logic within `maxXOR_of` correctly prioritizes taking the opposite bit to maximize the XOR sum. The use of `#define int long long` is a standard competitive programming practice, but the bit iteration `i = 31` suggests numbers are treated as 32-bit integers, which is a minor inconsistency if `long long` was intended for 64-bit numbers.',1.0,'The code shows no indications of being generated by AI. It is a very standard and well-known competitive programming solution for the Maximum XOR Pair problem using a Bitwise Trie. The variable names, structure, and common competitive programming idioms (like fast I/O and `#include <bits/stdc++.h>`) are all typical of human-written code in this context. There is no novelty or complexity that would suggest AI involvement.','#include <bits/stdc++.h>\nusing namespace std;\n #define int long long\n struct Node{\n    Node* child[2];\n    bool isANumber;\n    Node() {\n        isANumber = false;\n        child[0] = nullptr;\n        child[1] = nullptr;\n    } \n};\n struct TrieTree{\n    Node* root;\n    TrieTree() {\n        root = new Node();\n    }\n     void insert(int num) {\n        Node* p = root;\n        for (int i = 31; i >= 0; i--) {\n            int temp = (num & (1 << i));\n            if (temp) {\n                if (p->child[1] == nullptr) {\n                    p->child[1] = new Node();\n                } \n                p = p->child[1];\n            }\n            else {\n                if (p->child[0] == nullptr) {\n                    p->child[0] = new Node();\n                }\n                p = p->child[0];\n            }\n        }\n        p->isANumber = true;\n    }\n     bool search(int num) {\n        Node* p = root;'),(1,372482192,2222,1.0,'The code primarily uses `std::vector<int>` to store the input sequence and a `bool` variable to track the presence of a specific value. The vector is initialized with size `n+1` and accessed using 1-based indexing, a common practice in competitive programming. This is a very basic and fundamental use of data structures.',1.0,'The algorithm employs a simple linear scan (O(N) time complexity) to iterate through the elements of the input array. It checks each element for equality with the value 100. If 100 is found, a boolean flag is set. After processing all elements, it prints \'YES\' or \'NO\' based on this flag. This is a very straightforward and elementary search algorithm.',0.5,'The code is extremely simple, addressing a basic \'find an element\' problem. Its structure, variable names (`t`, `n`, `a`, `has100yet`), and use of standard competitive programming headers and practices (`#include <bits/stdc++.h>`, `using namespace std;`, 1-based indexing) are entirely conventional for a human competitive programmer, especially a beginner. There are no advanced patterns or unusual constructions that would suggest AI generation.','#include <bits/stdc++.h>\nusing namespace std;\n int main() {\n    int t;\n    cin >> t;\n    while(t--) {\n        int n;\n        cin >> n;\n        vector<int> a(n + 1);\n        bool has100yet = 0;\n        for (int i = 1; i <= n; i++) {\n            cin >> a[i];\n            if (a[i] == 100) has100yet = 1;\n        }\n        if(has100yet) cout << \"YES\\n\";\n        else cout << \"NO\\n\";\n             }\n}');
/*!40000 ALTER TABLE `submissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `data_structure_rate` decimal(2,1) NOT NULL,
  `algo_rate` decimal(2,1) NOT NULL,
  `using_AI_rate` decimal(2,1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'hoangngochung120',1.0,1.0,4.0),(2,'tourist',0.0,0.0,0.0),(5,'adamant',0.0,0.0,0.0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-17  5:57:20
