import kotlin.test.Ignore
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

    class Day9Puzzles {
        @Test
        fun `day 9, part 1`() {
            assertEquals(3765554916L, Day9.part1())
        }

        @Test
        fun `day 9, part 2`() {
            assertEquals(76642L, Day9.part2())
        }
    }

    class Day10Puzzles {
        @Test
        fun `day 10, part 1`() {
            assertEquals(340, Day10.part1())
        }

        @Test
        fun `day 10, part 2`() {
            assertEquals(2628, Day10.part2())
        }
    }

    class Day11Puzzles {
        companion object {
            private const val expected =
                    " #### #  # #### #  #  ##  #### ###  #  #   \n" +
                    " #    # #  #    # #  #  # #    #  # # #    \n" +
                    " ###  ##   ###  ##   #    ###  #  # ##     \n" +
                    " #    # #  #    # #  #    #    ###  # #    \n" +
                    " #    # #  #    # #  #  # #    # #  # #    \n" +
                    " #    #  # #### #  #  ##  #    #  # #  #   "
        }

        @Test
        fun `day 11, part 1`() {
            assertEquals(1964, Day11.part1())
        }

        @Test
        fun `day 11, part 2`() {
            assertEquals(expected, Day11.part2())
        }
    }

    class Day12Puzzles {
        @Test
        fun `day 12, part 1`() {
            assertEquals(7687, Day12.part1())
        }

        @Test
        fun `day 12, part 2`() {
            assertEquals(334_945_516_288_044L, Day12.part2())
        }
    }

    class Day13Puzzles {
        @Test
        fun `day 13, part 1`() {
            assertEquals(312, Day13.part1())
        }

        @Test
        fun `day 13, part 2`() {
            assertEquals(15909L, Day13.part2())
        }
    }

    class Day14Puzzles {
        @Test
        fun `day 14, part 1`() {
            assertEquals(720_484L, Day14.part1())
        }

        @Test
        fun `day 14, part 2`() {
            assertEquals(1_993_284L, Day14.part2())
        }
    }

    class Day15Puzzles {
        @Test
        fun `day 15, part 1`() {
            assertEquals(380, Day15.part1())
        }

        @Test
        fun `day 15, part 2`() {
            assertEquals(410, Day15.part2())
        }
    }

    class Day16Puzzles {
        @Test
        fun `day 16, part 1`() {
            assertEquals(73127523, Day16.part1())
        }

        @Test
        fun `day 16, part 2`() {
            assertEquals(80284420, Day16.part2())
        }
    }

    class Day17Puzzles {
        @Test
        fun `day 17, part 1`() {
            assertEquals(13580, Day17.part1())
        }

        @Test
        fun `day 17, part 2`() {
            assertEquals(1063081L, Day17.part2())
        }
    }

    class Day18Puzzles {
        @Test
        fun `day 18, part 1`() {
            assertEquals(5392L, Day18.part1())
        }

        @Test
        fun `day 18, part 2`() {
            assertEquals(1684L, Day18.part2())
        }
    }

    class Day19Puzzles {
        @Test
        fun `day 19, part 1`() {
            assertEquals(141, Day19.part1())
        }

        @Test
        @Ignore
        fun `day 19, part 2`() {
            assertEquals(15641348, Day19.part2())
        }
    }

    class Day20Puzzles {
        @Test
        fun `day 20, part 1`() {
            assertEquals(570L, Day20.part1())
        }

        @Test
        fun `day 20, part 2`() {
            assertEquals(7056L, Day20.part2())
        }
    }

    class Day21Puzzles {
        @Test
        fun `day 21, part 1`() {
            assertEquals(19357534L, Day21.part1())
        }

        @Test
        fun `day 21, part 2`() {
            assertEquals(1142814363L, Day21.part2())
        }
    }

    class Day22Puzzles {
        @Test
        fun `day 22, part 1`() {
            assertEquals(2604L, Day22.part1())
        }

        @Test
        fun `day 22, part 2`() {
            assertEquals(79_608_410_258_462L, Day22.part2())
        }
    }

    class Day23Puzzles {
        @Test
        fun `day 23, part 1`() {
            assertEquals(24954L, Day23.part1())
        }

        @Test
        fun `day 23, part 2`() {
            assertEquals(17091L, Day23.part2())
        }
    }

    class Day24Puzzles {
        @Test
        fun `day 24, part 1`() {
            assertEquals(28_903_899, Day24.part1())
        }

        @Test
        fun `day 24, part 2`() {
            assertEquals(1896, Day24.part2())
        }
    }
}
