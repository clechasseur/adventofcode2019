import kotlin.math.ceil

object Day14 {
    private val input = """
        2 LFPRM, 4 GPNQ => 2 VGZVD
        1 KXFHM, 14 SJLP => 8 MGRTM
        2 HBXVT, 3 HNHC, 5 BDLV => 1 DKTW
        2 MGRTM, 8 RVTB => 4 DFMW
        2 SJLP => 9 PXTS
        1 NXBG => 6 FXBXZ
        32 LPSQ => 9 GSDXD
        13 LZGTR => 4 ZRMJ
        1 FTPQ, 16 CPCS => 5 HNHC
        2 THQH, 2 NDJG, 5 MSKT => 4 LRZV
        2 BDLV, 9 HBXVT, 21 NXBG => 7 PLRK
        16 LNSKQ, 41 KXFHM, 1 DKTW, 1 NCPSZ, 3 ZCSB, 11 MGRTM, 19 WNJWP, 11 KRBG => 1 FUEL
        5 FTPQ, 1 HBXVT => 4 BDLV
        15 LSDX, 1 GFJW, 1 QDHJT => 4 NKHQV
        9 CZHTP, 1 FRPTK => 6 SNBS
        17 LFLVS, 2 WCFT => 8 KGJQ
        6 CMHLP => 1 SJLP
        144 ORE => 3 KQKXZ
        3 GFJW, 1 RVTB, 1 GPNQ => 2 NXBG
        4 BDLV => 5 CMHLP
        2 LSDX => 1 LZGTR
        156 ORE => 3 NDJG
        136 ORE => 8 MSKT
        4 BDLV, 1 NKHQV, 1 RVTB => 7 LNSKQ
        1 LRZV, 3 WCFT => 2 HBXVT
        5 KGJQ, 1 SWBSN => 7 QHFX
        2 DQHBG => 4 LPSQ
        6 GSDXD => 3 LSDX
        11 RWLD, 3 BNKVZ, 4 PXTS, 3 XTRQC, 5 LSDX, 5 LMHL, 36 MGRTM => 4 ZCSB
        8 CPCS => 2 FRPTK
        5 NDJG => 3 WCFT
        1 GDQG, 1 QHFX => 4 KXFHM
        160 ORE => 3 THQH
        20 GFJW, 2 DQHBG => 6 RVTB
        2 FXBXZ, 1 WNJWP, 1 VGZVD => 5 RWLD
        3 DQHBG => 7 SWBSN
        7 QHFX => 8 CPCS
        14 HBXVT => 3 VCDW
        5 FRPTK => 7 NGDX
        1 HWFQ => 4 LFLVS
        2 CPCS => 6 ZTKSW
        9 KGJQ, 8 ZTKSW, 13 BDLV => 6 GDQG
        13 LMHL, 1 LZGTR, 18 BNKVZ, 11 VCDW, 9 DFMW, 11 FTPQ, 3 RWLD => 4 KRBG
        1 XRCH => 7 GPNQ
        3 WCFT => 9 DQHBG
        1 FTPQ => 8 CZHTP
        1 PBMR, 2 ZTKSW => 2 BNKVZ
        2 PLRK, 3 CPCS => 8 ZSGBG
        3 NGDX, 3 XRCH => 6 XTRQC
        6 ZTKSW, 11 HNHC, 22 SNBS => 9 WNJWP
        5 KQKXZ => 8 HWFQ
        23 WCFT => 7 PBMR
        1 LRZV, 1 QDHJT => 2 GFJW
        1 ZSGBG, 5 CGTHV, 9 ZRMJ => 3 LMHL
        1 DQHBG => 9 XRCH
        1 GDQG, 17 RWLD, 2 KGJQ, 8 VCDW, 2 BNKVZ, 2 WNJWP, 1 VGZVD => 3 NCPSZ
        19 SJLP, 3 ZTKSW, 1 CZHTP => 4 LFPRM
        14 SNBS => 8 CGTHV
        3 DQHBG, 4 WCFT => 1 FTPQ
        3 MSKT, 3 NDJG => 5 QDHJT
    """.trimIndent().lineSequence().map { it.toReaction() }.toList()

    fun part1(): Int {
        val root = Node(input.find { it.output.name == "FUEL" }!!)
        populateChildren(root)
        val storage = mutableListOf(Component(Int.MAX_VALUE, "ORE"))
        var reactionsNodes = root.leafs.toMutableList()
        while (storage.none { it.name == "FUEL" }) {
            val reactionNode = reactionsNodes.first()
            if (storage.removeComponents(reactionNode.value.inputs)) {
                storage.addComponent(reactionNode.value.output)
            }
        }
    }

    private fun populateChildren(node: Node<Reaction>) {
        node.children.addAll(node.value.inputs.map { needed ->
            Node(input.find { it.output.name == needed.name }!!.producingAtLeast(needed.quantity), node)
        })
        node.children.forEach { populateChildren(it) }
    }

    private fun MutableList<Component>.addComponent(component: Component) {
        when (val reserve = find { it.name == component.name }) {
            null -> add(component)
            else -> {
                remove(reserve)
                add(Component(reserve.quantity + component.quantity, reserve.name))
            }
        }
    }

    private fun MutableList<Component>.removeComponents(inputs: List<Component>): Boolean {
        val hasRequirements = inputs.map { component ->
            val reserve = find { it.name == component.name }
            reserve != null && reserve.quantity >= component.quantity
        }.reduce { acc, b -> acc && b }
        if (hasRequirements) {
            inputs.forEach { component ->
                val reserve = find { it.name == component.name }!!
                remove(reserve)
                add(Component(reserve.quantity - component.quantity, reserve.name))
            }
        }
        return hasRequirements
    }
}

private data class Component(val quantity: Int, val name: String)

private data class Reaction(val inputs: List<Component>, val output: Component)

private fun String.toComponent(): Component {
    val (quantity, name) = split(' ')
    return Component(quantity.toInt(), name)
}

private fun String.toReaction(): Reaction {
    val (inputs, output) = split(" => ")
    return Reaction(inputs.split(", ").map { it.toComponent() }, output.toComponent())
}

private operator fun Component.times(n: Int) = Component(quantity * n, name)
private operator fun Reaction.times(n: Int) = Reaction(inputs.map { it * n }, output * n)
private fun Reaction.producingAtLeast(n: Int) =  this * ceil(n.toDouble() / output.quantity.toDouble()).toInt()

private class Node<T>(val value: T, val parent: Node<T>? = null) {
    val children = mutableListOf<Node<T>>()

    val leafs: List<Node<T>>
        get() = when (children.isEmpty()) {
            true -> listOf(this)
            false -> children.flatMap { it.leafs }
        }
}
