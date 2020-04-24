object Day1 {
    private val input = """
        87201
        146745
        99430
        67664
        67482
        72598
        114480
        111866
        146672
        51465
        59100
        87507
        106993
        61427
        97982
        133329
        133510
        117352
        58800
        129228
        102366
        77934
        149630
        71567
        139965
        130271
        53259
        134032
        54158
        74679
        148463
        101585
        51744
        112537
        59140
        92980
        83174
        67797
        58890
        55849
        50424
        112780
        104595
        114465
        90528
        86503
        51661
        124689
        101081
        81478
        124821
        83420
        108738
        76506
        111217
        104897
        133747
        128808
        81563
        106688
        67129
        130968
        75576
        101197
        129318
        135015
        149839
        110182
        104687
        147803
        140555
        70447
        63224
        85143
        146115
        77789
        64003
        115257
        61397
        86873
        143481
        129785
        68764
        99388
        91050
        109136
        101777
        98104
        103643
        131374
        83808
        125949
        147277
        144448
        112673
        136408
        75776
        141630
        116821
        113349
    """.trimIndent().lineSequence().map { it.toLong() }.toList()

    fun part1() {
        println("Day 1, part 1: ${input.asSequence().map { it.fuelReq() }.sum()}")
    }

    fun part2() {
        print("Day 1, part 2: ")
        println(input.asSequence().flatMap {
            module -> generateSequence(module.fuelReq()) { it.fuelReq() }.takeWhile { it > 0 }
        }.sum())
    }

    private fun Long.fuelReq() = this / 3 - 2
}