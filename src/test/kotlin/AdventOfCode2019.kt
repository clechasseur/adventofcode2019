import kotlin.test.Test
import kotlin.test.assertEquals

class AdventOfCode2019 {
    class Day1Puzzles {
        @Test
        fun `day 1, part 1`() {
            assertEquals(3361976, Day1.part1())
        }

        @Test
        fun `day 1, part 2`() {
            assertEquals(5040085, Day1.part2())
        }
    }

    class Day2Puzzles {
        @Test
        fun `day 2, part 1`() {
            assertEquals(3895705, Day2.part1())
        }

        @Test
        fun `day 2, part 2`() {
            assertEquals(6417, Day2.part2())
        }
    }

    class Day3Puzzles {
        @Test
        fun `day 3, part 1`() {
            assertEquals(232, Day3.part1())
        }

        @Test
        fun `day 3, part 2`() {
            assertEquals(6084, Day3.part2())
        }
    }

    class Day4Puzzles {
        @Test
        fun `day 4, part 1`() {
            assertEquals(895, Day4.part1())
        }

        @Test
        fun `day 4, part 2`() {
            assertEquals(591, Day4.part2())
        }
    }

    class Day5Puzzles {
        @Test
        fun `day 5, part 1`() {
            assertEquals(6761139, Day5.part1())
        }

        @Test
        fun `day 5, part 2`() {
            assertEquals(9217546, Day5.part2())
        }
    }

    class Day6Puzzles {
        @Test
        fun `day 6, part 1`() {
            assertEquals(135690, Day6.part1())
        }

        @Test
        fun `day 6, part 2`() {
            assertEquals(298, Day6.part2())
        }
    }

    class Day7Puzzles {
        @Test
        fun `day 7, part 1`() {
            assertEquals(34852, Day7.part1())
        }

        @Test
        fun `day 7, part 2`() {
            assertEquals(44282086, Day7.part2())
        }
    }

    class Day8Puzzles {
        companion object {
            private val part2expected = """
                1110010010111001111010010
                1001010010100101000010010
                1001011110100101110010010
                1110010010111001000010010
                1000010010100001000010010
                1000010010100001111001100
            """.trimIndent()
        }

        @Test
        fun `day 8, part 1`() {
            assertEquals(1452, Day8.part1())
        }

        @Test
        fun `day 8, part 2`() {
            assertEquals(part2expected, Day8.part2().joinToString("\n"))
        }
    }
}
