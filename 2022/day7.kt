import kotlin.math.min


enum class Type {
    FILE, DIR
}

class Node constructor (
    val type: Type,
    val name: String,
    val size: Int?,
    val parent: Node?,
    val children: MutableList<Node> = mutableListOf(),
) {
    val parentList: List<Node>
    val id: String

    init {
        if (parent != null) {
            parentList = parent.parentList + parent
        } else {
            parentList = listOf()
        }

        id = parentList.map { it.name }.joinToString("/") + "/$name"
    }

    fun getSpaceUtilised(): Int {
        return (size ?: 0) + children.map { it.getSpaceUtilised() }.sum()
    }

    fun prettyPrint() {
        this.parentList.forEach {
            print("|__")
        }
        println("$name(size=$size)")
        children.forEach {
            it.prettyPrint()
        }
    }

    override fun toString(): String {
        return name
    }
}

class Context( 
    var cwd: Node,
    val root: Node,
    val input: List<String>
) {
    private var inputIdx = 0

    fun peekInput(): String {
        return input[inputIdx]
    }

    fun nextInput(): String {
        return input[inputIdx++]
    }

    fun hasMore(): Boolean {
        return inputIdx < input.size
    }
}

fun cdCommand(context: Context, arg: String): Node {
    if (arg == "..") {
        return context.cwd.parent ?: context.cwd
    }

    if (arg == "/") {
        return context.root 
    }

    if (arg == context.cwd.name) {
        return context.cwd
    }

    var newNode = context.cwd.children.find { it -> it.name == arg }
    if (newNode == null) {
        newNode = Node(Type.DIR, arg, null, context.cwd) 
        context.cwd.children.add(newNode)
    }

    return newNode
}

fun lsCommand(context: Context) {
    while (context.hasMore() && !context.peekInput().startsWith("$")) {
        val (size_or_type, name) = context.nextInput().split(" ")

        var newNode = context.cwd.children.find { it -> it.name == name }
        if (newNode == null) {
            newNode = Node(
                        if (size_or_type == "dir") Type.DIR else Type.FILE, 
                        name, 
                        size_or_type.toIntOrNull(), 
                        context.cwd
                      )
            context.cwd.children.add(newNode)
        }
    }
}

fun readData(): List<String> {
    return generateSequence { readLine() }.toList()
}

fun loadFileTree(context: Context) {
    while (context.hasMore()) {
        val command = context.nextInput().split(' ')
        if (command.size == 2) {
            lsCommand(context)
        } else {
           context.cwd = cdCommand(context, command[2])
        }
    }
}

fun run(currNode: Node, candidateDirs: MutableList<Int>): Int {
    if (currNode.type == Type.DIR) {
        var dirSize = 0
        currNode.children.forEach {
            dirSize += run(it, candidateDirs)
        }
        candidateDirs.add(dirSize)
        return dirSize
    }
    return currNode.size ?: 0
}

fun p1(context: Context): Int {
    val candidateDirs: MutableList<Int> = mutableListOf()
    run(context.root, candidateDirs)

    return candidateDirs.filter { it <= 100000 }.sum()
}

fun p2(context: Context): Int {
    val candidateDirs: MutableList<Int> = mutableListOf()
    val totalUsedSpace = context.root.getSpaceUtilised()
    val totalUnusedSpace = 70000000 - totalUsedSpace
    val spaceToFreeUp = 30000000 - totalUnusedSpace
    if (spaceToFreeUp < 0) {
        return 0
    }

    candidateDirs.add(run(context.root, candidateDirs))

    var ans = 70000000
    candidateDirs.forEach {
        if (it >= spaceToFreeUp) {
            ans = min(ans, it)
        }
    }
    return ans
}

fun main() {
    val input = readData()
    val root = Node(Type.DIR, "root", null, null)
    val context = Context(root, root, input)

    loadFileTree(context)

    println(p1(context))
}
