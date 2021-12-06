package com.degofedal.advent.advent2020

import com.degofedal.advent.AdventOfCode

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

trait Dec22 extends AdventOfCode {

  case class Player(var cards: List[Int], id: Int = 0) {
    def hasCards: Boolean = cards.size > 0

    def score: Int = cards.reverse.zipWithIndex.map(t => t._1 * (t._2+1)).sum
  }

  def parsePlayers(raw: List[String]): (Player,Player) = {
    val p1 = raw.takeWhile(s => s != "").drop(1).map(_.toInt)
    val p2 = raw.drop(p1.size+3).map(_.toInt)
    (Player(p1,1),Player(p2,2))
  }

  def draw(p1: Player, p2: Player): Unit = {
    val p1h = p1.cards.head
    val p2h = p2.cards.head
    if(p1h > p2h) {
      p1.cards = p1.cards.tail ::: List(p1h, p2h)
      p2.cards = p2.cards.tail
    } else {
      p1.cards = p1.cards.tail
      p2.cards = p2.cards.tail ::: List(p2h, p1h)
    }
  }

}

/**
 * --- Day 22: Crab Combat ---
 * It only takes a few hours of sailing the ocean on a raft for boredom to sink in.
 * Fortunately, you brought a small deck of space cards!
 * You'd like to play a game of Combat,
 * and there's even an opponent available: a small crab that climbed aboard your raft before you left.
 *
 * Fortunately, it doesn't take long to teach the crab the rules.
 *
 * Before the game starts, split the cards so each player has their own deck (your puzzle input).
 * Then, the game consists of a series of rounds:
 * both players draw their top card, and the player with the higher-valued card wins the round.
 * The winner keeps both cards, placing them on the bottom of their own deck so that the winner's card is above the other card.
 * If this causes a player to have all of the cards, they win, and the game ends.
 *
 * For example, consider the following starting decks:
 *
 * Player 1:
 * 9
 * 2
 * 6
 * 3
 * 1
 *
 * Player 2:
 * 5
 * 8
 * 4
 * 7
 * 10
 * This arrangement means that player 1's deck contains 5 cards, with 9 on top and 1 on the bottom;
 * player 2's deck also contains 5 cards, with 5 on top and 10 on the bottom.
 *
 * The first round begins with both players drawing the top card of their decks: 9 and 5.
 * Player 1 has the higher card, so both cards move to the bottom of player 1's deck such that 9 is above 5.
 * In total, it takes 29 rounds before a player has all of the cards:
 *
 * -- Round 1 --
 * Player 1's deck: 9, 2, 6, 3, 1
 * Player 2's deck: 5, 8, 4, 7, 10
 * Player 1 plays: 9
 * Player 2 plays: 5
 * Player 1 wins the round!
 *
 * -- Round 2 --
 * Player 1's deck: 2, 6, 3, 1, 9, 5
 * Player 2's deck: 8, 4, 7, 10
 * Player 1 plays: 2
 * Player 2 plays: 8
 * Player 2 wins the round!
 *
 * -- Round 3 --
 * Player 1's deck: 6, 3, 1, 9, 5
 * Player 2's deck: 4, 7, 10, 8, 2
 * Player 1 plays: 6
 * Player 2 plays: 4
 * Player 1 wins the round!
 *
 * -- Round 4 --
 * Player 1's deck: 3, 1, 9, 5, 6, 4
 * Player 2's deck: 7, 10, 8, 2
 * Player 1 plays: 3
 * Player 2 plays: 7
 * Player 2 wins the round!
 *
 * -- Round 5 --
 * Player 1's deck: 1, 9, 5, 6, 4
 * Player 2's deck: 10, 8, 2, 7, 3
 * Player 1 plays: 1
 * Player 2 plays: 10
 * Player 2 wins the round!
 *
 * ...several more rounds pass...
 *
 * -- Round 27 --
 * Player 1's deck: 5, 4, 1
 * Player 2's deck: 8, 9, 7, 3, 2, 10, 6
 * Player 1 plays: 5
 * Player 2 plays: 8
 * Player 2 wins the round!
 *
 * -- Round 28 --
 * Player 1's deck: 4, 1
 * Player 2's deck: 9, 7, 3, 2, 10, 6, 8, 5
 * Player 1 plays: 4
 * Player 2 plays: 9
 * Player 2 wins the round!
 *
 * -- Round 29 --
 * Player 1's deck: 1
 * Player 2's deck: 7, 3, 2, 10, 6, 8, 5, 9, 4
 * Player 1 plays: 1
 * Player 2 plays: 7
 * Player 2 wins the round!
 *
 *
 * == Post-game results ==
 * Player 1's deck:
 * Player 2's deck: 3, 2, 10, 6, 8, 5, 9, 4, 7, 1
 * Once the game ends, you can calculate the winning player's score.
 * The bottom card in their deck is worth the value of the card multiplied by 1,
 * the second-from-the-bottom card is worth the value of the card multiplied by 2, and so on.
 * With 10 cards, the top card is worth the value on the card multiplied by 10.
 * In this example, the winning player's score is:
 *
 * 3 * 10
 * +  2 *  9
 * + 10 *  8
 * +  6 *  7
 * +  8 *  6
 * +  5 *  5
 * +  9 *  4
 * +  4 *  3
 * +  7 *  2
 * +  1 *  1
 * = 306
 * So, once the game ends, the winning player's score is 306.
 *
 * Play the small crab in a game of Combat using the two decks you just dealt. What is the winning player's score?
 */
