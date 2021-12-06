package com.degofedal.advent.advent2020

import com.degofedal.advent.AdventOfCode

import scala.collection.mutable

trait Dec21 extends AdventOfCode {

  case class Food(content: Array[String], allergens: Array[String])

  val foodPattern = """^(.*) \(contains (.*)\)$""".r

  def parseFood(s: String): Food = {
    val foodPattern(content, allergens) = s
    Food(content.split(" ").map(_.trim), allergens.split(",").map(_.trim))
  }

  def commonIngredients(f: List[Food]): Array[String] = {
    if(f.size == 1) {
      f.head.content
    } else {
      f.tail.foldLeft(f.head.content)((agg, food) => agg.intersect(food.content))
    }
  }

}

/**
 * --- Day 21: Allergen Assessment ---
 * You reach the train's last stop and the closest you can get to your vacation island without getting wet.
 * There aren't even any boats here, but nothing can stop you now: you build a raft.
 * You just need a few days' worth of food for your journey.
 *
 * You don't speak the local language, so you can't read any ingredients lists.
 * However, sometimes, allergens are listed in a language you do understand.
 * You should be able to use this information to determine which ingredient contains which allergen and
 * work out which foods are safe to take with you on your trip.
 *
 * You start by compiling a list of foods (your puzzle input), one food per line.
 * Each line includes that food's ingredients list followed by some or all of the allergens the food contains.
 *
 * Each allergen is found in exactly one ingredient.
 * Each ingredient contains zero or one allergen.
 * Allergens aren't always marked; when they're listed (as in (contains nuts, shellfish) after an ingredients list),
 * the ingredient that contains each listed allergen will be somewhere in the corresponding ingredients list.
 * However, even if an allergen isn't listed, the ingredient that contains that allergen could still be present:
 * maybe they forgot to label it, or maybe it was labeled in a language you don't know.
 *
 * For example, consider the following list of foods:
 *
 * mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
 * trh fvjkl sbzzf mxmxvkd (contains dairy)
 * sqjhc fvjkl (contains soy)
 * sqjhc mxmxvkd sbzzf (contains fish)
 *
 * The first food in the list has four ingredients (written in a language you don't understand): mxmxvkd, kfcds, sqjhc, and nhms.
 * While the food might contain other allergens, a few allergens the food definitely contains are listed afterward: dairy and fish.
 *
 * The first step is to determine which ingredients can't possibly contain any of the allergens in any food in your list.
 * In the above example, none of the ingredients kfcds, nhms, sbzzf, or trh can contain an allergen.
 *
 * Counting the number of times any of these ingredients appear in any ingredients list produces 5:
 * they all appear once each except sbzzf, which appears twice.
 *
 * Determine which ingredients cannot possibly contain any of the allergens in your list.
 * How many times do any of those ingredients appear?
 */
object Dec21a extends Dec21 with App {
  val foods = inputAsStringList("2020/dec21.txt").map(parseFood)

  // build allergen frequency map
  val allergenMap =
    foods.foldLeft(Map[String, Int]())((agg, food) => {
      agg ++ food.allergens.map(a => (a, agg.getOrElse(a,0)+1)).toMap
    })

  println("Allergen frequency")
  allergenMap.foreach(println)

  // try to correlate ingredients
  /*val allergens = allergenMap./*filter(_._2 > 1).*/flatMap(a => {
    commonIngredients(foods.filter(f => f.allergens.contains(a._1)))
  }).toSet

  val safe = foods.flatMap(_.content).toSet.diff(allergens)*/
  val safe = List()

  println("Safe ingridients")
  safe.foreach(println)

  // safe frequency
  val sumFreq = foods.map(f => f.content.count(c => safe.contains(c))).sum
  println(s"sum $sumFreq")
  // 2659

}

/**
 * --- Part Two ---
 * Now that you've isolated the inert ingredients, you should have enough
 * information to figure out which ingredient contains which allergen.
 *
 * In the above example:
 *
 * mxmxvkd contains dairy.
 * sqjhc contains fish.
 * fvjkl contains soy.
 * Arrange the ingredients alphabetically by their allergen and separate them by commas to produce your canonical dangerous ingredient list.
 * (There should not be any spaces in your canonical dangerous ingredient list.)
 * In the above example, this would be mxmxvkd,sqjhc,fvjkl.
 *
 * Time to stock your raft with supplies. What is your canonical dangerous ingredient list?
 */
object Dec21b extends Dec21 with App {
  val foods = inputAsStringList("2020/dec21.txt").map(parseFood)

  // build allergen frequency map
  val allergenMap =
    foods.foldLeft(Map[String, Int]())((agg, food) => {
      agg ++ food.allergens.map(a => (a, agg.getOrElse(a,0)+1)).toMap
    })

  println("Allergen frequency")
  allergenMap.foreach(println)

  println("Allergen translation")
  // try to correlate ingredients
  var allergens = allergenMap./*filter(_._2 > 1).*/map(a => {
    (a._1 -> commonIngredients(foods.filter(f => f.allergens.contains(a._1))).toList)
  })

  allergens.foreach(println)

  // reduce to 1-to-1 map
  val translation = mutable.Map[String,String]()
  while(allergens.nonEmpty) {
    println(allergens.size)
    val flaf = allergens.filter(a => a._2.size == 1).map(a => a._1 -> a._2.head)
    flaf.foreach(e => translation.put(e._1, e._2))
    val muhh = allergens.map(a => (a._1 -> a._2.diff(flaf.values.toList)))
    allergens = muhh.filter(a => a._2.size > 0)
  }

  println("Allergen translation")
  translation.foreach(println)

  val sortedList = translation.toList.sortBy(_._1).map(_._2)
  println(sortedList.mkString(","))

}