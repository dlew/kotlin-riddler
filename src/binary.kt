/**
 * Proof for the Riddler problem from March 18, 2016
 *
 * http://fivethirtyeight.com/features/can-you-best-the-mysterious-man-in-the-trench-coat/
 */

fun main(args: Array<String>) {
  findBestOutcome(1, 1000, 9)
}

fun findBestOutcome(lowerBound: Int, upperBound: Int, maxGuesses: Int, verbose: Boolean = false) {
  var best = 0
  var bestGuess = 0
  for (guess in lowerBound..upperBound) {
    val outcome = outcome(lowerBound, upperBound, maxGuesses, guess)
    if (outcome > best) {
      best = outcome
      bestGuess = guess
    }

    if (verbose) {
      println(String.format("Guessing %d yields an outcome of %d", guess, outcome))
    }
  }

  println(String.format("Best outcome: guess %d", bestGuess))
}

/**
 * A measure of how profitable a guess will be - the sum of all successful amounts it can get to
 */
fun outcome(lowerBound: Int, upperBound: Int, maxGuesses: Int, guess: Int): Int {
  var outcome = 0
  for (actual in lowerBound..upperBound) {
    val result = binary(lowerBound, upperBound, guess, actual)
    if (result <= maxGuesses) {
      outcome += actual
    }
  }
  return outcome
}

/**
 * Counts how many times it takes to guess the amount of money.
 *
 * This is a non-standard binary search because it allows you to start
 * on any number (but splits the halves after that).
 */
fun binary(lowerBound: Int, upperBound: Int, guess: Int, actual: Int): Int {
  if (lowerBound > upperBound
      || guess < lowerBound || guess > upperBound
      || actual < lowerBound || actual > upperBound) {
    throw IllegalArgumentException("You entered something wrong...")
  }

  var left: Int = lowerBound
  var right: Int = upperBound
  var iterations: Int = 0
  var isFirst: Boolean = true

  while (true) {
    // On the first runthrough, don't calculate the midpoint; use the guess provided
    val current = if (isFirst) guess else (left + right) / 2
    isFirst = false

    iterations += 1 // Each time we do this, it counts as a guess
    if (current == actual) {
      break;
    } else if (current < actual) {
      left = current + 1
    } else if (current > actual) {
      right = current - 1
    }
  }

  return iterations
}