object Dec22a extends Dec22 with App {
  val raw = inputAsStringList("2020/dec22.txt")

  //raw.foreach(println)

  //raw.takeWhile(s => s != "").foreach(println) //.drop(1).map(_.toInt).foreach(println)

  val (p1,p2) = parsePlayers(raw)

  do {
    draw(p1,p2)
  } while(p1.hasCards && p2.hasCards)

  println(p1)
  println(p2)

  // calculate score
  val score = if(p1.hasCards) {
    p1.cards.reverse.zipWithIndex.map(t => t._1 * (t._2+1))
  } else {
    p2.cards.reverse.zipWithIndex.map(t => t._1 * (t._2+1))
  }
  println(s"winning score: ${score.sum}")
  // 32677
}

/**
 * --- Part Two ---
 * You lost to the small crab! Fortunately, crabs aren't very good at recursion.
 * To defend your honor as a Raft Captain, you challenge the small crab to a game of Recursive Combat.
 *
 * Recursive Combat still starts by splitting the cards into two decks
 * (you offer to play with the same starting decks as before - it's only fair).
 * Then, the game consists of a series of rounds with a few changes:
 *
 * Before either player deals a card, if there was a previous round in this game that had
 * exactly the same cards in the same order in the same players' decks, the game instantly ends in a win for player 1.
 *
 * Previous rounds from other games are not considered.
 * (This prevents infinite games of Recursive Combat, which everyone agrees is a bad idea.)
 *
 * Otherwise, this round's cards must be in a new configuration;
 * the players begin the round by each drawing the top card of their deck as normal.
 * If both players have at least as many cards remaining in their deck as the value of the card they just drew,
 * the winner of the round is determined by playing a new game of Recursive Combat (see below).
 * Otherwise, at least one player must not have enough cards left in their deck to recurse;
 * the winner of the round is the player with the higher-value card.
 * As in regular Combat, the winner of the round (even if they won the round by winning a sub-game)
 * takes the two cards dealt at the beginning of the round and places them on the bottom of their own deck
 * (again so that the winner's card is above the other card).
 * Note that the winner's card might be the lower-valued of the two cards if they won the round due to winning a sub-game.
 * If collecting cards by winning the round causes a player to have all of the cards, they win, and the game ends.
 *
 * Here is an example of a small game that would loop forever without the infinite game prevention rule:
 *
 * Player 1:
 * 43
 * 19
 *
 * Player 2:
 * 2
 * 29
 * 14
 * During a round of Recursive Combat, if both players have at least as many cards in their
 * own decks as the number on the card they just dealt, the winner of the round is determined
 * by recursing into a sub-game of Recursive Combat.
 * (For example, if player 1 draws the 3 card, and player 2 draws the 7 card,
 * this would occur if player 1 has at least 3 cards left and player 2 has at least 7 cards left,
 * not counting the 3 and 7 cards that were drawn.)
 *
 * To play a sub-game of Recursive Combat, each player creates a new deck by making a copy of the next cards in their deck
 * (the quantity of cards copied is equal to the number on the card they drew to trigger the sub-game).
 * During this sub-game, the game that triggered it is on hold and completely unaffected;
 * no cards are removed from players' decks to form the sub-game.
 * (For example, if player 1 drew the 3 card, their deck in the sub-game would be copies of the next three cards in their deck.)
 *
 * Here is a complete example of gameplay, where Game 1 is the primary game of Recursive Combat:
 *
 * === Game 1 ===
 *
 * -- Round 1 (Game 1) --
 * Player 1's deck: 9, 2, 6, 3, 1
 * Player 2's deck: 5, 8, 4, 7, 10
 * Player 1 plays: 9
 * Player 2 plays: 5
 * Player 1 wins round 1 of game 1!
 *
 * -- Round 2 (Game 1) --
 * Player 1's deck: 2, 6, 3, 1, 9, 5
 * Player 2's deck: 8, 4, 7, 10
 * Player 1 plays: 2
 * Player 2 plays: 8
 * Player 2 wins round 2 of game 1!
 *
 * -- Round 3 (Game 1) --
 * Player 1's deck: 6, 3, 1, 9, 5
 * Player 2's deck: 4, 7, 10, 8, 2
 * Player 1 plays: 6
 * Player 2 plays: 4
 * Player 1 wins round 3 of game 1!
 *
 * -- Round 4 (Game 1) --
 * Player 1's deck: 3, 1, 9, 5, 6, 4
 * Player 2's deck: 7, 10, 8, 2
 * Player 1 plays: 3
 * Player 2 plays: 7
 * Player 2 wins round 4 of game 1!
 *
 * -- Round 5 (Game 1) --
 * Player 1's deck: 1, 9, 5, 6, 4
 * Player 2's deck: 10, 8, 2, 7, 3
 * Player 1 plays: 1
 * Player 2 plays: 10
 * Player 2 wins round 5 of game 1!
 *
 * -- Round 6 (Game 1) --
 * Player 1's deck: 9, 5, 6, 4
 * Player 2's deck: 8, 2, 7, 3, 10, 1
 * Player 1 plays: 9
 * Player 2 plays: 8
 * Player 1 wins round 6 of game 1!
 *
 * -- Round 7 (Game 1) --
 * Player 1's deck: 5, 6, 4, 9, 8
 * Player 2's deck: 2, 7, 3, 10, 1
 * Player 1 plays: 5
 * Player 2 plays: 2
 * Player 1 wins round 7 of game 1!
 *
 * -- Round 8 (Game 1) --
 * Player 1's deck: 6, 4, 9, 8, 5, 2
 * Player 2's deck: 7, 3, 10, 1
 * Player 1 plays: 6
 * Player 2 plays: 7
 * Player 2 wins round 8 of game 1!
 *
 * -- Round 9 (Game 1) --
 * Player 1's deck: 4, 9, 8, 5, 2
 * Player 2's deck: 3, 10, 1, 7, 6
 * Player 1 plays: 4
 * Player 2 plays: 3
 * Playing a sub-game to determine the winner...
 *
 * === Game 2 ===
 *
 * -- Round 1 (Game 2) --
 * Player 1's deck: 9, 8, 5, 2
 * Player 2's deck: 10, 1, 7
 * Player 1 plays: 9
 * Player 2 plays: 10
 * Player 2 wins round 1 of game 2!
 *
 * -- Round 2 (Game 2) --
 * Player 1's deck: 8, 5, 2
 * Player 2's deck: 1, 7, 10, 9
 * Player 1 plays: 8
 * Player 2 plays: 1
 * Player 1 wins round 2 of game 2!
 *
 * -- Round 3 (Game 2) --
 * Player 1's deck: 5, 2, 8, 1
 * Player 2's deck: 7, 10, 9
 * Player 1 plays: 5
 * Player 2 plays: 7
 * Player 2 wins round 3 of game 2!
 *
 * -- Round 4 (Game 2) --
 * Player 1's deck: 2, 8, 1
 * Player 2's deck: 10, 9, 7, 5
 * Player 1 plays: 2
 * Player 2 plays: 10
 * Player 2 wins round 4 of game 2!
 *
 * -- Round 5 (Game 2) --
 * Player 1's deck: 8, 1
 * Player 2's deck: 9, 7, 5, 10, 2
 * Player 1 plays: 8
 * Player 2 plays: 9
 * Player 2 wins round 5 of game 2!
 *
 * -- Round 6 (Game 2) --
 * Player 1's deck: 1
 * Player 2's deck: 7, 5, 10, 2, 9, 8
 * Player 1 plays: 1
 * Player 2 plays: 7
 * Player 2 wins round 6 of game 2!
 * The winner of game 2 is player 2!
 *
 * ...anyway, back to game 1.
 * Player 2 wins round 9 of game 1!
 *
 * -- Round 10 (Game 1) --
 * Player 1's deck: 9, 8, 5, 2
 * Player 2's deck: 10, 1, 7, 6, 3, 4
 * Player 1 plays: 9
 * Player 2 plays: 10
 * Player 2 wins round 10 of game 1!
 *
 * -- Round 11 (Game 1) --
 * Player 1's deck: 8, 5, 2
 * Player 2's deck: 1, 7, 6, 3, 4, 10, 9
 * Player 1 plays: 8
 * Player 2 plays: 1
 * Player 1 wins round 11 of game 1!
 *
 * -- Round 12 (Game 1) --
 * Player 1's deck: 5, 2, 8, 1
 * Player 2's deck: 7, 6, 3, 4, 10, 9
 * Player 1 plays: 5
 * Player 2 plays: 7
 * Player 2 wins round 12 of game 1!
 *
 * -- Round 13 (Game 1) --
 * Player 1's deck: 2, 8, 1
 * Player 2's deck: 6, 3, 4, 10, 9, 7, 5
 * Player 1 plays: 2
 * Player 2 plays: 6
 * Playing a sub-game to determine the winner...
 *
 * === Game 3 ===
 *
 * -- Round 1 (Game 3) --
 * Player 1's deck: 8, 1
 * Player 2's deck: 3, 4, 10, 9, 7, 5
 * Player 1 plays: 8
 * Player 2 plays: 3
 * Player 1 wins round 1 of game 3!
 *
 * -- Round 2 (Game 3) --
 * Player 1's deck: 1, 8, 3
 * Player 2's deck: 4, 10, 9, 7, 5
 * Player 1 plays: 1
 * Player 2 plays: 4
 * Playing a sub-game to determine the winner...
 *
 * === Game 4 ===
 *
 * -- Round 1 (Game 4) --
 * Player 1's deck: 8
 * Player 2's deck: 10, 9, 7, 5
 * Player 1 plays: 8
 * Player 2 plays: 10
 * Player 2 wins round 1 of game 4!
 * The winner of game 4 is player 2!
 *
 * ...anyway, back to game 3.
 * Player 2 wins round 2 of game 3!
 *
 * -- Round 3 (Game 3) --
 * Player 1's deck: 8, 3
 * Player 2's deck: 10, 9, 7, 5, 4, 1
 * Player 1 plays: 8
 * Player 2 plays: 10
 * Player 2 wins round 3 of game 3!
 *
 * -- Round 4 (Game 3) --
 * Player 1's deck: 3
 * Player 2's deck: 9, 7, 5, 4, 1, 10, 8
 * Player 1 plays: 3
 * Player 2 plays: 9
 * Player 2 wins round 4 of game 3!
 * The winner of game 3 is player 2!
 *
 * ...anyway, back to game 1.
 * Player 2 wins round 13 of game 1!
 *
 * -- Round 14 (Game 1) --
 * Player 1's deck: 8, 1
 * Player 2's deck: 3, 4, 10, 9, 7, 5, 6, 2
 * Player 1 plays: 8
 * Player 2 plays: 3
 * Player 1 wins round 14 of game 1!
 *
 * -- Round 15 (Game 1) --
 * Player 1's deck: 1, 8, 3
 * Player 2's deck: 4, 10, 9, 7, 5, 6, 2
 * Player 1 plays: 1
 * Player 2 plays: 4
 * Playing a sub-game to determine the winner...
 *
 * === Game 5 ===
 *
 * -- Round 1 (Game 5) --
 * Player 1's deck: 8
 * Player 2's deck: 10, 9, 7, 5
 * Player 1 plays: 8
 * Player 2 plays: 10
 * Player 2 wins round 1 of game 5!
 * The winner of game 5 is player 2!
 *
 * ...anyway, back to game 1.
 * Player 2 wins round 15 of game 1!
 *
 * -- Round 16 (Game 1) --
 * Player 1's deck: 8, 3
 * Player 2's deck: 10, 9, 7, 5, 6, 2, 4, 1
 * Player 1 plays: 8
 * Player 2 plays: 10
 * Player 2 wins round 16 of game 1!
 *
 * -- Round 17 (Game 1) --
 * Player 1's deck: 3
 * Player 2's deck: 9, 7, 5, 6, 2, 4, 1, 10, 8
 * Player 1 plays: 3
 * Player 2 plays: 9
 * Player 2 wins round 17 of game 1!
 * The winner of game 1 is player 2!
 *
 *
 * == Post-game results ==
 * Player 1's deck:
 * Player 2's deck: 7, 5, 6, 2, 4, 1, 10, 8, 9, 3
 * After the game, the winning player's score is calculated from the cards they have in their
 * original deck using the same rules as regular Combat.
 * In the above game, the winning player's score is 291.
 *
 * Defend your honor as Raft Captain by playing the small crab in a game of
 * Recursive Combat using the same two decks as before. What is the winning player's score?
 */
