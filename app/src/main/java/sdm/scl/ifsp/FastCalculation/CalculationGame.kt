package sdm.scl.ifsp.FastCalculation

import kotlin.random.Random

enum class Operator { ADD, SUB, MUL, DIV }
class CalculationGame(private val rounds: Int) {
    companion object {
        private const val INITIAL_VALUE = 1
        private const val FINAL_VALUE = 9
    }

    private var currentRound: Int = 0
    private val random = Random(System.currentTimeMillis())

    fun nextRound(): Round? {
        if (currentRound >= rounds) return null
        var op1 = random.nextInt(INITIAL_VALUE, FINAL_VALUE + 1)
        var op2 = random.nextInt(INITIAL_VALUE, FINAL_VALUE + 1)
        while (Math.abs(op1 - op2) < 2) {
            op2 = random.nextInt(INITIAL_VALUE, FINAL_VALUE + 1)
        }
        if (op1 < op2) {
            val temp = op1
            op1 = op2
            op2 = temp
        }
        val operator = Operator.values()[random.nextInt(4)]
        val answer: Int
        val question: String
        when (operator) {
            Operator.ADD -> {
                answer = op1 + op2
                question = "$op1 + $op2"
            }

            Operator.SUB -> {
                answer = op1 - op2
                question = "$op1 - $op2"
            }

            Operator.MUL -> {
                answer = op1 * op2
                question = "$op1 x $op2"
            }

            Operator.DIV -> {
                answer = op1 / op2
                question = "$op1 / $op2"
            }
        }
        currentRound++
        val altList = genAlternatives(op1, op2, answer)
        return Round(question, answer, altList[0], altList[1], altList[2], currentRound)
    }

    private fun genAlternatives(op1: Int, op2: Int, answer: Int): List<Int> {
        val operationSet = mutableSetOf(op1 - op2, op1 + op2, op1 * op2, op1 / op2)
        val alternativeSet = mutableSetOf(answer)
        while (alternativeSet.size < 3) {
            alternativeSet.add(random.nextInt(operationSet.min(), operationSet.max() + 1))
        }
        return alternativeSet.toList().shuffled()
    }

    data class Round(
        val question: String,
        val answer: Int,
        val alt1: Int,
        val alt2: Int,
        val alt3: Int,
        val round: Int
    )
}