object Dec22b extends Dec22 with App {
  val raw = inputAsStringList("2020/dec22.txt")

  val (p1,p2) = parsePlayers(raw)

  def playGame(p1: Player, p2: Player, level: Int): Player = {
    /*
     * During a round of Recursive Combat, if both players have at least as many cards in their
     * own decks as the number on the card they just dealt, the winner of the round is determined
     * by recursing into a sub-game of Recursive Combat.
     */

    var p1Tracker = List[List[Int]]()
    var p2Tracker = List[List[Int]]()

    var round = 1
    do {
      if(p1Tracker.contains(p1.cards) || p2Tracker.contains(p2.cards)) {
        p2.cards = List()
      } else {
        p1Tracker = p1.cards :: p1Tracker
        p2Tracker = p2.cards :: p2Tracker
        println(s"-- Round $round (Game $level)")
        println(s"Player 1 deck: ${p1.cards.mkString(", ")}")
        println(s"Player 2 deck: ${p2.cards.mkString(", ")}")
        val p1h = p1.cards.head
        val p2h = p2.cards.head
        if (p1h <= p1.cards.size - 1 && p2h <= p2.cards.size - 1) {
          // winner found in recurrent game
          val winner = playGame(Player(p1.cards.tail.take(p1h), 1), Player(p2.cards.tail.take(p2h), 2), level + 1)
          if (winner.id == 1) {
            p1.cards = p1.cards.tail ::: List(p1h, p2h)
            p2.cards = p2.cards.tail
          } else {
            p1.cards = p1.cards.tail
            p2.cards = p2.cards.tail ::: List(p2h, p1h)
          }
        } else {
          if (p1h > p2h) {
            p1.cards = p1.cards.tail ::: List(p1h, p2h)
            p2.cards = p2.cards.tail
          } else {
            p1.cards = p1.cards.tail
            p2.cards = p2.cards.tail ::: List(p2h, p1h)
          }
        }
        round += 1
      }
    } while(p1.hasCards && p2.hasCards)

    if(p1.hasCards) {
      p1
    } else {
      p2
    }
  }

  val winner = playGame(p1,p2,1)
  println(s"winner = ${winner.id}")
  val score = winner.cards.reverse.zipWithIndex.map(t => t._1 * (t._2+1))
  println(s"winning score: ${score.sum}")
  // winner p1
  // 33661

}